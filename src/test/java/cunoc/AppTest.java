package cunoc;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import cunoc.Logic.Converter.TextToImage;
import cunoc.Logic.Converter.TreeGraphConverter;
import cunoc.Logic.Letter.Letter;
import cunoc.Logic.Letter.ListSimbol;
import cunoc.Logic.Letter.ListType;
import cunoc.Logic.Tree.NodeBinary;
import cunoc.Logic.Tree.Tree;


/**
 * Unit test for simple App.
 */
public class AppTest {
    class num {
        int num;

        num(int num) {
            this.num = num;
        }

        public int getNum() {
            return this.num;
        }

        @Override
        public String toString() {
            return "[" + String.valueOf(num) + "]";
        }
    }

    Tree<num> testTree = new Tree<num>();

    @Test
    public void checkAddNode() {
        assertTrue(testTree.addBoolean(new NodeBinary<num>(new num(1), 1)));
    }

    @Test
    public void printTreeString() {
        int[] pru = { 8, 4, 8, 2, 20, 100, 90, 92, 97 };
        for (int i : pru) {
            testTree.add(new NodeBinary<num>(new num(i), i));
        }
        // String testPrint = testTree.printTreeHorizontally();
        // System.out.println(testPrint);
        assertTrue(true);
    }

    @Test
    public void printTreeVerticalString() {
        int[] pru = { 8, 4, 8, 2, 20, 100, 90, 92, 97 };
        for (int i : pru) {
            testTree.add(new NodeBinary<num>(new num(i), i));
        }
        // String testPrint = testTree.printTreeVertical();
        // System.out.println(testPrint);
        assertTrue(true);
    }

    @Test
    public void checkInOrder() {
        int[] pru = { 60, 47, 42, 32, 80, 7 };
        for (int i : pru) {
            testTree.add(new NodeBinary<num>(new num(i), i));
        }
        int[] array = testTree.inOrderArray();
        String prueva = "";
        // System.out.println("----------arry inOrden----------");
        for (int i : array) {
            prueva += "[" + i + "]";
        }
        // System.out.print(prueva);
        // System.out.println("----------arry inOrden----------");
        assertTrue(prueva.equals("[7][32][42][47][60][80]"));
    }

    @Test
    public void conversionStringToFile() {
        int[] pru = { 166, 197, 161, 11, 119, 184, 206, 54, 67, 166, 101, 253 };
        for (int i : pru) {
            testTree.add(new NodeBinary<num>(new num(i), i));
        }
        String testPrint = testTree.printTreeVertical();
        File file = new File("image.png");
        if (file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                assertTrue(false);
            }
        }
        boolean accept = new TextToImage(testPrint, file, 25).converter();
        assertTrue(accept);
    }

    @Test
    public void conversionTreeToFileImage() {
        int[] pru = { 166, 197, 161, 11, 119, 184, 206, 54, 67, 166, 101, 253 };
        for (int i : pru) {
            testTree.add(new NodeBinary<num>(new num(i), i));
        }
        File file = new File("image01.png");
        if (file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                assertTrue(false);
            }
        }
        assertTrue(new TextToImage(file)
                .converterFinal(new TreeGraphConverter<>(testTree, 50).converter()));
    }

    @Test
    public void treeAsLetter() {

        Tree<Letter> testTreeLetter = new Tree<Letter>();
        Letter[] pru = {
                new Letter(ListSimbol.HEART, ListType.FIVE),
                new Letter(ListSimbol.DIAMOND, ListType.EIGHT),
                new Letter(ListSimbol.CLOVER, ListType.FOUR),
                new Letter(ListSimbol.HEART, ListType.THREE),
                new Letter(ListSimbol.CLOVER, ListType.AS),
                new Letter(ListSimbol.HEART, ListType.NINE)
        };
        for (Letter i : pru) {
            testTreeLetter.add(new NodeBinary<Letter>(i, i.getValue().getValue()));
        }
        File file = new File("image02.png");
        if (file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                assertTrue(false);
            }
        }
        assertTrue(new TextToImage(file)
                .converterFinal(new TreeGraphConverter<>(testTreeLetter, 50).converter()));
    }
}
