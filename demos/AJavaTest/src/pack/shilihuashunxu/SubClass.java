package pack.shilihuashunxu;

public class SubClass extends Base {
	static int i = -1;

	public static void main(String[] args) {
		Base b = new SubClass();
		Base b1 = new Base();
		System.out.println(b.i);
		b.method();
		if (b instanceof SubClass) {
			SubClass c;
			c = (SubClass) b;
			c.getbase();
		}
	}

	public void method() {
		System.out.println("SubClassµÄ·½·¨i=" + i);
	}

	void getbase() {
		super.method();
	}
}
