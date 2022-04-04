package com.uwan.cookie.activityes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uwan.cookie.CookieAdapter;


import com.thatapp.cookie_room_example.R;


public class DetailsActivity extends AppCompatActivity {
    TextView txtFlavour, txtExpirationDate, txtBrand, txtName, txtWeight,imglink1;
    ImageView imglogo;
    //String imglink;
    //Context context = imglogo.getContext();

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
        txtFlavour =findViewById(R.id.txt_flavour_txt_details);
        txtExpirationDate =findViewById(R.id.txt_exp_txt_details);
        txtBrand =findViewById(R.id.txt_brand_txt_details);
        txtName =findViewById(R.id.txt_cookie_name_details);

        imglogo=findViewById(R.id.imageViewlogo);

        txtWeight =findViewById(R.id.txt_weght_details);
        //imglink1=findViewById(R.id.txtimglink);
        //button=findViewById(R.id.btn_ok);
        detail_img_fav=findViewById(R.id.detail_img_fav);

        Bundle bundle = getIntent().getExtras();
        txtFlavour.setText(bundle.getString(CookieAdapter.ktxtFlavour));
        txtExpirationDate.setText(bundle.getString(CookieAdapter.kTxtexpirationdate));
        txtBrand.setText(bundle.getString(CookieAdapter.kTxtBrand));
        txtName.setText(bundle.getString(CookieAdapter.kTxtName));
        txtWeight.setText(bundle.getString(CookieAdapter.kTxtWeight));
        imglink1.setText(bundle.getString(CookieAdapter.kimglink));
        //imglink=bundle.getString(CookieAdapter.kimglink);


      /*  Glide.with(context)
                .load("http://via.placeholder.com/300.png")
                .into(imglogo);*/

        id=bundle.getInt(CookieAdapter.key_id);
        sharedPreferences = getSharedPreferences("fav", MODE_PRIVATE);

        is_fav=  bundle.getBoolean(CookieAdapter.key_fav,false);
        if (is_fav){
            detail_img_fav.setImageResource(R.drawable.outline_favourite_black_20);
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
                    detail_img_fav.setImageResource(R.drawable.outline_favourite_black_20);
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