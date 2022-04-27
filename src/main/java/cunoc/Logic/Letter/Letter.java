package cunoc.Logic.Letter;

public class Letter {
    private ListSimbol simbol;
    private ListType value;
    private final String error = "?";

    public Letter(ListSimbol simbol, ListType value) {
        this.simbol = simbol;
        this.value = value;
    }

    public ListSimbol getSimbol() {
        return simbol;
    }

    public void setSimbol(ListSimbol simbol) {
        this.simbol = simbol;
    }

    public ListType getValue() {
        return value;
    }

    public void setValue(ListType value) {
        this.value = value;
    }

    public int getWeight() {
        return this.getValue().getValue() + this.getSimbol().getBleed();
    }

    //
    @Override
    public String toString() {
        String finalStringReturn = "";
        if (this.value != null) {
            finalStringReturn += this.value.toString();
        } else {
            finalStringReturn += Integer.toString(0);
        }
        if (this.simbol != null) {
            finalStringReturn += this.simbol.getSimbol();
        } else {
            finalStringReturn += error;
        }
        return finalStringReturn;
    }
}
