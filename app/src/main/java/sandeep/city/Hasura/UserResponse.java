package sandeep.city.Hasura;

/**
 * Created by sandeep_chi on 11/18/2017.
 */

public class UserResponse {

    public int getHasura_id() {
        return hasura_id;
    }

    public String getFirstName() {
        return firstName;
    }

    int hasura_id;

    public void setHasura_id(int hasura_id) {
        this.hasura_id = hasura_id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    String firstName;


}
