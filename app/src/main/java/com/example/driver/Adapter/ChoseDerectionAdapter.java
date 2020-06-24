package com.example.driver.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.driver.R;
import com.example.driver.model.Line;
import com.example.driver.model.Station;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ChoseDerectionAdapter extends ArrayAdapter<Station> {
    List<Station> stations;
    String[] prices;
    public ChoseDerectionAdapter(@NonNull Context context, List<Station> objects, String[] prices) {
        super(context,0, objects);
        this.stations=objects;
        this.prices=prices;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chose_price_item, parent, false);
        }
        TextView name=convertView.findViewById(R.id.station_name);
        TextView price=convertView.findViewById(R.id.price);

        name.setText(stations.get(position).getName()+"");
//        price.setText(prices[position]+"");

        return convertView;
    }
}