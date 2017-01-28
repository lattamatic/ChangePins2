package sandeep.city;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Fragment;
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

import sandeep.city.Views.CustomIconTitle;

public class PublicSectorFragment extends Fragment implements OnClickListener, OnLongClickListener,CDialog.Communicator {

	public static final String TAG = "PubSec";
	CustomIconTitle transport, publicSpces, wasteManagement, safety, utils, services;
	Button others;
	public String text;
    public String string;
    Dialog d;
	public TextView description;
    Tracker mTracker;
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
		View v = inflater.inflate(R.layout.public_comp, container, false);

		transport = (CustomIconTitle) v.findViewById(R.id.iiTransport);
		publicSpces = (CustomIconTitle) v.findViewById(R.id.iiPublicSpaces);
		wasteManagement = (CustomIconTitle) v.findViewById(R.id.iiWasteManagement);
		safety = (CustomIconTitle) v.findViewById(R.id.iiSafety);
		utils = (CustomIconTitle) v.findViewById(R.id.iiUtilities);
		services = (CustomIconTitle) v.findViewById(R.id.iiServices);
		others = (Button) v.findViewById(R.id.bPublic);

		transport.setOnClickListener(this);
		publicSpces.setOnClickListener(this);
		wasteManagement.setOnClickListener(this);
		safety.setOnClickListener(this);
		utils.setOnClickListener(this);
		services.setOnClickListener(this);
		others.setOnClickListener(this);

		transport.setOnLongClickListener(this);
		publicSpces.setOnLongClickListener(this);
		wasteManagement.setOnLongClickListener(this);
		safety.setOnLongClickListener(this);
		utils.setOnLongClickListener(this);
		services.setOnLongClickListener(this);

		return v;
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.bPublic) {

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



        } else {
			switch (v.getId()) {
			case R.id.iiTransport:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.click))
                        .setLabel(getString(R.string.transport))
                        .build());
				FinalComplaint.category = "Transport";
				break;
			case R.id.iiUtilities:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.click))
                        .setLabel(getString(R.string.utilities))
                        .build());
				FinalComplaint.category = "Utilities";
				break;
			case R.id.iiWasteManagement:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.click))
                        .setLabel(getString(R.string.wastemanagement))
                        .build());
				FinalComplaint.category = "Waste Management";
				break;
			case R.id.iiSafety:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.click))
                        .setLabel(getString(R.string.safety))
                        .build());
				FinalComplaint.category = "Safety";
				break;
			case R.id.iiPublicSpaces:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.click))
                        .setLabel(getString(R.string.publicspaces))
                        .build());
				FinalComplaint.category = "Public Spaces";
				break;
			case R.id.iiServices:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.click))
                        .setLabel(getString(R.string.services))
                        .build());
				FinalComplaint.category = "Services";
				break;
			}
            Intent i = new Intent(getActivity(),FinalComplaint.class);
            getActivity().startActivity(i);
		}

	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iiTransport:
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.views))
                    .setAction(getString(R.string.longclick))
                    .setLabel(getString(R.string.transport))
                    .build());
			AlertDialog.Builder builder = new Builder(getActivity());
			builder.setTitle("Transport");
			description = new TextView(getActivity());
			description
					.setText("Please document issues related to transport like - crowded buses, violation of rules etc");
			builder.setView(description);

			builder.show();
			break;

		case R.id.iiUtilities:
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.views))
                    .setAction(getString(R.string.longclick))
                    .setLabel(getString(R.string.utilities))
                    .build());
			AlertDialog.Builder builder2 = new Builder(getActivity());
			builder2.setTitle("Utilities");
			description = new TextView(getActivity());
			description
					.setText("Please document issues related to utilities like - Street lights not working, lack of proper traffic lights etc");
			builder2.setView(description);
			builder2.show();
			break;
		case R.id.iiWasteManagement:
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.views))
                    .setAction(getString(R.string.longclick))
                    .setLabel(getString(R.string.wastemanagement))
                    .build());
			AlertDialog.Builder builder3 = new Builder(getActivity());
			builder3.setTitle("Waste Management");
			description = new TextView(getActivity());
			description
					.setText("Please document issues related to waste management like - littering, too much usage of plastic etc");
			builder3.setView(description);
			builder3.show();
			break;
		case R.id.iiSafety:
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.views))
                    .setAction(getString(R.string.longclick))
                    .setLabel(getString(R.string.safety))
                    .build());
			AlertDialog.Builder builder4 = new Builder(getActivity());
			builder4.setTitle("Safety");
			description = new TextView(getActivity());
			description
					.setText("Description about Safety here!Description about Safety here!Description about Safety here!Description about Safety here!Description about Safety here!Description about Safety here!Description about Safety here!Description about Safety here!");
			builder4.setView(description);
			builder4.show();
			break;
		case R.id.iiPublicSpaces:
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.views))
                    .setAction(getString(R.string.longclick))
                    .setLabel(getString(R.string.publicspaces))
                    .build());
			AlertDialog.Builder builder5 = new Builder(getActivity());
			builder5.setTitle("Public Spaces");
			description = new TextView(getActivity());
			description
					.setText("Please document issues related to public spaces like - unclean public spaces, too much vehicle occupancy etc");
			builder5.setView(description);
			builder5.show();
			break;
		case R.id.iiServices:
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.views))
                    .setAction(getString(R.string.longclick))
                    .setLabel(getString(R.string.services))
                    .build());
			AlertDialog.Builder builder6 = new Builder(getActivity());
			builder6.setTitle("Services");
			description = new TextView(getActivity());
			description
					.setText("Please report issues related to services - lack of response from the municipal authorities, late issue of aadhar card etc");
			builder6.setView(description);
			builder6.show();
			break;
		}
		return true;
	}

    @Override
    public void onDialogMessage(String message) {
        HomeActivity.category=string = message;
        String gen = "";
        if (string.equals(gen)) {
			Toast.makeText(getActivity(), "Category cannot be empty",
                    Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(getActivity(),FinalComplaint.class);
            getActivity().startActivity(i);
        }
    }
}
