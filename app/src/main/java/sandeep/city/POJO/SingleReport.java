package sandeep.city.POJO;

/**
 * Created by sandeep on 22/6/15.
 */
public class SingleReport {

    int id;
    String title;
    String description;
    String img_link;
    double lat;
    double lon;
    String locAddress;
    String category;

    public SingleReport(){

    }

    public SingleReport(int id, String title, String desc, String img_link, double lat, double lon, String locAddress, String category){
        this.id= id;
        this.title = title;
        this.description = desc;
        this.img_link = img_link;
        this.lat = lat;
        this.lon = lon;
        this.locAddress = locAddress;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getLocAddress() {
        return locAddress;
    }

    public void setLocAddress(String locAddress) {
        this.locAddress = locAddress;
    }



}
