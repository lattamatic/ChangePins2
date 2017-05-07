package sandeep.city.RetrofitInterfaces;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import sandeep.city.POJO.SingleReport;

/**
 * Created by sandeep_chi on 5/6/2017.
 */

public interface ApiInterface {
    @Multipart
    @POST("/api/postReport")
    Call<SingleReport> submitReport(@Header("Authorization") String authorization,
                                    @Part("file\"; filename=\"pp.png\" ") RequestBody file,
                                    @Part("category") RequestBody category,
                                    @Part("title") RequestBody title,
                                    @Part("description") RequestBody description);
}
