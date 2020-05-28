package app.iflatco.eclinic.ui.profile;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import app.iflatco.eclinic.data.ClientServer;
import app.iflatco.eclinic.models.ResponseModel;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileViewModel extends ViewModel {

    private static final String TAG = "UserProfileViewModel";

    MutableLiveData<ResponseModel> responseModelMutableLiveData;

    public UserProfileViewModel() {
        responseModelMutableLiveData = new MutableLiveData<>();
    }

    void updateImage(String id, MultipartBody.Part image) {
        ClientServer.getINSTANCE().updateProfImage("Bearer " + id, image).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.code() == 200) {
                    responseModelMutableLiveData.setValue(response.body());
                    Log.d(TAG, "onResponse: " + response.body());
                } else {
                    Log.d(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}
