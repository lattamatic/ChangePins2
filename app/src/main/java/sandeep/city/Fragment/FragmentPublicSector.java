package sandeep.city.Fragment;

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


import sandeep.city.ChangePinsApplication;
import sandeep.city.InterfaceOnClickCategory;
import sandeep.city.R;
import sandeep.city.Views.ViewIconTitle;

public class FragmentPublicSector extends Fragment {

	private ViewIconTitle transport, publicSpces, wasteManagement, safety, utils, services;
	private Button others;
    private String string;
    private Dialog d;
	private TextView description;
    private ChangePinsApplication application;
	private InterfaceOnClickCategory myInterface;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        application = (ChangePinsApplication) getActivity().getApplication();
		myInterface = (InterfaceOnClickCategory) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frag_publiccategories, container, false);
		initializeViews(v);
		setOnIconClickListener();
		setOnIconLongClickListener();
		return v;
	}

	//Calls the method in HomeActivity to which this fragment is attached
	private void clickedCategory(String category, String description){
		myInterface.onClickCategory(category, description);
	}


	private void longClickedCategory(String category, String description){
		myInterface.onLongClickCategory(category,description);
	}

	private void initializeViews(View v){
		transport = (ViewIconTitle) v.findViewById(R.id.iiTransport);
		publicSpces = (ViewIconTitle) v.findViewById(R.id.iiPublicSpaces);
		wasteManagement = (ViewIconTitle) v.findViewById(R.id.iiWasteManagement);
		safety = (ViewIconTitle) v.findViewById(R.id.iiSafety);
		utils = (ViewIconTitle) v.findViewById(R.id.iiUtilities);
		services = (ViewIconTitle) v.findViewById(R.id.iiServices);
		others = (Button) v.findViewById(R.id.bPublic);
	}

	private void setOnIconClickListener(){
		transport.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickedCategory(getString(R.string.transport), getString(R.string.transportDescription));
			}
		});
		publicSpces.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickedCategory(getString(R.string.publicspaces), getString(R.string.publicspaceDescription));
			}
		});
		wasteManagement.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickedCategory(getString(R.string.wastemanagement), getString(R.string.wastemanagementDescription));
			}
		});
		safety.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickedCategory(getString(R.string.safety), getString(R.string.safetyDescription)) ;
			}
		});
		utils.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickedCategory(getString(R.string.utilities), getString(R.string.utilitiesDescription));
			}
		});
		services.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickedCategory(getString(R.string.services), getString(R.string.servicesDescription));
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

	}

	private void setOnIconLongClickListener(){
		transport.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				longClickedCategory(getString(R.string.transport),
						getString(R.string.transportDescription));
				return true;
			}
		});
		publicSpces.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				longClickedCategory(getString(R.string.publicspaces), getString(R.string.publicspaceDescription));
				return true;
			}
		});
		wasteManagement.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				longClickedCategory(getString(R.string.wastemanagement), getString(R.string.wastemanagementDescription));
				return true;
			}
		});
		safety.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				longClickedCategory(getString(R.string.safety), getString(R.string.safetyDescription));
				return true;
			}
		});
		utils.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				longClickedCategory(getString(R.string.utilities),
						getString(R.string.utilitiesDescription));
				return true;
			}
		});
		services.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				longClickedCategory(getString(R.string.services),
	getString(R.string.servicesDescription));
				return true;
			}
		});

	}
}
