package pack.clone.deepClone;

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Student student = new Student();
		System.out.println(student);
		System.out.println(student.getName());
		Student clone = student.clone();
		student.setName("jim");
		System.out.println(clone);
		System.out.println(clone.getName());
		System.out.println(student.getName());
	}

}
