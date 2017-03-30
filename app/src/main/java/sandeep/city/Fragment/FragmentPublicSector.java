package sandeep.city.Fragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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

public class FragmentPublicSector extends Fragment {

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

		transport.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickedCategory(getString(R.string.transport));
			}
		});
		publicSpces.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickedCategory(getString(R.string.publicspaces));
			}
		});
		wasteManagement.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickedCategory(getString(R.string.wastemanagement));
			}
		});
		safety.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickedCategory(getString(R.string.safety));
			}
		});
		utils.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickedCategory(getString(R.string.utilities));
			}
		});
		services.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickedCategory(getString(R.string.services));
			}
		});
		others.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
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
			}
		});

		transport.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				longClickedCategory(getString(R.string.transport),
						"Please document issues related to transport like - crowded buses, violation of rules etc");
				return true;
			}
		});
		publicSpces.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				longClickedCategory(getString(R.string.publicspaces),
						"Please document issues related to public spaces like - unclean public spaces, too much vehicle occupancy etc");
				return true;
			}
		});
		wasteManagement.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				longClickedCategory(getString(R.string.wastemanagement),
						"Please document issues related to waste management like - littering, too much usage of plastic etc");
				return true;
			}
		});
		safety.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				longClickedCategory(getString(R.string.safety),
						"Description about Safety here!Description about Safety here!Description about Safety here!Description about Safety here!Description about Safety here!Description about Safety here!Description about Safety here!Description about Safety here!");
				return true;
			}
		});
		utils.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				longClickedCategory(getString(R.string.utilities),
						"Please document issues related to utilities like - Street lights not working, lack of proper traffic lights etc");
				return true;
			}
		});
		services.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				longClickedCategory(getString(R.string.services),
						"Please report issues related to services - lack of response from the municipal authorities, late issue of aadhar card etc");
				return true;
			}
		});

		return v;
	}

	private void clickedCategory(String category){
		mTracker.send(new HitBuilders.EventBuilder()
				.setCategory(getString(R.string.views))
				.setAction(getString(R.string.click))
				.setLabel(category)
				.build());
		myInterface.onClickCategory(category);
	}

	private void longClickedCategory(String category, String content){
		AlertDialog.Builder builder = new Builder(getActivity());
		mTracker.send(new HitBuilders.EventBuilder()
				.setCategory(getString(R.string.views))
				.setAction(getString(R.string.longclick))
				.setLabel(category)
				.build());
		builder.setTitle(category);
		description = new TextView(getActivity());
		description.setText(content);
		builder.setView(description);
		builder.show();
	}
}
