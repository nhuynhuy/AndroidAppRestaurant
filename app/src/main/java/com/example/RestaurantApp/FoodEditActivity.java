package com.example.RestaurantApp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.RestaurantApp.adapters.FoodCursorAdapter;
import com.example.RestaurantApp.adapters.MenuAdapter;
import com.example.RestaurantApp.adapters.MenuCursorAdapter;
import com.example.RestaurantApp.entity.FoodModify;
import com.example.RestaurantApp.entity.MenuModify;
import com.example.RestaurantApp.models.Food;
import com.example.RestaurantApp.models.Menu;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class FoodEditActivity extends AppCompatActivity {
    int REQUEST_CODE_CAMERA =123;
    int REQUEST_CODE_UPLOAD =456;
    EditText edTitle;
    EditText edDisc;
    EditText edPrice;
    Button btnUpdate;
    Button btnDone;

    Button btnCamera ;
    Button btnUpload ;
    ImageView imgPicture ;
    Cursor cursor;
    //spinner menu
    Spinner spMenu;

    byte[] hinhAnh;
    private ArrayList<Menu> menuList;
    private MenuAdapter menuAdapter;
    ImageButton imMenu;

    String menuName;
    byte[] menuPicture;
    Spinner spinnerMenu;

    //adapter food cursor
    FoodCursorAdapter foodCursorAdapter;
    public static final int PICK_IMAGE = 1;
    public static int indexCurrent;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_edit);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.BlueNgoc)));

        AnhXa();
        //nut tro ve tren thanh action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        ArrayList<String> arrayList = new ArrayList<String>();
        MenuCursorAdapter menuCursorAdapter;
        Cursor cursorMenu = MenuModify.findAll();
        menuCursorAdapter = new MenuCursorAdapter(FoodEditActivity.this,cursorMenu);
        cursorMenu=menuCursorAdapter.getCursor();
        while (cursorMenu.moveToNext()){
            @SuppressLint("Range") String name = cursorMenu.getString(cursorMenu.getColumnIndex("name"));
            arrayList.add(name);
        }

        //menuAdapter = new MenuAdapter(this, menuList);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayList);
        arrayAdapter.setDropDownViewResource(R.layout.custom_text_spinner);
        spinnerMenu.setAdapter(arrayAdapter);
        spinnerMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                menuName = arrayList.get(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Toast.makeText(this, "bug here", Toast.LENGTH_SHORT).show();


        //get current position
        Cursor cursor = FoodModify.findAll();
        foodCursorAdapter = new FoodCursorAdapter(FoodEditActivity.this,cursor);
        cursor = foodCursorAdapter.getCursor();
        foodCursorAdapter = new FoodCursorAdapter(FoodEditActivity.this, cursor);
        cursor.moveToPosition(indexCurrent);

        FoodModify foodModify = new FoodModify();
        Food food = foodModify.getFood(indexCurrent);

        // get current food
        edTitle.setText(food.getTitle());
        edDisc.setText(food.getDisc());
        edPrice.setText(String.valueOf(food.getPrice()));
        byte[] pictureCR = food.getPicture();
        Bitmap bitmap = BitmapFactory.decodeByteArray(pictureCR,0, pictureCR.length);
        imgPicture.setImageBitmap(bitmap);


        // chup anh
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_UPLOAD);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View v) {

                // below line is change imageview -> byte
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgPicture.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] hinhAnh = byteArrayOutputStream.toByteArray();

                // below line is to get data from all edit text fields.
                String foodTitle = edTitle.getText().toString();
                String foodDisc = edDisc.getText().toString();
                Float foodPrice = Float.valueOf(edPrice.getText().toString());
                byte[] foodPicture = hinhAnh;

                //get update current food
                if(indexCurrent >= 0){
                    Cursor cursor = FoodModify.findAll();
                    foodCursorAdapter = new FoodCursorAdapter(FoodEditActivity.this,cursor);
                    cursor = foodCursorAdapter.getCursor();
                    foodCursorAdapter = new FoodCursorAdapter(FoodEditActivity.this, cursor);
                    cursor.moveToPosition(indexCurrent);

                    @SuppressLint("Range") int id = food.getFoodId();

                    Food foodUpdated = new Food(foodTitle, foodDisc, menuName,foodPrice,foodPicture);
                    foodUpdated.setFoodId(id);
                    FoodModify.update(foodUpdated);
                }

                // validating if the text fields are empty or not.
                if (foodTitle.isEmpty() && foodDisc.isEmpty() &&String.valueOf(foodPrice).isEmpty() && String.valueOf(foodPicture).isEmpty()) {
                    Toast.makeText(FoodEditActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }

                // on below line we are calling a method to add new
                // food to sqlite data and pass all our values to it.
                // after adding the data we are displaying a toast message.
                Toast.makeText(FoodEditActivity.this, "Food has been updated.", Toast.LENGTH_SHORT).show();

                //debug
                //Toast.makeText(FoodEditActivity.this, String.valueOf(foodPrice), Toast.LENGTH_SHORT).show();
                //

                edTitle.setText("");
                edDisc.setText("");
                edPrice.setText("");
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FoodEditActivity.this, FoodActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgPicture.setImageBitmap(bitmap);
        }

        if (requestCode == REQUEST_CODE_UPLOAD && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgPicture.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void AnhXa(){

        edTitle = findViewById(R.id.edTitle);
        edDisc = findViewById(R.id.edDisc);
        edPrice = findViewById(R.id.edPrice);


        btnCamera = findViewById(R.id.edbtn_shot);
        btnUpload = findViewById(R.id.edImbtn_upload);
        imgPicture = findViewById(R.id.edPicture);
        btnUpdate = findViewById(R.id.btnEdit);
        btnDone = findViewById(R.id.btnDone);
        spinnerMenu = findViewById(R.id.edFood_spinner);
    }

    public static void getCurrentIndex(int index){
        indexCurrent = index;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //change picture -> bitmap
    private void bitmap(int pic) {
        imMenu = findViewById(pic);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imMenu.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        hinhAnh = byteArrayOutputStream.toByteArray();
    }
}