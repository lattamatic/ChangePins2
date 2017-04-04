package sandeep.city.RetrofitTest;


import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sandeep_chi on 4/3/2017.
 */

public interface RetrofitInterface {
    @GET("android/jsonandroid")
    Call<JSONResponse> getJSON();
}
