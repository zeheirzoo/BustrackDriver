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

import java.util.ArrayList;
import java.util.List;

public class LineAdapter extends ArrayAdapter<Line> {
    List<Line> lines;
    public LineAdapter(@NonNull Context context, List<Line> objects) {
        super(context,0, objects);
        this.lines=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.line_item, parent, false);
        }
        TextView line_title=convertView.findViewById(R.id.line_title);

        Line line=lines.get(position);
        if ( line.getStation().size()>0)
            line_title.setText(line.getA_b_station().getName()+"  -->  "+line.getB_a_station().getName());

        else
            line_title.setText(line.getStation().size()+"--");


        line_title.getRootView().setBackgroundColor(Color.parseColor(line.getColortype()));


        return convertView;
    }
}
