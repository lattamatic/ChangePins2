package sandeep.city;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by sandeep on 22/3/15.
 */
public class HomeScreen extends Fragment{

    TextView eunoia,notification;
    private ViewPager _mViewPager;
    private NewCustomPagerAdapter _adapter;
    private RadioButton r1,r2,r3,r4,r5,r6;
    int t=10;
    int tw=20;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.home_new, container, false);
        eunoia = (TextView) v.findViewById(R.id.tvEunoia);
        _mViewPager = (ViewPager) v.findViewById(R.id.pager);
        _adapter = new NewCustomPagerAdapter(getActivity());
        _mViewPager.setAdapter(_adapter);
        _mViewPager.setCurrentItem(0);
        initButton(v);
        _mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrollStateChanged(int position) {}
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}
            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                r1.setChecked(false);
                r2.setChecked(false);
                r3.setChecked(false);
                r4.setChecked(false);
                r5.setChecked(false);
                r6.setChecked(false);
                switch (position){
                    case 0:
                        r1.setChecked(true);
                        break;
                    case 1:
                        r2.setChecked(true);
                        break;
                    case 2:
                        r3.setChecked(true);
                        break;
                    case 3:
                        r4.setChecked(true);
                        break;
                    case 4:
                        r5.setChecked(true);
                        break;
                    case 5:
                        r6.setChecked(true);
                        break;
                }
            }

        });
        return v;
    }


    private void initButton(View v){
        r1= (RadioButton) v.findViewById(R.id.rb1);
        r2= (RadioButton) v.findViewById(R.id.rb2);
        r3= (RadioButton) v.findViewById(R.id.rb3);
        r4= (RadioButton) v.findViewById(R.id.rb4);
        r5= (RadioButton) v.findViewById(R.id.rb5);
        r6= (RadioButton) v.findViewById(R.id.rb6);
        r1.setChecked(true);
    }

}
