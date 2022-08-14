package com.test.orderbook.implementation.dto;

import com.test.orderbook.implementation.domain.Trade;

import java.util.ArrayList;
import java.util.List;

public class TradesDTO {

    List<Trade> trades = new ArrayList<>();

    public TradesDTO() {
    }

    public TradesDTO(List<Trade> trades) {
        this.trades = trades;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }
}
