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
    }

    @Test
    public void fritTest() {
        Tree<num> pruevas = new Tree<num>(new NodeBinary<num>(new num(1), 1));
        num a = pruevas.getMainData();
        assertTrue(a.getNum() == 1);
    }

    @Test
    public void addInOrderTest() {
        Tree<num> pruevas = new Tree<num>();
        int[] pru = { 60, 47, 42, 32, 80, 7 };
        for (int i : pru) {
            pruevas.add(new NodeBinary<num>(new num(i), i));
        }
        // pru = {7,32,42,47,60,80};
        int[] array = pruevas.inOrderArray();
        String prueva = "";
        boolean test = false;
        System.out.println("----------arry inOrden----------");
        for (int i : array) {
            prueva += "[" + i + "]";
        }
        System.out.print(prueva);
        assertTrue(prueva.equals("[7][32][42][47][60][80]"));
    }
}
