package com.example.RestaurantApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txtDangNhap, txtDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        chuyenManHinh();
    }

    private void chuyenManHinh() {
        txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, dangKy.class);
                startActivity(intent);
            }
        });
        txtDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, dangNhap.class);
                startActivity(intent);
            }
        });
    }

    private void anhXa() {
        txtDangNhap = (TextView) findViewById(R.id.textViewSignUp);
        txtDangKy = (TextView) findViewById(R.id.textViewSignIn);

    }
}