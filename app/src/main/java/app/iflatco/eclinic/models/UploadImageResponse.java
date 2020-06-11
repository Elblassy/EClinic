package app.iflatco.eclinic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

public class UploadImageResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    @Nullable
    private String data;

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Nullable
    public String getData() {
        return data;
    }
}
