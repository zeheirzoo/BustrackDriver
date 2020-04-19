package com.example.driver;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import params.com.stepview.StatusView;
import params.com.stepview.StatusViewScroller;

public class LinePlanFragment extends Fragment {
    StatusViewScroller statusViewScroller;
    public LinePlanFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_line_plan, container, false);

        statusViewScroller =root.findViewById(R.id.status_view);
        Button me =root.findViewById(R.id.me);
        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                statusViewScroller.scrollBy(200,0);
                Toast.makeText(getContext(), "scrol", Toast.LENGTH_SHORT).show();
            }
        });
        me.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                statusViewScroller.scrollBy(-100,0);
                Toast.makeText(getContext(), "scrol", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return root;
    }
}

