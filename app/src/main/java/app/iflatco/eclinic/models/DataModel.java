package app.iflatco.eclinic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataModel {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("bmi")
    @Expose
    private Integer bmi;
    @SerializedName("fb_token_id")
    @Expose
    private String fbTokenId;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("role")
    @Expose
    private String role;

    public String getToken() {
        return token;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getBmi() {
        return bmi;
    }

    public String getFbTokenId() {
        return fbTokenId;
    }

    public String getGender() {
        return gender;
    }

    public String getRole() {
        return role;
    }
}
