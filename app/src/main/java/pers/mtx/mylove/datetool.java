package pers.mtx.mylove;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class datetool {
    public static String doit(String inoutday) throws ParseException {

        String rq1="2020-02-24";
        String rq2=inoutday;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = formatter.parse(rq1); // 将得到的日期字符串转换为日期类型
        Date d2 = formatter.parse(rq2);
        long day=Math.abs(d2.getTime()-d1.getTime());//得到的两个日期的毫秒数大小
        day = (long) (day / (1000 * 60 * 60 * 24));
        return ""+day;
    }
    public static String mtx(String newDays){
        String week = null;
        String day = null;
        int newdayss = Integer.parseInt(newDays);
        int res = newdayss % 7;
        switch (res){
            case 0: day="星期一";break;
            case 1: day="星期二";break;
            case 2: day="星期三";break;
            case 3: day="星期四";break;
            case 4: day="星期五";break;
            case 5: day="星期六";break;
            case 6: day="星期天";break;
        }
        int res2 = newdayss/7+1;
        week = "第"+res2+"周";
        return week+day;
    }
    public static Map<String,Integer> mtx2(String newDays){
        Map<String,Integer> map=new HashMap<>();
        int newdayss = Integer.parseInt(newDays);
        int res = newdayss % 7+1;

        int res2 = newdayss/7+1;
        map.put("day",res);
        map.put("week",res2);
        return map;
    }

    public static String newdays (Date date){
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        String newdays = null;
        try {
            newdays = datetool.doit(simpleDateFormat2.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newdays;
    }
}