package unilife.com.unilife.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


public class AppController extends MultiDexApplication {

    public static AppController mInstance;
    public static Context mContext;
    public static final String TAG = AppController.class.getSimpleName();
    private  SharedPreferences sharedPreferences;


    @Override
    public void onCreate() {
        super.onCreate();

        mInstance=this;
        mContext=getApplicationContext();

        FontsOverride.setDefaultFont(getApplicationContext(),"DEFAULT", "Arcon-Regular.otf");
        FontsOverride.setDefaultFont(getApplicationContext(),"DEFAULT_BOLD", "Stawix-SoinSansNeue-Bold.otf");
        FontsOverride.setDefaultFont(getApplicationContext(),"SERIF", "Arcon-Regular.otf");
        FontsOverride.setDefaultFont(getApplicationContext(),"SANS_SERIF", "Arcon-Regular.otf");
        FontsOverride.setDefaultFont(getApplicationContext(),"MONOSPACE", "Stawix-SoinSansNeue-Bold.otf");

        getFirebaseToken();

    }


    public static AppController getmInstance(){
        return mInstance;
    }

    public static Context getContext(){
        return mContext;
    }

    public static SharedPreferences getSharedPreferences(){
        return getContext().getSharedPreferences("Unilife", 0);
    }

    public static SharedPreferences getIdPrefs() {
        return getContext().getSharedPreferences("UniLifeToken", 0);
    }


    private void getFirebaseToken()
    {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("GetToken", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        sharedPreferences = AppController.getIdPrefs();
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("token",token);
                        editor.apply();

                        Log.e("MyToken",token);
                    }
                });
    }

}