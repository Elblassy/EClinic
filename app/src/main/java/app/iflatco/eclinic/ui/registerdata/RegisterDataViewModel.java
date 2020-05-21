package app.iflatco.eclinic.ui.registerdata;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import app.iflatco.eclinic.data.ClientServer;
import app.iflatco.eclinic.models.ResponseModel;
import app.iflatco.eclinic.models.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterDataViewModel extends ViewModel {

    private static final String TAG = "RegisterDataViewModel";
    MutableLiveData<ResponseModel> responseModelMutableLiveData;

    public RegisterDataViewModel() {
        responseModelMutableLiveData = new MutableLiveData<>();
    }

    public void registerData(UserModel userModel) {

        ClientServer.getINSTANCE().signUp(userModel).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.code() == 201) {
                    responseModelMutableLiveData.setValue(response.body());
                }

                Log.d(TAG, "onResponse:  " + response.code());
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }
}
