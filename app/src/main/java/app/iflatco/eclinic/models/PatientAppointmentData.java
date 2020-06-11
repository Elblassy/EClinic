package app.iflatco.eclinic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientAppointmentData {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("room_id")
    @Expose
    private String roomId;
    @SerializedName("appointment_id")
    @Expose
    private Integer appointmentId;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("appointment_status")
    @Expose
    private String appointmentStatus;
    @SerializedName("ar")
    @Expose
    private String ar;
    @SerializedName("en")
    @Expose
    private String en;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("start_time")
    @Expose
    private String startTime;


    public String getRoomId() {
        return roomId;
    }

    public String getDate() {
        return date;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public String getDay() {
        return day;
    }

    public String getAr() {
        return ar;
    }

    public String getEn() {
        return en;
    }

    public String getStartTime() {
        return startTime;
    }
}
