package com.database;

import java.util.ArrayList;

import com.bean.Bill;

public class BillOperation {
	private DB_Operation dbo = null;

	private void set_dbo() {
		DB_Operation dbo = new DB_Operation();
		dbo.setDriver("org.apache.derby.jdbc.EmbeddedDriver");
		dbo.setUrl("jdbc:derby:D:\\tools\\db-derby-10.9.1.0-bin\\bin\\mydb");
		dbo.setUsername("unit");
		dbo.setPassword("java");
		this.dbo = dbo;
	}

	// 获得所有账单
	public ArrayList<Bill> getbill() {
		set_dbo();
		return dbo.selectbill();
	}

	// 插入数据
	public boolean billinsert(Bill bill) {
		set_dbo();
		return dbo.billinsert(bill);
	}

	// 更新数据
	public boolean billupdate(Bill bill) {
		set_dbo();
		return dbo.billchange(bill);
	}

	// 删除数据
	public boolean billdelete(Bill bill) {
		set_dbo();
		return dbo.billdelete(bill);
	}

	public boolean billdelete1(long supplierid) {
		set_dbo();
		return dbo.billdelete1(supplierid);
	}

	// 数据查询
	public ArrayList<Bill> billlookfor(int presentpage, int ppage,
			String goodsname, String ispay) {
		set_dbo();
		return dbo.billlookfor(presentpage, ppage, goodsname, ispay);
	}

	// 表单数量
	public int billnum(String goodsname, String ispay) {
		set_dbo();
		return dbo.billnum(goodsname, ispay);
	}
}
