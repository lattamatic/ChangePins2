package sandeep.city.Hasura;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by sandeep_chi on 11/18/2017.
 */

public class HasuraFBResponse {

    @SerializedName("hasura_id")
    int hasuraId;

    @SerializedName("new_user")
    boolean isNewUser;

    @SerializedName("auth_token")
    String auth_token;

    @SerializedName("hasura_roles")
    Array hasuraRoles;

    public int getHasuraId() {
        return hasuraId;
    }

    public boolean getIsNewUser(){
        return isNewUser;
    }

    public String getAuth_token(){
        return auth_token;
    }

    public Array getHasuraRoles(){
        return hasuraRoles;
    }
}
