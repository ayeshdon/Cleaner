package android.cp.hseya.com.cleaner.adapter;

import android.content.Context;
import android.cp.hseya.com.cleaner.R;
import android.cp.hseya.com.cleaner.bean.ActivityBean;
import android.cp.hseya.com.cleaner.enumClass.ActivityAction;
import android.cp.hseya.com.cleaner.listener.ActivityActionListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ayesh on 8/16/2015.
 */
public class ItemActivityExpandListAdapter  extends BaseExpandableListAdapter {
    private LayoutInflater inflater;
    private ArrayList<ActivityBean> dataList;
    private ActivityActionListener listener;
    private Context  context;

    public ItemActivityExpandListAdapter(Context  context,ArrayList<ActivityBean> dataList,ActivityActionListener listener)
    {

        this.dataList = dataList;
        inflater      = LayoutInflater.from(context);
        this.listener = listener;
        this.context  = context;

    }


    // This Function used to inflate parent rows view

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parentView)
    {


        final ActivityBean parent = dataList.get(groupPosition);


        convertView = inflater.inflate(R.layout.listview_activity_group, parentView, false);

        TextView actiivtyGroupHeaderTextView = (TextView) convertView.findViewById(R.id.actiivtyGroupHeaderTextView);
        actiivtyGroupHeaderTextView.setText(parent.getActivity());

        if (parent.getFlag() == 1){
            actiivtyGroupHeaderTextView.setTextColor(context.getResources().getColor(R.color.btn_singup_bg));

        }else{
            actiivtyGroupHeaderTextView.setTextColor(context.getResources().getColor(R.color.text_black));
        }

        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parentView)
    {
        final ActivityBean parent = dataList.get(groupPosition);
        convertView = inflater.inflate(R.layout.listview_activity_child, parentView, false);

        final int pos  = groupPosition;
        final int flag = parent.getFlag() ;

        TextView childDataTextView = (TextView) convertView.findViewById(R.id.childDataTextView);
        childDataTextView.setText("Done By " + parent.getDoneName() + "  On " + parent.getEndDate());

        if (flag == 1){
            childDataTextView.setTextColor(context.getResources().getColor(R.color.btn_singup_bg));

        }else{
            childDataTextView.setTextColor(context.getResources().getColor(R.color.text_black));
        }

        ImageView subActivityImageView = (ImageView) convertView.findViewById(R.id.subActivityImageView);
        subActivityImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onActivityActionClick(ActivityAction.SUB_ACTVITY, pos,flag);
            }
        });

        ImageView feedBackImageViedw = (ImageView) convertView.findViewById(R.id.feedBackImageViedw);
        feedBackImageViedw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onActivityActionClick(ActivityAction.SUB_DETAILS,pos,flag);
            }
        });

        ImageView postponeImageView = (ImageView) convertView.findViewById(R.id.postponeImageView);
        postponeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onActivityActionClick(ActivityAction.POSTPONE,pos,flag);
            }
        });

        TextView moreDetailsTextView = (TextView) convertView.findViewById(R.id.moreDetailsTextView);
        moreDetailsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onActivityActionClick(ActivityAction.MORE_DETAILS,pos,flag);
            }
        });



        return convertView;
    }


    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return dataList.get(groupPosition).getActivity();
    }

    //Call when child row clicked
    @Override
    public long getChildId(int groupPosition, int childPosition)
    {


        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        int size=0;
        return 1;
    }


    @Override
    public Object getGroup(int groupPosition)
    {

        return dataList.get(groupPosition);
    }

    @Override
    public int getGroupCount()
    {
        return dataList.size();
    }

    //Call when parent row clicked
    @Override
    public long getGroupId(int groupPosition)
    {



        return groupPosition;
    }

    @Override
    public void notifyDataSetChanged()
    {
        super.notifyDataSetChanged();
    }

    @Override
    public boolean isEmpty()
    {
        return ((dataList == null) || dataList.isEmpty());
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }

}
