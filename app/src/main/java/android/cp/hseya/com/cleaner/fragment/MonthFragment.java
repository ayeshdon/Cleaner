package android.cp.hseya.com.cleaner.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.cp.hseya.com.cleaner.MainApplication;
import android.cp.hseya.com.cleaner.activity.InspectionItemActivity;
import android.cp.hseya.com.cleaner.adapter.InspectionItemListAdapter;
import android.cp.hseya.com.cleaner.api.API;
import android.cp.hseya.com.cleaner.api.APICall;
import android.cp.hseya.com.cleaner.api.APICallback;
import android.cp.hseya.com.cleaner.api.APICaller;
import android.cp.hseya.com.cleaner.api.APIResult;
import android.cp.hseya.com.cleaner.bean.JobSpecBean;
import android.cp.hseya.com.cleaner.json.JsonParser;
import android.cp.hseya.com.cleaner.utils.Const;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.cp.hseya.com.cleaner.R;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;


public class MonthFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private View view;
    private MainApplication application;
    private ImageView monthLeftImageView,monthRightImageView;
    private InspectionItemListAdapter listAdapter;
    private ListView monthJobSpecListView;
    private ProgressDialog progressDialog;
    private TextView monthCountTextView;

    public MonthFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {

            application = (MainApplication) getActivity().getApplication();


            view =  inflater.inflate(R.layout.fragment_month, container, false);

            UIInitialize();


            return  view;
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }



    private void UIInitialize()throws  Exception{
        try {

            monthLeftImageView = (ImageView) view.findViewById(R.id.monthLeftImageView);
            monthLeftImageView.setOnClickListener(this);

            monthRightImageView = (ImageView) view.findViewById(R.id.monthRightImageView);
            monthRightImageView.setOnClickListener(this);

            monthCountTextView = (TextView)view.findViewById(R.id.monthCountTextView);
            monthCountTextView.setText(""+application.monthJobCount);

            monthJobSpecListView = (ListView) view.findViewById(R.id.monthJobSpecListView);
            monthJobSpecListView.setOnItemClickListener(this);

            setupListAdapter();

        }catch (Exception e){
            throw  e;
        }
    }

    @Override
    public void onClick(View v) {
        try {

            switch (v.getId()){

                case R.id.monthLeftImageView :

                    application.fragmentActionListener.onFragmentMoverClick(3,1);

                    break;

                case R.id.monthRightImageView :

                    application.fragmentActionListener.onFragmentMoverClick(3,2);

                    break;

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setupListAdapter()throws Exception{

        try {

            if (application.jobSpecMonthList != null){

                listAdapter = new InspectionItemListAdapter(getActivity(),application.jobSpecMonthList);
                monthJobSpecListView.setAdapter(listAdapter);

            }




        }catch (Exception e){
            throw e;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        JobSpecBean bean =  application.jobSpecMonthList.get(position);

        try {
            Intent callItems = new Intent(getActivity(), InspectionItemActivity.class);
            callItems.putExtra("ID",bean.getId());
            callItems.putExtra("CLIENTNAME",bean.getClientName());
            callItems.putExtra("ADDRESS",bean.getLocationName());
            callItems.putExtra("LEVEL",bean.getLevelName());
            callItems.putExtra("DAY_FLAG",Const.MONTH_FRQ);
            startActivity(callItems);



        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
