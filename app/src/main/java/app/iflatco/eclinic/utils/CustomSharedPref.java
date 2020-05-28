package app.iflatco.eclinic.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    public void setProfImage(String profImage) {
        editor.putString("profImage", profImage);
        editor.apply();
    }


    public int getAge(String birth) {

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date == null) return 0;

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);


        return today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
    }

    public void logOut() {
        editor.clear();
        editor.apply();
    }

}
