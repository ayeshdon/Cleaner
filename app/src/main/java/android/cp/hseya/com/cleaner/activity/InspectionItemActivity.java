package android.cp.hseya.com.cleaner.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.cp.hseya.com.cleaner.MainApplication;
import android.cp.hseya.com.cleaner.R;
import android.cp.hseya.com.cleaner.adapter.ItemActivityExpandListAdapter;
import android.cp.hseya.com.cleaner.api.API;
import android.cp.hseya.com.cleaner.api.APICall;
import android.cp.hseya.com.cleaner.api.APICallback;
import android.cp.hseya.com.cleaner.api.APICaller;
import android.cp.hseya.com.cleaner.api.APIResult;
import android.cp.hseya.com.cleaner.bean.ActivityBean;
import android.cp.hseya.com.cleaner.enumClass.ActivityAction;
import android.cp.hseya.com.cleaner.json.JsonParser;
import android.cp.hseya.com.cleaner.listener.ActionbarTitleClickListener;
import android.cp.hseya.com.cleaner.listener.ActivityActionListener;
import android.cp.hseya.com.cleaner.utils.InternetConnection;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class InspectionItemActivity extends ActionBarActivity implements ActionbarTitleClickListener, APICallback, ActivityActionListener {


    private int specid,dayFlag;
    private Toolbar toolbar;
    private String clientName;
    private String levelName;
    private String locationName;
    private TextView levelNameTextView,clientNameTextView;
    private ProgressDialog progressDialog;
    private MainApplication application;
    private ArrayList<ActivityBean> activityList;
    private ExpandableListView lvExp;
    private ItemActivityExpandListAdapter epndListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            specid       = getIntent().getExtras().getInt("ID");
            dayFlag      = getIntent().getExtras().getInt("DAY_FLAG");

            clientName   = getIntent().getExtras().getString("CLIENTNAME");
            levelName    = getIntent().getExtras().getString("LEVEL");
            locationName = getIntent().getExtras().getString("ADDRESS");

            application = (MainApplication)getApplication();


            setContentView(R.layout.activity_inspection_item);

            showMenuIcon();

            UIInitialize();

            loadDataFromAPI();

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.exception), Toast.LENGTH_LONG).show();
            finish();
        }
    }
    private void showMenuIcon() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");

            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void UIInitialize() throws Exception{
        try {

            levelNameTextView = (TextView) findViewById(R.id.levelNameTextView);
            clientNameTextView = (TextView) findViewById(R.id.clientNameTextView);
            clientNameTextView.setText(clientName);
            levelNameTextView.setText(locationName+", "+levelName);

            lvExp = (ExpandableListView) findViewById(R.id.lvExp);

            lvExp.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                int previousGroup = -1;

                @Override
                public void onGroupExpand(int groupPosition) {
                    if(groupPosition != previousGroup)
                        lvExp.collapseGroup(previousGroup);
                    previousGroup = groupPosition;
                }
            });

            toolbar = (Toolbar) findViewById(R.id.tool_bar);
            toolbar.setTitle("Test");
            setSupportActionBar(toolbar);


            if (null != toolbar) {
                toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBack();
                    }
                });

            }


        }catch (Exception e){
            throw e;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadDataFromAPI();
    }

    private void loadDataFromAPI() {
        try {

            if (InternetConnection.getInstance(this).isConnectingToInternet()){
                showProgress();

                APICaller apiCaller = new APICaller(this, null);
                APICall apiCall = new APICall(API.JOB_SPECS+specid+"/"+dayFlag,
                        APICall.APICallMethod.GET, this);
                apiCall.setJsonSend(null);
                apiCaller.execute(apiCall);

            }else{

                showAlert(getResources().getString(R.string.ops),
                        getResources().getString(R.string.no_internet));

            }

        }catch (Exception e){
            hideProgress();
            throw e;
        }
    }




    private void showProgress() {
        try {

            progressDialog = ProgressDialog.show(this, "", "Please wait...");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void hideProgress() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }



    private void showAlert(String title,String msg){
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            alertDialogBuilder.setTitle(title);

            alertDialogBuilder
                    .setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }else if(id == android.R.id.home){

            callBack();

            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTitleClick() {
        callBack();
    }

    private void callBack() {
        finish();
    }

    @Override
    public void onBackPressed() {
        callBack();
    }

    @Override
    public void onAPICallComplete(APIResult apiResult) {
        hideProgress();

        try {

            if (apiResult.getURL().equals(API.JOB_SPECS+specid+"/"+dayFlag)){


                activityList = JsonParser.getInstance().getActivityList(apiResult.getResultJson());
                listViewAdapter();

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void listViewAdapter(){
        try {

            if (activityList == null){

                showAlert(getResources().getString(R.string.ops),
                        getResources().getString(R.string.no_data_found));

            }else if(activityList.size() == 0){

                showAlert(getResources().getString(R.string.ops),
                        getResources().getString(R.string.no_data_found));

            }else{

                epndListAdapter = new ItemActivityExpandListAdapter(this,activityList,this);
                lvExp.setAdapter(epndListAdapter);

            }




        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityActionClick(ActivityAction action,int pos,int flag) {
        try {
            String actiName = activityList.get(pos).getActivity();
            String doneName = activityList.get(pos).getDoneName();
            String endDate  = activityList.get(pos).getEndDate();


            if (action == ActivityAction.SUB_ACTVITY){

                Intent callSub = new Intent(this,SubTaskActivity.class);


                callSub.putExtra("ACT_ID",activityList.get(pos).getId());
                callSub.putExtra("SPEC_ID",specid);
                callSub.putExtra("CLIENT_NAME",clientName);
                callSub.putExtra("SPEC_ID",specid);
                callSub.putExtra("DAYS_FLAG",dayFlag);
                callSub.putExtra("ADDRESS",locationName);
                callSub.putExtra("LEVEL",levelName);
                callSub.putExtra("ACT_NAME",actiName);
                callSub.putExtra("DONE",doneName);
                callSub.putExtra("DAY_FLAG",dayFlag);
                callSub.putExtra("END_DATE",endDate);

                startActivity(callSub);



            }else if(action == ActivityAction.SUB_DETAILS){


                Intent callSubDetails = new Intent(this, SubTaskDetailsActivity.class);

                callSubDetails.putExtra("ACT_ID",activityList.get(pos).getId());
                callSubDetails.putExtra("CLIENT_NAME",clientName);
                callSubDetails.putExtra("SPEC_ID",specid);
                callSubDetails.putExtra("DAYS_FLAG",dayFlag);
                callSubDetails.putExtra("ADDRESS",locationName);
                callSubDetails.putExtra("LEVEL",levelName);
                callSubDetails.putExtra("DONE",doneName);
                callSubDetails.putExtra("ACT_NAME",actiName);
                callSubDetails.putExtra("END_DATE",endDate);
                callSubDetails.putExtra("IS_ACT",true);
                callSubDetails.putExtra("FLAG", flag);

                if (flag == 1){
                    if (InternetConnection.getInstance(this).isConnectingToInternet()){

                        startActivity(callSubDetails);

                    }else{

                        showAlert(getResources().getString(R.string.ops),
                                getResources().getString(R.string.no_internet));
                    }

                }else{

                    startActivity(callSubDetails);
                }





            }else if(action == ActivityAction.POSTPONE){

                Intent callPostpone = new Intent(this, PostponeActivity.class);

                callPostpone.putExtra("ACT_ID",activityList.get(pos).getId());
                callPostpone.putExtra("CLIENT_NAME",clientName);
                callPostpone.putExtra("ADDRESS",locationName);
                callPostpone.putExtra("LEVEL",levelName);
                callPostpone.putExtra("DONE",doneName);
                callPostpone.putExtra("ACT_NAME",actiName);
                callPostpone.putExtra("END_DATE",endDate);


                startActivity(callPostpone);

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
