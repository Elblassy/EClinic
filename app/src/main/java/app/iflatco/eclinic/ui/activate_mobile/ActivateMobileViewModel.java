package app.iflatco.eclinic.ui.activate_mobile;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import app.iflatco.eclinic.data.ClientServer;
import app.iflatco.eclinic.models.DataModel;
import app.iflatco.eclinic.models.ResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivateMobileViewModel extends AndroidViewModel {

    MutableLiveData<String> mVerificationId;
    MutableLiveData<PhoneAuthProvider.OnVerificationStateChangedCallbacks> mCallbacks;
    MutableLiveData<Boolean> success;
    MutableLiveData<ResponseModel> responseModelMutableLiveData;

    private static final String TAG = "ActivateMobileViewModel";
    private Application application;


    public ActivateMobileViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        mCallbacks = new MutableLiveData<>();
        mVerificationId = new MutableLiveData<>();
        success = new MutableLiveData<>();
        responseModelMutableLiveData = new MutableLiveData<>();
    }


    //the method is sending verification code
    public void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                 mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks.getValue());
        Log.e("Mobile", mobile);
    }

    void checkNumber(HashMap<String, String> sign) {
        ClientServer.getINSTANCE().signIn(sign).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.code() == 200) {
                    responseModelMutableLiveData.setValue(response.body());
                } else if (response.code() == 401) {
                    responseModelMutableLiveData.setValue(new ResponseModel(false, "", new DataModel()));
                    Log.d(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}
