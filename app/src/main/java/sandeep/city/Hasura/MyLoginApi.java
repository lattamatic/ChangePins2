package sandeep.city.Hasura;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sandeep_chi on 11/18/2017.
 */

public interface MyLoginApi {

    String BASE_URL = "https://auth.batterer83.hasura-app.io";

    @GET("v1/login")
    Call<HasuraFBResponse> getUserStatus(@Query("access_token") String accessToken);

    @GET("/user/logout")
    Call<HasuraFBResponse> logout();
}
