package app.iflatco.eclinic.ui.appointment_patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.databinding.ActivityMainBinding;
import app.iflatco.eclinic.databinding.MyAppointmentFragmentBinding;

public class MyAppointment extends AppCompatActivity {

    private MyAppointmentViewModel mViewModel;
    private MyAppointmentFragmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MyAppointmentFragmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}
