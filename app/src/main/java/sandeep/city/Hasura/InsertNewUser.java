package sandeep.city.Hasura;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sandeep_chi on 11/18/2017.
 */

public class InsertNewUser {


    @SerializedName("type")
    String type = "insert";

    @SerializedName("args")
    Args args;

    public InsertNewUser(String firstName, String lastName, String email,int mobile, int hasura_id) {
        args = new Args();
        args.objects = new ArrayList<>();
        User user = new User(firstName, lastName, email, mobile, hasura_id);
        args.objects.add(user);
    }

    class Args {

        @SerializedName("table")
        String table = "CP_USERS";

        @SerializedName("objects")
        ArrayList<User> objects;

        @SerializedName("returning")
        String [] returning = {
                "hasura_id",
                "first_name"};
    }

}
