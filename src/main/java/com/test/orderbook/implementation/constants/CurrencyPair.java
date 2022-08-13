package com.test.orderbook.implementation.constants;

import java.io.Serializable;

public enum CurrencyPair implements Serializable {
    BTCZAR;

    public String getCurrencyPair() {
        return this.name();
    }
}
