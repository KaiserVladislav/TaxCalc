package com.example.taxcalculator.LocalData;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "operations")
public class Operation {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "info")
    private String info;

    public int getId() {
        return id;
    }

    public String getInfo() {
        return info;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Operation(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", email='" + info + '\'' +
                '}';
    }
}
