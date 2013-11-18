package com.tixa.industry.reg;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.config.Constants;
import com.tixa.industry.config.DBManager;
import com.tixa.industry.model.AccountInfo;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.IndexData;
import com.tixa.industry.model.MemberUser;
import com.tixa.industry.util.L;
import com.tixa.industry.util.LogUtil;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.T;
import com.tixa.industry.util.TixaException;

public class DroidReg4 extends Fragment implements OnClickListener {
	private Activity context;
	private Button regComplete;
	private String mobile;
	private String password;
	private String onOkActivity;
	private IndustryApplication config;
	private HttpApi api;
	private String appID;
	private IndexData data;
	private View view;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Data.NODATA:

				break;
			case Data.NONETWORK:
				T.shortT(context, getResources().getString(R.string.nonetwork));
				break;
			case 0:
				Toast.makeText(context, "用户名或者密码错误", Toast.LENGTH_LONG).show();
				context.finish();
				break;
			case 1:
				Toast.makeText(context, "登录失败", Toast.LENGTH_LONG).show();
				context.finish();
				break;
			case 2:
				// Intent intent = new Intent(context, MainAct.class);
				// startActivity(intent);

				break;
			default:
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_reg_4, container, false);
		context = getActivity();
		onOkActivity = context.getIntent().getStringExtra("onOkActivity");
		initData();
		initView();
		initParam();
		return view;
	}

	private void initParam() {
		mobile = getArguments().getString("mobile");
		password = getArguments().getString("password");
	}

	Runnable r = new Runnable() {

		@Override
		public void run() {
			handler.sendEmptyMessage(0);
		}
	};

	private void initData() {
		config = IndustryApplication.getInstance();
		appID = config.getAppID();
		api = new HttpApi(appID);
	}

	private void initView() {
		regComplete = (Button) view.findViewById(R.id.regComplete);
		regComplete.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.regComplete) {
			startLogin(mobile, password);
		}
	}

	private void progressResult(String result) throws JSONException {
		if (StrUtil.isNotEmpty(result)) {
			if (StrUtil.isHttpException(result)) {
				LogUtil.show(context, "网络连接异常", 3000);
			} else if (result.equals("[]")) {
				LogUtil.show(context, "注册失败", 3000);
			} else {
				// 处理json信息
				JSONObject json = new JSONObject(result);
				if (json.optInt("s") == -1) {
					LogUtil.show(context, "手机号或密码不能为空", 2000);
				} else if (json.optInt("s") == -2) {
					LogUtil.show(context, "密码不能小于6位", 2000);
				} else if (json.optInt("s") == -3) {
					LogUtil.show(context, "验证码不能为空", 2000);
				} else if (json.optInt("s") == -4) {
					LogUtil.show(context, "手机号格式不正确", 2000);
				} else if (json.optInt("s") == -6) {
					LogUtil.show(context, "验证码已过期", 2000);
				} else if (json.optInt("s") == -7) {
					LogUtil.show(context, "验证码不正确", 2000);
				} else if (json.optInt("s") == -5) {
					LogUtil.show(context, "该账号已注册", 2000);
				} else if (json.optInt("s") == 1) {
					if (clickListener != null)
						clickListener.onclick(regComplete, mobile);
				} else {
					LogUtil.show(context, "未知错误", 3000);
				}

			}
		} else {
			LogUtil.show(context, "注册信息有误", 3000);
		}

	}

	private void startLogin(final String usernameC, final String passwordC) {
		api.login(usernameC, passwordC, new RequestListener() {

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
				try {
					if (StrUtil.isHttpException(response)) {
						handler.sendEmptyMessage(1);
						return;
					} else if (Integer.parseInt(response) > 0) {
//						DBManager dm = new DBManager(context, Constants.DBNAME);
//						SQLiteDatabase db = dm.getWritableDatabase();
//						db.delete(Constants.LOGINTABLENAME, null, null);
						JSONObject tempJson = new JSONObject(response);
						writeToPre("MemberMessage", tempJson.toString());
//						ContentValues cv = new ContentValues();
//						cv.put("username", usernameC);
//						cv.put("password", passwordC);
//						cv.put("autoLogin", 1);
//						cv.put("savepass", 1);
//						cv.put("info", tempJson.toString());
//						db.insert(Constants.LOGINTABLENAME, null, cv);
//						db.close();
//						dm.close();
						MemberUser ai = new MemberUser(tempJson);
						IndustryApplication app = (IndustryApplication) context.getApplication();
						app.setMemberUser(ai);
						handler.sendEmptyMessage(2);
						return;
					} else {
						handler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					handler.sendEmptyMessage(0);
				} finally {
				}
			}
		});
	}

	private ClickListener3 clickListener;

	public ClickListener3 getClickListener() {
		return clickListener;
	}

	public void setClickListener(ClickListener3 clickListener) {
		this.clickListener = clickListener;
	}

	public interface ClickListener3 {
		public void onclick(View view, String mobile);
	}
	private void writeToPre(String key, String str) {
		SharedPreferences sp = getSharedPreferences("userMessage",
				0);
		SharedPreferences.Editor edit = sp.edit();
		edit.putString(key, str);
		edit.commit();
	}

	private String readFromPre(String key) {
		SharedPreferences sp = getSharedPreferences("userMessage",
				0);
		return sp.getString(key, "");
	}

	private SharedPreferences getSharedPreferences(String string, int i) {
		// TODO Auto-generated method stub
		return null;
	}
}
