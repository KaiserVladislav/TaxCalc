package com.example.taxcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class nds_activity extends AppCompatActivity {
    Button calculateB;
    ImageButton addIB, backIB;
    Spinner spinner_type_of_goods;
    EditText priceET;
    private TextView tv;
    String type_of_goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nds);

        backIB=findViewById(R.id.back_to_SSA);
        backIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation left = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left);
                left.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                });
                backIB.setHovered(true);
                backIB.startAnimation(left);
            }
        });

        addIB=findViewById(R.id.add_to_history_NDS);
        tv=findViewById(R.id.TV_NDS_tax);
        priceET=findViewById(R.id.price_of_goods_ET);
        calculateB=findViewById(R.id.calc_NDS);

        spinner_type_of_goods=findViewById(R.id.spinner_type_of_goods);
        ArrayAdapter<CharSequence> adapter_type_of_goods = ArrayAdapter.createFromResource(this,
                R.array.types_of_goods, R.layout.custom_spinner_item);
        adapter_type_of_goods.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_type_of_goods.setAdapter(adapter_type_of_goods);
        spinner_type_of_goods.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type_of_goods = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        calculateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (priceET.getText().toString().isEmpty()){
                    String message="No input. Please retry";
                    tv.setText("");
                    tv.append(message);
                    tv.setVisibility(View.VISIBLE);
                }else{
                    if(type_of_goods.equals("Другое"))
                        type_of_goods="Other";
                    else if(type_of_goods.equals("Товары для экспорта"))
                        type_of_goods="Export";
                    else
                        type_of_goods="FMC"; // stands for Food Medicine Children lmao

                    double price = Double.parseDouble(priceET.getText().toString());
                    double result = TaxCalculation.calculateNDS(price,type_of_goods);

                    tv.setText("");
                    String formatted = String.format("%.0f",result);
                    tv.append(formatted+" Р");
                    tv.setVisibility(View.VISIBLE);

                    addIB.setVisibility(View.VISIBLE);
                    //igor

                }
            }
        });

    }
}