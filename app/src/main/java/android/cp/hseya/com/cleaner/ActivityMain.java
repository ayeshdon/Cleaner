package android.cp.hseya.com.cleaner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.cp.hseya.com.cleaner.activity.DashBoardActivity;
import android.cp.hseya.com.cleaner.api.API;
import android.cp.hseya.com.cleaner.api.APICall;
import android.cp.hseya.com.cleaner.api.APICallback;
import android.cp.hseya.com.cleaner.api.APICaller;
import android.cp.hseya.com.cleaner.api.APIResult;
import android.cp.hseya.com.cleaner.bean.JsonStatusBean;
import android.cp.hseya.com.cleaner.bean.LoginResponseBean;
import android.cp.hseya.com.cleaner.bean.SuccessBean;
import android.cp.hseya.com.cleaner.json.JsonParser;
import android.cp.hseya.com.cleaner.utils.InternetConnection;
import android.cp.hseya.com.cleaner.utils.Settings;
import android.cp.hseya.com.cleaner.utils.Utils;
import android.cp.hseya.com.cleaner.utils.Validator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;


public class ActivityMain extends Activity implements View.OnClickListener, APICallback {

    private EditText loginEmailEditText, loginPwEditText;
    private Button loginButton;
    private ProgressDialog progressDialog;
    private String email ;
    private TextView versionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            Settings settings = new Settings(this);

            if (settings.getLoginAlready()) {

                callDashboard();

            } else {

                setContentView(R.layout.activity_main);


                UIInitalize();
            }


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.exception), Toast.LENGTH_LONG).show();
        }
    }


    private void UIInitalize() throws Exception {
        try {
            loginEmailEditText = (EditText) findViewById(R.id.loginEmailEditText);
            loginPwEditText    = (EditText) findViewById(R.id.loginPwEditText);





            loginButton = (Button) findViewById(R.id.loginButton);

            loginButton.setOnClickListener(this);

        } catch (Exception e) {
            throw e;
        }
    }


    private void callDashboard() throws Exception {
        try {

            Intent callDashoard = new Intent(this, DashBoardActivity.class);
            startActivity(callDashoard);
            finish();

        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    public void onClick(View view) {
        try {

            switch (view.getId()) {
                case R.id.loginButton: {

                    if (InternetConnection.getInstance(this).isConnectingToInternet()) {

                         email = loginEmailEditText.getText().toString();
                        String pw = loginPwEditText.getText().toString();

                        if (email.isEmpty()) {
                            Toast.makeText(this, getResources().getString(R.string.email_empty), Toast.LENGTH_LONG).show();

                        } else if (!Validator.isValidEmail(email)) {
                            Toast.makeText(this, getResources().getString(R.string.email_invalid), Toast.LENGTH_LONG).show();

                        } else if (pw.isEmpty()) {
                            Toast.makeText(this, getResources().getString(R.string.pw_empty), Toast.LENGTH_LONG).show();

                        } else {
                    /*Valid request*/


                            String md5Pw = Utils.stringToMD5(pw);

                            JSONObject loginJson = JsonParser.getInstance().getLoginJson(email, md5Pw);

                            showProgress();
                            APICaller apiCaller = new APICaller(this, null);
                            APICall apiCall = new APICall(API.USER_LOGIN, APICall.APICallMethod.POST, this);

                            apiCall.setJsonSend(loginJson);
                            apiCaller.execute(apiCall);


                        }


                    } else {

                        Toast.makeText(this, getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();

                    }

                    break;
                }
            }
        } catch (Exception e) {
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
    public void onAPICallComplete(APIResult apiResult) {
        hideProgress();

        try {


            JSONObject resultJson = apiResult.getResultJson();

            JsonStatusBean statusBean = JsonParser.getInstance().getJsonStatus(resultJson);

            if (statusBean.getStatusCode() == 200) {
                /*Success response*/
                SuccessBean successBean = JsonParser.getInstance().getJsonSuccess(resultJson);

                if (successBean.isSuccess()) {

                    LoginResponseBean bean = JsonParser.getInstance().getJsonloginResponse(resultJson);

                    Settings settings = new Settings(this);

                    settings.setUserEmail(email);
                    settings.setLoginAlready(true);
                    settings.setUserName(bean.getName());
                    settings.setUserID(bean.getId());

                    callDashboard();

                } else {
                    Toast.makeText(this, successBean.getMessage(), Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(this, statusBean.getMessage(), Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
