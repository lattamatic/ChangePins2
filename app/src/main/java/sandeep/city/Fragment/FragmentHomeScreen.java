package sandeep.city.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sandeep.city.R;
import sandeep.city.POJO.SingleStory;
import sandeep.city.Adapter.StoryRecyclerViewAdapter;

/**
 * Created by sandeep on 22/3/15.
 */
public class FragmentHomeScreen extends Fragment{

    RecyclerView storyRV;
    RecyclerView.LayoutManager layoutManager;
    List<SingleStory> storyList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.frag_home, container, false);
        storyRV = (RecyclerView) v.findViewById(R.id.rvStories);

        storyList = new ArrayList<SingleStory>();
        layoutManager = new LinearLayoutManager(getActivity());
        storyRV.setLayoutManager(layoutManager);

        storyRV.setAdapter( new StoryRecyclerViewAdapter(storyList, getActivity()));

        prepareStoryList();

        return v;
    }


    //creating dummy data
    private void prepareStoryList(){
        for(int i=1;i<20;i++){
            storyList.add(new SingleStory("Title - "+i, "Author - "+i,i + "The Domlur Bus Stop getting  spotfixed! It's a shame that residents of Bengaluru tolerate the defacement and vandalism of its well-made bus-stops. Hopefully, things will change now - with this superb work by concerned residents of the area"));
        }
    }
}
