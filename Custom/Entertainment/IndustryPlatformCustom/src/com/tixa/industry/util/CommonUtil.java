package com.tixa.industry.util;

import java.io.File;

import com.tixa.industry.IndustryApplication;
import com.tixa.industry.R;
import com.tixa.industry.activity.NewsActivity;
import com.tixa.industry.activity.NewsGridActivity;
import com.tixa.industry.activity.SlidingNewsActivity;
import com.tixa.industry.config.Constants;
import com.tixa.industry.model.IndexData;
import com.tixa.industry.model.Modular;
import com.tixa.industry.model.ModularConfig;
import com.tixa.industry.model.ModularType;
import com.tixa.industry.model.SlideMenuConfig;
import com.tixa.industry.parser.ModularParser;
import com.tixa.industry.parser.RootParser;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.SparseArray;

public class CommonUtil {

	/**
	 * 检测sdcard是否可用
	 * 
	 * @return true为可用，否则为不可用
	 */
	public static boolean sdCardIsAvailable() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED))
			return false;
		return true;
	}
	
	/**
	 * 根据类型
	 * @param showType
	 * @return
	 */
	public static Fragment getNewsFragmentByShowType(long showType) {
		Fragment fragment = null;
		if(showType == 1) {
			fragment = new SlidingNewsActivity();
		}else if(showType == 4) {
			fragment = new NewsGridActivity();
		}else{
			fragment = new NewsActivity();
		}
		return fragment;
	}
	
	
	/**
	 * 判断首页栏目应该跳转到哪个页面
	 * @param className
	 * @return
	 */
	public static String formatClassName(IndustryApplication application,ModularConfig config,long showType) {		
		String className = config.getTypeName();
		if (className != null && className.equals("Sort")) {
			if (application.getAppID().equals("15")) { // 建筑特制
				className = "SortCata";
			}
		}else if(className != null && className.equals("News")) { //不同的新闻样式
			if(showType == 4) {
				className = "NewsGrid"; //网格
			}else if(showType == 1) {
				className = "SlidingNews";//滑动
			}
		}		
		if (className != null && !className.equals("")) {
			className = "com.tixa.industry.activity." + className
					+ "Activity";
		}		
		return className;
	}
	
	
	/**
	 * 组装导航栏栏目的Bundle参数
	 * @return
	 * 
	 * 
	 */
	public static Bundle packageNaviModileBundle(Modular modular,String fragmentName,int cur,Context context) {
		String moduleName = modular.getModularName();
		/*boolean isNewsCata = false;
		if(modular.getType() ==  ModularType.NewsCata) {
			isNewsCata = true;
		}*/
		long typeID = modular.getId();
		//long type = modular.getType();
		String childType = modular.getChildType();
		long showType = modular.getShowType();
		Bundle	args = new Bundle();
		args.putString("root", fragmentName);
		if (cur == 0) {
			moduleName = context.getResources().getString(R.string.app_name);
		}
		args.putString("modularName",moduleName);
		
		
		if(childType.equals(0+"")) {
			args.putString("typeID", typeID+""); 
		}else{
			args.putString("typeID", childType); 
		}
		
		args.putString("childType", childType); 
		
/*		if(!isNewsCata) {
			long cType = 0;
			try{
				cType = Long.parseLong(childType);
			}catch(Exception e) {
				L.e("commonUtil 栏目属性转型错误");
			}			
			if (cType == 0) {
				args.putLong("typeID", type); 
			} else {
				args.putLong("typeID", cType);
			}
			args.putLong("childType", cType); // 橱窗用
			
		}else{			
			args.putString("cType", childType); //资讯分类
		}*/
		
		args.putBoolean("isNav", true);	
		args.putLong("showType", showType); //资讯样式
		
		return args;
	}
	
	
	
	
	/**
	 * 组装非导航栏栏目的Bundle参数
	 * @return
	 */
	public static Bundle packagModuleBundle(Modular modular) {
		String name = modular.getModularName();
		long typeID = modular.getId();
/*		boolean isNewsCata = false;
		if(modular.getType() ==  ModularType.NewsCata) {
			isNewsCata = true;
		}
		*/
		String childType = modular.getChildType();
		long showType = modular.getShowType();
		
		Bundle	args = new Bundle();
		args.putString("modularName", name);
		
		if(childType.equals(0+"")) {
			args.putString("typeID", typeID+""); 
		}else{
			args.putString("typeID", childType); 
		}
		
		args.putString("childType", childType);
		
		/*if(!isNewsCata) {
			long cType = 0;
			try{
				cType = Long.parseLong(childType);
			}catch(Exception e) {
				L.e("commonUtil 栏目属性转型错误");
			}			
			if (cType == 0) {
				args.putLong("typeID", typeID); 
			} else {
				args.putLong("typeID", cType);
			}
			args.putLong("childType", cType); // 橱窗用
			
		}else{			
			args.putString("cType", childType); //资讯分类
		}*/
		
		args.putBoolean("isNav", false);	
		args.putLong("showType", showType); //资讯样式
		
		return args;
	}

	
	/**
	 * 初始化首页application
	 * 
	 */
	public static void initApplication(Context context,IndustryApplication application) {
		L.e("appliaction","----------------initApplication------------------");
		if(application != null) {
			//如果appliaction中 appid为空则初始化
			if(application.getAppID() == null || application.getAppID().equals("")) {
				L.e("appliaction","appID 等基础全局数据为空，重新加载");
				application.initData();
			}
			//如果全局配置文件不存在，则重新加载
			if(application.getModularMap() == null || application.getModularMap().size() == 0) {
				L.e("appliaction","modularMap 栏目对应关系 为空，重新加载");
				getRootConfig(context, application);
			}
			
			IndexData data = application.getMainData(); //HOME页数据			
			if(data == null || data.getNavList() == null || data.getModularList() == null) {
				L.e("appliaction","首页数据、导航栏、栏目列表 为空 ，重新加载");
				boolean b = getMainData(context, application);
				if(b) {
					L.e("appliaction","首页数据IndexData重新加载成功");					
				}else{
					L.e("appliaction","首页数据IndexData重新加载失败");
				}
			}			
		}else{
			L.e("application is null");		
		}	
	}
	
	// 加载全局配置文件
	private static void getRootConfig(Context context , IndustryApplication application) {
		RootParser parser = new RootParser(context);
		SlideMenuConfig slide = parser.parse();
		if (slide != null) {
			application.setIsSlide(slide.getIsSlide());
			application.setConfig(slide);
		}
		ModularParser parse = new ModularParser(context);
		SparseArray<ModularConfig> modularMap = parse.parse();
		if (modularMap != null) {
			application.setModularMap(modularMap);
		}
	}
	
	
	// 从SD卡取得首页数据
	private  static boolean getMainData(Context context,IndustryApplication application) {
		try {
			String appID = context.getResources().getText(R.string.appid).toString()+"";
			String dic = Constants.CACHE_DIR + appID + "/maindata.tx";
			IndexData info = (IndexData) FileUtil.getFile(dic);
			if (info != null) {
				application.setMainData(info);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * Checks if there is enough Space on SDCard
	 * 
	 * @param updateSize
	 *            Size to Check
	 * @return True if the Update will fit on SDCard, false if not enough space on SDCard Will also return false, if the SDCard is
	 *         not mounted as read/write
	 */
	public static boolean enoughSpaceOnSdCard(long updateSize) {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED))
			return false;
		return (updateSize < getRealSizeOnSdcard());
	}

	/**
	 * get the space is left over on sdcard
	 */
	public static long getRealSizeOnSdcard() {
		File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	/**
	 * Checks if there is enough Space on phone self
	 * 
	 */
	public static boolean enoughSpaceOnPhone(long updateSize) {
		return getRealSizeOnPhone() > updateSize;
	}

	/**
	 * get the space is left over on phone self
	 */
	public static long getRealSizeOnPhone() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		long realSize = blockSize * availableBlocks;
		return realSize;
	}
	
	/**
	 * 根据手机分辨率从dp转成px
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static  int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
	  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
	public static  int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f)-15;  
    }  
	
	/**
	 * 取得手机的高度像素
	 * @param context
	 * @return
	 */
	public static int getHeightPx(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		int screenHeight = dm.heightPixels;
		return screenHeight;
	}	
	
	/**
	 * 取得手机的高度像素
	 * @param context
	 * @return
	 */
	public static int getWidthPx(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		int screenHeight = dm.widthPixels;
		return screenHeight;
	}	
	
}
