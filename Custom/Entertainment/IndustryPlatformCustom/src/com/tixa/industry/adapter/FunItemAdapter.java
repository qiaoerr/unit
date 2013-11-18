package com.tixa.industry.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.tixa.industry.R;
import com.tixa.industry.model.FunItem;

public class FunItemAdapter extends BaseAdapter {
	private boolean arrowFlag = true;

	private class ViewHolder {
		ImageView iconView;
		TextView itemText;
		TextView itemText_big;
		ImageView adMenuImage;
		ImageView arrow;
		CheckBox checkBox;
		TextView cancelBtn;
		View itemLayout;
		TextView accountName;
		TextView accountMail;
		ImageView accountLogo;
		RadioButton radioBtn;
		Button deleteBtn;
		Spinner spin;
		TextView newNum;
	}

	private ArrayList<FunItem> myData;
	private LayoutInflater mInflater;
	private Context context;
	private ArrayAdapter<String> adapter;

	public FunItemAdapter(Context context, ArrayList<FunItem> _myData) {
		this.mInflater = LayoutInflater.from(context);
		this.myData = _myData;
		this.context = context;
	}

	@Override
	public int getCount() {
		return myData.size();
	}

	@Override
	public Object getItem(int position) {
		return myData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final FunItem fi = myData.get(position);
		if (fi.isDivide()) {
			TextView divide = new TextView(context);
			convertView = divide;
		} else if (fi.getType() == 0 || fi.getType() == 3) {// 0为其他，3为浏览设置。

			convertView = mInflater.inflate(R.layout.advancedfunitem, null);
			ViewHolder holder = new ViewHolder();
			holder.iconView = (ImageView) convertView
					.findViewById(R.id.MenuImage);
			holder.itemText = (TextView) convertView
					.findViewById(R.id.adMenuId);
			holder.itemText_big = (TextView) convertView
					.findViewById(R.id.adMenuId_big);
			if(fi.isMenu()) {
				holder.itemText_big.setVisibility(View.VISIBLE);
				holder.itemText.setVisibility(View.GONE);
			}else{
				holder.itemText_big.setVisibility(View.GONE);
				holder.itemText.setVisibility(View.VISIBLE);
			}
			
			
			holder.adMenuImage = (ImageView) convertView
					.findViewById(R.id.adMenuImage);
			holder.itemLayout = convertView.findViewById(R.id.itemLayout);
			holder.arrow = (ImageView) convertView.findViewById(R.id.fun_arrow);
			holder.newNum = (TextView) convertView.findViewById(R.id.new_num);
			if (arrowFlag) {
				holder.arrow.setVisibility(View.VISIBLE);
			} else {
				holder.arrow.setVisibility(View.GONE);
			}
			if (myData.get(position).getIcon() > 0) {
				holder.iconView
						.setImageResource(myData.get(position).getIcon());
			}else{
				holder.iconView.setVisibility(View.GONE);
			}
			if (myData.get(position).getNewCount() > 0) {
				holder.newNum.setVisibility(View.VISIBLE);
				holder.newNum.setText(myData.get(position).getNewCount() + "");
				holder.arrow.setVisibility(View.GONE);
			}
			if (myData.get(position).getrImage() > 0) {
				holder.arrow.setVisibility(View.VISIBLE);
				holder.arrow.setImageResource(myData.get(position).getrImage());
			}
			holder.itemText.setText(myData.get(position).getText());
			holder.itemText_big.setText(myData.get(position).getText());
			if (position != 0 && (position + 1) < myData.size()) {
				if (myData.get(position - 1).isDivide()
						&& !myData.get(position + 1).isDivide()) {
					holder.itemLayout
							.setBackgroundResource(R.drawable.ic_preference_first);
				} else if (!myData.get(position - 1).isDivide()
						&& myData.get(position + 1).isDivide()) {
					holder.itemLayout
							.setBackgroundResource(R.drawable.ic_preference_last);

				} else if (!myData.get(position - 1).isDivide()
						&& !myData.get(position + 1).isDivide()) {
					holder.itemLayout
							.setBackgroundResource(R.drawable.ic_preference_middle);
				} else {
					holder.itemLayout
							.setBackgroundResource(R.drawable.ic_preference_one);
				}
			}
		} else if (fi.getType() == 1) {// 此时为开关样式

			convertView = mInflater.inflate(R.layout.msg_setting_item, null);
			ViewHolder holder = new ViewHolder();
			holder.iconView = (ImageView) convertView
					.findViewById(R.id.msgMenuImage);
			holder.itemText = (TextView) convertView
					.findViewById(R.id.msgAdMenuId);
			holder.adMenuImage = (ImageView) convertView
					.findViewById(R.id.msgAdMenuImage);
			holder.itemLayout = convertView.findViewById(R.id.itemLayout);

			holder.checkBox = (CheckBox) convertView
					.findViewById(R.id.msgCheckBox);
			holder.checkBox.setChecked(fi.isChecked());
			if (myData.get(position).getIcon() > 0) {
				holder.iconView
						.setImageResource(myData.get(position).getIcon());
			}
			if (myData.get(position).getNewCount() > 0) {
				holder.adMenuImage.setVisibility(View.VISIBLE);
			} else {
				holder.adMenuImage.setVisibility(View.GONE);
			}
			holder.itemText.setText(myData.get(position).getText());
			if (position != 0 && (position + 1) < myData.size()) {
				if (myData.get(position - 1).isDivide()
						&& !myData.get(position + 1).isDivide()) {
					holder.itemLayout
							.setBackgroundResource(R.drawable.ic_preference_first);
				} else if (!myData.get(position - 1).isDivide()
						&& myData.get(position + 1).isDivide()) {
					holder.itemLayout
							.setBackgroundResource(R.drawable.ic_preference_last);

				} else if (!myData.get(position - 1).isDivide()
						&& !myData.get(position + 1).isDivide()) {
					holder.itemLayout
							.setBackgroundResource(R.drawable.ic_preference_middle);
				} else {
					holder.itemLayout
							.setBackgroundResource(R.drawable.ic_preference_one);
				}
			}
		}
		return convertView;
	}

	@Override
	public boolean isEnabled(int position) {
		if (myData.get(position).isDivide()) {
			return false;
		}
		return super.isEnabled(position);
	}

	public void setArrowFlag(Boolean isShow) {
		this.arrowFlag = isShow;
	}
}
