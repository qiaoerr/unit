package com.tixa.industry.config;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQliteHelper extends SQLiteOpenHelper {
	
	private static final String NAME = Constants.DATABASE_NAME;
	private static final int VERSION = Constants.DATABASE_VERSION;
	
	public SQliteHelper(Context context) {
		super(context, NAME, null, VERSION);
	}
	
	/**
	 * 创建表
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		CreateShopCart(db);
		CreateViewCache(db);
	}
	
	
	private void CreateShopCart(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = SHOPCART.SQL.CREATE;
		db.execSQL(sql);
	}
	
	private void CreateViewCache(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = VIEWCACHE.SQL.CREATE;
		db.execSQL(sql);
	}
	
	/**
	 * 更新表
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		DropDatabase(db);
		onCreate(db);
	}
	
	public void DropDatabase(SQLiteDatabase db){
		String sql = SHOPCART.SQL.DROP;
		db.execSQL(sql);
		sql = VIEWCACHE.SQL.DROP;
		db.execSQL(sql);
	}
	
	//浏览记录
	public interface VIEWCACHE {
		public static final String TABLENAME = "viewcache";
		public interface SQL{
			//创建表
			
			public static final String CREATE ="Create  TABLE if not exists viewcache("+
				   	" [id] INTEGER PRIMARY KEY AUTOINCREMENT "+
				   	",[goodsID]  integer"+
				   	",[price] double"+
				   	",[shopID] integer "+
					",[goodsName] varchar(64) "+
					",[shopName] varchar(64) "+
					",[customerID] integer "+
					",[mallID] integer "+
					",[goodsImg] varchar(256) "+
				   	");";
			//删除表
			public static final String DROP = "DROP TABLE IF EXISTS viewcache";
			
			//得到某人的所有浏览记录
			public static final String GET_VIEW= "select * from viewcache where customerID =";
			
			//清空所有浏览记录
			public static final String DELETE = "delete from viewcache where customerID =";
		}
	}
	
	//shopcart 表
	public interface SHOPCART{
		public static final String TABLENAME = "shopcart";					
		public interface SQL{
			
			//创建表
			public static final String CREATE ="Create  TABLE if not exists shopcart("+
				   	" [id] INTEGER PRIMARY KEY AUTOINCREMENT "+
				   	",[goodsID]  integer"+
				   	",[goodsNumber] integer "+
				   	",[totalPrice] double"+
				   	",[price] double"+
				   	",[shopID] integer "+
					",[goodsName] varchar(64) "+
					",[shopName] varchar(64) "+
					",[customerID] integer "+
					",[mallID] integer "+
					",[goodsImg] varchar(256) "+
				   	");";
			//删除表
			public static final String DROP = "DROP TABLE IF EXISTS shopcart";
			//得到某人的所有购物车商品
			public static final String GET_SHOPCART = "select * from shopcart";
//			public static final String GET_SHOPCART = "select * from shopcart where customerID =";
			//清空整个购物车
			public static final String DELETE = "delete from shopcart where customerID =";
			//删除购物车中某个商品
			public static final String DELETECONMES = "delete from shopcart where goodsID =";
			
			public static final String SELECTFORMCART = "select * from shopcart where goodsid=";
			
			public static final String GET_SHOPCARTSUM="select count(*) from shopcart"; 
			
		}
	}


}
