package com.uwan.cookie.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CookieDAO {
    @Insert
    void Insert(Cookie... cookies);
    @Delete
    void Delete(Cookie cookie);
    @Query("SELECT * FROM Cookie")
    List<Cookie> getAllCookies();

}
