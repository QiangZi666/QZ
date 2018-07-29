package com.qianfeng.util;

import com.qianfeng.common.DateEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间工具类
 */
public class TimeUtil {
public static final String Date_FORMAT="yyyy-MM-dd";//默认时间格式
    /**
     * 获取昨天的默认格式的时间
     */
    public static String getYesterday(){
        return getYesterday(Date_FORMAT);
    }

    /**
     * 获取昨天的指定格式的时间
     * @param pattern
     * @return
     */
    private static String getYesterday(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        return sdf.format(calendar.getTime());
    }
    /**
     * 判断时间是佛合法,true:有效
     */
    public static boolean isRunningValidate(String date){
        Matcher matcher = null;
        boolean res = false;
        String reg = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}";
        if (date!=null&&!date.isEmpty()){
            Pattern pattern = Pattern.compile(reg);
            matcher = pattern.matcher(date);
        }
        if (matcher!=null){
            res = matcher.matches();
        }
        return res;
    }
    /**
     * 将指定的时间戳转换成字符串的日期
     */
    public static String parseLong2String(long intput){
        return parseLong2String(intput,Date_FORMAT);

    }

    private static String parseLong2String(long intput, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(intput);
        return new SimpleDateFormat(pattern).format(calendar.getTime());
    }

    /**
     * 将字符串的日期转换成long类型的时间戳
     * @param intput
     * @return
     */
    private static long parseString2Long(String intput) throws ParseException {
        return parseString2Long(intput,Date_FORMAT);
    }

    private static long parseString2Long(String intput, String pattern) throws ParseException {
        Date date = null;
        date = new SimpleDateFormat(pattern).parse(intput);
        return date==null?0:date.getTime();
    }
    /**
     * 根据时间戳和type来获取对应值
     *
     */
    public static int getDateInfo(long time,DateEnum type){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        if (DateEnum.YEAR.equals(type)){
            return calendar.get(Calendar.YEAR);
        }
        if (DateEnum.SEASON.equals(type)){
            int month = calendar.get(Calendar.MONTH)+1;
            if (month%3==0){
                return month/3;
            }
            return month/3+1;
        }
        if (DateEnum.MONTH.equals(type)){
            int month =calendar.get(Calendar.MONTH)+1;
            return month;
        }
        if (DateEnum.WEEK.equals(type)){
            int month = calendar.get(Calendar.WEEK_OF_YEAR)+1;
            return month;
        }
        if (DateEnum.DAY.equals(type)){
            return calendar.get(Calendar.DAY_OF_MONTH);
        }
        if (DateEnum.HOUR.equals(type)){
            return calendar.get(Calendar.HOUR_OF_DAY);
        }
        throw new RuntimeException("该类型暂不支持时间信息获取:type:"+type.typeName);

    }
    /**
     * 根据时间戳获取当天所在周的第一天
     */
    public static long getFirstDayOfWeek(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.DAY_OF_WEEK,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis();
    }

    public static void main(String[] args) {
        System.out.println(getDateInfo(15372000000253L,DateEnum.DAY));
    }
//    public static void main(String[] args) throws ParseException {
//        System.out.println(parseLong2String(1532534400000L));
//        System.out.println(parseString2Long("2018-07-26"));
//    }






}
