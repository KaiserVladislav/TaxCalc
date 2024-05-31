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
import android.widget.Toast;

import com.example.taxcalculator.Home;
import com.example.taxcalculator.LocalData.Operation;
import com.example.taxcalculator.LocalData.OperationRepository;
import com.example.taxcalculator.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Locale;

public class ndfl_activity extends AppCompatActivity {
    Button calculateB;
    ImageButton addIB, backIB;
    Spinner spinner_residency;
    EditText incomeET;
    private TextView tv;

    String residency="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndfl);

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


        spinner_residency=findViewById(R.id.spinner_residency_NDFL);

        Locale currentLocale = Locale.getDefault();
        String currentLanguage = currentLocale.getLanguage();

        ArrayAdapter<CharSequence> adapter_residency;

        if(currentLanguage.equals("ru")){
            adapter_residency = ArrayAdapter.createFromResource(this,
                    R.array.residency_types_rus, R.layout.custom_spinner_item);
        }else{
            adapter_residency = ArrayAdapter.createFromResource(this,
                    R.array.residency_types_eng, R.layout.custom_spinner_item);
        }



        adapter_residency.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_residency.setAdapter(adapter_residency);
        spinner_residency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                residency = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        calculateB=findViewById(R.id.calc_NDFL);
        tv = findViewById(R.id.TV_ndfl_tax);
        addIB=findViewById(R.id.add_to_history_NDFL);
        calculateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomeET = findViewById(R.id.income_ET);
                if(incomeET.getText().toString().isEmpty()){
                    String message="No input. Please retry";
                    tv.setText("");
                    tv.append(message);
                    tv.setVisibility(View.VISIBLE);
                }else{
                    double income = Double.parseDouble(incomeET.getText().toString().toString());
                    double result = TaxCalculation.calculateNDFL(income,residency);


                    tv.setText("");
                    String formatted = String.format("%.0f",result);
                    tv.append(formatted+" Р");
                    tv.setVisibility(View.VISIBLE);

                    addIB.setVisibility(View.VISIBLE);
                    addIB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String info;
                            if(currentLanguage.equals("ru"))
                                info = "НДФЛ:\n " + String.format("%.0f",result)+" Р\n"+TaxCalculation.getTime();
                            else
                                info="NDFL:\n " + String.format("%.0f",result)+" Р\n"+TaxCalculation.getTime();

                            Operation op = new Operation(info);

                            ExecutorService executorService = Executors.newSingleThreadExecutor();
                            OperationRepository operationRepository = new OperationRepository(getApplicationContext());
                            executorService.execute(()->{
                                operationRepository.insertOperation(op);
                            });
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