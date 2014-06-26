package com.example.gridviewwithfooterview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter {

	private List<String> mList = new ArrayList<String>();
	private Context mContext;

	private String footerviewItem;

	private FooterView footerView;

	private boolean footerViewEnable = false;
	private OnClickListener ml;

	public ItemAdapter(Context context, List<String> list) {
		if (list != null) {
			this.mList = list;
		}
		this.mContext = context;

	}

	public boolean isFooterViewEnable() {
		return footerViewEnable;
	}

	/**
	 * 存放列表项控件句柄
	 */
	public static class ViewHolder {

		public TextView itemview;

	}

	public void setFootreViewEnable(boolean enable) {
		footerViewEnable = enable;
	}

	public void setOnFooterViewClickListener(OnClickListener l) {
		ml = l;
	}

	private int getDisplayWidth(Activity activity) {
		Display display = activity.getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		return width;
	}

	@Override
	public View getView(final int i, View convertView, ViewGroup parent) {
		// 伪造的空项可以根据楼盘id来确定。
		if (footerViewEnable && i == mList.size() - 1) {
			if (footerView == null) {
				footerView = new FooterView(parent.getContext());

				GridView.LayoutParams pl = new GridView.LayoutParams(
						getDisplayWidth((Activity) mContext),
						LayoutParams.WRAP_CONTENT);
				footerView.setLayoutParams(pl);
				footerView.setBackgroundColor(Color.RED);
				footerView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (ml != null) {
							ml.onClick(v);
						}

					}
				});
			}
			setFooterViewStatus(FooterView.MORE);
			return footerView;
		}
		final ViewHolder holder;

		if (convertView == null
				|| (convertView != null && convertView == footerView)) {

			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.textview, parent, false);
			holder = new ViewHolder();
			holder.itemview = (TextView) convertView
					.findViewById(R.id.textview);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.itemview.setText("item" + mList.get(i));
		return convertView;
	}

	public FooterView getFooterView() {
		return footerView;
	}

	public void setFooterViewStatus(int status) {
		if (footerView != null) {
			footerView.setStatus(status);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}
