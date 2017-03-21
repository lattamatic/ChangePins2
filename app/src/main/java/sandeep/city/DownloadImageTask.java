package sandeep.city;

/**
 * Created by sandeep on 27/10/15.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    ShowMapImage showMapImage;

    public interface ShowMapImage{
        void onResultsReceived(Bitmap result);
        void onDownloadStart();
    }

    public DownloadImageTask(Activity activity) {
        showMapImage = (ShowMapImage) activity;
    }

    @Override
    protected void onPreExecute() {
        showMapImage.onDownloadStart();
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        showMapImage.onResultsReceived(result);
    }
}