package sandeep.city;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import sandeep.city.Views.CustomIconTitle;

public class SelectSector extends Fragment implements OnClickListener {

	private CustomIconTitle privateSector;
	private CustomIconTitle publicSector;
    Tracker mTracker;
    String TAG="SelectSector";
    AnalyticsApplication application;


    @Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.select_sector, container, false);
		privateSector = (CustomIconTitle) v.findViewById(R.id.ivPrivateSector);
		publicSector = (CustomIconTitle) v.findViewById(R.id.ivPublicSector);
		privateSector.setOnClickListener(this);
		publicSector.setOnClickListener(this);
		return v;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.ivPublicSector:

            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.views))
                    .setAction(getString(R.string.click))
                    .setLabel(getString(R.string.public_sec))
                    .build());


            FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();
			transaction.setCustomAnimations(R.anim.enter, R.anim.exit,R.anim.came,R.anim.went);
			transaction.replace(R.id.fragment, new PublicSectorFragment())
					.addToBackStack("Secs");
			transaction.commit();
			break;

		case R.id.ivPrivateSector:

            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.views))
                    .setAction(getString(R.string.click))
                    .setLabel(getString(R.string.private_sec))
                    .build());



            FragmentTransaction transaction2 = getFragmentManager()
					.beginTransaction();
			transaction2.setCustomAnimations(R.anim.enter, R.anim.exit,R.anim.came,R.anim.went);
			transaction2.replace(R.id.fragment, new PrivateSectorFragment())
					.addToBackStack("Secs");
			transaction2.commit();
			break;
		}
	}



}
