package android.cp.hseya.com.cleaner.api;

public class API {

    public static final String PROTOCOL = "http";
    public static final String API_HOST = "www.clientkey.com.au/rest/CK/v1/";


    public static final String USER_LOGIN             = PROTOCOL + "://" + API_HOST + "login";
    public static final String JOB_COUNT              = PROTOCOL + "://" + API_HOST + "jobcount/";
    public static final String SUB_ACT_SUBMIT         = PROTOCOL + "://" + API_HOST + "inspect";
    public static final String ACT_SUBMIT             = PROTOCOL + "://" + API_HOST + "inspectActivity";
    public static final String JOB_PENDING_SPECS      = PROTOCOL + "://" + API_HOST + "jobspecsassigned/";
    public static final String JOB_DAILY_COUNT        = PROTOCOL + "://" + API_HOST + "jobcountDaily/";
    public static final String JOB_SPECS              = PROTOCOL + "://" + API_HOST + "jobspecs/";
    public static final String SUB_ACT                = PROTOCOL + "://" + API_HOST + "subactivities/";
    public static final String POSTPONEREASON         = PROTOCOL + "://" + API_HOST + "postponereasons";
    public static final String POSTPONE               = PROTOCOL + "://" + API_HOST + "postpone";


}
