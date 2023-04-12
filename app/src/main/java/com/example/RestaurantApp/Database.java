package com.example.RestaurantApp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {


    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    //Truy van nhung khong tra ket qua
    public void QueryData(String sql){
        SQLiteDatabase dataBase = getWritableDatabase();
        dataBase.execSQL(sql);
    }
    //Truy van nhung tra ket qua: SELECT
    public Cursor GetData(String sql){
        SQLiteDatabase dataBase = getReadableDatabase();
        return dataBase.rawQuery(sql,null);
    }
    public void addEmployeeToDataBase( String ten, String sdt, String email, String pass, String tenTK,byte[] hinhAnh, String quyen){
        SQLiteDatabase dataBase = getWritableDatabase();
        //  dataBase.execSQL("INSERT INTO BANG2 VALUES('" + tenTK+ "','"+ ten+"','"+ quyen+" ','"+ sdt+ "','"+ email+" ','"+ pass+ "','"+ hinhAnh+"')");
// // data.QueryData("CREATE TABLE BANG2( tenTaiKhoan VARCHAR(50) PRIMARY KEY,hoTenNV NVARCHAR(100), quyenHan NVARCHAR(50),
// SDT VARCHAR(20),email VARCHAR(50),matKhau VARCHAR(50),Hinh BLOB)");

        ContentValues cv = new ContentValues();
        cv.put("tenTaiKhoan", tenTK);
        cv.put("hoTenNV", ten);
        cv.put("quyenHan", quyen);
        cv.put("SDT", sdt);
        cv.put("email", email);
        cv.put("matKhau",pass);
        cv.put("Hinh",hinhAnh);

        dataBase.insert("BANG2", null, cv);
    }



    public void editEmployeeToDataBase(String ten, String sdt, String email, String pass, String tenTK,byte[] hinhAnh, String quyen){
        SQLiteDatabase dataBase = getWritableDatabase();
        //  dataBase.execSQL("UPDATE BANG2 SET hoTenNV = '" +  emp.getHoTenNV()+ "',quyenHan = '"+  emp.getQuyenHan()+"',SDT ='"+emp.getSDT()+" ',email ='"+emp.getEmail()+ "',matKhau ='"+emp.getMatKhau()+" ',Hinh ='"+ emp.getHinh()+"'  WHERE tenTaiKhoan = '"+emp.getTenTaiKhoan()+"'");
        ContentValues cv = new ContentValues();
        cv.put("tenTaiKhoan", tenTK);
        cv.put("hoTenNV", ten);
        cv.put("quyenHan", quyen);
        cv.put("SDT", sdt);
        cv.put("email", email);
        cv.put("matKhau",pass);
        cv.put("Hinh",hinhAnh);
        dataBase.update("BANG2",cv,"tenTaiKhoan=?", new String[]{tenTK});
        dataBase.close();
    }
    public void removeEmployeeToDatabase(user emp){
        SQLiteDatabase dataBase = getWritableDatabase();
        dataBase.execSQL("DELETE FROM BANG2 WHERE tenTaiKhoan = '"+emp.getTenTaiKhoan()+"';");
    }
    public void editPassWordToDataBase(String tenTK,String pass){
        SQLiteDatabase dataBase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("matKhau",pass);
        dataBase.update("BANG2",cv,"tenTaiKhoan=?", new String[]{tenTK});
        dataBase.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

