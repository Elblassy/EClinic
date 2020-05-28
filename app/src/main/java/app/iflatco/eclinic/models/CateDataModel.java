package app.iflatco.eclinic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CateDataModel {
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("en")
    @Expose
    private String en;
    @SerializedName("ar")
    @Expose
    private String ar;

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getEn() {
        return en;
    }

    public String getAr() {
        return ar;
    }
}
