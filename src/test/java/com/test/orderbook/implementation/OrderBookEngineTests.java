package com.test.orderbook.implementation;

import com.test.orderbook.implementation.constants.CurrencyPair;
import com.test.orderbook.implementation.constants.Side;
import com.test.orderbook.implementation.dao.OrderbookDAO;
import com.test.orderbook.implementation.dao.TradesDAO;
import com.test.orderbook.implementation.domain.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class OrderBookEngineTests {

    @Test
    public void testThatTheAddingSellsToAsksSortsTheListInAscendingOrderPriceAndAscendingTime(){
        OrderbookDAO orderbookDAO = new OrderbookDAO();
        Order order1 = new Order(Side.SELL, BigDecimal.valueOf(12.34345), BigDecimal.valueOf(1000), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:55:23"));
        Order order2 = new Order(Side.SELL, BigDecimal.valueOf(12.34345), BigDecimal.valueOf(908), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:55:24"));
        Order order3 = new Order(Side.SELL, BigDecimal.valueOf(3), BigDecimal.valueOf(1008), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:59:24"));
        Order order4 = new Order(Side.SELL, BigDecimal.valueOf(3), BigDecimal.valueOf(1000), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:59:26"));

        orderbookDAO.addToAsks(order1);
        orderbookDAO.addToAsks(order2);
        orderbookDAO.addToAsks(order3);
        orderbookDAO.addToAsks(order4);

        Assertions.assertEquals(orderbookDAO.getAsks().toString(),
                "[Order{side=SELL, quantity=12.34345, price=908, currencyPair=BTCZAR, orderPlaced=2022-08-14 17:55:24.0}, Order{side=SELL, quantity=12.34345, price=1000, currencyPair=BTCZAR, orderPlaced=2022-08-14 17:55:23.0}, Order{side=SELL, quantity=3, price=1000, currencyPair=BTCZAR, orderPlaced=2022-08-14 17:59:26.0}, Order{side=SELL, quantity=3, price=1008, currencyPair=BTCZAR, orderPlaced=2022-08-14 17:59:24.0}]");
    }

    @Test
    public void testThatAddingTheBuysToTheBidsListSortsInDescendingPriceAndAscendingTime(){
        OrderbookDAO orderbookDAO = new OrderbookDAO();
        Order order1 = new Order(Side.BUY, BigDecimal.valueOf(12.34345), BigDecimal.valueOf(1000), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:55:23"));
        Order order2 = new Order(Side.BUY, BigDecimal.valueOf(12.34345), BigDecimal.valueOf(908), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:55:24"));
        Order order3 = new Order(Side.BUY, BigDecimal.valueOf(3), BigDecimal.valueOf(1008), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:59:24"));
        Order order4 = new Order(Side.BUY, BigDecimal.valueOf(3), BigDecimal.valueOf(1000), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:59:26"));

        orderbookDAO.addToBids(order1);
        orderbookDAO.addToBids(order2);
        orderbookDAO.addToBids(order3);
        orderbookDAO.addToBids(order4);

        System.out.println(orderbookDAO.getBids());

        Assertions.assertEquals(orderbookDAO.getBids().toString(),
                "[Order{side=BUY, quantity=3, price=1008, currencyPair=BTCZAR, orderPlaced=2022-08-14 17:59:24.0}, Order{side=BUY, quantity=12.34345, price=1000, currencyPair=BTCZAR, orderPlaced=2022-08-14 17:55:23.0}, Order{side=BUY, quantity=3, price=1000, currencyPair=BTCZAR, orderPlaced=2022-08-14 17:59:26.0}, Order{side=BUY, quantity=12.34345, price=908, currencyPair=BTCZAR, orderPlaced=2022-08-14 17:55:24.0}]");
    }

    @Test
    public void testAnIncomingBuyOrderDoesNotHaveAnyMatchingSellOrdersEmptyAsks(){
        OrderbookDAO orderbookDAO = new OrderbookDAO();
        TradesDAO tradesDAO = new TradesDAO();

        OrderBookEngine orderBookEngine = new OrderBookEngine(orderbookDAO, tradesDAO);

        Order order1 = new Order(Side.BUY, BigDecimal.valueOf(12.34345), BigDecimal.valueOf(1000), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:55:23"));
        orderBookEngine.addBuyOrder(order1);

        Assertions.assertTrue(orderBookEngine.getCurrentOrders().getAsks().isEmpty());
        Assertions.assertFalse(orderBookEngine.getCurrentOrders().getBids().isEmpty());
        Assertions.assertTrue(orderBookEngine.getExecutedTrades().getTrades().isEmpty());
        Assertions.assertEquals(orderBookEngine.getCurrentOrders().getBids().toString(), "[Order{side=BUY, quantity=12.34345, price=1000, currencyPair=BTCZAR, orderPlaced=2022-08-14 17:55:23.0}]");
    }

    @Test
    public void testAnIncomingBuyOrderDoesNotHaveAnyMatchingSellOrdersFullAsksList() {
        OrderbookDAO orderbookDAO = new OrderbookDAO();
        TradesDAO tradesDAO = new TradesDAO();

        OrderBookEngine orderBookEngine = new OrderBookEngine(orderbookDAO, tradesDAO);

        Order order1 = new Order(Side.BUY, BigDecimal.valueOf(12.34345), BigDecimal.valueOf(1001), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:55:23"));
        Order order2 = new Order(Side.BUY, BigDecimal.valueOf(12.34345), BigDecimal.valueOf(1002), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:55:23"));
        Order order3 = new Order(Side.BUY, BigDecimal.valueOf(12.34345), BigDecimal.valueOf(1004), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:55:23"));
        Order order4 = new Order(Side.BUY, BigDecimal.valueOf(12.34345), BigDecimal.valueOf(1004), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:55:23"));
        Order order5 = new Order(Side.BUY, BigDecimal.valueOf(12.34345), BigDecimal.valueOf(1000.50), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:55:23"));


        orderBookEngine.addSellOrder(order1);
        orderBookEngine.addSellOrder(order2);
        orderBookEngine.addSellOrder(order3);
        orderBookEngine.addSellOrder(order4);
        orderBookEngine.addSellOrder(order5);

        Order order6 = new Order(Side.BUY, BigDecimal.valueOf(12.34345), BigDecimal.valueOf(1000), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:55:23"));
        orderBookEngine.addBuyOrder(order6);

        Assertions.assertEquals(orderBookEngine.getCurrentOrders().getAsks().size(), 5);
        Assertions.assertEquals(orderBookEngine.getCurrentOrders().getBids().size(), 1);
        Assertions.assertEquals(orderBookEngine.getExecutedTrades().getTrades().size(), 0);
    }

    @Test
    public void testAPerfectlyMatchedBuyOrderToAnExistingSellOrder(){
        OrderbookDAO orderbookDAO = new OrderbookDAO();
        TradesDAO tradesDAO = new TradesDAO();

        OrderBookEngine orderBookEngine = new OrderBookEngine(orderbookDAO, tradesDAO);

        Order order1 = new Order(Side.SELL, BigDecimal.valueOf(10), BigDecimal.valueOf(1003), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:55:23"));
        Order order2 = new Order(Side.SELL, BigDecimal.valueOf(8), BigDecimal.valueOf(1001), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:58:23"));

        orderBookEngine.addSellOrder(order1);
        orderBookEngine.addSellOrder(order2);

        Order order3 = new Order(Side.BUY, BigDecimal.valueOf(8), BigDecimal.valueOf(1002), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:59:23"));
        orderBookEngine.addBuyOrder(order3);

        Assertions.assertEquals(orderBookEngine.getCurrentOrders().getBids().size(), 0);
        Assertions.assertEquals(orderBookEngine.getCurrentOrders().getAsks().size(), 1);
        Assertions.assertEquals(orderBookEngine.getTradesRepo().getTrades().size(), 1);

        Assertions.assertEquals(orderBookEngine.getTradesRepo().getTrades().get(0).getPrice(), BigDecimal.valueOf(1001));
        Assertions.assertEquals(orderBookEngine.getTradesRepo().getTrades().get(0).getQuantity(), BigDecimal.valueOf(8));
        Assertions.assertEquals(orderBookEngine.getCurrentOrders().getAsks().toString(), "[Order{side=SELL, quantity=10, price=1003, currencyPair=BTCZAR, orderPlaced=2022-08-14 17:55:23.0}]");
    }


    @Test
    public void testABuyOrderAtAValueWayAboveCurrentMarketPriceMatchesToTheLowestSellPriceAndTrades(){
        OrderbookDAO orderbookDAO = new OrderbookDAO();
        TradesDAO tradesDAO = new TradesDAO();

        OrderBookEngine orderBookEngine = new OrderBookEngine(orderbookDAO, tradesDAO);

        Order order1 = new Order(Side.SELL, BigDecimal.valueOf(10), BigDecimal.valueOf(1050), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:52:23"));
        Order order2 = new Order(Side.SELL, BigDecimal.valueOf(10), BigDecimal.valueOf(1003), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:55:23"));
        Order order3 = new Order(Side.SELL, BigDecimal.valueOf(18), BigDecimal.valueOf(1001), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:58:23"));

        orderBookEngine.addSellOrder(order1);
        orderBookEngine.addSellOrder(order2);
        orderBookEngine.addSellOrder(order3);

        Order order4 = new Order(Side.BUY, BigDecimal.valueOf(2), BigDecimal.valueOf(1050), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:59:23"));
        orderBookEngine.addBuyOrder(order4);

        Assertions.assertEquals(orderBookEngine.getCurrentOrders().getBids().size(), 0);
        Assertions.assertEquals(orderBookEngine.getCurrentOrders().getAsks().size(), 3);
        Assertions.assertEquals(orderBookEngine.getTradesRepo().getTrades().size(), 1);

        Assertions.assertEquals(orderBookEngine.getOrderbookRepo().getAsks().toString(),
                "[Order{side=SELL, quantity=16, price=1001, currencyPair=BTCZAR, orderPlaced=2022-08-14 17:58:23.0}, Order{side=SELL, quantity=10, price=1003, currencyPair=BTCZAR, orderPlaced=2022-08-14 17:55:23.0}, Order{side=SELL, quantity=10, price=1050, currencyPair=BTCZAR, orderPlaced=2022-08-14 17:52:23.0}]");

        Assertions.assertEquals(orderBookEngine.getExecutedTrades().getTrades().get(0).getQuantity(), BigDecimal.valueOf(2));
        Assertions.assertEquals(orderBookEngine.getExecutedTrades().getTrades().get(0).getPrice(), BigDecimal.valueOf(1001));
    }

    @Test
    public void testPartialFillAcrossMultipleSellsFor1BuyOrder(){
        OrderbookDAO orderbookDAO = new OrderbookDAO();
        TradesDAO tradesDAO = new TradesDAO();

        OrderBookEngine orderBookEngine = new OrderBookEngine(orderbookDAO, tradesDAO);

        Order order1 = new Order(Side.SELL, BigDecimal.valueOf(10), BigDecimal.valueOf(1010), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:52:23"));
        Order order2 = new Order(Side.SELL, BigDecimal.valueOf(10), BigDecimal.valueOf(1003), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:55:23"));
        Order order3 = new Order(Side.SELL, BigDecimal.valueOf(18), BigDecimal.valueOf(1001), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:58:23"));

        orderBookEngine.addSellOrder(order1);
        orderBookEngine.addSellOrder(order2);
        orderBookEngine.addSellOrder(order3);

        Order order4 = new Order(Side.BUY, BigDecimal.valueOf(50), BigDecimal.valueOf(1050), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:59:23"));
        orderBookEngine.addBuyOrder(order4);

        Assertions.assertEquals(orderBookEngine.getCurrentOrders().getBids().size(), 1);
        Assertions.assertEquals(orderBookEngine.getCurrentOrders().getAsks().size(), 0);
        Assertions.assertEquals(orderBookEngine.getTradesRepo().getTrades().size(), 3);

        Assertions.assertEquals(orderBookEngine.getCurrentOrders().getBids().toString(), "[Order{side=BUY, quantity=12, price=1050, currencyPair=BTCZAR, orderPlaced=2022-08-14 17:59:23.0}]");
        Assertions.assertEquals(orderBookEngine.getExecutedTrades().getTrades().get(0).getPrice(), BigDecimal.valueOf(1001));
        Assertions.assertEquals(orderBookEngine.getExecutedTrades().getTrades().get(0).getQuantity(), BigDecimal.valueOf(18));
        Assertions.assertEquals(orderBookEngine.getExecutedTrades().getTrades().get(1).getPrice(), BigDecimal.valueOf(1003));
        Assertions.assertEquals(orderBookEngine.getExecutedTrades().getTrades().get(1).getQuantity(), BigDecimal.valueOf(10));
        Assertions.assertEquals(orderBookEngine.getExecutedTrades().getTrades().get(2).getPrice(), BigDecimal.valueOf(1010));
        Assertions.assertEquals(orderBookEngine.getExecutedTrades().getTrades().get(2).getQuantity(), BigDecimal.valueOf(10));
    }

    @Test
    public void testPartialFillAcrossMultipleBuysFor1SellOrder(){
        OrderbookDAO orderbookDAO = new OrderbookDAO();
        TradesDAO tradesDAO = new TradesDAO();

        OrderBookEngine orderBookEngine = new OrderBookEngine(orderbookDAO, tradesDAO);

        Order order1 = new Order(Side.BUY, BigDecimal.valueOf(10), BigDecimal.valueOf(1010), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:52:23"));
        Order order2 = new Order(Side.BUY, BigDecimal.valueOf(10), BigDecimal.valueOf(1003), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:55:23"));
        Order order3 = new Order(Side.BUY, BigDecimal.valueOf(18), BigDecimal.valueOf(1015), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:58:23"));

        orderBookEngine.addBuyOrder(order1);
        orderBookEngine.addBuyOrder(order2);
        orderBookEngine.addBuyOrder(order3);

        Order order4 = new Order(Side.SELL, BigDecimal.valueOf(50), BigDecimal.valueOf(1002), CurrencyPair.BTCZAR, getTimestampByString("14-08-2022 17:59:23"));
        orderBookEngine.addSellOrder(order4);

        Assertions.assertEquals(orderBookEngine.getCurrentOrders().getBids().size(), 0);
        Assertions.assertEquals(orderBookEngine.getCurrentOrders().getAsks().size(), 1);
        Assertions.assertEquals(orderBookEngine.getTradesRepo().getTrades().size(), 3);

        Assertions.assertEquals(orderBookEngine.getCurrentOrders().getAsks().toString(), "[Order{side=SELL, quantity=12, price=1002, currencyPair=BTCZAR, orderPlaced=2022-08-14 17:59:23.0}]");
        Assertions.assertEquals(orderBookEngine.getTradesRepo().getTrades().get(0).getPrice(), BigDecimal.valueOf(1015));
        Assertions.assertEquals(orderBookEngine.getTradesRepo().getTrades().get(1).getPrice(), BigDecimal.valueOf(1010));
        Assertions.assertEquals(orderBookEngine.getTradesRepo().getTrades().get(2).getPrice(), BigDecimal.valueOf(1003));
    }

    private Timestamp getTimestampByString(String timestampString){
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
