package app.iflatco.eclinic.ui.activate_mobile;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ActivateMobileViewModel extends AndroidViewModel {

    MutableLiveData<String> mVerificationId;
    MutableLiveData<PhoneAuthProvider.OnVerificationStateChangedCallbacks> mCallbacks;
    MutableLiveData<Boolean> success;

    private static final String TAG = "ActivateMobileViewModel";
    private Application application;


    public ActivateMobileViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        mCallbacks = new MutableLiveData<>();
        mVerificationId = new MutableLiveData<>();
        success = new MutableLiveData<>();
    }


    //the method is sending verification code
    public void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+2" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks.getValue());
        Log.e("Mobile", mobile);
    }



}
