package com.shokievarun.kmstores.ViewHolder;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.shokievarun.kmstores.LoginActivity;
import com.shokievarun.kmstores.R;

public class PushNotification extends FirebaseMessagingService {


/*
*foreground notification icon  blank
* notification sound not coming
* also set color icon it is in black and white
*
* create pdf in AdminOrdersUserProductsActivity (try making screenshot as pdf)
* categories fix it try it with listview(reference flipkart filters)

 */

    private static final String TAG = "PushNotification";
    private static final String CHANNEL_ID ="101" ;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }

    private void showNotification(String title,String message){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Bitmap rawBitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notify_km)
                .setLargeIcon(rawBitmap)
                .setContentTitle(title)

                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
        builder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText(message));
    }
}
