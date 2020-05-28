package app.iflatco.eclinic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<DrDataModel> data;

    public Boolean getStatus() {
        return status;
    }

    public List<DrDataModel> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
