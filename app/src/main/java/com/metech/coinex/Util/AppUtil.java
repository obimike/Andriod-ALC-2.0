package com.metech.coinex.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by sampeke on 10/26/2017.
 * Package com.metech.coinex.Util
 */

public class AppUtil {

    //Get full money name from currency code
    public String getCurrencyName(String CurrencySymbols) {
        switch (CurrencySymbols) {
            case "NGN": return "Naira";
            case "UGX": return "Ugandan Shilling";
            case "ZAR": return "Rand";
            case "XAF": return "CFA Franc BCEAO";
            case "NZD": return "New Zealand Dollar";
            case "USD": return "US Dollar";
            case "EUR": return "Euro";
            case "JPY": return "Yen";
            case "GBP": return "Pound Sterling";
            case "AUD": return "Australian Dollar";
            case "CAD": return "Canadian Dollar";
            case "CHF": return "Swiss Franc";
            case "CNY": return "Yuan";
            case "KES": return "Kenyan Shilling";
            case "GHS": return "Cedi";
            case "MYR": return "Malaysian Ringgit";
            case "BND": return "Brunei Dollar";
            case "GEL": return "Lari";
            case "RUB": return "Russian Ruble";
            case "INR": return "Indian Rupee";
            default: return null;
        }
    }

    //get network info
    public boolean networkInfo(Context c) {
        ConnectivityManager connMgr = (ConnectivityManager)
                c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
