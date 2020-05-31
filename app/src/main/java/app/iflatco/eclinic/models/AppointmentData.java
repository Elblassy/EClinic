package app.iflatco.eclinic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppointmentData {

    @SerializedName("appointment_status")
    @Expose
    private String appointmentStatus;
    @SerializedName("appointment_id")
    @Expose
    private Integer appointmentId;
    @SerializedName("slot_id")
    @Expose
    private Integer slotId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("date")
    @Expose
    private String date;

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public Integer getSlotId() {
        return slotId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getDate() {
        return date;
    }
}
