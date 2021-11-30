package com.meettechlab.m_expense;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import androidx.annotation.Nullable;

import com.meettechlab.m_expense.models.ModelDatabase;
import com.meettechlab.m_expense.models.ModelName;

import java.util.ArrayList;
import java.util.List;

public class dbmanager extends SQLiteOpenHelper {

    private static final String dbname = "dbcontact";

    public dbmanager(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qry = "create table tbl_contact ( trip_id integer primary key autoincrement, name text, destination text, date text, risk text, description text)";
        sqLiteDatabase.execSQL(qry);
        String qry2 = "create table tbl_expense ( expense_id integer primary key autoincrement, type text, amount text, time text, comment text, trip_id integer, CONSTRAINT fk_tbl_contact FOREIGN KEY (trip_id) REFERENCES tbl_contact (trip_id) ON DELETE CASCADE ON UPDATE CASCADE )";
        sqLiteDatabase.execSQL(qry2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String qry = "DROP TABLE IF EXISTS tbl_contact";
        sqLiteDatabase.execSQL(qry);
        String qry2 = "DROP TABLE IF EXISTS tbl_expense";
        sqLiteDatabase.execSQL(qry2);
        onCreate(sqLiteDatabase);
    }


    public String addexpense(String type,String amount, String time, String comment, Integer trip_id ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("type", type);
        cv.put("amount", amount);
        cv.put("time", time);
        cv.put("comment", comment);
        cv.put("trip_id", trip_id);
        float res = db.insert("tbl_expense", null, cv);

        if (res == -1)
            return "Failed";
        else
            return "Successfully inserted";

    }

    public String addrecord(String name, String destination, String date, String risk, String description) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("destination", destination);
        cv.put("date", date);
        cv.put("risk", risk);
        cv.put("description",description);
        float res = db.insert("tbl_contact", null, cv);

        if (res == -1)
            return "Failed";
        else
            return "Successfully inserted";

    }

    public String updaterecord(Integer id, String name, String destination, String date, String risk, String description) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("destination", destination);
        cv.put("date", date);
        cv.put("risk", risk);
        cv.put("description",description);
        Cursor cursor = db.rawQuery("select * from tbl_contact where trip_id = ?",new String[]{String.valueOf(id)});
        if(cursor.getCount()>0) {
            float res = db.update("tbl_contact", cv, "trip_id = ?", new String[]{String.valueOf(id)});

            if (res == -1)
                return "Failed";
            else
                return "Successfully updated";
        }else return "Failed";
    }


    public String delrecord(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tbl_contact where trip_id = ?",new String[]{String.valueOf(id)});
        if(cursor.getCount()>0) {
            float res = db.delete("tbl_contact", "trip_id = ?", new String[]{String.valueOf(id)});
            if (res == -1)
                return "Failed";
            else
                return "Successfully deleted";
        }else return "Failed";

    }

    public String delallrecord() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tbl_contact",null);
        if(cursor.getCount()>0) {
            float res = db.delete("tbl_contact", null,null);


            if (res == -1)
                return "Failed";
            else
                return "Successfully deleted";
        }else return "Failed";

    }

    public String delallexpenserecord(Integer tripID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tbl_expense where trip_id = ?",new String[]{String.valueOf(tripID)});
        if(cursor.getCount()>0) {
            float res = db.delete("tbl_expense", "trip_id = ?",new String[]{String.valueOf(tripID)});

            if (res == -1)
                return "Failed";
            else
                return "Successfully deleted";
        }else return "Failed";

    }

    public Cursor readalldata() {
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "select * from tbl_contact order by trip_id desc";
        Cursor cursor = db.rawQuery(qry, null);
        return cursor;
    }

    public Cursor readsingledata(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "select * from tbl_contact where trip_id = ?";
        Cursor cursor = db.rawQuery(qry, new String[]{String.valueOf(id)});
        return cursor;
    }

    public Cursor readallexpensedata(Integer tripID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "select * from tbl_expense where trip_id = ? order by trip_id desc";
        Cursor cursor = db.rawQuery(qry, new String[]{String.valueOf(tripID)});
        return cursor;
    }
    public Cursor searchbyname(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "select * from tbl_contact where name = ? like 'a__%' order by trip_id desc";
        Cursor cursor = db.rawQuery(qry, new String[]{name});
        return cursor;
    }


    public Cursor dynamicsearchbyname(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] sqlSelect = {"trip_id","name","destination","date","risk","description"};
        //String qry = "select * from tbl_contact where name = ? like 'a__%' order by trip_id desc";
        Cursor cursor = db.query("tbl_contact",sqlSelect,"name like ?",new String[]{"%" + name + "%"},null,null,null);
        return cursor;
    }


    public List<ModelDatabase> getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"trip_id","name","destination","date","risk","description"};
        String tableName = "tbl_contact";
        qb.setTables(tableName);
        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,null);
        List<ModelDatabase> result = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                ModelDatabase modelDatabase = new ModelDatabase();
                modelDatabase.setTrip_id(cursor.getInt(cursor.getColumnIndex("trip_id")));
                modelDatabase.setName(cursor.getString(cursor.getColumnIndex("name")));
                modelDatabase.setDestination(cursor.getString(cursor.getColumnIndex("destination")));
                modelDatabase.setDate(cursor.getString(cursor.getColumnIndex("date")));
                modelDatabase.setRisk(cursor.getString(cursor.getColumnIndex("risk")));
                modelDatabase.setDescription(cursor.getString(cursor.getColumnIndex("description")));

                result.add(modelDatabase);
            }while (cursor.moveToNext());
        }
        return result;
    }


    public List<ModelName> getAllNames(){
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"trip_id","name",};
        String tableName = "tbl_contact";
        qb.setTables(tableName);
        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,null);
        List<ModelName> result = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                ModelName modelName = new ModelName();
                modelName.setId(cursor.getInt(cursor.getColumnIndex("trip_id")));
                modelName.setTripName(cursor.getString(cursor.getColumnIndex("name")));
                result.add(modelName);
            }while (cursor.moveToNext());
        }
        return result;
    }


    public List<ModelName> getNameBySearch(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"trip_id","name",};
        String tableName = "tbl_contact";
        qb.setTables(tableName);
        Cursor cursor = qb.query(db,sqlSelect,"name like ?",new String[]{"%"+name+"%"},null,null,null);
        List<ModelName> result = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                ModelName modelName = new ModelName();
                modelName.setId(cursor.getInt(cursor.getColumnIndex("trip_id")));
                modelName.setTripName(cursor.getString(cursor.getColumnIndex("name")));
                result.add(modelName);
            }while (cursor.moveToNext());
        }
        return result;
    }


}


