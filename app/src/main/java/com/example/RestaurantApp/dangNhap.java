package com.example.RestaurantApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class dangNhap extends AppCompatActivity {
    ImageButton ibtnBackDN;
    TextView tvDKTuDN;
    TextView textViewSignUp , tvForgotPWLg;
    EditText edtUserName, edtPass;
    TextView btnSignIn;
    CheckBox cbRemember;
    Database data;
    SharedPreferences sharedPreferences;
    ArrayList<user> userArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        anhXa();
        chuyenManHinh();
        rememberTK();
    }

    private void chuyenManHinh() {
        ibtnBackDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvDKTuDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dangNhap.this,dangKy.class);
                startActivity(intent);
            }
        });
        tvForgotPWLg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dangNhap.this,forgotpassword.class);
                intent.putExtra("tenTK",edtUserName.getText().toString().trim());
                startActivity(intent);
            }
        });
    }

    private void anhXa() {
        ibtnBackDN = (ImageButton) findViewById(R.id.ibtnBackDN);
        tvDKTuDN = (TextView) findViewById(R.id.textViewDKTuDN);
        sharedPreferences = getSharedPreferences("dataSignIn",MODE_PRIVATE);
        edtUserName =(EditText) findViewById(R.id.editTextUserName);
        edtPass =(EditText) findViewById(R.id.editTextPassWord);
        cbRemember =(CheckBox) findViewById(R.id.cbrememberMe);
        btnSignIn =(TextView) findViewById(R.id.textviewDangNhap);
        tvForgotPWLg =(TextView) findViewById(R.id.textForgotPassWordLogin);
        getDaTaEmployee();
    }
    public void rememberTK(){
        edtUserName.setText(sharedPreferences.getString("Email",""));
        edtPass.setText(sharedPreferences.getString("PassWord",""));
        cbRemember.setChecked(sharedPreferences.getBoolean("Checked",false));
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtUserName.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                if(timKiem(email,pass)){

                    if(cbRemember.isChecked()){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Email",email);
                        editor.putString("PassWord",pass);
                        editor.putBoolean("Checked",true);
                        editor.commit();
                    }else{
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("Email");
                        editor.remove("PassWord");
                        editor.remove("Checked");
                        editor.commit();
                    }
                    Intent intent = new Intent(dangNhap.this,TrangChuChinhActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(dangNhap.this, "Loi dang nhap", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void getDaTaEmployee(){
        userArrayList = new ArrayList<>();
        data = new Database(dangNhap.this,"QLDatabase.sqlie",null,1);
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

    public boolean timKiem(String tenTK ,String password){
        for(user user : userArrayList){
            if(user.getTenTaiKhoan().equals(tenTK) && user.getMatKhau().equals(password)){
                return true;
            }
        }
        return false;
    }
}