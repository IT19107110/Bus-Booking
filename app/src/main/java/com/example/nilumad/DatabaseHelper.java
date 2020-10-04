package com.example.nilumad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="bus.db";
    public static final String TABLE_NAME ="employee_table";
    public static final String COL_1 ="TravelsName";
    public static final String COL_2 ="VehicleNo";
    public static final String COL_3 ="DriverName";
    public static final String COL_4 ="Departure";
    public static final String COL_5 ="Arrival";
    public static final String COL_6 ="TotalSeats";
    public static final String COL_7 ="TicketPrice";



    public DatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(TravelsName text  ,VehicleNo integer primary key,DriverName text,Departure text,Arrival text,TotalSeats integer,TicketPrice interger)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    public boolean insertData(String TravelsName ,String VehicleNo,String Drivername,String Departure,String Arrival,String TotalSeats,String TicketPrice){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put(COL_1,TravelsName);
        contentValues.put(COL_2,VehicleNo);
        contentValues.put(COL_3,Drivername);
        contentValues.put(COL_4,Departure);
        contentValues.put(COL_5,Arrival);
        contentValues.put(COL_6,TotalSeats);
        contentValues.put(COL_7,TicketPrice);
        long result = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        if(result== -1)
            return false;
        else
            return true;


    }
    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from "+TABLE_NAME,null);
        return res;



    }
    public boolean updateData(String TravelsName,String VehicleNo,String Drivername,String Departure,String Arrival,String TotalSeats,String TicketPrice) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,TravelsName);
        contentValues.put(COL_2, VehicleNo);
        contentValues.put(COL_3, Drivername);
        contentValues.put(COL_4, Departure);
        contentValues.put(COL_5, Arrival);
        contentValues.put(COL_6, TotalSeats);
        contentValues.put(COL_7, TicketPrice);
        sqLiteDatabase.update(TABLE_NAME, contentValues, "VehicleNo = ?", new String[]{VehicleNo});
        return true;
    }
    public Integer deleteData(String VehicleNo){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,"VehicleNo = ?",new String[] {VehicleNo});

    }
    public Cursor searchData(String VehicleNo) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_2 + "= '" + VehicleNo + "'",null);
        return data;
    };
}
