package in.infocruise.techsupport.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

import in.infocruise.techsupport.LoginActivity;
import in.infocruise.techsupport.R;

public class PhoneCallStateReceiver extends BroadcastReceiver {
    public static boolean isListening = false;
    //private NotificationManager mNotifyManager;
    // Create a constant variable for the notification ID
    //private static final int NOTIFICATION_ID = 0;


    @Override
    public void onReceive(final Context context, final Intent intent) {

        TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);

                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE:
                        Log.d("Notification","come here first");

                        //sendNotification(context, "CALL_STATE_IDLE");
                        Toast.makeText(context, "CALL_STATE_IDLE", Toast.LENGTH_SHORT).show();
                        break;
                    case TelephonyManager.CALL_STATE_RINGING:
                        //sendNotification(context, "CALL_STATE_RINGING");

                        Toast.makeText(context, "CALL_STATE_RINGING", Toast.LENGTH_SHORT).show();
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                       // sendNotification(context, "CALL_STATE_OFFHOOK");

                        Toast.makeText(context, "CALL_STATE_OFFHOOK", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

        };

       // mNotifyManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);


        if(!isListening) {
            assert mTelephonyManager != null;
            mTelephonyManager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
            isListening = true;
            //showNotification(context);
        }
        if (intent.getAction() != null) {
            Objects.equals(intent.getAction(), "android.net.phone.phone_state_changed");
        }

        Intent intent1 = new Intent(context.getApplicationContext(), LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent1, 0);

        Notification notification = new Notification.Builder(context.getApplicationContext())
                .setSmallIcon(R.drawable.ic_open_app)
                .setContentTitle("Ticket Notification")
                .setContentText("Please create ticket")
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(0, notification);
    }

//    private void showNotification(Context context) {
//        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
//                new Intent(context, LoginActivity.class), 0);
//
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(context)
//                        .setSmallIcon(R.drawable.ic_open_app)
//                        .setContentTitle("My notification")
//                        .setContentText("Hello World!");
//        mBuilder.setContentIntent(contentIntent);
//        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
//        mBuilder.setAutoCancel(true);
//        NotificationManager mNotificationManager =
//                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(1, mBuilder.build());
//
//    }


//    public void sendNotification(Context context, String message) {
//
//
//        Intent notificationIntent = new Intent(context, LoginActivity.class);
//
//        PendingIntent notificationPendingIntent = PendingIntent.getActivity(context,NOTIFICATION_ID,
//                notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
//
//        // Create and Instantiate the Notification Builder
//        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(context)
//                .setContentTitle("You've been notified!")
//                .setContentText(message)
//                .setSmallIcon(R.drawable.ic_open_app)
//                .setContentIntent(notificationPendingIntent);
//
//        Notification myNotification = notifyBuilder.build();
//        context.getSystemService(NOTIFICATION_SERVICE);
//        mNotifyManager.notify(NOTIFICATION_ID,myNotification);
//
//
//    }

}
