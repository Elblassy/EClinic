package app.iflatco.eclinic.ui.ring;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import app.iflatco.eclinic.databinding.ActivityRingBinding;
import app.iflatco.eclinic.ui.chat.Chat;
import app.iflatco.eclinic.utils.CustomSharedPref;

public class RingActivity extends AppCompatActivity {

    private Vibrator vib;
    private MediaPlayer mp;
    private ActivityRingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRingBinding.inflate(getLayoutInflater());

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        } else {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }
        setContentView(binding.getRoot());

        CustomSharedPref pref = new CustomSharedPref(this);

        binding.userName.setText(getIntent().getStringExtra("user_name"));
        binding.ring.setOnClickListener(v -> {
            startActivity(new Intent(RingActivity.this, Chat.class)
                    .putExtra("room_id", getIntent().getStringExtra("room_id")));
            finish();
        });
        setRingTone();
    }

    private void setRingTone() {
        Uri defaultRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(getApplicationContext(), RingtoneManager.TYPE_RINGTONE);
        mp = MediaPlayer.create(this, defaultRingtoneUri);
        vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        long[] pattern = {0, 500, 1000};
        vib.vibrate(pattern, 0);
        mp.start();
    }


    @Override
    protected void onDestroy() {
        mp.stop();
        vib.cancel();
        super.onDestroy();
    }
}