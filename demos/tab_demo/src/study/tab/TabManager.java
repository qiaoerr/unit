package study.tab;

import java.util.HashMap;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class TabManager implements OnTabChangeListener {
	private Activity mactivity = null;
	private TabHost mtabHost = null;
	private HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
	TabInfo lastTab = null;

	public TabManager(Activity mactivity, TabHost mtabHost) {
		this.mactivity = mactivity;
		this.mtabHost = mtabHost;
		mtabHost.setOnTabChangedListener(this);

	}

	public void addTab(TabSpec tabSpec, Class<?> clss, Bundle bundle) {
		tabSpec.setContent(contentFactory);
		String tag = tabSpec.getTag();
		bundle = new Bundle();
		bundle.putString("tag", tag);
		TabInfo tabInfo = new TabInfo(tag, clss, bundle);
		mTabs.put(tag, tabInfo);
		mtabHost.addTab(tabSpec);
	}

	private class TabInfo {
		private final String tag;
		private final Class<?> clss;
		private final Bundle args;
		private Fragment fragment;

		public TabInfo(String tag, Class<?> clss, Bundle args) {
			this.tag = tag;
			this.clss = clss;
			this.args = args;
		}

	}

	TabHost.TabContentFactory contentFactory = new TabHost.TabContentFactory() {

		@Override
		public View createTabContent(String tag) {
			View view = new View(mactivity);
			if (tag.equals("simple")) {
				view.setBackgroundColor(Color.BLUE);
			} else {
				view.setBackgroundColor(Color.RED);
			}
			return view;
		}
	};

	@Override
	public void onTabChanged(String tabId) {
		// FragmentManager fm = mactivity.getSupportFragmentManager();
		// Bundle bundle = new Bundle();
		// bundle.putString("tag", tabId);
		// DetailoneFragment detailoneFragment = new DetailoneFragment();
		// detailoneFragment.setArguments(bundle);
		// fm.beginTransaction().replace(R.id.realtabcontent, detailoneFragment)
		// .commit();
		TabInfo newTabInfo = mTabs.get(tabId);
		FragmentTransaction ft = mactivity.getFragmentManager()
				.beginTransaction();
		if (newTabInfo != lastTab) {
			if (lastTab != null) {
				if (lastTab.fragment != null) {
					ft.detach(lastTab.fragment);
				}
			}
			if (newTabInfo != null) {
				if (newTabInfo.fragment == null) {
					newTabInfo.fragment = Fragment.instantiate(mactivity,
							newTabInfo.clss.getName(), newTabInfo.args);
					ft.add(R.id.realtabcontent, newTabInfo.fragment,
							newTabInfo.tag);
				} else {
					ft.attach(newTabInfo.fragment);
				}
			}
			lastTab = newTabInfo;
			ft.commit();
			// mactivity.getSupportFragmentManager().executePendingTransactions();
		}
	}
}
