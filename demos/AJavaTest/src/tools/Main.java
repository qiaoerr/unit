/**
 * @Title: Main.java
 * @Package tools
 * @Description: TODO
 * Copyright: Copyright (c) 2011 
 * Company:�������»����Ƽ����޹�˾
 * 
 * @author Comsys-Administrator
 * @date 2013-8-8 ����09:53:42
 * @version V1.0
 */

package tools;

import java.io.File;

/**
 * @ClassName: Main
 * @Description: TODO ���������ļ�
 * @author Comsys-Administrator
 * @date 2013-8-8 ����09:53:42
 * 
 */

public class Main {
	private static String destinationFilePath = "F:\\file\\destination";
	private static String sourceFilePath = "F:\\file\\source";// Դ�ļ�Ŀ¼
	private static String sourceFileName = null;// Դ�ļ� ��from��
	private static String destinationFileName = null;// Ŀ���ļ� (to)

	public static void main(String[] args) throws Exception {

		// ���������ļ�
		int start = 10000;
		int end = 10021;
		copy(start, end);
	}

	public static void copy(int start, int end) throws Exception {
		for (int i = start; i <= end; i++) {
			String temp_dest;
			if (i < 0) {
				temp_dest = destinationFilePath + File.separator
						+ "TixaEnterStyle0" + i + File.separator + "res"
						+ File.separator + "layout";
			} else {
				temp_dest = destinationFilePath + File.separator
						+ "EcommercePlatformTemplate" + i + File.separator
						+ "res" + File.separator + "drawable-hdpi";
			}
			File directory = new File(temp_dest);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			File sourceFileDir = new File(sourceFilePath);
			String[] SourceFileNamesArray = sourceFileDir.list();
			for (int j = 0; j < SourceFileNamesArray.length; j++) {
				sourceFileName = sourceFileDir + File.separator
						+ SourceFileNamesArray[j].trim();
				destinationFileName = temp_dest + File.separator
						+ SourceFileNamesArray[j].trim();
				FileUtil.copy(sourceFileName, destinationFileName);
			}

		}
		System.out.println("end");
	}
}
