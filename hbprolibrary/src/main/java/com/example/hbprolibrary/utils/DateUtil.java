package com.example.hbprolibrary.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 通用日期工具类
 * @author
 *
 */
public class DateUtil {
	
	/**
	 * 获取当前日期时间
	 * @return
	 */
	public static String getCurrentDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		return format.format(new Date());
	}


	/**
	 *   获取今天的时间   格式:YYYY/MM/DD hh:mm:ss 终端时间
	 * @return
	 */
	public static String   getTodayDateAndTime(){
		SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyyMMddHHmmss");
		Date currentDate  = new Date();
		String dataString = dateFormat.format(currentDate);
		return dataString;
	}

	/**
	 *  获取转换之前的时间
	 * @param formatter    转换时间格式
	 * @param date			时间
	 * @return
     */
	public static String getTransformDate(String formatter,Date  date){
		SimpleDateFormat dateFormat  = new SimpleDateFormat(formatter, Locale.getDefault());
		String data = dateFormat.format(date);
		return data;
	}

	/**
	 * 获取当前自定义格式日期
	 * @return
	 */
	public static String getCurrentDate(String formatter) {
		SimpleDateFormat format = new SimpleDateFormat(formatter, Locale.getDefault());
		return format.format(new Date());
	}

    /**
     * 根据Long型获取时间
     * @param formatter
     * @param time
     * @return
     */
    public static String getTimeByTime(String formatter, long time) {
        if(!TextUtils.isEmpty(formatter) && time > 0l) {
            SimpleDateFormat format = new SimpleDateFormat(formatter, Locale.getDefault());
            return format.format(time);
        }
        return "";
    }

	/**
	 * long时间戳转yyyy-MM-dd
	 * @param time
	 * @return
     */
	public static String LongToStrTime(long time) {
		SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
		Date data = new Date(time*1000);
		String times = sdr.format(data);
		return times;

	}


	/**
     * 根据时间字符串获取Long
     * @param formatter
     * @param time
     * @return
     */
    public static long getLongByTime(String formatter, String time) {
        try {
            if (!TextUtils.isEmpty(formatter) && !TextUtils.isEmpty(time)) {
                SimpleDateFormat format = new SimpleDateFormat(formatter, Locale.getDefault());
                return format.parse(time).getTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
    }

	/**根据旧的日期格式获取新的日期格式时间
	 * @param oldFormat
	 * @param newFormat
	 * @param time
	 * @return
	 */
	public static String getTimeByTime(String oldFormat, String newFormat, String time) {
		String temp = time;
		if(!TextUtils.isEmpty(oldFormat) && !TextUtils.isEmpty(newFormat) && !TextUtils.isEmpty(time)) {
			SimpleDateFormat f1 = new SimpleDateFormat(oldFormat, Locale.getDefault());
			SimpleDateFormat f2 = new SimpleDateFormat(newFormat, Locale.getDefault());	
			try {
				temp = f2.format(f1.parse(time));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return temp;
	}
	
	/**判断是否是今天,并且获取HH:mm
	 * @param time
	 * @return
	 */
	public static String getTimeByTime(String time) {
		if(time.contains(getCurrentDate("yyyy-MM-dd"))) {//如果是今天
			return time.substring(time.indexOf(" "), time.lastIndexOf(":"));
		}
		return getTimeByTime("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss", time);
	}
	
	/**
	 * 获取时间的相差数
	 * @param begintime
	 * @param endtime
	 * @param type 类型常量
	 * 0 - 秒
	 * 1 - 分钟
	 * 2 - 小时
	 * 3 - 天
	 * @return
	 */
	public static int getDifferenceTime(String begintime, String endtime, int type) {
		try {
			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
			long startT = inputFormat.parse(begintime).getTime();
			long endT = inputFormat.parse(endtime).getTime();
			
			long secd = (endT - startT) / (1000); // 共计秒数
			int mint = (int) secd / 60; // 共计分钟数
			int hor = (int) secd / 3600; // 共计小时数
			int day = (int) hor / 24; // 共计天数
			
			switch (type) {
			case 0:
				return (int) secd;
			case 1:
				return mint;
			case 2:
				return hor;
			case 3:
				return day;
			default:
				return 0;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**两个时间相差距离多少天多少小时多少分多少秒 
	 * @param beginTime 格式：1990-01-01 12:00:00
	 * @param endTime 格式：2009-01-01 12:00:00
	 * @return long[] 返回值为：{天, 时, 分, 秒} 
	 */
	public static long[] getDistanceTimes(String beginTime, String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		try {
			one = sdf.parse(beginTime);
			two = sdf.parse(endTime);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long[] times = { day, hour, min, sec };
		return times;
	}

	/**两个时间相差距离多少天多少小时多少分多少秒
	 * @param beginTime 格式：1990-01-01 12:00:00
	 * @param endTime 格式：2009-01-01 12:00:00
	 * @return long[] 返回值为：{天, 时, 分, 秒}
	 */
	public static long[] getDistanceTimes(long beginTime, long endTime) {
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		try {
			long diff;
			if (beginTime < endTime) {
				diff = endTime - beginTime;
			} else {
				diff = beginTime - endTime;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long[] times = { day, hour, min, sec };
		return times;
	}


	/**两个时间相差距离多少天
	 * @param beginTime 格式：1990-01-01
	 * @param endTime 格式：2009-01-01
	 * @return long 返回值为：天 
	 */
	public static int getDistanceDays(String beginTime, String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		Date one;
		Date two;
		long day = 0;
		try {
			one = sdf.parse(beginTime);
			two = sdf.parse(endTime);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Integer.parseInt(String.valueOf(day));
	}
	
	/**两个时间相差距离几个月
	 * @param begintime
	 * @param endtime
	 * @return
	 */
	public static int getDistanceMonths(String begintime, String endtime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		int iMonth = 0;   
        try {
            Calendar objCalendarDate1 = Calendar.getInstance();      
            objCalendarDate1.setTime(sdf.parse(begintime));      
            Calendar objCalendarDate2 = Calendar.getInstance();      
            objCalendarDate2.setTime(sdf.parse(endtime));      
            if (objCalendarDate2.equals(objCalendarDate1)) {
               return 0;
            }
            if (objCalendarDate1.after(objCalendarDate2)) {   
               Calendar temp = objCalendarDate1;      
               objCalendarDate1 = objCalendarDate2;      
               objCalendarDate2 = temp;      
            }      
            if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR)) {
            	iMonth = (objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1.get(Calendar.YEAR)) * 12 + objCalendarDate2.get(Calendar.MONTH) - objCalendarDate1.get(Calendar.MONTH);
            } else {
                iMonth = objCalendarDate2.get(Calendar.MONTH) - objCalendarDate1.get(Calendar.MONTH);
            }
        } catch (Exception e) {      
    	   e.printStackTrace();      
        }      
        return iMonth;
	}
	
	/**获取当年
	 * @return
	 */
	public static int getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	/**获取当月
	 * @return
	 */
	public static int getCurrentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH);
	}
	
	/**获取当日
	 * @return
	 */
	public static int getCurrentDay() {
		return Calendar.getInstance().get(Calendar.DATE);
	}
	
	/**获取当时
	 * @return
	 */
	public static int getCurrentHour() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}
	
	/**获取当分
	 * @return
	 */
	public static int getCurrentMinute() {
		return Calendar.getInstance().get(Calendar.MINUTE);
	}

	/**
	 * 判断查询的时间是否本周内
	 * @param queryTime
	 * @return
     */
	public static boolean isLatestWeek(long queryTime){
		Date queryDate = new Date(queryTime);
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();  //得到日历
		calendar.setTime(currentDate);//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -7);  //设置为7天前
		Date before7days = calendar.getTime();   //得到7天前的时间
		if(before7days.getTime() < queryDate.getTime()){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 判断查询的时间是否在指定月份范围内
	 * @param currentTime
	 * @param queryMonth
	 * @return
     */
	public static  boolean isLatestMonth(long currentTime,int queryMonth){
		// 当前时间
		Date queryDate = new Date(currentTime);
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();  //得到日历
		calendar.setTime(currentDate);//把当前时间赋给日历
		calendar.add(Calendar.MONTH,- queryMonth); //一个月前
		Date before30days = calendar.getTime();   //得到30天前的时间
		if (before30days.getTime() < queryDate.getTime()){
			return  true;
		}else {
			return  false;
		}
	}


	/**
	 * 获取查询时间 在什么月份以内
	 * @param queryTime
	 * @return
     */
	public static int  getQueryTimeWithinMonth(long queryTime){
		Date  queryDate = new Date(queryTime);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(queryDate);
		int month = calendar.get(Calendar.MONTH);
		return month + 1 ;
	}

	/**
	 * 获取查询时间 在什么年月以内
	 * @param queryTime
	 * @return
     */
	public static  String  getQueryTimeWithinYearAndMonth(long queryTime){
		Date  queryDate = new Date(queryTime);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(queryDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		String date = String.format("%1$d%2$s%3$d%4$s", year, "年", month + 1, "月");
		return date;
	}

}
