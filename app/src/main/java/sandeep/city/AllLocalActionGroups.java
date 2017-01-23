package sandeep.city;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by sandeep on 16/5/15.
 */
public class AllLocalActionGroups extends Fragment implements View.OnClickListener {
    LocalActionPager mCustomPagerAdapter;
    ViewPager mViewPager;
    ImageView left, right;
    int current =0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.all_local_action_groups, container, false);
        left= (ImageView) v.findViewById(R.id.ivLeftArrow);
        right= (ImageView) v.findViewById(R.id.ivRightArrow);
        mCustomPagerAdapter = new LocalActionPager(getActivity());
        mViewPager = (ViewPager) v.findViewById(R.id.local_action_pager);
        mViewPager.setAdapter(mCustomPagerAdapter);


        left.setVisibility(View.GONE);

        mViewPager.setCurrentItem(current);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    left.setVisibility(View.GONE);
                }else if(position==mCustomPagerAdapter.getCount()-1){
                    right.setVisibility(View.GONE);
                }else{
                    left.setVisibility(View.VISIBLE);
                    right.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ivLeftArrow:
                current=mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(current-1);
                break;
            case R.id.ivRightArrow:
                current=mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(current+1);
                break;
        }
    }
}
