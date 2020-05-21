package app.iflatco.eclinic.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class CustomSharedPref {

    // Sharedpref file name
    private static final String PREFER_NAME = "UserData";
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;
    private Context context;


    @SuppressLint("CommitPrefEdits")
    public CustomSharedPref(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREFER_NAME, MODE_PRIVATE);
        editor = pref.edit();
    }

    public boolean getSessionBoolean(String key) {
        return pref.getBoolean(key, false);
    }

    public String getSessionValue(String key) {
        return pref.getString(key, "none");
    }

    public void setPrefMobile(String mobile) {
        editor.putString("mobile", mobile);
        editor.apply();
    }
    public void setPrefFirstName(String firstName) {
        editor.putString("firstName", firstName);
        editor.apply();
    }
    public void setPrefLastName(String lastName) {
        editor.putString("lastName", lastName);
        editor.apply();
    }
    public void setPrefGender(String gender) {
        editor.putString("gender", gender);
        editor.apply();
    }
    public void setPrefBirthDate(String birthDate) {
        editor.putString("birthDate", birthDate);
        editor.apply();
    }
    public void setPrefWeight(String weight) {
        editor.putString("weight", weight);
        editor.apply();
    }
    public void setPrefHeight(String height) {
        editor.putString("height", height);
        editor.apply();
    }
    public void setPrefBmi(String bmi) {
        editor.putString("bmi", bmi);
        editor.apply();
    }

    public void setPrefSignUp(boolean signUp) {
        editor.putBoolean("signUp", signUp);
        editor.apply();
    }

    public void setPrefVerified(boolean verified) {
        editor.putBoolean("verified", verified);
        editor.apply();
    }

    public void setPrefTokenId(String tokenId) {
        editor.putString("tokenId", tokenId);
        editor.apply();
    }
}
