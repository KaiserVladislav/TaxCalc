package com.example.taxcalculator.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxcalculator.LocalData.Operation;
import com.example.taxcalculator.R;

import java.util.List;

public class OperationAdapter extends RecyclerView.Adapter<OperationHolder> {

    private List<Operation> operationList;

    public OperationAdapter(List<Operation> operationList) {
        this.operationList = operationList;
    }

    @NonNull
    @Override
    public OperationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_operation_item, parent, false);
        return new OperationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OperationHolder holder, int position) {
        Operation op= operationList.get(position);
        holder.operation.setText(op.getInfo());
    }

    @Override
    public int getItemCount() {
        return operationList.size();
    }
}
