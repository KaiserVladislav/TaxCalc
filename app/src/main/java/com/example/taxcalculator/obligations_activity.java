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

public class obligations_activity extends AppCompatActivity {

    private Spinner spinner;
    private Button calculateB;
    private ImageButton backIB, addIB;
    private EditText purchase_priceET, sale_priceET, amount_of_sharesET;
    private TextView obligation_taxTV;
    private String residency="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obligations);


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

        purchase_priceET = findViewById(R.id.purchase_price_ET);
        sale_priceET = findViewById(R.id.sale_price_ET);
        amount_of_sharesET = findViewById(R.id.amount_of_shares_ET);

        calculateB=findViewById(R.id.calc_obligation_tax);
        obligation_taxTV=findViewById(R.id.TV_obligation_tax);
        addIB=findViewById(R.id.add_to_history_OBLIGATION);

        spinner = findViewById(R.id.spinner_residency_OblTax);
        ArrayAdapter<CharSequence> adapter_vt = ArrayAdapter.createFromResource(this,
                R.array.residency_types, R.layout.custom_spinner_item);
        adapter_vt.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner.setAdapter(adapter_vt);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                residency = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });



        calculateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (purchase_priceET.getText().toString().isEmpty() ||
                        purchase_priceET.getText().toString().isEmpty() ||
                        purchase_priceET.getText().toString().isEmpty() ){
                    String message="No input. Please retry";
                    obligation_taxTV.setText("");
                    obligation_taxTV.append(message);
                    obligation_taxTV.setVisibility(View.VISIBLE);

                }else{
                    if(residency.equals("Налоговый нерезидент"))
                        residency="Alien";
                    else {
                        residency="Russian";
                    }

                    double purchase_price=Double.parseDouble(purchase_priceET.getText().toString());
                    double sale_price=Double.parseDouble(sale_priceET.getText().toString());
                    int amount_of_shares=Integer.parseInt(amount_of_sharesET.getText().toString());

                    double result = TaxCalculation.calculateObligationsTax(purchase_price, sale_price, amount_of_shares,residency);

                    obligation_taxTV.setText("");
                    String formatted = String.format("%.0f",result);
                    obligation_taxTV.append(formatted+" Р");
                    obligation_taxTV.setVisibility(View.VISIBLE);

                    addIB.setVisibility(View.VISIBLE);

                }
            }
        });



    }
}