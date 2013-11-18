package com.tixa.industry.activity;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.model.AppForm;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.AsyncImageLoader;
import com.tixa.industry.util.L;
import com.tixa.industry.util.LogUtil;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.T;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.widget.LXProgressDialog;
import com.tixa.industry.widget.TopBar;

public class ServiceActivity extends Fragment {
	private View view;
	private Activity context;
	private TopBar topbar;
	private EditText ly_name;
	private EditText ly_tel;
	private EditText ly_context;
	private EditText ly_email;
	private EditText ly_qq;
	private EditText ly_sex;
	private Button button;
	private HttpApi api;
	private String appID;
	private String memberID;
	private IndustryApplication application;
	private LinearLayout qqlayout;
	private LinearLayout emaillayout;
	private LinearLayout addlayout;
	private LinearLayout sexlayout;
	private EditText address;
	private PageConfig config;
	private TopBarUtil util;
	private String modularName;
	private boolean isNav = false;
	private AsyncImageLoader loader;
	private LXProgressDialog dialog;
	private AppForm form;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (dialog != null) {
				dialog.dismiss();
			}
			switch (msg.what) {
			case Data.NONETWORK:
				T.shortT(context, getResources().getString(R.string.nonetwork));
				initView();
				break;
			case Data.FULLDATA:
				T.shortT(context, "提交成功");
				break;
			case Data.SUCCESS: // 成功
				form = (AppForm) msg.obj;
				initView();
				break;
			case Data.FAILED: // 失败
				initView();
				break;
			default:
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_liuyan, null);
		context = getActivity();
		getPageConfig();
		initData();
		getConfig();
		return view;

	}

	private void getConfig() {
		dialog = new LXProgressDialog(context, "正在加载数据");
		dialog.show();
		api.getAppForm(new RequestListener() {

			@Override
			public void onIOException(IOException e) {

			}

			@Override
			public void onError(TixaException e) {
				handler.sendEmptyMessage(Data.NONETWORK);
			}

			@Override
			public void onComplete(String response) {
				try {
					AppForm form = null;
					JSONObject obj = new JSONObject(response);
					if (obj.has("appForm")) {
						String res = obj.optString("appForm");
						if (res.equals("none")) {
							handler.sendEmptyMessage(Data.FAILED);
						} else {
							form = new AppForm(obj.optJSONObject("appForm"));
							Message msg = new Message();
							msg.what = Data.SUCCESS;
							msg.obj = form;
							handler.sendMessage(msg);
						}
					}

				} catch (JSONException e) {
					handler.sendEmptyMessage(Data.FAILED);
					e.printStackTrace();
				}

			}
		});
	}

	private void initData() {
		application = IndustryApplication.getInstance();
		appID = application.getAppID();
		api = new HttpApi(appID);
		modularName = getArguments().getString("modularName");
		isNav = getArguments().getBoolean("isNav");
		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();
		topbar = (TopBar) view.findViewById(R.id.topbar);
		util = new TopBarUtil(isNav, naviStyle, topbar, modularName,
				getFragmentManager(), context, application.getTemplateId(),
				true, navi);
		util.showConfig();
	}

	private void getPageConfig() {
		application = IndustryApplication.getInstance();
		PageConfigParser p = new PageConfigParser(context,
				"layout/MoreLayout.xml");
		config = p.parser();
	}

	private void initView() {

		loader = new AsyncImageLoader(context);
		ly_name = (EditText) view.findViewById(R.id.ly_name);
		ly_tel = (EditText) view.findViewById(R.id.ly_tel);
		ly_context = (EditText) view.findViewById(R.id.ly_context);
		ly_email = (EditText) view.findViewById(R.id.ly_email);
		ly_qq = (EditText) view.findViewById(R.id.ly_qq);
		ly_sex = (EditText) view.findViewById(R.id.ly_sex);
		button = (Button) view.findViewById(R.id.userButton);

		qqlayout = (LinearLayout) view.findViewById(R.id.qqlayout);
		emaillayout = (LinearLayout) view.findViewById(R.id.emaillayout);
		addlayout = (LinearLayout) view.findViewById(R.id.addlayout);
		sexlayout = (LinearLayout) view.findViewById(R.id.sexlayout);
		address = (EditText) view.findViewById(R.id.address);

		if (form != null) {
			if (form.getIsAddressShow() == 1) {
				addlayout.setVisibility(View.VISIBLE);
			} else {
				addlayout.setVisibility(View.GONE);
			}

			if (form.getIsMailShow() == 1) {
				emaillayout.setVisibility(View.VISIBLE);
			} else {
				emaillayout.setVisibility(View.GONE);
			}

			if (form.getIsQqShow() == 1) {
				qqlayout.setVisibility(View.VISIBLE);
			} else {
				qqlayout.setVisibility(View.GONE);
			}

			if (form.getIsSexShow() == 1) {
				sexlayout.setVisibility(View.VISIBLE);
			} else {
				sexlayout.setVisibility(View.GONE);
			}

		}

		ly_sex.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				changeGender();
			}
		});
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getData();
			}
		});
	}

	private void getData() {
		String ly_nameStr = ly_name.getText().toString().trim();
		String ly_telStr = ly_tel.getText().toString().trim();
		String ly_contextStr = ly_context.getText().toString().trim();
		String ly_emailStr = ly_email.getText().toString().trim();
		String ly_qqStr = ly_qq.getText().toString().trim();
		String ly_sexStr = ly_sex.getText().toString().trim();
		String ly_add = address.getText().toString().trim();
		int gender = 0;
		if (StrUtil.isNotEmpty(ly_sexStr) && ly_sexStr.equals("男")) {
			gender = 1;
		} else if (StrUtil.isNotEmpty(ly_sexStr) && ly_sexStr.equals("女")) {
			gender = 0;
		}

		if (StrUtil.isEmpty(ly_nameStr)) {
			LogUtil.show(context, "名字不能为空", 2000);
			return;
		} else if (StrUtil.isEmpty(ly_telStr)) {
			LogUtil.show(context, "手机号不能为空", 2000);
			return;
		} else if (StrUtil.isEmpty(ly_contextStr)) {
			LogUtil.show(context, "留言内容不能为空", 2000);
			return;
		} else if (ly_telStr.length() < 6) {
			LogUtil.show(context, "手机号格式不对", 2000);
			return;
		}

		if (form != null) {
			if (form.getIsAddressShow() == 1 && form.getIsAddressCheck() == 1) {
				if (StrUtil.isEmpty(ly_add)) {
					LogUtil.show(context, "地址不能为空", 2000);
					return;
				}
			} else if (form.getIsMailShow() == 1 && form.getIsMailCheck() == 1) {
				if (StrUtil.isNotEmpty(ly_emailStr)
						&& !ly_emailStr.contains("@")) {
					LogUtil.show(context, "邮箱格式不对", 2000);
					return;
				}
			} else if (form.getIsQqShow() == 1 && form.getIsQqCheck() == 1) {
				if (StrUtil.isEmpty(ly_qqStr)) {
					LogUtil.show(context, "QQ不能为空", 2000);
					return;
				}
			} else if (form.getIsSexShow() == 1 && form.getIsSexCheck() == 1) {
				if (StrUtil.isEmpty(ly_sexStr)) {
					LogUtil.show(context, "性别不能为空", 2000);
					return;
				}
			}
		}

		dialog = new LXProgressDialog(context, "正在发送数据,请稍候");
		dialog.show();
		api.createGuessBook(ly_nameStr, gender, memberID, ly_telStr,
				ly_emailStr, ly_contextStr, ly_add, new RequestListener() {

					@Override
					public void onIOException(IOException e) {
						T.shortT(context, "未知错误:" + e.getMessage());
					}

					@Override
					public void onError(TixaException e) {
						L.e("未知错误:" + e.getMessage() + " " + e.getStatusCode());
						handler.sendEmptyMessage(Data.NONETWORK);
					}

					@Override
					public void onComplete(String response) {

						handler.sendEmptyMessage(Data.FULLDATA);
						ly_name.setText("");
						ly_tel.setText("");
						ly_context.setText("");

					}
				});
	}

	private void changeGender() {

		final String[] items = new String[] { "男", "女" };
		AlertDialog dlg = new AlertDialog.Builder(context).setTitle("设置")
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, final int item) {
						ly_sex.setText(items[item]);

					}
				}).create();
		dlg.show();
	}
}
