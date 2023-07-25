package example.myapplication1.healthassistant.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns {
    public static final String DB_CONTACTS = "helsts.db";
    public static final int DATABASE_VERSION = 1;
    public DatabaseHelper(Context context) {
        super(context, DB_CONTACTS, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table USER ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL ," +
                "password TEXT, ves TEXT, gves TEXT, rost TEXT, pol TEXT, voz TEXT);");
        db.execSQL("create table lek ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, data TEXT, inter TEXT, prod TEXT, kol TEXT, doz TEXT, t1 TEXT, t2 TEXT, t3 TEXT, chek INTEGER);");
        db.execSQL("create table vod ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, data TEXT NOT NULL ,prog TEXT);");
        db.execSQL("create table event ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, data TEXT NOT NULL ,time TEXT);");
           }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.DBEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.DBy.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Lek.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.Event.TABLE_NAME);
        onCreate(db);
    }

    public Boolean login (String user, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from USER where name = ? and password = ?", new String[]{user, password});
        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }


    public Boolean insertData (String  username, String password, String ves, String gves, String rost, String pol, String voz){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", username);
        contentValues.put("password", password);
        contentValues.put("ves", ves);
        contentValues.put("gves", gves);
        contentValues.put("rost", rost);
        contentValues.put("pol", pol);
        contentValues.put("voz", voz);
        long result = MyDatabase.insert("USER", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Boolean insertDatavod (String data, String prog, String name){
        SQLiteDatabase MyDatabases = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("data", data);
        contentValues.put("prog", prog);
        contentValues.put("name", name);
        long result = MyDatabases.insert("vod", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Boolean insertlek ( String name, String data, String inter, String prod, String kol, String doz, String t1, String t2, String t3, int chek){
        SQLiteDatabase MyDatabases = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("data", data);
        contentValues.put("inter", inter);
        contentValues.put("prod", prod);
        contentValues.put("kol", kol);
        contentValues.put("doz", doz);
        contentValues.put("t1", t1);
        contentValues.put("t2", t2);
        contentValues.put("t3", t3);
        contentValues.put("chek", chek);
        long result = MyDatabases.insert("lek", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Boolean insertDataEvent (String data, String time, String name){
        SQLiteDatabase MyDatabases = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("data", data);
        contentValues.put("time", time);
        contentValues.put("name", name);
        long result = MyDatabases.insert("event", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}
