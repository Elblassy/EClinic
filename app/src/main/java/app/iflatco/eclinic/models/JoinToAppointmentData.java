package app.iflatco.eclinic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JoinToAppointmentData {
    @SerializedName("room_id")
    @Expose
    private String roomId;

    public String getRoomId() {
        return roomId;
    }
}
