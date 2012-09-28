package server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MyServer extends Thread {
	ServerSocket server = null;// 服务器端套接字
	BufferedReader input = null;// 带缓冲的读取流
	PrintStream output = null;// 打印流
	Socket client = null;// 套接字对象

	@Override
	public void run() {
		// 监听端口
		int port = 8888;
		try {
			server = new ServerSocket(port);
			while (true) {
				client = server.accept();
				// 侦听并接受到此套接字的连接
				input = new BufferedReader(new InputStreamReader(
						client.getInputStream()));
				// 创建基于socket的输出流
				output = new PrintStream(client.getOutputStream());
				String temp = input.readLine();
				String[] info = temp.split(" ");
				// 用户登录
				if (("login").equals(info[0])) {
					String name = info[1];
					String password = info[2];
					boolean login = false;
					try {
						login = infor.getinforlogin(name, password);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					if (login)
						output.println("success");
					else
						output.println("fail");
					output.flush();
					output.close();
					input.close();
					// 用户注册
				} else if (("register").equals(info[0])) {
					String name = info[1];
					String password = info[2];
					String email = info[3];
					try {
						String msg = "fail";
						msg = infor.register(name, password, email);
						output.println(msg);
						output.flush();
						output.close();
						input.close();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					// 向服务器传递主界面txa1信息
				} else if (("gettxa1").equals(info[0])) {
					output.println(new readfield().read());
					output.close();
					input.close();
					// 向服务器传递主界面txa2信息
				} else if (("gettxa2").equals(info[0])) {
					output.println(new readfield().read1());
					output.close();
					input.close();
					// 向服务器传递主界面picture信息
				} else if (("getpicture").equals(info[0])) {
					byte[] picturedata = new readfield().getpicture();
					output.write(picturedata);
					output.close();
					input.close();
					// 向服务器传递音乐列表信息
				} else if (("getmusiclist").equals(info[0])) {
					get_musicinformation();
					input.close();
					// 向服务器传递音乐
				} else if (("musicdown").equals(info[0])) {
					String name = info[1];
					DownlMouse(name);
					output.close();
					input.close();
				}

			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		try {
			if (output != null) {
				output.close();
				output = null;
			}
			if (!(client == null || client.isClosed())) {
				client.close();
				client = null;
			}
			if (!(server == null || server.isClosed())) {
				server.close();
				server = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("服务器端停止");
	}

	// 获取音乐列表的信息
	public void get_musicinformation() throws IOException {
		String path = Serverinfor_parse.Musicpath();
		readfield rfield = new readfield();
		HashMap<String, Long> map = rfield.fileforeach(path);
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(output);
			oos.writeObject(map);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			output.close();
			oos.close();
		}
	}

	// 下载歌曲
	private void DownlMouse(String name) {
		// System.out.println(name);////////////////////////
		String path = new readfield().GetPath(name);// 根据返回的歌曲名，获得下载路径
		byte[] sizes = new byte[2 * 1028 * 1028];
		int len = -1;
		try {
			FileInputStream input = new FileInputStream(path);
			while ((len = input.read(sizes)) > 0) {
				output.write(sizes, 0, len);
				System.out.println("发送数据的大小：" + len);
				output.flush();
			}
			input.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
