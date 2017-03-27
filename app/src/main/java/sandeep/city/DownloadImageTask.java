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

    DownloadImage downloadImage;

    public interface DownloadImage{
        void onImageDownloadCompleted(Bitmap result);
        void onImageDownloadStart();
    }

    public DownloadImageTask(Activity activity) {
        downloadImage = (DownloadImage) activity;
    }

    @Override
    protected void onPreExecute() {
        downloadImage.onImageDownloadStart();
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
        downloadImage.onImageDownloadCompleted(result);
    }
}