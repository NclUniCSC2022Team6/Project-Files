package com.example.b6015413.usbtourteam6;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.b6015413.usbtourteam6.Helper_Classes.DatabaseHelper;
import com.example.b6015413.usbtourteam6.Table_Models.Room;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void testAllRoutes() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DatabaseHelper dbh = new DatabaseHelper(appContext);
        List<Room> rooms = dbh.getAllRooms();
        List<Exception> errorsFound = new ArrayList<>();

        for (int i = 0; i < rooms.size(); i++) {
            for (int x = 0; x < rooms.size(); x++) {
                String from = rooms.get(i).getName(), to = rooms.get(x).getName();

                if (i != x) {
                    try {
                        dbh.getRoute(from, to, false);
                    } catch (Exception e) {
                        errorsFound.add(e);
                    }
                    try {
                        dbh.getRoute(from, to, true);
                    } catch (Exception e) {
                        errorsFound.add(e);
                    }
                }
            }
        }
        if (errorsFound.size() == 0) assertTrue(true);
        fail(Arrays.toString(errorsFound.toArray()));
    }
}
