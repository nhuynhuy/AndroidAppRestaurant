package com.example.RestaurantApp.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.RestaurantApp.entity.FoodModify;
import com.example.RestaurantApp.entity.MenuModify;

public class DBHelper extends SQLiteOpenHelper {
    //private static final String DB_NAME = "NhaHang";
    private static final String DB_NAME = "NhaHang2";
    private  static final int DB_VERSION = 1;

    private static DBHelper instance = null;

    public synchronized static  DBHelper getInstance(Context context) {
        if(instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    public void QueryData (String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

//    private DBHelper(@Nullable Context context) {
//        super(context, DB_NAME, null, DB_VERSION);
//    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // TH nao thi goi toi function : 1 lan khi tao DB_VERSION 1
        String sqlMenu = MenuModify.QUERY_CREATE_TABLE;
        sqLiteDatabase.execSQL(sqlMenu);
        String sql = FoodModify.QUERY_CREATE_TABLE;
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_students_table = String.format("DROP TABLE IF EXISTS %s", DB_NAME);
        db.execSQL(drop_students_table);

        onCreate(db);


//        if (oldVersion < 2) {
//            db.execSQL("ALTER TABLE my_table ADD COLUMN my_column INTEGER");
//        }

    }

}
