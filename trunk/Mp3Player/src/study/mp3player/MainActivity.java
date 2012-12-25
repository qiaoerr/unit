package study.mp3player;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;

public class MainActivity extends FragmentActivity {
	private TabHost tabHost = null;
	private ViewPager viewPager = null;
	private ArrayList<study.mp3player.MainActivity.PaperAdaper.Tabinfo> arrayList = new ArrayList<MainActivity.PaperAdaper.Tabinfo>();
	private boolean boo = true;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.main_activity);
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		viewPager = (ViewPager) findViewById(R.id.pager);
		PaperAdaper paperAdaper = new PaperAdaper(getSupportFragmentManager());
		paperAdaper.addTabs(
				tabHost.newTabSpec("Remote").setIndicator(
						"Remote",
						getResources().getDrawable(
								android.R.drawable.stat_sys_upload)),
				RemoteListFragment.class);
		paperAdaper.addTabs(
				tabHost.newTabSpec("Local").setIndicator(
						"Local",
						getResources().getDrawable(
								android.R.drawable.stat_sys_download)),
				LocalListFragment.class);

	}

	class PaperAdaper extends FragmentPagerAdapter implements
			OnTabChangeListener, OnPageChangeListener {

		public PaperAdaper(FragmentManager fm) {
			super(fm);
			tabHost.setOnTabChangedListener(this);
			viewPager.setOnPageChangeListener(this);
			viewPager.setAdapter(this);
		}

		private void addTabs(TabSpec tabSpec, Class<?> clss) {
			tabSpec.setContent(contentFactory);
			tabHost.addTab(tabSpec);
			String tag = tabSpec.getTag();
			Tabinfo info = new Tabinfo(clss, tag);
			arrayList.add(info);
		}

		private class Tabinfo {
			private Class<?> clss = null;
			private String tag = null;

			public Tabinfo(Class<?> clss, String tag) {
				this.clss = clss;
				this.tag = tag;
			}
		}

		TabContentFactory contentFactory = new TabContentFactory() {

			@Override
			public View createTabContent(String tag) {
				return new View(getApplicationContext());
			}
		};

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			int old = tabHost.getDescendantFocusability();
			tabHost.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
			tabHost.setCurrentTab(arg0);
			tabHost.setDescendantFocusability(old);
		}

		@Override
		public void onTabChanged(String tabId) {
			int item = tabHost.getCurrentTab();
			viewPager.setCurrentItem(item);
			System.out.println(item);
			if (item == 1) {
				sendBroadcast(new Intent("updtateLocalList"));
			}
		}

		@Override
		public Fragment getItem(int arg0) {
			Tabinfo info = arrayList.get(arg0);
			// Bundle bundle = new Bundle();
			// bundle.putInt("index", arg0);
			Fragment fragment = Fragment.instantiate(getApplicationContext(),
					info.clss.getName(), null);
			return fragment;
		}

		@Override
		public int getCount() {
			return arrayList.size();
		}

	}
}
