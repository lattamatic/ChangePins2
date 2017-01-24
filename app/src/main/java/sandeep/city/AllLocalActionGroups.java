package sandeep.city;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandeep on 16/5/15.
 */
public class AllLocalActionGroups extends Fragment {

    RecyclerView groupsRV;
    RecyclerView.LayoutManager layoutManager;
    List<SingleLocalActionGroup> localActionGroups;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.all_local_action_groups, container, false);

        localActionGroups = new ArrayList<SingleLocalActionGroup>();

        groupsRV = (RecyclerView) v.findViewById(R.id.rvLocalActionGroups);

        layoutManager = new LinearLayoutManager(getActivity());
        groupsRV.setLayoutManager(layoutManager);
        groupsRV.setAdapter(new LocalActionGroupsAdapter(localActionGroups, getActivity()));

        prepareGroupsData();

        return v;
    }

    private void prepareGroupsData(){
        for(int i=0;i<10;i++){
            localActionGroups.add(new SingleLocalActionGroup(i,i,"Title - "+i,"Location - "+i, i+"-"+i+"-"+"2017", null));
        }
    }

}
