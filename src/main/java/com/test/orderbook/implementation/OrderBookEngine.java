package com.test.orderbook.implementation;

import com.test.orderbook.implementation.dao.OrderbookDAO;
import com.test.orderbook.implementation.dao.TradesDAO;
import com.test.orderbook.implementation.domain.Order;
import com.test.orderbook.implementation.dto.OrderBookDTO;
import com.test.orderbook.implementation.dto.TradesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

