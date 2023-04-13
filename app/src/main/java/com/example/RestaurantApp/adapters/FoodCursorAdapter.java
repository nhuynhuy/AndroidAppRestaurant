package com.example.RestaurantApp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.RestaurantApp.food.FoodDetailsActivity;
import com.example.RestaurantApp.food.ImageConverter;
import com.example.RestaurantApp.R;

public class FoodCursorAdapter extends CursorAdapter {

    Activity activity;
    ImageButton imMenu;
    ImageButton thumbnail;


    public FoodCursorAdapter(Activity activity, Cursor c) {
        super(activity, c);
        this.activity = activity;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_food, null);
        return view;
    }
    private class ViewHolder {
        TextView title, disc, price;
        ImageView imgPicture;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        thumbnail = view.findViewById(R.id.food_thumbnail);
        TextView title = view.findViewById(R.id.food_title);
        imMenu=view.findViewById(R.id.imMenu);
        //get text spinner


        //
        //Spinner spinMenu = view.findViewById(R.id.food_spinner);
        TextView typeMenu = view.findViewById(R.id.menuFoodDetails);
        TextView desc = view.findViewById(R.id.descFoodDetails);
        TextView price = view.findViewById(R.id.food_price);


        @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("_id"));
        @SuppressLint("Range") String titleC = cursor.getString(cursor.getColumnIndex("title"));
        @SuppressLint("Range") String descC = cursor.getString(cursor.getColumnIndex("description"));
        @SuppressLint("Range") String nameMenu = cursor.getString(cursor.getColumnIndex("menuName"));
        @SuppressLint("Range") Float priceC = cursor.getFloat(cursor.getColumnIndex("price"));
        @SuppressLint("Range") byte[] pictureC = cursor.getBlob(cursor.getColumnIndex("picture"));

        title.setText(titleC);
        //desc.setText(descC);
        //typeMenu.setText(nameMenu);
        price.setText(String.valueOf(priceC));



        //chuyen byte[] -> bitmap
        byte[] pictureBo = pictureC;
        Bitmap bitmap = BitmapFactory.decodeByteArray(pictureBo,0, pictureBo.length);
        //imageView.setImageBitmap(bitmap);

        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);
        thumbnail.setImageBitmap(circularBitmap);


//        //chuyen byte[] -> bitmap
//        byte[] picture = pictureC;
//        Bitmap bitmap = BitmapFactory.decodeByteArray(picture,0, picture.length);
//        thumbnail.setImageBitmap(bitmap);

        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, FoodDetailsActivity.class);
                FoodDetailsActivity.getCurrentIndex(id);
                activity.startActivity(intent);
            }
        });


    }

}

