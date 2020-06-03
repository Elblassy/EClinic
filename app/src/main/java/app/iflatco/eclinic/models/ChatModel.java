package app.iflatco.eclinic.models;

public class ChatModel {

    private String message;
    private String userName;
    private String roll;

    public ChatModel(String message, String userName, String roll) {
        this.message = message;
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
}
