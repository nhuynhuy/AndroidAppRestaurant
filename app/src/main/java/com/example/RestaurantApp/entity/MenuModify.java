package com.example.RestaurantApp.entity;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.RestaurantApp.db.DBHelper;
import com.example.RestaurantApp.models.Menu;

public class MenuModify {
    public static final String QUERY_CREATE_TABLE = "create table menu (\n" +
            "\t_id integer primary key autoincrement,\n" +
            "\tname varchar(50),\n" +
            "\tpicture blob\n"+
            ")";

    public static Cursor findAll () {
        String sql = "select * from menu LIMIT 4 OFFSET 0";
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        return cursor;
    }

    public  static  void  insert(Menu menu) {
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", menu.getMenuName());
        values.put("picture", menu.getMenuPic());

        sqLiteDatabase.insert("menu", null, values);
    }
    public  static void update(Menu menu) {
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", menu.getMenuName());
        values.put("picture", menu.getMenuPic());

        sqLiteDatabase.update("menu", values, "_id = " + menu.getMenuId(), null);
    }

    public Menu getMenu(String nameMenu) {
        String sql = "select * from menu where name like '" + nameMenu +"'";
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        if(cursor != null)
            cursor.moveToFirst();
        Menu menu = new Menu(cursor.getInt(0), cursor.getString(1),  cursor.getBlob(2));
        return menu;
    }
    public static Cursor getMenuByNameCur(String nameMenu) {
        String sql = "select * from menu where name like '%" + nameMenu +"%'";
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        return cursor;
    }
    public static void  deletebyName(String menu){
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getWritableDatabase();

        sqLiteDatabase.delete("menu","name like '" + menu +"'",null);
    }
    public static void  delete(int id){
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getWritableDatabase();

        sqLiteDatabase.delete("menu","_id = " +id,null);
    }
}

