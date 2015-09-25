package android.cp.hseya.com.cleaner.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.cp.hseya.com.cleaner.ActivityMain;
import android.cp.hseya.com.cleaner.MainApplication;
import android.cp.hseya.com.cleaner.R;
import android.cp.hseya.com.cleaner.api.API;
import android.cp.hseya.com.cleaner.api.APICall;
import android.cp.hseya.com.cleaner.api.APICallback;
import android.cp.hseya.com.cleaner.api.APICaller;
import android.cp.hseya.com.cleaner.api.APIResult;
import android.cp.hseya.com.cleaner.bean.JobSpecBean;
import android.cp.hseya.com.cleaner.json.JsonParser;
import android.cp.hseya.com.cleaner.ui.CircularImageView;
import android.cp.hseya.com.cleaner.utils.InternetConnection;
import android.cp.hseya.com.cleaner.utils.Settings;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class DashBoardActivity extends ActionBarActivity implements View.OnClickListener, APICallback {



    private Toolbar toolbar;
    private CircularImageView profileImageView;
    private TextView profileNameTextView,inspectionCountTextView;
    private LinearLayout inspectionLinearLayout;
    private ProgressDialog progressDialog;
    private MainApplication application;
    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            application = (MainApplication)getApplication();

            setContentView(R.layout.activity_dash_board);

            settings = new Settings(this);

            application.employeeID = settings.getUserID();

            showMenuIcon();


            UIInitialize();

            loadDataFromAPI();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.exception), Toast.LENGTH_LONG).show();
        }

    }

    /**
     * load data from api
     */
    private void loadDataFromAPI() {
        try {

            if (InternetConnection.getInstance(this).isConnectingToInternet()){
                showProgress();

                APICaller apiCaller = new APICaller(this, null);
                APICall apiCall = new APICall(API.JOB_COUNT+application.employeeID,
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

    private void UIInitialize() throws  Exception{
        try {
            toolbar = (Toolbar) findViewById(R.id.tool_bar);
            toolbar.setTitle("");
            setSupportActionBar(toolbar);



            profileImageView = (CircularImageView)findViewById(R.id.profileImageView);
            profileImageView.setBorderWidth(0);

            inspectionCountTextView   = (TextView)findViewById(R.id.inspectionCountTextView);
            profileNameTextView       = (TextView)findViewById(R.id.profileNameTextView);
            profileNameTextView.setText(settings.getUserName());


            inspectionLinearLayout = (LinearLayout)findViewById(R.id.inspectionLinearLayout);
            inspectionLinearLayout.setOnClickListener(this);

        }catch (Exception e){
            throw e;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {

            return true;

        }else if (id == R.id.action_singout){
            sigoutAction();

            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    /*
    Profile signout action
     */
    private void sigoutAction() {
        try {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            alertDialogBuilder.setTitle("Logout");
            alertDialogBuilder
                    .setMessage("Are you wish to Logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            settings.setLoginAlready(false);
                            settings.setUserName(" ");

                            Intent callback = new Intent(DashBoardActivity.this, ActivityMain.class);
                            startActivity(callback);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
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
    public void onClick(View view) {
        try {

            switch (view.getId()){
                case R.id.inspectionLinearLayout:

                    Intent callinspection = new Intent(this,InspectionActivity.class);
                    startActivity(callinspection);


                    break;
            }

        }catch (Exception e){
            e.printStackTrace();
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
    public void onAPICallComplete(APIResult apiResult) {
        hideProgress();

        try {

            if (apiResult.getURL().equals(API.JOB_COUNT+application.employeeID)){

                JSONObject resultJson = apiResult.getResultJson();

                int inspectionCount = JsonParser.getInstance().getJobCount(resultJson);

                application.dayJobCount = inspectionCount;

                inspectionCountTextView.setText(""+inspectionCount);

                showProgress();

                APICaller apiCaller = new APICaller(this, null);
                APICall apiCall = new APICall(API.JOB_PENDING_SPECS+application.employeeID,
                        APICall.APICallMethod.GET, this);
                apiCall.setJsonSend(null);
                apiCaller.execute(apiCall);

            }else if(apiResult.getURL().equals(API.JOB_PENDING_SPECS+application.employeeID)){


                application.jobSpecList =
                        JsonParser.getInstance().getJobSpecList(apiResult.getResultJson());

                showProgress();

                APICaller apiCaller = new APICaller(this, null);
                APICall apiCall = new APICall(API.JOB_DAILY_COUNT+application.employeeID,
                        APICall.APICallMethod.GET, this);
                apiCall.setJsonSend(null);
                apiCaller.execute(apiCall);

            }else if(apiResult.getURL().equals(API.JOB_PENDING_SPECS+application.employeeID)){


                application.dayJobCount = JsonParser.getInstance().getJobCount(apiResult.getResultJson());

            }






        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
