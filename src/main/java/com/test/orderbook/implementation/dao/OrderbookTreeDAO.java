package com.test.orderbook.implementation.dao;

import com.test.orderbook.implementation.domain.Order;
import com.test.orderbook.implementation.tree.BinarySearchTree;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Comparator;

@Repository
public class OrderbookTreeDAO {

    BinarySearchTree Asks = new BinarySearchTree();

    BinarySearchTree Bids = new BinarySearchTree();

    public OrderbookTreeDAO() {
    }

    public BinarySearchTree getAsks() {
        return Asks;
    }

    public void setAsks(BinarySearchTree asks) {
        Asks = asks;
    }

    public BinarySearchTree getBids() {
        return Bids;
    }

    public void setBids(BinarySearchTree bids) {
        Bids = bids;
    }

    public void addToAsks(Order order){
        /*We sort the Asks : Seller prices in Ascending order price and time*/
        Asks.insert(order);
        Asks.inorder();
    }

    public void addToBids(Order order){
        /*We sort the Bids: Buyer prices in Descending Price but ascending time*/
        Bids.insert(order);
        Bids.inorder();
    }
}
