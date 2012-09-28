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
	ServerSocket server = null;// ���������׽���
	BufferedReader input = null;// ������Ķ�ȡ��
	PrintStream output = null;// ��ӡ��
	Socket client = null;// �׽��ֶ���

	@Override
	public void run() {
		// �����˿�
		int port = 8888;
		try {
			server = new ServerSocket(port);
			while (true) {
				client = server.accept();
				// ���������ܵ����׽��ֵ�����
				input = new BufferedReader(new InputStreamReader(
						client.getInputStream()));
				// ��������socket�������
				output = new PrintStream(client.getOutputStream());
				String temp = input.readLine();
				String[] info = temp.split(" ");
				// �û���¼
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
					// �û�ע��
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
					// �����������������txa1��Ϣ
				} else if (("gettxa1").equals(info[0])) {
					output.println(new readfield().read());
					output.close();
					input.close();
					// �����������������txa2��Ϣ
				} else if (("gettxa2").equals(info[0])) {
					output.println(new readfield().read1());
					output.close();
					input.close();
					// �����������������picture��Ϣ
				} else if (("getpicture").equals(info[0])) {
					byte[] picturedata = new readfield().getpicture();
					output.write(picturedata);
					output.close();
					input.close();
					// ����������������б���Ϣ
				} else if (("getmusiclist").equals(info[0])) {
					get_musicinformation();
					input.close();
					// ���������������
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
		System.out.println("��������ֹͣ");
	}

	// ��ȡ�����б����Ϣ
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

	// ���ظ���
	private void DownlMouse(String name) {
		// System.out.println(name);////////////////////////
		String path = new readfield().GetPath(name);// ���ݷ��صĸ��������������·��
		byte[] sizes = new byte[2 * 1028 * 1028];
		int len = -1;
		try {
			FileInputStream input = new FileInputStream(path);
			while ((len = input.read(sizes)) > 0) {
				output.write(sizes, 0, len);
				System.out.println("�������ݵĴ�С��" + len);
				output.flush();
			}
			input.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
