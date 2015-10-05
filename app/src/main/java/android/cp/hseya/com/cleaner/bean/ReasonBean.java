package android.cp.hseya.com.cleaner.bean;

/**
 * Created by ayesh on 9/8/2015.
 */
public class ReasonBean {

    private int id;
    private String description;

    public ReasonBean(){}

    public ReasonBean(int id, String description){
        this.id = id;
        this.description = description;
    }

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
}
