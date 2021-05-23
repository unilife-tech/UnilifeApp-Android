package unilife.com.unilife;


import android.content.Context;
import android.content.SharedPreferences;
import unilife.com.unilife.utils.AppController;

public class PreferenceFile {

    private static PreferenceFile mInstance;
    private static SharedPreferences sharedPreferences;

    public PreferenceFile(){

            sharedPreferences = AppController.getSharedPreferences();
    }

    public static PreferenceFile getInstance(){

        if(mInstance!=null){
            return mInstance;
        }else{
           return mInstance=new PreferenceFile();
        }
    }
    public  void saveData(Context context, String Key, String Data) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(Key,Data);
        editor.apply();
    }


    public String getPreferenceData(Context context, String Key) {
        SharedPreferences sharedPreferences=  AppController.getSharedPreferences();
        return sharedPreferences.getString(Key,null);
    }

    public  void logout() {

        AppController.getSharedPreferences().edit().clear().apply();
        mInstance = null;
    }
}
