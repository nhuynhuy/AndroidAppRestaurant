package com.example.RestaurantApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class maXacNhan extends AppCompatActivity {
    TextView tvForgotPW2 ,tvSDTorEmailForgotPassword,tvthoigian;
    TextView btnVerifyForgotPassword2;
    EditText edtSo1,edtSo2,edtSo3,edtSo4;
    Random rd;
    CountDownTimer countDownTimer;
    String email = "", sdt ="" , tenTK;
    int ngauNhien ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ma_xac_nhan);
        anhXa();
        intendChuyenMH();
    }
    void anhXa(){
        tvForgotPW2 =(TextView) findViewById(R.id.tvForgotPassword2);
        btnVerifyForgotPassword2 =(TextView) findViewById(R.id.buttonVerifyForgotPassword2);
        tvSDTorEmailForgotPassword = (TextView) findViewById(R.id.tvSDTorEmailForgotPassword);
        tvthoigian = (TextView)findViewById(R.id.textViewThoiGian);
        edtSo1 = (EditText) findViewById(R.id.edtSo1ToForgotPassWord2);
        edtSo2 = (EditText) findViewById(R.id.edtSo2ToForgotPassWord2);
        edtSo3 = (EditText) findViewById(R.id.edtSo3ToForgotPassWord2);
        edtSo4 = (EditText) findViewById(R.id.edtSo4ToForgotPassWord2);
        rd=new Random();
        String a = tvSDTorEmailForgotPassword.getText().toString().trim();
        Intent intent = getIntent();
        tenTK = intent.getStringExtra("TenTK");
        sdt = intent.getStringExtra("PhoneNumber");
        email = intent.getStringExtra("Email");
        tvSDTorEmailForgotPassword.setText( a + " " +sdt +" " + email);
        countDownTimer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvthoigian.setText("Resend code in "+ millisUntilFinished/1000 + "s");
            }

            @Override
            public void onFinish() {

            }


        };
        demNguoc();

    }
    void intendChuyenMH(){
        tvForgotPW2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnVerifyForgotPassword2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ma = Integer.parseInt(edtSo1.getText().toString().trim() + edtSo2.getText().toString().trim() + edtSo3.getText().toString().trim() + edtSo4.getText().toString().trim());
                if(ma == ngauNhien){
                    Intent intentPW2 = new Intent(maXacNhan.this, createPassNew.class);
                    intentPW2.putExtra("TenTK", tenTK);
                    intentPW2.putExtra("PhoneNumber",sdt);
                    intentPW2.putExtra("Email",email);
                    startActivity(intentPW2);
                }else {
                    ngauNhien=rd.nextInt((9999-1001)+1)+1001;
                    Toast.makeText(maXacNhan.this,"Mã Pin Mới Của Bạn Là "+ ngauNhien, Toast.LENGTH_LONG).show();
                    countDownTimer.start();
                }

            }
        });

    }
    public void demNguoc(){
        ngauNhien=rd.nextInt((9999-1001)+1)+1001;
        Toast.makeText(this,"Mã Pin Mới Của Bạn Là "+ ngauNhien, Toast.LENGTH_LONG).show();
        countDownTimer.start();



    }

}