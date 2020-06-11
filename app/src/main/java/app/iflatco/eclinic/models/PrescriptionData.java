package app.iflatco.eclinic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrescriptionData {

    @SerializedName("prescription_id")
    @Expose
    private Integer prescriptionId;
    @SerializedName("room_id")
    @Expose
    private String roomId;
    @SerializedName("diagnose")
    @Expose
    private String diagnose;
    @SerializedName("prescription")
    @Expose
    private String prescription;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public String getPrescription() {
        return prescription;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
