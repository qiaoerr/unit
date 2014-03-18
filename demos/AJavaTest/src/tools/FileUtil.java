/**
 * @Title: FileUtil.java
 * @Package test
 * @Description: TODO
 * Copyright: Copyright (c) 2011 
 * Company:北京天下互联科技有限公司
 * 
 * @author Comsys-Administrator
 * @date 2013-8-6 下午12:56:41
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
	 * getAppPath需要一个当前程序使用的Java类的class属性参数，它可以返回打包过的
	 * Java可执行文件（jar，war）所处的系统目录名或非打包Java程序所处的目录
	 * 
	 * @param cls为Class类型
	 * @return 返回值为该类所在的Java程序运行的目录
	 *         ----------------------------------------------
	 *         ---------------------------
	 */
	public static String getAppPath(Class cls) {
		// 检查用户传入的参数是否为空
		if (cls == null)
			throw new java.lang.IllegalArgumentException("参数不能为空！");
		ClassLoader loader = cls.getClassLoader();
		// 获得类的全名，包括包名
		String clsName = cls.getName() + ".class";
		// 获得传入参数所在的包
		Package pack = cls.getPackage();
		String path = "";
		// 如果不是匿名包，将包名转化为路径
		if (pack != null) {
			String packName = pack.getName();
			// 此处简单判定是否是Java基础类库，防止用户传入JDK内置的类库
			if (packName.startsWith("java.") || packName.startsWith("javax."))
				throw new java.lang.IllegalArgumentException("不要传送系统类！");
			// 在类的名称中，去掉包名的部分，获得类的文件名
			clsName = clsName.substring(packName.length() + 1);
			// 判定包名是否是简单包名，如果是，则直接将包名转换为路径，
			if (packName.indexOf(".") < 0)
				path = packName + "/";
			else {// 否则按照包名的组成部分，将包名转换为路径
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
		// 调用ClassLoader的getResource方法，传入包含路径信息的类文件名
		java.net.URL url = loader.getResource(path + clsName);
		// 从URL对象中获取路径信息
		String realPath = url.getPath();
		// 去掉路径信息中的协议名"file:"
		int pos = realPath.indexOf("file:");
		if (pos > -1)
			realPath = realPath.substring(pos + 5);
		// 去掉路径信息最后包含类文件信息的部分，得到类所在的路径
		pos = realPath.indexOf(path + clsName);
		realPath = realPath.substring(0, pos - 1);
		// 如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名
		if (realPath.endsWith("!"))
			realPath = realPath.substring(0, realPath.lastIndexOf("/"));
		/*------------------------------------------------------------ 
		  ClassLoader的getResource方法使用了utf-8对路径信息进行了编码，当路径 
		   中存在中文和空格时，他会对这些字符进行转换，这样，得到的往往不是我们想要 
		   的真实路径，在此，调用了URLDecoder的decode方法进行解码，以便得到原始的 
		   中文及空格路径 
		 -------------------------------------------------------------*/
		try {
			realPath = java.net.URLDecoder.decode(realPath, "utf-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return realPath;
	}// getAppPath定义结束

	public static String getAppPath() {
		if (appPath == null)
			appPath = getAppPath(FileUtil.class);
		return appPath;
	}

	/**
	 * 删除文件及文件夹
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

		if (file.list().length == 0) {// 删除空文件夹
			file.delete();
		}
	}

	/**
	 * 删除文件夹中包括子文件夹所有名为fileName的文件、文件夹 用于除去如版本控制之类产生的文件，减少应用大小
	 * 
	 * @param file
	 * @param fileName
	 *            文件或文件夹名
	 */
	public static void delFile(File file, String fileName) {
		if (!file.exists()) {
			return;
		}
		// 找到文件，删除
		if (file.getName().equals(fileName)) {
			System.out.print("删除文件 " + file.getPath());
			delFile(file);// 删除其子文件
			if (!file.exists()) {
				System.out.println(" 成功");
			} else {
				System.out.println("失败");
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
	 * 取得文件大小
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
	 * 复制文件
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
	 * 通过递归得到某一路径下所有的目录及其文件
	 */
	public static void replaceAllFiles(String filePath, String src,
			String content) {
		ArrayList<String> filelist = new ArrayList<String>();
		File root = new File(filePath);
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				/*
				 * 递归调用
				 */
				replaceAllFiles(file.getAbsolutePath(), src, content);
			} else {
				transferFile(file, src, content);
			}
		}
	}

	/**
	 * 读文件
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
	 *            图片路径
	 * @param saveUrl
	 *            保存路径
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

		// 建立链接
		url = new URL(destUrl);
		httpUrl = (HttpURLConnection) url.openConnection();
		// 连接指定的资源
		httpUrl.connect();
		// 获取网络输入流
		bis = new BufferedInputStream(httpUrl.getInputStream());
		// 建立文件
		fos = new FileOutputStream(fileName);

		// if (this.DEBUG)
		// System.out.println("正在获取链接[" + destUrl + "]的内容...\n将其保存为文件[" +
		// fileName + "]");
		// 保存文件
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
