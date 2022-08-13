package com.test.orderbook.implementation.constants;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Trade {
    private BigDecimal price;
    private BigDecimal quantity;
    private CurrencyPair currencyPair;
    private Timestamp tradedAt;
    private Side takerSide;

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
