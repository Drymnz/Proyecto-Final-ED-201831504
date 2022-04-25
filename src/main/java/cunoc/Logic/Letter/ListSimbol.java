package cunoc.Logic.Letter;

public enum ListSimbol {

    CLOVER("♣"), HEART("♥"), DIAMOND("♦"), APPLE("♠");

    private String simbol;

    private ListSimbol(String simbol) {
        this.simbol = simbol;
    }

    public String getSimbol() {
        return this.simbol;
    }
}
