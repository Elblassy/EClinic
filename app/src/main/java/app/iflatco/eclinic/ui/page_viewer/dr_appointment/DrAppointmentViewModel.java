package app.iflatco.eclinic.ui.page_viewer.dr_appointment;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

import app.iflatco.eclinic.data.ClientServer;
import app.iflatco.eclinic.models.DoctorDaysResponse;
import app.iflatco.eclinic.models.DrAvailableSlotsResponse;
import app.iflatco.eclinic.models.ResponseAppointment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrAppointmentViewModel extends ViewModel {
    private static final String TAG = "DrAppointmentViewModel";

    MutableLiveData<ResponseAppointment> responseAppointmentMutableLiveData;
    MutableLiveData<DrAvailableSlotsResponse> drSlotsMutableLiveData;

    public DrAppointmentViewModel() {
        drSlotsMutableLiveData = new MutableLiveData<>();
        responseAppointmentMutableLiveData = new MutableLiveData<>();

    }

    void getAvailableSlots(String auth, int id, int range) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("doctor_id", id);
        map.put("searchIn", range);

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

    void pendingAppointment(String auth, int id, String date) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("slot_id", id);
        map.put("date", date);

        Log.d(TAG, "pendingAppointment: " + id);

        ClientServer.getINSTANCE().pendingAppointment("Bearer " + auth, map).enqueue(new Callback<ResponseAppointment>() {
            @Override
            public void onResponse(Call<ResponseAppointment> call, Response<ResponseAppointment> response) {
                if (response.code() == 201) {
                    responseAppointmentMutableLiveData.setValue(response.body());
                    Log.d(TAG, "onResponse: " + response.body().getData().getDate());
                } else {
                    Log.d(TAG, "pendingAppointment response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseAppointment> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });


    }


}
