package android.cp.hseya.com.cleaner.bean;

/**
 * Created by ayesh on 8/18/2015.
 */
public class SubActivityBean {

    private int id;
    private int flag;
    private String description;
    private String doneName;
    private String date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoneName() {
        return doneName;
    }

    public void setDoneName(String doneName) {
        this.doneName = doneName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
