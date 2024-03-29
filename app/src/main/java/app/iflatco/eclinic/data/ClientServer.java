package app.iflatco.eclinic.data;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import app.iflatco.eclinic.models.CateResponse;
import app.iflatco.eclinic.models.DoctorDaysResponse;
import app.iflatco.eclinic.models.DrAvailableSlotsResponse;
import app.iflatco.eclinic.models.DrResponse;
import app.iflatco.eclinic.models.JoinToAppointmentResponse;
import app.iflatco.eclinic.models.OldMessagesModel;
import app.iflatco.eclinic.models.PatientAppointmentResponse;
import app.iflatco.eclinic.models.PrescriptionResponse;
import app.iflatco.eclinic.models.ResponseAppointment;
import app.iflatco.eclinic.models.ResponseModel;
import app.iflatco.eclinic.models.UploadImageResponse;
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

    public Call<ResponseAppointment> pendingAppointment(String authToken, HashMap<String, Object> appointment) {
        return apiInterface.pendingAppointment(authToken, appointment);
    }


    public Call<ResponseAppointment> confirmAppointment(String authToken, HashMap<String, Object> appointmentId) {
        return apiInterface.confirmAppointment(authToken, appointmentId);
    }

    public Call<ResponseAppointment> cancelAppointment(String authToken, HashMap<String, Object> appointmentId) {
        return apiInterface.cancelAppointment(authToken, appointmentId);
    }

    public Call<PatientAppointmentResponse> getPatientAppointment(String authToken) {
        return apiInterface.getPatientAppointment(authToken);
    }

    public Call<PatientAppointmentResponse> getFinishedAppointment(String authToken) {
        return apiInterface.getFinishedAppointment(authToken);
    }


    public Call<JoinToAppointmentResponse> joinToAppointment(String authToken, HashMap<String, Object> appointmentId) {
        return apiInterface.joinToAppointment(authToken, appointmentId);
    }

    public Call<OldMessagesModel> getOldMessages(String authToken, HashMap<String, Object> roomId) {
        return apiInterface.getOldMessages(authToken, roomId);
    }

    public Call<UploadImageResponse> uploadImage(String authToken, MultipartBody.Part image, MultipartBody.Part chatRoomId) {
        return apiInterface.uploadImage(authToken, image, chatRoomId);
    }

    public Call<PrescriptionResponse> getPrescription(String authToken, HashMap<String, Object> data) {
        return apiInterface.getPrescription(authToken, data);
    }
}
