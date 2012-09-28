package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class infor {

	static ArrayList<User> listuser = new ArrayList<User>();

	// 登录验证
	@SuppressWarnings("unchecked")
	public static boolean getinforlogin(String name, String password)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		boolean login = false;
		System.out.println("当前路径： " + System.getProperty("user.dir"));
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
				"conf/information.txt"));
		listuser = (ArrayList<User>) ois.readObject();
		for (User user : listuser) {
			if (user.getUsername().equals(name)
					&& user.getPassword().equals(password)) {
				login = true;
				break;
			} else {
				login = false;
			}
		}
		ois.close();
		return login;

	}

	// 注册验证
	@SuppressWarnings("unchecked")
	public static String register(String user, String password, String email)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
				"conf/information.txt"));
		listuser = (ArrayList<User>) ois.readObject();
		ois.close();
		for (User use : listuser) {
			if (use.getUsername().equals(user)) {
				return "used";
			}

		}
		User use = new User(user, password, email);
		listuser.add(use);
		save(listuser);
		return "success";

	}

	// 初始序列化
	public static void main(String[] args) throws FileNotFoundException,
			IOException, ClassNotFoundException {
		User user = new User("1", "1", "@");
		listuser.add(user);
		save(listuser);

	}

	// 存入用户资料
	public static void save(ArrayList<User> listuser)
			throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				"conf/information.txt"));
		oos.writeObject(listuser);
		oos.flush();
		oos.close();
	}
}
