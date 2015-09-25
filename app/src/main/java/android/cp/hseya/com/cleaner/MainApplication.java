package android.cp.hseya.com.cleaner;

import android.app.Application;
import android.cp.hseya.com.cleaner.bean.JobSpecBean;
import android.cp.hseya.com.cleaner.listener.InspectionFragmentActionListener;

import java.util.ArrayList;

/**
 * Created by ayesh on 8/9/2015.
 */
public class MainApplication extends Application {

    public InspectionFragmentActionListener fragmentActionListener;
    public String employeeID;
    public int dayJobCount   = 0;
    public int weekJobCount  = 0;
    public int monthJobCount = 0;

    public ArrayList<JobSpecBean> jobSpecList;


}
