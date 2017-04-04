package sandeep.city.POJO;

public class SinglePlace {


    String title, address;
    long latitute, longitude;
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

    public long getLatitute() {
        return latitute;
    }

    public void setLatitute(long latitute) {
        this.latitute = latitute;
    }

    public long getLongitude() {
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
