package com.example.RestaurantApp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class createPassNew extends AppCompatActivity {
    TextView tvCreateNewPassword;
    Database data;
    String email , sdt,tenTK;
    CountDownTimer countDownTimer;
    TextView btnContinueCreatePassword;
    EditText edtpassWord , edtpassWordAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pass_new);
        anhXa();
        intendChuyenMH();
    }
    void anhXa(){
        tvCreateNewPassword =(TextView) findViewById(R.id.textViewCreateNewPassword);
        btnContinueCreatePassword =(TextView) findViewById(R.id.buttonContinueCreatePassword);
        edtpassWord = (EditText) findViewById(R.id.edtPasswordToCNP);
        edtpassWordAgain = (EditText) findViewById(R.id.edtAgainPasswordToCNP);
        Intent intent = getIntent();
        tenTK = intent.getStringExtra("TenTK");
        sdt = intent.getStringExtra("PhoneNumber");
        email = intent.getStringExtra("Email");

    }
    void intendChuyenMH(){
        tvCreateNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnContinueCreatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtpassWord.getText().toString().trim().equals(edtpassWordAgain.getText().toString().trim()) ){
                    data = new Database(createPassNew.this,"QLDatabase.sqlie",null,1);
                    data.editPassWordToDataBase(tenTK,edtpassWord.getText().toString().trim());
                    countDownTimer = new CountDownTimer(30000,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            Dialog dialog = new Dialog(createPassNew.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.dialog_congratulation);
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.show();
                        }

                        @Override
                        public void onFinish() {

                        }


                    }.start();
                    Intent intent = new Intent(createPassNew.this,TrangChuChinhActivity.class);
                    startActivity(intent);



                }else {
                    edtpassWordAgain.setText("");
                    edtpassWordAgain.setHint("wrong!!!");
                    edtpassWordAgain.setHintTextColor(Integer.parseInt("#FFFFFF"));
                }

            }
        });

    }
}