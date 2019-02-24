package com.example.b6015413.usbtourteam6.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.b6015413.usbtourteam6.Model.Tutor;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME = "tutor.db";
    private static final int DB_VER=1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    // Function get all Friends
    public List<Tutor> getTutors() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Id", "Surname", "Name", "Room"};
        String tableName = "Tutors";

        qb.setTables(tableName);
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);
        List<Tutor> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Tutor tutor = new Tutor();
                tutor.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                tutor.setSurname(cursor.getString(cursor.getColumnIndex("Surname")));
                tutor.setName(cursor.getString(cursor.getColumnIndex("Name")));
                tutor.setRoom(cursor.getString(cursor.getColumnIndex("Room")));
                result.add(tutor);
            } while (cursor.moveToNext());
        }
        return result;
    }

    // Function get all tutor's name
    public List<String> getNames()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Name"}; // col
        String tableName = "Tutors"; //Table name

        qb.setTables(tableName);
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);
        List<String> result = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do {
                result.add(cursor.getString(cursor.getColumnIndex("Name")));
            } while (cursor.moveToNext());
        }
        return result;
    }

    // Function get tutor by name
    public List<Tutor> getTutorByName(String name)
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Id", "Surname", "Name", "Room"};
        String tableName = "Tutors";

        qb.setTables(tableName);
        //Query select from the tutor where Name LIKE %pattern% so not exact
        Cursor cursor = qb.query(db, sqlSelect, "Name LIKE ?", new String[]{"%"+name+"%"}, null, null, null);
        List<Tutor> result = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do{
                Tutor tutor = new Tutor();
                tutor.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                tutor.setSurname(cursor.getString(cursor.getColumnIndex("Surname")));
                tutor.setName(cursor.getString(cursor.getColumnIndex("Name")));
                tutor.setRoom(cursor.getString(cursor.getColumnIndex("Room")));
                result.add(tutor);
            } while (cursor.moveToNext());
        }
        return result;
    }
}


