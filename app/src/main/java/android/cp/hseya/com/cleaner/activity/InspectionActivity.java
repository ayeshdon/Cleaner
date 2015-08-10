package android.cp.hseya.com.cleaner.activity;

import android.content.Intent;
import android.cp.hseya.com.cleaner.adapter.InspectionFPAdapter;
import android.cp.hseya.com.cleaner.listener.ActionbarTitleClickListener;
import android.cp.hseya.com.cleaner.ui.CommonUI;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.cp.hseya.com.cleaner.R;
import android.cp.hseya.com.cleaner.R;
import android.widget.TabHost;
import android.widget.Toast;

public class InspectionActivity extends ActionBarActivity implements ActionbarTitleClickListener {

    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {



      final ActionBar customActionBar =  getSupportActionBar();


            customActionBar.hide();
            customActionBar.setIcon(null);

            customActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            CommonUI.getInstance(this).setCommonActionBar(customActionBar,this,true,false,
                    getResources().getString(R.string.inspection),true,this);


            customActionBar.show();


            setContentView(R.layout.activity_inspection);


            //--------------------

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
            tab = customActionBar.newTab()
                    .setText("Week")
                    .setTabListener(tabListener);

            customActionBar.addTab(tab);

            /** Creating fragment3 Tab */
            tab = customActionBar.newTab()
                    .setText("Month")
                    .setTabListener(tabListener);

            customActionBar.addTab(tab);


            //--------------------








        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.exception), Toast.LENGTH_LONG).show();
            finish();
        }
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
}
