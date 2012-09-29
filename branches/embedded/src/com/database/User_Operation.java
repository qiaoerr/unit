package com.database;

import java.sql.SQLException;
import java.util.ArrayList;

import com.bean.User;

public class User_Operation {
	private DB_Operation dbo = null;

	private void set_dbo() {
		DB_Operation dbo = new DB_Operation();
		dbo.setDriver("org.apache.derby.jdbc.EmbeddedDriver");
		dbo.setUrl("jdbc:derby:mydb");
		dbo.setUsername("unit");
		dbo.setPassword("java");
		this.dbo = dbo;
	}

	public ArrayList<User> getUser() {
		set_dbo();
		ArrayList<User> users = null;
		users = dbo.selectUser();
		return users;
	}

	// �û��ĸ���
	public boolean userupdate(User user) throws SQLException {
		set_dbo();
		return dbo.userupdate(user);
	}

	// �û�ɾ��
	public boolean userdelete(User user) throws SQLException {
		set_dbo();
		return dbo.userdelete(user);
	}

	// �û���ѯ
	public ArrayList<User> lookforuser(String name) {
		set_dbo();
		ArrayList<User> users = null;
		users = dbo.lookforUser(name);
		return users;
	}

	// ��������
	public boolean changepwd(User user) throws SQLException {
		set_dbo();
		return dbo.changepwd(user);
	}

	// �û�����
	public boolean userinsert(User user) throws SQLException {
		set_dbo();
		return dbo.userinsert(user);
	}

	// �û�Ȩ��
	public String getPower(String name) {
		set_dbo();
		return dbo.getPower(name);
	}

	// �û�������ѯ
	public int usernum() {
		set_dbo();
		return dbo.usernum();
	}

	public int usernum1(String name) {
		set_dbo();
		return dbo.usernum1(name);
	}

	// ��ҳ��ѯ
	public ArrayList<User> querybypage(int page, int ppage, String name) {
		set_dbo();
		return dbo.querybypage(page, ppage, name);
	}

	// ���Դ���
	public static void main(String[] args) {
		ArrayList<User> users = new User_Operation().querybypage(2, 3, "����");
		for (User user : users) {
			System.out.println(user.getName());
		}
	}

}
