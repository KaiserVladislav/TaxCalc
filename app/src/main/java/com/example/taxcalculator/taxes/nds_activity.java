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

public class nds_activity extends AppCompatActivity {
    Button calculateB;
    ImageButton addIB, backIB;
    Spinner spinner_type_of_goods;
    EditText priceET;
    private TextView tv;
    int type_of_goods;

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

        Locale currentLocale = Locale.getDefault();
        String currentLanguage = currentLocale.getLanguage();
        ArrayAdapter<CharSequence> adapter_type_of_goods;
        if(Home.CURRENT_LANGUAGE.equals("ru")){
             adapter_type_of_goods = ArrayAdapter.createFromResource(this,
                    R.array.types_of_goods_rus, R.layout.custom_spinner_item);
        }else{
             adapter_type_of_goods = ArrayAdapter.createFromResource(this,
                    R.array.types_of_goods_eng, R.layout.custom_spinner_item);

        }


        adapter_type_of_goods.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_type_of_goods.setAdapter(adapter_type_of_goods);
        spinner_type_of_goods.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type_of_goods_interpretation = parent.getItemAtPosition(position).toString();
                type_of_goods = Integer.parseInt(type_of_goods_interpretation.split("\\.")[0]);
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
                    String message;
                    if(Home.CURRENT_LANGUAGE.equals("eng"))
                        message="No input. Please retry";
                    else
                        message="Ошибка ввода. Попробуйте еще раз";

                    tv.setText("");
                    tv.append(message);
                    tv.setVisibility(View.VISIBLE);
                }else{

                    double price = Double.parseDouble(priceET.getText().toString());
                    double result = TaxCalculation.calculateNDS(price,type_of_goods);

                    tv.setText("");
                    String formatted = String.format("%.0f",result);
                    tv.append(formatted+" Р");
                    tv.setVisibility(View.VISIBLE);

                    addIB.setVisibility(View.VISIBLE);
                    addIB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String info="";
                            if (Home.CURRENT_LANGUAGE.equals("ru"))
                                info = "НДС: \n " + String.format("%.0f",result)+" Р\n"+TaxCalculation.getTime();
                            else
                                info = "NDS:  \n " + String.format("%.0f",result)+" Р\n"+TaxCalculation.getTime();
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