package app.iflatco.eclinic.ui.page_viewer.payment;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

import app.iflatco.eclinic.data.ClientServer;
import app.iflatco.eclinic.models.ResponseAppointment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentViewModel extends ViewModel {
    private static final String TAG = "PaymentViewModel";

    MutableLiveData<ResponseAppointment> confirmAppointmentMutableLiveData;
    MutableLiveData<ResponseAppointment> cancelAppointmentMutableLiveData;


    public  PaymentViewModel(){
        confirmAppointmentMutableLiveData = new MutableLiveData<>();
        cancelAppointmentMutableLiveData = new MutableLiveData<>();

    }

    void confirmAppointment(String auth, int id){
        HashMap<String, Object> map = new HashMap<>();
        map.put("appointment_id", id);

        ClientServer.getINSTANCE().confirmAppointment("Bearer " + auth, map).enqueue(new Callback<ResponseAppointment>() {
            @Override
            public void onResponse(Call<ResponseAppointment> call, Response<ResponseAppointment> response) {
                if (response.code() == 200) {
                    Log.d(TAG, "confirmAppointment response: " + response.body().getStatus());

                    confirmAppointmentMutableLiveData.setValue(response.body());
                } else {
                    Log.d(TAG, "confirmAppointment response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseAppointment> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    void cancelAppointment(String auth, int id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("appointment_id", id);

        ClientServer.getINSTANCE().cancelAppointment("Bearer " + auth, map).enqueue(new Callback<ResponseAppointment>() {
            @Override
            public void onResponse(Call<ResponseAppointment> call, Response<ResponseAppointment> response) {
                if (response.code() == 200) {
                    cancelAppointmentMutableLiveData.setValue(response.body());
                    Log.d(TAG, "onResponse: " + response.body().getStatus());
                } else {
                    Log.d(TAG, "cancel response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseAppointment> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }


}
