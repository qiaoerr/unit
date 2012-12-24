package study.mp3player;

import java.util.List;

import study.download.FileUtils;
import study.model.Mp3Info;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class LocalListFragment extends ListFragment {
	MyBroadCastReceiver receiver = null;
	Handler myHandler = new Handler();
	Runnable runnable = new Runnable() {

		@Override
		public void run() {

		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("onCreateView");
		// 更新本地音乐列表
		updateLocalMusicList();
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

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
	public void onActivityCreated(Bundle savedInstanceState) {
		System.out.println("onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		System.out.println("onAttach");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onPause() {
		getActivity().unregisterReceiver(receiver);
		System.out.println("onPause");
		super.onPause();
	}

	private void updateLocalMusicList() {
		String path = "mp3";
		List<Mp3Info> mp3Infos = new FileUtils().getMp3Files(path);
	}

	private class MyBroadCastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

		}
	}
}
