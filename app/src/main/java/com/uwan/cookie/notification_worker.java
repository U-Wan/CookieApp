package com.uwan.cookie;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.room.Room;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.thatapp.cookie_room_example.R;
import com.uwan.cookie.activityes.AddnewActivity;
import com.uwan.cookie.database.AppDatabase;
import com.uwan.cookie.database.Cookie;

import java.util.List;
import java.util.Random;

public class notification_worker extends Worker {
    Context context;

    public notification_worker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
/*      //   Intent okay = new Intent(getApplicationContext(), BroadCastRec.class);
         okay.putExtra("k", 0);
          PendingIntent snoozePendingIntent =
               PendingIntent.getBroadcast(getApplicationContext(),
                      //0, okay, PendingIntent.FLAG_IMMUTABLE);*/
      AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "my_db").build();
      List<Cookie>cookies= db.CookieDao().getAllCookies();
        Random rand = new Random();
        int randomNum = rand.nextInt((cookies.size()));
        Cookie cookie =cookies.get(randomNum);
    Intent intent = new Intent(context.getApplicationContext(), AddnewActivity.class);
        intent.putExtra(CookieAdapter.key_txt_cookie_name,cookie.getName());
        intent.putExtra(CookieAdapter.key_fav,cookie.isFav());
        intent.putExtra(CookieAdapter.key_id,cookie.getId());
        intent.putExtra(CookieAdapter.key_txt_brand_txt,cookie.getBrand());
        intent.putExtra(CookieAdapter.key_txt_exp_txt,cookie.getExpdate());
        intent.putExtra(CookieAdapter.key_txt_flavour_txt,cookie.getFavour());
        intent.putExtra(CookieAdapter.key_txt_weight,cookie.getWeight());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "999";
            String description = "without any Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("999", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "999")
                    .setSmallIcon(R.drawable.outline_favorite_black_20)
                    .setContentTitle("Cookie...")
                    .setContentText("Click for random Cookie")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(
                            PendingIntent.getActivity(
                                    context,
                                    0,
                                    intent,
                                    PendingIntent.FLAG_MUTABLE));

            notificationManager.notify(999, builder.build());
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "999")
                    .setSmallIcon(R.drawable.outline_favorite_black_20)
                    .setContentTitle("Cookie...")
                    .setContentText("Click for random Cookie")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(
                            PendingIntent.getActivity(
                                    context,
                                    0,
                                    intent,
                                    PendingIntent.FLAG_ONE_SHOT));;
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(0, builder.build());


        }
        Log.e("Worker","Worker is working");
        return Result.success();

    }

}
