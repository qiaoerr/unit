package com.tixa.industry.reg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.config.Constants;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.IndexData;
import com.tixa.industry.model.MemberUser;
import com.tixa.industry.util.L;
import com.tixa.industry.util.LogUtil;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.T;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.widget.LXProgressDialog;

public class DroidReg3 extends Fragment implements OnClickListener {
	private Activity context;
	private EditText userName, userPwd;
	private final String TAG = "reg";
	private Button regComplete;
	private String mobile;
	private String password;
	private LXProgressDialog pd;
	private IndustryApplication config;
	private HttpApi api;
	private String appID;
	private IndexData data;
	private int checkCount = 0;
	private String onOkActivity;
//	private final String CHECK_URL = Constant.webDomain
//			+ "account/regMobile.jsp";
	private long accountId;
	private View view;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Data.FULLDATA:
				if (pd != null)
					pd.dismiss();
				String result = (String) msg.obj;
				try {
					progressResult(result);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			case Data.NODATA:

				break;	
			case 2:	
				Intent bIntent = new Intent();
				bIntent.setAction(Constants.MY_REGISTER_SUCCESS_ACTION);
				//bIntent.putExtra("position", 0);
				context.sendBroadcast(bIntent);
				context.finish();
				break;	
				
			case Data.NONETWORK:
				T.shortT(context, getResources().getString(R.string.nonetwork));
				break;
			default:
				break;
			}	
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_reg_3, container, false);
		context = getActivity();
		onOkActivity = context.getIntent().getStringExtra("onOkActivity");
		initData();
		initView();
		initParam();
		return view;
	}

	private void initParam() {
		mobile = getArguments().getString("mobile");
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
		userName = (EditText) view.findViewById(R.id.fav_name);
		userPwd = (EditText) view.findViewById(R.id.password);
		regComplete = (Button) view.findViewById(R.id.regComplete);
		regComplete.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.regComplete) {
			final String name = userName.getText().toString();
			password = userPwd.getText().toString();
			if (StrUtil.isNotEmpty(password)) {
				pd = new LXProgressDialog(context, "正在发送");
				pd.show();
				final String str;
				if (StrUtil.isEmpty(name)) {
					str=mobile;
				} else {
					str=name;
				}
				api.mobileRegister(mobile, str, password, new RequestListener() {
					
					@Override
					public void onIOException(IOException e) {
						T.shortT(context, "未知错误:"+e.getMessage());
					}
					
					@Override
					public void onError(TixaException e) {
						L.e("未知错误:"+e.getMessage()+" "+e.getStatusCode());
						handler.sendEmptyMessage(Data.NONETWORK);	
					}
					
					@Override
					public void onComplete(String response) {
						Message msg = new Message();
						msg.what = Data.FULLDATA;
						msg.obj = response;
						handler.sendMessage(msg);
					
					}
				});
	
			} else {
				LogUtil.show(context, "密码不能为空", 3000);
			}
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
				JSONObject tempJson = null;
				if (json.optInt("memberUser") == -1) {
					LogUtil.show(context, "手机号或密码不能为空", 2000);
				} else if (json.optInt("memberUser") == -2) {
					LogUtil.show(context, "密码不能小于6位", 2000);
				} else if (json.optInt("memberUser") == -3) {
					LogUtil.show(context, "验证码不能为空", 2000);
				} else if (json.optInt("memberUser") == -4) {
					LogUtil.show(context, "手机号格式不正确", 2000);
				} else if (json.optInt("memberUser") == -6) {
					LogUtil.show(context, "验证码已过期", 2000);
				} else if (json.optInt("memberUser") == -7) {
					LogUtil.show(context, "验证码不正确", 2000);
				} else if (json.optInt("memberUser") == -5) {
					LogUtil.show(context, "该账号已注册", 2000);
				} else if (json.optInt("memberUser") == -10 ) {
					LogUtil.show(context, "用户名已存在", 2000);
				} else {
					tempJson = json.optJSONObject("memberUser");
				}
				
				writeToPre("userMessage", tempJson.toString());
				MemberUser ai = new MemberUser(json);		
				IndustryApplication app =  IndustryApplication.getInstance();
				app.setMemberUser(ai);
				
				handler.sendEmptyMessage(2);		
					
			}
		} else {
			LogUtil.show(context, "注册信息有误", 3000);
		}

	}
	
	private void writeToPre(String key, String str) {
		SharedPreferences sp = context.getSharedPreferences("userMessage",
				0);
		SharedPreferences.Editor edit = sp.edit();
		edit.putString(key, str);
		edit.commit();
	}
	
	private ClickListener3 clickListener;

	public ClickListener3 getClickListener() {
		return clickListener;
	}

	public void setClickListener(ClickListener3 clickListener) {
		this.clickListener = clickListener;
	}

	public interface ClickListener3 {
		public void onclick(View view, String mobile, String password);
	}
}
