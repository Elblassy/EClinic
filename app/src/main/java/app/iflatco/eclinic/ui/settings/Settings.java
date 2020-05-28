package app.iflatco.eclinic.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import app.iflatco.eclinic.ui.splash.SplashActivity;
import app.iflatco.eclinic.databinding.SettingsFragmentBinding;
import app.iflatco.eclinic.utils.CustomSharedPref;

public class Settings extends Fragment {

    private SettingsViewModel mViewModel;
    private SettingsFragmentBinding binding;

    public static Settings newInstance() {
        return new Settings();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SettingsFragmentBinding.inflate(inflater, container, false);

        CustomSharedPref pref = new CustomSharedPref(getContext());
        binding.logout.setOnClickListener(v -> {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Log out");
            alertDialog.setMessage("Are you sure you want to log out");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", (dialog, which) -> {
                pref.logOut();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), SplashActivity.class));
                dialog.dismiss();
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                    (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        });
        return binding.getRoot();
    }


}
