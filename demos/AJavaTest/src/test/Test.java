package test;

import java.text.ParseException;

public class Test {

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		String dateString = "May 14, 2013";
		dateString = dateString.replace(",", "");
		dateString = dateString.replace("Jan", "01");
		dateString = dateString.replace("Feb", "02");
		dateString = dateString.replace("Mar", "03");
		dateString = dateString.replace("Apr", "04");
		dateString = dateString.replace("May", "05");
		dateString = dateString.replace("Jun", "06");
		dateString = dateString.replace("Jul", "07");
		dateString = dateString.replace("Aug", "08");
		dateString = dateString.replace("Sep", "09");
		dateString = dateString.replace("Oct", "10");
		dateString = dateString.replace("Nov", "11");
		dateString = dateString.replace("Dec", "12");
		String[] strs = dateString.split(" ");
		StringBuffer sb = new StringBuffer();
		sb.append(strs[2]).append("/").append(strs[0]).append("/")
				.append(strs[1]);
		System.out.println(sb.toString());

	}

}
