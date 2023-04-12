package com.example.RestaurantApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
public class dangKy extends AppCompatActivity {
    ImageButton ibtnBackDK;
    EditText edtTenTKTTK, edtMatKhauTTK, edtSDTTTK, edtEmailTTK, edtHoTenTTK;
    TextView ibtnFolderTTK, ibtnCameraTTK;
    ImageView imgHinhTTK;

    Button btnTTK;

    Database data;
    final int REQUEST_CODE_CAMERATTK = 123, REQUEST_CODE_FOLDERTTK = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        data = new Database(dangKy.this,"QLDatabase.sqlie",null,1);
        //data.QueryData("CREATE TABLE BANG2( tenTaiKhoan VARCHAR(50) PRIMARY KEY,hoTenNV NVARCHAR(100), quyenHan NVARCHAR(50),SDT VARCHAR(20),email VARCHAR(50),matKhau VARCHAR(50),Hinh BLOB)");


        anhXa();
        chuyenManHinh();
        btnTTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quyen = "Khach";
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgHinhTTK.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] hinhAnh = byteArrayOutputStream.toByteArray();

                data.addEmployeeToDataBase(
                        edtHoTenTTK.getText().toString().trim(),
                        edtSDTTTK.getText().toString().trim(),
                        edtEmailTTK.getText().toString().trim(),
                        edtMatKhauTTK.getText().toString().trim(),
                        edtTenTKTTK.getText().toString().trim(),
                        hinhAnh,
                        quyen
                );
                Toast.makeText(dangKy.this, "Đăng Ký Thành Công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(dangKy.this,TrangChuChinhActivity.class);
                startActivity(intent);
            }
        });
    }

    private void chuyenManHinh() {

        ibtnBackDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ibtnCameraTTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(dangKy.this, new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_CAMERATTK);

            }
        });
        ibtnFolderTTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(dangKy.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_FOLDERTTK);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_CAMERATTK:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CODE_CAMERATTK);
                } else {
                    Toast.makeText(this, "Ban khong cap quyen truy cap camera", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_FOLDERTTK:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_FOLDERTTK);
                } else {
                    Toast.makeText(this, "Ban khong cap quyen truy cap thu vien", Toast.LENGTH_SHORT).show();
                }
                break;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAMERATTK && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinhTTK.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDERTTK && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgHinhTTK.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    private void anhXa() {
        ibtnBackDK = (ImageButton) findViewById(R.id.ibtnBackDK);
        edtHoTenTTK = (EditText) findViewById(R.id.textViewHoTenTTK);
        edtEmailTTK  = (EditText) findViewById(R.id.textViewEmailTTK);
        edtSDTTTK  = (EditText) findViewById(R.id.textViewSDTTTK);
        edtMatKhauTTK  = (EditText) findViewById(R.id.textViewMatKhauTTK);
        edtTenTKTTK  = (EditText) findViewById(R.id.textViewTenTaiKhoanTTK);
        imgHinhTTK=(ImageView) findViewById(R.id.imageViewHinhTTK);
        ibtnCameraTTK =(TextView) findViewById(R.id.imageButtonCameraTTK);
        ibtnFolderTTK =(TextView) findViewById(R.id.imageButtonFolderTTK);
        btnTTK = (Button) findViewById(R.id.buttonTTK);

    }
}