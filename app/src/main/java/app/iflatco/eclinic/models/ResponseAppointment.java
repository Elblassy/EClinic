package app.iflatco.eclinic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAppointment {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private AppointmentData data;

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public AppointmentData getData() {
        return data;
    }
}
