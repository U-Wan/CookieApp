package com.uwan.cookie.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Cookie {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String  Favour;
    private String   expdate;
    private String   brand;
    private String   weight;


    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    @Ignore
    private boolean isFav;


    public Cookie(String name, String Favour, String expdate, String brand, String weight) {
        this.name = name;
        this.Favour = Favour;
        this.expdate = expdate;
        this.brand = brand;
        this.weight = weight;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFavour() {
        return Favour;
    }

    public String getExpdate() {
        return expdate;
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }



}
