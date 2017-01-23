package sandeep.city.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sandeep.city.R;

/**
 * Created by sandeep on 7/8/15.
 */
public class ImageIcon extends RelativeLayout {

    Context c;


    public ImageIcon(Context context) {
        super(context);
        this.c=context;
    }

    public ImageIcon(Context context, AttributeSet attrs) {
        super(context, attrs);

        c=context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageIcon,0,0);
        String title = a.getString(R.styleable.ImageIcon_title_name);
        int image_id = a.getResourceId(R.styleable.ImageIcon_image_icon, 0);



        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.image_icon,this,true);

        TextView tit = (TextView) findViewById(R.id.image_title);
        ImageView icon = (ImageView) findViewById(R.id.image_icon);

        tit.setText(title);
        icon.setImageResource(image_id);
        tit.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);

        a.recycle();
    }


}
