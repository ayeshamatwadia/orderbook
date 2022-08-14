package com.test.orderbook.implementation.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.test.orderbook.implementation.constants.CurrencyPair;
import com.test.orderbook.implementation.constants.Side;

import java.io.Serializable;
import java.math.BigDecimal;

public class Order implements Serializable {
    private Side side;
    private BigDecimal quantity;
    private BigDecimal price;
    private CurrencyPair currencyPair;

    public Order() {
    }

    @JsonCreator
    public Order(Side side, BigDecimal quantity, BigDecimal price, CurrencyPair currencyPair) {
        this.side = side;
        this.quantity = quantity;
        this.price = price;
        this.currencyPair = currencyPair;
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
}
