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

public class edit_employee_Activity extends AppCompatActivity {
    EditText edtTenTK, edtMatKhau, edtSDT , edtEmail, edtHoTen;
    ImageButton ibtnFolderSua ,ibtnCameraSua;
    ImageView imgHinhSua;
    RadioButton rbnNhanVien , rbnQuanLy;
    TextView back;
    Button btnSua;
    String tenTK1;
    final int REQUEST_CODE_CAMERASUA = 1234,REQUEST_CODE_FOLDERSUA = 4567 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);

        anhXa();
        dieuKhienCameraVaFolder();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String quyen ;

                if(rbnNhanVien.isChecked()){
                    quyen = "Nhân viên";
                }else{
                    quyen = "Quản lý";
                }
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgHinhSua.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] hinhAnh = byteArrayOutputStream.toByteArray();
                thongTinNhanVien.data.editEmployeeToDataBase(
                        edtHoTen.getText().toString().trim(),
                        edtSDT.getText().toString().trim(),
                        edtEmail.getText().toString().trim(),
                        edtMatKhau.getText().toString().trim(),
                        tenTK1,
                        hinhAnh,
                        quyen

                );
                Intent intent = new Intent(edit_employee_Activity.this,thongTinNhanVien.class);
                startActivity(intent);
            }
        });



    }
    private void dieuKhienCameraVaFolder() {
        ibtnCameraSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(edit_employee_Activity.this, new String[]{android.Manifest.permission.CAMERA},REQUEST_CODE_CAMERASUA);
            }
        });
        ibtnFolderSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(edit_employee_Activity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_FOLDERSUA);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_CAMERASUA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CODE_CAMERASUA);
                } else {
                    Toast.makeText(this, "Ban khong cap quyen truy cap camera", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_FOLDERSUA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_FOLDERSUA);
                } else {
                    Toast.makeText(this, "Ban khong cap quyen truy cap thu vien", Toast.LENGTH_SHORT).show();
                }
                break;
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_CAMERASUA&&resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinhSua.setImageBitmap(bitmap);
        }
        if(requestCode == REQUEST_CODE_FOLDERSUA&&resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgHinhSua.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void anhXa() {
        edtHoTen = (EditText) findViewById(R.id.textViewSuaHoTen);
        edtEmail = (EditText) findViewById(R.id.textViewSuaEmail);
        edtSDT = (EditText) findViewById(R.id.textViewSuaSDT);
        edtMatKhau = (EditText) findViewById(R.id.textViewSuaMatKhau);
        edtTenTK = (EditText) findViewById(R.id.textViewSuaTenTaiKhoan);
        rbnNhanVien = (RadioButton) findViewById(R.id.radioSuaNhanVien);
        rbnQuanLy = (RadioButton) findViewById(R.id.radioSuaQuanLy);
        imgHinhSua = (ImageView) findViewById(R.id.imageViewHinh1Sua);
        ibtnCameraSua = (ImageButton) findViewById(R.id.imageButtonCameraSua);
        ibtnFolderSua = (ImageButton) findViewById(R.id.imageButtonFolderSua);
        back = (TextView) findViewById(R.id.textViewSuaNhanVien);
        btnSua = (Button) findViewById(R.id.buttonSua);
        Intent intent = getIntent();
        tenTK1 = intent.getStringExtra("TenTK");
        String ten = intent.getStringExtra("Ten");
        String sdt = intent.getStringExtra("SDT");
        String email = intent.getStringExtra("Email");
        String quyen = intent.getStringExtra("Quyen");
        String pass = intent.getStringExtra("Pass");
        if( quyen == "Nhân Viên"){
            rbnNhanVien.setChecked(true);
        }
        edtTenTK.setText(tenTK1 + " <không đổi>" );
        edtTenTK.setEnabled(false);
        edtHoTen.setText(ten);
        edtSDT.setText(sdt);
        edtEmail.setText(email);
        edtMatKhau.setText(pass);
        byte[] hinh = intent.getByteArrayExtra("Hinh");
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);
        imgHinhSua.setImageBitmap(bitmap);


    }
}