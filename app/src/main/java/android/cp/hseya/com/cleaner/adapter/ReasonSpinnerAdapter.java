package android.cp.hseya.com.cleaner.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.cp.hseya.com.cleaner.R;
import android.cp.hseya.com.cleaner.bean.ReasonBean;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


public class ReasonSpinnerAdapter extends BaseAdapter {


	private Context context;
	private ArrayList<ReasonBean> arrayList;


	public ReasonSpinnerAdapter(Context context, ArrayList<ReasonBean> arrayList) {

		this.arrayList = arrayList;
		this.context   = context;
	}


	@Override
	public int getCount() {
		return arrayList.size();
	}

	@Override
	public Object getItem(int pos) {
		return arrayList.get(pos);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public int getItemViewType(int arg0) {
		return 0;
	}

	@Override
	public View getView(int pos, View arg1, ViewGroup arg2) {
		try{

			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.listview_general_display_list, null);
			TextView textview = (TextView) view.findViewById(R.id.citiesNameTextView);


			textview.setText(arrayList.get(pos).getDescription());





			return view;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int getViewTypeCount() {
		return android.R.layout.simple_spinner_item;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver arg0) {
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver arg0) {
	}

	@Override
	public View getDropDownView(int pos, View arg1, ViewGroup arg2) {

		try {


			LayoutInflater inflater = (LayoutInflater)    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.listview_general_dropdown, null);
			TextView textview = (TextView) view.findViewById(R.id.dropDownNameTextView);


			textview.setText(arrayList.get(pos).getDescription());


			return view;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
}
