package clientA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import client.Sent;
import client.finder;

public class Enterface {
	JFrame awindow = new JFrame("音乐共享平台");
	Icon img = new ImageIcon("conf/main.jpg");
	JLabel lbl1 = new JLabel(img);
	JPanel toppanel = new JPanel();
	JTabbedPane tabpane = new JTabbedPane();
	JPanel panel1 = new JPanel();
	// JPanel panel2=new JPanel();
	JScrollPane panel2 = new JScrollPane();
	JScrollPane panel3 = new JScrollPane();
	JPanel panel4 = new JPanel();
	JScrollPane jspane = new JScrollPane();
	public static DefaultTableModel tablemodel = new DefaultTableModel(
			new String[] { "文件名", "下载进度", "文件大小" }, 0);
	private JTable table = new JTable(tablemodel);

	JPanel panel_a = new JPanel();
	JPanel panel_b = new JPanel();
	JPanel panel_c = new JPanel();

	JTextPane txa1 = new JTextPane();
	JTextPane txa2 = new JTextPane();
	JLabel lbl2 = new JLabel();
	JList mylist = new JList();
	String str = null;
	String str1 = null;
	byte[] picture = null;
	// byte[] picture=new byte[2*1024*1024];//与上一句有什么不一样
	HashMap<String, Long> map = null;

	Vector<String> data = new Vector<String>();
	JList list = new JList();

	public static void main(String[] args) {
		Enterface init = new Enterface();
		init.intoface();
	}

	public void enter() {

		// gettxa1 gettxa = new gettxa1();
		// gettxa.sent1();
		// gettxa.sent2();
		// gettxa.sent3();
		// gettxa.sentformusic();

		// 不用静态变量的写法
		Sent clientsocket = new Sent();

		str = clientsocket.gettxa1();
		txa1.setText("   " + str.substring(0, 51) + ".......");
		txa1.setMargin(new Insets(16, 16, 6, 6));
		Font font = new Font(null, Font.BOLD, 16);
		txa1.setFont(font);
		txa1.setForeground(Color.RED);// //为什么没效果

		str1 = clientsocket.gettxa2();
		txa2.setText(str1);
		txa2.setForeground(Color.BLACK);

		picture = clientsocket.getpicture();
		lbl2.setIcon(new ImageIcon(picture));

		map = clientsocket.getmusic();
		// new Enterface().intoface();
		intoface();
	}

	// 窗体设置
	@SuppressWarnings({ "deprecation" })
	public void intoface() {
		awindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		toppanel.setLayout(null);
		// awindow.setBounds(210, 105, 810, 590);
		awindow.setSize(810, 590);
		awindow.setLocationRelativeTo(null);
		// awindow.setSize(810, 590);
		lbl1.setBounds(0, 0, 800, 436);
		tabpane.setBounds(0, 43, 800, 516);

		txa1.setBounds(10, 20, 200, 160);
		txa2.setBounds(10, 20, 200, 160);
		tabpane.addTab("主窗口", panel1);
		tabpane.addTab("歌曲列表", panel2);
		tabpane.addTab("下载列表", panel3);
		tabpane.addTab("我的音乐", panel4);
		// panel4.add(mylist);
		// tabpane.setTabPlacement(JTabbedPane.TOP);//默认为TOP不必再设置
		{
			panel_a.setBounds(20, 20, 490, 455);
			panel_b.setBounds(530, 20, 220, 200);
			panel_c.setBounds(530, 255, 220, 200);

			// tabpane.setSelectedIndex(-1);

			panel1.setLayout(null);// 设置内置容器的布局管理，注意默认情况下不为null
			panel1.add(panel_a);
			panel1.add(panel_b);
			panel1.add(panel_c);

			panel_b.setLayout(null);
			panel_b.add(txa1);
			panel_c.setLayout(null);
			panel_c.add(txa2);

			txa1.enable(false);
			txa2.enable(false);
			txa1.setEditable(false);
			txa2.setEditable(false);

			panel_a.setLayout(null);
			jspane.setBounds(3, 3, 485, 450);
			jspane.setViewportView(lbl2);
			// lbl2.setSize(1000, 1000);
			// lbl2.setPreferredSize(new java.awt.Dimension(1000, 1000));
			panel_a.add(jspane);

			Border border = BorderFactory.createEtchedBorder(
					EtchedBorder.LOWERED, Color.GREEN, Color.GREEN);
			// Border border=BorderFactory.createBevelBorder(BevelBorder.RAISED,
			// Color.RED, Color.GREEN);
			Border border1 = BorderFactory.createEtchedBorder(
					EtchedBorder.LOWERED, Color.BLACK, Color.BLACK);
			TitledBorder tBorder1 = BorderFactory.createTitledBorder(border1,
					"专辑介绍", TitledBorder.LEFT, TitledBorder.TOP);
			TitledBorder tBorder2 = BorderFactory.createTitledBorder(border1,
					"曲目列表", TitledBorder.LEFT, TitledBorder.TOP);
			panel_a.setBorder(border);
			panel_b.setBorder(tBorder1);
			panel_c.setBorder(tBorder2);
		}

		{
			Vector<String> data = new Vector<String>();
			Collection<Map.Entry<String, Long>> set = map.entrySet();
			for (Map.Entry<String, Long> kv : set) {
				String name = kv.getKey();
				data.add(name);
			}
			// panel2.setLayout(null);
			// panel2.setLayout(new BorderLayout());
			// panel2.setLayout(new ScrollPaneLayout());
			// list.setBounds(0, 0, 790, 520);
			// ListModel listmodel =new DefaultComboBoxModel(data);
			// list.setModel(listmodel);
			list.setListData(data);
			list.setCellRenderer(new Colorlist());
			panel2.setViewportView(list);// ////////111111111
			panel2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		}
		{
			panel3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			panel3.setViewportView(table);
			ProgressBar progressbar = new ProgressBar();
			table.getColumnModel().getColumn(1).setCellRenderer(progressbar);// 添加进度条
			table.setEnabled(false);

		}

		{
			panel4.setLayout(new BorderLayout());
			JButton btn = new JButton("修改默认音乐存储文件夹");
			btn.setPreferredSize(new java.awt.Dimension(10, 56));
			// btn.setSize(10, 56);
			panel4.add(btn, BorderLayout.SOUTH);

			String strpath = Clientxml.XPathc();
			Vector<String> vec = new finder().fileforeach(strpath);
			mylist.setListData(vec);
			mylist.setCellRenderer(new Colorlist());
			// JScrollPane spane=new JScrollPane(mylist);////////22222222
			// spane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			panel4.add(mylist);

			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File(Clientxml.XPathc()));// 设置当前目录
					chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);// 设置用户只能选择目录
					int returnVal = chooser.showDialog(null, "设置保存路径");
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File ff = chooser.getSelectedFile();

						String strpath = ff.getPath();
						Vector<String> vec = new finder().fileforeach(strpath);
						mylist.setListData(vec);
						panel4.add(mylist);// ////？？？？？？？？？？？？？？？？

						mylist.setCellRenderer(new Colorlist());
						JScrollPane spane = new JScrollPane(mylist);
						spane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
						panel4.add(spane, BorderLayout.CENTER);
						Clientxml.amend(ff.getPath());// 修改路径
					}

				}
			});
		}

		toppanel.add(tabpane);
		toppanel.add(lbl1);
		awindow.add(toppanel);
		awindow.setResizable(false);
		awindow.setVisible(true);

		lbl2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JFrame frame = new JFrame("专辑详细信息");
					frame.setSize(600, 400);
					// 设置窗体显示位置
					frame.setLocationRelativeTo(null);
					JTextPane panel = new JTextPane();
					// panel.setSize(350, 260);//不起作用
					JTextPane panel1 = new JTextPane();
					Icon icon = new ImageIcon(picture);
					JScrollPane spane = new JScrollPane(panel);
					spane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
					spane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
					panel.setMargin(new Insets(56, 36, 56, 36));
					panel.setText("     " + str.substring(0, 560));
					panel.insertIcon(icon);

					{
						Calendar calendar = Calendar.getInstance();
						int year = calendar.get(Calendar.YEAR);
						int month = calendar.get(Calendar.MONTH) + 1;
						int day = calendar.get(Calendar.DAY_OF_MONTH);
						int hour = calendar.get(Calendar.HOUR_OF_DAY);
						int minute = calendar.get(Calendar.MINUTE);
						int second = calendar.get(Calendar.SECOND);
						panel1.setText(str.substring(560, str.length())
								+ "\n\n\n\n\n\n"
								+ "                                              "
								+ "当前时间是" + year + "年" + month + "月" + day
								+ "天" + hour + "时" + minute + "分" + second
								+ "秒");
					}
					panel.insertComponent(panel1);
					panel.setFont(new Font(null, Font.ITALIC, 16));
					panel1.setFont(new Font(null, Font.ITALIC, 16));
					panel.setEditable(false);
					// panel.setSelectionStart(2);
					panel1.setSelectionColor(Color.GREEN);
					panel1.setForeground(Color.BLUE);
					frame.add(spane);
					frame.setVisible(true);
				}
			}
		});
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					String name = (String) list.getSelectedValue();

					JFileChooser choose = new JFileChooser();

					choose.setCurrentDirectory(new File(Clientxml.XPathc()));
					choose.setSelectedFile(new File(name));
					choose.addChoosableFileFilter(new namefilter("wma"));
					choose.addChoosableFileFilter(new namefilter("mp3"));
					choose.addChoosableFileFilter(new namefilter("txt"));
					int value = choose.showDialog(awindow, "music_save");
					if (value == JFileChooser.APPROVE_OPTION) {

						File file = choose.getSelectedFile();// 获得文件保存的路径
						long sizes = size(name);
						if (file.exists()) {
							int i = JOptionPane.showConfirmDialog(null,
									"Sure to override existed music ？", "提示",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.INFORMATION_MESSAGE);
							if (i == JOptionPane.YES_OPTION) {
								tabpane.setSelectedIndex(2);

								Sent sent = new Sent();
								sent.musicdown(name, sizes, file);
							}
						} else {
							tabpane.setSelectedIndex(2);
							Sent sent = new Sent();
							if (sent.musicdown(name, sizes, file)) {
								String strpath = Clientxml.XPathc();
								;
								Vector<String> vec = new finder()
										.fileforeach(strpath);
								mylist.setListData(vec);
							}
						}
					}
				}
			}
		});
	}

	public Long size(String name) {
		long big = 0;
		Collection<Map.Entry<String, Long>> set = map.entrySet();
		for (Map.Entry<String, Long> k_v : set) {
			if (k_v.getKey().equals(name))
				big = k_v.getValue();
		}
		return big;
	}

}
