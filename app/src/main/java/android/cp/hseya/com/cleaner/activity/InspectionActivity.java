package android.cp.hseya.com.cleaner.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.cp.hseya.com.cleaner.MainApplication;
import android.cp.hseya.com.cleaner.adapter.InspectionFPAdapter;
import android.cp.hseya.com.cleaner.api.API;
import android.cp.hseya.com.cleaner.api.APICall;
import android.cp.hseya.com.cleaner.api.APICallback;
import android.cp.hseya.com.cleaner.api.APICaller;
import android.cp.hseya.com.cleaner.api.APIResult;
import android.cp.hseya.com.cleaner.json.JsonParser;
import android.cp.hseya.com.cleaner.listener.ActionbarTitleClickListener;
import android.cp.hseya.com.cleaner.listener.InspectionFragmentActionListener;
import android.cp.hseya.com.cleaner.ui.CommonUI;
import android.cp.hseya.com.cleaner.utils.Const;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.cp.hseya.com.cleaner.R;
import android.cp.hseya.com.cleaner.R;
import android.widget.TabHost;
import android.widget.Toast;

import org.json.JSONObject;

public class InspectionActivity extends ActionBarActivity implements ActionbarTitleClickListener,InspectionFragmentActionListener, APICallback {

    private ViewPager mPager;
    private MainApplication application;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            application = (MainApplication)getApplication();

            application.fragmentActionListener = this;

            final ActionBar customActionBar =  getSupportActionBar();


            customActionBar.hide();
            customActionBar.setIcon(null);

            customActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            CommonUI.getInstance(this).setCommonActionBar(customActionBar,this,true,false,
                    getResources().getString(R.string.inspection),true,this);


            customActionBar.show();


            setContentView(R.layout.activity_inspection);



            mPager = (ViewPager) findViewById(R.id.pager);

            FragmentManager fm = getSupportFragmentManager();

            ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener(){
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);

                    customActionBar.setSelectedNavigationItem(position);
                }
            };



            /** Setting the pageChange listener to the viewPager */
            mPager.setOnPageChangeListener(pageChangeListener);

            /** Creating an instance of FragmentPagerAdapter */
            InspectionFPAdapter fragmentPagerAdapter = new InspectionFPAdapter(fm);

            /** Setting the FragmentPagerAdapter object to the viewPager object */
            mPager.setAdapter(fragmentPagerAdapter);

            customActionBar.setDisplayShowTitleEnabled(true);

            /** Defining tab listener */
            ActionBar.TabListener tabListener = new ActionBar.TabListener() {

                @Override
                public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                }

                @Override
                public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {


                    mPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                }
            };

            /** Creating fragment1 Tab */
            ActionBar.Tab tab = customActionBar.newTab()
                    .setText("Day")
                    .setTabListener(tabListener);

            customActionBar.addTab(tab);

            /** Creating fragment2 Tab */
            ActionBar.Tab tab1 = customActionBar.newTab()
                    .setText("Week")
                    .setTabListener(tabListener);

            customActionBar.addTab(tab1);

            /** Creating fragment3 Tab */
            ActionBar.Tab tab2 = customActionBar.newTab()
                    .setText("Month")
                    .setTabListener(tabListener);

            customActionBar.addTab(tab2);

            loadAPIData();




        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.exception), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void loadAPIData() throws Exception{
        try {


            showProgress();



            APICaller apiCaller = new APICaller(this, null);
            APICall apiCall = new APICall(API.JOB_GENERIC_COUNT+application.employeeID+"/"+ Const.WEEK_FRQ,
                    APICall.APICallMethod.GET, this);
            apiCall.setJsonSend(null);
            apiCaller.execute(apiCall);


        }catch (Exception e){
            e.printStackTrace();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inspection, menu);
        return true;
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

    private void callBack() {
        finish();
    }

    @Override
    public void onBackPressed() {
        callBack();
    }

    @Override
    public void onTitleClick() {
        callBack();
    }

    @Override
    public void onFragmentMoverClick(int fragment, int side) {
        try {


            if ((fragment == 1) && (side == 2)){
                mPager.setCurrentItem(1);

            }else if ((fragment == 2) && (side == 1)) {
                mPager.setCurrentItem(0);

            }else if ((fragment == 2) && (side == 2)) {
                mPager.setCurrentItem(2);

            }else if ((fragment == 3) && (side == 1)) {
                mPager.setCurrentItem(1);

            }else if ((fragment == 3) && (side == 2)) {
                mPager.setCurrentItem(0);

            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onAPICallComplete(APIResult apiResult) {

        hideProgress();
        try {

            if(apiResult.getURL().equals(API.JOB_PENDING_SPECS+application.employeeID+"/"+ Const.WEEK_FRQ)){


                application.jobSpecWeekList =
                        JsonParser.getInstance().getJobSpecList(apiResult.getResultJson());

//                showProgress();
//
//
//
//                APICaller apiCaller = new APICaller(this, null);
//                APICall apiCall = new APICall(API.JOB_GENERIC_COUNT+application.employeeID+"/"+ Const.MONTH_FRQ,
//                        APICall.APICallMethod.GET, this);
//                apiCall.setJsonSend(null);
//                apiCaller.execute(apiCall);



            } else if(apiResult.getURL().equals(API.JOB_GENERIC_COUNT+application.employeeID+"/"+ Const.WEEK_FRQ)){

                JSONObject resultJson = apiResult.getResultJson();

                int inspectionCount = JsonParser.getInstance().getJobCount(resultJson);

                application.weekJobCount = inspectionCount;

//                weekCountTextView.setText(""+inspectionCount);

                showProgress();

                APICaller apiCaller = new APICaller(this, null);
                APICall apiCall = new APICall(API.JOB_PENDING_SPECS+application.employeeID+"/"+ Const.WEEK_FRQ,
                        APICall.APICallMethod.GET, this);
                apiCall.setJsonSend(null);
                apiCaller.execute(apiCall);

            }else if(apiResult.getURL().equals(API.JOB_PENDING_SPECS+application.employeeID+"/"+ Const.MONTH_FRQ)){


                application.jobSpecMonthList =
                        JsonParser.getInstance().getJobSpecList(apiResult.getResultJson());





            } else if(apiResult.getURL().equals(API.JOB_GENERIC_COUNT+application.employeeID+"/"+ Const.MONTH_FRQ)){

                JSONObject resultJson = apiResult.getResultJson();

                int inspectionCount = JsonParser.getInstance().getJobCount(resultJson);

                application.monthJobCount = inspectionCount;

//                monthCountTextView.setText(""+inspectionCount);

//                showProgress();
//
//                APICaller apiCaller = new APICaller(this, null);
//                APICall apiCall = new APICall(API.JOB_PENDING_SPECS+application.employeeID+"/"+ Const.MONTH_FRQ,
//                        APICall.APICallMethod.GET, this);
//                apiCall.setJsonSend(null);
//                apiCaller.execute(apiCall);

            }

        }catch (Exception e){
            hideProgress();
            e.printStackTrace();
        }

    }
}
