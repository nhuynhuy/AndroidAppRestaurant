package com.example.RestaurantApp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.CursorWindow;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.RestaurantApp.adapters.FoodCursorAdapter;
import com.example.RestaurantApp.db.DBHelper;
import com.example.RestaurantApp.entity.FoodModify;
import com.example.RestaurantApp.entity.MenuModify;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Field;

;



public class FoodActivity extends AppCompatActivity {
    ListView foodListView;

    //ImageView foodThumbnail;
    FoodCursorAdapter foodAdapter;
    int currentIndex = -1;
    FloatingActionButton floatingActionButton;
    ImageButton imageButton;

    static String menuName;
    ImageView imageView;
    TextView textView, textView2;



    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        DBHelper.getInstance(this);
        foodListView = findViewById(R.id.listFood);
        floatingActionButton = findViewById(R.id.fab);
        imageButton=findViewById(R.id.food_thumbnail);
        imageView =findViewById(R.id.imBackround);
        textView=findViewById(R.id.tvMenuBackround);
        textView2 = findViewById(R.id.tvMenuBackround2);

        //doi mau actionbar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.BlueNgoc)));

        //doi mau noi dung floating button
        floatingActionButton.setImageTintList(ColorStateList.valueOf(R.color.BlueNgoc));


        //nut tro ve tren action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

         //creat adapter
        String menu = menuName.toString();
        Cursor cursor = FoodModify.getFoodByMenu(menu);
        //Cursor cursor = FoodModify.findAll();
        foodAdapter = new FoodCursorAdapter(this,cursor);
        foodListView.setAdapter(foodAdapter);

        MenuModify menuModify = new MenuModify();
        com.example.RestaurantApp.models.Menu menu1 = menuModify.getMenu(menuName);
        textView.setText(menu1.getMenuName());
        textView2.setText(menu1.getMenuName());
        //bo hinh

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivities(new Intent[]{new Intent(FoodActivity.this, FoodAddActivity.class)});
            }
        });

        registerForContextMenu(foodListView);
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

    public static void getMenuName(String name){
        menuName = name;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        ((MenuInflater) inflater).inflate(R.menu.menu_context, menu);
        MenuInflater inflater2 = getMenuInflater();
        inflater2.inflate(R.menu.menu_actionbar, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Enter name of Food to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // xử lý khi người dùng nhấn nút tìm kiếm
                SearchResultActivity.getFoodName(query, 2);
                startActivities(new Intent[]{new Intent(FoodActivity.this, SearchResultActivity.class)});
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
            case R.id.edit_menu:
                MenuEditActivity.getCurrentIndex(menuName);
                startActivities(new Intent[]{new Intent(FoodActivity.this, MenuEditActivity.class)});
                return true;
            case R.id.delete_menu:
                MenuDelete.showDialogDelete(menuName, FoodActivity.this);
                //finish();
                return true;
            case  android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}