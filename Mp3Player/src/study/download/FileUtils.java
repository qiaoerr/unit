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
		// �õ���ǰ�ⲿ�洢�豸��Ŀ¼
		// File.separator��ϵͳ�йص�Ĭ�����Ʒָ��������ֶα���ʼ��Ϊ����ϵͳ���� file.separator
		// ��ֵ�ĵ�һ���ַ����� UNIX ϵͳ�ϣ����ֶε�ֵΪ '/'���� Microsoft Windows ϵͳ�ϣ���Ϊ '\\'��
		SDCardRoot = Environment.getExternalStorageDirectory() + File.separator;
	}

	/**
	 * ��SD���ϴ����ļ�
	 * 
	 * @param fileName
	 *            �ļ���
	 * @param dir
	 *            Ŀ¼��
	 * @return File����
	 * @throws IOException
	 */
	private File creatFileInSDCard(String fileName, String dir)
			throws IOException {
		File file = new File(SDCardRoot + dir + File.separator + fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * ��SD���ϴ���Ŀ¼
	 * 
	 * @param dirName
	 *            Ŀ¼����
	 */
	private File creatSDDir(String dir) {
		File dirFile = new File(SDCardRoot + dir + File.separator);
		dirFile.mkdirs();
		return dirFile;
	}

	/**
	 * �ж�SD���ϵ��ļ����Ƿ����
	 * 
	 * @param fileName
	 *            �ļ���
	 * @param path
	 *            ·��
	 * @return
	 */
	public boolean isFileExist(String fileName, String path) {
		File file = new File(SDCardRoot + path + File.separator + fileName);
		return file.exists();
	}

	/**
	 * ��һ��InputStream���������д�뵽SD����
	 * 
	 * @param path
	 *            ·��
	 * @param fileName
	 *            �ļ���
	 * @param input
	 *            ������
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
	 * �������ļ�����Ϊ�����ļ��ķ���
	 * 
	 * @param destUrl
	 *            ���ص�����
	 * @param fileName
	 *            �������ļ�·�����ļ���
	 * @throws IOException
	 */
	public void saveToFile(String destUrl, String fileName) throws IOException {

		// FileOutputStream fos = null;
		// BufferedInputStream bis = null;
		// HttpURLConnection httpconn = null;
		// URL url = null;
		// byte[] buf = new byte[6048];
		// int size = 0;
		// // ��������
		// url = new URL(destUrl);
		// httpconn = (HttpURLConnection) url.openConnection();
		// // ����ָ������Դ
		// httpconn.connect();
		// // ��ȡ����������
		// bis = new BufferedInputStream(httpconn.getInputStream());
		// // �����ļ�
		// fos = new FileOutputStream(fileName);
		// // �����ļ�
		// while ((size = bis.read(buf)) != -1)
		// fos.write(buf, 0, size);
		// fos.close();
		// bis.close();
		// httpconn.disconnect();
	}

	/**
	 * ��ȡĿ¼��mp3�����ƺʹ�С
	 * 
	 * @param path
	 *            Ŀ¼
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