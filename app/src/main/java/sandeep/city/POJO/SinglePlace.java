package sandeep.city.POJO;

public class SinglePlace {


    String title, address;
    double latitute, longitude;
    long id;


    public SinglePlace(long id, String title, String address, long latitute, long longitude) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.latitute = latitute;
        this.longitude = longitude;
    }

    public SinglePlace(){

    }

    public SinglePlace(String title){
        this.title = title;
    }

    public SinglePlace(String title, String address, double latitute, double longitude){
        this.title = title;
        this.latitute = latitute;
        this.longitude = longitude;
        this.address = address;
    }
    public SinglePlace(long id, String title, String address, double latitute, double longitude){
        this.id = id;
        this.title = title;
        this.latitute = latitute;
        this.longitude = longitude;
        this.address = address;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitute() {
        return latitute;
    }

    public void setLatitute(long latitute) {
        this.latitute = latitute;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
