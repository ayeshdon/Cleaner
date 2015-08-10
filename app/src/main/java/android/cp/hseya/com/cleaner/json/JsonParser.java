package android.cp.hseya.com.cleaner.json;


import android.cp.hseya.com.cleaner.bean.JsonStatusBean;
import android.cp.hseya.com.cleaner.bean.LoginResponseBean;
import android.cp.hseya.com.cleaner.bean.SuccessBean;

import org.json.JSONObject;

/**
 * Created by ayesh on 5/17/2015.
 */
public class JsonParser implements JsonTag {

    private static JsonParser instance;

    public static JsonParser getInstance() {

        if (instance == null) {
            instance = new JsonParser();
        }

        return instance;
    }


    /**
     * json response status process
     *
     * @param jsonObj
     * @return
     * @throws Exception
     */
    public JsonStatusBean getJsonStatus(JSONObject jsonObj) throws Exception {
        try {
            JsonStatusBean bean = new JsonStatusBean();

            bean.setStatusCode(jsonObj.getInt(STATUS_CODE));
            bean.setMessage(jsonObj.optString(MESSAGE));

            return bean;

        } catch (Exception e) {
            throw e;

        }
    }


    /**
     * json success response process
     *
     * @param jsonObj
     * @return
     * @throws Exception
     */
    public SuccessBean getJsonSuccess(JSONObject jsonObj) throws Exception {
        try {
            SuccessBean bean = new SuccessBean();

            bean.setSuccess(jsonObj.getBoolean(SUCCESS));
            bean.setMessage(jsonObj.optString(MESSAGE));

            return bean;

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * process login success respose date
     *
     * @param jsonObj
     * @return
     * @throws Exception
     */
    public LoginResponseBean getJsonloginResponse(JSONObject jsonObj) throws Exception {
        try {
            LoginResponseBean bean = new LoginResponseBean();

            bean.setName(jsonObj.optString(NAME));

            return bean;

        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * user login request json
     *
     * @param userName - email
     * @param password - password
     * @return
     * @throws Exception
     */
    public JSONObject getLoginJson(String userName, String password) throws Exception {
        try {

            JSONObject rootJson = new JSONObject();
            rootJson.put(EMAIL, userName);
            rootJson.put(PASSWORD, password);


            return rootJson;

        } catch (Exception e) {
            throw e;
        }
    }


}
