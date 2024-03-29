package app.iflatco.eclinic.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.ui.appointment_patient.MyAppointment;
import app.iflatco.eclinic.ui.ring.RingActivity;
import app.iflatco.eclinic.utils.CustomSharedPref;


public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    private final String ADMIN_CHANNEL_ID = "eClinic";
    NotificationManager notificationManager;
    private static final String TAG = "MyFirebaseInstanceIDSer";


    public MyFirebaseInstanceIDService() {
    }


    @Override
    public void onNewToken(@NotNull String s) {
        super.onNewToken(s);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("MyFireBaseMessaging", "getInstanceId failed", task.getException());
                        return;
                    }
                    // Get new Instance ID token
                    String token = task.getResult().getToken();
                    Log.e("My Token", token);
                });
    }

    @Override
    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = remoteMessage.getData().get("title");
        int notificationId = new Random().nextInt(60000);
        CustomSharedPref pref = new CustomSharedPref(this);

        assert title != null;
        if (title.equals("Call")) {
            String roomId = String.valueOf(remoteMessage.getData().get("room_id"));
            String userName = String.valueOf(remoteMessage.getData().get("user_name"));

            startActivity(new Intent(this, RingActivity.class).putExtra("room_id", roomId)
                    .putExtra("user_name", userName).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            return;
        }

        final Intent intent = new Intent(this, MyAppointment.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //Setting up Notification channels for android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels();
        }


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(getResources().getColor(R.color.colorAccent))//a resource for your custom small icon
                .setContentTitle(remoteMessage.getData().get("title")) //the "title" value you sent in your notification
                .setContentText(remoteMessage.getData().get("message")) //ditto
                .setAutoCancel(true)  //dismisses the notification on click
                .setSound(defaultSoundUri)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels() {

        CharSequence adminChannelName = "eClinic";
        String adminChannelDescription = "Receive Notification";

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(getResources().getColor(R.color.colorAccent));
        adminChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        adminChannel.setShowBadge(true);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }

    }
}