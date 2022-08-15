package com.test.orderbook.restservice;

import com.test.orderbook.implementation.BinaryTreeOrderBookEngine;
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

    private boolean useBinaryTreeImplementation;
    private OrderBookEngine orderBookEngine;

    private BinaryTreeOrderBookEngine binaryTreeOrderBookEngine;

    @Autowired
    public OrderbookRestController(OrderBookEngine orderBook, BinaryTreeOrderBookEngine binaryTreeOrderBookEngine) {
        this.orderBookEngine = orderBook;
    }

    @GetMapping("/test")
    public Order test(){
        return new Order(Side.BUY, BigDecimal.valueOf(12), BigDecimal.valueOf(1209), CurrencyPair.BTCZAR, new Timestamp(System.currentTimeMillis()));
    }

    @GetMapping("/orderbook")
    public OrderBookDTO getOrders() {
        if(useBinaryTreeImplementation){
            return binaryTreeOrderBookEngine.getCurrentOrders();
        }else{
            return orderBookEngine.getCurrentOrders();
        }
    }

    @GetMapping("/trades")
    public TradesDTO getTrades() {
        if(useBinaryTreeImplementation){
            return binaryTreeOrderBookEngine.getExecutedTrades();
        } else{
            return orderBookEngine.getExecutedTrades();
        }
    }

    @PostMapping("/orders/limit")
    public ResponseEntity placeLimitOrder(@RequestBody Order limitOrder) {
        limitOrder.setOrderPlaced(new Timestamp(System.currentTimeMillis()));
        if (limitOrder.getSide().equals(Side.BUY)){
            if(useBinaryTreeImplementation){
                binaryTreeOrderBookEngine.addBuyOrder(limitOrder);
            }else {
                orderBookEngine.addBuyOrder(limitOrder);
            }
        } else {
            if(useBinaryTreeImplementation){
                binaryTreeOrderBookEngine.addSellOrder(limitOrder);
            }else {
                orderBookEngine.addSellOrder(limitOrder);
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}
