package app.iflatco.eclinic.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

public class ResponseModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    @Nullable
    private DataModel data;


    public ResponseModel(Boolean status, String message, @Nullable DataModel data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    @Nullable
    public DataModel getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
