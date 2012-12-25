package study.mp3player;

import java.util.Iterator;
import java.util.List;

import study.download.FileUtils;
import study.model.Mp3Info;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LocalListFragment extends ListFragment {
	private static List<Mp3Info> mp3Infos = null;
	private MyBroadCastReceiver receiver = null;

	/**
	 * @return the mp3Infos
	 */
	public static List<Mp3Info> getMp3Infos() {
		return mp3Infos;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// System.out.println("onCreateView");
		// 更新本地音乐列表
		updateLocalMusicList();
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(getActivity(), PlayerActivity.class);
		intent.putExtra("index", position);
		getActivity().startActivity(intent);
	}

	@Override
	public void onResume() {
		System.out.println("onResume");
		receiver = new MyBroadCastReceiver();
		IntentFilter filter = new IntentFilter("updtateLocalList");
		getActivity().registerReceiver(receiver, filter);
		super.onResume();

	}

	@Override
	public void onPause() {
		// 必须要unregister，否则会报错
		getActivity().unregisterReceiver(receiver);
		super.onPause();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		System.out.println("onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	private void updateLocalMusicList() {
		String path = "mp3";
		mp3Infos = new FileUtils().getMp3Files(path);
		if (mp3Infos.size() == 0) {
			setListAdapter(new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1));
			return;
		}
		Iterator<Mp3Info> iterator = mp3Infos.iterator();
		StringBuffer sb = new StringBuffer();
		while (iterator.hasNext()) {
			Mp3Info mp3Info = (Mp3Info) iterator.next();
			sb.append(mp3Info.getMp3Name() + "==");
		}
		String[] strs = sb.toString().split("==");
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, strs));
	}

	private class MyBroadCastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			updateLocalMusicList();
		}
	}
}
