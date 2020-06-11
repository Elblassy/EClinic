package app.iflatco.eclinic.ui.old_chat;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

import app.iflatco.eclinic.data.ClientServer;
import app.iflatco.eclinic.models.OldMessagesModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OldChatViewModel extends ViewModel {
    private static final String TAG = "OldChatViewModel";

    MutableLiveData<OldMessagesModel> oldMessagesMutableLiveData;

    public OldChatViewModel() {
        oldMessagesMutableLiveData = new MutableLiveData<>();
    }

    void getOldMessages(String auth, String roomId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("room_id", roomId);

        ClientServer.getINSTANCE().getOldMessages("Bearer " + auth, map).enqueue(new Callback<OldMessagesModel>() {
            @Override
            public void onResponse(Call<OldMessagesModel> call, Response<OldMessagesModel> response) {
                if (response.code() == 200) {
                    oldMessagesMutableLiveData.setValue(response.body());
                } else {
                    Log.d(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<OldMessagesModel> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}
