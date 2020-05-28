package app.iflatco.eclinic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CateResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<CateDataModel> data = null;

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<CateDataModel> getData() {
        return data;
    }
}
