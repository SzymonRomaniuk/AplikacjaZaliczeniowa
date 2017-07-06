package com.example.szymon.aplikacjazaliczeniowa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Szymon on 2017-07-06.
 */

public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "MyDBName.db";


    public static final String TABLE_NAME_A = "lokalizacjaA";
    public static final String ID_A = "id";
    public static final String DLUGOSC_GEOGRAFICZNA_A = "dlugosc geograficzna";
    public static final String SZEROKOSC_GEOGRAFICZNA_A = "szerokosc geograficzna";

    public static final String TABLE_NAME_B = "lokalizacjaB";
    public static final String ID_B = "id";
    public static final String DLUGOSC_GEOGRAFICZNA_B = "dlugosc geograficzna";
    public static final String SZEROKOSC_GEOGRAFICZNA_B = "szerokosc geograficzna";




    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table lokalizacjaA " +
                        "(id integer primary key, dlugosc_geograficzna real,szerokosc_geograficzna real) \n"+


                "create table lokalizacjaB "+
                        "(id integer primary key, dlugosc_geograficzna real, szerokosc_geograficzna real) \n"
        );

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS lokalizacjaA, lokalizacjaB");

        onCreate(db);
    }

    public boolean insertLokalizacjaA (float dlugosc_geograficzna, float szerokosc_geograficzna) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("dlugosc geograficzna", dlugosc_geograficzna);
        contentValues.put("szerokosc geograficzna", szerokosc_geograficzna);

        db.insert("lokalizacjaA", null, contentValues);
        return true;
    }

    public boolean insertLokalizacjaB (float dlugosc_geograficzna, float szerokosc_geograficzna) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("dlugosc geograficzna", dlugosc_geograficzna);
        contentValues.put("szerokosc geograficzna", szerokosc_geograficzna);

        db.insert("lokalizacjaB", null, contentValues);
        return true;
    }

    public Cursor getDataA(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from lokalizacjaA where id="+id+"", null );
        return res;
    }

    public Cursor getDataB(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from lokalizacjaB where id="+id+"", null );
        return res;
    }

    public int numberOfRowsA(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db,TABLE_NAME_A);
        return numRows;
    }

    public int numberOfRowsB(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db,TABLE_NAME_B);
        return numRows;
    }

    public Integer deleteLocationA (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("lokalizacjaA",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public Integer deleteLocationB (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("lokalizacjaB",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<WspolrzedneGeograficzne> getAllWspolrzedneA() {
        ArrayList<WspolrzedneGeograficzne> array_list = new ArrayList<>();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from lokalizacjaA", null );
        res.moveToFirst();
        WspolrzedneGeograficzne wspolrzedneGeograficzne;
        while(res.isAfterLast() == false)
        {
            wspolrzedneGeograficzne = new WspolrzedneGeograficzne(res.getFloat(res.getColumnIndex(DLUGOSC_GEOGRAFICZNA_A)),
                    res.getFloat(res.getColumnIndex(SZEROKOSC_GEOGRAFICZNA_A)));
            array_list.add(wspolrzedneGeograficzne);
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<WspolrzedneGeograficzne> getAllWspolrzedneB() {
        ArrayList<WspolrzedneGeograficzne> array_list = new ArrayList<>();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from lokalizacjaB", null );
        res.moveToFirst();
        WspolrzedneGeograficzne wspolrzedneGeograficzne;
        while(res.isAfterLast() == false)
        {
            wspolrzedneGeograficzne = new WspolrzedneGeograficzne(res.getFloat(res.getColumnIndex(DLUGOSC_GEOGRAFICZNA_B)),
                    res.getFloat(res.getColumnIndex(SZEROKOSC_GEOGRAFICZNA_B)));
            array_list.add(wspolrzedneGeograficzne);
            res.moveToNext();
        }
        return array_list;
    }


}
