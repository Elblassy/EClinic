package app.iflatco.eclinic.ui.page_viewer.select_doctor;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.List;

import app.iflatco.eclinic.data.ClientServer;
import app.iflatco.eclinic.models.DrDataModel;
import app.iflatco.eclinic.models.DrResponse;
import app.iflatco.eclinic.utils.CustomSharedPref;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectDoctorViewModel extends AndroidViewModel {
    private static final String TAG = "SelectDoctorViewModel";

    MutableLiveData<List<DrDataModel>> listMutableLiveData;
    CustomSharedPref pref;

    public SelectDoctorViewModel(Application application) {
        super(application);
        listMutableLiveData = new MutableLiveData<>();
        pref = new CustomSharedPref(application.getBaseContext());
    }

    void getDrList(int id) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("category_id",id);

        ClientServer.getINSTANCE().getDrList("Bearer " + pref.getSessionValue("tokenId").trim(),map).enqueue(new Callback<DrResponse>() {
            @Override
            public void onResponse(Call<DrResponse> call, Response<DrResponse> response) {
                if (response.code() == 200) {
                    listMutableLiveData.setValue(response.body().getData());
                } else {
                    Log.d(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DrResponse> call, Throwable t) {

            }
        });
    }
}

