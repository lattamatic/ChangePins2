package sandeep.city;

/**
 * Created by sandeep on 22/6/15.
 */

        import java.util.ArrayList;
        import java.util.List;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;
        import android.widget.Toast;

public class DataHelp
{
    SQLiteDatabase db;
    Context  context;
    ArrayList<SinglePlace> places = new ArrayList<SinglePlace>() ;
    ArrayList<SingleReport> reports = new ArrayList<SingleReport>();
    SingleReport report = new SingleReport();
    ArrayList<SingleGroup> groups = new ArrayList<SingleGroup>();
    int id;

    public DataHelp(Context con)
    {
        this.context = con;
        SQLiteOpenHelper myHelper = new MyOpenHelper(this.context);
        this.db = myHelper.getWritableDatabase();
    }


    public void place_insert(String name, double lat, double lon, String locAdd){
        ContentValues conV = new ContentValues();
        conV.put("name",name);
        conV.put("lat",lat);
        conV.put("lon",lon);
        conV.put("locAdd",locAdd);

        long newrow = db.insert(MyOpenHelper.MY_PLACES,null,conV);
        if(newrow >= 0)
        {
            mToast("Success!");
        }
        else
        {
            mToast("Failed. Try again!");
        }

    }


    public void report_insert(String title,String desc,String img_link, double lat, double lon, String locAdd,String category){
        ContentValues conV = new ContentValues();
        conV.put("title",title);
        conV.put("desc",desc);
        conV.put("img",img_link);
        conV.put("lat",lat);
        conV.put("lon",lon);
        conV.put("locAdd",locAdd);
        conV.put("category",category);

        long newrow = db.insert(MyOpenHelper.MY_REPORTS,null,conV);
        if(newrow >= 0)
        {
            mToast("Successfully reported!");
        }
        else
        {
            mToast("Failed. Try again!");
        }

    }

    public void group_insert(String title,String place, String date, int no_of_vols, int no_of_hours,String img_link, int status,int group_id, String comments){
        ContentValues conV = new ContentValues();
        conV.put("group_id",group_id);
        conV.put("title",title);
        conV.put("place",place);
        conV.put("date",date);
        conV.put("no_of_vols",no_of_vols);
        conV.put("no_of_hours",no_of_hours);
        conV.put("status",status);
        conV.put("comments",comments);
        conV.put("img",img_link);

        long newrow = db.insert(MyOpenHelper.GROUPS,null,conV);
        if(newrow >= 0)
        {
            Log.d("group_insertion", "success");
        }
        else
        {
            Log.d("group_insertion", "failure");
        }

    }

    public void group_delete()
    {

        db.execSQL("DROP TABLE IF EXISTS " + MyOpenHelper.GROUPS);
        db.execSQL("CREATE TABLE groups(group_id INTEGER, title TEXT, place TEXT, date TEXT, no_of_vols INT, no_of_hours INT, status INT, comments TEXT, img TEXT)");
    }



    public void place_delete(int id)
    {
        String where = "place_id = "+id;
        int newrow = db.delete(MyOpenHelper.MY_PLACES, where, null);
        System.out.println(newrow);

        if(newrow == 0)
        {
            mToast("Failed to remove.");

        }
        else
        {
            mToast("Removed!");
        }
        getPlaces();
    }

    public void report_delete(int id)
    {
        String where = "report_id = "+id;
        int newrow = db.delete(MyOpenHelper.MY_REPORTS, where, null);
        System.out.println(newrow);

        if(newrow == 0)
        {
            mToast("Failed to remove");

        }
        else
        {
            mToast("Removed");
        }
        getReports();
    }

    public void report_update(int id, String title,String desc, double lat, double lon, String locAdd){
        ContentValues conV = new ContentValues();
        conV.put("report_id",id);
        conV.put("desc",desc);
//        conV.put("img",img_link);

        conV.put("title",title);
        conV.put("lat",lat);
        conV.put("lon",lon);
        conV.put("locAdd",locAdd);
        String where = "report_id = "+id;
        int updatedRow =db.update(MyOpenHelper.MY_REPORTS, conV, where, null);
        if(updatedRow == 0)
        {
            mToast("Data is not updated");
        }
        else
        {
            mToast("Data is updated");
        }
    }

    public ArrayList<SingleReport> getReports() {


        reports.clear();
        Cursor c = db.rawQuery("SELECT * FROM myReports",null);

        if(c!= null)
        {
            if(c.moveToFirst())
            {
                do
                {
                    int id = c.getInt(c.getColumnIndex("report_id"));
                    String title= c.getString(c.getColumnIndex("title"));
                    String desc = c.getString(c.getColumnIndex("desc"));
                    String img = c.getString(c.getColumnIndex("img"));
                    double lat = c.getDouble(c.getColumnIndex("lat"));
                    double lon = c.getDouble(c.getColumnIndex("lon"));
                    String locAdd = c.getString(c.getColumnIndex("locAdd"));
                    String category = c.getString(c.getColumnIndex("category"));
                    reports.add(new SingleReport(id,title,desc,img,lat,lon,locAdd,category));
                }
                while(c.moveToNext());
            }
        }
        else
        {
            mToast("Table as No contain");
        }
        c.close();
        //	dbClose(db);
        return reports ;
    }

    public SingleReport getaReport(int id) {


        reports.clear();
        Cursor c = db.rawQuery("SELECT * FROM myReports where report_id='"+id+"'",null);

        if(c!= null)
        {
            if(c.moveToFirst())
            {
                do
                {
//                    int ids = c.getInt(c.getColumnIndex("report_id"));
                    String title= c.getString(c.getColumnIndex("title"));
                    String desc = c.getString(c.getColumnIndex("desc"));
                    String img = c.getString(c.getColumnIndex("img"));
                    double lat = c.getDouble(c.getColumnIndex("lat"));
                    double lon = c.getDouble(c.getColumnIndex("lon"));
                    String locAdd = c.getString(c.getColumnIndex("locAdd"));
                    String category = c.getString(c.getColumnIndex("category"));
                    report = new SingleReport(id,title,desc,img,lat,lon,locAdd,category);
                }
                while(c.moveToNext());
            }
        }
        else
        {
            mToast("Table as No contain");
        }
        c.close();
        //	dbClose(db);
        return report ;
    }

    public ArrayList<SingleGroup> getGroups() {


        groups.clear();
        Cursor c = db.rawQuery("SELECT * FROM groups",null);

        if(c!= null)
        {
            if(c.moveToFirst())
            {
                do
                {
                    int id = c.getInt(c.getColumnIndex("group_id"));
                    String title= c.getString(c.getColumnIndex("title"));
                    String place = c.getString(c.getColumnIndex("place"));
                    String date = c.getString(c.getColumnIndex("date"));
                    int status = c.getInt(c.getColumnIndex("status"));
                    int no_of_vols=c.getInt(c.getColumnIndex("no_of_vols"));
                    int no_of_hours = c.getInt(c.getColumnIndex("no_of_hours"));
                    String comments = c.getString(c.getColumnIndex("comments"));

                    groups.add(new SingleGroup(id,no_of_hours,no_of_vols,status,title,place,date,comments));
                }
                while(c.moveToNext());
            }
        }
        else
        {
            Toast.makeText(context,"Table has nothing",Toast.LENGTH_SHORT).show();
        }
        c.close();

        //	dbClose(db);
        return groups ;
    }

    public ArrayList<SinglePlace> getPlaces()
    {
        places.clear();
        Cursor c = db.rawQuery("SELECT * FROM myPlaces",null);

        if(c!= null)
        {
            if(c.moveToFirst())
            {
                do
                {
                    int id = c.getInt(c.getColumnIndex("place_id"));
                    String name= c.getString(c.getColumnIndex("name"));
                    double lat = c.getDouble(c.getColumnIndex("lat"));
                    double lon = c.getDouble(c.getColumnIndex("lon"));
                    String locAdd = c.getString(c.getColumnIndex("locAdd"));
                    places.add(new SinglePlace(context,id,name,lat,lon,locAdd));
                }
                while(c.moveToNext());
            }
        }
        else
        {
            mToast("Table as No contain");
        }
        c.close();
        return places ;
    }





    private void mToast(String text)
    {
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();

    }
    public void dbClose(SQLiteDatabase db)
    {
        db.close();
    }

    public void place_update(int id,String name, double latitude, double longitude, String locationAddress) {
        ContentValues conV = new ContentValues();
		conV.put("place_id",id);
        conV.put("name",name);
        conV.put("lat",latitude);
        conV.put("lon",longitude);
        conV.put("locAdd",locationAddress);
		String where = "place_id = "+id;
		int newrow =db.update(MyOpenHelper.MY_PLACES, conV, where, null);
		if(newrow == 0)
		{
			mToast("Data is not updated");

		}
		else
		{
			mToast("Data is updated");
		}
    }

    public void group_update(String title,String place, String date, int no_of_vols, int no_of_hours,String img_link, int group_id,int status, String comments) {
        ContentValues conV = new ContentValues();
        conV.put("group_id",group_id);
        conV.put("title",title);
        conV.put("place",place);
        conV.put("date",date);
        conV.put("no_of_vols",no_of_vols);
        conV.put("no_of_hours",no_of_hours);
        conV.put("status",status);
        conV.put("comments",comments);
        conV.put("img",img_link);

        String where = "group_id = "+group_id;

        Log.d("check",group_id+" or "+status);

        int newrow =db.update(MyOpenHelper.GROUPS, conV, where, null);
        if(newrow == 0)
        {
            Log.d("reading comments","Failed");

        }
        else
        {
            Log.d("reading comments","Success");
        }
    }
}