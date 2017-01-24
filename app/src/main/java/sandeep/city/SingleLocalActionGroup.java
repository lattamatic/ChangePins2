package sandeep.city;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by sandeep_chi on 1/24/2017.
 */

public class SingleLocalActionGroup {

    int id, noOfHours, noOfVols;
    String title, location, date;
    List<String> comments;

    public SingleLocalActionGroup(int noOfHours, int noOfVols, String title, String location, String date, List<String> comments){
        this.noOfHours = noOfHours;
        this.noOfVols = noOfVols;
        this.title = title;
        this.location = location;
        this.date = date;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNoOfHours() {
        return noOfHours;
    }

    public void setNoOfHours(int noOfHours) {
        this.noOfHours = noOfHours;
    }

    public int getNoOfVols() {
        return noOfVols;
    }

    public void setNoOfVols(int noOfVols) {
        this.noOfVols = noOfVols;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }
}
