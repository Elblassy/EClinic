package app.iflatco.eclinic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

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
    private int weight;
    @SerializedName("height")
    @Expose
    private int height;
    @SerializedName("bmi")
    @Expose
    private int bmi;
    @SerializedName("fb_token_id")
    @Expose
    private String fbTokenId;
    @SerializedName("gender")
    @Expose
    private String gender;


    public UserModel(String phoneNumber, String firstName, String lastName, String birthDate,
                     int weight, int height, int bmi, String fbTokenId, String gender) {
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.fbTokenId = fbTokenId;
        this.gender = gender;
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

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public int getBmi() {
        return bmi;
    }

    public String getFbTokenId() {
        return fbTokenId;
    }

    public String getGender() {
        return gender;
    }
}
