package app.iflatco.eclinic.ui.old_appointment;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import app.iflatco.eclinic.data.ClientServer;
import app.iflatco.eclinic.models.PatientAppointmentResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOldAppointmentViewModel extends ViewModel {
    private static final String TAG = "MyOldAppointmentViewMod";

    MutableLiveData<PatientAppointmentResponse> myAppointmentLiveData;

    public MyOldAppointmentViewModel() {
        myAppointmentLiveData = new MutableLiveData<>();
    }

    void getMyAppointment(String auth) {

        ClientServer.getINSTANCE().getFinishedAppointment("Bearer " + auth).enqueue(new Callback<PatientAppointmentResponse>() {
            @Override
            public void onResponse(Call<PatientAppointmentResponse> call, Response<PatientAppointmentResponse> response) {
                if (response.code() == 200) {
                    myAppointmentLiveData.setValue(response.body());
                } else {
                    myAppointmentLiveData.setValue(new PatientAppointmentResponse());
                    Log.d(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<PatientAppointmentResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


}
