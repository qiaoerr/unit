package com.example.dragsorttest;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.drag.DragSortListView;

public class MainActivity extends Activity {
	private DragSortListView dragSortListView;
	private Context context;
	ArrayList<String> arrayList;
	private LayoutInflater inflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		inflater = LayoutInflater.from(context);
		dragSortListView = (DragSortListView) findViewById(R.id.myDragSortListView);
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arrayList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			CheckableLinearLayout view = (CheckableLinearLayout) inflater
					.inflate(R.layout.list_item_checkable, null);
			TextView textView = (TextView) view.findViewById(R.id.text);
			textView.setText(arrayList.get(position));
			return view;
		}

	}
}
