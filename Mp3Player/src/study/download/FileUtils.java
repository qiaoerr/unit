package study.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import study.model.Mp3Info;
import android.os.Environment;

public class FileUtils {

	private String SDCardRoot;

	public String getSDPATH() {
		return SDCardRoot;
	}

	public FileUtils() {
		// 得到当前外部存储设备的目录
		// File.separator与系统有关的默认名称分隔符。此字段被初始化为包含系统属性 file.separator
		// 的值的第一个字符。在 UNIX 系统上，此字段的值为 '/'；在 Microsoft Windows 系统上，它为 '\\'。
		SDCardRoot = Environment.getExternalStorageDirectory() + File.separator;
	}

	/**
	 * 在SD卡上创建文件
	 * 
	 * @param fileName
	 *            文件名
	 * @param dir
	 *            目录名
	 * @return File对象
	 * @throws IOException
	 */
	private File creatFileInSDCard(String fileName, String dir)
			throws IOException {
		File file = new File(SDCardRoot + dir + File.separator + fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirName
	 *            目录名称
	 */
	private File creatSDDir(String dir) {
		File dirFile = new File(SDCardRoot + dir + File.separator);
		dirFile.mkdirs();
		return dirFile;
	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 * 
	 * @param fileName
	 *            文件名
	 * @param path
	 *            路径
	 * @return
	 */
	public boolean isFileExist(String fileName, String path) {
		File file = new File(SDCardRoot + path + File.separator + fileName);
		return file.exists();
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 * 
	 * @param path
	 *            路径
	 * @param fileName
	 *            文件名
	 * @param input
	 *            输入流
	 * @return
	 */
	public File write2SDFromInput(String path, String fileName,
			InputStream input) {
		File file = null;
		FileOutputStream fos = null;
		try {
			creatSDDir(path);
			creatFileInSDCard(fileName, path);
			file = new File(SDCardRoot + path + File.separator + fileName);
			fos = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int temp = -1;
			while ((temp = input.read(buffer)) != -1) {
				fos.write(buffer, 0, temp);
			}
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return file;
		// File file = null;
		// OutputStream output = null;
		// try {
		// creatSDDir(path);
		// file = creatFileInSDCard(fileName, path);
		// output = new FileOutputStream(file);
		// byte buffer[] = new byte[4 * 1024];
		// int temp;
		// while ((temp = input.read(buffer)) != -1) {
		// output.write(buffer, 0, temp);
		// }
		// output.flush();
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// try {
		// output.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// return file;
	}

	/**
	 * 将网络文件保存为本地文件的方法
	 * 
	 * @param destUrl
	 *            下载的链接
	 * @param fileName
	 *            完整的文件路径加文件名
	 * @throws IOException
	 */
	public void saveToFile(String destUrl, String fileName) throws IOException {

		// FileOutputStream fos = null;
		// BufferedInputStream bis = null;
		// HttpURLConnection httpconn = null;
		// URL url = null;
		// byte[] buf = new byte[6048];
		// int size = 0;
		// // 建立链接
		// url = new URL(destUrl);
		// httpconn = (HttpURLConnection) url.openConnection();
		// // 连接指定的资源
		// httpconn.connect();
		// // 获取网络输入流
		// bis = new BufferedInputStream(httpconn.getInputStream());
		// // 建立文件
		// fos = new FileOutputStream(fileName);
		// // 保存文件
		// while ((size = bis.read(buf)) != -1)
		// fos.write(buf, 0, size);
		// fos.close();
		// bis.close();
		// httpconn.disconnect();
	}

	/**
	 * 读取目录中mp3的名称和大小
	 * 
	 * @param path
	 *            目录
	 * @return
	 */
	public List<Mp3Info> getMp3Files(String para) {
		ArrayList<Mp3Info> mp3infos = new ArrayList<Mp3Info>();
		String path = SDCardRoot + para;
		File file = new File(path);
		File[] files = file.listFiles();
		if (files == null) {
			return null;
		}
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().toLowerCase().endsWith("mp3")
					|| files[i].getName().toLowerCase().endsWith("wma")) {
				Mp3Info info = new Mp3Info();
				info.setMp3Name(files[i].getName());
				info.setMp3Size(files[i].length() + "");
				info.setLrcName(files[i].getName().substring(0,
						files[i].getName().lastIndexOf(".") + 1)
						+ "lrc");
				mp3infos.add(info);
			}
		}
		return mp3infos;

		// List<Mp3Info> mp3infos = new ArrayList<Mp3Info>();
		// File file = new File(SDCardRoot + path + File.separator);
		// File[] files = file.listFiles();
		// for (int i = 0; i < files.length; i++) {
		//
		// if (files[i].getName().endsWith("mp3")) {
		// Mp3Info mp3info = new Mp3Info();
		// mp3info.setMp3Name(files[i].getName());
		// mp3info.setMp3Size(files[i].length() + "");
		// mp3info.setLrcName(files[i].getName().substring(0,
		// files[i].getName().lastIndexOf("."))
		// + ".lrc");
		// mp3infos.add(mp3info);
		// }
		// }
		// return mp3infos;
	}
}