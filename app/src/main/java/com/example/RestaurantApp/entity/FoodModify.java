package com.example.RestaurantApp.entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.RestaurantApp.db.DBHelper;
import com.example.RestaurantApp.models.Food;

public class FoodModify {
    public static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS food2 (\n" +
            "\t_id integer primary key autoincrement,\n" +
            "\ttitle varchar(50),\n" +
            "\tdescription text,\n" +
            "\tmenuName varchar(50),\n" +
            "\tprice float,\n" +
            "\tpicture blob\n"+
            ")";

    public static Cursor findAll () {
        String sql = "select * from food2";
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        return cursor;
    }

    public  static  void  insert(Food food) {
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", food.getTitle());
        values.put("description", food.getDisc());
        values.put("menuName", food.getMenuName());
        values.put("price", food.getPrice());
        values.put("picture", food.getPicture());

        sqLiteDatabase.insert("food2", null, values);
    }
    public  static void update(Food food) {
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", food.getTitle());
        values.put("description", food.getDisc());
        values.put("menuName", food.getMenuName());
        values.put("price", food.getPrice());
        values.put("picture", food.getPicture());

        sqLiteDatabase.update("food2", values, "_id = " + food.getFoodId(), null);
    }

    public Food getFood(int studentId) {
        SQLiteDatabase db = DBHelper.getInstance(null).getReadableDatabase();

        Cursor cursor = db.query("food2", null, "_id" + " = ?", new String[] { String.valueOf(studentId) },null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        Food food = new Food(cursor.getInt(0), cursor.getString(1), cursor.getString(2),cursor.getString(3), cursor.getFloat(4), cursor.getBlob(5));
        return food;
    }
    public static Cursor getFoodByMenu(String menuName) {
        String sql = "select * from food2 where menuName like '" + menuName +"' ORDER BY title ASC";
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        return cursor;
    }
    public Food getFoodByName(String nameFood) {
        String sql = "select * from food2 where title like '" + nameFood +"'";
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        if(cursor != null)
            cursor.moveToFirst();
        Food food = new Food(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getFloat(4),  cursor.getBlob(5));
        return food;
    }
    public static Cursor getFoodByNameCur(String nameFood) {
        String sql = "select * from food2 where title like '%" + nameFood +"%'";
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        return cursor;
    }

    public static void  delete(int id){
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getWritableDatabase();

        sqLiteDatabase.delete("food2","_id = " +id,null);
    }
}

