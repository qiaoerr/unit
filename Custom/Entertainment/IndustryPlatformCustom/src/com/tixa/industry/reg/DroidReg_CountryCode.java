package com.tixa.industry.reg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tixa.industry.R;
import com.tixa.industry.model.CountryCode;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.widget.SideBar;
import com.tixa.industry.widget.TopBar;
import com.tixa.industry.widget.TopBar.TopBarListener;

public class DroidReg_CountryCode extends Activity implements
		OnItemClickListener {

	private ArrayList<CountryCode> codeList = null;
	private CountryCodeAdapter adapter = null;
	private Context context;
	private EditText edit;
	private ImageView delBtn;
	private TopBar topbar;
	private ListView list;
	private SideBar sidebar;
	private TextView dialogText;
	private final static int COUNTRY_CODE = 101;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				ArrayList<CountryCode> tempList = (ArrayList<CountryCode>) msg.obj;
				if (tempList != null && tempList.size() > 0) {
					codeList = tempList;
					adapter = new CountryCodeAdapter(context, codeList);
					list.setAdapter(adapter);
					sidebar.setListView(list);
					sidebar.setDialogText(dialogText);
					sidebar.invalidate();
					sidebar.setVisibility(View.VISIBLE);
					saveFile();
				} else {
					Toast.makeText(context, "获取失败", Toast.LENGTH_SHORT).show();
				}
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_countrycode);
		context = this;
		initView();
		getData();
	}

	private void initView() {
		list = (ListView) findViewById(R.id.list);
		dialogText = (TextView) findViewById(R.id.fast_position);
		list.setOnItemClickListener(this);
		topbar = (TopBar) findViewById(R.id.topbar);
		//topbar.showButtonText("返回", "", "");
//		topbar.setBackgroundImage(1);
		topbar.showButtonImage(R.drawable.top_back, 0, 0);
		topbar.showConfig("注册", true, false, false, false);
		topbar.setmListener(new TopBarListener() {

			@Override
			public void onButton3Click(View view) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onButton2Click(View view) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onButton1Click(View view) {
				finish();
			}
		});
		View view = LayoutInflater.from(context).inflate(
				R.layout.layout_searchbar, null);
		edit = (EditText) view.findViewById(R.id.EditText_Search);
		edit.setHint("搜索国家代码");
		delBtn = (ImageView) view.findViewById(R.id.btn_del_search);
		edit.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2,
					int arg3) {
				if (StrUtil.isNotEmpty(edit.getText().toString())) {
					delBtn.setVisibility(View.VISIBLE);
				} else {
					delBtn.setVisibility(View.GONE);
				}
				if (adapter != null)
					adapter.getFilter().filter(s);
				adapter.notifyDataSetChanged();

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		delBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				edit.setText("");
			}
		});
		list.addHeaderView(view);
		sidebar = (SideBar) findViewById(R.id.sideBar);
		sidebar.setVisibility(View.GONE);
	}

	private void saveFile() {
		String dic = getCacheDir() + "/";
		String fileName = "countryCode.tx";
		try {
			FileUtil.saveFile(dic, fileName, codeList);
		} catch (Exception e) {
		}
	}

	private void getData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				ArrayList<CountryCode> tempList = (ArrayList<CountryCode>) FileUtil
						.getFile(getCacheDir() + "/" + "countryCode.tx");
				if (tempList == null || tempList.size() <= 0) {
					tempList = getFileToArrList("country_code.txt");
				}
				Message msg = new Message();
				msg.what = 0;
				msg.obj = tempList;
				handler.sendMessage(msg);
			}
		}).start();
	}

	public ArrayList<CountryCode> getFileToArrList(String fileName) {
		ArrayList<CountryCode> tempList = new ArrayList<CountryCode>();
		String str = "";
		InputStream in = null;
		BufferedReader reader = null;
		try {
			in = getResources().getAssets().open(fileName);
			reader = new BufferedReader(new InputStreamReader(in));
			while ((str = reader.readLine()) != null) {
				String temp = new String(str.getBytes(), "UTF8");
				CountryCode code = new CountryCode();
				code.setCountryName(temp.split(",")[0]);
				code.setCountryCode(temp.split(",")[1]);
				tempList.add(code);
			}
			Collections.sort(tempList, new Comparator<CountryCode>() {

				@Override
				public int compare(CountryCode c1, CountryCode c2) {
					if (c1.getCountryName_pinyin() == '#'
							&& c2.getCountryName_pinyin() != '#') {
						return 1;
					} else if (c1.getCountryName_pinyin() != '#'
							&& c2.getCountryName_pinyin() == '#') {
						return -1;
					} else if (c1.getCountryName_pinyin() == '#'
							&& c2.getCountryName_pinyin() == '#') {
						return 0;
					} else {
						if (c1.getCountryName_pinyin() > c2
								.getCountryName_pinyin()) {
							return 1;
						} else if (c1.getCountryName_pinyin() < c2
								.getCountryName_pinyin()) {
							return -1;
						} else {
							return 0;
						}
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return tempList;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		position = position - list.getHeaderViewsCount();
		String str1 = codeList.get(position).getCountryName();
		String str2 = codeList.get(position).getCountryCode();
		Intent intent = new Intent(context, DroidRegMain.class);
		intent.putExtra("CountryName", str1);
		intent.putExtra("CountryCode", str2);
		setResult(COUNTRY_CODE, intent);
		finish();
	}
}
