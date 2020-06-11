package app.iflatco.eclinic.ui.page_viewer.dr_appointment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import app.iflatco.eclinic.databinding.DrAppointmentFragmentBinding;
import app.iflatco.eclinic.models.DrAvailableSlotsData;
import app.iflatco.eclinic.models.SlotesFilteratiton;
import app.iflatco.eclinic.utils.CustomClickListener;
import app.iflatco.eclinic.utils.CustomSharedPref;
import app.iflatco.eclinic.utils.OnAppointmentSelected;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DrAppointment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private DrAppointmentViewModel mViewModel;
    private DrAppointmentFragmentBinding binding;
    private CustomSharedPref pref;
    private int id;
    private String drName;
    private List<String> days;
    private List<DrAvailableSlotsData> slotsDataList;
    private String date;
    private DatePickerDialog dpd;
    private DrAppointmentAdapter drAppointmentAdapter;
    private Calendar now;
    private OnAppointmentSelected onAppointmentSelected;
    private String finalDay = "";
    private String finalDate = "";
    private DateFormat dayFormat;
    private List<SlotesFilteratiton> slotesListFiltered;

    public static DrAppointment newInstance(Context context) {
        return new DrAppointment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setLocal();
        binding = DrAppointmentFragmentBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(DrAppointmentViewModel.class);

        dayFormat = new SimpleDateFormat("EEE");
        slotesListFiltered = new ArrayList<>();
        slotsDataList = new ArrayList<>();
        days = new ArrayList<>();
        pref = new CustomSharedPref(getActivity());
        now = Calendar.getInstance();

        dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );

        FragmentManager manager = getActivity().getSupportFragmentManager();

        binding.day.setOnClickListener(v -> {
            if (Settings.Global.getInt(getActivity().getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1) {
                dpd.show(manager, "Datepickerdialog");
            } else {
                Toast.makeText(getContext(), "Please set Auto time in your settings first", Toast.LENGTH_LONG).show();
            }
        });

        initView();
        initObservers();

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onAppointmentSelected = (OnAppointmentSelected) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(((Activity) context).getLocalClassName()
                    + " must implement OnButtonClickListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        mViewModel.getAvailableSlots(pref.getSessionValue("tokenId"), id, 30);

        Log.d(TAG, "onCreateView: " + id);
        binding.drName.setText(drName);
    }

    public void setData(int id, String name) {
        this.id = id;
        drName = name;
    }

    @NotNull
    private Calendar[] setSelectableDays() {
        Calendar calendar;
        List<Calendar> available = new ArrayList<>();
        int weeks = 4;
        days.size();
        for (int k = 0; k < days.size(); k++) {
            switch (days.get(k)) {
                case "fri":
                    for (int i = 0; i < (weeks * 7); i = i + 7) {
                        calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_YEAR, (Calendar.FRIDAY - calendar.get(Calendar.DAY_OF_WEEK) + i));
                        if (calendar.compareTo(now) >= 0) {
                            available.add(calendar);
                        }
                    }
                    break;
                case "sat":
                    for (int i = 0; i < (weeks * 7); i = i + 7) {
                        calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_YEAR, (Calendar.SATURDAY - calendar.get(Calendar.DAY_OF_WEEK) + i));
                        if (calendar.compareTo(now) >= 0) {
                            available.add(calendar);
                        }
                    }
                    break;
                case "sun":
                    for (int i = 0; i < (weeks * 7); i = i + 7) {
                        calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_YEAR, (Calendar.SUNDAY - calendar.get(Calendar.DAY_OF_WEEK) + i));
                        if (calendar.compareTo(now) >= 0) {
                            available.add(calendar);
                        }
                    }
                    break;
                case "mon":
                    for (int i = 0; i < (weeks * 7); i = i + 7) {
                        calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_YEAR, (Calendar.MONDAY - calendar.get(Calendar.DAY_OF_WEEK) + i));
                        if (calendar.compareTo(now) >= 0) {
                            available.add(calendar);
                        }
                    }
                    break;
                case "tue":
                    for (int i = 0; i < (weeks * 7); i = i + 7) {
                        calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_YEAR, (Calendar.TUESDAY - calendar.get(Calendar.DAY_OF_WEEK) + i));
                        if (calendar.compareTo(now) >= 0) {
                            available.add(calendar);
                        }
                    }
                    break;
                case "wed":
                    for (int i = 0; i < (weeks * 7); i = i + 7) {
                        calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_YEAR, (Calendar.WEDNESDAY - calendar.get(Calendar.DAY_OF_WEEK) + i));
                        if (calendar.compareTo(now) >= 0) {
                            available.add(calendar);
                        }
                    }
                    break;
                case "thu":
                    for (int i = 0; i < (weeks * 7); i = i + 7) {
                        calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_YEAR, (Calendar.THURSDAY - calendar.get(Calendar.DAY_OF_WEEK) + i));
                        if (calendar.compareTo(now) >= 0) {
                            available.add(calendar);
                        }
                    }
                    break;
            }
        }

        Log.d(TAG, "setSelectableDays: " + available.size());
        return available.toArray(new Calendar[available.size()]);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date dt1 = sdf.parse(date);
            finalDay = dayFormat.format(dt1);
            finalDate = sdf.format(dt1);
            Log.d(TAG, "onDateSet: " + finalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        filterDate(slotsDataList, finalDate);
        binding.day.setText(finalDate);
        Log.d(TAG, "onDateSet: " + finalDay);
    }

    private void initObservers() {

        mViewModel.drSlotsMutableLiveData.observe(getViewLifecycleOwner(), observer -> {
            slotsDataList = observer.getData();
            filterDays(observer.getData());
        });

        mViewModel.responseAppointmentMutableLiveData.observe(getViewLifecycleOwner(), responseAppointment -> {
            onAppointmentSelected.onSelectedAppointment(responseAppointment.getData().getAppointmentId());
        });
    }

    private void initView() {
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 3);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setNestedScrollingEnabled(false);
        drAppointmentAdapter = new DrAppointmentAdapter(getContext(), new CustomClickListener() {
            @Override
            public void onClick(int position) {
                SlotesFilteratiton data = slotesListFiltered.get(position);
                mViewModel.pendingAppointment(pref.getSessionValue("tokenId"), data.getSloteId(),
                        data.getStartTime());
            }
        });
        binding.recyclerView.setAdapter(drAppointmentAdapter);
    }

    private void filterDays(@NotNull List<DrAvailableSlotsData> data) {
        SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmX");

        for (int i = 0; i < data.size(); i++) {
            try {
                Date date = utcFormat.parse(data.get(i).getStartTime());
                String day = dayFormat.format(date);
                if (!days.contains(day)) {
                    days.add(day.toLowerCase());
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        dpd.setSelectableDays(setSelectableDays());
        binding.progress.smoothToHide();
        binding.day.setVisibility(View.VISIBLE);
    }

    private void filterDate(List<DrAvailableSlotsData> data, String date) {
        SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmX");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat actualTime = new SimpleDateFormat("HH:mm");

        slotesListFiltered.clear();
        Log.d(TAG, "filterDate: " + date);


        for (int i = 0; i < data.size(); i++) {
            try {
                Date dateFormated = utcFormat.parse(data.get(i).getStartTime());
                String mDateFormated = yearFormat.format(dateFormated);
                Log.d(TAG, "filterDate: " + actualTime.format(dateFormated));

                if (mDateFormated.equals(date)) {
                    slotesListFiltered.add(new SlotesFilteratiton(data.get(i).getSlotId(),
                            data.get(i).getStartTime(), actualTime.format(dateFormated), yearFormat.format(dateFormated)));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        drAppointmentAdapter.setList(slotesListFiltered);

    }

    private void setLocal() {
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = getActivity().getBaseContext().getResources().getConfiguration();
        config.locale = locale;
    }
}
