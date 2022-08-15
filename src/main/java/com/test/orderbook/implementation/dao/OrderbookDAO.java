package com.test.orderbook.implementation.dao;

import com.test.orderbook.implementation.domain.Order;
import com.test.orderbook.implementation.tree.BinarySearchTree;
import org.springframework.stereotype.Repository;

import java.util.*;

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

    public void addToAsks(Order order){
        /*We sort the Asks : Seller prices in Ascending order price and time*/
        Asks.add(order);
        Collections.sort(Asks, Comparator.comparing(Order::getPrice)
                .thenComparing(Order::getOrderPlaced));
    }

    public void addToBids(Order order){
        /*We sort the Bids: Buyer prices in Descending Price but ascending time*/
        Bids.add(order);
        Collections.sort(Bids, Comparator.comparing(Order::getPrice).reversed()
                .thenComparing(Order::getOrderPlaced));
    }
}
