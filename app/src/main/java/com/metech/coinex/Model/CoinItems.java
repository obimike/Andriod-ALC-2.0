package com.metech.coinex.Model;

/**
 * Created by sampeke on 10/24/2017.
 * Package com.metech.coinex.Model
 */

public class CoinItems {

    private String currency;
    private double btcValue, ethValue;

    public CoinItems(String currency, double btcValue, double ethValue) {
        this.currency = currency;
        this.btcValue = btcValue;
        this.ethValue = ethValue;
    }

    public String getCurrency() {
        return currency;
    }

    public double getBtcValue() {
        return btcValue;
    }

    public double getEthValue() {
        return ethValue;
    }


}
