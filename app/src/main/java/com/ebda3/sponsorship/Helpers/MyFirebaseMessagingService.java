package com.ebda3.sponsorship.Helpers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ebda3.sponsorship.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by EBDA3 on 8/30/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getData().get("message"));
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getData().get("Notifications"));
        //sendNotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("body"));
        String Type = remoteMessage.getData().get("type");
        String rateID = remoteMessage.getData().get("rateID");
        String ratePoints = remoteMessage.getData().get("ratePoints");
        String rateText = remoteMessage.getData().get("rateText");
        String points = remoteMessage.getData().get("points");
        String pounds = remoteMessage.getData().get("pounds");
        String imgUrl = remoteMessage.getData().get("AD");
        String Title = remoteMessage.getData().get("title");
        String Body = remoteMessage.getData().get("body");
        String ADS = remoteMessage.getData().get("ADS");
        String Notifications = remoteMessage.getData().get("Notifications");
        int countNotifications = Integer.parseInt(remoteMessage.getData().get("countNotifications"));
        createNotification(Title,Type, Body,imgUrl,ADS,Notifications,countNotifications, getApplicationContext(), (int) Math.random(), "545",points,pounds,rateID,ratePoints,rateText );


        /*
        Intent intent1 = new Intent(this,MyCodes.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);
        */


        /*
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle(remoteMessage.getData().get("title"))
                        .setContentText(remoteMessage.getData().get("body"))
                        .setDefaults(Notification.DEFAULT_ALL) // must requires VIBRATE permission
                        .setPriority(NotificationCompat.PRIORITY_HIGH); //must give priority to High, Max which will considered as heads-up notification


        NotificationManager notificationManager =      (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, builder.build());
        */


    }

    private void sendNotification(String messageTitle, String messageBody) {

        long[] pattern = {500,500,500,500,500};

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setLights(Color.BLUE,1,1)
                .setSound(defaultSoundUri);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) Math.random() /* ID of notification */, notificationBuilder.build());


    }




    public  void createNotification(String title, String type, String body, String image_url, String ADS, String Notifications, final int countNotifications, Context context, int notificationsId, String single_id, String points, String pounds, String rateID, String ratePoints, String rateText) {
        Intent notificationIntent;


        SharedPreferences sp=getSharedPreferences("notification", 0);
        SharedPreferences.Editor Ed=sp.edit();
        Ed.putString("NotificationContent", Notifications );
        Ed.putString("ADS", ADS );
        Ed.putInt("countNotifications", countNotifications );
        Ed.commit();

        SharedPreferences sp1=getSharedPreferences("Login", 0);
        SharedPreferences.Editor Ed1=sp1.edit();
        Ed1.putString("points", points );
        Ed1.putString("pounds", pounds );
        try {
            if ( rateID.length() > 0  )
            {


                Ed1.putString("rateID", rateID );
                Ed1.putString("ratePoints", ratePoints   );
                Ed1.putString("rateText", rateText   );

            }
        }
        catch (Exception e)
        {

        }

        Ed1.commit();


        Log.d("tester", String.valueOf(countNotifications));
        //HomeNavPage.notificationNum.setText(String.valueOf(countNotifications));





        long when = System.currentTimeMillis();
        int id = (int) System.currentTimeMillis();


        Bitmap bitmap = getBitmapFromURL(image_url);
        NotificationCompat.BigPictureStyle notifystyle = new NotificationCompat.BigPictureStyle();
        notifystyle.bigPicture(bitmap);
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
        contentView.setImageViewBitmap(R.id.image, bitmap);
        contentView.setTextViewText(R.id.title, title);
        contentView.setTextViewText(R.id.text, body);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo)
                .setStyle(notifystyle)
                .setCustomBigContentView(contentView)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentTitle(title)
                .setContentText(body);

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);




        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.t2);




    }

    public static Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}