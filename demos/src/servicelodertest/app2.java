package servicelodertest;

public class app2 implements SpiServer {
	String str;

	@Override
	public void set(String str) {
		this.str = str;
	}

	@Override
	public void test() {
		System.out.println(str);
	}

}
