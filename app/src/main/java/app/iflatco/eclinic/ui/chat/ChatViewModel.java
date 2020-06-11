package app.iflatco.eclinic.ui.chat;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import app.iflatco.eclinic.data.ClientServer;
import app.iflatco.eclinic.models.UploadImageResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatViewModel extends ViewModel {
    private static final String TAG = "ChatViewModel";

    MutableLiveData<UploadImageResponse> uploadMutableLiveData;

    public ChatViewModel() {
        uploadMutableLiveData = new MutableLiveData<>();
    }

    void uploadImage(String id, MultipartBody.Part image, MultipartBody.Part chatRoomId) {
        ClientServer.getINSTANCE().uploadImage("Bearer " + id, image, chatRoomId).enqueue(new Callback<UploadImageResponse>() {
            @Override
            public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                if (response.code() == 200) {
                    uploadMutableLiveData.setValue(response.body());
                    Log.d(TAG, "onResponse: " + response.body().getStatus());
                } else {
                    Log.d(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
