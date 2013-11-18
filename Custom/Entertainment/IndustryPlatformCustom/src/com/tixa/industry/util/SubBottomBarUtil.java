package com.tixa.industry.util;

import android.app.Activity;
import android.content.Context;

import com.tixa.industry.R;
import com.tixa.industry.widget.BadgeView;
import com.tixa.industry.widget.BottomTab;
import com.tixa.industry.widget.BottomTab.ClickListener;
import com.tixa.industry.widget.BottomTabBar;


/**
 *  二级页面底部栏封装 供求购 资讯列表等使用
  * @ClassName: SubBottomBarUtil
  * @Description: TODO
  * @author shengy
  * @date 2013-7-23 下午6:53:13
  *
 */
public class SubBottomBarUtil {
	private BottomTabBar tabBar;
	private Activity context;
	private BadgeView badge; 
	
	public SubBottomBarUtil(Activity context , BottomTabBar tabBar) {
		this.tabBar = tabBar;
		this.context = context;
		tabBar.setBackgroundResource(R.drawable.sub_footer);
		
		CreateBottomBar();
	}

	private void CreateBottomBar() {
		
		BottomTab comment = new BottomTab(context, tabBar , null , R.drawable.bottom_comment,
				false , new ClickListener() {
			
			@Override
			public void onClick() {
				
				
			}
		});
		
		
		badge = new BadgeView(context, comment);
		badge.setText("1");
		badge.show();
		
		BottomTab phone = new BottomTab(context, tabBar , null , R.drawable.bottom_phone, 
				false , new ClickListener() {
			
			@Override
			public void onClick() {
				
				
			}
		});
		
		BottomTab collect = new BottomTab(context, tabBar , null , R.drawable.bottom_collect, 
				false , new ClickListener() {
			
			@Override
			public void onClick() {
				
				
			}
		});	

	}

}
