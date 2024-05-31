package com.example.taxcalculator.taxes;

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
import android.util.Log;
import android.widget.Toast;

import com.example.taxcalculator.Home;
import com.example.taxcalculator.LocalData.Operation;
import com.example.taxcalculator.LocalData.OperationRepository;
import com.example.taxcalculator.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Locale;

public class car_tax_activity extends AppCompatActivity {

    private Spinner spinner_regions,spinner_months,spinner_vehicle_types,spinner_release_dates;
    private Button calculate;
    private EditText horsepower;
    private ImageButton back;
    private TextView tv;
    private int months=12;
    private int region=77;
    private int vehicle_type=1;
    int release_date=2024;

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

        ArrayAdapter<CharSequence> adapter_regions; // REGIONS
        Locale currentLocale = Locale.getDefault();
        String currentLanguage = currentLocale.getLanguage();
        if(currentLanguage.equals("ru")) {
            adapter_regions = ArrayAdapter.createFromResource(this,
                    R.array.dropdown_regions_rus, R.layout.custom_spinner_item);
        }else{
            adapter_regions = ArrayAdapter.createFromResource(this,
                    R.array.dropdown_regions_eng, R.layout.custom_spinner_item);
        }

        adapter_regions.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_regions.setAdapter(adapter_regions);
        spinner_regions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String regions_interpretation = parent.getItemAtPosition(position).toString();
                region=Integer.parseInt(regions_interpretation.split("-")[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_months = findViewById(R.id.spinner_months_of_ownership); // MONTHS OF OWNERSHIP
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

        spinner_vehicle_types = findViewById(R.id.spinner_vehicle_types); // VEHICLE TYPES
        ArrayAdapter<CharSequence> adapter_vt;
        if(currentLanguage.equals("ru")){
            adapter_vt = ArrayAdapter.createFromResource(this,
                    R.array.dropdown_vehicle_types_rus, R.layout.custom_spinner_item);
        }else{
            adapter_vt = ArrayAdapter.createFromResource(this,
                    R.array.dropdown_vehicle_types_eng, R.layout.custom_spinner_item);
        }

        adapter_vt.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_vehicle_types.setAdapter(adapter_vt);
        spinner_vehicle_types.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String vehicle_type_interpretation = parent.getItemAtPosition(position).toString();
                vehicle_type=Integer.parseInt(vehicle_type_interpretation.split("\\.")[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });


        spinner_release_dates = findViewById(R.id.release_date_spinner); // RELEASE DATE
        ArrayAdapter<CharSequence> adapter_rd= ArrayAdapter.createFromResource(this,
                R.array.dropdown_release_date, R.layout.custom_spinner_item);;

        adapter_rd.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_release_dates.setAdapter(adapter_rd);
        spinner_release_dates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String release_date_interpretation = parent.getItemAtPosition(position).toString();
                try{
                    release_date=Integer.parseInt(release_date_interpretation);
                }catch (NumberFormatException e){
                    release_date=2000;
                }
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
                    double result = TaxCalculation.calculateCarTax(region,vehicle_type,hp, months, release_date);
                    tv.setText("");
                    String formatted = String.format("%.0f",result);
                    tv.append(formatted+" Р");
                    tv.setVisibility(View.VISIBLE);
                    add.setVisibility(View.VISIBLE);


                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String info;
                            if(currentLanguage.equals("ru"))
                                info = "Транспортный налог:\n" + String.format("%.0f",result)+" Р\n"+TaxCalculation.getTime();
                            else
                                info = "Transport tax:\n " + String.format("%.0f",result)+" Р\n"+TaxCalculation.getTime();
                            Operation op = new Operation(info);

                            ExecutorService executorService = Executors.newSingleThreadExecutor();
                            OperationRepository operationRepository = new OperationRepository(getApplicationContext());
                            executorService.execute(()->{
                                operationRepository.insertOperation(op);
                            });

                            Log.d("History", "CarTax added");

                            if (currentLanguage.equals("ru")){
                                Toast.makeText(getApplicationContext(),"Расчет добавлен в историю",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"Calculation was added to history",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }


            }
        });





    }
}