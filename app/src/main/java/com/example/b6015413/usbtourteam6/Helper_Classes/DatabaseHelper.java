package com.example.b6015413.usbtourteam6.Helper_Classes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.b6015413.usbtourteam6.Table_Models.Room;
import com.example.b6015413.usbtourteam6.Table_Models.Route;
import com.example.b6015413.usbtourteam6.Table_Models.Tutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String DB_NAME = "TourSys.db";

//    "Tutor(tutorId, firstName, lastName, rName)",
//    "Room(rName, level, prevRoom, coords, description)",
//    "Route(rFrom, rTo, route)"

    // region set up database

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        // uncomment to re-read data in
        onUpgrade(getWritableDatabase(), 1, 1);
    }

    // sets up tables
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `Tutor`(`tutorID` INTEGER, `lastName` TEXT, `firstName` TEXT, `rName` TEXT, " +
                "PRIMARY KEY(`tutorID`));");

        db.execSQL("CREATE TABLE `Room`(`rName` TEXT, `level` INTEGER, `prevRoom` TEXT, `coords` TEXT, `description` TEXT, " +
                "PRIMARY KEY(rName));");

        db.execSQL("CREATE TABLE `Route`(`rFrom` TEXT, `rTo` TEXT, route TEXT, " +
                "PRIMARY KEY(`rFrom`, `rTo`));");

        // helper class to get data from text files
        insertData(db);
    }

    // used when db needs to be updated
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade to new version by dropping all tables and re-creating
        db.execSQL("DROP TABLE IF EXISTS `Tutor`;");
        db.execSQL("DROP TABLE IF EXISTS `Room`;");
        db.execSQL("DROP TABLE IF EXISTS `Route`;");
        // TODO this next line needs to be deleted when everyone has updated
        db.execSQL("DROP TABLE IF EXISTS `Staff`;");
        onCreate(db);
    }

    // private helper method of onUpgrade to insert data from text files into table
    private void insertData(SQLiteDatabase db) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open("Tutors.txt")));

            // read the file
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                db.execSQL("INSERT INTO `Tutor` VALUES (" + mLine + ");");
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
    // endregion

    // region methods to return results from the database

    // returns list of get all tutors
    public List<Tutor> getTutors() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"tutorID", "firstName", "lastName", "rName"};
        String tableName = "Tutor";

        qb.setTables(tableName);
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);
        List<Tutor> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Tutor tutor = new Tutor();
                tutor.setId(cursor.getInt(cursor.getColumnIndex("tutorID")));
                tutor.setFirstname(cursor.getString(cursor.getColumnIndex("firstName")));
                tutor.setSurname(cursor.getString(cursor.getColumnIndex("lastName")));
                tutor.setRoom(cursor.getString(cursor.getColumnIndex("rName")));
                result.add(tutor);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return result;
    }

    // Function get all tutor's name
    public List<String> getNames() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"firstName", "lastName"}; // col
        String tableName = "Tutor"; //Table name

        qb.setTables(tableName);
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);
        List<String> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(cursor.getColumnIndex("firstName")) + " " +
                        cursor.getString(cursor.getColumnIndex("lastName")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return result;
    }

    // Function get tutor by name or surname
    public List<Tutor> getTutorByName(String name) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"tutorID", "firstName", "lastName", "rName"};
        String tableName = "Tutor";

        qb.setTables(tableName);
        //Query select from the tutor where Name LIKE %pattern% so not exact
        name = removeSpace(name);
        Cursor cursor = qb.query(db, sqlSelect, "firstName LIKE ? OR lastName LIKE ?", new String[]{"%" + name + "%", "%" + name + "%"}, null, null, null);
        List<Tutor> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Tutor tutor = new Tutor();
                tutor.setId(cursor.getInt(cursor.getColumnIndex("tutorID")));
                tutor.setFirstname(cursor.getString(cursor.getColumnIndex("firstName")));
                tutor.setSurname(cursor.getString(cursor.getColumnIndex("lastName")));
                tutor.setRoom(cursor.getString(cursor.getColumnIndex("rName")));
                result.add(tutor);
            } while (cursor.moveToNext());
        }
        cursor.close();
        result.addAll(getTutorByFullName(name, db));
        db.close();
        return result;
    }

    // splits name into parts based on space location then searches for a match on firstname AND lastname
    // tutors MUST have no spaces in last name, middle names must be part of firstname (in database)
    private List<Tutor> getTutorByFullName(String name, SQLiteDatabase db) {
        name = removeSpace(name);
        List<Tutor> result = new ArrayList<>();
        String[] split = name.split(" ");
        String[] firstLast = new String[]{"", ""};

        if (split.length < 2) return result;

        for (int i = 0; i < split.length; i++) {
            firstLast[(i == split.length - 1) ? 1 : 0] += split[i];
        }

        Cursor cursor = db.rawQuery("SELECT * FROM Tutor WHERE firstName LIKE ? AND lastName LIKE ?",
                new String[]{"%" + firstLast[0] + "%", "%" + firstLast[1] + "%"});
        if (cursor.moveToFirst()) {
            do {
                Tutor tutor = new Tutor();
                tutor.setId(cursor.getInt(cursor.getColumnIndex("tutorID")));
                tutor.setFirstname(cursor.getString(cursor.getColumnIndex("firstName")));
                tutor.setSurname(cursor.getString(cursor.getColumnIndex("lastName")));
                tutor.setRoom(cursor.getString(cursor.getColumnIndex("rName")));
                result.add(tutor);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    // Function returns list of all rooms that are not stairs or lifts
    public List<Room> getAllRooms() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Room", null);
        Room room;
        List<Room> roomList = new ArrayList<>();

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            room = new Room(
                    cursor.getString(cursor.getColumnIndex("rName")),
                    cursor.getInt(cursor.getColumnIndex("level")),
                    cursor.getString(cursor.getColumnIndex("prevRoom")),
                    cursor.getString(cursor.getColumnIndex("coords")),
                    cursor.getString(cursor.getColumnIndex("description"))
            );

            // find a way to do this with the query
            if (!(room.getName().contains("stair") || room.getName().contains("lift"))) {
                roomList.add(room);
            }
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return roomList;
    }

    // function return list of all rooms on a given level
    public List<Room> getRoomsOnLevel(int level) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Room WHERE level = ?", new String[]{"" + level});
        Room room;
        List<Room> roomList = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            room = new Room(
                    cursor.getString(cursor.getColumnIndex("rName")),
                    cursor.getInt(cursor.getColumnIndex("level")),
                    cursor.getString(cursor.getColumnIndex("prevRoom")),
                    cursor.getString(cursor.getColumnIndex("coords")),
                    cursor.getString(cursor.getColumnIndex("description"))
            );
            if (!(room.getName().contains("stair") || room.getName().contains("lift"))) {
                roomList.add(room);
            }
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return roomList;
    }

    // returns list of all rooms that are not tutor rooms or study spaces on a level
    public List<Room> getOtherRoomsOnLevel(int level) {
        List<Room> roomList = getRoomsOnLevel(level);
        roomList.removeAll(getStudySpacesOnLevel(level));
        roomList.removeAll(getTutorRoomsOnLevel(level));
        return roomList;
    }

    // returns list of all tutor rooms on a level
    public List<Room> getTutorRoomsOnLevel(int level) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Room.* FROM Room, Tutor WHERE  Tutor.rName = room.rName AND level = ?", new String[]{"" + level});
        Room room;
        List<Room> roomList = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            room = new Room(
                    cursor.getString(cursor.getColumnIndex("rName")),
                    cursor.getInt(cursor.getColumnIndex("level")),
                    cursor.getString(cursor.getColumnIndex("prevRoom")),
                    cursor.getString(cursor.getColumnIndex("coords")),
                    cursor.getString(cursor.getColumnIndex("description"))
            );
            if (!(room.getName().contains("stair") || room.getName().contains("lift"))) {
                roomList.add(room);
            }
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return roomList;
    }

    // returns list of all tutors on a given level
    public List<Tutor> getTutorOnLevel(int level) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  Tutor.* FROM Tutor, Room WHERE Tutor.rName = room.rName AND room.level = ?", new String[]{"" + level});
        List<Tutor> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Tutor tutor = new Tutor();
                tutor.setId(cursor.getInt(cursor.getColumnIndex("tutorID")));
                tutor.setFirstname(cursor.getString(cursor.getColumnIndex("firstName")));
                tutor.setSurname(cursor.getString(cursor.getColumnIndex("lastName")));
                tutor.setRoom(cursor.getString(cursor.getColumnIndex("rName")));
                result.add(tutor);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return result;
    }

    // returns list of study spaces on a level. defined as containing 'study space' in the description
    public List<Room> getStudySpacesOnLevel(int level) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Room WHERE level = ? AND description LIKE ?", new String[]{"" + level, "%study space%"});
        Room room;
        List<Room> roomList = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            room = new Room(
                    cursor.getString(cursor.getColumnIndex("rName")),
                    cursor.getInt(cursor.getColumnIndex("level")),
                    cursor.getString(cursor.getColumnIndex("prevRoom")),
                    cursor.getString(cursor.getColumnIndex("coords")),
                    cursor.getString(cursor.getColumnIndex("description"))
            );
            if (!(room.getName().contains("stair") || room.getName().contains("lift"))) {
                roomList.add(room);
            }
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return roomList;
    }

    // method to return list of route objects for a given route between room names
    public List<Route> getRoute(String routeFrom, String routeTo, boolean sfa) {
        Room from = getRoomByName(routeFrom),
                to = getRoomByName(routeTo);

        if (from == null || to == null) throw new IllegalArgumentException("Room is not valid");
        return getRoute(from, to, sfa);
    }

    // method to return list of route objects for a given route of route objects
    public List<Route> getRoute(Room routeFrom, Room routeTo, boolean sfa) {
        SQLiteDatabase db = getReadableDatabase();
        List<Room> btcFrom = backtrack(routeFrom, db);
        List<Room> btcTo = backtrack(routeTo, db);
        List<Route> route;

        if (routeFrom.getLevel() == routeTo.getLevel()) {
            Room ecn = ECN(btcFrom, btcTo);
            route = routeNav(btcFrom, ecn, "LR", db);
            route.addAll(routeNav(btcTo, ecn, "RL", db));
            return route;
        } else if (sfa) {
            btcFrom.add(getRoomByName(routeFrom.getLevel() + ".lift"));
            btcTo.add(getRoomByName(routeTo.getLevel() + ".lift"));
        } else {
            btcFrom.add(getRoomByName(routeFrom.getLevel() + ".stairs"));
            btcTo.add(getRoomByName(routeTo.getLevel() + ".stairs"));
        }
        route = routeNav(btcFrom, null, "LR", db);
        route.add(new Route(routeTo.getLevel() + ".stairs", routeFrom.getLevel() + ".stairs", " travel to level " + routeTo.getLevel()));
        route.addAll(routeNav(btcTo, null, "RL", db));
        return route;
    }

    // method to return instance of room class for a given room name
    public Room getRoomByName(String rName) {
        SQLiteDatabase db = getReadableDatabase();
        rName = removeSpace(rName);
        Cursor cursor = db.rawQuery("SELECT  * FROM Room WHERE rName = ?", new String[]{rName});
        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        Room room = new Room(
                cursor.getString(cursor.getColumnIndex("rName")),
                cursor.getInt(cursor.getColumnIndex("level")),
                cursor.getString(cursor.getColumnIndex("prevRoom")),
                cursor.getString(cursor.getColumnIndex("coords")),
                cursor.getString(cursor.getColumnIndex("description"))
        );
        cursor.close();
        return room;
    }

    public int[] getRoomCoords(String rName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT coords FROM Room WHERE rName = ?", new String[]{rName});
        int[] result;


        if (cursor.moveToFirst()) {
            String[] rawCoords = cursor.getString(cursor.getColumnIndex("coords")).split(",");
            result = new int[]{Integer.valueOf(rawCoords[0]), Integer.valueOf(rawCoords[1])};
        } else {
            result = new int[]{0, 0};
        }
        cursor.close();
        db.close();
        return result;

    }
//endregion

    // region helper methods

    // private helper method to return the list of previous rooms for a given room
    private List<Room> backtrack(Room from, SQLiteDatabase db) {
        List<Room> btc = new ArrayList<Room>();
        btc.add(from);
        Room prevRoom;
        while ((prevRoom = getRoomByName(from.getPrevRoom())) != null) {
            btc.add(prevRoom);
            from = prevRoom;
        }
        return btc;
    }

    // private helper method for getRoute return earliest common route of two backtracked routes
    private Room ECN(List<Room> btcFrom, List<Room> btcTo) {
        int fromPoint = btcFrom.size() - 1;
        int toPoint = btcTo.size() - 1;

        while (fromPoint >= 0 && toPoint >= 0 && (btcFrom.get(fromPoint).equals(btcTo.get(toPoint)))) {
            fromPoint--;
            toPoint--;
        }
        return btcFrom.get(fromPoint + 1);
    }

    // private helper method for routeNav
    private Route getRouteHelper(Route route, SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT * FROM Route WHERE rFrom = ? AND rTo = ?", new String[]{route.getFrom(), route.getTo()});
        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        route.setRoute(cursor.getColumnName(cursor.getColumnIndex("")));
        cursor.close();
        return route;
    }

    // private heplper method for getRoute returns the list of routes based on parameters
    private List<Route> routeNav(List<Room> backtrack, Room ecn, String direction, SQLiteDatabase db) {
        int pointer, change;
        if (direction == "LR") {
            pointer = 0;
            change = 1;
        } else {
            pointer = backtrack.size() - 1;
            change = -1;
        }

        boolean isNavigating = backtrack.get(pointer) != ecn && pointer >= 0 && pointer < backtrack.size();
        List<Route> routes = new ArrayList<Route>();

        while (isNavigating) {
            routes.add(getRouteHelper(new Route(backtrack.get(pointer + change).getName(), backtrack.get(pointer).getName(), null), db));
        }

        return routes;
    }

    // method removes leading or trailing spaces
    private String removeSpace(String input) {
//        throw new IllegalArgumentException("'" + input +"'");
        if (input.length() == 0) return input;

        if (input.matches("[\\s]+")) return ""; // if just 1 or more spaces

        if (input.charAt(0) == ' ') input = input.substring(1);

        if (input.charAt(input.length() - 1) == ' ') input = input.substring(0, input.length() - 1);

        return input;

    }
    // endregion
}