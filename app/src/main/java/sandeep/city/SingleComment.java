package sandeep.city;

/**
 * Created by sandeep on 26/6/15.
 */
public class SingleComment {
    int id;
    String user_name;
    String comment;
    String time;

    SingleComment(String user_name,String comment,String time){
        this.user_name=user_name;
        this.comment=comment;
        this.time=time;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
