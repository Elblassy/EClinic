package app.iflatco.eclinic.ui.page_viewer.dr_appointment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app.iflatco.eclinic.databinding.DrAppointmentFragmentBinding;
import app.iflatco.eclinic.utils.CustomSharedPref;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DrAppointment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private DrAppointmentViewModel mViewModel;
    private DrAppointmentFragmentBinding binding;
    private CustomSharedPref pref;
    private int id;
    private List<String> days;

    public static DrAppointment newInstance(Context context) {
        return new DrAppointment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DrAppointmentFragmentBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(DrAppointmentViewModel.class);

        days = new ArrayList<>();

        pref = new CustomSharedPref(getActivity());

        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );

        dpd.setMinDate(now);
        FragmentManager manager = getActivity().getSupportFragmentManager();

        binding.day.setOnClickListener(v -> {
            dpd.show(manager, "Datepickerdialog");
        });
        mViewModel.drDaysMutableLiveData.observe(getViewLifecycleOwner(), observer -> {
            days.addAll(observer.getData());
            dpd.setSelectableDays(setSelectableDays());
            Log.d(TAG, "onCreateView: " + observer.getData());
        });
        mViewModel.drSlotsMutableLiveData.observe(getViewLifecycleOwner(), observer -> {
            Log.d(TAG, "onCreateView: " + observer.getData().get(0).getDay());
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getDrDays(pref.getSessionValue("tokenId"), id);
        Log.d(TAG, "onCreateView: " + id);

    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    private Calendar[] setSelectableDays() {
        Calendar calendar;
        List<Calendar> available = new ArrayList<>();
        int weeks = 4;
        for (int k = 0; k < days.size(); k++) {
            switch (days.get(k)) {
                case "fri":
                    for (int i = 0; i < (weeks * 7); i = i + 7) {
                        calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_YEAR, (Calendar.FRIDAY - calendar.get(Calendar.DAY_OF_WEEK) + 7 + i));
                        Log.d(TAG, "setSelectableDays: " + calendar.toString());
                        available.add(calendar);
                    }
                    break;
                case "sat":
                    for (int i = 0; i < (weeks * 7); i = i + 7) {
                        calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_YEAR, (Calendar.SATURDAY - calendar.get(Calendar.DAY_OF_WEEK) + 7 + i));
                        available.add(calendar);
                    }
                    break;
                case "sun":
                    for (int i = 0; i < (weeks * 7); i = i + 7) {
                        calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_YEAR, (Calendar.SUNDAY - calendar.get(Calendar.DAY_OF_WEEK) + 7 + i));
                        available.add(calendar);
                    }
                    break;
                case "mon":
                    for (int i = 0; i < (weeks * 7); i = i + 7) {
                        calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_YEAR, (Calendar.MONDAY - calendar.get(Calendar.DAY_OF_WEEK) + 7 + i));
                        available.add(calendar);
                    }
                    break;
                case "tue":
                    for (int i = 0; i < (weeks * 7); i = i + 7) {
                        calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_YEAR, (Calendar.TUESDAY - calendar.get(Calendar.DAY_OF_WEEK) + 7 + i));
                        available.add(calendar);
                    }
                    break;
                case "wed":
                    for (int i = 0; i < (weeks * 7); i = i + 7) {
                        calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_YEAR, (Calendar.WEDNESDAY - calendar.get(Calendar.DAY_OF_WEEK) + 7 + i));
                        available.add(calendar);
                    }
                    break;
                case "thu":
                    for (int i = 0; i < (weeks * 7); i = i + 7) {
                        calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_YEAR, (Calendar.THURSDAY - calendar.get(Calendar.DAY_OF_WEEK) + 7 + i));
                        available.add(calendar);
                    }
                    break;
            }
        }

        return available.toArray(new Calendar[available.size()]);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String finalDay = "";

        try {
            Date dt1 = sdf.parse(date);
            DateFormat format = new SimpleDateFormat("EEE");
            finalDay = format.format(dt1);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        mViewModel.getAvailableSlots(pref.getSessionValue("tokenId"), id, finalDay.toLowerCase(), date);
        binding.day.setText(date);
        Log.d(TAG, "onDateSet: " + finalDay);
    }
}
