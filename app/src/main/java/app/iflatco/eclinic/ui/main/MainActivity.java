package app.iflatco.eclinic.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.data.ClientServer;
import app.iflatco.eclinic.databinding.ActivityMainBinding;
import app.iflatco.eclinic.models.ResponseModel;
import app.iflatco.eclinic.utils.CustomSharedPref;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    CustomSharedPref pref;
    ActivityMainBinding binding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pref = new CustomSharedPref(this);
        HashMap<String, String> fbToken = new HashMap<>();

        Log.d(TAG, "tokenId: " + pref.getSessionValue("tokenId"));
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnSuccessListener(MainActivity.this, instanceIdResult -> {
                    String fcmToken = instanceIdResult.getToken();
                    Log.e("fcmToken", fcmToken);
                    if (!fcmToken.equals("")) {
                        fbToken.put("new_token", fcmToken);
                        sendFbToken(fbToken);
                    }
                });

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    private void sendFbToken(HashMap<String, String> fbToken) {
        ClientServer.getINSTANCE().updateFbToken("Bearer " + pref.getSessionValue("tokenId").trim(), fbToken).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    Log.d(TAG, "onResponse: " + response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}
