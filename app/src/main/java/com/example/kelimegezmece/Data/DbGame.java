package com.example.kelimegezmece.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbGame extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;//database versiyon
    private static final String DATABASE_NAME = "GameDB";//database adı
    private static final String TABLE_NAME = "AllLevel";//tablo adı

    // tablodaki kolonlar

    private static final String level = "level";
    private static final String gecildiMi = "gecildiMi";
    private static final String harfler = "harfler";
    private static final String cevaplar = "cevaplar";
    private static final String isim = "isim";
    private static final String sure = "sure";
    private static final String puan = "puan";
    SQLiteDatabase db;
    public DbGame(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db=db;
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS  " + TABLE_NAME + "("
                + level + " INTEGER PRIMARY KEY, "
                + gecildiMi +" INTEGER, "
                + puan +" INTEGER, "
                + sure +" INTEGER, "
                + harfler +" TEXT, "
                + isim +" TEXT, "
                + cevaplar +" TEXT "
                + ")";
        db.execSQL(CREATE_TABLE);
    }
    public  void addLevel(LevelTable levelTable){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(level, levelTable.getLevel());
        values.put(gecildiMi,levelTable.isGecildiMi());
        values.put(harfler, levelTable.getHarfler());
        values.put(cevaplar, levelTable.getCevaplar());
        values.put(isim,levelTable.getIsim());
        values.put(sure, levelTable.getSure());
        values.put(puan, levelTable.getPuan());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public List<LevelTable> getAllLevel(){
        db = this.getWritableDatabase();
        List<LevelTable> data = new ArrayList<>();
        Cursor c = null;
        c=db.rawQuery("select * from " + TABLE_NAME ,null);
        while (c.moveToNext()){
            LevelTable levelTable = new LevelTable();
            levelTable.setLevel(c.getInt(c.getColumnIndex(level)));
            levelTable.setGecildiMi(c.getInt(c.getColumnIndex(gecildiMi)) == 1);
            levelTable.setHarfler(c.getString(c.getColumnIndex(harfler)));
            levelTable.setCevaplar(c.getString(c.getColumnIndex(cevaplar)));
            levelTable.setIsim(c.getString(c.getColumnIndex(isim)));
            levelTable.setSure(c.getInt(c.getColumnIndex(sure)));
            levelTable.setPuan(c.getInt(c.getColumnIndex(puan)));
            data.add(levelTable);
        }
        db.close();
        return data;

    }
    public LevelTable getLevel(int target){
        db = this.getWritableDatabase();
        LevelTable levelTable = new LevelTable();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME + " where " + level + "=" + target ,null);
        while ((c.moveToNext())){
            levelTable.setLevel(c.getInt(c.getColumnIndex(level)));
            levelTable.setGecildiMi(c.getInt(c.getColumnIndex(gecildiMi)) == 1);
            levelTable.setHarfler(c.getString(c.getColumnIndex(harfler)));
            levelTable.setCevaplar(c.getString(c.getColumnIndex(cevaplar)));
            levelTable.setIsim(c.getString(c.getColumnIndex(isim)));
            levelTable.setSure(c.getInt(c.getColumnIndex(sure)));
            levelTable.setPuan(c.getInt(c.getColumnIndex(puan)));
        }
        db.close();
        return levelTable;
    }
    public void updateLevel(LevelTable levelTable){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(gecildiMi,levelTable.isGecildiMi());
      /*  values.put(harfler, levelTable.getHarfler());
        values.put(cevaplar, levelTable.getCevaplar());*/
        values.put(isim,levelTable.getIsim());
        values.put(sure, levelTable.getSure());
        values.put(puan, levelTable.getPuan());
        db.update(TABLE_NAME, values,level + "=" + levelTable.getLevel(),null );
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sorgu ="DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(sorgu);
        onCreate(db);
    }
}
