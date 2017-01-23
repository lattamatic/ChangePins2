package sandeep.city;

/**
 * Created by sandeep on 22/6/15.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "changePins";
    private static final int DATABASE_VERSION = 1;
    public static final String MY_PLACES = "myPlaces";
    public static final String MY_REPORTS = "myReports";
    public static final String GROUPS = "groups";

    public MyOpenHelper(Context context)
    {	// TODO Auto-generated constructor stub
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {	// TODO Auto-generated method stub
        db.execSQL("CREATE TABLE myPlaces(place_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, lat REAL, lon REAL, locAdd TEXT)");
        db.execSQL("CREATE TABLE myReports(report_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, desc TEXT, lat REAL, lon REAL, locAdd TEXT, img TEXT, category TEXT)");
        db.execSQL("CREATE TABLE groups(group_id INTEGER, title TEXT, place TEXT, date TEXT, no_of_vols INT, no_of_hours INT, status INT, comments TEXT, img TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {	// TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + MY_PLACES);
        db.execSQL("DROP TABLE IF EXISTS " + MY_REPORTS);
        db.execSQL("DROP TABLE IF EXISTS " + GROUPS);
        onCreate(db);
        System.out.println("On Upgrade call");
    }

}