package www.knowledgeshare.com.knowledgeshare.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {
    private static SharedPreferences mSharedPreferences;

    public static void putString(Context context, String key, String value){
        mSharedPreferences =  context.getSharedPreferences(MyContants.FILENAME,Context.MODE_PRIVATE);
        mSharedPreferences.edit().putString(key,value).commit();
    }

    public static String getString(Context context,String key,String defValue){
        mSharedPreferences=context.getSharedPreferences(MyContants.FILENAME,Context.MODE_PRIVATE);
        return mSharedPreferences.getString(key,defValue);
    }

    public static void putBoolean(Context context,String key,boolean value){
        mSharedPreferences=context.getSharedPreferences(MyContants.FILENAME,Context.MODE_PRIVATE);
        mSharedPreferences.edit().putBoolean(key,value).commit();
    }

    public static boolean getBoolean(Context context,String key,boolean defValue){
        mSharedPreferences=context.getSharedPreferences(MyContants.FILENAME,Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(key,defValue);
    }

    public static void putInt(Context context,String key,int value){
        mSharedPreferences=context.getSharedPreferences(MyContants.FILENAME,Context.MODE_PRIVATE);
        mSharedPreferences.edit().putInt(key,value).commit();
    }

    public static int getInt(Context context,String key,int defValue){
        mSharedPreferences=context.getSharedPreferences(MyContants.FILENAME,Context.MODE_PRIVATE);
        return mSharedPreferences.getInt(key,defValue);
    }


}
