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

	// ��������˵�
	public ArrayList<Bill> getbill() {
		set_dbo();
		return dbo.selectbill();
	}

	// ��������
	public boolean billinsert(Bill bill) {
		set_dbo();
		return dbo.billinsert(bill);
	}

	// ��������
	public boolean billupdate(Bill bill) {
		set_dbo();
		return dbo.billchange(bill);
	}

	// ɾ������
	public boolean billdelete(Bill bill) {
		set_dbo();
		return dbo.billdelete(bill);
	}

	public boolean billdelete1(long supplierid) {
		set_dbo();
		return dbo.billdelete1(supplierid);
	}

	// ���ݲ�ѯ
	public ArrayList<Bill> billlookfor(int presentpage, int ppage,
			String goodsname, String ispay) {
		set_dbo();
		return dbo.billlookfor(presentpage, ppage, goodsname, ispay);
	}

	// ������
	public int billnum(String goodsname, String ispay) {
		set_dbo();
		return dbo.billnum(goodsname, ispay);
	}
}
