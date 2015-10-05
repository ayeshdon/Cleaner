package android.cp.hseya.com.cleaner.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.cp.hseya.com.cleaner.MainApplication;
import android.cp.hseya.com.cleaner.R;
import android.cp.hseya.com.cleaner.adapter.ItemActivityExpandListAdapter;
import android.cp.hseya.com.cleaner.adapter.SubActivityItemAdapter;
import android.cp.hseya.com.cleaner.api.API;
import android.cp.hseya.com.cleaner.api.APICall;
import android.cp.hseya.com.cleaner.api.APICallback;
import android.cp.hseya.com.cleaner.api.APICaller;
import android.cp.hseya.com.cleaner.api.APIResult;
import android.cp.hseya.com.cleaner.bean.SubActivityBean;
import android.cp.hseya.com.cleaner.enumClass.ActivityAction;
import android.cp.hseya.com.cleaner.json.JsonParser;
import android.cp.hseya.com.cleaner.listener.ActivityActionListener;
import android.cp.hseya.com.cleaner.utils.InternetConnection;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SubTaskActivity extends ActionBarActivity implements APICallback, ActivityActionListener {

    private ProgressDialog progressDialog;
    private MainApplication application;
    private int actId,dayFlag,specID;
    private String clientName,address,level,actName,done,endDate;
    private TextView clientNameSubTextView,levelNameSubTextView,actNameSubTextView;
    private Toolbar toolbar;
    private ArrayList<SubActivityBean> subActivityList;
    private ExpandableListView subLvExp;
    private SubActivityItemAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            actId      = getIntent().getExtras().getInt("ACT_ID");
            dayFlag    = getIntent().getExtras().getInt("DAY_FLAG");
            specID     = getIntent().getExtras().getInt("SPEC_ID");
            clientName = getIntent().getExtras().getString("CLIENT_NAME");
            address    = getIntent().getExtras().getString("ADDRESS");
            level      = getIntent().getExtras().getString("LEVEL");
            done       = getIntent().getExtras().getString("DONE");
            endDate    = getIntent().getExtras().getString("END_DATE");
            actName    = getIntent().getExtras().getString("ACT_NAME");


            application = (MainApplication) getApplication();

            setContentView(R.layout.activity_sub_task);

            UIInitialize();

            loadDataFromAPI();

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.exception), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void UIInitialize() throws  Exception{
        try {

            clientNameSubTextView = (TextView) findViewById(R.id.clientNameSubTextView);
            levelNameSubTextView  = (TextView) findViewById(R.id.levelNameSubTextView);
            actNameSubTextView    = (TextView) findViewById(R.id.actNameSubTextView);


            subLvExp = (ExpandableListView) findViewById(R.id.subLvExp);
            subLvExp.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                int previousGroup = -1;

                @Override
                public void onGroupExpand(int groupPosition) {
                    if(groupPosition != previousGroup)
                        subLvExp.collapseGroup(previousGroup);
                    previousGroup = groupPosition;
                }
            });



            clientNameSubTextView.setText(clientName);
            levelNameSubTextView.setText(address+", "+level);
            actNameSubTextView.setText(actName);

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


    private void loadDataFromAPI() {
        try {

            if (InternetConnection.getInstance(this).isConnectingToInternet()){
                showProgress();

                APICaller apiCaller = new APICaller(this, null);
                APICall apiCall = new APICall(API.SUB_ACT+specID+"/"+ actId+"/"+dayFlag,
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
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home){

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
    public void onAPICallComplete(APIResult apiResult) {
        hideProgress();

        try {

            subActivityList =
                    JsonParser.getInstance().getSubActivityList(apiResult.getResultJson(),done,endDate);

            setListView();


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setListView(){
        try {



            if (subActivityList == null){

                showAlert(getResources().getString(R.string.ops),
                        getResources().getString(R.string.no_data_found));

            }else if(subActivityList.size() == 0){

                showAlert(getResources().getString(R.string.ops),
                        getResources().getString(R.string.no_data_found));

            }else{

                listAdapter = new SubActivityItemAdapter(this,subActivityList,this);
                subLvExp.setAdapter(listAdapter);

            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityActionClick(ActivityAction action, int pos,int flag) {
        try {



            SubActivityBean bean = subActivityList.get(pos);


            Intent callSubDetails = new Intent(this, SubTaskDetailsActivity.class);
            callSubDetails.putExtra("ACT_ID",actId);
            callSubDetails.putExtra("ACT_NAME",actName);
            callSubDetails.putExtra("CLIENT_NAME",clientName);
            callSubDetails.putExtra("ADDRESS",address);
            callSubDetails.putExtra("LEVEL",level);
            callSubDetails.putExtra("SPEC_ID",specID);
            callSubDetails.putExtra("DAYS_FLAG",dayFlag);
            callSubDetails.putExtra("DONE",done);
            callSubDetails.putExtra("END_DATE",endDate);
            callSubDetails.putExtra("SUB_ACT_ID",bean.getId());
            callSubDetails.putExtra("FLAG", flag);
            callSubDetails.putExtra("SUB_ACT_NAME", bean.getDescription());

            startActivity(callSubDetails);
            finish();



        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
