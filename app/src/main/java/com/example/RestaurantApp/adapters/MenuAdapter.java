package com.example.RestaurantApp.adapters;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.RestaurantApp.FoodAddActivity;
import com.example.RestaurantApp.FoodEditActivity;
import com.example.RestaurantApp.R;
import com.example.RestaurantApp.models.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends ArrayAdapter<Menu> {

    public MenuAdapter(@NonNull Context context, ArrayList<Menu> menuList) {
        super(context,0, (List) menuList);
    }

    public MenuAdapter(FoodAddActivity context, ArrayList<Menu> menuList) {
        super(context,0,menuList);
    }

    public MenuAdapter(FoodEditActivity context, ArrayList<Menu> menuList) {
        super(context,0,menuList);
    }


    @NonNull
    @Override
    public View getView (int position, View converView, ViewGroup parent){
        return  initView(position, converView,parent);
    }
    @Override
    public  View getDropDownView(int position, View converView, ViewGroup parent){
        return initView(position, converView,parent);
    }

    private  View initView(int position, View converView, ViewGroup parent ){
        if (converView==null){
            converView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_menu, parent,false
            );
        }
        ImageButton imageButton = converView.findViewById(R.id.imMenu);
        TextView textViewMenu = converView.findViewById(R.id.tvMenu);

        Menu menu = getItem(position);

        if (menu!=null){
            textViewMenu.setText(menu.getMenuName());
            byte[] picture = menu.getMenuPic();
            Bitmap bitmap = BitmapFactory.decodeByteArray(picture,0, picture.length);
            imageButton.setImageBitmap(bitmap);
        }

        return converView;
    }
}

