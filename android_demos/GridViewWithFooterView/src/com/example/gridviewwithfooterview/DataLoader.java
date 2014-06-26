package com.example.gridviewwithfooterview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

public class DataLoader {
	private Context context;
	private OnCompletedListener l;

	public DataLoader(Context mContext) {
		this.context = mContext;

	}

	public void setOnCompletedListerner(OnCompletedListener mL) {
		l = mL;
	}

	public void startLoading(HashMap<String, String> mParams) {
		if (mParams != null) {
			LoadTask task = new LoadTask();
			task.execute(mParams);
		}
	}

	private class LoadTask extends
			AsyncTask<HashMap<String, String>, Void, List<String>> {

		@Override
		protected List<String> doInBackground(HashMap<String, String>... params) {
			List<String> ret = null;
			try {
				Thread.sleep(3050);
				// 模拟数据加载
				int page = Integer.parseInt(params[0].get("page"));
				int page_size = Integer.parseInt(params[0].get("page_size"));
				ret = new ArrayList<String>();
				for (int i = 1; i <= page_size; i++) {
					ret.add(((page - 1) * 10 + i) + "");
				}
			} catch (Exception e) {

			}

			return ret;
		}

		@Override
		protected void onPostExecute(List<String> ret) {

			if (ret == null) {
				l.onCompletedFailed("------------------faild");
			} else {
				l.onCompletedSucceed(ret);
			}

		}
	}

	public interface OnCompletedListener {
		public void onCompletedSucceed(List<String> list);

		public void onCompletedFailed(String str);

		public void getCount(int count);
	}
}
