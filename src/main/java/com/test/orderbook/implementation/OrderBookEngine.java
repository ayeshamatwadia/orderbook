package com.test.orderbook.implementation;

import com.test.orderbook.implementation.constants.Side;
import com.test.orderbook.implementation.dao.OrderbookDAO;
import com.test.orderbook.implementation.dao.TradesDAO;
import com.test.orderbook.implementation.domain.Order;
import com.test.orderbook.implementation.domain.Trade;
import com.test.orderbook.implementation.dto.OrderBookDTO;
import com.test.orderbook.implementation.dto.TradesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderBookEngine {

    private OrderbookDAO orderbookRepo;

    private TradesDAO tradesRepo;

    public OrderbookDAO getOrderbookRepo() {
        return orderbookRepo;
    }

    @Autowired
    public void setOrderbookRepo(OrderbookDAO orderbookRepo) {
        this.orderbookRepo = orderbookRepo;
    }

    public TradesDAO getTradesRepo() {
        return tradesRepo;
    }

    @Autowired
    public void setTradesRepo(TradesDAO tradesRepo) {
        this.tradesRepo = tradesRepo;
    }

    public OrderBookEngine(OrderbookDAO orderbookResource, TradesDAO tradesResource) {
        this.orderbookRepo = orderbookResource;
        this.tradesRepo = tradesResource;
    }

    public OrderBookEngine() {
    }

    public void addBuyOrder(Order order) {
        List<Order> asks = orderbookRepo.getAsks();
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

    public void addSellOrder(Order order) {
        List<Order> bids = orderbookRepo.getBids();
        List<Order> matchingBids = new ArrayList<>();

        BigDecimal currentOrderQuantity = order.getQuantity();
        /*here we are getting all the bids (buyers that are greater than or equal to the sellers price)*/
        for (Order bid : bids) {
            if(currentOrderQuantity.compareTo(BigDecimal.ZERO) <= 0){
                /*exit the loop if we have enough bid-quantity to fullfil the current order - we only need x bids, not all the bids*/
                break;
            }
            if(order.getPrice().compareTo(bid.getPrice()) <= 0){
               matchingBids.add(bid);
               currentOrderQuantity.subtract(bid.getQuantity());
            }
        }
        for (Order matchingBid : matchingBids) {
            if(order.getQuantity().compareTo(matchingBid.getQuantity()) > 0){
                /* sell order wants more quantity than the matched price bid has - partial seller fill*/
                orderbookRepo.getBids().remove(matchingBid);
                Trade trade = new Trade(matchingBid.getPrice(), matchingBid.getQuantity(), matchingBid.getCurrencyPair(), new Timestamp(System.currentTimeMillis()), Side.BUY);
                tradesRepo.getTrades().add(trade);
                BigDecimal orderQuantity = order.getQuantity();
                order.setQuantity(orderQuantity.subtract(matchingBid.getQuantity()));
            } else if (order.getQuantity().compareTo(matchingBid.getQuantity()) < 0) {
                /*sell order wants less quantity than the matched price ask - partial buyer fill*/
                orderbookRepo.getBids().remove(matchingBid);
                Trade trade = new Trade(matchingBid.getPrice(), order.getQuantity(), matchingBid.getCurrencyPair(), new Timestamp(System.currentTimeMillis()), Side.BUY);
                tradesRepo.getTrades().add(trade);
                matchingBid.setQuantity(matchingBid.getQuantity().subtract(order.getQuantity()));
                orderbookRepo.addToBids(matchingBid);
            } else {
                /*the trading quantity for the buy and sell match exactly*/
                orderbookRepo.getBids().remove(matchingBid);
                Trade trade = new Trade(matchingBid.getPrice(), matchingBid.getQuantity(), matchingBid.getCurrencyPair(), new Timestamp(System.currentTimeMillis()), Side.BUY);
                tradesRepo.getTrades().add(trade);
                order.setQuantity(order.getQuantity().subtract(matchingBid.getQuantity()));
            }
        }
        /*If the current sell order has been partially filled or there were no matching bids then add it to the Asks list*/
        if(order.getQuantity().compareTo(BigDecimal.ZERO) > 0){
            orderbookRepo.addToAsks(order);
        }

    }

    public OrderBookDTO getCurrentOrders() {
        return new OrderBookDTO(orderbookRepo.getAsks(), orderbookRepo.getBids());
    }

    public TradesDTO getExecutedTrades(){
        return new TradesDTO(tradesRepo.getTrades());
    }

}

