/**
  @Title: Main.java
  @Package compare
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2014-3-6 ÏÂÎç06:07:36
  @version V1.0
 */

package pack.comparator;

import java.util.Arrays;

/**
 * @ClassName: Main
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2014-3-6 ÏÂÎç06:07:36
 * 
 */

public class Main {

	public Main() {
	}

	public static void main(String[] args) {
		Person[] person = new Person[] {
				new Person("ouyang", "feng", 'ÄÐ', new Integer(27)),
				new Person("zhuang", "gw", 'ÄÐ', new Integer(27)),
				new Person("deng", "jx", 'ÄÐ', new Integer(28)),
				new Person("deng", "jx", 'Å®', new Integer(27)),

		};

		for (int i = 0; i < person.length; i++) {
			System.out.println("ÅÅÐòÇ°=" + person[i].getFirstname() + "  "
					+ person[i].getLastname() + "  " + person[i].getAge()
					+ "  " + person[i].getSex());
		}
		/*
		 * ¸ù¾Ýsort·½·¨µÄµÚÒ»¸ö²ÎÊýÊÇperson£¬È»ºóÔÚComparators.getComparator()·½·¨ÖÐÕÒµ½¶ÔÓ¦µÄ·½·¨
		 * compare(Person o1, Person o2)£¬²ÎÊýÓëpersonÀàÐÍÒ»ÖÂ
		 */
		java.util.Arrays.sort(person, Comparators.getComparator());

		System.out
				.println("¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª");
		for (int i = 0; i < person.length; i++) {
			System.out.println("ÅÅÐòºó=" + person[i].getFirstname() + "  "
					+ person[i].getLastname() + "  " + person[i].getAge()
					+ "  " + person[i].getSex());
		}
		System.out
				.println("¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª");

		Integer[] data = new Integer[] { 2, 3, 1 };

		for (int i = 0; i < data.length; i++) {
			System.out.println("ÅÅÐòÇ°=" + data[i] + "  ");
		}
		Arrays.sort(data, Comparators.getComparator());
		System.out
				.println("¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª¡ª");

		for (int i = 0; i < data.length; i++) {
			System.out.println("ÅÅÐòºó=" + data[i] + "  ");
		}

	}

}
