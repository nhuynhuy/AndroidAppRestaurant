package com.example.RestaurantApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;


public class forgotpassword extends AppCompatActivity {
    TextView tvForgotPW1, tvSDT, tvEmail;
    TextView btnContinueForgotPassword;
    RadioButton rdEmail, rdSDT;
    Database data;
    String email,tenTK;
    ArrayList<user> userArrayList;
    String sdt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        anhXa();
        getDaTaEmployee();
        Intent intent = getIntent();
        tenTK = intent.getStringExtra("tenTK");
        tvEmail.setText(timKiemEmail(tenTK));
        tvSDT.setText("+84 " + timKiemSDT(tenTK));
        intendChuyenMH();

    }

    void anhXa() {
        tvForgotPW1 = (TextView) findViewById(R.id.tvForgotPassword1);
        btnContinueForgotPassword = (TextView) findViewById(R.id.buttonContinueForgotPassword);
        tvSDT = (TextView) findViewById(R.id.tvSDTForgotPassword);
        tvEmail = (TextView) findViewById(R.id.tvEmailForgotPassword);
        rdSDT = (RadioButton) findViewById(R.id.radioSDT);
        rdEmail = (RadioButton) findViewById(R.id.radioEmail);


    }
    private void getDaTaEmployee(){
        userArrayList = new ArrayList<>();
        data = new Database(forgotpassword.this,"QLDatabase.sqlie",null,1);
        Cursor dataNhanVien = data.GetData("SELECT * FROM BANG2");
       while(dataNhanVien.moveToNext()) {
           String tenTK = dataNhanVien.getString(0);
            String hoTen = dataNhanVien.getString(1);
            String quyenHan = dataNhanVien.getString(2);
            String SDT = dataNhanVien.getString(3);
            String email = dataNhanVien.getString(4);
            String matKhau = dataNhanVien.getString(5);
            userArrayList.add(new user(tenTK,hoTen,quyenHan,SDT,email,matKhau,dataNhanVien.getBlob(6)));

        }

    }

    void intendChuyenMH() {
        tvForgotPW1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnContinueForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdSDT.isChecked()) {
                    Intent intentPW2 = new Intent(forgotpassword.this, maXacNhan.class);
                    intentPW2.putExtra("TenTK", tenTK);
                    intentPW2.putExtra("PhoneNumber", timKiemSDT(tenTK));
                    intentPW2.putExtra("Email", " ");
                    startActivity(intentPW2);

                } else {
                    Intent intentPW3 = new Intent(forgotpassword.this, maXacNhan.class);
                    intentPW3.putExtra("TenTK", tenTK);
                    intentPW3.putExtra("PhoneNumber", " ");
                    intentPW3.putExtra("Email", timKiemEmail(tenTK));
                    startActivity(intentPW3);
                }


            }
        });

    }
    public String timKiemEmail(String  tenTK ){
        for(user user : userArrayList){
            if(user.getTenTaiKhoan().equals(tenTK) ){
                return user.getEmail();
            }
        }
        return "";
    }
    public String timKiemSDT(String  tenTK){
        for(user user : userArrayList){
            if(user.getTenTaiKhoan().equals(tenTK) ){
                return user.getSDT();
            }
        }
        return "";
    }
}