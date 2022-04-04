package com.uwan.cookie.activityes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.thatapp.cookie_room_example.R;

import com.uwan.cookie.database.AppDatabase;
import com.uwan.cookie.database.Cookie;
import com.uwan.cookie.notification_worker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    AppDatabase db;
    TextView txtflavour, txtexpirationdate, txtbrand, txtname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtflavour = findViewById(R.id.txt_flavour_txt);
        txtbrand = findViewById(R.id.txt_brand_txt);
        txtname = findViewById(R.id.txt_cookie_name);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "my_db").build();
        isDBPopulated();

    }

    public void recent() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Background work here
                List<Cookie> cookies = db.CookieDao().getAllCookies();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI Thread work here
                        Cookie cookie = cookies.get(cookies.size() - 1);
                        txtflavour.setText(cookie.getFavour());
                               // txtexpirationdate.setText(cookie.getExpdate());
                                txtbrand.setText(cookie.getBrand());
                                txtname.setText(cookie.getName());
                        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(notification_worker.class,15, TimeUnit.NANOSECONDS)
                                .build();

                      WorkManager workManager=  WorkManager.getInstance(getApplicationContext());
                        workManager.pruneWork();
                        workManager.enqueue(periodicWorkRequest);


                    }
                });
            }
        });
    }
    public void isDBPopulated() {
        boolean x;
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<Boolean> result = es.submit(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                if (db.CookieDao().getAllCookies().size() > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        try {
            x = result.get();
            if (!x) {
                populate_db();
            } else {
                recent();
            }
        } catch (Exception e) {
            // failed
        }
        es.shutdown();
    }

    public void populate_db() {
        InputStream is = getResources().openRawResource(R.raw.cookiedata);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            int n;
            while (true) {
                try {
                    if ((n = reader.read(buffer)) == -1) break;
                    writer.write(buffer, 0, n);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String jsonString = writer.toString();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray cookie_array = jsonObject.getJSONArray("cookies");
            String name, Favour, expdate, brand, imglink;
            int weight;
            for (int i = 0; i < cookie_array.length(); i++) {
                name = cookie_array.getJSONObject(i).getString("name");
                Favour = cookie_array.getJSONObject(i).getString("flavour");
                expdate = cookie_array.getJSONObject(i).getString("expirationdate");
                brand = cookie_array.getJSONObject(i).getString("brand");
                weight = cookie_array.getJSONObject(i).getInt("weight");
               // imglink = cookie_array.getJSONObject(i).getString("imglink");
                String finalName = name;
                String finalFavour = Favour;
                String finalExpdate = expdate;
                String finalBrand = brand;
                //String finalimgLink=imglink;
                int finalWeight = weight;


                boolean x;
                ExecutorService es = Executors.newSingleThreadExecutor();
                Future<Boolean> result = es.submit(new Callable<Boolean>() {
                    public Boolean call() throws Exception {
                        db.CookieDao().Insert(new Cookie(finalName, finalFavour, finalExpdate, finalBrand, String.valueOf(finalWeight)));
                        return true;
                    }
                });
                try {
                    x = result.get();
                    if (x) {
                        recent();                    }
                } catch (Exception e) {
                    // failed
                }
                es.shutdown();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void Show_All(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        startActivity(intent);
    }
}