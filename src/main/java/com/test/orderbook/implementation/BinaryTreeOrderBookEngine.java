package com.test.orderbook.implementation;

import com.test.orderbook.implementation.constants.Side;
import com.test.orderbook.implementation.dao.OrderbookTreeDAO;
import com.test.orderbook.implementation.dao.TradesDAO;
import com.test.orderbook.implementation.domain.Order;
import com.test.orderbook.implementation.domain.Trade;
import com.test.orderbook.implementation.dto.OrderBookDTO;
import com.test.orderbook.implementation.dto.TradesDTO;
import com.test.orderbook.implementation.tree.BinarySearchTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class BinaryTreeOrderBookEngine {

    private OrderbookTreeDAO orderbookRepo;

    private TradesDAO tradeRepo;

    public OrderbookTreeDAO getOrderbookRepo() {
        return orderbookRepo;
    }

    @Autowired
    public void setOrderbookRepo(OrderbookTreeDAO orderbookRepo) {
        this.orderbookRepo = orderbookRepo;
    }

    public TradesDAO getTradeRepo() {
        return tradeRepo;
    }

    @Autowired
    public void setTradeRepo(TradesDAO tradeRepo) {
        this.tradeRepo = tradeRepo;
    }

    public BinaryTreeOrderBookEngine(OrderbookTreeDAO orderbookRepo, TradesDAO tradeRepo) {
        this.orderbookRepo = orderbookRepo;
        this.tradeRepo = tradeRepo;
    }

    public BinaryTreeOrderBookEngine() {
    }



    public OrderBookDTO getCurrentOrders() {
        return null;
    }


    public TradesDTO getExecutedTrades() {
        return null;
    }


    public void addBuyOrder(Order order) {
        BinarySearchTree asks = orderbookRepo.getAsks();
        List<Order> matchingAsks = new ArrayList<>();

        BigDecimal currentOrderQuantity = order.getQuantity();
        /*here we are getting all the asks (seller orders that are less than or equal to the buyers price)*/
        for (Order ask : asks) {
            if(currentOrderQuantity.compareTo(BigDecimal.ZERO) <=0){
                /*exit the loop if we have enough ask-quantity to fullfil the current order - we only need x asks, not all the asks*/
                break;
            }else {
                if(order.getPrice().compareTo(ask.getPrice()) >= 0) {
                    matchingAsks.add(ask);
                    currentOrderQuantity.subtract(ask.getQuantity());
                }
            }
        }

        for (Order matchingAsk : matchingAsks) {
            if(order.getQuantity().compareTo(matchingAsk.getQuantity()) > 0){
                /*new order is buying more quantity than the matched price ask has - partial buyer fill*/
                orderbookRepo.getAsks().remove(matchingAsk);
                Trade trade = new Trade(matchingAsk.getPrice(), matchingAsk.getQuantity(), matchingAsk.getCurrencyPair(), new Timestamp(System.currentTimeMillis()), Side.BUY);
                tradesRepo.getTrades().add(trade);
                BigDecimal orderQuantity = order.getQuantity();
                order.setQuantity(orderQuantity.subtract(matchingAsk.getQuantity()));
            } else if (order.getQuantity().compareTo(matchingAsk.getQuantity())< 0) {
                /*new order is buying less quantity than the matched price ask - partial seller fill*/
                orderbookRepo.getAsks().remove(matchingAsk);
                Trade trade = new Trade(matchingAsk.getPrice(), order.getQuantity(), matchingAsk.getCurrencyPair(), new Timestamp(System.currentTimeMillis()), Side.BUY);
                tradesRepo.getTrades().add(trade);
                matchingAsk.setQuantity(matchingAsk.getQuantity().subtract(order.getQuantity()));
                orderbookRepo.addToAsks(matchingAsk);
                order.setQuantity(BigDecimal.ZERO);
                break;
            } else {
                /*the trading quantity for the buy and sell match exactly*/
                orderbookRepo.getAsks().remove(matchingAsk);
                Trade trade = new Trade(matchingAsk.getPrice(), matchingAsk.getQuantity(), matchingAsk.getCurrencyPair(), new Timestamp(System.currentTimeMillis()), Side.BUY);
                tradesRepo.getTrades().add(trade);
                order.setQuantity(order.getQuantity().subtract(matchingAsk.getQuantity()));
            }
        }
        /*If the current buy order has been partially filled or there were no matching asks then add it to the Bids list*/
        if(order.getQuantity().compareTo(BigDecimal.ZERO) > 0) {
            orderbookRepo.addToBids(order);
        }
    }


    public void addSellOrder(Order limitOrder) {

    }
}
