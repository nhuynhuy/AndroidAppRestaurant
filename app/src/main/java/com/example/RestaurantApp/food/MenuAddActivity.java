package com.example.RestaurantApp.food;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.RestaurantApp.R;
import com.example.RestaurantApp.adapters.FoodCursorAdapter;
import com.example.RestaurantApp.adapters.MenuAdapter;
import com.example.RestaurantApp.entity.MenuModify;
import com.example.RestaurantApp.models.Menu;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MenuAddActivity extends AppCompatActivity {

    int REQUEST_CODE_CAMERA =123;
    int REQUEST_CODE_UPLOAD =456;
    EditText edTitle;

    Button btnAdd;
    Button btnDone;

    Button btnCamera ;
    Button btnUpload ;
    ImageView imgPicture ;
    byte[] hinhAnh2;

    private ArrayList<Menu> menuList;
    private MenuAdapter menuAdapter;
    ImageButton imMenu;

    String menuName;
    byte[] menuPicture;
    Spinner spinnerMenu;

    FoodCursorAdapter foodCursorAdapter;
    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_add);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.BlueNgoc)));
        //nut tro ve tren thanh action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        AnhXa();

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


        menuAdapter = new MenuAdapter(this, menuList);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below is get data spinner


                // below line is change imageview -> byte
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgPicture.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] hinhAnh = byteArrayOutputStream.toByteArray();


                // below line is to get data from all edit text fields.
                String foodTitle = edTitle.getText().toString();

                byte[] foodPicture = hinhAnh;

                // validating if the text fields are empty or not.
                if (foodTitle.isEmpty()   && String.valueOf(foodPicture).isEmpty()) {
                    Toast.makeText(MenuAddActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }

                // on below line we are calling a method to add new
                // food to sqlite data and pass all our values to it.
                //insert to DATABASE
                Menu menu = new Menu(foodTitle, foodPicture);
                MenuModify.insert(menu);

                //change cursor
//                Cursor cursor = FoodModify.findAll();
//                foodCursorAdapter.changeCursor(cursor);
                //foodDatabase.addNewFood2(foodTitle, foodDisc, foodPrice, foodPicture);
                // after adding the data we are displaying a toast message.
                Toast.makeText(MenuAddActivity.this, "Menu has been added.", Toast.LENGTH_SHORT).show();


                edTitle.setText("");
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuAddActivity.this, MenuFood.class);
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

        edTitle = findViewById(R.id.titleMenu);

        btnCamera = findViewById(R.id.btn_shot);
        btnUpload = findViewById(R.id.btn_upload);
        imgPicture = findViewById(R.id.pictureMenu);
        btnAdd = findViewById(R.id.btnAdd);
        btnDone = findViewById(R.id.btnDone);
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
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imMenu.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        hinhAnh2 = byteArrayOutputStream.toByteArray();
    }
}