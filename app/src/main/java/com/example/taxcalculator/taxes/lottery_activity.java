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

public class lottery_activity extends AppCompatActivity {

    private Spinner spinner_residency, spinner_lottery_type;
    private Button calculateB;
    private ImageButton backIB, addIB;
    private EditText prize_ET;
    private TextView lottery_tax_TV;
    private int residency=1;
    private int lottery_type=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);


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

        prize_ET = findViewById(R.id.prize_ET);
        calculateB=findViewById(R.id.calc_lottery_tax);
        lottery_tax_TV=findViewById(R.id.TV_lottery_tax);
        addIB=findViewById(R.id.add_to_history_LOTTERY);


        spinner_residency=findViewById(R.id.spinner_residency_Lottery);
        spinner_lottery_type=findViewById(R.id.spinner_lottery_type);

        ArrayAdapter<CharSequence> adapter_residency;
        Locale currentLocale = Locale.getDefault();
        String currentLanguage = currentLocale.getLanguage();
        if(Home.CURRENT_LANGUAGE.equals("ru")){
            adapter_residency= ArrayAdapter.createFromResource(this,
                    R.array.residency_types_rus, R.layout.custom_spinner_item);

        }else{

            adapter_residency= ArrayAdapter.createFromResource(this,
                    R.array.residency_types_eng, R.layout.custom_spinner_item);
        }

        adapter_residency.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_residency.setAdapter(adapter_residency);
        spinner_residency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String residency_interpretation =  parent.getItemAtPosition(position).toString();
                residency = Integer.parseInt(residency_interpretation.split("\\.")[0]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });


        ArrayAdapter<CharSequence> adapter_lottery_type;
        if(Home.CURRENT_LANGUAGE.equals("ru")){
            adapter_lottery_type = ArrayAdapter.createFromResource(this,
                    R.array.lottery_types_rus, R.layout.custom_spinner_item);
        }else{
            adapter_lottery_type = ArrayAdapter.createFromResource(this,
                    R.array.lottery_types_eng, R.layout.custom_spinner_item);
        }

        adapter_lottery_type.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_lottery_type.setAdapter(adapter_lottery_type);
        spinner_lottery_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String lottery_type_interpretation = parent.getItemAtPosition(position).toString();
                lottery_type=Integer.parseInt(lottery_type_interpretation.split("\\.")[0]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });


        calculateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (prize_ET.getText().toString().isEmpty() ){
                    String message;
                    if(Home.CURRENT_LANGUAGE.equals("eng"))
                        message="No input. Please retry";
                    else
                        message="Ошибка ввода. Попробуйте еще раз";

                    lottery_tax_TV.setText("");
                    lottery_tax_TV.append(message);
                    lottery_tax_TV.setVisibility(View.VISIBLE);
                }else{


                    double prize=Double.parseDouble(prize_ET.getText().toString());
                    double result = TaxCalculation.calculateLotteryTax(prize, lottery_type,residency);

                    lottery_tax_TV.setText("");
                    String formatted = String.format("%.0f",result);
                    lottery_tax_TV.append(formatted+" Р");
                    lottery_tax_TV.setVisibility(View.VISIBLE);

                    addIB.setVisibility(View.VISIBLE);
                    addIB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String info;
                            if (Home.CURRENT_LANGUAGE.equals("ru"))
                                info = "Налог на лотерейный выигрыш:\n " + String.format("%.0f",result)+" Р\n"+TaxCalculation.getTime();
                            else
                                info="Lottery gains tax:\n " + String.format("%.0f",result)+" Р\n"+TaxCalculation.getTime();
                            Operation op = new Operation(info);

                            ExecutorService executorService = Executors.newSingleThreadExecutor();
                            OperationRepository operationRepository = new OperationRepository(getApplicationContext());
                            executorService.execute(()->{
                                operationRepository.insertOperation(op);
                            });
                            if (Home.CURRENT_LANGUAGE.equals("ru")){
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