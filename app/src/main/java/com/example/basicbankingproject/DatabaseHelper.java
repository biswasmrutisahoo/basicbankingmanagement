package com.example.basicbankingproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private String TABLE1 = "user_table";
    private String TABLE2 = "transfers_table";
    public static final String DATABASE_NAME = "BankDB";

    //COLUMNS OF TABLE1

    private static final String KEY_ACCOUNTNO = "ACCOUNT_NO";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_BALANCE = "BALANCE";
    private static final String KEY_EMAIL = "EMAIL";
    private static final String KEY_IFSC = "IFSC_CODE";
    private static final String KEY_PHONE_NO = "PHONE_NUMBER";

    //COLUMNS OF TABLE 2
    private static final String KEY_TRANSACTIONID = "TRANSACTIONID";
    private static final String KEY_DATE = "DATE";
    private static final String KEY_FROMNAME = "FROMNAME";
    private static final String KEY_TONAME = "TONAME";
    private static final String KEY_STATUS = "STATUS";
    private static final String KEY_AMOUNT = "AMOUNT";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE1 + "(" + KEY_ACCOUNTNO + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT," + KEY_BALANCE + " DECIMAL, " + KEY_EMAIL + " VARCHAR, " + KEY_IFSC + " VARCHAR, " + KEY_PHONE_NO + " TEXT" + ")");
        db.execSQL("CREATE TABLE " + TABLE2 + "(" + KEY_TRANSACTIONID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DATE + " TEXT," + KEY_FROMNAME + " TEXT," + KEY_TONAME + " TEXT," + KEY_BALANCE + " DECIMAL," + KEY_STATUS + " TEXT" + ")");


        db.execSQL("insert into " + TABLE1 + " values(10021546897,'Biswa',100000.00,'biswa@gmail.com','CNRB3540',9090323291)");
        db.execSQL("insert into " + TABLE1 + " values(10021546898,'Soumya',50000.00,'sunil@gmail.com','CNRB3540',9090323291)");
        db.execSQL("insert into " + TABLE1 + " values(10021546899,'Sunil',20000.00,'biswa@gmail.com','CNRB3540',9090323291)");
        db.execSQL("insert into " + TABLE1 + " values(10021546896,'Jasobanta',30000.00,'soumya@gmail.com','CNRB3540',9090323291)");
        db.execSQL("insert into " + TABLE1 + " values(10021546895,'Suraj',25000.00,'sunil@gmail.com','PUNB1010',9090323291)");
        db.execSQL("insert into " + TABLE1 + " values(10021546894,'Papu',8000.00,'mohan@gmail.com','BISW5023',9090323291)");
        db.execSQL("insert into " + TABLE1 + " values(10021546893,'Malaya',4864.00,'soumya@gmail.com','PUNB1010',9090323291)");
        db.execSQL("insert into " + TABLE1 + " values(10021546892,'Satya',7000.00,'sunil@gmail.com','BISW5023',9090323291)");
        db.execSQL("insert into " + TABLE1 + " values(10021546891,'Kriti',15000.00,'sunil@gmail.com','PUNB1010',9090323291)");
        db.execSQL("insert into " + TABLE1 + " values(10021546890,'Janmejaya',1000.00,'soumya@gmail.com','BISW5023',9090323291)");
        db.execSQL("insert into " + TABLE1 + " values(100215468922,'Manas',35000.00,'biswa@gmail.com','PUNB1010',9090323291)");
    }
    @Override
    public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2);
        onCreate(db);
    }
    public Cursor readalldata(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE1, null);
        return cursor;
    }

    public Cursor readparticulardata(String accountnumber){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE1+" where "+KEY_ACCOUNTNO +" = " +accountnumber, null);
        return cursor;
    }

    public Cursor readselectuserdata(String accountnumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE1+" except select * from user_table where "+KEY_ACCOUNTNO+" = " +accountnumber, null);
        return cursor;
    }

    public void updateAmount(String accountnumber, String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update "+TABLE1+" set balance = " + amount + " where "+KEY_ACCOUNTNO+" = " +accountnumber);
    }

    public Cursor readtransferdata(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE2, null);
        return cursor;
    }

    public boolean insertTransferData(String date,String from_name, String to_name, String amount, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_DATE, date);
        contentValues.put(KEY_FROMNAME, from_name);
        contentValues.put(KEY_TONAME, to_name);
        contentValues.put(KEY_AMOUNT, amount);
        contentValues.put(KEY_STATUS, status);
        Long result = db.insert(TABLE2, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
}