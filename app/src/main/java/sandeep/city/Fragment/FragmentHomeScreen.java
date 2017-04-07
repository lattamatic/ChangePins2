package sandeep.city.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sandeep.city.Adapter.StoryRecyclerViewAdapter;
import sandeep.city.POJO.SingleStory;
import sandeep.city.R;
import sandeep.city.RetrofitTest.AndroidVersion;
import sandeep.city.RetrofitTest.DataAdapter;
import sandeep.city.RetrofitTest.JSONResponse;
import sandeep.city.RetrofitTest.RetrofitInterface;


/**
 * Created by sandeep on 22/3/15.
 */
public class FragmentHomeScreen extends Fragment{

    RecyclerView storyRV;
    RecyclerView.LayoutManager layoutManager;
    List<SingleStory> storyList;
    ArrayList<AndroidVersion> data;
    DataAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.frag_home, container, false);
        storyRV = (RecyclerView) v.findViewById(R.id.rvStories);

        storyList = new ArrayList<SingleStory>();
        layoutManager = new LinearLayoutManager(getActivity());
        storyRV.setLayoutManager(layoutManager);

        prepareStoryList();
        //loadJSON();

        return v;
    }


    //creating dummy data
    private void prepareStoryList(){
        for(int i=1;i<20;i++){
            storyList.add(new SingleStory("Title - "+i, "Author - "+i,i +
                    "The Domlur Bus Stop getting  spotfixed! It's a shame that residents of Bengaluru tolerate the defacement and vandalism of its well-made bus-stops. Hopefully, things will change now - with this superb work by concerned residents of the area"));
        }
        storyRV.setAdapter( new StoryRecyclerViewAdapter(storyList, getActivity()));
    }

    private void loadJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.learn2crack.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitInterface request = retrofit.create(RetrofitInterface.class);
        Call<JSONResponse> call = request.getJSON();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getAndroid()));
                adapter = new DataAdapter(data);
                storyRV.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}