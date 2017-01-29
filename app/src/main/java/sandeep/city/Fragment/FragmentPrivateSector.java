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

public class FragmentPrivateSector extends Fragment implements OnClickListener,OnLongClickListener {

    ViewIconTitle electricity, waterSupply, housing, education;
    Button others;
    AlertDialog.Builder builder;

    public EditText other;
    public String string;
    TextView description;
    Dialog d;

    Tracker mTracker;
    AnalyticsApplication application;

    InterfaceOnClickCategory myInterface;

    public interface PrivateInterface {
        void onClickPrivateSector(String category);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        myInterface = (InterfaceOnClickCategory) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_privatecategories, container, false);

        electricity = (ViewIconTitle) v.findViewById(R.id.iiElectricity);
        waterSupply = (ViewIconTitle) v.findViewById(R.id.iiWaterSupply);
        housing = (ViewIconTitle) v.findViewById(R.id.iiHousing);
        education = (ViewIconTitle) v.findViewById(R.id.iiEducation);
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

            d = new Dialog(getActivity());
            d.setContentView(R.layout.dialog);
            d.setTitle("Enter Category");

            final EditText category = (EditText) d.findViewById(R.id.etCat);
            Button dialogButton = (Button) d.findViewById(R.id.bSetCat);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String gen = "";
                    string = category.getText().toString();
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

            String category = "";

            switch (v.getId()) {

                case R.id.iiElectricity:
                    category = "Electricity";
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory(getString(R.string.views))
                            .setAction(getString(R.string.click))
                            .setLabel(getString(R.string.electricity))
                            .build());
                    break;

                case R.id.iiWaterSupply:
                    category = "Water Supply";
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory(getString(R.string.views))
                            .setAction(getString(R.string.click))
                            .setLabel(getString(R.string.watersupply))
                            .build());
                    break;

                case R.id.iiHousing:
                    category = "Housing";
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory(getString(R.string.views))
                            .setAction(getString(R.string.click))
                            .setLabel(getString(R.string.housing))
                            .build());
                    break;

                case R.id.iiEducation:
                    category = "Education";
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory(getString(R.string.views))
                            .setAction(getString(R.string.click))
                            .setLabel(getString(R.string.education))
                            .build());
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
            case R.id.iiElectricity:

                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.longclick))
                        .setLabel(getString(R.string.electricity))
                        .build());

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

                builder.setTitle("Water Supply");
                description = new TextView(getActivity());
                description
                        .setText("Please document issues and problems related to water or its supply here");
                builder.setView(description);

                builder.show();
                break;

            case R.id.iiHousing:

                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.longclick))
                        .setLabel(getString(R.string.housing))
                        .build());

                builder.setTitle("Housing");
                description = new TextView(getActivity());
                description
                        .setText("Please document issues related to housing here");
                builder.setView(description);
                builder.show();
                break;

            case R.id.iiEducation:

                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.longclick))
                        .setLabel(getString(R.string.education))
                        .build());

                builder.setTitle("Education");
                description = new TextView(getActivity());
                description
                        .setText("Please document issues related to education here");
                builder.setView(description);
                builder.show();
                break;
        }
        return false;
    }
}
