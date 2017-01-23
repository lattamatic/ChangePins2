package sandeep.city;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sandeep on 1/3/15.
 */
public class NewCustomPagerAdapter extends PagerAdapter implements View.OnClickListener {



    Context mContext;
    LayoutInflater mLayoutInflater;
    PackageManager pm;
    int[] mResources = {
            R.drawable.eunoia1,
            R.drawable.eunoia2,
            R.drawable.eunoia3,
            R.drawable.eunoia4,
            R.drawable.eunoia5,
            R.drawable.eunoia6,
    };

    String [] mComplainTitles={"The Domlur Bus Stop getting  spotfixed! It's a shame that residents of Bengaluru tolerate the defacement and vandalism of its well-made bus-stops. Hopefully, things will change now - with this superb work by concerned residents of the area",
            "People of rajahmundry pick random unclean public spaces for spot fixing. Kaam Chalu Mooh Bandh!",
            "Thanks to the initiative of Pariapara Village Unit of NSS, IIT Kharagpur a health check-up camp was organized at village primary school.",
            "Cyclovia translates from Spanish into English as “bike path” is either a permanently designated bicycle route or the closing of city streets to automobiles for the enjoyment of cyclists and public alike. It first started in Bogota and is now very popular all over the world known by different names viz. Open Streets, Summer Streets, etc.",
            "Students of IIT Madras visited the centres of NGO's and provided hands on workshops on cultural, literary and social activities",
    "In a quest to make Gurgaon accessible for its residents and encourage the use of cycling, walking and public transport in the city, organizations and activists of Gurgaon have come together to execute a novel concept  – ‘Raahgiri Day’"};



    public NewCustomPagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pm=mContext.getPackageManager();
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.single_image, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.ivImages);
        TextView desc = (TextView) itemView.findViewById(R.id.tvEunoiaDesc);
        ImageView share = (ImageView) itemView.findViewById(R.id.ivShare);
        ImageView fbShare = (ImageView) itemView.findViewById(R.id.ivShareFB);
        ImageView twitShare = (ImageView) itemView.findViewById(R.id.ivShareTwitter);
        ImageView whatsShare = (ImageView) itemView.findViewById(R.id.ivShareWhatsapp);

        imageView.setImageResource(mResources[position]);
        desc.setText(mComplainTitles[position]);
        container.addView(itemView);

        share.setOnClickListener(this);
        fbShare.setOnClickListener(this);
        twitShare.setOnClickListener(this);
        whatsShare.setOnClickListener(this);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.ivShareWhatsapp:

                try {
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String text = "DOWNLOAD LINK";

                    PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    waIntent.setPackage("com.whatsapp");

                    waIntent.putExtra(Intent.EXTRA_TEXT, text);
                    mContext.startActivity(Intent.createChooser(waIntent, "Share with"));

                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(mContext, "Couldn't find Whatsapp on your mobile!", Toast.LENGTH_SHORT)
                            .show();
                }
                break;

            case R.id.ivShareFB:
                try {
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String text = "DOWNLOAD LINK";

                    PackageInfo info=pm.getPackageInfo("com.facebook.katana", PackageManager.GET_META_DATA);
                    waIntent.setPackage("com.facebook.katana");

                    waIntent.putExtra(Intent.EXTRA_TEXT, text);
                    mContext.startActivity(Intent.createChooser(waIntent, "Share with"));

                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(mContext, "Couldn't find FB on your mobile!", Toast.LENGTH_SHORT)
                            .show();
                }
                break;

            case R.id.ivShareTwitter:
                try {
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String text = "DOWNLOAD LINK";

                    PackageInfo info=pm.getPackageInfo("com.twitter.android", PackageManager.GET_META_DATA);
                    waIntent.setPackage("com.twitter.android");

                    waIntent.putExtra(Intent.EXTRA_TEXT, text);
                    mContext.startActivity(Intent.createChooser(waIntent, "Share with"));

                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(mContext, "Couldn't find Twitter on your mobile!", Toast.LENGTH_SHORT)
                            .show();
                }
                break;

            case R.id.ivShare:
                String message = "\n DOWNLOAD LINK";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);
                mContext.startActivity(Intent.createChooser(share, "Share ChangePins"));
                break;

        }
    }
}