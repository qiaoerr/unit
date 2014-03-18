package tools;

import java.io.File;

public class ReNameFile {

	public static String path = "C:\\Documents and Settings\\Administrator\\桌面\\drawable-hdpi";
	private static int num = 97;

	public static void main(String[] args) {
		reName(path);
	}

	public static void reName(String filePath) {
		File rootFile = new File(filePath);
		if (rootFile.isDirectory()) {

			File files[] = rootFile.listFiles();
			if (files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					File f = files[i];
					if (f.isDirectory()) {
						reName(f.getAbsolutePath());
					} else {

						f.renameTo(new File(
								"C:\\Documents and Settings\\Administrator\\桌面\\test\\"
										+ "nicon" + num + ".png"));// 记得将路径也输入
						num++;
					}
				}
			}
		} else {
			rootFile.renameTo(new File(
					"C:\\Documents and Settings\\Administrator\\桌面\\test\\"
							+ "nicon" + num + ".png"));// 记得将路径也输入 }
			num++;
		}

	}
}
