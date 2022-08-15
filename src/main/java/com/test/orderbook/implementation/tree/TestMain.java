package com.test.orderbook.implementation.tree;

import com.test.orderbook.implementation.domain.Order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestMain {
    public static void main(String[] args) {
        TestMain testMain = new TestMain();
        BinarySearchTree binarySearchTree = new BinarySearchTree();

        Order order1 = new Order();
        order1.setPrice(BigDecimal.valueOf(100));
        order1.setOrderPlaced(testMain.getTimestampByString("14-08-2022 17:21:01"));

        Order order2 = new Order();
        order2.setPrice(BigDecimal.valueOf(100));
        order2.setOrderPlaced(testMain.getTimestampByString("14-08-2022 17:21:02"));

        Order order3 = new Order();
        order3.setPrice(BigDecimal.valueOf(100));
        order3.setOrderPlaced(testMain.getTimestampByString("14-08-2022 17:20:21"));

        Order order4 = new Order();
        order4.setPrice(BigDecimal.valueOf(100));
        order4.setOrderPlaced(testMain.getTimestampByString("14-08-2022 17:21:03"));

        Order order5 = new Order();
        order5.setPrice(BigDecimal.valueOf(2000));

        Order order6 = new Order();
        order6.setPrice(BigDecimal.valueOf(5));

        Order order7 = new Order();
        order7.setPrice(BigDecimal.valueOf(8));

        Order order8 = new Order();
        order8.setPrice(BigDecimal.valueOf(2));

        binarySearchTree.insert(order1);
        binarySearchTree.insert(order2);
        binarySearchTree.insert(order3);
        binarySearchTree.insert(order4);
        binarySearchTree.insert(order5);
        binarySearchTree.insert(order6);
        binarySearchTree.insert(order7);
        binarySearchTree.insert(order8);
        binarySearchTree.inorder();

        System.out.println(binarySearchTree);
    }

    public Timestamp getTimestampByString(String timestampString){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(timestampString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        long time = date.getTime();
        return new Timestamp(time);
    }
}
