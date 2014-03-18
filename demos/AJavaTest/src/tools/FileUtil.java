/**
 * @Title: FileUtil.java
 * @Package test
 * @Description: TODO
 * Copyright: Copyright (c) 2011 
 * Company:�������»����Ƽ����޹�˾
 * 
 * @author Comsys-Administrator
 * @date 2013-8-6 ����12:56:41
 * @version V1.0
 */

package tools;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FileUtil {
	static String appPath = null;
	public static int buffer_size = 2048;

	private static void doCopy(File src, File dst) {
		if (src.isFile()) {
			try {
				copyFile(src, dst);

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			File dir = copyDirectory(src, dst);
			File[] files = src.listFiles();
			if (files.length == 0) {

			} else {
				for (File file : files) {
					doCopy(file, dir);
				}
			}
		}
	}

	public static void copy(File src, File dst) throws IOException {
		if (!src.exists() || !dst.exists())
			throw new FileNotFoundException();
		else if (src.isFile() && dst.isFile())
			copy(new FileReader(src), new FileWriter(dst));
		else if (src.isDirectory() && (!dst.isDirectory()))
			throw new IllegalArgumentException(
					"Destination should be a directory!");
		else {
			doCopy(src, dst);
		}

	}

	private static void copyFile(File src, File dst) throws IOException {
		File file = new File(dst, src.getName());
		copy(new FileReader(src), new FileWriter(file));
	}

	private static File copyDirectory(File src, File dst) {
		File file = new File(dst, src.getName());
		file.mkdir();
		return file;
	}

	private static int copy(Reader in, Writer out) throws IOException {
		int byteCount = 0;
		try {
			int bytesReader = -1;
			char[] buffer = new char[buffer_size];

			while ((bytesReader = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesReader);
				byteCount += bytesReader;
			}
			out.flush();
		} finally {
			in.close();
			out.close();
		}
		return byteCount;
	}

	/**
	 * -----------------------------------------------------------------------
	 * getAppPath��Ҫһ����ǰ����ʹ�õ�Java���class���Բ����������Է��ش������
	 * Java��ִ���ļ���jar��war��������ϵͳĿ¼����Ǵ��Java����������Ŀ¼
	 * 
	 * @param clsΪClass����
	 * @return ����ֵΪ�������ڵ�Java�������е�Ŀ¼
	 *         ----------------------------------------------
	 *         ---------------------------
	 */
	public static String getAppPath(Class cls) {
		// ����û�����Ĳ����Ƿ�Ϊ��
		if (cls == null)
			throw new java.lang.IllegalArgumentException("��������Ϊ�գ�");
		ClassLoader loader = cls.getClassLoader();
		// ������ȫ������������
		String clsName = cls.getName() + ".class";
		// ��ô���������ڵİ�
		Package pack = cls.getPackage();
		String path = "";
		// ���������������������ת��Ϊ·��
		if (pack != null) {
			String packName = pack.getName();
			// �˴����ж��Ƿ���Java������⣬��ֹ�û�����JDK���õ����
			if (packName.startsWith("java.") || packName.startsWith("javax."))
				throw new java.lang.IllegalArgumentException("��Ҫ����ϵͳ�࣡");
			// ����������У�ȥ�������Ĳ��֣��������ļ���
			clsName = clsName.substring(packName.length() + 1);
			// �ж������Ƿ��Ǽ򵥰���������ǣ���ֱ�ӽ�����ת��Ϊ·����
			if (packName.indexOf(".") < 0)
				path = packName + "/";
			else {// �����հ�������ɲ��֣�������ת��Ϊ·��
				int start = 0, end = 0;
				end = packName.indexOf(".");
				while (end != -1) {
					path = path + packName.substring(start, end) + "/";
					start = end + 1;
					end = packName.indexOf(".", start);
				}
				path = path + packName.substring(start) + "/";
			}
		}
		// ����ClassLoader��getResource�������������·����Ϣ�����ļ���
		java.net.URL url = loader.getResource(path + clsName);
		// ��URL�����л�ȡ·����Ϣ
		String realPath = url.getPath();
		// ȥ��·����Ϣ�е�Э����"file:"
		int pos = realPath.indexOf("file:");
		if (pos > -1)
			realPath = realPath.substring(pos + 5);
		// ȥ��·����Ϣ���������ļ���Ϣ�Ĳ��֣��õ������ڵ�·��
		pos = realPath.indexOf(path + clsName);
		realPath = realPath.substring(0, pos - 1);
		// ������ļ��������JAR���ļ���ʱ��ȥ����Ӧ��JAR�ȴ���ļ���
		if (realPath.endsWith("!"))
			realPath = realPath.substring(0, realPath.lastIndexOf("/"));
		/*------------------------------------------------------------ 
		  ClassLoader��getResource����ʹ����utf-8��·����Ϣ�����˱��룬��·�� 
		   �д������ĺͿո�ʱ���������Щ�ַ�����ת�����������õ�����������������Ҫ 
		   ����ʵ·�����ڴˣ�������URLDecoder��decode�������н��룬�Ա�õ�ԭʼ�� 
		   ���ļ��ո�·�� 
		 -------------------------------------------------------------*/
		try {
			realPath = java.net.URLDecoder.decode(realPath, "utf-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return realPath;
	}// getAppPath�������

	public static String getAppPath() {
		if (appPath == null)
			appPath = getAppPath(FileUtil.class);
		return appPath;
	}

	/**
	 * ɾ���ļ����ļ���
	 * 
	 * @param file
	 */
	public static void delFile(File file) {
		if (!file.exists()) {
			return;
		}
		if (file.isFile()) {
			file.delete();
			return;
		}
		File files[] = file.listFiles();

		for (int i = 0; i < files.length; i++) {
			delFile(files[i]);

		}

		if (file.list().length == 0) {// ɾ�����ļ���
			file.delete();
		}
	}

	/**
	 * ɾ���ļ����а������ļ���������ΪfileName���ļ����ļ��� ���ڳ�ȥ��汾����֮��������ļ�������Ӧ�ô�С
	 * 
	 * @param file
	 * @param fileName
	 *            �ļ����ļ�����
	 */
	public static void delFile(File file, String fileName) {
		if (!file.exists()) {
			return;
		}
		// �ҵ��ļ���ɾ��
		if (file.getName().equals(fileName)) {
			System.out.print("ɾ���ļ� " + file.getPath());
			delFile(file);// ɾ�������ļ�
			if (!file.exists()) {
				System.out.println(" �ɹ�");
			} else {
				System.out.println("ʧ��");
			}
			return;
		}
		if (file.isFile()) {
			return;
		}
		File files[] = file.listFiles();

		for (int i = 0; i < files.length; i++) {
			delFile(files[i], fileName);

		}

	}

	/**
	 * ȡ���ļ���С
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSizes(File f) {
		long s = 0;
		FileInputStream fis = null;
		try {
			if (f.exists()) {

				fis = new FileInputStream(f);
				s = fis.available();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}

		return s;
	}

	/**
	 * �����ļ�
	 * 
	 * @param sourceFile
	 * @param destFile
	 * @throws Exception
	 */
	public static void copy(String sourceFile, String destFile)
			throws Exception {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			byte[] buf = new byte[1024];
			fis = new FileInputStream(sourceFile);
			fos = new FileOutputStream(destFile);
			int len = 0;
			while ((len = fis.read(buf)) > 0) {
				fos.write(buf, 0, len);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
	}

	public static void write(String file, String content) {
		try {
			PrintWriter pw = new PrintWriter(file);
			pw.write(content);
			pw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public static void write(String file, String content, String tem) {
		try {
			PrintWriter pw = new PrintWriter(file, tem);
			pw.write(content);
			pw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public static void transferFile(File file, String src, String content) {
		try {
			// File file=new File(name);
			BufferedInputStream bin = new BufferedInputStream(
					new FileInputStream(file));
			byte[] buff = new byte[(int) file.length()];
			bin.read(buff);
			FileOutputStream fout = new FileOutputStream(file);
			String str = new String(buff);
			String[] lines = str.split("\n");
			for (String line : lines) {
				String line_changed = line.replace(src, content);
				fout.write((line_changed + "\n").getBytes());
			}
			fout.flush();
			fout.close();
			bin.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/*
	 * ͨ���ݹ�õ�ĳһ·�������е�Ŀ¼�����ļ�
	 */
	public static void replaceAllFiles(String filePath, String src,
			String content) {
		ArrayList<String> filelist = new ArrayList<String>();
		File root = new File(filePath);
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				/*
				 * �ݹ����
				 */
				replaceAllFiles(file.getAbsolutePath(), src, content);
			} else {
				transferFile(file, src, content);
			}
		}
	}

	/**
	 * ���ļ�
	 * 
	 * @param path
	 * @return
	 */
	public static String readFile(String path) {
		StringBuffer sb = new StringBuffer();
		try {
			File enterF = new File(path);
			if (enterF.exists()) {
				FileInputStream fi;
				fi = new FileInputStream(enterF);
				InputStreamReader isr = new InputStreamReader(fi, "UTF-8");
				BufferedReader bfin = new BufferedReader(isr);
				String rLine = "";
				while ((rLine = bfin.readLine()) != null) {
					sb.append(rLine + "\n");
				}
				fi.close();
				isr.close();
				bfin.close();

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String readFile(String path, String tem) {
		StringBuffer sb = new StringBuffer();
		try {
			File enterF = new File(path);
			if (enterF.exists()) {
				FileInputStream fi;
				fi = new FileInputStream(enterF);
				InputStreamReader isr = new InputStreamReader(fi, tem);
				BufferedReader bfin = new BufferedReader(isr);
				String rLine = "";
				while ((rLine = bfin.readLine()) != null) {
					sb.append(rLine + "\n");
				}
				fi.close();
				isr.close();
				bfin.close();

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static void modifyFile(String file, String regex,
			String replacement, String tem) {
		String str = readFile(file, tem).replaceAll(regex, replacement);
		write(file, str, tem);
	}

	public static void modifyFile(String file, String regex, String replacement) {
		String str = readFile(file).replaceAll(regex, replacement);
		write(file, str);
	}

	/**
	 * @param imgUrl
	 *            ͼƬ·��
	 * @param saveUrl
	 *            ����·��
	 */
	public static void downLoad(String imgUrl, String saveUrl) {
		try {
			URL url = new URL(imgUrl);
			File outFile = new File(saveUrl);
			OutputStream os = new FileOutputStream(outFile);
			InputStream is = url.openStream();
			byte[] buff = new byte[1024];
			while (true) {
				int readed = is.read(buff);
				if (readed == -1) {
					break;
				}
				byte[] temp = new byte[readed];
				System.arraycopy(buff, 0, temp, 0, readed);
				os.write(temp);
			}
			is.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	}

	public static void saveToFile(String destUrl, String fileName)
			throws IOException {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		byte[] buf = new byte[1024 * 2];
		int size = 0;

		// ��������
		url = new URL(destUrl);
		httpUrl = (HttpURLConnection) url.openConnection();
		// ����ָ������Դ
		httpUrl.connect();
		// ��ȡ����������
		bis = new BufferedInputStream(httpUrl.getInputStream());
		// �����ļ�
		fos = new FileOutputStream(fileName);

		// if (this.DEBUG)
		// System.out.println("���ڻ�ȡ����[" + destUrl + "]������...\n���䱣��Ϊ�ļ�[" +
		// fileName + "]");
		// �����ļ�
		while ((size = bis.read(buf)) != -1)
			fos.write(buf, 0, size);

		fos.close();
		bis.close();
		httpUrl.disconnect();
	}

	public static void main(String[] args) throws IOException {

		for (int i = 44; i < 130; i++) {

			copy(new File("d://imgs"), new File("d://qiyeT/TixaEnterStyle" + i
					+ "/res/drawable-hdpi"));

		}

	}
}
