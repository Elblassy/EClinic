package app.iflatco.eclinic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrDataModel {

    @SerializedName("doctor_id")
    @Expose
    private Integer doctorId;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("sub_category")
    @Expose
    private String subCategory;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("avaliable")
    @Expose
    private Boolean avaliable;
    @SerializedName("price")
    @Expose
    private String price;

    public Integer getDoctorId() {
        return doctorId;
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

    public String getCountry() {
        return country;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public String getPicture() {
        return picture;
    }

    public Boolean getAvaliable() {
        return avaliable;
    }

    public String getPrice() {
        return price;
    }
}
