package com.test.orderbook.implementation.dao;

import com.test.orderbook.implementation.domain.Trade;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TradesDAO {

    List<Trade> trades = new ArrayList<>();

    public TradesDAO() {
    }

    public TradesDAO(List<Trade> trades) {
        this.trades = trades;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }
}
