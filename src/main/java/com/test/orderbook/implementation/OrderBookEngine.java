package com.test.orderbook.implementation;

import com.test.orderbook.implementation.constants.CurrencyPair;
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

    public void addBuyOrder(Order order){
        List<Order> asks = orderbookRepo.getAsks();
        for (Order ask : asks) {
            List<Order> matchingAsks = new ArrayList<>();
            /*here we are getting all the asks (seller orders that are less than or equal to the buyers price)*/
            if(order.getPrice().compareTo(ask.getPrice()) >= 0) {
                 matchingAsks.add(ask);
            }

            for (Order matchingAsk : matchingAsks) {
                if(order.getQuantity().compareTo(matchingAsk.getQuantity()) > 0){
                    /*we buying more quantity than the matched price ask has - partial buyer fill*/
                    orderbookRepo.getAsks().remove(matchingAsk);
                    Trade trade = new Trade(matchingAsk.getPrice(), matchingAsk.getQuantity(), matchingAsk.getCurrencyPair(), new Timestamp(System.currentTimeMillis()), Side.BUY);
                    tradesRepo.getTrades().add(trade);
                    order.getQuantity().subtract(matchingAsk.getQuantity());
                } else if (order.getQuantity().compareTo(matchingAsk.getQuantity())< 0) {
                    /*we buying less quantity than the matched price ask - partial seller fill*/
                    
                } else {
                    /*the trading quantity for the buy and sell match exactly*/
                }
            }
            /*If the currennt order has been partially filled then add it to the Bids list with the remaining quantity*/
            if(order.getQuantity().compareTo(BigDecimal.ZERO) > 0){
                orderbookRepo.getBids().add(order);
            }

        }
        this.orderbookRepo.getBids().add(order);
    }

    public void addSellOrder(Order order){
        this.orderbookRepo.getAsks().add(order);
    }

    public OrderBookDTO getCurrentOrders() {
        return new OrderBookDTO(orderbookRepo.getAsks(), orderbookRepo.getBids());
    }

    public TradesDTO getExecutedTrades(){
        return new TradesDTO(tradesRepo.getTrades());
    }

}

