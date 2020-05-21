package app.iflatco.eclinic.ui.contact_dr;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.databinding.ContactDrFragmentBinding;
import app.iflatco.eclinic.ui.page_viewer.DoctorsViewPages;

public class ContactDr extends Fragment {

    private ContactDrViewModel mViewModel;
    private ContactDrFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ContactDrFragmentBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(ContactDrViewModel.class);

        binding.next.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), DoctorsViewPages.class));
            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        });

        return binding.getRoot();
    }


}
