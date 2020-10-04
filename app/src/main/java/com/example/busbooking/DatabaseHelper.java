package com.example.busbooking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "customer_bookingkisho1.db";
    public static final String TABLE_NAME = "Customer_Booking_Details";
    public static final String COL_1 = "TravelsName";
    public static final String COL_2 = "Name";
    public static final String COL_3 = "Email";
    public static final String COL_4 = "PhoneNo";
    public static final String COL_5 = "Departure";
    public static final String COL_6 = "Arrival";
    public static final String COL_7 = "Date";
    public static final String COL_8 = "NoOfSeats";
    public static final String COL_9 = "TotalCost";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "( TravelsName TEXT, Name TEXT,Email TEXT PRIMARY KEY , PhoneNo TEXT , Departure TEXT, Arrival TEXT, Date TEXT, NoOfSeats integer, TotalCost integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public boolean insertData(String tname, String name, String email, String phone, String departure, String arrival, String date, int noOfSeats, int tcost) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, tname);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, email);
        contentValues.put(COL_4, phone);
        contentValues.put(COL_5, departure);
        contentValues.put(COL_6, arrival);
        contentValues.put(COL_7, date);
        contentValues.put(COL_8, noOfSeats);
        contentValues.put(COL_9, tcost);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }
    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
    public  boolean updateDetail(String tname, String name, String email, String phone, String departure, String arrival, String date, String noOfSeats, String tcost){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_1, tname);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, email);
        contentValues.put(COL_4, phone);
        contentValues.put(COL_5, departure);
        contentValues.put(COL_6, arrival);
        contentValues.put(COL_7, date);
        contentValues.put(COL_8, noOfSeats);
        contentValues.put(COL_9, tcost);
        sqLiteDatabase.update(TABLE_NAME,contentValues,"email = ?",new String[] {email});
        return true;
    }
    public Integer deleteDetail (String email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,"email = ?",new String[] {email});
    }

}