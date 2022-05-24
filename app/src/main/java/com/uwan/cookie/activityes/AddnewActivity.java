package com.uwan.cookie.activityes;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.thatapp.cookie_room_example.R;
import com.uwan.cookie.database.AppDatabase;
import com.uwan.cookie.database.Cookie;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AddnewActivity extends AppCompatActivity {
    EditText txtflavour, txtexpirationdate, txtbrand, txtname, txtweight;
    Button addbutton;
    AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);
        txtflavour = findViewById(R.id.txt_flavour_txt_edit);
        txtexpirationdate = findViewById(R.id.txt_exp_txt_edit);
        txtbrand = findViewById(R.id.txt_brand_txt_edit);
        txtname = findViewById(R.id.txt_cookie_name_edit);
        txtweight =findViewById(R.id.edit_txt_weght);
        addbutton =findViewById(R.id.btn_add);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "my_db").build();

        addbutton.setOnClickListener(view -> {
            String flavour,expiration,brand,name,weight;

            flavour= txtflavour.getText().toString();
            expiration= txtexpirationdate.getText().toString();
            brand= txtbrand.getText().toString();
            name= txtname.getText().toString();
            weight= txtweight.getText().toString();
            Cookie cookie1 = new Cookie(name,flavour,expiration,brand,weight);
            boolean x;
            ExecutorService es = Executors.newSingleThreadExecutor();
            Future<Boolean> result = es.submit(() -> {
                db.CookieDao().Insert(cookie1);
                return true;
            });
            try {
                x = result.get();
                if (x) {
                finish();
                setResult(99);
                }
            } catch (Exception ignored) {
            }
            es.shutdown();
        });
    }



}
