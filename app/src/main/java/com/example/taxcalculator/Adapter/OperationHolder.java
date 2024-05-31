package com.example.taxcalculator.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxcalculator.R;

public class OperationHolder extends RecyclerView.ViewHolder {
    TextView operation;
    public OperationHolder(@NonNull View itemView) {
        super(itemView);
        operation = itemView.findViewById(R.id.operation);
    }
}
