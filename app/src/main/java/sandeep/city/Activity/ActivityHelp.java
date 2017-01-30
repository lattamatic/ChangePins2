package sandeep.city.Activity;

import android.app.Activity;
import android.os.Bundle;

import sandeep.city.ChangePinsActivity;
import sandeep.city.R;

/**
 * Created by sandeep on 1/3/15.
 */
public class ActivityHelp extends ChangePinsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getTitleText() {
        return R.string.help;
    }

    @Override
    protected int getLayout() {
        return R.layout.ac_help;
    }
}
