package com.example.maps_project.data.room;

import androidx.room.Database;

@Database(entities = Entity.class, version = 1, exportSchema = false)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {
    public abstract EntityDao entityDao();
}
