package app.iflatco.eclinic.models;

public class SlotesFilteratiton {

    private int sloteId;
    private String startTime;
    private String actualTime;
    private String date;

    public SlotesFilteratiton(int sloteId, String startTime, String actualTime, String date) {
        this.sloteId = sloteId;
        this.startTime = startTime;
        this.actualTime = actualTime;
        this.date = date;
    }

    public int getSloteId() {
        return sloteId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getActualTime() {
        return actualTime;
    }

    public String getDate() {
        return date;
    }
}
