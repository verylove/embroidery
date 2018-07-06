package cn.wind.common.utils;

import java.util.Random;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/6 14:53
 * @Description:
 */
public class MathUtil {
    /**
     *
     * @Description: 产生随机的六位数
     * @param @return
     * @return String
     */
    public static String getSix(){
        Random rad=new Random();
        String result  = 100000+rad.nextInt(899999) +"";

        return result;
    }

    /**
     *
     * @Description: 获得区间随机数
     * @param @param max
     * @param @return
     */
    public static Integer getRandomNum(int min,int  max){
        int Min = min;
        int Max = max;
        int result = Min + (int)(Math.random() * ((Max - Min) + 1));
        return result;
    }

    public static String formatTimeForHMS(long time){
        long hour = time/3600;
        long minute = time%3600/60;
        long second = (time%3600)%60;
        String result = String.format("%02d",hour)+":"+String.format("%02d",minute)+":"+String.format("%02d",second);
        return result;
    }
}
