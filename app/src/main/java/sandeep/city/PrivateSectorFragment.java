package sandeep.city;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import sandeep.city.Views.ImageIcon;

public class PrivateSectorFragment extends Fragment implements OnClickListener,
		android.content.DialogInterface.OnClickListener, OnLongClickListener {

	ImageIcon electricity, waterSupply, housing, education;
	Button others;
	AlertDialog.Builder builder;

	public EditText other;
	public String string;
	TextView description;
    Dialog d;

    Tracker mTracker;
    AnalyticsApplication application;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.private_comp, container, false);

		electricity = (ImageIcon) v.findViewById(R.id.iiElectricity);
		waterSupply = (ImageIcon) v.findViewById(R.id.iiWaterSupply);
		housing = (ImageIcon) v.findViewById(R.id.iiHousing);
		education = (ImageIcon) v.findViewById(R.id.iiEducation);
		others = (Button) v.findViewById(R.id.bPrivate);

		electricity.setOnClickListener(this);
		waterSupply.setOnClickListener(this);
		housing.setOnClickListener(this);
		education.setOnClickListener(this);
		others.setOnClickListener(this);

		return v;
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.bPrivate) {

            d= new Dialog(getActivity());
            d.setContentView(R.layout.dialog);
            d.setTitle("Enter Category");

            final EditText category = (EditText) d.findViewById(R.id.etCat);
            Button dialogButton = (Button) d.findViewById(R.id.bSetCat);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String gen = "";
                    string=category.getText().toString();
                    if (string.equals(gen)) {
                        d.dismiss();

                        Toast.makeText(getActivity(), "Category cannot be empty",
                                Toast.LENGTH_SHORT).show();
                    } else {
						FinalComplaint.category=string;
                        Intent i = new Intent(getActivity(),FinalComplaint.class);
                        getActivity().startActivity(i);

                    }
                    d.dismiss();

                }
            });

            d.show();


            ///////////////////


        } else {
			switch (v.getId()) {

			case R.id.iiElectricity:
				FinalComplaint.category="Electricity";
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.click))
                        .setLabel(getString(R.string.electricity))
                        .build());
				break;

			case R.id.iiWaterSupply:
				FinalComplaint.category="Water Supply";
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.click))
                        .setLabel(getString(R.string.watersupply))
                        .build());
				break;

			case R.id.iiHousing:
				FinalComplaint.category="Housing";
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.click))
                        .setLabel(getString(R.string.housing))
                        .build());
				break;

			case R.id.iiEducation:
				FinalComplaint.category="Education";
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.click))
                        .setLabel(getString(R.string.education))
                        .build());
				break;

			}

            Intent i = new Intent(getActivity(),FinalComplaint.class);
            getActivity().startActivity(i);
			
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

		FinalComplaint.category=string = other.getText().toString();
		String gen = "";
		if (string.equals(gen)) {
			dialog.dismiss();

			Toast.makeText(getActivity(), "Category cannot be empty",
					Toast.LENGTH_SHORT).show();
		} else {
            Intent i = new Intent(getActivity(),FinalComplaint.class);
            getActivity().startActivity(i);
		}
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iiElectricity:

            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.views))
                    .setAction(getString(R.string.longclick))
                    .setLabel(getString(R.string.electricity))
                    .build());

			AlertDialog.Builder builder = new Builder(getActivity());
			builder.setTitle("Electricity");
			description = new TextView(getActivity());
			description
					.setText("Please document issues or problems related to electricity here");
			builder.setView(description);

			builder.show();
			break;

		case R.id.iiWaterSupply:

            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.views))
                    .setAction(getString(R.string.longclick))
                    .setLabel(getString(R.string.watersupply))
                    .build());

			AlertDialog.Builder builder2 = new Builder(getActivity());
			builder2.setTitle("Water Supply");
			description = new TextView(getActivity());
			description
					.setText("Please document issues and problems related to water or its supply here");
			builder2.setView(description);

			builder2.show();
			break;

		case R.id.iiHousing:

            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.views))
                    .setAction(getString(R.string.longclick))
                    .setLabel(getString(R.string.housing))
                    .build());

			AlertDialog.Builder builder3 = new Builder(getActivity());
			builder3.setTitle("Housing");
			description = new TextView(getActivity());
			description
					.setText("Please document issues related to housing here");
			builder3.setView(description);

			builder3.show();
			break;

		case R.id.iiEducation:

            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.views))
                    .setAction(getString(R.string.longclick))
                    .setLabel(getString(R.string.education))
                    .build());

			AlertDialog.Builder builder4 = new Builder(getActivity());
			builder4.setTitle("Education");
			description = new TextView(getActivity());
			description
					.setText("Please document issues related to education here");
			builder4.setView(description);

			builder4.show();
			break;
		}
		return false;
	}

}
