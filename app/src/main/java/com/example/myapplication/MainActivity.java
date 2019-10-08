package com.example.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean checkCategory(PackageManager pm, StatusBarNotification notif)  {
        ApplicationInfo app = null;
        try {
            app = pm.getApplicationInfo(notif.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Log.i("element", Integer.toString(app.category) + " " + pm.getInstallerPackageName(notif.getPackageName()) + " " + app.packageName);
        String name = pm.getInstallerPackageName(notif.getPackageName());
        if(name == null || !name.equals("com.android.vending")){
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public StatusBarNotification openApp(NotificationListener listener, Context context){
        StatusBarNotification[] notificationsArray = listener.getActiveNotifications();
        Log.i("myTag", Integer.toString(notificationsArray.length));
        ArrayList<StatusBarNotification> notifications = new ArrayList<>(Arrays.asList(notificationsArray));


        Log.i("myTag", "asdfasdf11111111111");
        Log.i("elements2", notifications.toString());
        PackageManager packageManager = context.getPackageManager();
        notifications.removeIf(s -> checkCategory(packageManager, s));
        Log.i("elements", notifications.toString());
        if(notifications.size() == 1){
            return notifications.get(0);
        }else if(notifications.size() == 0){
            return null;
        }
        Comparator<StatusBarNotification> cmp = Comparator.comparing(StatusBarNotification::getPostTime);

        StatusBarNotification notif = Collections.max(notifications, cmp);

        Log.i("myTag", "asdfasdf");
        return notif;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("myTag", "asdf");
        NotificationListener listener = NotificationListener.getInstance();

        Context context = getApplicationContext();
        StatusBarNotification notif = openApp(listener, context);
        Log.i("length", notif.toString());
        if(notif != null) {
            Log.i("myTag", notif.getPackageName());

            PendingIntent launchIntent = notif.getNotification().contentIntent;

            if (launchIntent != null) {
                try {
                    launchIntent.send();//null pointer check in case package name was not found
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
            }

        }
        Log.i("closed", "Closed");


    }





}
