package sandeep.city.POJO;

import java.util.ArrayList;

/**
 * Created by sandeep on 26/6/15.
 */
public class SingleGroup {

    int id,no_hours,no_vols,status;
    String title;
    String place;
    String date;
    String comments;


    public SingleGroup(int id, int hours, int vols, int status, String title, String place, String date, String comments){
        this.id=id;
        this.title=title;
        this.place=place;
        this.date=date;
        this.comments=comments;
        this.no_hours=hours;
        this.no_vols=vols;
        this.status=status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNo_hours() {
        return no_hours;
    }

    public void setNo_hours(int no_hours) {
        this.no_hours = no_hours;
    }

    public int getNo_vols() {
        return no_vols;
    }

    public void setNo_vols(int no_vols) {
        this.no_vols = no_vols;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
