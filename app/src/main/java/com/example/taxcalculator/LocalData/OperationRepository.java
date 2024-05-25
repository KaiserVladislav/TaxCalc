package com.example.taxcalculator.LocalData;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

public class OperationRepository {
    OperationDAO operationDAO;

    public OperationRepository(Context context){
        OperationDataBase operationDataBase = Room.databaseBuilder(context,
                OperationDataBase.class, "LocalDB").build();
        operationDAO = operationDataBase.operationDAO();
    }

    public List<Operation> getOperations() {
        return operationDAO.getOperations();
    }

    public void insertOperation(Operation op){
        operationDAO.insertOperation(op);
    }
}
