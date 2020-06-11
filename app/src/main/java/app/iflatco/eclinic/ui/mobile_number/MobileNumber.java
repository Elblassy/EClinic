package app.iflatco.eclinic.ui.mobile_number;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.databinding.MobileNumberFragmentBinding;
import app.iflatco.eclinic.utils.CustomSharedPref;

public class MobileNumber extends Fragment {

    private MobileNumberViewModel mViewModel;
    MobileNumberFragmentBinding binding;
    private static final String TAG = "MobileNumber";


    public static MobileNumber newInstance() {
        return new MobileNumber();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MobileNumberFragmentBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(MobileNumberViewModel.class);

        CustomSharedPref pref = new CustomSharedPref(getContext());


        binding.next.setOnClickListener(v -> {
            if (!binding.number.getText().toString().isEmpty() && binding.number.getText().toString().length() >= 5) {
                Navigation.findNavController(v).navigate(R.id.action_mobileNumber_to_activateMobile);
                pref.setPrefMobile(binding.ccp.getSelectedCountryCodeWithPlus() + binding.number.getText().toString().trim());
            } else {
                Toast.makeText(getContext(), getResources().getString(R.string.enter_num_toast), Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }


}
