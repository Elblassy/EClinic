package app.iflatco.eclinic.ui.old_appointment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.iflatco.eclinic.databinding.ActivityMyOldAppointmentBinding;
import app.iflatco.eclinic.models.PatientAppointmentData;
import app.iflatco.eclinic.ui.old_chat.OldChat;
import app.iflatco.eclinic.utils.CustomClickListener;
import app.iflatco.eclinic.utils.CustomSharedPref;

public class MyOldAppointment extends AppCompatActivity {
    private static final String TAG = "MyOldAppointment";

    private MyOldAppointmentViewModel mViewModel;
    private ActivityMyOldAppointmentBinding binding;
    private MyoldAppointmentAdapter myAppointmentAdapter;
    private List<PatientAppointmentData> dataList;
    CustomSharedPref pref;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyOldAppointmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mViewModel = new ViewModelProvider(this).get(MyOldAppointmentViewModel.class);
        pref = new CustomSharedPref(this);
        dataList = new ArrayList<>();

        mViewModel.getMyAppointment(pref.getSessionValue("tokenId"));

        mViewModel.myAppointmentLiveData.observe(this, appointments -> {
            binding.progress.smoothToHide();
            if (appointments.getData() != null) {
                if (appointments.getData().size() > 0) {
                    dataList.addAll(appointments.getData());
                    myAppointmentAdapter.setList(appointments.getData());
                }else {
                    binding.emptyView.setVisibility(View.VISIBLE);
                }
            }
        });


        initView();
    }

    private void initView() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setNestedScrollingEnabled(false);
        myAppointmentAdapter = new MyoldAppointmentAdapter(this, new CustomClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(MyOldAppointment.this, OldChat.class)
                        .putExtra("room_id", dataList.get(position).getRoomId())
                .putExtra("chat_header",dataList.get(position).getFirstName() + " " + dataList.get(position).getLastName()));
            }
        });
        binding.recyclerView.setAdapter(myAppointmentAdapter);
    }

}