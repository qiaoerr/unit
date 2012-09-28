package client;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Clientregister {
	JFrame awindow = new JFrame();
	Icon img = new ImageIcon("conf/b1.jpg");
	JLabel lblt5 = new JLabel(img);
	JLabel lblt1 = new JLabel("用户名:");
	JLabel lblt2 = new JLabel("输入密码:");
	JLabel lblt3 = new JLabel("确认密码:");
	JLabel lblt4 = new JLabel("邮箱:");
	JTextField txt1 = new JTextField();
	JPasswordField txt2 = new JPasswordField();
	JPasswordField txt3 = new JPasswordField();
	JTextField txt4 = new JTextField();
	JButton btn1 = new JButton("注册");
	JButton btn2 = new JButton("重置");
	JButton btn3 = new JButton("返回");
	JButton btn4 = new JButton("是");
	JButton btn5 = new JButton("否");
	JFrame jframe = new JFrame();
	JDialog dialog = new JDialog(jframe, "您确定返回吗？");

	public static void main(String[] args) {
		new Clientregister().clientregister();
	}

	// 注册
	public void clientregister() {
		// Clientregister awindow=new Clientregister();
		// awindow.inface();
		// awindow.tolistener();
		this.inface();
		this.tolistener();
	}

	// 内部监听器类
	private class myactionlistener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("注册")) {
				String username = txt1.getText();
				char[] password = txt2.getPassword();
				// char[] conformpassword=txt3.getPassword();
				String email = txt4.getText();
				if (username.length() != 0 && email.contains("@")) {
					Sent sent = new Sent();
					String str = sent.register(username, password, email);
					if (str.equals("success")) {
						JOptionPane.showMessageDialog(awindow, "注册成功");
						awindow.setVisible(false);
						ClientLogin.awindow.setVisible(true);
					} else if (str.equals("used")) {
						JOptionPane.showMessageDialog(awindow, "用户已存在");

					}
				} else {
					JOptionPane.showMessageDialog(awindow, "请检查注册信息是否完整和正确");
				}

			} else if (e.getActionCommand().equals("重置")) {
				txt1.setText("");
				txt2.setText("");
				txt3.setText("");
				txt4.setText("");
			} else if (e.getActionCommand().equals("返回")) {
				dialog.setLayout(new FlowLayout());
				dialog.setBounds(580, 345, 200, 80);
				dialog.add(btn4);
				dialog.add(btn5);
				dialog.setVisible(true);
			} else if (e.getActionCommand().equals("是")) {
				dialog.setVisible(false);
				awindow.setVisible(false);
				ClientLogin.awindow.setVisible(true);
			} else if (e.getActionCommand().equals("否")) {
				dialog.setVisible(false);
			}
		}

	}

	// 监听器的绑定
	public void tolistener() {
		myactionlistener mal = new myactionlistener();
		// myactionlistener1 mal1=new myactionlistener1();
		btn1.addActionListener(mal);
		btn2.addActionListener(mal);
		btn3.addActionListener(mal);
		btn4.addActionListener(mal);
		btn5.addActionListener(mal);
	}

	// 窗体设置
	public void inface() {
		awindow.setTitle("音乐共享平台");
		awindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		awindow.setSize(910, 600);

		// awindow.getContentPane().setLayout(null);
		// awindow.setLocation(180, 120);
		lblt1.setBounds(500, 160, 90, 20);
		txt1.setBounds(560, 160, 150, 20);
		lblt2.setBounds(500, 190, 90, 20);
		txt2.setBounds(560, 190, 150, 20);
		lblt3.setBounds(500, 220, 90, 20);
		txt3.setBounds(560, 220, 150, 20);
		lblt4.setBounds(500, 250, 90, 20);
		txt4.setBounds(560, 250, 150, 20);
		lblt5.setBounds(0, 0, 900, 550);
		btn1.setBounds(500, 320, 60, 30);
		btn2.setBounds(580, 320, 60, 30);
		btn3.setBounds(660, 320, 60, 30);
		// awindow.getContentPane().add(lblt5);
		awindow.getContentPane().add(lblt1);
		awindow.getContentPane().add(txt1);
		awindow.getContentPane().add(lblt2);
		awindow.getContentPane().add(txt2);
		awindow.getContentPane().add(lblt3);
		awindow.getContentPane().add(txt3);
		awindow.getContentPane().add(lblt4);
		awindow.getContentPane().add(txt4);
		awindow.getContentPane().add(btn1);
		awindow.getContentPane().add(btn2);
		awindow.getContentPane().add(btn3);
		awindow.getContentPane().add(lblt5);
		awindow.pack();
		awindow.setLocationRelativeTo(null);
		awindow.setVisible(true);
	}
}
