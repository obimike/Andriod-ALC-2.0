package com.metech.coinex.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.metech.coinex.Model.CoinItems;
import com.metech.coinex.R;
import com.metech.coinex.UI.BaseConverter;

import java.util.ArrayList;

/**
 * Created by sampeke on 10/24/2017.
 * Package com.metech.coinex.Model
 */

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.MyViewHolder> {

    private ArrayList<CoinItems> coinItems = new ArrayList<>();
    private Context context;

    public CoinAdapter(ArrayList<CoinItems> coinItems, Context context) {
        this.coinItems = coinItems;
        this.context = context;
    }

    @Override
    public CoinAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.compare_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CoinAdapter.MyViewHolder holder, int position) {
        final CoinItems items = coinItems.get(position);
        holder.currency.setText(items.getCurrency());
        holder.btc.setText(String.format("%1$,.2f", items.getBtcValue()));
        holder.eth.setText(String.format("%1$,.2f", items.getEthValue()));

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BaseConverter.class);
                intent.putExtra("Currency", items.getCurrency());
                intent.putExtra("Btc", items.getBtcValue());
                intent.putExtra("Eth", items.getEthValue());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return coinItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView currency, btc, eth;
        LinearLayout card;
        MyViewHolder(View itemView) {
            super(itemView);
            currency = itemView.findViewById(R.id.conrrency);
            btc = itemView.findViewById(R.id.BitCoin_value);
            eth = itemView.findViewById(R.id.Ethereum_value);
            card = itemView.findViewById(R.id.card);
        }
    }
}
