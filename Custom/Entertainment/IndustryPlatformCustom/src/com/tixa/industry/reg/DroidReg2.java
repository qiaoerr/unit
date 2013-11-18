package com.tixa.industry.reg;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
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
import android.widget.Toast;

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

public class DroidReg2 extends Fragment implements OnClickListener {
	private Activity context;
	private EditText userRegCode;
	private final String TAG = "reg";
	private Button regComplete;
	private String mobile;
	private String password;
	private Button getRegCode;
	private final int MAX_CHECK = 120;
	private LXProgressDialog pd;
	private IndustryApplication config;
	private HttpApi api;
	private String appID;
	private IndexData data;
	private int checkCount = MAX_CHECK;
	private String onOkActivity;
//	private final String CHECK_URL = Constant.webDomain
//			+ "account/checkMobileCode.jsp";
//	private final String REG_URL = Constant.webDomain
//			+ "account/checkMobileReg.jsp";
	private long accountId;
	private View view;
	private Runnable r = new Runnable() {
		@Override
		public void run() {
			handler.sendEmptyMessage(0);
		}
	};
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
			case Data.NONETWORK:
				T.shortT(context, getResources().getString(R.string.nonetwork));
				break;
			case -100:
				Toast.makeText(context, "获取失败，请稍候再试", 3000).show();
				getRegCode.setText("重新获取");
				handler.removeCallbacks(r);
				getRegCode.setEnabled(true);
				break;
			case 0:
				checkCount--;
				if (checkCount >= 0) {
					getRegCode.setText("重新获取(" + checkCount + ")");
					handler.postDelayed(r, 1000);
				} else {
					getRegCode.setText("重新获取");
					handler.removeCallbacks(r);
					getRegCode.setEnabled(true);
					try{
						getRegCode.setTextColor(getResources().getColor(
							R.color.black));
					}catch(Exception e) {
						L.e(e.toString());
					}
				}
				break;
			default:
				break;
			}	
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_reg_2, container, false);
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
	private void initData() {
		config = IndustryApplication.getInstance();
		appID = config.getAppID();
		api = new HttpApi(appID);
	}
	private void initView() {
		userRegCode = (EditText) view.findViewById(R.id.userRegCode);
		regComplete = (Button) view.findViewById(R.id.regComplete);
		getRegCode = (Button) view.findViewById(R.id.getRegCode);
		regComplete.setOnClickListener(this);
		getRegCode.setOnClickListener(this);
		getRegCode.setEnabled(false);
		getRegCode.setTextColor(getResources().getColor(
				R.color.shout_info_around));
		handler.postDelayed(r, 1000);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.regComplete) {
			final String regCode = userRegCode.getText().toString();
			if (regCode != null && !regCode.trim().equals("")) {
				pd = new LXProgressDialog(context, "正在发送");
				pd.show();
				api.getCheckcode(mobile, regCode, new RequestListener() {
					
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
				LogUtil.show(context, "验证码不能为空", 3000);
			}
		} else if (id == R.id.getRegCode) {
			getRegCode.setTextColor(getResources().getColor(
					R.color.shout_info_around));
			getRegCode.setEnabled(false);
			getRegCode.setText("正在获取...");
			checkCount = MAX_CHECK;
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
					JSONObject json = null;
					try {
						json = new JSONObject(response);
						if (json.optInt("s") <= 0
								|| StrUtil.isHttpException(response)) {
							handler.sendEmptyMessage(-100);
						} else if (json.optInt("s") > 0) {
							handler.postDelayed(r, 1000);
						}
					} catch (JSONException e) {
						handler.sendEmptyMessage(-100);
						e.printStackTrace();
					}
				}
			});
		}
	}

	private void progressResult(String result) throws JSONException {
		if (StrUtil.isNotEmpty(result)) {
			if (StrUtil.isHttpException(result)) {
				LogUtil.show(context, "网络连接异常", 3000);
			} else if (result.equals("[]")) {
				LogUtil.show(context, "注册失败", 3000);
			} else {
				// ����json��Ϣ
				JSONObject json = new JSONObject(result);
				if (json.optInt("s") == -2) {
					LogUtil.show(context, "验证码已过期", 2000);
				} else if (json.optInt("s") == -3) {
					LogUtil.show(context, "验证码不正确", 2000);
				} else if (json.optInt("s") == 1) {
					handler.removeCallbacks(r);
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

	private ClickListener2 clickListener;

	public ClickListener2 getClickListener() {
		return clickListener;
	}

	public void setClickListener(ClickListener2 clickListener) {
		this.clickListener = clickListener;
	}

	public interface ClickListener2 {
		public void onclick(View view, String mobile);
	}

}
