package com.example.myapplication;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NotificationListener extends NotificationListenerService {

    boolean listening;
    private static NotificationListener single_instance = null;

    // variable of type String

    // private constructor restricted to this class itself


    // static method to create instance of Singleton class
    public static NotificationListener getInstance()
    {
        if (single_instance == null)
            single_instance = new NotificationListener();

        return single_instance;
    }
    public NotificationListener(){
        this.listening = false;
    }

    public boolean getListening(){
        return this.listening;
    }

    @Override
    public void onListenerConnected(){
        this.listening = true;


    }



    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }





}