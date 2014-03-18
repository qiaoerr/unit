/**
 * @Title: ArrayTest.java
 * @Package test
 * @Description: TODO
 * Copyright: Copyright (c) 2011 
 * Company:北京天下互联科技有限公司
 * 
 * @author Comsys-Administrator
 * @date 2013-7-31 上午09:57:13
 * @version V1.0
 */

package test;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayTest {

	/**
	 * 
	 * @Title: main
	 * @Description: TODO
	 * @param @param args 设定文件
	 * @return void 返回类型
	 * @throws
	 */

	public static void main(String[] args) {
		ArrayList<String> total = new ArrayList<String>();
		ArrayList<String> sub = new ArrayList<String>();
		total.add("a");
		total.add("b");
		total.add("c");
		sub.add("1");
		sub.add("2");
		sub.add("3");
		total.addAll(0, sub);
		for (int i = 0; i < total.size(); i++) {
			System.out.println(total.get(i));
		}
		total.removeAll(sub);
		Iterator<String> iterator = total.iterator();
		while (iterator.hasNext()) {
			String tem = iterator.next();
			System.out.println(tem);
		}
	}

}
