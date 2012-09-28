package Test;

import server.MyServer;
import client.ClientLogin;

public class TestDemo {

	public static void main(String[] args) {
		MyServer myserver = new MyServer();
		myserver.start();
		ClientLogin clientlogin = new ClientLogin();
		clientlogin.enterit();
	}

}
