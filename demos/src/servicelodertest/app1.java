package servicelodertest;

public class app1 implements SpiServer {
	String str;

	public void set(String str) {
		this.str = str;
	}

	@Override
	public void test() {
		System.out.println(str);
	}

}
