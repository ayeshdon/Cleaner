package android.cp.hseya.com.cleaner.json;


import android.cp.hseya.com.cleaner.bean.ActivityBean;
import android.cp.hseya.com.cleaner.bean.JobDisplayBean;
import android.cp.hseya.com.cleaner.bean.JobSpecBean;
import android.cp.hseya.com.cleaner.bean.JsonStatusBean;
import android.cp.hseya.com.cleaner.bean.LoginResponseBean;
import android.cp.hseya.com.cleaner.bean.PostponeBean;
import android.cp.hseya.com.cleaner.bean.ReasonBean;
import android.cp.hseya.com.cleaner.bean.SubActivityBean;
import android.cp.hseya.com.cleaner.bean.SuccessBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

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



    public JSONObject getActivityInspection(String date,int actId,int specId,int freqId,
                                            float rating,String comment,String email)throws  Exception{
        try {

            JSONObject rootJson = new JSONObject();

            rootJson.put(DATE,date);
            rootJson.put(SPECSID,specId);
            rootJson.put(FREQUENCY,freqId);
            rootJson.put(ACTIVITY,actId);
            rootJson.put(RATING,rating);
            rootJson.put(COMMENT,comment);
            rootJson.put(EMAIL,email);



            return rootJson;

        }catch (Exception e){
            throw e;
        }
    }


    /**
     * get sub activity sent json data
     */
    public JSONObject getSubActivityInspection(String date,int actId,int subActId,int specId,int freqId,
                                               float rating,String comment,String email)throws  Exception{
        try {

            JSONObject rootJson = new JSONObject();

            rootJson.put(DATE,date);
            rootJson.put(SPECSID,specId);
            rootJson.put(FREQUENCY,freqId);
            rootJson.put(ACTIVITY,actId);
            rootJson.put(SUBACTIVITY,subActId);
            rootJson.put(RATING,rating);
            rootJson.put(COMMENT,comment);
            rootJson.put(EMAIL,email);




            return rootJson;

        }catch (Exception e){
            throw e;
        }
    }

    /**
     * get JsonObject send to API for do postpone
     * @param dataBean
     * @return
     * @throws Exception
     */
    public JSONObject getPostponeJson(PostponeBean dataBean)throws  Exception{
        try {

            JSONObject rootJson = new JSONObject();
            rootJson.put(SPECS_ID,dataBean.getActID());
            rootJson.put(STATUS,dataBean.getStatus());
            rootJson.put(REMARKS,dataBean.getRemark());

            return rootJson;
        }catch (Exception e){
            throw e;
        }

    }


    /**
     * get postpone reason list form API
     * @param bean
     * @param object
     * @return
     * @throws Exception
     */
    public ArrayList<ReasonBean> getPostponeReasonList(ReasonBean bean,JSONObject object)throws  Exception{
        try {

            ArrayList<ReasonBean> dataList = new ArrayList<ReasonBean>();
            dataList.add(bean);

            JSONArray rootArray = object.getJSONArray(DATA);

            for (int i = 0; i < rootArray.length(); i++) {

                JSONObject childJson = rootArray.getJSONObject(i);

                ReasonBean dataBean = new ReasonBean();

                dataBean.setId(childJson.getInt(ID));
                dataBean.setDescription(childJson.getString(DESCRIPTION));

                dataList.add(dataBean);

            }

            return dataList;

        }catch (Exception e){
            throw e;
        }
    }

    public boolean isSuccess(JSONObject jsonObj) throws Exception{
        try {

            return  jsonObj.getBoolean(ERROR);
        }catch (Exception e){
            throw e;
        }
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
     * Get display finished activity data
     * @param jsonObj
     * @return
     * @throws Exception
     */
    public JobDisplayBean getDisplayData(JSONObject jsonObj) throws Exception {
        try {
            JobDisplayBean bean = new JobDisplayBean();

            JSONObject jsonObject = jsonObj.getJSONObject(INSPECTION);

            bean.setComment(jsonObject.getString(COMMENT));
            bean.setRating(jsonObject.getInt(RATING));


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
            bean.setId(jsonObj.optString(EMPLOYEEID));

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


    /**
     * get job count api call response
     * @param msgjson
     * @return
     * @throws Exception
     */
    public int getJobCount(JSONObject msgjson)throws  Exception{
        try {

            boolean isError = msgjson.getBoolean(ERROR);

            if (isError){
                return 0;
            }else{
                String count = msgjson.getString(COUNT);

                try {

                    int val = Integer.parseInt(count);

                    return val;

                }catch (Exception e){
                    return 0;
                }


            }

        }catch (Exception e){
            throw e;
        }
    }

    /**
     * get job specification list
     * @return
     * @throws Exception
     */
    public ArrayList<JobSpecBean> getJobSpecList(JSONObject object) throws  Exception{
        try {

            ArrayList<JobSpecBean> dataList = new ArrayList<JobSpecBean>();

            boolean isError = object.getBoolean(ERROR);

            if (!isError){

                JSONArray jsonArray = object.optJSONArray(DATA);

                if (jsonArray == null){

                }else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JobSpecBean dataBean = new JobSpecBean();


                        JSONObject childJson = jsonArray.getJSONObject(i);

                        dataBean.setId(childJson.getInt(SPEC_ID));
                        dataBean.setClientName(childJson.getString(CLIENT_NAME));
                        dataBean.setLevelName(childJson.getString(LEVEL_NAME));
                        dataBean.setLocationName(childJson.getString(LOCATION_NAME));
                        dataBean.setDate(childJson.getString(DATE));

                        dataList.add(dataBean);

                    }
                }

            }else{

            }


            return dataList;

        }catch (Exception e){
            throw  e;
        }
    }


    /**
     * get Activity list
     * @return
     * @throws Exception
     */
    public ArrayList<ActivityBean> getActivityList(JSONObject object)throws  Exception{
        try {

            ArrayList<ActivityBean> dataList = new ArrayList<ActivityBean>();
            boolean isError = object.getBoolean(ERROR);

            if (!isError){

                JSONArray jsonArray = object.optJSONArray(SPECS);

                if (jsonArray != null){

                    for (int i =0; i <jsonArray.length();i++){
                        JSONObject childJson = jsonArray.getJSONObject(i);

                        ActivityBean dataBean = new ActivityBean();

                        dataBean.setId(childJson.getInt(ACTIVITYID));
                        dataBean.setFlag(childJson.getInt(FLAG));
                        dataBean.setActivity(childJson.getString(DESCRIPTION));
                        dataBean.setEndDate(childJson.optString(END_DATE, "None"));
                        dataBean.setDoneName(childJson.optString(EMPLOYEE, "None"));


                        dataList.add(dataBean);
                    }

                }

            }else{

            }



            return dataList;

        }catch (Exception e){
            throw  e;
        }
    }


    public ArrayList<SubActivityBean> getSubActivityList(JSONObject object,String doneName,String endDate)throws  Exception{
        try {

            ArrayList<SubActivityBean> dataList = new ArrayList<SubActivityBean>();
            boolean isError = object.getBoolean(ERROR);

            if (!isError){

                JSONArray jsonArray = object.optJSONArray(SPECS);

                if (jsonArray != null){

                    for (int i =0; i <jsonArray.length();i++){
                        JSONObject childJson = jsonArray.getJSONObject(i);

                        SubActivityBean dataBean = new SubActivityBean();

                        dataBean.setId(childJson.getInt(SUB_ACTIVITY_ID));
                        dataBean.setFlag(childJson.getInt(FLAG));
                        dataBean.setDate(endDate);
                        dataBean.setDoneName(doneName);
                        dataBean.setDescription(childJson.getString(DESCRIPTION));

                        dataList.add(dataBean);
                    }

                }

            }else{

            }



            return dataList;

        }catch (Exception e){
            throw  e;
        }
    }
}
