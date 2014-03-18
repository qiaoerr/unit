/**
 * @Title: Main.java
 * @Package tools
 * @Description: TODO
 * Copyright: Copyright (c) 2011 
 * Company:北京天下互联科技有限公司
 * 
 * @author Comsys-Administrator
 * @date 2013-8-8 上午09:53:42
 * @version V1.0
 */

package tools;

import java.io.File;

/**
 * @ClassName: Main
 * @Description: TODO 批量复制文件
 * @author Comsys-Administrator
 * @date 2013-8-8 上午09:53:42
 * 
 */

public class Main {
	private static String destinationFilePath = "F:\\file\\destination";
	private static String sourceFilePath = "F:\\file\\source";// 源文件目录
	private static String sourceFileName = null;// 源文件 （from）
	private static String destinationFileName = null;// 目标文件 (to)

	public static void main(String[] args) throws Exception {

		// 批量复制文件
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
