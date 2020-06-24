package com.example.driver;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.driver.model.Line;

import me.omidh.liquidradiobutton.LiquidRadioButton;


public class ChoseDerectionDialog extends Dialog {
    SharedPreferences linePref;
    SharedPreferences.Editor lineEditor;
    Context context;
    String a,b;
    Line line;
    int type;
    Button confirm;
    public ChoseDerectionDialog(@NonNull Context context, Line line,int type) {
        super(context);
        this.context=context;
      this.line=line;
      this.type=type;
    }
    LiquidRadioButton ab,ba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chose_derection_dialog);

        linePref =context.getSharedPreferences("linePref", Context.MODE_PRIVATE);
        lineEditor=linePref.edit();
        a=line.getA_b_station().getName();
        b=line.getB_a_station().getName();
        ab=findViewById(R.id.ab);
        ba=findViewById(R.id.ba);
        confirm=findViewById(R.id.confirm);

        ab.setText(a+" -> "+b);
        ba.setText(b+" -> "+a);
        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ba.setBackgroundColor(Color.rgb(230,230,230));
                ab.setBackgroundColor(Color.WHITE);
            }
        });
        ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ab.setBackgroundColor(Color.rgb(230,230,230));
                ba.setBackgroundColor(Color.WHITE);


            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String direction;
                if (ba.isChecked()){
                    direction="b_a";


                }else {
                    direction="a_b";

                }
                lineEditor.putString("direction",direction);
                lineEditor.commit();
                Intent i=new Intent(context, MainActivity.class);

                i.putExtra("Line", line);

                context.startActivity(i);
//                getOwnerActivity().finish();
                dismiss();
            }
        });
//
//        ba.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lineEditor.putString("direction","b_a");
//                lineEditor.commit();
//                context.startActivity(new Intent(context, MainActivity.class));
//                getOwnerActivity().finish();
//                dismiss();
//            }
//        });


    }
}
