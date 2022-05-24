package com.uwan.cookie.activityes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;

import com.thatapp.cookie_room_example.R;
import com.uwan.cookie.CookieAdapter;
import com.uwan.cookie.database.AppDatabase;
import com.uwan.cookie.database.Cookie;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SecondActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CookieAdapter cookieAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        recyclerView = findViewById(R.id.recycler);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        getData(Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "my_db").build());
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData(Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "my_db").build());
    }

    private void getData(AppDatabase appDatabase) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            //Background work here
            List<Cookie> cookies = appDatabase.CookieDao().getAllCookies();
            SharedPreferences sharedPreferences = getSharedPreferences("fav",MODE_PRIVATE);
            Map<String, ?> favorites= sharedPreferences.getAll();
            cookieAdapter = new CookieAdapter(cookies,getApplicationContext(),favorites);
            handler.post(() -> {
                //UI Thread work here
                recyclerView.setAdapter(cookieAdapter);
                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();

            });
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.second_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==99){

            getData(Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "my_db").build());

        }
    }
    public void add_new(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), AddnewActivity.class);
        startActivityForResult(intent,99);
    }
}
