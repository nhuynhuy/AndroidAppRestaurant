package com.example.RestaurantApp.food;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.example.RestaurantApp.entity.MenuModify;

public class MenuDelete {
    static public void showDialogDelete(String name, Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //MenuCursorAdapter menuCursorAdapter;

        builder.setTitle("Do you want to delete"+ name +" menu?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MenuModify.deletebyName(name);
                Intent intent = new Intent(activity, MenuFood.class);
                activity.startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }
}
