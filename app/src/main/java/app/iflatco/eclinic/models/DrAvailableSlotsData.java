package app.iflatco.eclinic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrAvailableSlotsData {
    @SerializedName("slot_id")
    @Expose
    private Integer slotId;
    @SerializedName("doctor_id")
    @Expose
    private Integer doctorId;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("slot_time")
    @Expose
    private String slotTime;
    @SerializedName("available")
    @Expose
    private Integer available;

    public Integer getSlotId() {
        return slotId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getSlotTime() {
        return slotTime;
    }

    public Integer getAvailable() {
        return available;
    }
}
