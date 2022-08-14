This is a very basic implementation of an orderbook. To simplify my code
I worked with only 1 instrument/trading pair BTCZAR however this can be extended
to many other orderbooks for different instruments.

During my research I came across many matching algorithms used on trading apps:
1. FIFO - first in first out - price-time prioritization (This is how I have implemented matching)
2. Pro-Rata - price-quantity-time prioritization
3. LMM - Lead Market Makers - Prioritize the Market makers for a given price
etc

My service has 3 endpoints:

http://localhost:8080/orderbook
Gets the orderbook (Can be a public API and does not require any authorization)

http://localhost:8080/trades
Gets executed Trades (Can be a public API and does not require any authorization)

POST: http://localhost:8080/orders/limit
{
    "side": "SELL",
    "quantity": 12,
    "price": 1210,
    "currencyPair": "BTCZAR"
}
(Todo: This would ordinarily a protected API and we would need to know that there is
sufficient funds for the order to be placed.
)

todo: look into the current data structures used for the Asks and Bids List
I am currently using an ArrayList, which I do not think is the best structure
for the problem at hand but I chose to get started. I have looked at perhaps a
TreeMap data type for the Asks and Bids list as it is sorted datastructure

