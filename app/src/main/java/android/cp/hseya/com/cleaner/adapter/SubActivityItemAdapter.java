package android.cp.hseya.com.cleaner.adapter;

import android.content.Context;
import android.cp.hseya.com.cleaner.R;
import android.cp.hseya.com.cleaner.bean.SubActivityBean;
import android.cp.hseya.com.cleaner.enumClass.ActivityAction;
import android.cp.hseya.com.cleaner.listener.ActivityActionListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ayesh on 8/18/2015.
 */
public class SubActivityItemAdapter   extends BaseExpandableListAdapter {
    private LayoutInflater inflater;
    private ArrayList<SubActivityBean> dataList;
    private ActivityActionListener listener;
    private Context context;

    public SubActivityItemAdapter(Context context,ArrayList<SubActivityBean> dataList,ActivityActionListener listener)
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


        final SubActivityBean parent = dataList.get(groupPosition);


        convertView = inflater.inflate(R.layout.listview_subactivity_group, parentView, false);
        TextView subActiivtyGroupHeaderTextView = ((TextView) convertView.findViewById(R.id.subActiivtyGroupHeaderTextView));
        subActiivtyGroupHeaderTextView.setText(parent.getDescription());


        if (parent.getFlag() == 0){

            subActiivtyGroupHeaderTextView.setTextColor(context.getResources().getColor(R.color.text_black));

        }else{

            subActiivtyGroupHeaderTextView.setTextColor(context.getResources().getColor(R.color.btn_singup_bg));

        }



        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parentView)
    {
        final SubActivityBean parent = dataList.get(groupPosition);
        convertView = inflater.inflate(R.layout.listview_subactivity_child, parentView, false);

        final int pos  = groupPosition;
        final int flag = parent.getFlag();

        TextView weekTextView         = (TextView) convertView.findViewById(R.id.weekTextView);
        TextView childSubDataTextView = (TextView) convertView.findViewById(R.id.childSubDataTextView);
        childSubDataTextView.setText("Done By "+parent.getDoneName()+"  On "+parent.getDate());


        if (parent.getFlag() == 0){

            weekTextView.setTextColor(context.getResources().getColor(R.color.text_black));
            childSubDataTextView.setTextColor(context.getResources().getColor(R.color.text_black));

        }else{

            weekTextView.setTextColor(context.getResources().getColor(R.color.btn_singup_bg));
            childSubDataTextView.setTextColor(context.getResources().getColor(R.color.btn_singup_bg));

        }



        ImageView subcommentImageView = (ImageView) convertView.findViewById(R.id.subcommentImageView);
        subcommentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onActivityActionClick(ActivityAction.SUB_DETAILS,pos,flag);
            }
        });



        return convertView;
    }


    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        //Log.i("Childs", groupPosition+"=  getChild =="+childPosition);
        return dataList.get(groupPosition).getId();
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
        Log.i("Parent", groupPosition + "=  getGroup ");

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
        // Refresh List rows
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
