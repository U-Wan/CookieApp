package com.uwan.cookie.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities ={Cookie.class},version=1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CookieDAO CookieDao();

}
