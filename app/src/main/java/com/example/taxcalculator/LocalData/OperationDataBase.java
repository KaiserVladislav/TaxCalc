package com.example.taxcalculator.LocalData;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Operation.class}, version = 1)
public abstract class OperationDataBase extends RoomDatabase {
    public abstract OperationDAO operationDAO();
}
