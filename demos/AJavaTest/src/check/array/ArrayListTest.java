package check.array;

import java.util.AbstractList;
import java.util.ArrayList;

public class ArrayListTest {

	private ArrayList<Student> students;
	ArrayList<Student> temp;
	private Student a;

	public static void main(String[] args) {
		new ArrayListTest().TestMethod();

	}

	private void TestMethod() {
		students = new ArrayList<Student>();
		// temp = students;
		temp = new ArrayList<Student>();
		a = new Student();
		students.add(a);
		temp.add(a);
		System.out.println(students.get(0).getName());
		System.out.println(temp.get(0).getName());
		// ArrayLIst是一个集合容器，里面只是放了各个对象的引用。所以当对象a放到容器students和temp
		// 中时，当通过students获得的对象a与通过容器temp获取的对象a是同一个。
		System.out.println("验证集合只是一个容器，里面放着对象的引用");
		students.get(0).setName("tom");
		System.out.println(students.toString());
		System.out.println(students.hashCode());
		System.out.println(students.get(0).getName());
		System.out.println(temp.toString());
		System.out.println(temp.hashCode());
		System.out.println(temp.get(0).getName());
		if (temp == students) {
			System.out.println("temp === students");
		}else {
			System.out.println("hashcode相同 ，但是两个对象不同");
		}

	}

}
