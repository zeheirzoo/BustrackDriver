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

import com.vinay.stepview.HorizontalStepView;
import com.vinay.stepview.models.Step;

import java.util.ArrayList;
import java.util.List;

public class LinePlanFragment extends Fragment {

    public LinePlanFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_line_plan, container, false);
        HorizontalStepView horizontalStepView = root.findViewById(R.id.horizontal_step_view);

        List<Step> stepList = new ArrayList<>();
        stepList.add(new Step("Lorem", Step.State.COMPLETED));
        stepList.add(new Step("Ipsum", Step.State.COMPLETED));
        stepList.add(new Step("Ipsum", Step.State.COMPLETED));
        stepList.add(new Step("Ipsum", Step.State.COMPLETED));
        stepList.add(new Step("Ipsum", Step.State.COMPLETED));
        stepList.add(new Step("Ipsum", Step.State.COMPLETED));
        stepList.add(new Step("Ipsum", Step.State.COMPLETED));
        stepList.add(new Step("Ipsum", Step.State.COMPLETED));
        stepList.add(new Step("Ipsum", Step.State.COMPLETED));
        stepList.add(new Step("Ipsum", Step.State.COMPLETED));
        stepList.add(new Step("Dolor", Step.State.CURRENT));
        stepList.add(new Step("Sit")); // State defaults to NOT_COMPLETED
        stepList.add(new Step("Amet")); // State defaults to NOT_COMPLETED
        horizontalStepView // Also applies to VerticalStepView
                // Drawables
                .setCompletedStepIcon(AppCompatResources.getDrawable(getContext(), R.drawable.ic_code_scanner_auto_focus_on))
                .setNotCompletedStepIcon(AppCompatResources.getDrawable(getContext(), R.drawable.ic_home))
                .setCurrentStepIcon(AppCompatResources.getDrawable(getContext(), R.drawable.ic_arrow_back_black_24dp))
                // Text colors
                .setCompletedStepTextColor(Color.DKGRAY) // Default: Color.WHITE
                .setNotCompletedStepTextColor(Color.DKGRAY) // Default: Color.WHITE
                .setCurrentStepTextColor(Color.BLACK) // Default: Color.WHITE
                // Line colors
                .setCompletedLineColor(Color.parseColor("#ea655c")) // Default: Color.WHITE
                .setNotCompletedLineColor(Color.parseColor("#eaac5c")) // Default: Color.WHITE
                // Text size (in sp)
                .setTextSize(15) // Default: 14sp
                // Drawable radius (in dp)
                .setCircleRadius(15) // Default: ~11.2dp
                // Length of lines separating steps (in dp)
                .setLineLength(50); // Default: ~34dp


        horizontalStepView.setSteps(stepList);
        return root;
    }
}

