package pack.clone.general;

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Student student = new Student();
		System.out.println(student);
		System.out.println(student.getName());
		Student clone = (Student) student.clone();
		System.out.println(clone);
		System.out.println(clone.getName());

	}

}
