package pack.clone.general;

public class Student implements ProtoType {
	private String name = "lily";
	private int age = 19;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public ProtoType clone() {
		ProtoType object = new Student();
		object.setAge(age);
		object.setName(name);
		return object;
	}

}
