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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class add_employee_Activity extends AppCompatActivity {
    EditText edtTenTK, edtMatKhau, edtSDT , edtEmail, edtHoTen;
    ImageButton ibtnFolderThem ,ibtnCameraThem;
    ImageView imgHinhThem;
    RadioButton rbnNhanVien;
    TextView back;
    Button btnThem;
    final int REQUEST_CODE_CAMERATHEM = 123,REQUEST_CODE_FOLDERTHEM = 456 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        anhXa();
        dieuKhienCameraVaFolder();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quyen;
                if(rbnNhanVien.isChecked()){
                    quyen = "Nhân viên";
                }else {
                    quyen = "Quản lý";
                }
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgHinhThem.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] hinhAnh = byteArrayOutputStream.toByteArray();
                // data.QueryData("CREATE TABLE BANG2( tenTaiKhoan VARCHAR(50) PRIMARY KEY,hoTenNV NVARCHAR(100), quyenHan NVARCHAR(50),SDT VARCHAR(20),email VARCHAR(50),matKhau VARCHAR(50),Hinh BLOB)");

                thongTinNhanVien.data.addEmployeeToDataBase(
                        edtHoTen.getText().toString().trim(),
                        edtSDT.getText().toString().trim(),
                        edtEmail.getText().toString().trim(),
                        edtMatKhau.getText().toString().trim(),
                        edtTenTK.getText().toString().trim(),
                        hinhAnh,
                        quyen
                );
                Intent intent = new Intent(add_employee_Activity.this,thongTinNhanVien.class);
                startActivity(intent);
            }
        });
    }

    private void dieuKhienCameraVaFolder() {
        ibtnCameraThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(add_employee_Activity.this, new String[]{android.Manifest.permission.CAMERA},REQUEST_CODE_CAMERATHEM);
            }
        });
        ibtnFolderThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(add_employee_Activity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_FOLDERTHEM);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_CAMERATHEM:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CODE_CAMERATHEM);
                } else {
                    Toast.makeText(this, "Ban khong cap quyen truy cap camera", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_FOLDERTHEM:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_FOLDERTHEM);
                } else {
                    Toast.makeText(this, "Ban khong cap quyen truy cap thu vien", Toast.LENGTH_SHORT).show();
                }
                break;
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_CAMERATHEM&&resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinhThem.setImageBitmap(bitmap);
        }
        if(requestCode == REQUEST_CODE_FOLDERTHEM&&resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgHinhThem.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void anhXa() {
        edtHoTen = (EditText) findViewById(R.id.textViewThemHoTen);
        edtEmail  = (EditText) findViewById(R.id.textViewThemEmail);
        edtSDT  = (EditText) findViewById(R.id.textViewThemSDT);
        edtMatKhau  = (EditText) findViewById(R.id.textViewThemMatKhau);
        edtTenTK  = (EditText) findViewById(R.id.textViewThemTenTaiKhoan);
        rbnNhanVien =(RadioButton) findViewById(R.id.radioThemNhanVien);
        imgHinhThem =(ImageView) findViewById(R.id.imageViewHinh1Them);
        ibtnCameraThem =(ImageButton) findViewById(R.id.imageButtonCameraThem);
        ibtnFolderThem =(ImageButton) findViewById(R.id.imageButtonFolderThem);
        back = (TextView) findViewById(R.id.textViewThemNhanVien);
        btnThem = (Button) findViewById(R.id.buttonThem);
    }

}