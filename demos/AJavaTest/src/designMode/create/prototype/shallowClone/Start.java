package designMode.create.prototype.shallowClone;

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Student student = new Student();
		System.out.println(student);
		System.out.println(student.getName());
		// System.out.println(student.getAge());
		Student clone = student.clone();
		student.setName("jim");
		System.out.println(clone);
		System.out.println(clone.getName());
		System.out.println(student.getName());
		// System.out.println(clone.getAge());

	}

}
