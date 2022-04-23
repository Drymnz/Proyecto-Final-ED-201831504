package cunoc;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cunoc.Tree.NodeBinary;
import cunoc.Tree.Tree;

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
            return "["+String.valueOf(num)+"]";
        }
    }
    Tree<num> testTree = new Tree<num>();

    @Test
    public void fritTest() {
        testTree.add(new NodeBinary<num>(new num(1), 1));
        num a = testTree.getMainData();
        assertTrue(a.getNum() == 1);
    }
    @Test
    public void printTree(){
        int[] pru = {8,4,8,2,20,100,90,92,97};
        for (int i : pru) {
            testTree.add(new NodeBinary<num>(new num(i), i));
        }
        //String testPrint = testTree.printTree();
        //System.out.println(testPrint);
        assertTrue(true);
    }

    @Test
    public void addInOrderTest() {
        int[] pru = { 60, 47, 42, 32, 80, 7 };
        for (int i : pru) {
            testTree.add(new NodeBinary<num>(new num(i), i));
        }
        int[] array = testTree.inOrderArray();
        String prueva = "";
        //System.out.println("----------arry inOrden----------");
        for (int i : array) {
            prueva += "[" + i + "]";
        }
        //System.out.print(prueva);
        //System.out.println("----------arry inOrden----------");
        assertTrue(prueva.equals("[7][32][42][47][60][80]"));
    }
    @Test
    public void conversionStringToFile() {
        int[] pru = {8,4,8,2,20,100,90,92,97};
        for (int i : pru) {
            testTree.add(new NodeBinary<num>(new num(i), i));
        }
        //String testPrint = testTree.printTree();
        //System.out.println(testPrint);
        assertTrue(true);
    }
}
