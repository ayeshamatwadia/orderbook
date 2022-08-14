package com.test.orderbook.restservice;

import com.test.orderbook.implementation.domain.Order;
import com.test.orderbook.implementation.OrderBookEngine;
import com.test.orderbook.implementation.constants.CurrencyPair;
import com.test.orderbook.implementation.constants.Side;
import com.test.orderbook.implementation.dto.OrderBookDTO;
import com.test.orderbook.implementation.dto.TradesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@RestController
public class OrderbookRestController {

    private OrderBookEngine orderBookEngine;

    @Autowired
    public OrderbookRestController(OrderBookEngine orderBook) {
        this.orderBookEngine = orderBook;
    }

    @GetMapping("/test")
    public Order test(){
        return new Order(Side.BUY, BigDecimal.valueOf(12), BigDecimal.valueOf(1209), CurrencyPair.BTCZAR, new Timestamp(System.currentTimeMillis()));
    }

    @GetMapping("/orderbook")
    public OrderBookDTO getOrders() {
        return orderBookEngine.getCurrentOrders();
    }

    @GetMapping("/trades")
    public TradesDTO getTrades() {
        return orderBookEngine.getExecutedTrades();
    }

    @PostMapping("/orders/limit")
    public ResponseEntity placeLimitOrder(@RequestBody Order limitOrder) {
        limitOrder.setOrderPlaced(new Timestamp(System.currentTimeMillis()));
        if (limitOrder.getSide().equals(Side.BUY)){
            orderBookEngine.addBuyOrder(limitOrder);
        } else {
            orderBookEngine.addSellOrder(limitOrder);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}
