/**
  @Title: Main.java
  @Package compare
  @Description: TODO
  Copyright: Copyright (c) 2011 
  
  @author Comsys-Administrator
  @date 2014-3-6 ����06:07:36
  @version V1.0
 */

package pack.comparator;

import java.util.Arrays;

/**
 * @ClassName: Main
 * @Description: TODO
 * @author Comsys-Administrator
 * @date 2014-3-6 ����06:07:36
 * 
 */

public class Main {

	public Main() {
	}

	public static void main(String[] args) {
		Person[] person = new Person[] {
				new Person("ouyang", "feng", '��', new Integer(27)),
				new Person("zhuang", "gw", '��', new Integer(27)),
				new Person("deng", "jx", '��', new Integer(28)),
				new Person("deng", "jx", 'Ů', new Integer(27)),

		};

		for (int i = 0; i < person.length; i++) {
			System.out.println("����ǰ=" + person[i].getFirstname() + "  "
					+ person[i].getLastname() + "  " + person[i].getAge()
					+ "  " + person[i].getSex());
		}
		/*
		 * ����sort�����ĵ�һ��������person��Ȼ����Comparators.getComparator()�������ҵ���Ӧ�ķ���
		 * compare(Person o1, Person o2)��������person����һ��
		 */
		java.util.Arrays.sort(person, Comparators.getComparator());

		System.out
				.println("������������������������������������������������������������������������������������������������������������������������������������������������");
		for (int i = 0; i < person.length; i++) {
			System.out.println("�����=" + person[i].getFirstname() + "  "
					+ person[i].getLastname() + "  " + person[i].getAge()
					+ "  " + person[i].getSex());
		}
		System.out
				.println("������������������������������������������������������������������������������������������������������������������������������������������������");

		Integer[] data = new Integer[] { 2, 3, 1 };

		for (int i = 0; i < data.length; i++) {
			System.out.println("����ǰ=" + data[i] + "  ");
		}
		Arrays.sort(data, Comparators.getComparator());
		System.out
				.println("������������������������������������������������������������������������������������������������������������������������������������������������");

		for (int i = 0; i < data.length; i++) {
			System.out.println("�����=" + data[i] + "  ");
		}

	}

}
