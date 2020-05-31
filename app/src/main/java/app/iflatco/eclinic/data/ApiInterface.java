package app.iflatco.eclinic.data;

import java.util.HashMap;

import app.iflatco.eclinic.models.CateResponse;
import app.iflatco.eclinic.models.DrAvailableSlotsResponse;
import app.iflatco.eclinic.models.DrResponse;
import app.iflatco.eclinic.models.ResponseAppointment;
import app.iflatco.eclinic.models.ResponseModel;
import app.iflatco.eclinic.models.UserModel;
import app.iflatco.eclinic.models.DoctorDaysResponse;
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
    Call<ResponseModel> updateProfImage(@Header("Authorization") String authToken, @Part MultipartBody.Part image);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("slots/getDoctorDays")
    Call<DoctorDaysResponse> getDoctorDays(@Header("Authorization") String authToken, @Body HashMap<String, Integer> doctorId);

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
}
