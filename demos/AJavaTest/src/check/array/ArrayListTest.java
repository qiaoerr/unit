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
		// ArrayLIst��һ����������������ֻ�Ƿ��˸�����������á����Ե�����a�ŵ�����students��temp
		// ��ʱ����ͨ��students��õĶ���a��ͨ������temp��ȡ�Ķ���a��ͬһ����
		System.out.println("��֤����ֻ��һ��������������Ŷ��������");
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
			System.out.println("hashcode��ͬ ��������������ͬ");
		}

	}

}
