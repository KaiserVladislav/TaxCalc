package com.example.taxcalculator;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
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