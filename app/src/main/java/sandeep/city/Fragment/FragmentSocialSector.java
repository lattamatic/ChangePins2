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

public class FragmentSocialSector extends Fragment implements OnClickListener,OnLongClickListener {

    ViewIconTitle electricity, waterSupply, housing, education, genderissues, nutrition;
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
        View v = inflater.inflate(R.layout.frag_socialcategories, container, false);

        electricity = (ViewIconTitle) v.findViewById(R.id.iiElectricity);
        waterSupply = (ViewIconTitle) v.findViewById(R.id.iiWaterSupply);
        housing = (ViewIconTitle) v.findViewById(R.id.iiHousing);
        education = (ViewIconTitle) v.findViewById(R.id.iiEducation);
        genderissues = (ViewIconTitle) v.findViewById(R.id.iiGender);
        nutrition = (ViewIconTitle) v.findViewById(R.id.iiNutrition);
        others = (Button) v.findViewById(R.id.bSocial);

        electricity.setOnClickListener(this);
        waterSupply.setOnClickListener(this);
        housing.setOnClickListener(this);
        education.setOnClickListener(this);
        genderissues.setOnClickListener(this);
        nutrition.setOnClickListener(this);
        others.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.bSocial) {

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
                    category = getString(R.string.electricity);
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory(getString(R.string.views))
                            .setAction(getString(R.string.click))
                            .setLabel(category)
                            .build());
                    break;

                case R.id.iiWaterSupply:
                    category = getString(R.string.watersupply);
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory(getString(R.string.views))
                            .setAction(getString(R.string.click))
                            .setLabel(category)
                            .build());
                    break;

                case R.id.iiHousing:
                    category = getString(R.string.housing);
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory(getString(R.string.views))
                            .setAction(getString(R.string.click))
                            .setLabel(category)
                            .build());
                    break;

                case R.id.iiEducation:
                    category = getString(R.string.education);
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory(getString(R.string.views))
                            .setAction(getString(R.string.click))
                            .setLabel(category)
                            .build());
                    break;
                case R.id.iiGender:
                    category = getString(R.string.gender);
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory(getString(R.string.views))
                            .setAction(getString(R.string.click))
                            .setLabel(category)
                            .build());
                    break;

                case R.id.iiNutrition:
                    category = getString(R.string.nutrition);
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory(getString(R.string.views))
                            .setAction(getString(R.string.click))
                            .setLabel(category)
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
        String category;
        switch (v.getId()) {
            case R.id.iiElectricity:
                category = getString(R.string.electricity);
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.longclick))
                        .setLabel(category)
                        .build());

                builder.setTitle(category);
                description = new TextView(getActivity());
                description
                        .setText("Please document issues or problems related to Electricity here");
                builder.setView(description);

                builder.show();
                break;

            case R.id.iiWaterSupply:
                category = getString(R.string.watersupply);
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.longclick))
                        .setLabel(category)
                        .build());

                builder.setTitle(category);
                description = new TextView(getActivity());
                description
                        .setText("Please document issues and problems related to Water or Water Supply here");
                builder.setView(description);

                builder.show();
                break;

            case R.id.iiHousing:

                category = getString(R.string.housing);
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.longclick))
                        .setLabel(category)
                        .build());

                builder.setTitle(category);
                description = new TextView(getActivity());
                description
                        .setText("Please document issues related to Housing here");
                builder.setView(description);
                builder.show();
                break;

            case R.id.iiEducation:
                category = getString(R.string.education);
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.longclick))
                        .setLabel(category)
                        .build());

                builder.setTitle(category);
                description = new TextView(getActivity());
                description
                        .setText("Please document issues related to Education here");
                builder.setView(description);
                builder.show();
                break;

            case R.id.iiGender:
                category = getString(R.string.gender);
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.longclick))
                        .setLabel(category)
                        .build());

                builder.setTitle(category);
                description = new TextView(getActivity());
                description
                        .setText("Please document issues related to Gender here");
                builder.setView(description);
                builder.show();
                break;
            case R.id.iiNutrition:
                category = getString(R.string.nutrition);
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.views))
                        .setAction(getString(R.string.longclick))
                        .setLabel(category)
                        .build());

                builder.setTitle(category);
                description = new TextView(getActivity());
                description
                        .setText("Please document issues related to Nutrition here");
                builder.setView(description);
                builder.show();
                break;
        }
        return false;
    }
}
