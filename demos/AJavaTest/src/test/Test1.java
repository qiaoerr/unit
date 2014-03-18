package test;

public class Test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String temp = "00aa00";
		int len = temp.length();
		int[] rgb = new int[len / 2];
		for (int i = 0, j = 0; i < len; j++) {
			rgb[j] = Integer.parseInt(temp.substring(i, i + 2), 16);
			i += 2;
			System.out.println(rgb[j]);
		}
	}

}
