package com.example.taxcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxcalculator.Adapter.OperationAdapter;
import com.example.taxcalculator.LocalData.Operation;
import com.example.taxcalculator.LocalData.OperationRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HistoryActivity extends AppCompatActivity {

    Handler handler;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView =  findViewById(R.id.OperationList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadOperations();
        ImageButton backIB=findViewById(R.id.back_to_SSA);
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



    }

    private void loadOperations() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        OperationRepository operationRepository = new OperationRepository(getApplicationContext());
        executorService.execute(()->{
            List<Operation> operations = operationRepository.getOperations();
            Log.d("DEBUG", "loadOperations: " + operations.toString());
            OperationAdapter operationAdapter = new OperationAdapter(operations);
            recyclerView.setAdapter(operationAdapter);
        });
    }
}