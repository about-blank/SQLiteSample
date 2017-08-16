package com.vishal.sqlitesample;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vishal.sqlitesample.model.Employee;

import java.util.List;

import nl.qbusict.cupboard.CupboardFactory;

/**
 * Created by Vishal Aroor on 16-Aug-17.
 */

public class EmployeeCupboardDBHelper extends SQLiteOpenHelper {

    Context context;
    SQLiteDatabase db;

    static {

        CupboardFactory.cupboard().register(Employee.class);
    }

    public EmployeeCupboardDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        this.context = context;
        db = getWritableDatabase();
    }

    public EmployeeCupboardDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);

        this.context = context;
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            CupboardFactory.cupboard().withDatabase(sqLiteDatabase).createTables();
        }
        catch (Exception e) {
            Log.e("CupboardDBHelper", "Caught Exception in onCreate  ..." + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public long saveEmployee(Employee employee) {

        try {
            long l = CupboardFactory.cupboard().withDatabase(db).put(employee);
            return l;
        }
        catch (Exception e) {

            Log.e("CupboardDBHelper", "Caught Exception in saveEmployee ..." + e.getMessage());
            e.printStackTrace();
        }

        return -1;
    }

    public Employee findEmployee(Employee employee) {

        try {
            Employee result = CupboardFactory.cupboard().withDatabase(db).get(employee);
            return result;
        }
        catch (Exception e) {

            Log.e("CupboardDBHelper", "Caught Exception in findEmployee ..." + e.getMessage());
            e.printStackTrace();
        }

        return  null;
    }

    public long updateEmployee(Employee employee) {

        try {
            ContentValues cv = new ContentValues();
            long l = CupboardFactory.cupboard().withDatabase(db).put(employee);
            return l;
        }
        catch (Exception e) {

            Log.e("CupboardDBHelper", "Caught Exception in updateEmployee ..." + e.getMessage());
            e.printStackTrace();
        }

        return -1;
    }


    public boolean deleteEmployee(Employee employee) {

        try {
            boolean b = CupboardFactory.cupboard().withDatabase(db).delete(employee);
            return b;
        }
        catch (Exception e) {

            Log.e("CupboardDBHelper", "Caught Exception in deleteEmployee ..." + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public List<Employee> fetchAll() {

        //Cursor c = db.query("emp", new String[] {"_id, name, age"}, null, null, null, null, null);

        //if we don't have a primary key column with the name _id and instead if it was empid
        //Cursor c1 = db.rawQuery("select empid _id, * from emp", null);

        try {
            List<Employee> employees = CupboardFactory.cupboard().withDatabase(db).query(Employee.class).list();
            return employees;
        }
        catch (Exception e) {

            Log.e("CupboardDBHelper", "Caught Exception in fetchAll ..." + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }



}
