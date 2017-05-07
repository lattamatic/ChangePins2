package sandeep.city.POJO;

/**
 * Created by sandeep_chi on 1/24/2017.
 */

public class SingleStory {

    int id;
    String title;
    String author;
    String description;
    String date;


    public SingleStory(String title, String author, String description, String date){
        this.title = title;
        this.author = author;
        this.description = description;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
