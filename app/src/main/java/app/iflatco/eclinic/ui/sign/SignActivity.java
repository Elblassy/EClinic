package app.iflatco.eclinic.ui.sign;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.utils.CustomSharedPref;

public class SignActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign);

        CustomSharedPref pref = new CustomSharedPref(this);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        if (pref.getSessionBoolean("verified")) {
            navController.navigate(R.id.action_mobileNumber_to_registerData);
        }
    }


}
