package app.iflatco.eclinic.ui.contact_dr;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import app.iflatco.eclinic.data.ClientServer;
import app.iflatco.eclinic.models.CateResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactDrViewModel extends ViewModel {

    private static final String TAG = "ContactDrViewModel";

    MutableLiveData<CateResponse> cateResponseMutableLiveData;

    public ContactDrViewModel() {
        cateResponseMutableLiveData = new MutableLiveData<>();
    }

    void getCategories(String tokenId) {
        Log.d(TAG, "getCategories: " + tokenId);
        ClientServer.getINSTANCE().getCategories("Bearer " + tokenId).enqueue(new Callback<CateResponse>() {
            @Override
            public void onResponse(Call<CateResponse> call, Response<CateResponse> response) {
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: " + response.body().getData().get(0).getAr());

                    cateResponseMutableLiveData.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CateResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}
