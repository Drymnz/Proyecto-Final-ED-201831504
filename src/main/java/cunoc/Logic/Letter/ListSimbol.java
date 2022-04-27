package cunoc.Logic.Letter;

public enum ListSimbol {

    CLOVER("♣", 0),
    HEART("♥", 40),
    DIAMOND("♦",20),
    APPLE("♠", 60);

    private String simbol;
    private int bleed;// corrimiento

    private ListSimbol(String simbol, int bleed) {
        this.simbol = simbol;
        this.bleed = bleed;
    }

    public String getSimbol() {
        return this.simbol;
    }

    public int getBleed() {
        return this.bleed;
    }

    @Override
    public String toString() {
        return this.getSimbol();
    }
}
