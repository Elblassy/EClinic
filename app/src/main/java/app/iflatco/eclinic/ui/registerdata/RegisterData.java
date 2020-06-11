package app.iflatco.eclinic.ui.registerdata;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import app.iflatco.eclinic.R;
import app.iflatco.eclinic.databinding.RegisterDataFragmentBinding;
import app.iflatco.eclinic.models.DataModel;
import app.iflatco.eclinic.models.UserModel;
import app.iflatco.eclinic.ui.main.MainActivity;
import app.iflatco.eclinic.utils.CustomSharedPref;

public class RegisterData extends Fragment {

    private RegisterDataViewModel mViewModel;
    RegisterDataFragmentBinding binding;
    private static final String TAG = "RegisterData";
    CustomSharedPref pref;
    List<String> genders;
    ArrayAdapter<String> genderAdapter;
    AlertDialog.Builder builder;
    Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RegisterDataFragmentBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(RegisterDataViewModel.class);
        pref = new CustomSharedPref(getContext());

        initView();

        builder = new AlertDialog.Builder(getContext());

        builder.setView(R.layout.progress_dialog);
        dialog = builder.create();
        dialog.setCancelable(false);

        mViewModel.responseModelMutableLiveData.observe(getViewLifecycleOwner(), responseModel -> {
            if (responseModel.getStatus()) {
                pref.setPrefSignUp(true);
                DataModel data = responseModel.getData();
                pref.setPrefUserId(data.getUserId());
                pref.setPrefFirstName(data.getFirstName());
                pref.setPrefLastName(data.getLastName());
                pref.setPrefGender(data.getGender());
                pref.setPrefBirthDate(data.getBirthDate());
                pref.setPrefHeight(String.valueOf(data.getHeight()));
                pref.setPrefWeight(String.valueOf(data.getWeight()));
                pref.setPrefBmi(String.valueOf(data.getBmi()));
                pref.setPrefTokenId(data.getToken());
                dialog.dismiss();
                startActivity(new Intent(getContext(), MainActivity.class));
            } else {
                dialog.dismiss();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        genders = new ArrayList<>();
        genders.add("Gender");
        genders.add("Male");
        genders.add("Female");

        genderAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, genders);
    }

    private void initView() {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener data = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                binding.birthDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        binding.gender.setAdapter(genderAdapter);

        binding.birthDate.setOnClickListener(v -> {
            Log.d(TAG, "initView: " + "hererere");
            new DatePickerDialog(getContext(), R.style.MySpinnerDatePickerStyle, data, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        binding.submit.setOnClickListener(v -> {
            setData();
        });
    }


    private void setData() {
        String firstName = binding.firstName.getText().toString().trim();
        String lastName = binding.lastName.getText().toString().trim();
        String mobile = pref.getSessionValue("mobile");
        String gender = binding.gender.getText().toString();
        String weight = binding.weight.getText().toString().trim();
        String height = binding.height.getText().toString().trim();
        String birthDate = binding.birthDate.getText().toString().trim();

        if (firstName.isEmpty()) {
            binding.firstName.setError(getResources().getString(R.string.select_name));
            return;
        }
        if (lastName.isEmpty()) {
            binding.lastName.setError(getResources().getString(R.string.select_name));
            return;
        }
        if (gender.isEmpty() || binding.gender.getSelectedIndex() == 0) {
            binding.gender.setError(getResources().getString(R.string.select_gender));
            return;
        }
        if (weight.isEmpty() || Integer.parseInt(weight) > 355) {
            binding.weight.setError(getResources().getString(R.string.select_weight));
            return;
        }
        if (height.isEmpty() || Integer.parseInt(height) > 255) {
            binding.height.setError(getResources().getString(R.string.select_height));
            return;
        }
        if (birthDate.isEmpty()) {
            binding.birthDate.setError(getResources().getString(R.string.select_date));
            return;
        }

        int bmi = (int) (Double.parseDouble(weight) / Math.pow(Double.parseDouble(height), 2));

        UserModel userModel = new UserModel(mobile, firstName, lastName, birthDate,
                Integer.parseInt(weight), Integer.parseInt(height), bmi, "123", gender);

        mViewModel.registerData(userModel);
        setDialog(true);
    }

    private void setDialog(boolean show) {
        if (show) builder.show();
        else dialog.dismiss();
    }

}
