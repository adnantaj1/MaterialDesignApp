package com.example.pearl.materialdesignapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by Pearl on 10/5/2015.
 */
public class DBUsers  {

    private SQLiteDatabase db;
    private DatabaseHelper databaseHelper;
    private final Context context;
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Users.db";

    public static final String USERS_TABLE_NAME = "users";
    public static final String USERS_COLUMN_ID = "id";
    public static final String USERS_COLUMN_NAME = "name";
    public static final String USERS_COLUMN_PASSWORD = "password";

    private static final String DATABASE_CREATE = "create table if not exists "+USERS_TABLE_NAME+"(id integer primary" +
            " key autoincrement,"
            + " name VARCHAR, password VARCHAR) ; ";

    public DBUsers(Context ctx){
        this.context = ctx;
        databaseHelper = new DatabaseHelper(context);
    }

    public Cursor getAllRecords() {
        return db.query(USERS_TABLE_NAME, new String[] { USERS_COLUMN_ID, USERS_COLUMN_NAME,
                USERS_COLUMN_PASSWORD }, null, null, null, null, null);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (Exception e) {
                Log.e("SQL EXCEPTIOM", "SQL EXCEPTION THERE");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("Drop TABLE IF EXISTS users");
            onCreate(db);
        }
    }  //** end of inner class **//

    public long insertUsers(String name, String password){
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_COLUMN_NAME, name );
        contentValues.put(USERS_COLUMN_PASSWORD,password);
        return db.insert("users", null, contentValues);


    }

    public boolean deleteUser(long rowId){
        return db.delete(USERS_TABLE_NAME,USERS_COLUMN_ID + "=" + rowId,null)>0;
    }

    public DBUsers open(){
        db = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }

    public Cursor getData(String name){
        Cursor res =  db.rawQuery( "select * from users where name=" + name+"", null );
        return res;
    }
    public boolean comparison(String name, String password){
        Cursor c = getData(name);
        String n = c.getString(0);
        String p = c.getString(1);
        if ( n.equalsIgnoreCase(name) && p.equalsIgnoreCase(password)){
            return true;
        }
        return false;
    }

    public int numberOfRows(){
        int numRows = (int) DatabaseUtils.queryNumEntries(db, USERS_TABLE_NAME);
        return numRows;
    }

    public boolean updateUsers (Integer id, String name, String password)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        db.update("users", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
}
