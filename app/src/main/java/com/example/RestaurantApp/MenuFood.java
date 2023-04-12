package com.example.RestaurantApp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWindow;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.RestaurantApp.adapters.MenuCursorAdapter;
import com.example.RestaurantApp.db.DBHelper;
import com.example.RestaurantApp.entity.MenuModify;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;

public class MenuFood extends AppCompatActivity {
    GridView menuGridView;
    MenuCursorAdapter menuAdapter;
    int currentIndex = -1;
    ImageButton imageButton;
    BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_food);



        DBHelper.getInstance(this);
        menuGridView=findViewById(R.id.menuGridView);
        //bottomNavigationView = findViewById(R.id.bottom_navigation);
        imageButton = findViewById(R.id.imbtnMenu);

        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.BlueNgoc)));


        //create adapter

        Cursor cursor = MenuModify.findAll();
        menuAdapter = new MenuCursorAdapter(this, cursor);
        menuGridView.setNumColumns(2);
        menuGridView.setAdapter(menuAdapter);


//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.navigation_home:
//                        startActivities(new Intent[]{new Intent(MenuFood.this, MainActivity.class)});
//                        return true;
//                    case R.id.navigation_search:
//                        // Handle the Dashboard action
//                        return true;
//                    case R.id.navigation_assignment:
//                        // Handle the Notifications action
//                        return true;
//                }
//                return false;
//            }
//        });

//        try {
//            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
//            field.setAccessible(true);
//            field.set(null, 100 * 1024 * 1024); //100MB
//        } catch (Exception e) {
//            if (BuildConfig.DEBUG) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        ((MenuInflater) inflater).inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_menu:
                startActivities(new Intent[]{new Intent(MenuFood.this, MenuAddActivity.class)});
                return true;
            case R.id.delete_menu:

                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        ((MenuInflater) inflater).inflate(R.menu.menu, menu);

        MenuInflater inflater2 = getMenuInflater();
        inflater2.inflate(R.menu.menu_actionbar, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // xử lý khi người dùng nhấn nút tìm kiếm
                SearchResultActivity.getFoodName(query, 1);
                startActivities(new Intent[]{new Intent(MenuFood.this, SearchResultActivity.class)});
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // xử lý khi người dùng nhập từ khóa tìm kiếm
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_menu:
                startActivities(new Intent[]{new Intent(MenuFood.this, MenuAddActivity.class)});
                return true;
            case R.id.exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
