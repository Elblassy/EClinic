package app.iflatco.eclinic.data;

import java.util.HashMap;

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
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;

interface ApiInterface {

    @POST("auth/signup")
    Call<ResponseModel> signUp(@Body UserModel userModel);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("auth/signin")
    Call<ResponseModel> signIn(@Body HashMap<String, String> phoneNumber);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PATCH("auth/updateFirebaseToken")
    Call<ResponseModel> updateFbToken(@Header("Authorization") String authToken,
                                      @Body HashMap<String, String> fbToken);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("doctors/getDoctors")
    Call<DrResponse> getDrList(@Header("Authorization") String authToken, @Body HashMap<String, Integer> cateId);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("categories/getCategories")
    Call<CateResponse> getCategories(@Header("Authorization") String authToken);


    @Multipart
    @PATCH("user/updateImage")
    Call<ResponseModel> updateProfImage(@Header("Authorization") String authToken,
                                        @Part MultipartBody.Part image);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("slots/getDoctorDays")
    Call<DoctorDaysResponse> getDoctorDays(@Header("Authorization") String authToken,
                                           @Body HashMap<String, Integer> doctorId);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("slots/getOpenSlots")
    Call<DrAvailableSlotsResponse> getAvailableSlots(@Header("Authorization") String authToken,
                                                     @Body HashMap<String, Object> doctorId);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("appointments/addAppointment")
    Call<ResponseAppointment> pendingAppointment(@Header("Authorization") String authToken,
                                                 @Body HashMap<String, Object> doctorId);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PATCH("appointments/confirmAppointment")
    Call<ResponseAppointment> confirmAppointment(@Header("Authorization") String authToken,
                                                 @Body HashMap<String, Object> doctorId);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PATCH("appointments/cancelAppointment")
    Call<ResponseAppointment> cancelAppointment(@Header("Authorization") String authToken,
                                                @Body HashMap<String, Object> doctorId);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("appointments/upcomingAppointments")
    Call<PatientAppointmentResponse> getPatientAppointment(@Header("Authorization") String authToken);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("appointments/finishedAppointments")
    Call<PatientAppointmentResponse> getFinishedAppointment(@Header("Authorization") String authToken);


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PATCH("appointments/joinUserToAppointment")
    Call<JoinToAppointmentResponse> joinToAppointment(@Header("Authorization") String authToken,
                                                      @Body HashMap<String, Object> doctorId);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("messages/getFinishedMessages")
    Call<OldMessagesModel> getOldMessages(@Header("Authorization") String authToken,
                                          @Body HashMap<String, Object> roomId);

    @Multipart
    @POST("messages/uploadFile")
    Call<UploadImageResponse> uploadImage(@Header("Authorization") String authToken,
                                          @Part MultipartBody.Part image,
                                          @Part MultipartBody.Part chatRoomId);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("prescriptions/getPrescription")
    Call<PrescriptionResponse> getPrescription(@Header("Authorization") String authToken,
                                               @Body HashMap<String, Object> doctorId);

}
