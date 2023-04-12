package com.example.RestaurantApp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class thongTinNhanVien extends AppCompatActivity {
    ListView lvEmployee;
    ArrayList<user> employeeArrayList;
    employee_adapter adapter;
    public static Database data;
    TextView back;
    ImageView add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nhan_vien);
        anhXa();
        getDataEmployee();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thongTinNhanVien.this, TrangChuChinhActivity.class);
                startActivity(intent);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thongTinNhanVien.this, add_employee_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void anhXa() {
        lvEmployee = (ListView) findViewById(R.id.listviewMain);
        back = (TextView) findViewById(R.id.backNV);
        add = (ImageView) findViewById(R.id.addNV);
    }

    public void getDataEmployee() {
            employeeArrayList = new ArrayList<>();
            adapter = new employee_adapter(thongTinNhanVien.this, R.layout.dong_employee, employeeArrayList);
            lvEmployee.setAdapter(adapter);
            data = new Database(thongTinNhanVien.this,"QLDatabase.sqlie",null,1);
            Cursor dataNhanVien = data.GetData("SELECT * FROM BANG2");
            while (dataNhanVien.moveToNext()) {
                String tenTK = dataNhanVien.getString(0);
                String hoTen = dataNhanVien.getString(1);
                String quyenHan = dataNhanVien.getString(2);
                String SDT = dataNhanVien.getString(3);
                String email = dataNhanVien.getString(4);
                String matKhau = dataNhanVien.getString(5);
                if(quyenHan.equals("Nhân viên") || quyenHan.equals("Quản lý")) {
                    employeeArrayList.add(new user(tenTK, hoTen, quyenHan, SDT, email, matKhau, dataNhanVien.getBlob(6)));
                }

            }
            adapter.notifyDataSetChanged();
        }


        public void removeEmployee( int i){
            AlertDialog.Builder alertDiaLog = new AlertDialog.Builder(this);
            alertDiaLog.setTitle("Thông Báo!");
            alertDiaLog.setIcon(R.mipmap.ic_launcher);
            alertDiaLog.setMessage("Bạn có muốn xoá thông tin nhân viên này không? ");
            alertDiaLog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    data.removeEmployeeToDatabase(employeeArrayList.get(i));
                    employeeArrayList.remove(i);
                    getDataEmployee();

                }
            });
            alertDiaLog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            alertDiaLog.show();
        }
        public void editEmployee( int i){
            Intent intent1 = new Intent(thongTinNhanVien.this, edit_employee_Activity.class);
            intent1.putExtra("TenTK", employeeArrayList.get(i).getTenTaiKhoan().toString().trim());
            intent1.putExtra("Ten", employeeArrayList.get(i).getHoTenNV().toString().trim());
            intent1.putExtra("Quyen", employeeArrayList.get(i).getQuyenHan().toString().trim());
            intent1.putExtra("SDT", employeeArrayList.get(i).getSDT().toString().trim());
            intent1.putExtra("Email", employeeArrayList.get(i).getEmail().toString().trim());
            intent1.putExtra("Pass", employeeArrayList.get(i).getMatKhau().toString().trim());
            intent1.putExtra("Hinh", employeeArrayList.get(i).getHinh());
            startActivity(intent1);
        }


    }
