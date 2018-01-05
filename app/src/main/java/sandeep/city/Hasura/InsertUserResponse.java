package sandeep.city.Hasura;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sandeep_chi on 11/18/2017.
 */

public class InsertUserResponse {

    public int getAffectedRows() {
        return affectedRows;
    }

    public ArrayList<UserResponse> getReturning() {
        return returning;
    }

    @SerializedName("affected_rows")
    int affectedRows;

    @SerializedName("returning")
    ArrayList<UserResponse> returning;


}
