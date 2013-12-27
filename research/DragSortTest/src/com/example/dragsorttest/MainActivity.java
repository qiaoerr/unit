package com.example.dragsorttest;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.drag.DragSortListView;
import com.example.drag.DragSortListView.RemoveListener;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity implements OnItemClickListener {
	private DragSortListView dragSortListView;
	private Context context;
	ArrayList<String> arrayList;
	private NoScrollListView listView;
	private LayoutInflater inflater;
	private MyAdapter adapter;
	private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
		@Override
		public void drop(int from, int to) {
			if (from != to) {
				String str = arrayList.get(from);
				for (int i = 0; i < arrayList.size(); i++) {
					System.out.println(arrayList.get(i));
				}
				arrayList.remove(str);
				arrayList.add(to, str);
				for (int i = 0; i < arrayList.size(); i++) {
					System.out.println(arrayList.get(i));
				}
				adapter.notifyDataSetChanged();
				dragSortListView.moveCheckState(from, to);
			}
		}
	};

	private RemoveListener onRemove = new DragSortListView.RemoveListener() {
		@Override
		public void remove(int which) {
			// String item = adapter.getItem(which);
			// adapter.remove(item);
			dragSortListView.removeCheckState(which);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		inflater = LayoutInflater.from(context);
		arrayList = new ArrayList<String>();
		arrayList.add("one");
		arrayList.add("one1");
		arrayList.add("one2");
		arrayList.add("one3");
		arrayList.add("one4");
		arrayList.add("one5");
		arrayList.add("one6");
		arrayList.add("one7");
		arrayList.add("one8");
		arrayList.add("one9");
		listView = (NoScrollListView) findViewById(R.id.listtest);
		listView.setOnItemClickListener(this);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		dragSortListView = (DragSortListView) findViewById(R.id.myDragSortListView);
		adapter = new MyAdapter();
		listView.setAdapter(adapter);
		dragSortListView.setAdapter(adapter);
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
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			CheckableLinearLayout view = (CheckableLinearLayout) inflater
					.inflate(R.layout.list_item_checkable, null);
			ImageView imageView = (ImageView) view
					.findViewById(R.id.drag_handle);
			if (arrayList.get(position).equals("one")) {
				imageView.setBackgroundResource(R.drawable.ic_launcher);
			}
			TextView textView = (TextView) view.findViewById(R.id.text);
			textView.setText(arrayList.get(position));
			return view;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		System.out.println("listView.getCheckedItemCount()"
				+ listView.getCheckedItemCount());
		/*long[] ids = listView.getCheckedItemIds();
		for (int i = 0; i < ids.length; i++) {
			System.out.println("ids: " + ids[i]);
		}*/
		SparseBooleanArray arrays = listView.getCheckedItemPositions();
		for (int i = 0; i < arrays.size(); i++) {
			System.out.println("arrays: " + arrays.get(i));
		}
	}
}
