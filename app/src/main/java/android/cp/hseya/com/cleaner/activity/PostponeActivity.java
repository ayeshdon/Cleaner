package android.cp.hseya.com.cleaner.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.cp.hseya.com.cleaner.adapter.ReasonSpinnerAdapter;
import android.cp.hseya.com.cleaner.api.API;
import android.cp.hseya.com.cleaner.api.APICall;
import android.cp.hseya.com.cleaner.api.APICallback;
import android.cp.hseya.com.cleaner.api.APICaller;
import android.cp.hseya.com.cleaner.api.APIResult;
import android.cp.hseya.com.cleaner.bean.PostponeBean;
import android.cp.hseya.com.cleaner.bean.ReasonBean;
import android.cp.hseya.com.cleaner.json.JsonParser;
import android.cp.hseya.com.cleaner.utils.InternetConnection;
import android.cp.hseya.com.cleaner.utils.Utils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.internal.widget.DialogTitle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.cp.hseya.com.cleaner.R;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class PostponeActivity extends ActionBarActivity implements View.OnClickListener, APICallback {


    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    private String clientName,address,level,actName,done,endDate;
    private int actId;
    private TextView clientNamePostponeTextView,levelNamePostponeTextView,postponeActNameTextView,postponeActDoneTextView;
    private EditText dateAttendEditText,postPoneDateEditText;

    private int year;
    private int month;
    private int day;
    private ArrayList<ReasonBean> reasonList;
    private Spinner reasonSpinner;
    static final int DATE_PICKER_ID = 1111;
    private ReasonSpinnerAdapter reasonSpinnerAdapter;
    private Button postponeSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            actId      = getIntent().getExtras().getInt("ACT_ID");
            clientName = getIntent().getExtras().getString("CLIENT_NAME");
            address    = getIntent().getExtras().getString("ADDRESS");
            level      = getIntent().getExtras().getString("LEVEL");
            done       = getIntent().getExtras().getString("DONE");
            endDate    = getIntent().getExtras().getString("END_DATE");
            actName    = getIntent().getExtras().getString("ACT_NAME");


            setContentView(R.layout.activity_postpone);


            final Calendar c = Calendar.getInstance();
            year  = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day   = c.get(Calendar.DAY_OF_MONTH);

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


            clientNamePostponeTextView  = (TextView) findViewById(R.id.clientNamePostponeTextView);
            levelNamePostponeTextView   = (TextView) findViewById(R.id.levelNamePostponeTextView);
            postponeActNameTextView     = (TextView) findViewById(R.id.postponeActNameTextView);
            postponeActDoneTextView     = (TextView) findViewById(R.id.postponeActDoneTextView);


            reasonSpinner = (Spinner)findViewById(R.id.reasonSpinner);


            clientNamePostponeTextView.setText(clientName);


            postponeActNameTextView.setText(actName);

            levelNamePostponeTextView.setText(address+", "+level);
            postponeActDoneTextView.setText("Done by " + done + " On " + endDate);


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


            postPoneDateEditText  = (EditText)findViewById(R.id.postPoneDateEditText);
            dateAttendEditText    = (EditText)findViewById(R.id.dateAttendEditText);
            dateAttendEditText.setText(Utils.getCurrentDateOnly());

            postponeSaveButton = (Button)findViewById(R.id.postponeSaveButton);
            postponeSaveButton.setOnClickListener(this);


            postPoneDateEditText.setOnClickListener(this);

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

                case R.id.postPoneDateEditText:

                    showDialog(DATE_PICKER_ID);

                    break;

                case R.id.postponeSaveButton :

                    String remark = postPoneDateEditText.getText().toString().trim();
                    int reasonPos = reasonSpinner.getSelectedItemPosition();

                    if (remark.isEmpty()){

                        Toast.makeText(this,getResources().getString(R.string.postpone_date_empty),
                                Toast.LENGTH_LONG).show();

                    }else if (reasonPos == 0){

                        Toast.makeText(this,getResources().getString(R.string.postpone_reason_empty),
                                Toast.LENGTH_LONG).show();

                    }else{

                        PostponeBean  dataBean = new PostponeBean();

                        dataBean.setActID(actId);
                        dataBean.setRemark(remark);
                        dataBean.setStatus(reasonList.get(reasonPos).getDescription());


                        JSONObject sendJson = JsonParser.getInstance().getPostponeJson(dataBean);



                        if (InternetConnection.getInstance(this).isConnectingToInternet()){
                            showProgress();

                            APICaller apiCaller = new APICaller(this, null);
                            APICall apiCall = new APICall(API.POSTPONE,
                                    APICall.APICallMethod.POST, this);
                            apiCall.setJsonSend(sendJson);
                            apiCaller.execute(apiCall);

                        }else{

                            showAlert(getResources().getString(R.string.ops),
                                    getResources().getString(R.string.no_internet),false);

                        }

                    }


                    break;

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:
                return new DatePickerDialog(this, pickerListener, year, month,day);
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;

            String monthVal,dayVal;

            if ((day) < 10){
                dayVal = "0"+(day);
            }else{
                dayVal = ""+(day);
            }

            if ((month + 1) < 10){
                monthVal = "0"+(month + 1);
            }else{
                monthVal = ""+(month + 1);
            }

            postPoneDateEditText.setText(new StringBuilder().append(year)
                    .append("-").append(monthVal).append("-").append(dayVal)
                    .append(" "));

        }
    };



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


    private void loadDataFromAPI() {
        try {

            if (InternetConnection.getInstance(this).isConnectingToInternet()){
                showProgress();

                APICaller apiCaller = new APICaller(this, null);
                APICall apiCall = new APICall(API.POSTPONEREASON,
                        APICall.APICallMethod.GET, this);
                apiCall.setJsonSend(null);
                apiCaller.execute(apiCall);

            }else{

                showAlert(getResources().getString(R.string.ops),
                        getResources().getString(R.string.no_internet),true);

            }

        }catch (Exception e){
            hideProgress();
            throw e;
        }
    }


    private void showAlert(String title,String msg, final boolean isFinish){
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

                            if (isFinish){

                                PostponeActivity.this.finish();

                            }




                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAPICallComplete(APIResult apiResult) {
        hideProgress();

        try {

            if (apiResult.getURL().equals(API.POSTPONEREASON)){

                ReasonBean defualtBean = new ReasonBean(-1,"Tap to Select");


                reasonList =
                        JsonParser.getInstance().getPostponeReasonList(defualtBean,apiResult.getResultJson());

                setupReasonSpinner();


            }else if(apiResult.getURL().equals(API.POSTPONE)){

                if (!JsonParser.getInstance().isSuccess(apiResult.getResultJson())){

                    showAlert(getResources().getString(R.string.success),
                            getResources().getString(R.string.update_successfully),true);

                }else{

                    showAlert(getResources().getString(R.string.ops),
                            getResources().getString(R.string.exception),false);

                }


            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setupReasonSpinner(){
        try {

            reasonSpinnerAdapter = new ReasonSpinnerAdapter(this,reasonList);
            reasonSpinner.setAdapter(reasonSpinnerAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
