package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import clientA.Enterface;

public class ClientLogin {
	static JFrame awindow = new JFrame();
	Icon img = new ImageIcon("conf/b1.jpg");
	JLabel lblt5 = new JLabel(img);
	JLabel lblt1 = new JLabel("用户名:");
	JLabel lblt2 = new JLabel("密码:");
	JTextField txt1 = new JTextField();
	JPasswordField txt2 = new JPasswordField();
	JButton btn1 = new JButton("登录");
	JButton btn2 = new JButton("注册");

	public static void main(String[] args) {
		ClientLogin user = new ClientLogin();
		user.inface();
		user.tolistener();
	}

	public void enterit() {
		ClientLogin user = new ClientLogin();
		user.inface();
		user.tolistener();
	}

	// 内部监听器类
	private class myactionlistener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("登录")) {
				if (txt1.getText().equals("") || txt2.getPassword().length == 0) {
					JOptionPane.showMessageDialog(null, "请填写完整的信息");
				} else {
					String str1 = txt1.getText();
					char[] str2 = txt2.getPassword();
					Sent sent = new Sent();
					String str = sent.login(str1, str2);
					if (str.equals("success")) {
						awindow.setVisible(false);
						Enterface enterface = new Enterface();
						enterface.enter();
					} else {
						JOptionPane.showMessageDialog(null, "登录信息错误");
					}
				}

			} else if (e.getActionCommand().equals("注册")) {
				Clientregister register = new Clientregister();
				awindow.setVisible(false);
				register.clientregister();

			}
		}
	}

	// 监听器的绑定
	public void tolistener() {
		myactionlistener mal = new myactionlistener();
		btn1.addActionListener(mal);
		btn2.addActionListener(mal);

	}

	// 窗体设置
	public void inface() {
		awindow.setTitle("音乐共享平台");
		awindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// awindow.setSize(910, 550);
		// awindow.setLocationRelativeTo(null);
		// awindow.getContentPane().setLayout(new BorderLayout());
		// awindow.getContentPane().setLayout(null);

		lblt1.setBounds(500, 220, 90, 20);
		txt1.setBounds(560, 220, 150, 20);
		Border border = BorderFactory.createBevelBorder(BevelBorder.RAISED,
				Color.RED, Color.GREEN);
		txt1.setBorder(border);
		lblt2.setBounds(500, 260, 90, 20);
		Border border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
				Color.RED, Color.GREEN);
		txt2.setBounds(560, 260, 150, 20);
		txt2.setBorder(border1);
		lblt5.setBounds(0, 0, 900, 550);
		btn1.setBounds(530, 300, 60, 30);
		btn2.setBounds(630, 300, 60, 30);
		Border border3 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,
				Color.BLACK, Color.BLACK);
		btn1.setBorder(border3);
		btn2.setBorder(border3);

		awindow.getContentPane().add(lblt1);
		awindow.getContentPane().add(txt1);
		awindow.getContentPane().add(lblt2);
		awindow.getContentPane().add(txt2);
		awindow.getContentPane().add(btn1);
		awindow.getContentPane().add(btn2);
		awindow.getContentPane().add(lblt5);
		awindow.pack();
		awindow.setLocationRelativeTo(null);
		awindow.setResizable(false);
		awindow.setVisible(true);
	}

}
