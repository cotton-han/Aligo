package com.example.ds.aligo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service {
    NotificationManager Noti_M;
    ServiceThread thread;
    Notification Noti ;
    NotificationCompat.Builder builder;
    String str;
    boolean vibrate;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Noti_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();

        str = intent.getStringExtra("ringtone");
        vibrate = intent.getBooleanExtra("vibrate",false);
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        Log.d("service","onCreate 실행");
        str="";
        vibrate = false;
    }

    public void onDestroy() {
        Log.d("service","onDestroy 실행");
        thread.stopForever();
        thread = null;//쓰레기 값을 만들어서 빠르게 회수하라고 null을 넣어줌.
    }

    class myServiceHandler extends Handler {
        public void handleMessage(android.os.Message msg) {
            if (Build.VERSION.SDK_INT >=26){
                NotificationChannel mChannel = new NotificationChannel("alligo","alligo",NotificationManager.IMPORTANCE_DEFAULT);
                Noti_M.createNotificationChannel(mChannel);
                builder = new NotificationCompat.Builder(MyService.this, mChannel.getId());
            }
            else{
                builder = new NotificationCompat.Builder(MyService.this);
            }

            Intent intent = new Intent(MyService.this, HomeActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this,0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

            Uri soundUri = Uri.parse(str);

            Noti = builder.setContentTitle("스타벅스")
                    .setContentText("삼성카드 taptapO, 썸타는 우리카드 사용 가능")
                    .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.intro))
                    .setSmallIcon(R.drawable.intro)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setVisibility(Notification.VISIBILITY_PUBLIC)
                    .setSound(soundUri)
                    .build();

            if(vibrate){
                Noti.flags = Notification.DEFAULT_VIBRATE;
            }

            //알림 소리를 한번만 내도록
            Noti.flags = Notification.FLAG_ONLY_ALERT_ONCE;

            //확인하면 자동으로 알림이 제거 되도록
            Noti.flags = Notification.FLAG_AUTO_CANCEL;

            Noti_M.notify( 777 , Noti);

            stopSelf();
        }
    }
}
