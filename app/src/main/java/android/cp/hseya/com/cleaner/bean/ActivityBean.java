package android.cp.hseya.com.cleaner.bean;

/**
 * Created by ayesh on 8/16/2015.
 */
public class ActivityBean {

    private int id;
    private String activity;
    private String endDate;
    private String doneName;
    private int flag = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getDoneName() {
        if (doneName == null){
            return "None";
        }
        return doneName;
    }

    public void setDoneName(String doneName) {
        this.doneName = doneName;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
