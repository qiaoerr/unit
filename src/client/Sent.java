package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import clientA.Enterface;

public class Sent {
	BufferedReader input = null;
	PrintStream output = null;
	String info = null;
	Socket socket = null;
	StringBuffer buf = new StringBuffer();

	// 登录
	public String login(String str1, char[] str2) {
		try {
			socket = new Socket("localhost", 8888);
			input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			output = new PrintStream(socket.getOutputStream());

			// 发送登陆请求
			output.println("login " + str1.trim() + " "
					+ new String(str2).trim());
			output.flush();
			// 接受服务器响应
			info = input.readLine();
			System.out.println(info);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (output != null) {

					output = null;
				}
				if (!(socket == null || socket.isClosed())) {
					socket.close();
					socket = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return info;
	}

	// 注册
	public String register(String str1, char[] str2, String str3) {
		try {
			socket = new Socket("localhost", 8888);// 建立与服务器连接
			input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			output = new PrintStream(socket.getOutputStream());

			// 发送注册请求
			output.println("register " + str1.trim() + " "
					+ new String(str2).trim() + " " + str3.trim());
			output.flush();// 清空输出
			// 接受服务器响应
			info = input.readLine();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
					output = null;
				}
				if (!(socket == null || socket.isClosed())) {
					socket.close();
					socket = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return info;
	}

	// 从服务器获取专辑介绍
	public String gettxa1() {
		try {
			socket = new Socket("localhost", 8888);// 建立与服务器连接
			input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			output = new PrintStream(socket.getOutputStream());

			// 发送请求
			output.println("gettxa1");
			output.flush();// 清空输出
			// 接受服务器响应
			info = input.readLine();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
					output = null;
				}
				if (!(socket == null || socket.isClosed())) {
					socket.close();
					socket = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return info;
	}

	// 从服务器获取专辑曲目
	public String gettxa2() {
		try {
			socket = new Socket("localhost", 8888);// 建立与服务器连接
			input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			output = new PrintStream(socket.getOutputStream());

			// 发送请求
			output.println("gettxa2");
			output.flush();// 清空输出
			// 接受服务器响应
			info = input.readLine().replace('A', '\n');
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
					output = null;
				}
				if (!(socket == null || socket.isClosed())) {
					socket.close();
					socket = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return info;
	}

	// 从服务器获取图片
	public byte[] getpicture() {
		byte[] info = new byte[4 * 1024 * 1024];
		try {
			socket = new Socket("localhost", 8888);// 建立与服务器连接
			// input = new BufferedReader(new
			// InputStreamReader(socket.getInputStream()));
			InputStream input = socket.getInputStream();
			output = new PrintStream(socket.getOutputStream());

			// 发送请求
			output.println("getpicture");
			output.flush();// 清空输出
			// 接受服务器响应
			input.read(info);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
					output = null;
				}
				if (!(socket == null || socket.isClosed())) {
					socket.close();
					socket = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return info;
	}

	// 从服务器获取音乐列表信息
	@SuppressWarnings("unchecked")
	public HashMap<String, Long> getmusic() {
		HashMap<String, Long> map = new HashMap<String, Long>();
		try {
			socket = new Socket("localhost", 8888);// 建立与服务器连接
			InputStream input = socket.getInputStream();
			output = new PrintStream(socket.getOutputStream());

			// 发送请求
			output.println("getmusiclist");
			output.flush();// 清空输出
			// 接受服务器响应
			ObjectInputStream ois = new ObjectInputStream(input);
			map = (HashMap<String, Long>) ois.readObject();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
					output = null;
				}
				if (!(socket == null || socket.isClosed())) {
					socket.close();
					socket = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	// 从服务器下载音乐
	public boolean musicdown(String name, long sizes, File savepath) {
		final long size = sizes;
		float sizemb = (float) size;
		sizemb = sizemb / (1024f * 1024f);
		int a = (int) (sizemb * 1000);
		float b = (float) a / 1000;
		Object[] addrow = new Object[] { name, 0, b + "M" };
		final int row = getindexofrow(addrow);
		try {
			socket = new Socket("localhost", 8888);
			InputStream input = socket.getInputStream();
			PrintWriter output = new PrintWriter(socket.getOutputStream());
			String request = "musicdown" + " " + name;
			FileOutputStream writetofile = new FileOutputStream(savepath);
			output.println(request);
			output.flush();
			int len = -1;
			byte[] bb = new byte[2 * 1028 * 1028];
			int i = 0;
			while ((len = input.read(bb)) > 0) {
				i += len;
				update((int) (i * 100 / size), row, 1);
				writetofile.write(bb, 0, len);
				writetofile.flush();
			}
			writetofile.close();
			input.close();
			output.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public int getindexofrow(Object[] data) {
		Enterface.tablemodel.addRow(data);
		return Enterface.tablemodel.getRowCount() - 1;
	}

	public void update(int value, int row, int col) {
		Enterface.tablemodel.setValueAt(value, row, col);
	}

}