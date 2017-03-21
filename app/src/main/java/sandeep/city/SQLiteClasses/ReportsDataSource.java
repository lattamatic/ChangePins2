package sandeep.city.SQLiteClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import sandeep.city.POJO.SingleReport;


/**
 * Created by sandeep_chi on 3/8/2017.
 */

public class ReportsDataSource {

    private SQLiteDatabase database;
    private ReportsOpenHelper helper;
    private String[] allColumns = {ReportsOpenHelper.REPORT_ID, ReportsOpenHelper.REPORT_CATEGORY,
            ReportsOpenHelper.REPORT_TITLE, ReportsOpenHelper.REPORT_DESCRIPTION,
            ReportsOpenHelper.REPORT_IMAGE_PATH, ReportsOpenHelper.REPORT_PLACE_ID};

    public ReportsDataSource(Context context) {
        helper = new ReportsOpenHelper(context);
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public SingleReport createReport(String category, String title, String description, String image_path) {
        ContentValues values = new ContentValues();
        values.put(ReportsOpenHelper.REPORT_CATEGORY, category);
        values.put(ReportsOpenHelper.REPORT_TITLE, title);
        values.put(ReportsOpenHelper.REPORT_DESCRIPTION, description);
        values.put(ReportsOpenHelper.REPORT_IMAGE_PATH, image_path);

        long insertId = database.insert(ReportsOpenHelper.REPORTS_TABLE, null,
                values);
        Cursor cursor = database.query(ReportsOpenHelper.REPORTS_TABLE,
                allColumns, ReportsOpenHelper.REPORT_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        SingleReport newReport = cursorToReport(cursor);
        cursor.close();
        return newReport;
    }

    public SingleReport updateReport(long id, String category, String title, String description, String image_path) {
        ContentValues values = new ContentValues();

        values.put(ReportsOpenHelper.REPORT_CATEGORY, category);
        values.put(ReportsOpenHelper.REPORT_TITLE, title);
        values.put(ReportsOpenHelper.REPORT_DESCRIPTION, description);
        values.put(ReportsOpenHelper.REPORT_IMAGE_PATH, image_path);

        database.update(ReportsOpenHelper.REPORTS_TABLE, values, ReportsOpenHelper.REPORT_ID + "=" + id, null);

        Cursor cursor = database.query(ReportsOpenHelper.REPORTS_TABLE,
                allColumns, ReportsOpenHelper.REPORT_ID + " = " + id, null,
                null, null, null);
        cursor.moveToFirst();
        SingleReport updatedReport = cursorToReport(cursor);
        cursor.close();

        return updatedReport;
    }

    public List<SingleReport> getAllReports() {
        List<SingleReport> allReports = new ArrayList<SingleReport>();

        Cursor cursor = database.query(ReportsOpenHelper.REPORTS_TABLE,
                allColumns, null, null, null, null, ReportsOpenHelper.REPORT_ID + " DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SingleReport report = cursorToReport(cursor);
            allReports.add(report);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return allReports;
    }

    public SingleReport getAReport(long id){
              Cursor cursor = database.query(ReportsOpenHelper.REPORTS_TABLE, allColumns,
                      ReportsOpenHelper.REPORT_ID+" = "+id,
                null,null,null,null);
        cursor.moveToFirst();
        return cursorToReport(cursor);
    }

    private SingleReport cursorToReport(Cursor cursor) {
        SingleReport report = new SingleReport();
        report.setId(cursor.getLong(0));
        report.setCategory(cursor.getString(1));
        report.setTitle(cursor.getString(2));
        report.setDescription(cursor.getString(3));
        report.setImage_path(cursor.getString(4));
        report.setPlace_id(cursor.getLong(5));
        return report;
    }
}
