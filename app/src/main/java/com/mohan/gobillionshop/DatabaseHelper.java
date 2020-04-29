package com.mohan.gobillionshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "product.db";
    public static final String TABLE_NAME = "product";
    public static final String COL_1 = "id";
    public static final String COL_2 = "name";
    public static final String COL_3 = "description";
    public static final String COL_4 = "price";
    public static final String COL_5 = "image";
    public static final String COL_6 = "rating";
    public static final String COL_7 = "nRating";
    public static final String COL_8 = "quantitiy";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+ TABLE_NAME + " (COL_1 INTEGER PRIMARY KEY AUTOINCREMENT,COL_2 TEXT,COL_3 TEXT,COL_4 TEXT,COL_5 TEXT,COL_6 TEXT,COL_7 INT,COL_8 INT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String desc, String price,String image,String rating,int nrate){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,desc);
        contentValues.put(COL_4,price);
        contentValues.put(COL_5,image);
        contentValues.put(COL_6,rating);
        contentValues.put(COL_7,nrate);
        contentValues.put(COL_8,1);

        long result = database.insert(TABLE_NAME,null,contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor res = database.rawQuery("select * from "+TABLE_NAME,null);
        return res;

    }
}
