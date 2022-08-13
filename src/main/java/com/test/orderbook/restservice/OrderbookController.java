package com.test.orderbook.restservice;

import com.test.orderbook.implementation.Order;
import com.test.orderbook.implementation.OrderBook;
import com.test.orderbook.implementation.constants.CurrencyPair;
import com.test.orderbook.implementation.constants.Side;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class OrderbookController {

    private OrderBook orderBook;

    @Autowired
    public OrderbookController(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    @GetMapping("/sayHello")
    public String SayHello(@RequestParam(value = "name", defaultValue = "") String name) {
        return "Hello " + name;
    }

    @GetMapping("/orderbook")
    public OrderBook getOrders() {
        return orderBook;
    }

    @GetMapping("/test")
    public Order test(){
        return new Order(Side.BUY, BigDecimal.valueOf(12), BigDecimal.valueOf(1209), CurrencyPair.BTCZAR);
    }

    @PostMapping("/orders/limit")
    public ResponseEntity placeLimitOrder(@RequestBody Order limitOrder) {
        if (limitOrder.getCurrencyPair().equals(CurrencyPair.BTCZAR)) {
            if (limitOrder.getSide().equals(Side.BUY)) {
                orderBook.addBuyOrder(limitOrder);
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("");
            } else if (limitOrder.getSide().equals(Side.SELL)) {
                orderBook.addSellOrder(limitOrder);
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("");
            } else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Side is required to be BUY or SELL " + limitOrder.getSide());
            }
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Unsupported currency pair " + limitOrder.getCurrencyPair());
        }
    }
}
