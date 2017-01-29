package sandeep.city;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by sandeep_chi on 1/29/2017.
 */

public abstract class ChangePinsActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayout());

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            toolbar.setNavigationIcon(R.drawable.back);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setBackNavigation();
                }
            });

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getTitleText());
            }
        }
    }

    protected abstract int getTitleText();

    protected abstract int getLayout();

    protected void setBackNavigation(){
        finish();
    }
}
