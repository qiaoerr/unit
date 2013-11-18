package com.tixa.industry.reg;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.EncodingUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class DroidReg extends Fragment implements OnClickListener,
		OnCheckedChangeListener {
	private TextView userMobie, agreeWith;

	public TextView getUserMobie() {
		return userMobie;
	}

	private CheckBox cb1;
	private EditText userMobileNum;
	private String name, code;
	private final static int COUNTRY_CODE = 101;
	private String suffix = "";

	public EditText getUserMobileNum() {
		return userMobileNum;
	}

	public void setUserMobileNum(EditText userMobileNum) {
		this.userMobileNum = userMobileNum;
	}
	private IndustryApplication config;
	private HttpApi api;
	private String appID;
	private IndexData data;
	private Button userReg;
	private LXProgressDialog pd;
	private Activity context;
	private View view;
	private String mobile;
	public static final String ENCODING = "GBK";
	public static final String ENCODING2 = ":UTF8";
//	private final String REG_URL = Constant.webDomain
//			+ "account/checkMobileReg.jsp";
	private ClickListener clickListener;
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
		view = inflater.inflate(R.layout.layout_reg, container, false);
		context = getActivity();
		initData();
		initView();
		return view;
	}

	private void initView() {
		cb1 = (CheckBox) view.findViewById(R.id.agree_checkbox);
		agreeWith = (TextView) view.findViewById(R.id.agree_text);
		userMobie = (TextView) view.findViewById(R.id.userMobile);
		userMobileNum = (EditText) view.findViewById(R.id.userPwd);
		userReg = (Button) view.findViewById(R.id.userReg);
		userReg.setOnClickListener(this);
		agreeWith.setOnClickListener(this);
		userMobie.setOnClickListener(this);
		cb1.setOnCheckedChangeListener(this);
		code = "+86";
	}
	private void initData() {
		config = IndustryApplication.getInstance();
		appID = config.getAppID();
		api = new HttpApi(appID);
	}
	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.userReg) {
			mobile = userMobileNum.getText().toString();
			if (StrUtil.isEmpty(mobile)) {
				LogUtil.show(context, "请输入手机号", 2000);
				userMobie.requestFocus();
				return;
			} else if (mobile.length() < 6) {
				LogUtil.show(context, "格式不对", 2000);
				userMobie.requestFocus();
				return;
			}
			pd = new LXProgressDialog(context, "正在发送");
			pd.show();
			api.checkMobile(mobile, new RequestListener() {
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
		} else if (id == agreeWith.getId()) {
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
			alter.show();
		} else if (id == userMobie.getId()) {
			Intent intent = new Intent(context, DroidReg_CountryCode.class);
			startActivityForResult(intent, COUNTRY_CODE);
		}
	}

	private void progressResult(String result) throws JSONException {
		if (StrUtil.isNotEmpty(result)) {
			if (result.equals("-1") || result.equals("-2")
					|| result.equals("-6") || result.equals("-7")) {
				LogUtil.show(context, "网络连接异常", 3000);
			} else if (result.equals("[]")) {
				LogUtil.show(context, "注册失败", 3000);
			} else {
				JSONObject json = new JSONObject(result);
				if (json.optInt("s") == -1) {
					LogUtil.show(context, "手机号或密码不能为空", 2000);
				} else if (json.optInt("s") == -2) {
					LogUtil.show(context, "密码不能小于6位", 2000);
				} else if (json.optInt("s") == -3) {
					LogUtil.show(context, "手机号格式不正确", 2000);
				} else if (json.optInt("s") == -4) {
					LogUtil.show(context, "该账号已注册", 2000);
				} else if (json.optInt("s") == 1) {
					if (clickListener != null)
						clickListener.onclick(userReg, mobile);
				} else {
					LogUtil.show(context, "未知错误", 3000);
				}
			}
		} else {
			LogUtil.show(context, "注册信息有误", 3000);
		}
	}

	public ClickListener getClickListener() {
		return clickListener;
	}

	public void setClickListener(ClickListener clickListener) {
		this.clickListener = clickListener;
	}

	public interface ClickListener {
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
			int lenght = in.available();
			byte[] buffer = new byte[lenght];
			in.read(buffer);
			result = EncodingUtils.getString(buffer, ENCODING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
