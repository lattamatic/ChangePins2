package sandeep.city.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sandeep.city.R;

/**
 * Created by sandeep_chi on 2/6/2017.
 */

public class FragmentHelp extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ac_help, container, false);
        return v;
    }
}
