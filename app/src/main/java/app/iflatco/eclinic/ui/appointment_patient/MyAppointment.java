package app.iflatco.eclinic.ui.appointment_patient;

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

public class MyAppointment extends Fragment {

    private MyAppointmentViewModel mViewModel;

    public static MyAppointment newInstance() {
        return new MyAppointment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        BottomNavigationView navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.GONE);
        return inflater.inflate(R.layout.my_appointment_fragment, container, false);
    }



}
