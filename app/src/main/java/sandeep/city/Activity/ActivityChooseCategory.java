package sandeep.city.Activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import sandeep.city.ChangePinsActivity;
import sandeep.city.Fragment.FragmentPrivateSector;
import sandeep.city.Fragment.FragmentPublicSector;
import sandeep.city.Fragment.FragmentSelectSector;
import sandeep.city.InterfaceOnClickCategory;
import sandeep.city.R;

/**
 * Created by sandeep_chi on 1/29/2017.
 */

public class ActivityChooseCategory extends ChangePinsActivity implements FragmentSelectSector.SelectSectorInterface, InterfaceOnClickCategory {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentSelectSector frament = new FragmentSelectSector();
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.flCategoryContainer, frament,"Select Sector");
        trans.addToBackStack(null);
        trans.commit();
    }


    @Override
    protected void setBackNavigation() {
        checkNavigation();
    }

    @Override
    protected int getTitleText() {
        return R.string.registercomplaint;
    }

    @Override
    protected int getLayout() {
        return R.layout.ac_choosecategory;
    }

    @Override
    public void onClickPublic() {
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.came, R.anim.went);
        transaction.replace(R.id.flCategoryContainer, new FragmentPublicSector())
                .addToBackStack("Secs");
        transaction.commit();
    }

    @Override
    public void onClickPrivate() {
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.came, R.anim.went);
        transaction.replace(R.id.flCategoryContainer, new FragmentPrivateSector())
                .addToBackStack("Secs");
        transaction.commit();
    }

    @Override
    public void onClickCategory(String category) {
        Intent intent = new Intent(ActivityChooseCategory.this, ActivityRegisterComplaint.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        checkNavigation();
    }

    private void checkNavigation(){
        if (getFragmentManager().findFragmentByTag("Select Sector").isVisible()) {
            finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
