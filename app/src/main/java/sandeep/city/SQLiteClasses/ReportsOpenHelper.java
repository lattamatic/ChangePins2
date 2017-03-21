package sandeep.city.SQLiteClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sandeep_chi on 3/8/2017.
 */

public class ReportsOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "changepins";
    private static final int DATABASE_VERSION = 1;
    public static final String REPORTS_TABLE = "reports_table";
    public static final String REPORT_ID = "report_id";
    public static final String REPORT_CATEGORY = "category";
    public static final String REPORT_TITLE = "report_title";
    public static final String REPORT_DESCRIPTION = "report_description";
    public static final String REPORT_IMAGE_PATH = "image_path";
    public static final String REPORT_PLACE_ID = "place_id";

    private static final String CREATE_REPORTS = "create table " + REPORTS_TABLE + " ( " +
            REPORT_ID + " integer primary key autoincrement, " +
            REPORT_CATEGORY + " text not null, " +
            REPORT_TITLE + " text not null, " +
            REPORT_DESCRIPTION + " text not null, " +
            REPORT_IMAGE_PATH + " text not null, " +
            REPORT_PLACE_ID + " integer)";

    public ReportsOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_REPORTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + REPORTS_TABLE);
        onCreate(sqLiteDatabase);
    }
}
