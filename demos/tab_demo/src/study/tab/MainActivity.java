package study.tab;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends Activity {
	TabHost tabHost = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("oncreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		TabManager tabManager = new TabManager(this, tabHost);
		tabManager.addTab(tabHost.newTabSpec("simple").setIndicator("simple"),
				DetailoneFragment.class, null);
		tabManager.addTab(tabHost.newTabSpec("custom").setIndicator("custom"),
				DetailoneFragment.class, null);
	}
}
