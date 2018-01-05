package sandeep.city.Fragment;

import android.app.AlertDialog;
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


import sandeep.city.ChangePinsApplication;
import sandeep.city.InterfaceOnClickCategory;
import sandeep.city.R;
import sandeep.city.Views.ViewIconTitle;

public class FragmentSocialSector extends Fragment {

    ViewIconTitle electricity, waterSupply, housing, education, trees, nutrition;
    Button others;
    AlertDialog.Builder builder;

    public EditText other;
    public String string;
    TextView description;
    Dialog d;

    ChangePinsApplication application;

    InterfaceOnClickCategory myInterface;

    public interface PrivateInterface {
        void onClickPrivateSector(String category);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (ChangePinsApplication) getActivity().getApplication();
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
        trees = (ViewIconTitle) v.findViewById(R.id.iiTrees);
        nutrition = (ViewIconTitle) v.findViewById(R.id.iiNutrition);
        others = (Button) v.findViewById(R.id.bSocial);

        electricity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
             clickedCategory(getString(R.string.electricity), getString(R.string.electricityDescription));
            }
        });
        waterSupply.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedCategory(getString(R.string.watersupply), getString(R.string.watersupplyDescription));
            }
        });
        housing.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedCategory(getString(R.string.housing), getString(R.string.housingDescription));
            }
        });
        education.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedCategory(getString(R.string.education), getString(R.string.educationDescription));
            }
        });
        trees.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedCategory(getString(R.string.trees), getString(R.string.treesDescription));
            }
        });
        nutrition.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedCategory(getString(R.string.nutrition), getString(R.string.nutritionDescription));
            }
        });
        others.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        electricity.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickedCategory(getString(R.string.electricity), getString(R.string.electricityDescription));
                return true;
            }
        });
        waterSupply.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickedCategory(getString(R.string.watersupply),
                        getString(R.string.watersupplyDescription));
                return true;
            }
        });
        housing.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickedCategory(getString(R.string.housing),getString(R.string.housingDescription));
                return true;
            }
        });
        education.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickedCategory(getString(R.string.education), getString(R.string.educationDescription));
                return true;
            }
        });
        trees.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickedCategory(getString(R.string.trees), getString(R.string.treesDescription));
                return true;
            }
        });
        nutrition.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickedCategory(getString(R.string.nutrition), getString(R.string.nutritionDescription));
                return true;
            }
        });

        return v;
    }

    private void clickedCategory(String category, String description){
        myInterface.onClickCategory(category, description);
    }

    private void longClickedCategory(String category, String desc){

       myInterface.onLongClickCategory(category,desc);
    }
}