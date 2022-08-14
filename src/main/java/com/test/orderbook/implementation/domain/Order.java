package com.test.orderbook.implementation.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.test.orderbook.implementation.constants.CurrencyPair;
import com.test.orderbook.implementation.constants.Side;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Order implements Serializable {
    private Side side;
    private BigDecimal quantity;
    private BigDecimal price;
    private CurrencyPair currencyPair;

    private Timestamp orderPlaced;

    public Order() {
    }

    @JsonCreator
    public Order(Side side, BigDecimal quantity, BigDecimal price, CurrencyPair currencyPair, Timestamp orderPlaced) {
        this.side = side;
        this.quantity = quantity;
        this.price = price;
        this.currencyPair = currencyPair;
        this.orderPlaced = orderPlaced;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

    public Timestamp getOrderPlaced() {
        return orderPlaced;
    }

    public void setOrderPlaced(Timestamp orderPlaced) {
        this.orderPlaced = orderPlaced;
    }

    @Override
    public String toString() {
        return "Order{" +
                "side=" + side +
                ", quantity=" + quantity +
                ", price=" + price +
                ", currencyPair=" + currencyPair +
                ", orderPlaced=" + orderPlaced +
                '}';
    }
}
