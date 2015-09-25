package android.cp.hseya.com.cleaner.fragment;

import android.app.Activity;
import android.cp.hseya.com.cleaner.MainApplication;
import android.cp.hseya.com.cleaner.adapter.InspectionItemListAdapter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.cp.hseya.com.cleaner.R;
import android.widget.ImageView;
import android.widget.ListView;


public class MonthFragment extends Fragment implements View.OnClickListener{

    private View view;
    private MainApplication application;
    private ImageView monthLeftImageView,monthRightImageView;
    private InspectionItemListAdapter listAdapter;
    private ListView monthJobSpecListView;

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

            monthJobSpecListView = (ListView) view.findViewById(R.id.monthJobSpecListView);

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

            if (application.jobSpecList != null){

                listAdapter = new InspectionItemListAdapter(getActivity(),application.jobSpecList);
                monthJobSpecListView.setAdapter(listAdapter);

            }




        }catch (Exception e){
            throw e;
        }

    }
}
