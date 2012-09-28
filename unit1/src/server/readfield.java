package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeSet;

public class readfield {
	public StringBuffer sstr = new StringBuffer();

	public TreeSet<String> filenamelist = new TreeSet<String>();
	public TreeSet<String> filepathlist = new TreeSet<String>();

	HashMap<String, Long> hashmap = new HashMap<String, Long>();

	public String read() {
		try {
			FileReader in = new FileReader("conf/info.txt");
			BufferedReader br = new BufferedReader(in);
			try {
				String str = null;
				while ((str = br.readLine()) != null) {
					sstr.append(str);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return sstr.toString();
	}

	public String read1() {
		try {
			FileReader in = new FileReader("conf/list.txt");
			BufferedReader br = new BufferedReader(in);
			try {
				String str = null;
				while ((str = br.readLine()) != null) {
					sstr.append(str + "A");
					// return str;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return sstr.toString();
	}


	public byte[] getpicture() throws IOException {
		File file = new File("conf/RiverSouth.jpg");
		FileInputStream fin = new FileInputStream(file);
		// 字节数组
		byte[] picturedata = new byte[4 * 1024 * 1024];
		fin.read(picturedata);
		fin.close();
		return picturedata;
	}

	// 文件递归遍历
	public HashMap<String, Long> fileforeach(String strPath) {
		File dir = new File(strPath);
		File[] files = dir.listFiles();
		// System.out.println(files);/////////////
		// System.out.println(files.length);//为什么加上此句后下面的return会变成deadcode
		if (files == null)
			return null;
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				// System.out.println(files[i].getAbsolutePath());////////
				fileforeach(files[i].getAbsolutePath());
			} else {
				String strFileName = files[i].getAbsolutePath().toLowerCase();
				// System.out.println(strFileName);
				String s = strFileName.substring(strPath.length() + 1,
						strFileName.length());
				// System.out.println(s);///////////
				long l = 0;
				for (int j = 0; j < s.length(); j++) {
					if (s.charAt(j) == '.') {
						String name = null;
						name = s.toLowerCase().substring(j + 1, s.length());
						if (name.equals("mp3") || name.equals("wma")) {
							File file = new File(strFileName);
							l = file.length();
							hashmap.put(s, l);
							filenamelist.add(s);
							filepathlist.add(strFileName);

						}
					}
				}
			}
		}
		return hashmap;
	}

	public String GetPath(String name) {
		String path = null;
		String path1 = Serverinfor_parse.Musicpath();
		fileforeach(path1);
		for (String str : filepathlist) {
			String pathName = str.substring((str.length() - name.length()),
					str.length());
			if (pathName.equals(name))
				path = str;
		}
		// System.out.println(path);//////////////////////////////////////
		return path;
	}

	public static void main(String[] args) {
		readfield r = new readfield();
		r.GetPath("西界.mp3");
	}
}
