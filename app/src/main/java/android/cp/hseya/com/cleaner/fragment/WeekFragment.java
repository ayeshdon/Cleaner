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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.cp.hseya.com.cleaner.R;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

public class WeekFragment extends Fragment implements View.OnClickListener ,AdapterView.OnItemClickListener {

    private  View view;
    private ImageView weekLeftImageView,weekRightImageView;
    private MainApplication application;
    private ListView weekJobSpecListView;
    private InspectionItemListAdapter listAdapter;
    private ProgressDialog progressDialog;
    private TextView weekCountTextView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {


            application = (MainApplication) getActivity().getApplication();

            view = inflater.inflate(R.layout.fragment_week, container, false);

            UIInitialize();


            return view;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }





    private void UIInitialize() throws  Exception{
        try {

            weekLeftImageView = (ImageView) view.findViewById(R.id.weekLeftImageView);
            weekLeftImageView.setOnClickListener(this);

            weekRightImageView = (ImageView) view.findViewById(R.id.weekRightImageView);
            weekRightImageView.setOnClickListener(this);

            weekJobSpecListView = (ListView)view.findViewById(R.id.weekJobSpecListView);
            weekJobSpecListView.setOnItemClickListener(this);

            weekCountTextView    = (TextView) view.findViewById(R.id.weekCountTextView);
            weekCountTextView.setText(""+application.weekJobCount);

            setupListAdapter();

        }catch (Exception e){
            throw  e;
        }

    }

    private void setupListAdapter()throws Exception{

        try {

            if (application.jobSpecWeekList != null){

                listAdapter = new InspectionItemListAdapter(getActivity(),application.jobSpecWeekList);
                weekJobSpecListView.setAdapter(listAdapter);

            }




        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public void onClick(View v) {

        try {

            switch (v.getId()){

                case  R.id.weekLeftImageView :

                    application.fragmentActionListener.onFragmentMoverClick(2,1);


                    break;

                case R.id.weekRightImageView :

                    application.fragmentActionListener.onFragmentMoverClick(2,2);
                    break;
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("P", "eeeeeeeeeee");
    }

    @Override
    public void onResume() {

        Log.e("P","++++++++++");
        super.onResume();

        try {
            setupListAdapter();

            if (weekCountTextView == null){

                weekCountTextView    = (TextView) view.findViewById(R.id.weekCountTextView);
                weekCountTextView.setText(""+application.weekJobCount);

            }else{

                weekCountTextView.setText(""+application.weekJobCount);
            }

        }catch (Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
        JobSpecBean bean =  application.jobSpecWeekList.get(pos);

        try {
            Intent callItems = new Intent(getActivity(), InspectionItemActivity.class);
            callItems.putExtra("ID",bean.getId());
            callItems.putExtra("CLIENTNAME",bean.getClientName());
            callItems.putExtra("ADDRESS",bean.getLocationName());
            callItems.putExtra("LEVEL",bean.getLevelName());
            callItems.putExtra("DAY_FLAG",Const.WEEK_FRQ);
            startActivity(callItems);



        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
