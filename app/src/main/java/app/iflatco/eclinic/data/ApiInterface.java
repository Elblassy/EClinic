package app.iflatco.eclinic.data;

import java.util.HashMap;

import app.iflatco.eclinic.models.ResponseModel;
import app.iflatco.eclinic.models.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

interface ApiInterface {

    @POST("auth/signup")
    Call<ResponseModel> signUp(@Body UserModel userModel);

    Call<ResponseModel> signIn(@Body HashMap<String,String> phoneNumber);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PATCH("auth/updateFirebaseToken")
    Call<ResponseModel> updateFbToken(@Header("Authorization") String authToken,
                                      @Body HashMap<String, String> fbToken);
}
