package com.example.maps_project.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
@Dao
public interface EntityDao {
    @Insert
    void insert(Entity entity);

    @Query("SELECT * FROM CEPHistory WHERE id = :id")
    Entity findData(String id);

    @Query("SELECT COUNT(*) FROM CEPHistory WHERE id = :id")
    int checkDataExist(String id);
}
