package com.example.faceandme;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 동현 on 2016-04-13.
 */
public class dailyservice extends Service {



    MyReceiver mr;

    @Override

    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);


        mr = new MyReceiver();
        registerReceiver(mr, filter);

    }
    public dailyservice(){};

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mr);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }




    public class  MyReceiver extends BroadcastReceiver {

        //여기서 처리..
        @Override
        public void onReceive(Context context, Intent intent) {
            // 1분 마다 여기서 리시브가 된다..
            if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                //doSomting
                //여기서 시간을 설정하면 된다.

                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        alarm();
                    }
                };
                TimeZone jst = TimeZone.getTimeZone ("JST");
                Calendar cal = Calendar.getInstance ( jst ); // 주어진 시간대에 맞게 현재 시각으로 초기화된 GregorianCalender 객체를 반환.// 또는 Calendar now = Calendar.getInstance(Locale.KOREA);
                // System.out.println ( cal.get ( Calendar.YEAR ) + "년 " + ( cal.get ( Calendar.MONTH ) + 1 ) + "월 " + cal.get ( Calendar.DATE ) + "일 " + cal.get ( Calendar.HOUR_OF_DAY ) + "시 " +cal.get ( Calendar.MINUTE ) + "분 " + cal.get ( Calendar.SECOND ) + "초 " );

                Timer timer = new Timer();

                if(cal.get(Calendar.MINUTE) ==21)
                    timer.schedule(timerTask,1000); //푸쉬알람 on/off , 데이터전달


            }






        }
    }

    public void alarm(){
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder= new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher));
        builder.setTicker("DailyCheck");
        builder.setContentTitle("DailyCheck");
        builder.setContentText("체크리스트를 작성하세요!");
        builder.setVibrate(new long[]{0, 3000});
        Uri soundUri= RingtoneManager.getActualDefaultRingtoneUri(this,
                RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(soundUri);
        builder.setAutoCancel(true);

        Intent intent = new Intent(this,daily.class);
        PendingIntent pIntent = PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pIntent);
        Notification notification= builder.build();
        manager.notify(1, notification);


    }
}
