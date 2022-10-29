package com.example.rodentshelper.ROOM;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {RodentModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DAO userDao();
}

