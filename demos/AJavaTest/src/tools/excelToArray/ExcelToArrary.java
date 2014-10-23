package tools.excelToArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;

public class ExcelToArrary {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> arrayList = null;
		ArrayList<String> proArray = new ArrayList<String>();
		TreeMap<String, ArrayList<String>> map = new TreeMap<String, ArrayList<String>>();
		try {
			InputStream in = ExcelToArrary.class
					.getResourceAsStream("excel.txt");
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String str = null;
			while ((str = reader.readLine()) != null) {
				str = str.trim();
				String[] strs = str.split("	");
				if (strs.length == 2) {
					arrayList = new ArrayList<String>();
					arrayList.add(strs[1]);
					map.put(strs[0], arrayList);
					proArray.add(strs[0]);
				} else {
					arrayList.add(strs[0]);
				}
			}
			StringBuffer sBuffer = new StringBuffer();
			for (int i = 0; i < proArray.size(); i++) {
				ArrayList<String> tempArray = map.get(proArray.get(i));
				sBuffer.append("{");
				for (int j = 0; j < tempArray.size(); j++) {
					// System.out.println(tempArray.get(j));
					if (j == (tempArray.size() - 1)) {
						sBuffer.append("\"" + tempArray.get(j) + "\"");
					} else {
						sBuffer.append("\"" + tempArray.get(j) + "\",");
					}
				}
				if (i == proArray.size() - 1) {
					sBuffer.append("}");
				} else {
					sBuffer.append("},");
				}
			}
			System.out.println(sBuffer.toString());
			/*Set<String> set = map.keySet();
			Iterator<String> temp = set.iterator();
			while (temp.hasNext()) {
				String proStr = temp.next();
				System.out.print(proStr);
				ArrayList<String> tempArray = map.get(proStr);
				for (int i = 0; i < tempArray.size(); i++) {
					System.out.println(tempArray.get(i));
				}
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
