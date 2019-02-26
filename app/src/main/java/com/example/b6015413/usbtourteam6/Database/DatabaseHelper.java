package com.example.b6015413.usbtourteam6.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.b6015413.usbtourteam6.Table_Models.Room;
import com.example.b6015413.usbtourteam6.Table_Models.Tutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;


    private static final String DB_NAME = "TourSys.db";

//    "Staff(staffId, firstName, lastName, room)",
//    "Room(rName, level, prevRoom, coords, description)",
//    "Route(from, to, route)"

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        // uncomment to re-read data in
        onUpgrade(getWritableDatabase(), 1, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `Staff`(`staffID` INTEGER, `lastName` TEXT, `firstName` TEXT, `rName` TEXT, " +
                "PRIMARY KEY(`staffID`));");

        db.execSQL("CREATE TABLE `Room`(`rName` TEXT, `level` INTEGER, `prevRoom` TEXT, `coords` TEXT, `description` TEXT, " +
                "PRIMARY KEY(rName));");

        db.execSQL("CREATE TABLE `Route`(`from` TEXT, `to` TEXT, route TEXT, " +
                "PRIMARY KEY(`from`, `to`));");

        // helper class to get data from text files
        insertData(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade to new version by dropping all tables and re-creating
        db.execSQL("DROP TABLE IF EXISTS `Staff`;");
        db.execSQL("DROP TABLE IF EXISTS `Room`;");
        db.execSQL("DROP TABLE IF EXISTS `Route`;");
        onCreate(db);
    }

    // Function get all tutors
    public List<Tutor> getTutors() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"staffID", "firstName", "lastName", "rName"};
        String tableName = "Staff";

        qb.setTables(tableName);
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);
        List<Tutor> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Tutor tutor = new Tutor();
                tutor.setId(cursor.getInt(cursor.getColumnIndex("staffID")));
                tutor.setFirstname(cursor.getString(cursor.getColumnIndex("firstName")));
                tutor.setSurname(cursor.getString(cursor.getColumnIndex("lastName")));
                tutor.setRoom(cursor.getString(cursor.getColumnIndex("rName")));
                result.add(tutor);
            } while (cursor.moveToNext());
        }
        return result;
    }

    // Function get all tutor's name
    public List<String> getNames() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"firstName", "lastName"}; // col
        String tableName = "Staff"; //Table name

        qb.setTables(tableName);
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);
        List<String> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(cursor.getColumnIndex("firstName")) + " " +
                        cursor.getString(cursor.getColumnIndex("lastName")));
            } while (cursor.moveToNext());
        }
        return result;
    }

    // Function get tutor by name and surname
    public List<Tutor> getTutorByName(String name) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"staffID", "firstName", "lastName", "rName"};
        String tableName = "Staff";

        qb.setTables(tableName);
        //Query select from the tutor where Name LIKE %pattern% so not exact
        Cursor cursor = qb.query(db, sqlSelect, "Name LIKE ? OR Surname LIKE ?", new String[]{"%" + name + "%", "%" + name + "%"}, null, null, null);
        List<Tutor> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Tutor tutor = new Tutor();
                tutor.setId(cursor.getInt(cursor.getColumnIndex("staffID")));
                tutor.setFirstname(cursor.getString(cursor.getColumnIndex("firstName")));
                tutor.setSurname(cursor.getString(cursor.getColumnIndex("lastName")));
                tutor.setRoom(cursor.getString(cursor.getColumnIndex("rName")));
                result.add(tutor);
            } while (cursor.moveToNext());
        }
        return result;
    }


    private void insertData(SQLiteDatabase db) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("Tutors.txt")));

            // read the file
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                db.execSQL("INSERT INTO `Staff` VALUES (" + mLine + ");");
            }

            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("Room.txt")));

            // read the file
            while ((mLine = reader.readLine()) != null) {
                db.execSQL("INSERT INTO `Room` VALUES (" + mLine + ");");
            }

        } catch (IOException e) {
            // fail quietly
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // fail quietly
                }
            }
        }
    }

    public List<Room> getAllRooms() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Room WHERE rName NOT LIKE ? OR rName NOT LIKE ?", new String[]{"'%lift'", "'%stair'"});
        Room room;
        List<Room> roomList = new ArrayList<>();

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            room = new Room(cursor.getString(0), cursor.getInt(1), cursor.getString(2)
                    , cursor.getString(3), cursor.getString(4));
            roomList.add(room);
            cursor.moveToNext();
        }
        cursor.close();
        return roomList;
    }


    public String getRoute(String routeFrom, String routeTo, boolean sfa, SQLiteDatabase db) {
//        not yet implemented
        return null;
    }

}