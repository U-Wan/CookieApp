package com.uwan.cookie;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thatapp.cookie_room_example.R;
import com.uwan.cookie.activityes.AddnewActivity;
import com.uwan.cookie.database.Cookie;

import java.util.List;
import java.util.Map;

public class CookieAdapter extends RecyclerView.Adapter<CookieAdapter.CustomViewHolder> {
    List<Cookie> cookies;
    Context context;
    Map<String, ?> favorites;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;
   public static String key_txt_flavour_txt="key_txt_flavour_txt";
    public static  String  key_txt_exp_txt="key_txt_exp_txt";
    public static  String  key_txt_brand_txt="key_txt_brand_txt";
    public static  String  key_txt_cookie_name="key_txt_cookie_name";
    public static  String key_txt_weight="key_txt_weight";
    public static  String key_fav="key_fav";
    public static String key_id="key_id";


    public CookieAdapter(List<Cookie> cookies, Context context, Map<String, ?> favorites) {
        this.cookies = cookies;
        this.context = context;
        this.favorites = favorites;
        sharedPreferences = context.getSharedPreferences("fav", MODE_PRIVATE);
        myEdit = sharedPreferences.edit();


    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.brand.setText(cookies.get(holder.getAdapterPosition()).getBrand());
        holder.name.setText(cookies.get(holder.getAdapterPosition()).getName());
        holder.flavour.setText(cookies.get(holder.getAdapterPosition()).getFavour());
        int Cookie_id = cookies.get(holder.getAdapterPosition()).getId();
        if (favorites.containsKey(String.valueOf(Cookie_id))) {
            holder.Img_view.setImageResource(R.drawable.outline_favorite_black_20);
            cookies.get(holder.getAdapterPosition()).setFav(true);
        } else {
            cookies.get(holder.getAdapterPosition()).setFav(false);

        }

    }

    @Override
    public int getItemCount() {
        return cookies.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView name, brand, flavour;
        ImageView Img_view;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_name_det);
            brand = itemView.findViewById(R.id.txt_brand_det);
            flavour = itemView.findViewById(R.id.txt_flav_det);
            Img_view = itemView.findViewById(R.id.img_fav);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Cookie cookie = cookies.get(getAdapterPosition());
                    String txt_flavour_txt, txt_exp_txt, txt_brand_txt, txt_cookie_name, txt_weight;
                    txt_flavour_txt = cookie.getFavour();
                    txt_exp_txt = cookie.getExpdate();
                    txt_brand_txt = cookie.getBrand();
                    txt_cookie_name = cookie.getName();
                    txt_weight = cookie.getWeight();
                    Intent intent = new Intent(context, AddnewActivity.class);
                    intent.putExtra(key_txt_cookie_name,txt_cookie_name);
                    intent.putExtra(key_txt_weight,txt_weight);
                    intent.putExtra(key_txt_brand_txt,txt_brand_txt);
                    intent.putExtra(key_txt_flavour_txt,txt_flavour_txt);
                    intent.putExtra(key_txt_exp_txt,txt_exp_txt);
                    intent.putExtra(key_fav,cookie.isFav());
                    intent.putExtra(key_id,cookie.getId());
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
            Img_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cookies.get(getAdapterPosition()).isFav()) {
                        Img_view.setImageResource(R.drawable.outline_favorite_border_black_20);
                        cookies.get(getAdapterPosition()).setFav(false);
                        myEdit.remove(String.valueOf(cookies.get(getAdapterPosition()).getId()));
                        myEdit.commit();
                    } else {
                        Img_view.setImageResource(R.drawable.outline_favorite_black_20);
                        cookies.get(getAdapterPosition()).setFav(true);
                        myEdit.putString(String.valueOf(cookies.get(getAdapterPosition()).getId()), "fav");
                        myEdit.commit();
                    }

                }
            });

        }
    }
}
