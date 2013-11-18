package com.tixa.industry.reg;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EncodingUtils;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.api.HttpApi;
import com.tixa.industry.model.Data;
import com.tixa.industry.model.IndexData;
import com.tixa.industry.util.L;
import com.tixa.industry.util.LogUtil;
import com.tixa.industry.util.RequestListener;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.T;
import com.tixa.industry.util.TixaException;
import com.tixa.industry.widget.LXProgressDialog;

public class DroidReg5 extends Fragment implements OnClickListener,
		OnCheckedChangeListener {
	private TextView userMobie;
	private CheckBox cb1;
	private EditText email, name, password;
	private Button userReg;
	private LXProgressDialog pd;
	private Activity context;
	private View view;
	private IndustryApplication config;
	private HttpApi api;
	private String appID;
	private IndexData data;
	private TextView agree_text;
	private final String TAG = "reg";
	private String onOkActivity;
	private String emailAddress, pwd;
	private final static String ENCODING = "GBK";
	private ClickListener5 clickListener;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Data.FULLDATA:
				if (pd != null)
					pd.dismiss();
				String result = (String) msg.obj;
				
				L.e("result is"+result);
				
				try {
					progressResult(result);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			case Data.NODATA:

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
		view = inflater.inflate(R.layout.layout_reg_5, container, false);
		context = getActivity();
		onOkActivity = context.getIntent().getStringExtra("onOkActivity");
		 initData();
		initView();
		return view;
	}

	private void initView() {
		cb1 = (CheckBox) view.findViewById(R.id.agree_checkbox);
		email = (EditText) view.findViewById(R.id.reg_email);
		name = (EditText) view.findViewById(R.id.reg_name);
		agree_text = (TextView) view.findViewById(R.id.agree_text);
		password = (EditText) view.findViewById(R.id.reg_password);
		userReg = (Button) view.findViewById(R.id.userReg);
		userReg.setOnClickListener(this);
		agree_text.setOnClickListener(this);
		cb1.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.userReg) {
			emailAddress = email.getText().toString();
			pwd = password.getText().toString();
			if (StrUtil.isEmpty(emailAddress)) {
				LogUtil.show(context, "请输入电子邮箱地址", 2000);
				return;
			} else if (StrUtil.isEmpty(pwd)) {
				LogUtil.show(context, "请输入密码", 2000);
				return;
			} else if (pwd.length() < 6) {
				LogUtil.show(context, "密码至少要6位", 2000);
				return;
			} else if (!emailAddress.contains("@")) {
				LogUtil.show(context, "邮箱格式不对", 2000);
				return;
			}
			pd = new LXProgressDialog(context, "正在发送");
			pd.show();
			String nameStr = name.getText().toString();
			if (StrUtil.isEmpty(nameStr)) {
				nameStr = "";
			}
			api.emailRegister(emailAddress, nameStr, pwd, new RequestListener() {
				
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
		} else if (id == agree_text.getId()) {
			AlertDialog.Builder alter = new AlertDialog.Builder(context);
			alter.setTitle("联系网服务条款");
			alter.setMessage(getFromAssets("terms_of_service.txt"));
			alter.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
						}
					});
			alter.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
						}
					});
			// alter.setView(view);
			alter.show();

		}

	}
	private void initData() {
		config = IndustryApplication.getInstance();
		appID = config.getAppID();
		api = new HttpApi(appID);
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
				if (json.optInt("memberUser") == -1) {
					LogUtil.show(context, "邮箱或密码不能为空", 2000);
				} else if (json.optInt("memberUser") == -2) {
					LogUtil.show(context, "密码不能小于6位", 2000);
				} else if (json.optInt("memberUser") == -3) {
					LogUtil.show(context, "邮箱格式不正确", 2000);
				} else if (json.optInt("memberUser") == -5) {
					LogUtil.show(context, "该账号已注册", 2000);
				} else if (json.optString("memberUser") !=null && !json.optString("memberUser").equals("")) {
					if (clickListener != null)
						clickListener.onclick(userReg, emailAddress);
				} else {
					LogUtil.show(context, "未知错误", 3000);
				}
			}
		} else {
			LogUtil.show(context, "注册信息有误", 3000);
		}
	}

	public ClickListener5 getClickListener() {
		return clickListener;
	}

	public void setClickListener(ClickListener5 clickListener) {
		this.clickListener = clickListener;
	}

	public interface ClickListener5 {
		public void onclick(View view, String mobile);
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		userReg.setEnabled(arg1);
	}

	public String getFromAssets(String fileName) {
		String result = "";
		try {
			InputStream in = getResources().getAssets().open(fileName);
			// 获取文件的字节数
			int lenght = in.available();
			// 创建byte数组
			byte[] buffer = new byte[lenght];
			// 将文件中的数据读到byte数组中
			in.read(buffer);
			result = EncodingUtils.getString(buffer, ENCODING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
