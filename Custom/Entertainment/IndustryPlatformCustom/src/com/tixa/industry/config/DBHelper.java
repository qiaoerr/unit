package com.tixa.industry.config;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper {
	
	private SQliteHelper helper = null;
	public DBHelper(Context context){
		helper = new SQliteHelper(context);
	}
	
	/**
	 * 完成对数据的增删改操作
	 * @param sql 通常是以insert / update /delete 打头的SQL
	 */
	public void execSQL(String sql) {
		SQLiteDatabase db = null;
		try {
		    db = this.helper.getWritableDatabase();
			db.execSQL(sql);
		} catch (SQLException ex) {
			throw ex;
		} finally {
			db.close();
		}
	}
	
	/**
	 * 添加一条记录
	 * @param table 表名
	 * @param values 表字段及值的HashMap
	 */
	public void insert(String table,ContentValues values){
		SQLiteDatabase db = null;
		try {
		    db = this.helper.getWritableDatabase();
			db.insert(table, null, values);
		} catch (SQLException ex) {
			throw ex;
		} finally {
			db.close();
		}
	}
	
	/**
	 * 根据条件修改记录
	 * @param table 表名
	 * @param values 表字段及值的HashMap
	 * @param whereClause Where语句部分(eg: id=?)
	 * @param whereArgs where部分应填充的值(eg:new String[]{1});
	 */
	public void update(String table,ContentValues values,String whereClause,String[] whereArgs ){
		SQLiteDatabase db = null;
		try {
		    db = this.helper.getWritableDatabase();
			db.update(table, values, whereClause, whereArgs);	
		} catch (SQLException ex) {
			throw ex;
		} finally {
			db.close();
		}
	}
	/**
	 * 根据条件删除记录
	 * @param table 表名
	 * @param whereClause Where语句部分(eg: id=?)
	 * @param whereArgs where部分应填充的值(eg:new String[]{1});
	 */
	public void delete(String table,String whereClause,String[] whereArgs ){
		SQLiteDatabase db = null;
		try {
		    db = this.helper.getWritableDatabase();
			db.delete(table, whereClause, whereArgs);
		} catch (SQLException ex) {
			throw ex;
		} finally {
			db.close();
		}
	}
	/**
	 * 完成对数据的查询操作
	 * @param sql 以Select打头的SQL
	 * @return 供查询操作的游标
	 */
	public Cursor rawQuery(String sql){
		SQLiteDatabase db = null;
		try {
		    db = this.helper.getReadableDatabase();
			return db.rawQuery(sql, null);
		} catch (SQLException ex) {
			throw ex;
		} finally {
			//db.close();
		}
	}
	/**
	 * 对数据进行查询（返回首行首列的单一值)
	 * @param sql 通常是用于聚合函数(min,max,avg,sum,count);
	 * @return 返回首行首列的单一值
	 */
	public int rawQuerySingle(String sql){
		Cursor cursor = this.rawQuery(sql);
		int result = -1;
		if (cursor.moveToNext()){
			result = cursor.getInt(0);
		}
		return result;
	}
	
	
	/**
	 * 插入数据的重载版本(加入事务)
	 * @param sqls 多条SQL语句构成的List<String>集合
	 * @return true:代表操作成功，false:代表操作失败
	 */
	public boolean execSQL(List<String> sqls){
		SQLiteDatabase db = null;
		try {
		    db = this.helper.getWritableDatabase();
		    db.beginTransaction();
			for(String sql:sqls){
				db.execSQL(sql);
			}
			db.setTransactionSuccessful();
			return true;
		} catch (SQLException ex) {
			return false;
		} finally {
			db.endTransaction();
			db.close();
		}
	}
	
	
	
	/**
	 * 关闭DataBase
	 */
	public void close(){
		this.helper.close();
	}

}
