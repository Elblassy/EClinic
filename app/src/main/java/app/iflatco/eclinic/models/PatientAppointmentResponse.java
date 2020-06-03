package app.iflatco.eclinic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PatientAppointmentResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    @Nullable
    private List<PatientAppointmentData> data;

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<PatientAppointmentData> getData() {
        return data;
    }
}
