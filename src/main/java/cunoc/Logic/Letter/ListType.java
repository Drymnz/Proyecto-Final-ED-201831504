package cunoc.Logic.Letter;

public enum ListType {
    AS(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SEX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    J(11),
    Q(12),
    K(13);

    private int value;

    private ListType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
