package com.example.maps_project.data.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
@androidx.room.Entity(tableName = "CEPHistory")
public class Entity {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String id;

    @ColumnInfo(name = "data")
    private String data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
