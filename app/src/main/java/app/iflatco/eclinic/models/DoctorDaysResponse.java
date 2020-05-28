package app.iflatco.eclinic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DoctorDaysResponse {

    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<String> data;


    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getData() {
        return data;
    }
}
