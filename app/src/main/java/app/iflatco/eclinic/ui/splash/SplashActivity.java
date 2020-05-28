package app.iflatco.eclinic.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.ui.main.MainActivity;
import app.iflatco.eclinic.ui.sign.SignActivity;
import app.iflatco.eclinic.utils.CustomSharedPref;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        CustomSharedPref pref = new CustomSharedPref(this);
        FirebaseAuth.getInstance().getCurrentUser();
        new Handler().postDelayed(() -> {
            if (pref.getSessionBoolean("signUp")) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();

            } else {
                startActivity(new Intent(SplashActivity.this, SignActivity.class));
                finish();
            }
        }, 200);
    }
}
