package utils;


import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {


    /**
     * sp存储的工具类
     */
    public static void setStringValue(Context context,String key,String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("infor",Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key,value).commit();

    }

    public static String getStringValue(Context context,String key) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("infor",Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"0");

    }

    public static void setIntValue(Context context,String key,int value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("infor",Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key,value).commit();

    }

    public static int getIntValue(Context context,String key) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("infor",Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key,0);

    }

    public static void setLongValue(Context context,String key,long value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("infor",Context.MODE_PRIVATE);
        sharedPreferences.edit().putLong(key,value).commit();

    }

    public static long getLongValue(Context context,String key) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("infor",Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key,0);

    }



}
