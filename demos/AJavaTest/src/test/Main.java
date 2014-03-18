package test;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int n = 1;
		if (n > 0) {
			try {
				throw new MyException("test", new NullPointerException(
						"nullpoint"));
			} catch (MyException e) {

				e.printStackTrace();
			}
		}

	}

}
