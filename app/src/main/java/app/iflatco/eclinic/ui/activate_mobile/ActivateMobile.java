package app.iflatco.eclinic.ui.activate_mobile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.databinding.ActivateMobileFragmentBinding;
import app.iflatco.eclinic.utils.CustomSharedPref;

public class ActivateMobile extends Fragment {

    private static final String TAG = "ActivateMobilePhone";

    private ActivateMobileViewModel mViewModel;
    private ActivateMobileFragmentBinding binding;
    private String mVerificationId;
    private FirebaseAuth mAuth;
    private CustomSharedPref pref;
    View view;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();
            Log.d(TAG, "onVerificationCompleted: " + phoneAuthCredential.getSignInMethod());
            Log.d(TAG, "onVerificationCompleted: " + phoneAuthCredential.getProvider());
            Log.d(TAG, "onVerificationCompleted: " + phoneAuthCredential.getSmsCode());

            if (code != null) {
                Log.e("Code", code);
                binding.code.setValue(code);
                //verifying the code
                verifyVerificationCode(code);
            }else {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.d(TAG, "onVerificationFailed: " + e.getMessage());
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Log.d(TAG, "onCodeSent: " + s);
            mVerificationId = s;
        }
    };


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ActivateMobileFragmentBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(ActivateMobileViewModel.class);
        pref = new CustomSharedPref(getContext());

        mViewModel.mCallbacks.setValue(mCallbacks);
        Log.d(TAG, "mobile: " + pref.getSessionValue("mobile"));
        mViewModel.sendVerificationCode(pref.getSessionValue("mobile"));
        mAuth = FirebaseAuth.getInstance();
        mAuth.useAppLanguage();

        binding.verify.setOnClickListener(v -> {
            view = v;
            //Navigation.findNavController(v).navigate(R.id.action_activateMobile_to_registerData);
            if (!binding.code.getValue().isEmpty()) {
                verifyVerificationCode(binding.code.getValue());
            } else {
                Toast.makeText(getContext(), "Please enter the code", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }


    private void verifyVerificationCode(String code) {
        try {
            //creating the credential
            Log.d(TAG, "verifyVerificationCode: " + mVerificationId);
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

            //signing the user
            signInWithPhoneAuthCredential(credential);
        } catch (Exception e) {
            Log.d(TAG, "verifyVerificationCode error: " + e.getMessage());
        }

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {

                    if (task.isSuccessful()) {
                        pref.setPrefVerified(true);
                        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_activateMobile_to_registerData);
                        Log.d(TAG, "signInWithPhoneAuthCredential: " + "success");
                    } else {
                        //verification unsuccessful.. display an error message
                        String message = "Somthing is wrong, we will fix it soon...";

                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid code entered...";
                        }
                        Log.d(TAG, "signInWithPhoneAuthCredential: " + message);
                    }
                });
    }


}
