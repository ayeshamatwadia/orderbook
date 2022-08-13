package com.test.orderbook.implementation;

import com.test.orderbook.implementation.Order;
import com.test.orderbook.implementation.constants.Trade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderBook {

    private List<Order> Asks = new ArrayList<>();

    private List<Order> Bids = new ArrayList<>();

    private List<Trade> trades = new ArrayList<>();

    public OrderBook() {

    }

    public List<Order> getAsks() {
        return Asks;
    }

    public void setAsks(List<Order> asks) {
        Asks = asks;
    }

    public List<Order> getBids() {
        return Bids;
    }

    public void setBids(List<Order> bids) {
        Bids = bids;
    }

    public void addSellOrder(Order order){
        this.Asks.add(order);
    }

    public void addBuyOrder(Order order){
        this.Bids.add(order);
    }
}
