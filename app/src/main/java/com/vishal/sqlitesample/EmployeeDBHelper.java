package com.vishal.sqlitesample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Vishal Aroor on 16-Aug-17.
 */

public class EmployeeDBHelper extends SQLiteOpenHelper {

    Context context;
    SQLiteDatabase db;

    public EmployeeDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        this.context = context;
        db = getWritableDatabase();
    }

    public EmployeeDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);

        this.context = context;
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table Employee(_id integer primary key autoincrement, ename text,age integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long saveEmployee(String name, String age) {

        try {
            ContentValues cv = new ContentValues();
            cv.put("ename", name);
            cv.put("age", age);
            long l = db.insert("Employee", null, cv);
            return l;
        }
        catch (Exception e) {

            Log.e("EmployeeDBHelper", "Caught Exception in saveEmployee ..." + e.getMessage());
            e.printStackTrace();
        }

        return  -1;
    }

    public Cursor findEmployee(String id) {

        try {
            Cursor cursor = db.query("Employee", new String[] {"ename", "age"}, "_id="+id,null,null,null, null );
            return cursor;
        }
        catch (Exception e) {
            Log.e("EmployeeDBHelper", "Caught Exception in findEmployee ..." + e.getMessage());
            e.printStackTrace();
        }

        return  null;
    }

    public int updateEmployee(String id, String name, String age) {

        try {
            ContentValues cv = new ContentValues();
            cv.put("ename", name);
            cv.put("age", age);

            int i = db.update("Employee", cv, "_id="+id, null);
            return i;
        }
        catch (Exception e) {
            Log.e("EmployeeDBHelper", "Caught Exception in updateEmployee ..." + e.getMessage());
            e.printStackTrace();
        }

        return -1;
    }


    public int deleteEmployee(String id) {

        try {

            int i = db.delete("Employee", "_id=" + id, null);
            return i;
        }
        catch (Exception e) {
            Log.e("EmployeeDBHelper", "Caught Exception in deleteEmployee ..." + e.getMessage());
            e.printStackTrace();
        }

        return  -1;
    }

    public Cursor fetchAll() {

        try {
            Cursor c = db.query("Employee", new String[]{"_id, name, age"}, null, null, null, null, null);

            //if we don't have a primary key column with the name _id and instead if it was empid
            //Cursor c1 = db.rawQuery("select empid _id, * from emp", null);

            return c;
        }
        catch (Exception e) {
            Log.e("EmployeeDBHelper", "Caught Exception in fetchAll ..." + e.getMessage());
            e.printStackTrace();
        }

        return null;

    }


}
