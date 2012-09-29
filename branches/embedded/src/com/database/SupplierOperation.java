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

	// 获得所有供应商
	public ArrayList<Supplier> getSupplier() {
		set_dbo();
		return dbo.selectsupplier();
	}

	// 更改供应商信息
	public boolean supplierinfochange(Supplier supplier) {
		set_dbo();
		return dbo.supplierinfochange(supplier);
	}

	// 删除供应商信息
	public boolean supplierdelete(Supplier supplier) {
		set_dbo();
		return dbo.supplierdelete(supplier);
	}

	// 插入供应商信息
	public boolean supplierinsert(Supplier supplier) {
		set_dbo();
		return dbo.supplierinsert(supplier);
	}

	// 分页查询
	public ArrayList<Supplier> supplierlookfor(int presentpage, int ppage,
			String suppliername, String supplierdescription) {
		set_dbo();
		return dbo.supplierlookfor(presentpage, ppage, suppliername,
				supplierdescription);
	}

	// 查询供应商数量
	public int suppliernum(String suppliername, String supplierdescription) {
		set_dbo();
		return dbo.suppliernum(suppliername, supplierdescription);
	}
}
