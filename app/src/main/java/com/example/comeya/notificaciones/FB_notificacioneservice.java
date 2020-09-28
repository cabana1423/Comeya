package com.example.comeya.notificaciones;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.comeya.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.loopj.android.http.AsyncHttpClient.log;

public class FB_notificacioneservice extends FirebaseMessagingService {
    public static final String TAG="MyFirebaseMsgService";

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        log.d(TAG,"onNewToken"+s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        log.d(TAG,"menaje recibido");
        RemoteMessage.Notification notification=remoteMessage.getNotification();
        String title=notification.getTitle();
        String msg=notification.getBody();
        senNotification(title,msg);
    }

    private void senNotification(String title, String msg) {
        Intent intent=new Intent(this, Notificaciones.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this, FB_Mynotification.NOTIFICATION_ID,intent,PendingIntent.FLAG_ONE_SHOT);

        FB_Mynotification FBMynotification =new FB_Mynotification(this, FB_Mynotification.CHANNEL_ID_NOTIFICATIONS);
        FBMynotification.build(R.drawable.ic_launcher_background,title,msg,pendingIntent);
        FBMynotification.addChannel("notificaciones",NotificationManager.IMPORTANCE_DEFAULT);
        FBMynotification.createChannelGroup(FB_Mynotification.CHANNEL_GROUP_GENERAL,R.string.notification_channel_group_general);
        FBMynotification.show(FB_Mynotification.NOTIFICATION_ID);
    }



    /*private void generarnotificacion(String body, String title) {
        Intent intent=new Intent(this,notificaciones.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_ONE_SHOT);
        Uri sonidoUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(sonidoUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if(NOTIFICATION_ID>1073741245){
            NOTIFICATION_ID=0;
        }
        notificationManager.notify(NOTIFICATION_ID++,notificationBuilder.build());
    }*/
}
