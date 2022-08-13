package com.test.orderbook.implementation.constants;

import java.io.Serializable;

public enum Side implements Serializable {
    BUY,
    SELL;

    public String getSide() {
        return this.name();
    }
}
