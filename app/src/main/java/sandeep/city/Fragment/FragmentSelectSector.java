package sandeep.city.Fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import sandeep.city.AnalyticsApplication;
import sandeep.city.R;
import sandeep.city.Views.ViewIconTitle;

public class FragmentSelectSector extends Fragment implements OnClickListener {

	private ViewIconTitle privateSector;
	private ViewIconTitle publicSector;
    Tracker mTracker;
    String TAG="FragmentSelectSector";
    AnalyticsApplication application;
	SelectSectorInterface myInterface;

	public interface SelectSectorInterface{
		void onClickPublic();
		void onClickPrivate();
	}


    @Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
		myInterface = (SelectSectorInterface) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.select_sector, container, false);
		privateSector = (ViewIconTitle) v.findViewById(R.id.ivPrivateSector);
		publicSector = (ViewIconTitle) v.findViewById(R.id.ivPublicSector);
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

            myInterface.onClickPublic();
			break;

		case R.id.ivPrivateSector:

            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.views))
                    .setAction(getString(R.string.click))
                    .setLabel(getString(R.string.private_sec))
                    .build());

            myInterface.onClickPrivate();
			break;
		}
	}



}
