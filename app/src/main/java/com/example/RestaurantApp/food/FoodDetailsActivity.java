package com.example.RestaurantApp.food;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.RestaurantApp.R;
import com.example.RestaurantApp.adapters.FoodCursorAdapter;
import com.example.RestaurantApp.entity.FoodModify;
import com.example.RestaurantApp.models.Food;

public class FoodDetailsActivity extends AppCompatActivity {
    ImageView imageView;
    Button btnEditFood;
    Button btnDeleteFood;
    static int idfoodCurrent = -1;
    FoodCursorAdapter foodCursorAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        btnDeleteFood = findViewById(R.id.btnDeleteDetails);
        btnEditFood=findViewById(R.id.btnEditDetails);
        imageView=findViewById(R.id.imFoodDetails);

        TextView title = findViewById(R.id.titleFoodDetails);
        TextView typeMenu = findViewById(R.id.menuFoodDetails);
        TextView desc = findViewById(R.id.descFoodDetails);
        TextView price = findViewById(R.id.priceFoodDetails);



        FoodModify foodModify = new FoodModify();
        Food food = foodModify.getFood(idfoodCurrent);

        //nut tro ve tren thanh action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.BlueNgoc)));

        title.setText(food.getTitle());
        desc.setText(food.getDisc());
        typeMenu.setText(food.getMenuName());
        price.setText(String.valueOf(food.getPrice()));
//        //chuyen byte[] -> bitmap
        byte[] picture = food.getPicture();
        Bitmap bitmap = BitmapFactory.decodeByteArray(picture,0, picture.length);
        imageView.setImageBitmap(bitmap);
//
//        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);
//
//        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView circularImageView = (ImageView) findViewById(R.id.imFoodDetails);
//        circularImageView.setImageBitmap(circularBitmap);


        btnEditFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodEditActivity.getCurrentIndex(idfoodCurrent);
                startActivities(new Intent[]{new Intent(FoodDetailsActivity.this, FoodEditActivity.class)});
            }
        });
        btnDeleteFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Tạo đối tượng AlertDialog.Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(FoodDetailsActivity.this);

                // Thiết lập tiêu đề và thông báo của hộp thoại
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn có chắc muốn xóa?");

                // Thiết lập nút tích cực và hành động khi nhấn vào
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý hành động khi nhấn nút Đồng ý
                        FoodModify.delete(idfoodCurrent);
                        startActivities(new Intent[]{new Intent(FoodDetailsActivity.this, FoodActivity.class)});
                        Toast.makeText(FoodDetailsActivity.this, "Food has been deleted.", Toast.LENGTH_SHORT).show();
                    }
                });

                // Thiết lập nút tiêu cực và hành động khi nhấn vào
                builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý hành động khi nhấn nút Hủy bỏ
                    }
                });

                // Hiển thị hộp thoại
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

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

    public static void getCurrentIndex(int index){
        idfoodCurrent = index;
    }

}