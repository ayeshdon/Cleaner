package android.cp.hseya.com.cleaner.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.cp.hseya.com.cleaner.api.API;
import android.cp.hseya.com.cleaner.api.APICall;
import android.cp.hseya.com.cleaner.api.APICallback;
import android.cp.hseya.com.cleaner.api.APICaller;
import android.cp.hseya.com.cleaner.api.APIResult;
import android.cp.hseya.com.cleaner.bean.JobDisplayBean;
import android.cp.hseya.com.cleaner.bean.SuccessBean;
import android.cp.hseya.com.cleaner.json.JsonParser;
import android.cp.hseya.com.cleaner.utils.InternetConnection;
import android.cp.hseya.com.cleaner.utils.Settings;
import android.cp.hseya.com.cleaner.utils.Utils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.cp.hseya.com.cleaner.R;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class SubTaskDetailsActivity extends ActionBarActivity implements View.OnClickListener,APICallback {

    private String clientName,address,level,actName,done,endDate,subActName;
    private int actId,subActId,flag,specId,daysFlag;
    private Toolbar toolbar;
    private TextView clientNameSubDetailsTextView,levelNameSubDetalsTextView,subActDetailsNameTextView,
            subActDetailsDoneTextView;
    private boolean isAct = true;
    private Button resumeButton,saveAndEndButton;
    private RatingBar detailsRatingBar;
    private EditText commentEditText;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            actId      = getIntent().getExtras().getInt("ACT_ID");
            specId     = getIntent().getExtras().getInt("SPEC_ID");
            daysFlag   = getIntent().getExtras().getInt("DAYS_FLAG");
            flag       = getIntent().getExtras().getInt("FLAG");
            clientName = getIntent().getExtras().getString("CLIENT_NAME");
            address    = getIntent().getExtras().getString("ADDRESS");
            level      = getIntent().getExtras().getString("LEVEL");
            done       = getIntent().getExtras().getString("DONE");
            endDate    = getIntent().getExtras().getString("END_DATE");
            actName    = getIntent().getExtras().getString("ACT_NAME");



            try {
                isAct = getIntent().getExtras().getBoolean("IS_ACT");

            }catch (Exception e){
                isAct = false;
            }

            if (!isAct){

                subActId   = getIntent().getExtras().getInt("SUB_ACT_ID");
                subActName = getIntent().getExtras().getString("SUB_ACT_NAME");

            }





            setContentView(R.layout.activity_sub_task_details);


            UIInitialize();


        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.exception), Toast.LENGTH_LONG).show();
            callBack();
        }

    }

    private void UIInitialize() throws  Exception{
        try {


            clientNameSubDetailsTextView = (TextView) findViewById(R.id.clientNameSubDetailsTextView);
            levelNameSubDetalsTextView   = (TextView) findViewById(R.id.levelNameSubDetalsTextView);
            subActDetailsNameTextView    = (TextView) findViewById(R.id.subActDetailsNameTextView);
            subActDetailsDoneTextView    = (TextView) findViewById(R.id.subActDetailsDoneTextView);


            clientNameSubDetailsTextView.setText(clientName);

            if (isAct){
                subActDetailsNameTextView.setText(actName);
            }else{
                subActDetailsNameTextView.setText(subActName);
            }


            levelNameSubDetalsTextView.setText(address+", "+level);
            subActDetailsDoneTextView.setText("Done by "+done+" On "+endDate);


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


            detailsRatingBar = (RatingBar) findViewById(R.id.detailsRatingBar);

            commentEditText = (EditText) findViewById(R.id.commentEditText);

            resumeButton = (Button) findViewById(R.id.resumeButton);
            resumeButton.setOnClickListener(this);

            saveAndEndButton = (Button) findViewById(R.id.saveAndEndButton);
            saveAndEndButton.setOnClickListener(this);

            if (flag != 0){
                saveAndEndButton.setVisibility(View.GONE);
                commentEditText.setEnabled(false);
                detailsRatingBar.setEnabled(false);




                if (InternetConnection.getInstance(this).isConnectingToInternet()){
                    showProgress();

                    APICaller apiCaller = new APICaller(this, null);
                    APICall apiCall = null;

                    if (isAct){

                         apiCall = new APICall(API.DISPLAY_ACTIVITY+specId+"/"+actId,
                                APICall.APICallMethod.GET, this);

                    }else{

                       apiCall = new APICall(API.DISPLAY_SUB_ACTIVITY+actId+"/"+subActId,
                                APICall.APICallMethod.GET, this);
                    }



                    apiCall.setJsonSend(null);
                    apiCaller.execute(apiCall);

                }else{

                    showAlert(getResources().getString(R.string.ops),
                            getResources().getString(R.string.no_internet));

                }

            }else{
                saveAndEndButton.setVisibility(View.VISIBLE);
                commentEditText.setEnabled(true);
                detailsRatingBar.setEnabled(true);

            }

        }catch ( Exception e){
            throw e;
        }
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
    public void onClick(View view) {
        try {
            switch (view.getId()){

                case R.id.resumeButton :
                    resumeAction();
                    break;

                case R.id.saveAndEndButton:

                    float rating   = detailsRatingBar.getRating();
                    String comment = commentEditText.getText().toString();
                    String date    = Utils.getCurrentDateOnly();

                    Settings settings = new Settings(this);

                    String email = settings.getUserEmail();


                    if (comment.isEmpty() && rating != 4) {

                        showAlert(getResources().getString(R.string.ops),
                                getResources().getString(R.string.comment_empty));

                    }else if(rating == 0){

                        showAlert(getResources().getString(R.string.ops),
                                getResources().getString(R.string.rating_empty));

                    }else{
                        if (InternetConnection.getInstance(this).isConnectingToInternet()){

                            if (isAct){

                                JSONObject sendJson =
                                        JsonParser.getInstance().getActivityInspection(date, actId,specId,daysFlag, rating,
                                                comment, email);

                                Log.e("ACT",sendJson.toString());


                                showProgress();

                                APICaller apiCaller = new APICaller(this, null);
                                APICall apiCall = new APICall(API.ACT_SUBMIT,
                                        APICall.APICallMethod.POST, this);
                                apiCall.setJsonSend(sendJson);
                                apiCaller.execute(apiCall);


                            }else{

                                JSONObject sendJson =
                                        JsonParser.getInstance().getSubActivityInspection(date,actId,subActId,specId,daysFlag,rating,
                                                comment,email);

                                Log.e("SUB ACT",sendJson.toString());


                                showProgress();

                                APICaller apiCaller = new APICaller(this, null);
                                APICall apiCall = new APICall(API.SUB_ACT_SUBMIT,
                                        APICall.APICallMethod.POST, this);
                                apiCall.setJsonSend(sendJson);
                                apiCaller.execute(apiCall);



                            }
                        }else{

                            showAlert(getResources().getString(R.string.ops),
                                    getResources().getString(R.string.no_internet));

                        }
                    }

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

    private void resumeAction()throws Exception{
        try {



            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            alertDialogBuilder.setTitle("Resume");
            alertDialogBuilder
                    .setMessage("Are you wish to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            callBack();
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
            throw  e;
        }
    }


    @Override
    public void onAPICallComplete(APIResult apiResult) {
        hideProgress();

        try {

            if (apiResult.getURL().equals(API.DISPLAY_ACTIVITY + +specId+"/"+actId)) {

              setShowValue(apiResult.getResultJson());


            }else if (apiResult.getURL().equals(API.DISPLAY_SUB_ACTIVITY+actId+"/"+subActId)){



                setShowValue(apiResult.getResultJson());

            }else  if(apiResult.getURL().equals(API.ACT_SUBMIT)){

                SuccessBean successBean = JsonParser.getInstance().getJsonSuccess(apiResult.getResultJson());

                if (successBean.isSuccess()){

                    Toast.makeText(this,successBean.getMessage(),Toast.LENGTH_LONG).show();
                    callBack();

                }else{

                    showAlert(getResources().getString(R.string.ops),successBean.getMessage());

                }


            } else  if(apiResult.getURL().equals(API.SUB_ACT_SUBMIT)) {

                SuccessBean successBean = JsonParser.getInstance().getJsonSuccess(apiResult.getResultJson());

                if (successBean.isSuccess()) {

                    Toast.makeText(this, successBean.getMessage(), Toast.LENGTH_LONG).show();
                    callBack();

                } else {

                    showAlert(getResources().getString(R.string.ops), successBean.getMessage());

                }

            }




        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setShowValue(JSONObject resultJson) {
        try {

            JobDisplayBean displayBean = JsonParser.getInstance().getDisplayData(resultJson);

            detailsRatingBar.setRating(displayBean.getRating());
            commentEditText.setText(displayBean.getComment());

        }catch (Exception w){
            w.printStackTrace();
        }
    }
}
