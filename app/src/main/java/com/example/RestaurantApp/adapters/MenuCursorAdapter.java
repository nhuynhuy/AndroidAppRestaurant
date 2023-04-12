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
import android.widget.TextView;

import com.example.RestaurantApp.FoodActivity;
import com.example.RestaurantApp.R;

public class MenuCursorAdapter extends CursorAdapter {

    private static int currentIndex;
    Activity activity;
    ImageButton pictureMenu;
    public MenuCursorAdapter(Activity activity, Cursor cursor) {
        super(activity,cursor);
        this.activity = activity;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.gridview_row, null);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name = view.findViewById(R.id.tvNameMenu);
        pictureMenu = view.findViewById(R.id.imbtnMenu);

        @SuppressLint("Range") String nameMenu = cursor.getString(cursor.getColumnIndex("name"));
        @SuppressLint("Range") byte[] picMenu = cursor.getBlob(cursor.getColumnIndex("picture"));
        //@SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));

        name.setText(nameMenu);
        byte[] picture = picMenu;
        Bitmap bitmap = BitmapFactory.decodeByteArray(picture,0, picture.length);
        pictureMenu.setImageBitmap(bitmap);


        pictureMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String menu = nameMenu.toString();
                FoodActivity.getMenuName(nameMenu);
                Intent intent = new Intent(activity, FoodActivity.class);
                activity.startActivity(intent);
            }
        });
//        pictureMenu.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                PopupMenu popupMenu = new PopupMenu(activity,view);
//                popupMenu.getMenuInflater().inflate(R.menu.menu_context, popupMenu.getMenu());
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();
//                        int index = info.position;
//                        switch (menuItem.getItemId()){
//                            case R.id.edit_menu:
//                                MenuEditActivity.getCurrentIndex(index);
//                                Intent intent = new Intent(activity, MenuEditActivity.class);
//                                activity.startActivity(intent);
//                                return true;
//                            case R.id.delete_menu:
//                                MenuDelete.showDialogDelete(index, activity);
//                                return true;
//                        }
//                        return false;
//                    }
//                });
//                return true;
//            }
//        });


    }

}

