package app.iflatco.eclinic.ui.prescription;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

import app.iflatco.eclinic.data.ClientServer;
import app.iflatco.eclinic.models.PrescriptionResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrescriptionViewModel extends ViewModel {
    private static final String TAG = "PrescriptionViewModel";

    MutableLiveData<PrescriptionResponse> responseMutableLiveData;

    public PrescriptionViewModel() {
        responseMutableLiveData = new MutableLiveData<>();
    }

    void getPrescription(String auth, String roomId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("room_id", roomId);

        ClientServer.getINSTANCE().getPrescription("Bearer " + auth, map).enqueue(new Callback<PrescriptionResponse>() {
            @Override
            public void onResponse(Call<PrescriptionResponse> call, Response<PrescriptionResponse> response) {
                if (response.code() == 200) {
                    responseMutableLiveData.setValue(response.body());

                } else {
                    Log.d(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<PrescriptionResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
