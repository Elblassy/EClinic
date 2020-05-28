package app.iflatco.eclinic.data;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import app.iflatco.eclinic.models.CateResponse;
import app.iflatco.eclinic.models.DoctorDaysResponse;
import app.iflatco.eclinic.models.DrAvailableSlotsResponse;
import app.iflatco.eclinic.models.DrResponse;
import app.iflatco.eclinic.models.ResponseModel;
import app.iflatco.eclinic.models.UserModel;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientServer {

    private ApiInterface apiInterface;
    private static ClientServer INSTANCE;
    private static final String BASE_URL = "https://clinice.herokuapp.com/api/";

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    private ClientServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static ClientServer getINSTANCE() {
        if (null == INSTANCE) {
            INSTANCE = new ClientServer();
        }
        return INSTANCE;
    }

    public Call<ResponseModel> signUp(UserModel userModel) {
        return apiInterface.signUp(userModel);
    }

    public Call<ResponseModel> signIn(HashMap<String, String> phoneNumber) {
        return apiInterface.signIn(phoneNumber);
    }

    public Call<ResponseModel> updateFbToken(String authToken, HashMap<String, String> fbToken) {
        return apiInterface.updateFbToken(authToken, fbToken);
    }

    public Call<DrResponse> getDrList(String authToken, HashMap<String, Integer> cateId) {
        return apiInterface.getDrList(authToken, cateId);
    }

    public Call<CateResponse> getCategories(String authToken) {
        return apiInterface.getCategories(authToken);
    }

    public Call<ResponseModel> updateProfImage(String authToken, MultipartBody.Part image) {
        return apiInterface.updateProfImage(authToken, image);
    }

    public Call<DoctorDaysResponse> getDoctorDays(String authToken, HashMap<String, Integer> doctorId) {
        return apiInterface.getDoctorDays(authToken, doctorId);
    }

    public Call<DrAvailableSlotsResponse> getAvailableSlots(String authToken, HashMap<String, Object> doctorId) {
        return apiInterface.getAvailableSlots(authToken, doctorId);
    }

}
