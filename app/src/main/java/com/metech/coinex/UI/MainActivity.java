package com.metech.coinex.UI;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.metech.coinex.Adapter.CoinAdapter;
import com.metech.coinex.Model.CoinItems;
import com.metech.coinex.R;
import com.metech.coinex.Util.AppController;
import com.metech.coinex.Util.AppUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<CoinItems> list;
    ProgressDialog progressDialog;
    RelativeLayout errorLayout, noInternetLayout;
    SwipeRefreshLayout refreshLayout;
    AppUtil appUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("HomeFeed", "..........................Entered onCreate");
        appUtil = new AppUtil();

        //initializing variables
        intVar();
    }

    void intVar(){

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        errorLayout = (RelativeLayout) findViewById(R.id.connection_error_layout);
        errorLayout.setVisibility(View.GONE);
        noInternetLayout = (RelativeLayout) findViewById(R.id.no_internet);
        noInternetLayout.setVisibility(View.GONE);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        list = new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading, Please wait...");
        progressDialog.setCancelable(false);

        //Checking for network access
        checkConnection();

        //swipe down to refresh
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                noInternetLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                errorLayout.setVisibility(View.GONE);
               checkConnection();
            }
        });
    }

    void checkConnection(){
        if (appUtil.networkInfo(getApplicationContext())) {
            loadFeed();
        } else {
            noInternetLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    void loadFeed() {

        progressDialog.show();
        String URL_FEED = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=NGN,USD,EUR,JPY,CNY,KES,GHS,UGX,ZAR,XAF,NZD,MYR,BND,GEL,RUB,INR,GBP,AUD,CAD,CHF";

        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(URL_FEED, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    parseJsonFeed(jsonObject);
                    progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    progressDialog.dismiss();
                    errorLayout.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }
    }

    private void parseJsonFeed(JSONObject response) {

        try {

            AppUtil appUtil = new AppUtil();

            JSONObject btcObject = response.getJSONObject("BTC");
            JSONObject ethObject = response.getJSONObject("ETH");

            Iterator<?> btcKeys = btcObject.keys();
            Iterator<?> ethKeys = ethObject.keys();

            while(btcKeys.hasNext() && ethKeys.hasNext()) {
                String btcValue = (String) btcKeys.next();
                String ethValue = (String) ethKeys.next();

                CoinItems items = new CoinItems(appUtil.getCurrencyName(btcValue), btcObject.getDouble(btcValue), ethObject.getDouble(ethValue));

              list.add(items);

            }

            CoinAdapter adapter = new CoinAdapter(list, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();


        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Error while fetching data", Toast.LENGTH_LONG).show();
        }
    }

}
