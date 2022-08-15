This is a very basic implementation of an orderbook. To simplify my code
I worked with only 1 instrument/trading pair BTCZAR however this can be extended
to many other orderbooks for different instruments.

During my research I came across many matching algorithms used on trading apps:
1. FIFO - first in first out - price-time prioritization (This is how I have implemented matching)
2. Pro-Rata - price-quantity-time prioritization
3. LMM - Lead Market Makers - Prioritize the Market makers for a given price
etc

My service has 3 endpoints: Which can be imported into POSTMAN using the
'Orderbook Collection.postman_collection.json' in the resources folder

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
(This has some basic authentication with an API key)

