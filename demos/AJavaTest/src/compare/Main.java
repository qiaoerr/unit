/**
  @Title: Main.java
  @Package compare
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2014-3-6 和怜06:07:36
  @version V1.0
 */

package compare;

import java.util.Arrays;

/**
 * @ClassName: Main
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2014-3-6 和怜06:07:36
 * 
 */

public class Main {

	public Main() {
	}

	public static void main(String[] args) {
		Person[] person = new Person[] {
				new Person("ouyang", "feng", '槻', new Integer(27)),
				new Person("zhuang", "gw", '槻', new Integer(27)),
				new Person("deng", "jx", '槻', new Integer(28)),
				new Person("deng", "jx", '溺', new Integer(27)),

		};

		for (int i = 0; i < person.length; i++) {
			System.out.println("電會念=" + person[i].getFirstname() + "  "
					+ person[i].getLastname() + "  " + person[i].getAge()
					+ "  " + person[i].getSex());
		}
		/*
		 * 功象sort圭隈議及匯倖歌方頁person��隼朔壓Comparators.getComparator()圭隈嶄孀欺斤哘議圭隈
		 * compare(Person o1, Person o2)��歌方嚥person窃侏匯崑
		 */
		java.util.Arrays.sort(person, Comparators.getComparator());

		System.out
				.println("！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
		for (int i = 0; i < person.length; i++) {
			System.out.println("電會朔=" + person[i].getFirstname() + "  "
					+ person[i].getLastname() + "  " + person[i].getAge()
					+ "  " + person[i].getSex());
		}
		System.out
				.println("！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");

		Integer[] data = new Integer[] { 2, 3, 1 };

		for (int i = 0; i < data.length; i++) {
			System.out.println("電會念=" + data[i] + "  ");
		}
		Arrays.sort(data, Comparators.getComparator());
		System.out
				.println("！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");

		for (int i = 0; i < data.length; i++) {
			System.out.println("電會朔=" + data[i] + "  ");
		}

	}

}
