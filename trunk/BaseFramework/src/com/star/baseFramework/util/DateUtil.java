package com.star.baseFramework.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理类
 * 
 */
public class DateUtil {
	public static void main(String[] args) {
		System.out.println(dateFormat(new Date(), null));
	}

	// 格式化时间格式为 22:55
	public static String dateFormat(Date date) {
		String dateStr = "";
		if (date == null) {
			return "";
		}
		// SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mmss");
		SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
		dateStr = sd.format(date);
		return dateStr;
	}

	// 将一个java.util.Date对象转换成特定格式的字符串
	// 默认的格式化时间格式为 12月10日 22:59
	public static String dateFormat(Date date, String formatStr) {
		if (date == null) {
			return "";
		}
		if (StrUtil.isEmpty(formatStr)) {
			formatStr = "MM月dd日 HH:mm";
		}
		SimpleDateFormat sd = new SimpleDateFormat(formatStr);
		String dateStr = sd.format(date);
		return dateStr;
	}

	/**
	 * 将传入的日期(已经排序)转换为符合消息显示的字符串形式（按一定时间段对时间列表进行分割，返回Map<间隔位置,间隔位置的提示内容>
	 * 一小时以内显示“NN分钟前” 一天以内的显示“NN小时前” 3天内显示“1天前2天前3天前” 一周以外显示“2009-03-10”
	 */
	public static String parseDate(Date sourceDate) {
		String resultStr = "";
		if (sourceDate == null) {
			return resultStr;
		}
		Date curDate = new Date();
		long curTime = System.currentTimeMillis();
		long sourceTime = sourceDate.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		long subTime = Math.abs(curTime - sourceTime);// 毫秒单位的间隔
		int subDate = Math.abs(getDistance(curDate, sourceDate));// 天单位的间隔
		if (subDate > 0) {
			// 间隔超过了一天，比如第一个时间23:50,第二个是次日0:10，已经不是同一天的了，不必考虑毫秒数
			if (subDate > 3) {
				resultStr = formatter.format(sourceDate);
			} else {
				resultStr = subDate + "天前";
			}
		} else if (subDate == 0) {
			// 间隔在同一天之内
			if (subTime < 60 * 60 * 1000) {
				// 一小时以内
				String tempValue = ((subTime / (60 * 1000) > 0) ? ("" + subTime
						/ (60 * 1000)) : "1")
						+ "分钟之前";
				resultStr = tempValue;
			} else if (subTime >= 60 * 60 * 1000
					&& subTime < 24 * 60 * 60 * 1000) {
				// 一天以内
				resultStr = (subTime / (60 * 60 * 1000) > 0 ? "" + subTime
						/ (60 * 60 * 1000) : "1")
						+ "小时之前";
			}
		}
		return resultStr;
	}

	/**
	 * 显示今天、昨天或者几月几日的形式
	 */
	public static String parseDateForDay(Date date) {
		String resultStr = "";
		if (date == null) {
			return resultStr;
		}
		resultStr = DateUtil.dateFormat(date, "M月dd日");
		String today = DateUtil.dateFormat(new Date(), "M月dd日");
		if (today.equals(resultStr)) {
			return "今天";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("M月dd日");
		Date tempDate = new Date();
		long tempLong = tempDate.getTime();
		tempLong = tempLong - (24 * 60 * 60 * 1000);
		tempDate.setTime(tempLong);
		String yesterday = formatter.format(tempDate);
		if (yesterday.equals(resultStr)) {
			return "昨天";
		}
		return resultStr;
	}

	// 处理May 26,2013格式的日期
	public static String parseDateFromStr(String dateString) {
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
		return sb.toString();
	}

	public static String getFomatDate(Date date) {
		SimpleDateFormat fomat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return fomat.format(date);
	}

	/**
	 * 
	 * 根据出生日期得到年龄
	 */
	public static String getAgeFromBirthday(String data) throws ParseException {
		SimpleDateFormat myDataFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Date beDate = myDataFormat.parse(data);
		Date edDate = myDataFormat.parse(getTimeStr(date, "yyyy-MM-dd"));
		if (edDate.getTime() < beDate.getTime()) {
			return "未知";
		}
		long day = (edDate.getTime() - beDate.getTime())
				/ (24 * 60 * 60 * 1000) + 1;
		String year = new DecimalFormat("#.00").format(day / 365f);
		if (year.startsWith(".")) {
			year = "0" + year;
		}
		String age[] = year.split("\\.");
		if (year.contains(".")) {
			return age[0];
		} else {
			return "";
		}

	}

	public static String getTimeStr(Date date, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(date);
	}

	/**
	 * 取两日期之间的天数间隔
	 */
	private static int getDistance(Date date1, Date date2) {
		int distance = 0;
		if (date1 == null || date2 == null) {
			return distance;
		}
		distance = (int) ((date2.getTime() - date1.getTime()) / 1000 / 60 / 60 / 24);
		return distance;
	}

}
