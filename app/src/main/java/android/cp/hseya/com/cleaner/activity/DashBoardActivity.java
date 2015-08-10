package android.cp.hseya.com.cleaner.activity;

import android.content.Intent;
import android.cp.hseya.com.cleaner.ActivityMain;
import android.cp.hseya.com.cleaner.R;
import android.cp.hseya.com.cleaner.ui.CircularImageView;
import android.cp.hseya.com.cleaner.utils.Settings;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class DashBoardActivity extends ActionBarActivity implements View.OnClickListener {



    private Toolbar toolbar;
    private CircularImageView profileImageView;
    private TextView profileNameTextView;
    private Settings settings;
    private LinearLayout inspectionLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {


            setContentView(R.layout.activity_dash_board);

            showMenuIcon();


            UIInitialize();

//            loadContu

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.exception), Toast.LENGTH_LONG).show();
        }

    }

    private void UIInitialize() throws  Exception{
        try {
            toolbar = (Toolbar) findViewById(R.id.tool_bar);
            toolbar.setTitle("");
            setSupportActionBar(toolbar);

             settings = new Settings(this);

            profileImageView = (CircularImageView)findViewById(R.id.profileImageView);
            profileImageView.setBorderWidth(0);

            profileNameTextView = (TextView)findViewById(R.id.profileNameTextView);
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

            settings.setLoginAlready(false);
            settings.setUserName(" ");

            Intent callback = new Intent(this, ActivityMain.class);
            startActivity(callback);
            finish();

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
}
