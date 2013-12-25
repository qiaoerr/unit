package com.mobeta.android.demodslv;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobeta.android.dslv.DragSortListView;
import com.mobeta.android.dslv.DragSortListView.RemoveListener;

public class MultipleChoiceListView extends Activity {
	private ArrayAdapter<String> adapter;
	private DragSortListView dragSortListView;
	private Context context;
	ArrayList<String> arrayList;
	private LayoutInflater inflater;
	private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
		@Override
		public void drop(int from, int to) {
			if (from != to) {
				String item = adapter.getItem(from);
				adapter.remove(item);
				adapter.insert(item, to);
				dragSortListView.moveCheckState(from, to);
			}
		}
	};

	private RemoveListener onRemove = new DragSortListView.RemoveListener() {
		@Override
		public void remove(int which) {
			String item = adapter.getItem(which);
			adapter.remove(item);
			dragSortListView.removeCheckState(which);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkable_main);
		context = this;
		inflater = LayoutInflater.from(context);
		dragSortListView = (DragSortListView) findViewById(R.id.myDragSortListView);
		String[] array = getResources().getStringArray(
				R.array.jazz_artist_names);
		arrayList = new ArrayList<String>(Arrays.asList(array));

		adapter = new ArrayAdapter<String>(this, R.layout.list_item_checkable,
				R.id.text, arrayList);

		// dragSortListView.setAdapter(adapter);
		dragSortListView.setAdapter(new MyAdapter());

		dragSortListView.setDropListener(onDrop);
		dragSortListView.setRemoveListener(onRemove);
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
