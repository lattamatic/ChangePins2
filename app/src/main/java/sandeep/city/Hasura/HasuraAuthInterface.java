package sandeep.city.Hasura;

import io.hasura.sdk.model.response.LoginResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sandeep_chi on 10/7/2017.
 */

public interface HasuraAuthInterface {

    String base_url = "https://auth.batterer83.hasura-app.io";

    @GET("facebook/authenticate")
    Call<LoginResponse> getLoginResponse(@Query("access_token") String accesstoken);

}


