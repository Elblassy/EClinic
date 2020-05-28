package app.iflatco.eclinic.ui.page_viewer.select_doctor;

import android.app.Activity;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.iflatco.eclinic.databinding.DoctorsFragmentBinding;
import app.iflatco.eclinic.models.DrDataModel;
import app.iflatco.eclinic.utils.CustomClickListener;
import app.iflatco.eclinic.utils.OnDoctorSelected;

public class SelectDoctor extends Fragment {
    private static final String TAG = "SelectDoctor";

    private SelectDoctorViewModel mViewModel;
    private DoctorsFragmentBinding binding;
    private SelectDoctorAdapter selectDoctorAdapter;
    private int id = 0;
    private OnDoctorSelected onDoctorSelected;
    private List<DrDataModel> list;

    public static SelectDoctor newInstance(Context context, int id) {
        SelectDoctor selectDoctor = new SelectDoctor();
        Bundle args = new Bundle();
        args.putInt("category_id", id);
        selectDoctor.setArguments(args);
        return selectDoctor;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt("category_id");
        }


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onDoctorSelected = (OnDoctorSelected) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(((Activity) context).getLocalClassName()
                    + " must implement OnButtonClickListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DoctorsFragmentBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(SelectDoctorViewModel.class);

        mViewModel.getDrList(id);

        mViewModel.listMutableLiveData.observe(getViewLifecycleOwner(), list -> {
            selectDoctorAdapter.setList(list);
            binding.progress.smoothToHide();
            this.list = list;
        });

        initView();
        return binding.getRoot();
    }

    private void initView() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setNestedScrollingEnabled(false);
        selectDoctorAdapter = new SelectDoctorAdapter(getContext(), new CustomClickListener() {
            @Override
            public void onClick(int position) {
                int id = list.get(position).getDoctorId();
                Log.d(TAG, "onDoctorSelected: " + id);
                onDoctorSelected.onClicked(id);
            }
        });
        binding.recyclerView.setAdapter(selectDoctorAdapter);
    }


}
