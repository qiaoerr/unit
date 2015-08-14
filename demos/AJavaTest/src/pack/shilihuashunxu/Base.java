package pack.shilihuashunxu;

public class Base {
	public int i = 99;

	public void method() {
		System.out.println("Base的方法i=" + i);
	}

	void method1() {
		System.out.println("Base的方法i=" + i);
	}

	public Base() {
		method();
	}
}
