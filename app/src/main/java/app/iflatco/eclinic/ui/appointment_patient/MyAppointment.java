package app.iflatco.eclinic.ui.appointment_patient;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.iflatco.eclinic.databinding.MyAppointmentBinding;
import app.iflatco.eclinic.models.PatientAppointmentData;
import app.iflatco.eclinic.ui.chat.Chat;
import app.iflatco.eclinic.utils.CustomClickListener;
import app.iflatco.eclinic.utils.CustomSharedPref;

public class MyAppointment extends AppCompatActivity {

    private MyAppointmentViewModel mViewModel;
    private MyAppointmentBinding binding;
    private MyAppointmentAdapter myAppointmentAdapter;
    private List<PatientAppointmentData> dataList;
    CustomSharedPref pref;
    private String doctorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MyAppointmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mViewModel = new ViewModelProvider(this).get(MyAppointmentViewModel.class);
        pref = new CustomSharedPref(this);
        dataList = new ArrayList<>();

        mViewModel.getMyAppointment(pref.getSessionValue("tokenId"));

        mViewModel.myAppointmentLiveData.observe(this, appointments -> {
            assert appointments.getData() != null;
            dataList.addAll(appointments.getData());
            myAppointmentAdapter.setList(appointments.getData());
        });

        mViewModel.joinMutableLiveData.observe(this, join -> {
            if (join.getStatus()) {
                startActivity(new Intent(MyAppointment.this, Chat.class)
                        .putExtra("room_id", join.getData().getRoomId())
                        .putExtra("doctor_name", doctorName));
            } else {
                Toast.makeText(this, "Please wait your time", Toast.LENGTH_SHORT).show();
            }
        });

        initView();

    }

    private void initView() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setNestedScrollingEnabled(false);
        myAppointmentAdapter = new MyAppointmentAdapter(this, new CustomClickListener() {
            @Override
            public void onClick(int position) {
                mViewModel.joinAppointment(pref.getSessionValue("tokenId"),
                        dataList.get(position).getAppointmentId());
                doctorName = dataList.get(position).getFirstName() + " " + dataList.get(position).getLastName();
            }
        });
        binding.recyclerView.setAdapter(myAppointmentAdapter);
    }
}
