package com.database;

import java.util.ArrayList;

import com.bean.Supplier;

public class SupplierOperation {
	private DB_Operation dbo = null;

	private void set_dbo() {
		DB_Operation dbo = new DB_Operation();
		dbo.setDriver("org.apache.derby.jdbc.EmbeddedDriver");
		dbo.setUrl("jdbc:derby:mydb");
		dbo.setUsername("unit");
		dbo.setPassword("java");
		this.dbo = dbo;
	}

	// ������й�Ӧ��
	public ArrayList<Supplier> getSupplier() {
		set_dbo();
		return dbo.selectsupplier();
	}

	// ���Ĺ�Ӧ����Ϣ
	public boolean supplierinfochange(Supplier supplier) {
		set_dbo();
		return dbo.supplierinfochange(supplier);
	}

	// ɾ����Ӧ����Ϣ
	public boolean supplierdelete(Supplier supplier) {
		set_dbo();
		return dbo.supplierdelete(supplier);
	}

	// ���빩Ӧ����Ϣ
	public boolean supplierinsert(Supplier supplier) {
		set_dbo();
		return dbo.supplierinsert(supplier);
	}

	// ��ҳ��ѯ
	public ArrayList<Supplier> supplierlookfor(int presentpage, int ppage,
			String suppliername, String supplierdescription) {
		set_dbo();
		return dbo.supplierlookfor(presentpage, ppage, suppliername,
				supplierdescription);
	}

	// ��ѯ��Ӧ������
	public int suppliernum(String suppliername, String supplierdescription) {
		set_dbo();
		return dbo.suppliernum(suppliername, supplierdescription);
	}
}
