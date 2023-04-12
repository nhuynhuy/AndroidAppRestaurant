package com.example.RestaurantApp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.RestaurantApp.adapters.FoodCursorAdapter;
import com.example.RestaurantApp.adapters.MenuCursorAdapter;
import com.example.RestaurantApp.db.DBHelper;
import com.example.RestaurantApp.entity.FoodModify;
import com.example.RestaurantApp.entity.MenuModify;

public class SearchResultActivity extends AppCompatActivity {
    ListView listView;
    GridView gridView;
    static String foodName;
    static int type =0;
    TextView textView;
    FoodCursorAdapter foodCursorAdapter;
    MenuCursorAdapter menuCursorAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        listView= findViewById(R.id.lvSearch);
        gridView = findViewById(R.id.menuGridView);
        textView = findViewById(R.id.tvSearchResult);
        textView.setText(foodName);

        if (type == 1){
            DBHelper.getInstance(this);
            Cursor cursor = MenuModify.getMenuByNameCur(foodName);
            menuCursorAdapter = new MenuCursorAdapter(this,cursor);
            listView.setAdapter(menuCursorAdapter);
        }
        else {
            DBHelper.getInstance(this);
            Cursor cursor = FoodModify.getFoodByNameCur(foodName);
            foodCursorAdapter = new FoodCursorAdapter(this,cursor);
            listView.setAdapter(foodCursorAdapter);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }
    public static void getFoodName(String name , int typeN){
        foodName = name;
        type = typeN;
    }

}