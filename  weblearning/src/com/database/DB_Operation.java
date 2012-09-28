package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bean.Bill;
import com.bean.Supplier;
import com.bean.User;

public class DB_Operation {

	private String driver;
	private String url;
	private String username;
	private String password;

	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// 连接数据库
	private void getConnection() {
		try {
			Class.forName(driver);
			this.conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 关闭数据库
	private void shutdown() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 对数据库的操作方法
	public int usernum() {

		getConnection();
		String query = "select count(userid) from users";
		int num = 0;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				num = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		return num;
	}

	public int usernum1(String name) {

		getConnection();
		String query = "select count(userid) from users where username=?";
		int num = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, name);
			rs = ps.executeQuery();
			while (rs.next()) {
				num = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		return num;
	}

	public ArrayList<User> querybypage(int page, int ppage, String name) {
		ArrayList<User> users = new ArrayList<User>();
		getConnection();
		if (name == null || name.length() == 0) {
			// String query =
			// "select userid,username,sex,age,telphone,address,power,password from "
			// + "(select rownum as rn,users.* from users) where rn between "
			// + (ppage * (page - 1) + 1) + " and " + (ppage * page);
			String query = "select userid,username,sex,age,telphone,address,power,password from users  offset "
					+ ppage
					* (page - 1)
					+ " rows fetch next "
					+ ppage
					+ " rows only";
			try {
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				while (rs.next()) {
					User user = new User();
					user.setUserid(rs.getLong("userid"));
					user.setName(rs.getString("username"));
					user.setSex(rs.getString("sex"));
					user.setAge(rs.getInt("age"));
					user.setTelphone(rs.getString("telphone"));
					user.setAddress(rs.getString("address"));
					user.setPower(rs.getString("power"));
					user.setPassword(rs.getString("password"));
					users.add(user);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				shutdown();
			}
			return users;
		} else {
			// String query =
			// "select userid,username,sex,age,telphone,address,power,password from "
			// +
			// "(select rownum as rn,users.* from users where username=?) where rn between "
			// + (ppage * (page - 1) + 1) + " and " + (ppage * page);
			String query = "select userid,username,sex,age,telphone,address,power,password from users where username=? offset "
					+ ppage
					* (page - 1)
					+ " rows fetch next "
					+ ppage
					+ " rows only";
			try {
				ps = conn.prepareStatement(query);
				ps.setString(1, name);
				rs = ps.executeQuery();
				while (rs.next()) {
					User user = new User();
					user.setUserid(rs.getLong("userid"));
					user.setName(rs.getString("username"));
					user.setSex(rs.getString("sex"));
					user.setAge(rs.getInt("age"));
					user.setTelphone(rs.getString("telphone"));
					user.setAddress(rs.getString("address"));
					user.setPower(rs.getString("power"));
					user.setPassword(rs.getString("password"));
					users.add(user);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				shutdown();
			}
			return users;
		}

	}

	public ArrayList<User> selectUser() {
		ArrayList<User> users = new ArrayList<User>();
		getConnection();
		String query = "select userid,username,sex,age,telphone,address,power,password from users";
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserid(rs.getLong("userid"));
				user.setName(rs.getString("username"));
				user.setSex(rs.getString("sex"));
				user.setAge(rs.getInt("age"));
				user.setTelphone(rs.getString("telphone"));
				user.setAddress(rs.getString("address"));
				user.setPower(rs.getString("power"));
				user.setPassword(rs.getString("password"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		return users;
	}

	public boolean userupdate(User user) {
		getConnection();
		String query = "update users set username=?,sex=?,age=?,telphone=?,address=?,power=?where userid=?";
		int i = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, user.getName());
			ps.setString(2, user.getSex());
			ps.setInt(3, user.getAge());
			ps.setString(4, user.getTelphone());
			ps.setString(5, user.getAddress());
			ps.setString(6, user.getPower());
			ps.setLong(7, user.getUserid());
			i = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean userdelete(User user) {
		getConnection();
		int i = 0;
		String query = "delete users where userid=?";
		try {
			ps = conn.prepareStatement(query);
			ps.setLong(1, user.getUserid());
			i = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<User> lookforUser(String name) {
		ArrayList<User> users = new ArrayList<User>();
		getConnection();
		StringBuffer query = new StringBuffer(
				"select userid,username,sex,age,telphone,address,power,password from users where ");
		// System.out.println(name+"dddd");
		// System.out.println(name==null);
		if (name == null || name.equals("")) {// ////////////////////////////////

			query.append(" 1=1");
			System.out.println(query);
			try {
				ps = conn.prepareStatement(query.toString());
				rs = ps.executeQuery();
				while (rs.next()) {
					User user = new User();
					user.setUserid(rs.getLong("userid"));
					user.setName(rs.getString("username"));
					user.setSex(rs.getString("sex"));
					user.setAge(rs.getInt("age"));
					user.setTelphone(rs.getString("telphone"));
					user.setAddress(rs.getString("address"));
					user.setPower(rs.getString("power"));
					user.setPassword(rs.getString("password"));
					users.add(user);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				shutdown();
			}
			return users;
		} else {
			query.append(" username=?");// ////////////////////////
			System.out.println(query + name + "fffffffffffffffffffffff");
			try {
				ps = conn.prepareStatement(query.toString());
				ps.setString(1, name);
				rs = ps.executeQuery();
				while (rs.next()) {
					User user1 = new User();
					user1.setUserid(rs.getLong("userid"));
					user1.setName(rs.getString("username"));
					user1.setSex(rs.getString("sex"));
					user1.setAge(rs.getInt("age"));
					user1.setTelphone(rs.getString("telphone"));
					user1.setAddress(rs.getString("address"));
					user1.setPower(rs.getString("power"));
					user1.setPassword(rs.getString("password"));
					users.add(user1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				shutdown();
			}
			return users;
		}

	}

	public boolean changepwd(User user) {
		getConnection();
		int i = 0;
		String query = "update users set password=? where userid=?";
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, user.getPassword());
			ps.setLong(2, user.getUserid());
			i = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean userinsert(User user) {
		getConnection();
		String query = "insert into users values(next value for user_seq,?,?,?,?,?,?,?)";
		int i = 0;
		try {
			ps = conn.prepareStatement(query);

			ps.setString(1, user.getName());
			ps.setString(2, user.getSex());
			ps.setInt(3, user.getAge());
			ps.setString(4, user.getTelphone());
			ps.setString(5, user.getAddress());
			ps.setString(6, user.getPower());
			ps.setString(7, user.getPassword());
			i = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	public String getPower(String name) {
		getConnection();
		String power = "";
		String query = "select power from users where username=?";
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, name);
			rs = ps.executeQuery();
			while (rs.next()) {
				power = rs.getString("power");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		return power;
	}

	public ArrayList<Supplier> selectsupplier() {
		ArrayList<Supplier> suppliers = new ArrayList<Supplier>();
		getConnection();
		String query = "select supplierid,suppliername,supplierdescription,linkedman,telphone,address from supplier";
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				Supplier supplier = new Supplier();
				supplier.setSupplierid(rs.getLong(1));
				supplier.setSuppliername(rs.getString(2));
				supplier.setSupplierdescription(rs.getString(3));
				supplier.setLinkedman(rs.getString(4));
				supplier.setTelphone(rs.getString(5));
				supplier.setAddress(rs.getString(6));
				suppliers.add(supplier);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		return suppliers;
	}

	public int suppliernum(String suppliername, String supplierdescription) {

		getConnection();
		String query = "select count(supplierid) from supplier where suppliername like '%'||?||'%' and supplierdescription like '%'||?||'%'";
		int num = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, suppliername);
			ps.setString(2, supplierdescription);
			rs = ps.executeQuery();
			while (rs.next()) {
				num = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		return num;
	}

	public boolean supplierinfochange(Supplier supplier) {
		getConnection();
		boolean consequence = false;
		String query = "update supplier set suppliername=?,supplierdescription=?,linkedman=?,telphone=?,address=? where supplierid=?";
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, supplier.getSuppliername());
			ps.setString(2, supplier.getSupplierdescription());
			ps.setString(3, supplier.getLinkedman());
			ps.setString(4, supplier.getTelphone());
			ps.setString(5, supplier.getAddress());
			ps.setLong(6, supplier.getSupplierid());
			int i = ps.executeUpdate();
			consequence = i > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		return consequence;
	}

	public boolean supplierdelete(Supplier supplier) {
		getConnection();
		boolean consequence = false;
		String query = "delete supplier where supplierid=?";
		try {
			ps = conn.prepareStatement(query);
			ps.setLong(1, supplier.getSupplierid());
			int i = ps.executeUpdate();
			consequence = i > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		return consequence;
	}

	public boolean supplierinsert(Supplier supplier) {
		getConnection();
		boolean consequence = false;
		String query = "insert into supplier values(next value for supplier_seq,?,?,?,?,?)";
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, supplier.getSuppliername());
			ps.setString(2, supplier.getSupplierdescription());
			ps.setString(3, supplier.getLinkedman());
			ps.setString(4, supplier.getTelphone());
			ps.setString(5, supplier.getAddress());
			int i = ps.executeUpdate();
			consequence = i > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		return consequence;
	}

	public ArrayList<Supplier> supplierlookfor(int page, int ppage,
			String suppliername, String supplierdescription) {
		ArrayList<Supplier> suppliers = new ArrayList<Supplier>();
		getConnection();
		// String query =
		// "select supplierid,suppliername,supplierdescription,linkedman,telphone,address"
		// +
		// " from (select rownum as rn,supplier.* from supplier where suppliername "
		// + "like '%'||?||'%' and supplierdescription like '%'||?||'%')"
		// + "where rn between "
		// + (ppage * (page - 1) + 1)
		// + " and "
		// + (ppage * page);
		String query = "select supplierid,suppliername,supplierdescription,linkedman,telphone,address from supplier where suppliername like '%'||?||'%' and supplierdescription like '%'||?||'%' offset "
				+ ppage
				* (page - 1)
				+ " rows fetch next "
				+ ppage
				+ " rows only";
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, suppliername);
			ps.setString(2, supplierdescription);
			rs = ps.executeQuery();
			while (rs.next()) {
				Supplier supplier = new Supplier();
				supplier.setSupplierid(rs.getLong(1));
				supplier.setSuppliername(rs.getString(2));
				supplier.setSupplierdescription(rs.getString(3));
				supplier.setLinkedman(rs.getString(4));
				supplier.setTelphone(rs.getString(5));
				supplier.setAddress(rs.getString(6));
				suppliers.add(supplier);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		return suppliers;
	}

	public ArrayList<Bill> selectbill() {
		ArrayList<Bill> bills = new ArrayList<Bill>();
		getConnection();
		String query = "select billid,goodsname,goodsnum,money,ispay,suppliername,goodsdescription,billtime from bill";
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				Bill bill = new Bill();
				bill.setBillid(rs.getLong(1));
				bill.setGoodsname(rs.getString(2));
				bill.setGoodsnum(rs.getInt(3));
				bill.setMoney(rs.getDouble(4));
				bill.setIspay(rs.getString(5));
				bill.setSuppliername(rs.getString(6));
				bill.setGooddescription(rs.getString(7));
				bill.setBillTime(rs.getDate(8));
				bills.add(bill);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		return bills;
	}

	public boolean billinsert(Bill bill) {
		getConnection();
		boolean consequence = false;
		String query = "insert into bill values(next value for bill_seq,?,?,?,?,?,?,?)";
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, bill.getGoodsname());
			ps.setInt(2, bill.getGoodsnum());
			ps.setDouble(3, bill.getMoney());
			ps.setString(4, bill.getIspay());
			ps.setString(5, bill.getSuppliername());
			ps.setString(6, bill.getGooddescription());
			ps.setDate(7, bill.getBillTime());

			int i = ps.executeUpdate();
			consequence = i > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		return consequence;
	}

	public boolean billchange(Bill bill) {
		getConnection();
		boolean consequence = false;
		String query = "update bill set goodsname=?,goodsnum=?,money=?,ispay=?,suppliername=?,goodsdescription=?,billtime=? where billid=? ";
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, bill.getGoodsname());
			ps.setInt(2, bill.getGoodsnum());
			ps.setDouble(3, bill.getMoney());
			ps.setString(4, bill.getIspay());
			ps.setString(5, bill.getSuppliername());
			ps.setString(6, bill.getGooddescription());
			ps.setDate(7, bill.getBillTime());
			ps.setLong(8, bill.getBillid());
			int i = ps.executeUpdate();
			consequence = i > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		return consequence;
	}

	public boolean billdelete(Bill bill) {
		getConnection();
		boolean consequence = false;
		String query = "delete bill where billid=?";
		try {
			ps = conn.prepareStatement(query);
			ps.setLong(1, bill.getBillid());
			int i = ps.executeUpdate();
			consequence = i > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		return consequence;
	}

	public boolean billdelete1(long supplierid) {
		getConnection();
		boolean consequence = false;
		String query = "delete bill where suppliername=?";
		try {
			ps = conn.prepareStatement(query);
			ps.setLong(1, supplierid);
			int i = ps.executeUpdate();
			consequence = i > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		return consequence;
	}

	public ArrayList<Bill> billlookfor(int page, int ppage, String goodsname,
			String ispay) {
		ArrayList<Bill> bills = new ArrayList<Bill>();
		getConnection();
		// String query =
		// "select a.billid,a.goodsname,a.goodsnum,a.money,a.ispay,a.sup,a.goodsdescription,a.billtime"
		// +
		// " from (select rownum as rn,s.suppliername as sup,b.* from bill b,supplier s "
		// + "where b.suppliername=s.supplierid and b.goodsname "
		// + "like '%'||?||'%' and b.ispay like '%'||?||'%')a "
		// + "where rn between "
		// + (ppage * (page - 1) + 1)
		// + " and "
		// + (ppage * page);
		String query = "select a.billid,a.goodsname,a.goodsnum,a.money,a.ispay,a.sup,a.goodsdescription,a.billtime from (select s.suppliername as sup,b.* from bill b,supplier s where b.suppliername=s.supplierid and b.goodsname "
				+ "like '%'||?||'%' and b.ispay like '%'||?||'%')a offset "
				+ ppage
				* (page - 1)
				+ " rows fetch next "
				+ ppage
				+ " rows only";

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, goodsname);
			ps.setString(2, ispay);
			rs = ps.executeQuery();
			while (rs.next()) {
				Bill bill = new Bill();
				bill.setBillid(rs.getLong(1));
				bill.setGoodsname(rs.getString(2));
				bill.setGoodsnum(rs.getInt(3));
				bill.setMoney(rs.getDouble(4));
				bill.setIspay(rs.getString(5));
				bill.setSuppliername(rs.getString(6));
				bill.setGooddescription(rs.getString(7));
				bill.setBillTime(rs.getDate(8));
				bills.add(bill);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		return bills;
	}

	public int billnum(String goodname, String ispay) {

		getConnection();
		String query = "select count(billid) from bill where goodsname like '%'||?||'%' and ispay like '%'||?||'%'";
		int num = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, goodname);
			ps.setString(2, ispay);
			rs = ps.executeQuery();
			while (rs.next()) {
				num = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}

		return num;

	}

}
