/*
 * mp3¡¢wmaÒôÀÖ¸ñÊ½Ñ¡Ôñ
 */
package clientA;

import java.io.File;
import javax.swing.filechooser.FileFilter;
public class namefilter extends FileFilter {
	int i=0;
	String str;
	namefilter(String str) {
		this.str = str;
	}

	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return false;
		}
		String fileName = file.getName();
		
//		System.out.println(fileName+"aaa"+(++i));
		int index = fileName.lastIndexOf('.');

		if (index > 0 && index < fileName.length() - 1) {
			String extension = fileName.substring(index + 1).toLowerCase();
			if (extension.equals(str))
				return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		if (str.equals("mp3")) {
			return "mp3 music file";
		}else if (str.equals("wma")) {
			return "wma music file";
		}else if(str.equals("txt")){
			return "txt file";
		}else{
		  return "";
		}
	}
}