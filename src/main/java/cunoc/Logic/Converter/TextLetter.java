package cunoc.Logic.Converter;

import cunoc.Logic.Letter.Letter;
import cunoc.Logic.Letter.ListSimbol;
import cunoc.Logic.Letter.ListType;

public class TextLetter {
    private String text;
    private ListSimbol type;
    private int typeInt;

    public TextLetter(String text, int typeInt) {
        this.text = text;
        this.typeInt = typeInt;
    }

    public Letter converter() {
        String array[] = this.text.split("\"");
        ListType value = null;
        switch (this.typeInt) {
            case 163:
                type = ListSimbol.CLOVER;
                break;
            case 165:
                type = ListSimbol.HEART;
                break;
            case 166:
                type = ListSimbol.DIAMOND;
                break;
            case 160:
                type = ListSimbol.APPLE;
                break;
            default:
                break;
        }
        switch (array[array.length - 1]) {
            case "1":
                value = ListType.AS;
                break;
            case "2":
                value = ListType.TWO;
                break;
            case "3":
                value = ListType.THREE;
                break;
            case "4":
                value = ListType.FOUR;
                break;
            case "5":
                value = ListType.FIVE;
                break;
            case "6":
                value = ListType.SEX;
                break;
            case "7":
                value = ListType.SEVEN;
                break;
            case "8":
                value = ListType.EIGHT;
                break;
            case "9":
                value = ListType.NINE;
                break;
            case "10":
                value = ListType.TEN;
                break;
            case "J":
                value = ListType.J;
                break;
            case "Q":
                value = ListType.Q;
                break;
            case "K":
                value = ListType.K;
                break;
            default:
                break;
        }
        return new Letter(this.type, value);
    }
}
