package com.tixa.industry.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.activity.MainActivity.MainReceiver;
import com.tixa.industry.adapter.FunItemAdapter;
import com.tixa.industry.config.Constants;
import com.tixa.industry.model.FunItem;
import com.tixa.industry.model.FunItem.ClickLisener;
import com.tixa.industry.model.PageConfig;
import com.tixa.industry.parser.PageConfigParser;
import com.tixa.industry.util.FileUtil;
import com.tixa.industry.util.L;
import com.tixa.industry.util.StrUtil;
import com.tixa.industry.util.TopBarUtil;
import com.tixa.industry.util.VersionUpdateManager;
import com.tixa.industry.widget.LXProgressDialog;
import com.tixa.industry.widget.TopBar;

public class MoreActivity extends Fragment implements OnItemClickListener {
	private View view;
	private TextView text;
	private Button button;
	private FragmentManager fragmentManager;
	private FragmentActivity context;
	private String titleName;
	private String modularName;
	private String typeID;
	private boolean isNav = false;
	private TopBar topbar;
	private ListView menuList;
	private ArrayList<FunItem> myData;
	private FunItemAdapter adapter;
	private LXProgressDialog pd;
	private String parentActivity;
	private IndustryApplication application;
	private PageConfig config;
	private TopBarUtil util;
	private int type = 0; // 1关于软件 2.使用帮助 3关于我们
	private String str = "";
	private LoginReceiver receiver;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		fragmentManager = context.getSupportFragmentManager();
		view = inflater.inflate(R.layout.advancedfun, container, false);
		registerIntentReceivers();
		getPageConfig();
		modularName = getArguments().getString("modularName");
		typeID = getArguments().getString("typeID");

		isNav = getArguments().getBoolean("isNav");
		topbar = (TopBar) view.findViewById(R.id.topbar);

		if (StrUtil.isEmpty(modularName)) {
			titleName = "更多";
		} else {
			titleName = modularName;
		}

		int naviStyle = config.getNavi().getBackItem();
		int navi = config.getNavi().getType();

		util = new TopBarUtil(isNav, naviStyle, topbar, modularName,
				getFragmentManager(), context, application.getTemplateId(),
				false, navi);
		util.showConfig();

		menuList = (ListView) view.findViewById(R.id.advancedFunList);
		getData();
		menuList.setOnItemClickListener(this);

		return view;

	}

	private void getPageConfig() {
		application = IndustryApplication.getInstance();
		PageConfigParser p = new PageConfigParser(context,
				"layout/MoreLayout.xml");
		config = p.parser();
	}

	@Override
	public void onDestroy() {
		unregisterIntentReceivers();
		super.onDestroy();
	}

	private void getData() {
		myData = new ArrayList<FunItem>();

		FunItem fi = null;
		fi = new FunItem(true);
		myData.add(fi);

		fi = new FunItem("个人中心", new ClickLisener() {
			@Override
			public void onclick() {
				
				if(application.getMemberID()<=0) {
					Intent intent = new Intent(context, LoginActivity.class);
					startActivity(intent);	
				}else{
					Fragment fragment = new UserActivity();
					Bundle args = new Bundle();
					args.putString("modularName", "个人中心");
					args.putString("typeID", 9+"");
					args.putBoolean("isNav", false);
					fragment.setArguments(args);
					FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
					fragmentTransaction.addToBackStack(null);
					fragmentTransaction.replace(R.id.fragment, fragment);
					fragmentTransaction.commit();		
				}
			}
		});
		// fi.setIcon(R.drawable.setting_message);
		myData.add(fi);

		fi = new FunItem("浏览记录", new ClickLisener() {
			@Override
			public void onclick() {
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				Fragment fragment = new ScanRecoderActivity();
				transaction.replace(R.id.fragment, fragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
		// fi.setIcon(R.drawable.setting_mode);
		myData.add(fi);
		fi = new FunItem("关于软件", new ClickLisener() {

			@Override
			public void onclick() {
				type = 1;
				str = "关于软件";
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				Fragment fragment =  new AboutActivity();
				Bundle args = new Bundle();
				args.putString("str", str);
				args.putInt("type", type);
				fragment.setArguments(args);
				transaction.replace(R.id.fragment, fragment);
				transaction.addToBackStack(null);		
				transaction.commit();			
			}
		});
		// fi.setIcon(R.drawable.setting_account);
		myData.add(fi);

		/*fi = new FunItem("推送通知", new ClickLisener() {

			@Override
			public void onclick() {

			}
		});
		// fi.setIcon(R.drawable.setting_clean);
		myData.add(fi);*/
		
		fi = new FunItem("清除缓存", new ClickLisener() {

		@Override
		public void onclick() {
			new AlertDialog.Builder(context)
			.setTitle("清空缓存")
			.setMessage("确定要清空缓存吗？")
			.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							delAllCache();
							Toast.makeText(context, "清空成功", 1).show();
						}
					})
			.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							dialog.cancel();
						}
					}).show();
		}
	});
	// fi.setIcon(R.drawable.setting_clean);
	myData.add(fi);
		
		
		
		fi = new FunItem(true);
		myData.add(fi);

		fi = new FunItem("使用帮助", new ClickLisener() {

			@Override
			public void onclick() {
				type = 2;
				str = "使用帮助";
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				Fragment fragment =  new AboutActivity();
				Bundle args = new Bundle();
				args.putString("str", str);
				args.putInt("type", type);
				fragment.setArguments(args);
				transaction.replace(R.id.fragment, fragment);
				transaction.addToBackStack(null);		
				transaction.commit();			

			}
		});
		// fi.setIcon(R.drawable.setting_help);
		myData.add(fi);
		fi = new FunItem("客服中心", new ClickLisener() {

			@Override
			public void onclick() {
				Intent intent = new Intent(context, LeaveMessageAct.class);
				startActivity(intent);
			}
		});
		// fi.setIcon(R.drawable.setting_feedback);
		myData.add(fi);
		fi = new FunItem("关于我们", new ClickLisener() {
			@Override
			public void onclick() {
				type = 3;
				str = "关于我们";
				
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				Fragment fragment =  new AboutActivity();
				Bundle args = new Bundle();
				args.putString("str", str);
				args.putInt("type", type);
				fragment.setArguments(args);
				transaction.replace(R.id.fragment, fragment);
				transaction.addToBackStack(null);		
				transaction.commit();			

				/*Intent intent = new Intent(context, AboutAct.class);
				intent.putExtra("type", type);
				intent.putExtra("str", str);
				startActivity(intent);*/
			}
		});
		// fi.setIcon(R.drawable.setting_about);
		myData.add(fi);
		
		fi = new FunItem("版本更新", new ClickLisener() {
			@Override
			public void onclick() {
				// 检查软件更新
				VersionUpdateManager manager = new VersionUpdateManager(context);
				manager.checkUpdate();
			}
		});
		// fi.setIcon(R.drawable.setting_about);
		myData.add(fi);
		
		
		fi = new FunItem(true);
		myData.add(fi);

		adapter = new FunItemAdapter(context, myData);
		menuList.setAdapter(adapter);

		// view = inflater.inflate(R.layout.test_bottom, null);
		// view.setBackgroundColor(getResources().getColor(R.color.red));
		// text = (TextView) view.findViewById(R.id.textView1);
		// text.setText("this is MoreActivity");
		// button = (Button) view.findViewById(R.id.button1);
		//
		// button.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// FragmentTransaction fragmentTransaction =
		// getFragmentManager().beginTransaction();
		// fragmentTransaction.addToBackStack(null);
		// fragmentTransaction.replace(R.id.fragment, new UserActivity());
		// fragmentTransaction.commit();
		//
		// }
		// });

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		FunItem fi = myData.get(position);
		fi.getListener().onclick();
	}
	
	class LoginReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(action.equals(Constants.MY_LOGIN_SUCCESS_ACTION)) {
				L.e("------------------------onReceive----------------");
				Fragment fragment = new UserActivity();
				Bundle args = new Bundle();
				args.putString("modularName", "个人中心");
				args.putString("typeID", 9+"");
				args.putBoolean("isNav", false);
				fragment.setArguments(args);
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.replace(R.id.fragment, fragment);
				fragmentTransaction.commitAllowingStateLoss();
			}
		}		
	}
	
	private void registerIntentReceivers() {
		receiver =  new LoginReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.MY_LOGIN_SUCCESS_ACTION);
		context.registerReceiver(receiver, filter);
	}
	
	private void unregisterIntentReceivers() {
		try{
			context.unregisterReceiver(receiver);
		}catch(Exception e) {
			
		}
	}
	
	// 清空缓存
	private void delAllCache() {
		String image_cache = Constants.IMG_DIR;
		String data_cache = Constants.CACHE_DIR + getResources().getString(R.string.appid);
		FileUtil.delFolder(image_cache);
		FileUtil.delFolder(data_cache);
	}
	
}
