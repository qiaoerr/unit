package tools;

import java.io.File;

public class ReNameFile {

	public static String path = "C:\\Documents and Settings\\Administrator\\����\\drawable-hdpi";
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
								"C:\\Documents and Settings\\Administrator\\����\\test\\"
										+ "nicon" + num + ".png"));// �ǵý�·��Ҳ����
						num++;
					}
				}
			}
		} else {
			rootFile.renameTo(new File(
					"C:\\Documents and Settings\\Administrator\\����\\test\\"
							+ "nicon" + num + ".png"));// �ǵý�·��Ҳ���� }
			num++;
		}

	}
}
