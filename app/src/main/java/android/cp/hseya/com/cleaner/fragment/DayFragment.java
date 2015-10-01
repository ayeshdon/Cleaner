package android.cp.hseya.com.cleaner.fragment;

import android.app.Activity;
import android.content.Intent;
import android.cp.hseya.com.cleaner.MainApplication;
import android.cp.hseya.com.cleaner.activity.InspectionItemActivity;
import android.cp.hseya.com.cleaner.adapter.InspectionItemListAdapter;
import android.cp.hseya.com.cleaner.bean.JobSpecBean;
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


public class DayFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageView dayLeftImageView,dayRightImageView;
    private TextView dayCountTextView;
    private MainApplication application;
    private InspectionItemListAdapter listAdapter;
    private ListView jobSpecListView;


    public DayFragment() {
    }

    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {

            application = (MainApplication)getActivity().getApplication();

            view = inflater.inflate(R.layout.fragment_day, container, false);

            UIInitialize();


            return view;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    private void UIInitialize() throws Exception{
        try {

            dayLeftImageView = (ImageView) view.findViewById(R.id.dayLeftImageView);
            dayLeftImageView.setOnClickListener(this);

            dayRightImageView = (ImageView) view.findViewById(R.id.dayRightImageView);
            dayRightImageView.setOnClickListener(this);


            dayCountTextView = (TextView)view.findViewById(R.id.dayCountTextView);
            dayCountTextView.setText("" + String.format("%02d", application.dayJobCount));


            jobSpecListView = (ListView) view.findViewById(R.id.jobSpecListView);
            jobSpecListView.setOnItemClickListener(this);

            setupListAdapter();

        }catch (Exception e){
            throw  e;
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()){

                case R.id.dayLeftImageView:
                    application.fragmentActionListener.onFragmentMoverClick(1,1);
                    break;

                case R.id.dayRightImageView:
                    application.fragmentActionListener.onFragmentMoverClick(1,2);
                    break;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setupListAdapter()throws Exception{

        try {

            if (application.jobSpecDayList != null){

                listAdapter = new InspectionItemListAdapter(getActivity(),application.jobSpecDayList);
                jobSpecListView.setAdapter(listAdapter);

            }




        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
        JobSpecBean bean =  application.jobSpecDayList.get(pos);

        try {
            Intent callItems = new Intent(getActivity(), InspectionItemActivity.class);
            callItems.putExtra("ID",bean.getId());
            callItems.putExtra("CLIENTNAME",bean.getClientName());
            callItems.putExtra("ADDRESS",bean.getLocationName());
            callItems.putExtra("LEVEL",bean.getLevelName());
            callItems.putExtra("DAY_FLAG", Const.DAY_FRQ);
            startActivity(callItems);




        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
