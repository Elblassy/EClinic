package app.iflatco.eclinic.models;

public class ChatModel {

    private String message;
    private String image;
    private String userName;
    private String roll;

    public ChatModel(String message,String image, String userName, String roll) {
        this.message = message;
        this.image = image;
        this.userName = userName;
        this.roll = roll;
    }

    public String getMessage() {
        return message;
    }

    public String getUserName() {
        return userName;
    }

    public String getRoll() {
        return roll;
    }

    public String getImage() {
        return image;
    }
}
