package app.iflatco.eclinic.ui.prescription;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.databinding.ActivityPrescriptionBinding;
import app.iflatco.eclinic.utils.CustomSharedPref;

public class PrescriptionActivity extends AppCompatActivity {
    private static final String TAG = "PrescriptionActivity";

    ActivityPrescriptionBinding binding;
    PrescriptionViewModel viewModel;
    CustomSharedPref pref;
    AlertDialog.Builder builder;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.progress_dialog);
        dialog = builder.create();
        dialog.setCancelable(false);

        if (!dialog.isShowing()) {
            dialog.show();
        }

        viewModel = new ViewModelProvider(this).get(PrescriptionViewModel.class);
        pref = new CustomSharedPref(this);

        Log.d(TAG, "onCreate: " + getIntent().getStringExtra("room_id"));
        viewModel.getPrescription(pref.getSessionValue("tokenId"), getIntent().getStringExtra("room_id"));

        viewModel.responseMutableLiveData.observe(this, prescriptionResponse -> {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (prescriptionResponse.getStatus()) {
                binding.diagnosed.setText(prescriptionResponse.getData().getDiagnose());
                binding.prescription.setText(prescriptionResponse.getData().getPrescription());
            }
        });
        binding.done.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}