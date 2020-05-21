package app.iflatco.eclinic.models;

public class DrModel {
    private String name;
    private String title;
    private int image;
    private String price;

    public DrModel(String name, String title,String price, int image) {
        this.name = name;
        this.title = title;
        this.image = image;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public String getPrice() {
        return price;
    }
}
