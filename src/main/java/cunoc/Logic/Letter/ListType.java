package cunoc.Logic.Letter;

public enum ListType {
    AS(1, "A"),
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SEX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "10"),
    J(11, "J"),
    Q(12, "Q"),
    K(13, "K");

    private int value;
    private String toString;

    private ListType(int value, String toString) {
        this.value = value;
        this.toString = toString;
    }

    @Override
    public String toString() {
        return this.toString;
    }

    public int getValue() {
        return this.value;
    }
}
