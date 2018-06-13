package com.watheq.watheq.notifications.firebaseNotification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.watheq.watheq.MainActivity;
import com.watheq.watheq.R;
import com.watheq.watheq.SplashScreen;
import com.watheq.watheq.base.BaseChatActivity;
import com.watheq.watheq.thread.ThreadActivity;
import com.watheq.watheq.utils.App;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.v(TAG, remoteMessage.toString());
        if (remoteMessage.getData().get("type").equals("NotAcceptedRequest"))
            return;
        Activity currentActivity = ((App) getApplicationContext()).getCurrentActivity();
        if (currentActivity instanceof BaseChatActivity && remoteMessage.getData().get("type").equals("message")) {

        } else if (remoteMessage.getData().get("type").equals("AcceptedRequest")) {
            showBreakingNewsAlert(remoteMessage);
        } else if (currentActivity != null) {
            sendLowNotification(remoteMessage);
        } else
            sendNotification(remoteMessage);
//        }
    }

    private void showBreakingNewsAlert(RemoteMessage remoteMessage) {
        Intent broadCastIntent = new Intent();
        broadCastIntent.putExtra("orderId", remoteMessage.getData().get("orderId"));
        broadCastIntent.putExtra("senderId", remoteMessage.getData().get("lawyerId"));
        broadCastIntent.setAction("AcceptedOrder");
//        broadCastIntent.putExtra(Keys.SendViaIntentKeys.TITLE_PUSH, remoteMessage.getData().get("MessageTitle"));
//        broadCastIntent.setAction(Keys.SendViaIntentKeys.REPORT_PUSH);
        sendBroadcast(broadCastIntent);

    }

    private void sendNotification(RemoteMessage remoteMessage) {
        Intent mainIntent = new Intent(this, SplashScreen.class);
        mainIntent.putExtra("Push", true);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(SplashScreen.class);
        stackBuilder.addNextIntent(mainIntent);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = null;
        if (remoteMessage.getData().get("type").equals("message")) {
            intent = new Intent(this, ThreadActivity.class);
            intent.putExtra("orderId", remoteMessage.getData().get("id"));
            intent.putExtra("displayName", remoteMessage.getData().get("title"));
            intent.putExtra("senderId", remoteMessage.getData().get("senderId"));
        } else if (remoteMessage.getData().get("type").equals("AcceptedRequest")) {
            intent = new Intent(this, ThreadActivity.class);
            intent.putExtra("orderId", remoteMessage.getData().get("orderId"));
            intent.putExtra("senderId", remoteMessage.getData().get("lawyerId"));
        } else if (remoteMessage.getData().get("type").equals("CloseRequest")) {
            intent = new Intent(this, ThreadActivity.class);
            intent.putExtra("orderId", remoteMessage.getData().get("orderId"));
            intent.putExtra("senderId", remoteMessage.getData().get("lawyerId"));
            intent.putExtra("closedOrder", true);
        }
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "M_CH_ID")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("content"))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NotificationID.getID(), notificationBuilder.build());
    }

    private void sendLowNotification(RemoteMessage remoteMessage) {
//        Intent mainIntent = new Intent(this, SplashScreen.class);
//        mainIntent.putExtra("Push", true);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(SplashScreen.class);
//        stackBuilder.addNextIntent(mainIntent);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = null;
        if (remoteMessage.getData().get("type").equals("message")) {
            intent = new Intent(this, ThreadActivity.class);
            intent.putExtra("orderId", remoteMessage.getData().get("id"));
            intent.putExtra("displayName", remoteMessage.getData().get("title"));
            intent.putExtra("senderId", remoteMessage.getData().get("senderId"));
        } else if (remoteMessage.getData().get("type").equals("AcceptedRequest")) {
            intent = new Intent(this, ThreadActivity.class);
            intent.putExtra("orderId", remoteMessage.getData().get("orderId"));
            intent.putExtra("senderId", remoteMessage.getData().get("lawyerId"));
        } else if (remoteMessage.getData().get("type").equals("CloseRequest")) {
            intent = new Intent(this, ThreadActivity.class);
            intent.putExtra("orderId", remoteMessage.getData().get("orderId"));
            intent.putExtra("senderId", remoteMessage.getData().get("lawyerId"));
            intent.putExtra("closedOrder", true);
        }
//        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent,
                PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "M_CH_ID")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("content"))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_LOW)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NotificationID.getID(), notificationBuilder.build());
    }

}