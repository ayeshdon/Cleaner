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

public class WeekFragment extends Fragment implements View.OnClickListener {

    private  View view;
    private ImageView weekLeftImageView,weekRightImageView;
    private MainApplication application;
    private ListView weekJobSpecListView;
    private InspectionItemListAdapter listAdapter;



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

            setupListAdapter();

        }catch (Exception e){
            throw  e;
        }

    }

    private void setupListAdapter()throws Exception{

        try {

            if (application.jobSpecList != null){

                listAdapter = new InspectionItemListAdapter(getActivity(),application.jobSpecList);
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
}
