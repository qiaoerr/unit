package pack.clone.shallowClone;

public class Student implements Cloneable {
	private StringBuffer name = new StringBuffer("lily");
	private int age = 19;

	public String getName() {
		return name.toString();
	}

	public void setName(String name) {
		this.name.delete(0, name.length() + 1);
		this.name.append(name);
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	protected Student clone() {
		Student object = null;
		try {
			object = (Student) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return object;
	}

}
