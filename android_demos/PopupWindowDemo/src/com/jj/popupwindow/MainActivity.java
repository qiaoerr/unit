package com.jj.popupwindow;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
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
		System.out.println("button.getTop()QQ: " + button.getTop() + " button.getBottom()QQ: "
				+ button.getBottom());
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				button.getTop();
				int y = button.getBottom()
						+ (int) (25 * getResources().getDisplayMetrics().density);
				System.out.println("button.getTop(): " + button.getTop() + " button.getBottom(): "
						+ button.getBottom());
				int x = getResources().getDisplayMetrics().widthPixels / 4;
				showPopupWindow(x, y);
			}
		});
	}

	public void showPopupWindow(int x, int y) {
		if (popupWindow == null) {
			layout = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog,
					null);
			layout.setBackgroundColor(Color.BLUE);
			listView = (ListView) layout.findViewById(R.id.lv_dialog);
			listView.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.text,
					R.id.tv_text, title));
			popupWindow = new PopupWindow(layout, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT, true);
			popupWindow.setBackgroundDrawable(new BitmapDrawable());// �����ã����popupWindow֮��Ĳ��֣��������ڲ���رա�
			// popupWindow.setOutsideTouchable(true);
			popupWindow.setFocusable(true);
			// popupWindow.setWindowLayoutMode(0, 0);
			popupWindow.setAnimationStyle(R.style.PopupAnimation);
			// �����ĳ��view��λ
			popupWindow.showAsDropDown(findViewById(R.id.tv_title));
			// ����������ֻ���Ļ��λ,��Ҫָ��Gravity��Ĭ�������center.
			// popupWindow.showAtLocation(findViewById(R.id.main), Gravity.LEFT
			// | Gravity.TOP, x, y);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					button.setText(title[arg2]);
					button.invalidate();
					popupWindow.dismiss();
				}
			});
		} else {
			popupWindow.showAsDropDown(findViewById(R.id.tv_title));
		}
	}
}