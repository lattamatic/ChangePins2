package sandeep.city.POJO;

/**
 * Created by sandeep_chi on 3/8/2017.
 */

public class SingleReport {

    long id;
    String title;
    String category;
    String description;
    String image_path;
    long place_id;

    public SingleReport(){

    }

    public SingleReport(long id, String title, String category, String description, String image_path, long place_id){
        this.id = id;
        this.title = title;
        this.category = category;
        this.description = description;
        this.image_path = image_path;
        this.place_id = place_id;
    }

    public SingleReport(String title, String category, String description, String image_path, long place_id){
        this.title = title;
        this.category = category;
        this.description = description;
        this.image_path = image_path;
        this.place_id = place_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public long getPlace_id(){
        return place_id;
    }

    public void setPlace_id(long place_id){
        this.place_id = place_id;
    }
}
