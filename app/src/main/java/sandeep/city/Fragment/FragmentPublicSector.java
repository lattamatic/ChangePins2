package sandeep.city.Fragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Fragment;
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

import sandeep.city.AnalyticsApplication;
import sandeep.city.InterfaceOnClickCategory;
import sandeep.city.R;
import sandeep.city.Views.ViewIconTitle;

public class FragmentPublicSector extends Fragment implements OnClickListener, OnLongClickListener{

	public static final String TAG = "PubSec";
	ViewIconTitle transport, publicSpces, wasteManagement, safety, utils, services;
	Button others;
	public String text;
    public String string;
    Dialog d;
	public TextView description;
    Tracker mTracker;
    AnalyticsApplication application;
	InterfaceOnClickCategory myInterface;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
		myInterface = (InterfaceOnClickCategory) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frag_publiccategories, container, false);

		transport = (ViewIconTitle) v.findViewById(R.id.iiTransport);
		publicSpces = (ViewIconTitle) v.findViewById(R.id.iiPublicSpaces);
		wasteManagement = (ViewIconTitle) v.findViewById(R.id.iiWasteManagement);
		safety = (ViewIconTitle) v.findViewById(R.id.iiSafety);
		utils = (ViewIconTitle) v.findViewById(R.id.iiUtilities);
		services = (ViewIconTitle) v.findViewById(R.id.iiServices);
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
                        myInterface.onClickCategory(string);
                    }
                    d.dismiss();
                }
            });
            d.show();
        } else {
			String category="";
			switch (v.getId()) {
			case R.id.iiTransport:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.click))
                        .setLabel(getString(R.string.transport))
                        .build());
				category = "Transport";
				break;
			case R.id.iiUtilities:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.click))
                        .setLabel(getString(R.string.utilities))
                        .build());
				category = "Utilities";
				break;
			case R.id.iiWasteManagement:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.click))
                        .setLabel(getString(R.string.wastemanagement))
                        .build());
				category = "Waste Management";
				break;
			case R.id.iiSafety:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.click))
                        .setLabel(getString(R.string.safety))
                        .build());
				category = "Safety";
				break;
			case R.id.iiPublicSpaces:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.click))
                        .setLabel(getString(R.string.publicspaces))
                        .build());
				category = "Public Spaces";
				break;
			case R.id.iiServices:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.click))
                        .setLabel(getString(R.string.services))
                        .build());
				category = "Services";
				break;
			}
            myInterface.onClickCategory(category);
		}

	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new Builder(getActivity());
		switch (v.getId()) {
		case R.id.iiTransport:
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.views))
                    .setAction(getString(R.string.longclick))
                    .setLabel(getString(R.string.transport))
                    .build());
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
			builder.setTitle("Utilities");
			description = new TextView(getActivity());
			description
					.setText("Please document issues related to utilities like - Street lights not working, lack of proper traffic lights etc");
			builder.setView(description);
			builder.show();
			break;
		case R.id.iiWasteManagement:
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.views))
                    .setAction(getString(R.string.longclick))
                    .setLabel(getString(R.string.wastemanagement))
                    .build());
			builder.setTitle("Waste Management");
			description = new TextView(getActivity());
			description
					.setText("Please document issues related to waste management like - littering, too much usage of plastic etc");
			builder.setView(description);
			builder.show();
			break;
		case R.id.iiSafety:
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.views))
                    .setAction(getString(R.string.longclick))
                    .setLabel(getString(R.string.safety))
                    .build());
			builder.setTitle("Safety");
			description = new TextView(getActivity());
			description
					.setText("Description about Safety here!Description about Safety here!Description about Safety here!Description about Safety here!Description about Safety here!Description about Safety here!Description about Safety here!Description about Safety here!");
			builder.setView(description);
			builder.show();
			break;
		case R.id.iiPublicSpaces:
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.views))
                    .setAction(getString(R.string.longclick))
                    .setLabel(getString(R.string.publicspaces))
                    .build());
			builder.setTitle("Public Spaces");
			description = new TextView(getActivity());
			description
					.setText("Please document issues related to public spaces like - unclean public spaces, too much vehicle occupancy etc");
			builder.setView(description);
			builder.show();
			break;
		case R.id.iiServices:
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(getString(R.string.views))
                    .setAction(getString(R.string.longclick))
                    .setLabel(getString(R.string.services))
                    .build());
			builder.setTitle("Services");
			description = new TextView(getActivity());
			description
					.setText("Please report issues related to services - lack of response from the municipal authorities, late issue of aadhar card etc");
			builder.setView(description);
			builder.show();
			break;
		}
		return true;
	}

}
