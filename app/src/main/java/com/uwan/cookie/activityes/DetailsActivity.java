package com.uwan.cookie.activityes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;

import com.uwan.cookie.CookieAdapter;


import com.thatapp.cookie_room_example.R;


public class DetailsActivity extends AppCompatActivity {
    TextView txt_flavour_txt, txt_exp_txt, txt_brand_txt, txt_cookie_name, txt_weight;
    Button button;
    boolean is_fav=false;
    ImageView detail_img_fav;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        txt_flavour_txt =findViewById(R.id.txt_flavour_txt_details);
        txt_exp_txt =findViewById(R.id.txt_exp_txt_details);
        txt_brand_txt =findViewById(R.id.txt_brand_txt_details);
        txt_cookie_name =findViewById(R.id.txt_cookie_name_details);
        txt_weight=findViewById(R.id.txt_weght_details);
        button=findViewById(R.id.btn_ok);
        detail_img_fav=findViewById(R.id.detail_img_fav);
        Bundle bundle = getIntent().getExtras();
        txt_flavour_txt.setText(bundle.getString(CookieAdapter.key_txt_flavour_txt));
        txt_exp_txt.setText(bundle.getString(CookieAdapter.key_txt_exp_txt));
        txt_brand_txt.setText(bundle.getString(CookieAdapter.key_txt_brand_txt));
        txt_cookie_name.setText(bundle.getString(CookieAdapter.key_txt_cookie_name));
        txt_weight.setText(bundle.getString(CookieAdapter.key_txt_weight));
        id=bundle.getInt(CookieAdapter.key_id);
        sharedPreferences = getSharedPreferences("fav", MODE_PRIVATE);

        is_fav=  bundle.getBoolean(CookieAdapter.key_fav,false);
        if (is_fav){
            detail_img_fav.setImageResource(R.drawable.outline_favorite_black_20);
        }else{
            detail_img_fav.setImageResource(R.drawable.outline_favorite_border_black_20);
        }
        detail_img_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_fav){
                    detail_img_fav.setImageResource(R.drawable.outline_favorite_border_black_20);
                    is_fav=false;
                    myEdit = sharedPreferences.edit();
                    myEdit.remove(String.valueOf(id));
                    myEdit.commit();
                }else{
                    detail_img_fav.setImageResource(R.drawable.outline_favorite_black_20);
                    is_fav=true;
                    myEdit = sharedPreferences.edit();
                    myEdit.putString(String.valueOf(id), "fav");
                    myEdit.commit();
                }

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }
}