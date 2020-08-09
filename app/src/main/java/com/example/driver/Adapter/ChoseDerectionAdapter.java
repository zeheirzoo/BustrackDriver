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

import java.util.ArrayList;
import java.util.List;

public class ChoseDerectionAdapter extends ArrayAdapter<Station> {
    List<Station> stations;
    List<String> prices;
    List<Integer>colors=new ArrayList<>();
    public ChoseDerectionAdapter(@NonNull Context context, List<Station> objects,List<String> prices) {
        super(context,0, objects);
        this.stations=objects;
        this.prices=prices;
        this.colors=new ArrayList<>();
        this.colors.add(R.color.colorPrimary);
        this.colors.add(R.color.green_light);
        this.colors.add(R.color.red);


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
        if (prices.size()-1>=position){
            price.setText(prices.get(position)+"");
        }else
            price.setText(prices.get(prices.size()-1)+"");

        price.getRootView().setBackgroundColor((int)colors.get(position));
        return convertView;
    }
}