package com.metech.coinex.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.metech.coinex.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseConverter extends AppCompatActivity {

    String currency, selectedItem;
    double btcRate, ethRate, input;
    LinearLayout btc_layout, eth_layout, currency_layout;
    CardView card_layout;
    private TextView btc_conversion;
    private TextView eth_conversion;
    private TextView currency_conversion;
    Button convert;
    EditText edit;

    public BaseConverter() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_converter);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.second_activity);

        //Initializing Class variables
        intVar();
    }

    void intVar() {
        Intent intent = getIntent();
        currency = intent.getStringExtra("Currency");
        btcRate = intent.getDoubleExtra("Btc", 0);
        ethRate = intent.getDoubleExtra("Eth", 0);

        edit = (EditText) findViewById(R.id.edit_value);
        convert = (Button) findViewById(R.id.convertButton);
        card_layout = (CardView) findViewById(R.id.result_card);
        card_layout.setVisibility(View.GONE);
        btc_layout = (LinearLayout) findViewById(R.id.btc_layout);
        eth_layout = (LinearLayout) findViewById(R.id.eth_layout);
        currency_layout = (LinearLayout) findViewById(R.id.currency_layout);
        TextView currency_label = (TextView) findViewById(R.id.currency_label);
        currency_label.setText(currency);
        btc_conversion = (TextView) findViewById(R.id.BitCoin_conversion);
        eth_conversion = (TextView) findViewById(R.id.Ethereum_conversion);
        currency_conversion = (TextView) findViewById(R.id.currency_conversion);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String [] array = {currency, "BitCoin", "Ethereum"};

        List<String> spinner_array = new ArrayList<>(Arrays.asList(array));

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinner_array);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //Spinner ItemSelectedListener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Button OnClickListener
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               doConversion();
            }
        });
    }

    void doConversion(){
        double currencyValue, btcValue, ethValue;
        try {

            input = Double.parseDouble(edit.getText().toString());

            if (selectedItem.equalsIgnoreCase(currency)) {
                ethValue = input / ethRate;
                btcValue = input / btcRate;
                currency_layout.setVisibility(View.GONE);
                btc_layout.setVisibility(View.VISIBLE);
                eth_layout.setVisibility(View.VISIBLE);
                btc_conversion.setText(String.format("%1$,.5f", btcValue));
                eth_conversion.setText(String.format("%1$,.5f", ethValue));
                card_layout.setVisibility(View.VISIBLE);
            } else if (selectedItem.equalsIgnoreCase("BitCoin")) {
                currencyValue = input * btcRate;
                ethValue = (input * btcRate)/ethRate;
                btc_layout.setVisibility(View.GONE);
                currency_layout.setVisibility(View.VISIBLE);
                eth_layout.setVisibility(View.VISIBLE);
                currency_conversion.setText(String.format("%1$,.5f", currencyValue));
                eth_conversion.setText(String.format("%1$,.5f", ethValue));
                card_layout.setVisibility(View.VISIBLE);
            } else if (selectedItem.equalsIgnoreCase("Ethereum")) {
                currencyValue = input * ethRate;
                btcValue = (input * ethRate)/btcRate;
                eth_layout.setVisibility(View.GONE);
                btc_layout.setVisibility(View.VISIBLE);
                currency_layout.setVisibility(View.VISIBLE);
                btc_conversion.setText(String.format("%1$,.5f", btcValue));
                currency_conversion.setText(String.format("%1$,.5f", currencyValue));
                card_layout.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            Toast.makeText(this, "Not a valid Number!", Toast.LENGTH_LONG).show();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
