package app.iflatco.eclinic.ui.page_viewer.dr_appointment;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

import app.iflatco.eclinic.data.ClientServer;
import app.iflatco.eclinic.models.DoctorDaysResponse;
import app.iflatco.eclinic.models.DrAvailableSlotsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrAppointmentViewModel extends ViewModel {
    private static final String TAG = "DrAppointmentViewModel";

    MutableLiveData<DoctorDaysResponse> drDaysMutableLiveData;
    MutableLiveData<DrAvailableSlotsResponse> drSlotsMutableLiveData;

    public DrAppointmentViewModel() {
        drDaysMutableLiveData = new MutableLiveData<>();
        drSlotsMutableLiveData = new MutableLiveData<>();
    }

    void getDrDays(String auth, int id) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("doctor_id", id);

        ClientServer.getINSTANCE().getDoctorDays("Bearer " + auth, map).enqueue(new Callback<DoctorDaysResponse>() {
            @Override
            public void onResponse(Call<DoctorDaysResponse> call, Response<DoctorDaysResponse> response) {
                if (response.code() == 200) {
                    drDaysMutableLiveData.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DoctorDaysResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    void getAvailableSlots(String auth, int id, String day, String date) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("doctor_id", id);
        map.put("day", day);
        map.put("date", date);

        ClientServer.getINSTANCE().getAvailableSlots("Bearer " + auth, map).enqueue(new Callback<DrAvailableSlotsResponse>() {
            @Override
            public void onResponse(Call<DrAvailableSlotsResponse> call, Response<DrAvailableSlotsResponse> response) {
                if (response.code() == 200) {
                    drSlotsMutableLiveData.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DrAvailableSlotsResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });


    }

}
