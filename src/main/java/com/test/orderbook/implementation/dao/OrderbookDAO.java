package com.test.orderbook.implementation.dao;

import com.test.orderbook.implementation.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderbookDAO {

    private List<Order> Asks = new ArrayList<>();

    private List<Order> Bids = new ArrayList<>();


    public OrderbookDAO() {
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
}
