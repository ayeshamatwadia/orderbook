package com.test.orderbook.implementation.domain;

import com.test.orderbook.implementation.constants.CurrencyPair;
import com.test.orderbook.implementation.constants.Side;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Trade {
    private BigDecimal price;
    private BigDecimal quantity;
    private CurrencyPair currencyPair;
    private Timestamp tradedAt;
    private Side takerSide;

    public Trade(BigDecimal price, BigDecimal quantity, CurrencyPair currencyPair, Timestamp tradedAt, Side takerSide) {
        this.price = price;
        this.quantity = quantity;
        this.currencyPair = currencyPair;
        this.tradedAt = tradedAt;
        this.takerSide = takerSide;
    }

    public Trade() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

    public Timestamp getTradedAt() {
        return tradedAt;
    }

    public void setTradedAt(Timestamp tradedAt) {
        this.tradedAt = tradedAt;
    }

    public Side getTakerSide() {
        return takerSide;
    }

    public void setTakerSide(Side takerSide) {
        this.takerSide = takerSide;
    }
}
