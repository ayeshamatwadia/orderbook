package com.test.orderbook.implementation.dto;

import com.test.orderbook.implementation.domain.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderBookDTO {

    List<Order> asks = new ArrayList<>();
    List<Order> bids = new ArrayList<>();

    public OrderBookDTO() {
    }

    public OrderBookDTO(List<Order> asks, List<Order> bids) {
        this.asks = asks;
        this.bids = bids;
    }

    public List<Order> getAsks() {
        return asks;
    }

    public void setAsks(List<Order> asks) {
        this.asks = asks;
    }

    public List<Order> getBids() {
        return bids;
    }

    public void setBids(List<Order> bids) {
        this.bids = bids;
    }
}
