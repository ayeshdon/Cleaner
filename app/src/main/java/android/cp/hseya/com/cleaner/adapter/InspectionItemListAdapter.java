package android.cp.hseya.com.cleaner.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.cp.hseya.com.cleaner.R;
import android.cp.hseya.com.cleaner.bean.JobSpecBean;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class InspectionItemListAdapter  extends BaseAdapter {

	private Activity activity;
	private List<JobSpecBean> dataList;
	private JobSpecBean dataBean;

	public InspectionItemListAdapter(Activity act, List<JobSpecBean> arrayList) {

		this.activity = act;
		this.dataList = arrayList;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		try {

			View view = convertView;

			ViewHolder holder;

			if (view == null) {

				LayoutInflater inflater = (LayoutInflater) activity
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.listview_inspection_jobs, null);

				holder = new ViewHolder();
				view.setTag(holder);

			} else {
				holder = (ViewHolder) view.getTag();
			}

			if ((dataList == null) || ((position + 1) > dataList.size()))
				return view;

			dataBean = dataList.get(position);


			holder.specClientNameTextView       = (TextView) view.findViewById(R.id.specClientNameTextView);
			holder.specLevelTetView             = (TextView) view.findViewById(R.id.specLevelTetView);
			holder.dateTextView                 = (TextView) view.findViewById(R.id.dateTextView);

			if (holder.specClientNameTextView != null) {

				holder.specClientNameTextView.setText(dataBean.getClientName());
			}

			if (holder.dateTextView != null) {

				holder.dateTextView.setText(dataBean.getDate());
			}

			if (holder.specLevelTetView != null) {

				holder.specLevelTetView.setText(dataBean.getLocationName()+", "+dataBean.getLevelName());
			}


			return view;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	

	public class ViewHolder {

		public TextView specClientNameTextView, specLevelTetView,dateTextView;

	}

	@Override
	public int getCount() {

		if (dataList != null) {
			return dataList.size();
		} else {
			return 1;
		}

	}

	@Override
	public Object getItem(int index) {

		if (dataList != null) {
			return dataList.get(index);
		} else {
			return null;
		}

	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	/**
	 * update and refresh data list
	 */
	public void updateDataSet(ArrayList<JobSpecBean> dataList) {
		this.dataList = dataList;
		notifyDataSetChanged();
	}
}