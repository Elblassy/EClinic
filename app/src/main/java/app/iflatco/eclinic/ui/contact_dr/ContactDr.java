package app.iflatco.eclinic.ui.contact_dr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.databinding.ContactDrFragmentBinding;
import app.iflatco.eclinic.models.CateDataModel;
import app.iflatco.eclinic.ui.page_viewer.DoctorsViewPages;
import app.iflatco.eclinic.utils.CustomSharedPref;

public class ContactDr extends Fragment {

    private static final String TAG = "ContactDr";

    private ContactDrViewModel mViewModel;
    private ContactDrFragmentBinding binding;
    private boolean selected = false;
    List<CateDataModel> cateDataModel;
    List<String> cateList;

    ArrayAdapter<String> cateAdapter;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ContactDrFragmentBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(ContactDrViewModel.class);

        cateDataModel = new ArrayList<>();
        cateList = new ArrayList<>();

        CustomSharedPref pref = new CustomSharedPref(getContext());

        mViewModel.getCategories(pref.getSessionValue("tokenId"));

        mViewModel.cateResponseMutableLiveData.observe(getViewLifecycleOwner(), list -> {
            selected = true;
            cateDataModel = list.getData();
            initSpinner();
        });

        binding.next.setOnClickListener(v -> {
            if (selected && !binding.category.getText().toString().isEmpty()) {
                int id = cateDataModel.get(binding.category.getSelectedIndex()).getCategoryId();
                Log.d(TAG, "onCreateView: " + id);
                startActivity(new Intent(getContext(), DoctorsViewPages.class)
                        .putExtra("category_id", id));
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            } else {
                Toast.makeText(getContext(), getResources().getString(R.string.select_cate), Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }


    private void initSpinner() {
        for (int i = 0; i < cateDataModel.size(); i++) {
            cateList.add(cateDataModel.get(i).getAr());
            Log.d(TAG, "initSpinner: " + cateDataModel.get(i).getCategoryId());
        }
        cateAdapter = new ArrayAdapter<>(getContext(),
                R.layout.custom_spinner_text, cateList);
        binding.category.setAdapter(cateAdapter);
    }
}
