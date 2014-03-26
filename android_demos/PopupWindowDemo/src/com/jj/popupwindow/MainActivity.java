package com.jj.popupwindow;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/***
 * PopupWindow
 * 
 * @author zhangjia
 * 
 */
public class MainActivity extends Activity {
	private TextView button;
	private PopupWindow popupWindow;
	private LinearLayout layout;
	private ListView listView;
	private String[] title = { "ȫ��", "�ҵ�΢��", "�ܱ�", "�����Ű�", "ͬѧ" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		button = (TextView) findViewById(R.id.tv_title);
		System.out.println("button.getTop()QQ: " + button.getTop()
				+ " button.getBottom()QQ: " + button.getBottom());
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				button.getTop();
				int y = button.getBottom()
						+ (int) (25 * getResources().getDisplayMetrics().density);
				System.out.println("button.getTop(): " + button.getTop()
						+ " button.getBottom(): " + button.getBottom());
				int x = getResources().getDisplayMetrics().widthPixels / 4;
				showPopupWindow(x, y);
			}
		});
	}

	public void showPopupWindow(int x, int y) {
		layout = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(
				R.layout.dialog, null);
		layout.setBackgroundColor(Color.BLUE);
		listView = (ListView) layout.findViewById(R.id.lv_dialog);
		listView.setAdapter(new ArrayAdapter<String>(MainActivity.this,
				R.layout.text, R.id.tv_text, title));

		popupWindow = new PopupWindow(MainActivity.this);
		popupWindow.setContentView(layout);
		// popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow
				.setWidth(getResources().getDisplayMetrics().widthPixels / 2);
		popupWindow.setHeight(300);
		// popupWindow.setOutsideTouchable(false);
		popupWindow.setFocusable(true);
		popupWindow.setWindowLayoutMode(0, 0);
		// showAsDropDown��������view��Ϊ���������Ҫ������Ļparent
		// popupWindow.showAsDropDown(findViewById(R.id.tv_title), x, 10);
		popupWindow.showAtLocation(findViewById(R.id.main), Gravity.LEFT
				| Gravity.TOP, x, y);// ��Ҫָ��Gravity��Ĭ�������center.

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				button.setText(title[arg2]);
				button.invalidate();
				popupWindow.dismiss();
				popupWindow = null;
			}
		});
	}
}