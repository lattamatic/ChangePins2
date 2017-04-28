package sandeep.city.SQLiteClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.location.places.Place;

import java.util.ArrayList;
import java.util.List;

import sandeep.city.POJO.SinglePlace;
import sandeep.city.POJO.SingleReport;

/**
 * Created by sandeep_chi on 4/4/2017.
 */

public class PlacesDataSource {
    private SQLiteDatabase database;
    private DBOpenHelper helper;
    private String[] allColumns = {DBOpenHelper.PLACE_ID, DBOpenHelper.PLACE_TITLE,
            DBOpenHelper.PLACE_ADDRESS, DBOpenHelper.PLACE_LAT, DBOpenHelper.PLACE_LONG};

    public PlacesDataSource(Context context){
        helper = new DBOpenHelper(context);
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public SinglePlace createPlace(SinglePlace place) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.PLACE_TITLE, place.getTitle());
        values.put(DBOpenHelper.PLACE_ADDRESS, place.getAddress());
        values.put(DBOpenHelper.PLACE_LAT, place.getLatitute());
        values.put(DBOpenHelper.PLACE_LONG, place.getLongitude());

        long insertId = database.insert(DBOpenHelper.PLACES_TABLE, null,
                values);
        Cursor cursor = database.query(DBOpenHelper.PLACES_TABLE,
                allColumns, DBOpenHelper.PLACE_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        SinglePlace newPlace = cursorToPlace(cursor);
        cursor.close();
        return newPlace;
    }

    public List<SinglePlace> getAllPlaces() {
        List<SinglePlace> allPlaces = new ArrayList<SinglePlace>();

        Cursor cursor = database.query(DBOpenHelper.PLACES_TABLE,
                allColumns, null, null, null, null, DBOpenHelper.PLACE_ID + " DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SinglePlace place = cursorToPlace(cursor);
            allPlaces.add(place);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return allPlaces;
    }

    public SinglePlace updatePlace(SinglePlace place) {
        ContentValues values = new ContentValues();

        values.put(DBOpenHelper.PLACE_TITLE, place.getTitle());
        values.put(DBOpenHelper.PLACE_ADDRESS, place.getAddress());
        values.put(DBOpenHelper.PLACE_LAT, place.getLatitute());
        values.put(DBOpenHelper.PLACE_LONG, place.getLongitude());

        database.update(DBOpenHelper.PLACES_TABLE, values, DBOpenHelper.PLACE_ID + "=" + place.getId(), null);

        Cursor cursor = database.query(DBOpenHelper.PLACES_TABLE,
                allColumns, DBOpenHelper.PLACE_ID + " = " + place.getId(), null,
                null, null, null);
        cursor.moveToFirst();
        SinglePlace updatedPlace = cursorToPlace(cursor);
        cursor.close();

        return updatedPlace;
    }


    public SinglePlace getAPlace(long id){
        Cursor cursor = database.query(DBOpenHelper.PLACES_TABLE, allColumns,
                DBOpenHelper.PLACE_ID+" = "+id,
                null,null,null,null);
        cursor.moveToFirst();
        return cursorToPlace(cursor);
    }

    private SinglePlace cursorToPlace(Cursor cursor) {
        SinglePlace place = new SinglePlace();
        if(cursor!=null){
            place.setId(cursor.getLong(0));
            place.setTitle(cursor.getString(1));
            place.setAddress(cursor.getString(2));
            place.setLatitute(cursor.getLong(3));
            place.setLongitude(cursor.getLong(4));
        }
        return place;
    }
}
