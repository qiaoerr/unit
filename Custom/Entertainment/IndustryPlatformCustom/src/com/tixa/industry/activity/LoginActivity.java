package com.tixa.industry.activity;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.config.Constants;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.IndexData;
import com.tixa.industry.model.MemberUser;
import com.tixa.industry.reg.DroidRegMain;
import com.tixa.industry.util.AndroidUtil;
import com.tixa.industry.util.L;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.T;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.widget.LXProgressDialog;
import com.tixa.industry.widget.TopBar;
import com.tixa.industry.widget.TopBar.TopBarListener;


public class LoginActivity extends Activity implements OnClickListener,
		OnKeyListener {
	private EditText username;
	private EditText password;
	private Button loginButton;
	private TextView forgetPassword;
	private TopBar topbar;
	private String onOkActivity = "";
	private LoginActivity context;
	private LXProgressDialog pd = null;

	private IndustryApplication config;
	private HttpApi api;
	private String appID;
	private IndexData data;
	private int position;

	public static String uid = "";
	public String mAccessToken, mOpenId;
	private int appType;
	private final int LOGIN_ERROR = 1000;
	private final int LOGIN_OK = 1001;
	private String fromActivity;
	
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (pd != null)
				pd.dismiss();
			switch (msg.what) {
			
			case Data.NONETWORK:
				T.shortT(context, getResources().getString(R.string.nonetwork));
				break;
				
			case 1:
				Toast.makeText(context, getString(R.string.login_faile),
						Toast.LENGTH_LONG).show();
				break;
				
			case 2:	
				
			
				Intent bIntent = new Intent();
				bIntent.setAction(Constants.MY_LOGIN_SUCCESS_ACTION);
				bIntent.putExtra("position", position);
				sendBroadcast(bIntent);
				
				finish();
				break;
				
			case -1:
				T.shortT(context, "用户名错误");
				break;
				
			case -2:
				T.shortT(context, "账户未激活");
				break;
				
			case -3:
				T.shortT(context, "密码错误");
				break;
				
			case -4:
				T.shortT(context, "未知错误");
				break;
				
			case -10:
				T.shortT(context, "用户名或密码错误");
				break;	
				
			default:
				if (pd != null)
					pd.dismiss();
				break;
			}
			;

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		context = this;
		initData();
		Intent intent = getIntent();
		position = intent.getIntExtra("position", -1);
		fromActivity = intent.getStringExtra("fromActivity");
		topbar = (TopBar) findViewById(R.id.topbar);
		topbar.showConfig(getString(R.string.login), true, false, true, false);
		topbar.showButtonText("", "", getString(R.string.reg));
		topbar.showButtonImage(R.drawable.top_back, 0, 0);
		topbar.imageConfig(0, 0, R.drawable.top_right);
		topbar.setmListener(new TopBarListener() {
			@Override
			public void onButton3Click(View view) {
				startReg();
			}

			@Override
			public void onButton2Click(View view) {
			}

			@Override
			public void onButton1Click(View view) {			
				AndroidUtil.collapseSoftInputMethod(context, view);
				returnBack();
			}
		});

		onOkActivity = intent.getStringExtra("onOkActivity");
		Log.v("login", "onOkActivity = " + onOkActivity);
		username = (EditText) findViewById(R.id.username);
		username.setText(Constants.TESTNAME);
		password = (EditText) findViewById(R.id.password);
		password.setText(Constants.TESTPASSWORD);
		password.setOnKeyListener(this);
		loginButton = (Button) this.findViewById(R.id.login);
		loginButton.setOnClickListener(this);
		forgetPassword = (TextView) findViewById(R.id.forgetPassword);
		forgetPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Intent intent = new Intent(context, FoundPwd.class);
				// startActivity(intent);
			}
		});

	}

	private void initData() {
		config = IndustryApplication.getInstance();
		appID = config.getAppID();
		api = new HttpApi(appID);
	}

	public void startDialog(final String usernameC, final String passwordC) {
		pd = new LXProgressDialog(context, "正在登录");
		pd.show();
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
				if (StrUtil.isEmpty(response) || response.equals("error")) {
					handler.sendEmptyMessage(1);
				} else {
					try {
						loginManage(usernameC, passwordC, response, 0);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				}
			}
		});
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.login) {
			startLogin();
		}
	}

	private void startReg() {
		Intent intent = new Intent(context, DroidRegMain.class);
		intent.putExtra("onOkActivity", onOkActivity);
		intent.putExtra("appType", appType);
		startActivity(intent);
		finish();
	};

	private void startLogin() {
		String userStr = username.getText().toString().trim();
		String passStr = password.getText().toString().trim();
		if (userStr.equals("") || passStr.equals("")) {
			Toast.makeText(context, getString(R.string.login_toast_error),
					Toast.LENGTH_LONG).show();
			return;
		}
		startDialog(userStr, passStr);
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_ENTER
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			String userStr = username.getText().toString().trim();
			String passStr = password.getText().toString().trim();
			if (userStr.equals("") || passStr.equals("")) {
				Toast.makeText(context, getString(R.string.login_toast_error),
						Toast.LENGTH_LONG).show();
				return false;
			}
			startDialog(userStr, passStr);
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		pd = null;
		// if (receiver != null) {
		// unregisterIntentReceivers();
		// }
		super.onDestroy();
	}

	private void returnBack() {
		AndroidUtil.collapseSoftInputMethod(context, username);
		AndroidUtil.collapseSoftInputMethod(context, password);
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (pd != null)
				pd.dismiss();
			returnBack();
			return true;
		}
		return false;
	}

	private void loginManage(final String usernameC, final String passwordC,
			String resultStr, final int authType) throws JSONException {

		try {

			JSONObject tempJson = new JSONObject(resultStr);
			L.d("test", "tempJson.toString()="+tempJson.toString());
			JSONObject json = null;
			
			if(tempJson.has("memberUser")) {
				String result = tempJson.optString("memberUser");
				if(result.equals("-1")) {
					handler.sendEmptyMessage(-1);
					return;
				}else if(result.equals("-2")) {
					handler.sendEmptyMessage(-2);
					return;
				}else if(result.equals("-3")) {
					handler.sendEmptyMessage(-3);
					return;
				}else if(result.equals("-10")) {
					handler.sendEmptyMessage(-10);
					return;
				} else {			
					json = tempJson.optJSONObject("memberUser");
				}
			}
			
			writeToPre("userMessage", json.toString());
			MemberUser ai = new MemberUser(json);		
			IndustryApplication app =  IndustryApplication.getInstance();
			app.setMemberUser(ai);
			
			handler.sendEmptyMessage(2);			
		} catch (SQLException e) {
			handler.sendEmptyMessage(-4);		
			e.printStackTrace();
		}
	}
	private void writeToPre(String key, String str) {
		SharedPreferences sp = getSharedPreferences("userMessage",
				0);
		SharedPreferences.Editor edit = sp.edit();
		edit.putString(key, str);
		edit.commit();
	}

/*	
	private String readFromPre(String key) {
		SharedPreferences sp = getSharedPreferences("userMessage",
				0);
		return sp.getString(key, "");
	}*/
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	}

}