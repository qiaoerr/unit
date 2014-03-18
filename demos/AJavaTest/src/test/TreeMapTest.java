package test;

import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

public class TreeMapTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TreeMap<String, String> map = new TreeMap<String, String>(
				Collections.reverseOrder());
		map.put("1", "1");
		map.put("a", "3");
		map.put("4", "4");
		map.put("5", "5");
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String temp = iterator.next();
			System.out.println(temp);
			// System.out.println(map.get(temp));
		}
	}

}
