package app.iflatco.eclinic.data;

import java.util.HashMap;

import app.iflatco.eclinic.models.ResponseModel;
import app.iflatco.eclinic.models.UserModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientServer {

    private ApiInterface apiInterface;
    private static ClientServer INSTANCE;
    private static final String BASE_URL = "https://clinice.herokuapp.com/api/";

    private ClientServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
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

    public Call<ResponseModel> signUp(UserModel userModel){
        return apiInterface.signUp(userModel);
    }

    public Call<ResponseModel> signIn(HashMap<String,String> phoneNumber){
        return apiInterface.signIn(phoneNumber);
    }

    public Call<ResponseModel> updateFbToken(String authToken, HashMap<String, String> fbToken) {
        return apiInterface.updateFbToken(authToken, fbToken);
    }

}
