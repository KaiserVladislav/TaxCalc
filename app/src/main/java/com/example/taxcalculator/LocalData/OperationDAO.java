package com.example.taxcalculator.LocalData;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OperationDAO {
    @Query("SELECT * FROM operations")
    public List<Operation> getOperations();

    @Insert
    public void insertOperation(Operation operation);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public void updateOperation(Operation operation);
}
