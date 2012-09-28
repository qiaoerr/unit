package client;

import java.io.File;
import java.util.Vector;
public class finder {
	Vector<String> namelist=new Vector<String>();
	public  Vector<String> fileforeach(String strPath) {
	    File dir = new File(strPath);
	    File[] files = dir.listFiles();
	    if (files == null)
	        return null;
	    for (int i = 0; i < files.length; i++) {
	        if (files[i].isDirectory()){
	        	fileforeach(files[i].getAbsolutePath());
	        } else {
	        	String strFileName = files[i].getAbsolutePath().toLowerCase();
	            String s=strFileName.substring(strPath.length()+1,strFileName.length());

	            for(int j=0;j<s.length();j++){
	            	if(s.charAt(j)=='.'){
	            		String str=null;
	            		str=s.toLowerCase().substring(j+1,s.length());
	            		if(str.equals("mp3")||str.equals("wma")){    
	            			namelist.add(s);
	            		}
	            	}
	            }
	        }
	    }
	    return namelist;
}
}