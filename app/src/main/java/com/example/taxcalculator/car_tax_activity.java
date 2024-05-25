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

public class car_tax_activity extends AppCompatActivity {

    private Spinner spinner_regions,spinner_months,spinner_vehicle_types;
    private Button calculate;
    private EditText horsepower;
    private ImageButton back;
    private TextView tv;
    private int months=12;
    private String region="";
    private String vehicle_type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_tax);

        back=findViewById(R.id.back_to_SSA);
        back.setOnClickListener(new View.OnClickListener() {
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
                back.setHovered(true);
                back.startAnimation(left);
            }
        });

        spinner_regions = findViewById(R.id.spinner_regions);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dropdown_regions, R.layout.custom_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner_regions.setAdapter(adapter);

        // Set an item selected listener
        spinner_regions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                region = parent.getItemAtPosition(position).toString();
                //region=Integer.parseInt(selectedItem);
                //Toast.makeText(car_tax_activity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        spinner_months = findViewById(R.id.spinner_months_of_ownership);
        ArrayAdapter<CharSequence> adapter_sm = ArrayAdapter.createFromResource(this,
                R.array.dropdown_months_of_ownership, R.layout.custom_spinner_item);
        adapter_sm.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_months.setAdapter(adapter_sm);
        spinner_months.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                months=Integer.parseInt(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        spinner_vehicle_types = findViewById(R.id.spinner_vehicle_types);
        ArrayAdapter<CharSequence> adapter_vt = ArrayAdapter.createFromResource(this,
                R.array.dropdown_vehicle_types, R.layout.custom_spinner_item);
        adapter_vt.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_vehicle_types.setAdapter(adapter_vt);
        spinner_vehicle_types.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vehicle_type = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });


        horsepower=findViewById(R.id.horsepower_ET);
        calculate=findViewById(R.id.calc_car_tax);
        tv = findViewById(R.id.TV_car_tax);
        ImageButton add = findViewById(R.id.add_to_history);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (horsepower.getText().toString().isEmpty()){
                    tv.setText("");
                    tv.append("No input. Please retry");
                    tv.setVisibility(View.VISIBLE);
                }else{
                    double hp = Double.parseDouble(horsepower.getText().toString());

                    if (region.equals("77-Москва")){
                        region="Moscow";
                    }else if(region.equals("78-Санкт-Петербург")){
                        region="Saint-Petersburg";
                    }

                    double result = TaxCalculation.calculateCarTax(region,hp,months);
                    tv.setText("");
                    String formatted = String.format("%.0f",result);
                    tv.append(formatted+" Р");
                    tv.setVisibility(View.VISIBLE);

                    add.setVisibility(View.VISIBLE);

                    //

                }


            }
        });



    }
}