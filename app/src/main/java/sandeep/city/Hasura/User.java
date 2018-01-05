package sandeep.city.Hasura;

/**
 * Created by sandeep_chi on 11/18/2017.
 */

class User {

    String firstName;
    String lastName;
    String email;
    int mobile;
    int hasura_id;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public int getHasura_id() {
        return hasura_id;
    }

    public void setHasura_id(int hasura_id) {
        this.hasura_id = hasura_id;
    }

    public User (String firstName, String lastName, String email, int mobile, int hasura_id){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this. hasura_id = hasura_id;
    }
}
