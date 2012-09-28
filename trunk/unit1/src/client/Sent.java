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

	// ��¼
	public String login(String str1, char[] str2) {
		try {
			socket = new Socket("localhost", 8888);
			input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			output = new PrintStream(socket.getOutputStream());

			// ���͵�½����
			output.println("login " + str1.trim() + " "
					+ new String(str2).trim());
			output.flush();
			// ���ܷ�������Ӧ
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

	// ע��
	public String register(String str1, char[] str2, String str3) {
		try {
			socket = new Socket("localhost", 8888);// ���������������
			input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			output = new PrintStream(socket.getOutputStream());

			// ����ע������
			output.println("register " + str1.trim() + " "
					+ new String(str2).trim() + " " + str3.trim());
			output.flush();// ������
			// ���ܷ�������Ӧ
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

	// �ӷ�������ȡר������
	public String gettxa1() {
		try {
			socket = new Socket("localhost", 8888);// ���������������
			input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			output = new PrintStream(socket.getOutputStream());

			// ��������
			output.println("gettxa1");
			output.flush();// ������
			// ���ܷ�������Ӧ
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

	// �ӷ�������ȡר����Ŀ
	public String gettxa2() {
		try {
			socket = new Socket("localhost", 8888);// ���������������
			input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			output = new PrintStream(socket.getOutputStream());

			// ��������
			output.println("gettxa2");
			output.flush();// ������
			// ���ܷ�������Ӧ
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

	// �ӷ�������ȡͼƬ
	public byte[] getpicture() {
		byte[] info = new byte[4 * 1024 * 1024];
		try {
			socket = new Socket("localhost", 8888);// ���������������
			// input = new BufferedReader(new
			// InputStreamReader(socket.getInputStream()));
			InputStream input = socket.getInputStream();
			output = new PrintStream(socket.getOutputStream());

			// ��������
			output.println("getpicture");
			output.flush();// ������
			// ���ܷ�������Ӧ
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

	// �ӷ�������ȡ�����б���Ϣ
	@SuppressWarnings("unchecked")
	public HashMap<String, Long> getmusic() {
		HashMap<String, Long> map = new HashMap<String, Long>();
		try {
			socket = new Socket("localhost", 8888);// ���������������
			InputStream input = socket.getInputStream();
			output = new PrintStream(socket.getOutputStream());

			// ��������
			output.println("getmusiclist");
			output.flush();// ������
			// ���ܷ�������Ӧ
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

	// �ӷ�������������
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