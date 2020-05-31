package app.iflatco.eclinic.ui.page_viewer.payment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import app.iflatco.eclinic.databinding.PaymentFragmentBinding;
import app.iflatco.eclinic.utils.CustomSharedPref;

public class Payment extends Fragment {
    private static final String TAG = "Payment";

    private PaymentViewModel mViewModel;
    private int id;
    private CustomSharedPref pref;
    private PaymentFragmentBinding binding;

    public static Payment newInstance(Context context) {
        return new Payment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = PaymentFragmentBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(PaymentViewModel.class);

        pref = new CustomSharedPref(getContext());

        binding.confirm.setOnClickListener(v -> {
            pref.setPrefPending(false);
            mViewModel.confirmAppointment(pref.getSessionValue("tokenId"), id);
            Log.d(TAG, "onCreateView: " + pref.getSessionBoolean("pending"));
        });

        return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " + id);
        pref.setPrefSlotId(id);
        pref.setPrefPending(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onResume: " + "Here");
        if (pref.getSessionBoolean("pending")) {
            mViewModel.cancelAppointment(pref.getSessionValue("tokenId"), pref.getSlotId());
        }
    }


    public void setId(int id) {
        this.id = id;
    }
}
