package app.iflatco.eclinic.ui.page_viewer.select_doctor;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.databinding.DoctorsFragmentBinding;
import app.iflatco.eclinic.models.DrModel;

public class SelectDoctor extends Fragment {

    private SelectDoctorViewModel mViewModel;
    private DoctorsFragmentBinding binding;
    private SelectDoctorAdapter selectDoctorAdapter;

    public static SelectDoctor newInstance(Context context) {
        return new SelectDoctor();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DoctorsFragmentBinding.inflate(inflater, container, false);

        initView();
        setList();
        return binding.getRoot();
    }

    private void initView() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setNestedScrollingEnabled(false);
        selectDoctorAdapter = new SelectDoctorAdapter(getContext());
        binding.recyclerView.setAdapter(selectDoctorAdapter);
    }

    private void setList() {
        DrModel drModel = new DrModel("Muhammad Elblasy",
                "Doctor of cardiology", "80 EGP", R.drawable.elblasy);
        DrModel drModel2 = new DrModel("Mostfa Essam",
                "Doctor of cardiology", "90 EGP", R.drawable.sasa);
        DrModel drModel3 = new DrModel("Fawzi Essam",
                "Doctor of cardiology", "200 EGP", R.drawable.fawzi);

        List<DrModel> list = new ArrayList<>();
        list.add(drModel);
        list.add(drModel2);
        list.add(drModel3);
        list.add(drModel);
        list.add(drModel2);
        list.add(drModel3);
        selectDoctorAdapter.setList(list);
    }

}
