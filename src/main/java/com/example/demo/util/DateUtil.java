package com.example.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static String parseDate2Str(Date date,String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	public static String getCurrent2Str(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(Calendar.getInstance().getTime());
	}
	
	public static long getCurrentSecond() {
		return Calendar.getInstance().getTimeInMillis() / 1000;
	}
	
	public static Date add(int calendarType,int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendarType, num);
		return calendar.getTime();
	}

	public static Date add(Date date,int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, num);
		return calendar.getTime();
	}

	/**
	 * 获取今天开始时间
	 * @return
	 */
	public static String getTodayStartTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(cal.getTime());
	}

	/**
	 * 大于今天,则返回true
	 * @param compareDate
	 * @param pattern
	 * @return
	 */
	public static Boolean compareNow(String compareDate, String pattern){
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return Calendar.getInstance().getTime().before(format.parse(compareDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Date stringToDate(String date, String pattern) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.parse(date);
	}
}
