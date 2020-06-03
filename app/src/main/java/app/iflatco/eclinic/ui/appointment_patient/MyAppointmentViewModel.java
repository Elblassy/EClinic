package app.iflatco.eclinic.ui.appointment_patient;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import java.util.HashMap;

import app.iflatco.eclinic.data.ClientServer;
import app.iflatco.eclinic.models.JoinToAppointmentResponse;
import app.iflatco.eclinic.models.PatientAppointmentResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAppointmentViewModel extends ViewModel {
    private static final String TAG = "MyAppointmentViewModel";

    MutableLiveData<PatientAppointmentResponse> myAppointmentLiveData;
    MutableLiveData<JoinToAppointmentResponse> joinMutableLiveData;

    public MyAppointmentViewModel(){
        myAppointmentLiveData = new MutableLiveData<>();
        joinMutableLiveData = new MutableLiveData<>();
    }

    void getMyAppointment(String auth) {

        ClientServer.getINSTANCE().getPatientAppointment("Bearer " + auth).enqueue(new Callback<PatientAppointmentResponse>() {
            @Override
            public void onResponse(Call<PatientAppointmentResponse> call, Response<PatientAppointmentResponse> response) {
                if (response.code() == 200) {
                    myAppointmentLiveData.setValue(response.body());
                    Log.d(TAG, "onResponse: " + response.body().getData().get(0).getDate());

                } else {
                    Log.d(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<PatientAppointmentResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    void joinAppointment(String auth, int appointmentId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("appointment_id", appointmentId);


        ClientServer.getINSTANCE().joinToAppointment("Bearer " + auth,map).enqueue(new Callback<JoinToAppointmentResponse>() {
            @Override
            public void onResponse(Call<JoinToAppointmentResponse> call, Response<JoinToAppointmentResponse> response) {
                if (response.code() == 200) {
                    joinMutableLiveData.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JoinToAppointmentResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}
