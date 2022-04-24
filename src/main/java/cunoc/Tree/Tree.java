package cunoc.Tree;

public class Tree<T> implements Runnable {
    private final int wide = 12;
    private final String nameTree = "Arbol";
    private final String characterSpace = " ";
    private final String characterSons = "_";

    private NodeBinary<T> main = null;
    private int itemsCounter = 0;

    // Builder
    public Tree(final NodeBinary<T> data) {
        add(data);
    }

    public Tree() {
    }

    // add node in tree
    public void add(final NodeBinary<T> data) {
        // if the mein null
        if (this.main == null) {
            this.main = data;
        } else {
            this.main = addSort(data, this.main);
        }
        // sort the treee
        // new Thread(this).start();
        this.main = balanceTree(this.main);
        this.itemsCounter++;
    }

    private NodeBinary<T> addSort(final NodeBinary<T> data, final NodeBinary<T> branch) {
        if (!(branch.getValue() == data.getValue())) {
            if (branch.getValue() < data.getValue()) {
                if (branch.getSonR() == null) {
                    branch.setSonR(data);
                    data.setFather(branch);
                } else {
                    addSort(data, branch.getSonR());
                }
            } else {
                if (branch.getSonL() == null) {
                    branch.setSonL(data);
                    data.setFather(branch);
                } else {
                    addSort(data, branch.getSonL());
                }
            }
        }
        return branch;
    }

    private boolean search(final NodeBinary<T> data, final NodeBinary<T> branch) {
        if (data != null && branch != null) {
            if (data.getValue() == branch.getValue()) {
                return true;
            } else if (branch.getValue() < data.getValue()) {
                return search(data, branch.getSonR());
            } else {
                return search(data, branch.getSonL());
            }
        } else {
            return false;
        }
    }

    private NodeBinary<T> lastLTree(final NodeBinary<T> sort) {
        if (sort.getSonL() != null) {
            sort.getSonL().setFather(sort);
            return lastLTree(sort.getSonL());
        } else {
            return sort;
        }
    }

    private NodeBinary<T> lastRTree(final NodeBinary<T> sort) {
        if (sort.getSonR() != null) {
            sort.getSonR().setFather(sort);
            return lastLTree(sort.getSonR());
        } else {
            return sort;
        }
    }

    @Override
    public void run() {
        // automatically balance
        this.main = this.balanceTree(this.main);
    }

    // the tree swings only with simple movements
    private NodeBinary<T> balanceTree(NodeBinary<T> sort) {
        final int level = levelNode(sort);
        if (level > 1) {
            int balance = levelNode(sort.getSonL()) - levelNode(sort.getSonR());
            do {
                if (balance > 1) {
                    sort = this.rotateL(sort);
                } else if (balance < -1) {
                    sort = this.rotateR(sort);
                }
                balance = levelNode(sort.getSonL()) - levelNode(sort.getSonR());
            } while (balance > 1 | balance < -1);
        }
        //for subtrees
        if (sort.getSonL() != null) {
            sort.setSonL(balanceTree(sort.getSonL()));
        }
        if (sort.getSonR() != null) {
            sort.setSonR(balanceTree(sort.getSonR()));
        }
        return sort;
    }

    // simple rotation to the left
    private NodeBinary<T> rotateL(NodeBinary<T> rota) {
        final NodeBinary<T> add = new NodeBinary<T>(rota.getData(), rota.getValue());
        if (rota.getSonL() != null) {
            final NodeBinary<T> addSon = new NodeBinary<T>(rota.getSonL().getData(), rota.getSonL().getValue());
            addSon.setSonL(rota.getSonL().getSonL());
            addSon.setSonR(rota.getSonL().getSonR());
            add.setSonR(rota.getSonR());
            rota = addSort(add, addSon);
        }
        return rota;
    }

    // simple rotation to the right
    private NodeBinary<T> rotateR(NodeBinary<T> rota) {
        final NodeBinary<T> add = new NodeBinary<T>(rota.getData(), rota.getValue());
        if (rota.getSonR() != null) {
            final NodeBinary<T> addSon = new NodeBinary<T>(rota.getSonR().getData(), rota.getSonR().getValue());
            addSon.setSonL(rota.getSonR().getSonL());
            addSon.setSonR(rota.getSonR().getSonR());
            add.setSonL(rota.getSonL());
            rota = addSon;
            addSort(add, rota);
        }
        return rota;
    }

    // node height
    private int levelNode(final NodeBinary<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + maxNum(levelNode(node.getSonL()), levelNode(node.getSonR()));
        }
    }

    // compare two ints to return the larger
    private int maxNum(final int one, final int two) {
        return (one > two) ? one : two;
    }

    // array of tree in order
    public int[] inOrderArray() {
        final int[] ret = new int[this.itemsCounter];
        int counter = 0;
        NodeBinary<T> printNode = lastLTree(this.main);
        while (counter < this.itemsCounter) {
            if (printNode != null) {
                if (!searchArrayInt(printNode.getValue(), ret)) {
                    ret[counter] = printNode.getValue();
                    counter++;
                } else {
                    if (printNode.getFather() != null) {
                        printNode = printNode.getFather();
                        ret[counter] = printNode.getValue();
                        counter++;
                    }
                }
            }
            if (printNode.getSonR() == null && printNode.getSonL() == null) {
                if (printNode.getFather() != null) {
                    printNode = printNode.getFather();
                }
            } else {
                if (printNode.getSonR() != null) {
                    printNode = lastLTree(printNode.getSonR());
                } else if (printNode.getSonL() != null) {
                    printNode = lastLTree(printNode.getSonL());
                }
            }
        }
        return ret;
    }

    private boolean searchArrayInt(final int data, final int[] array) {
        for (final int i : array) {
            if (i == data) {
                return true;
            }
        }
        return false;
    }

    // return a string from the tree
    public String printTreeHorizontally() {
        String finalString = basePrintTree();
        finalString += stringTree(wide, this.main) + "\n";
        return finalString;
    }

    public String printTreeVertical() {
        String finalString = basePrintTree();
        //finalString += stringTreeVertical(wide, this.main) + "\n";
        int length = this.getHeight();
        int center = this.wide*length;
        for (int i = 0; i < length; i++) {
            center/=2;
            NodeBinary<T> arrayNode[] = arrayNodeLevel(i, this.main, new NodeBinary[(i * 2) + 1], 0);
            for (int j = 0; j < arrayNode.length; j++) {
                finalString += stringNode(center, arrayNode[j]); 
            }
            finalString += "\n";
        }
        return finalString;
    }

    private NodeBinary<T>[] arrayNodeLevel(int level, NodeBinary<T> node, NodeBinary<T>[] arrayNode, int poss) {
        if (poss < arrayNode.length) {
            if (level == 0) {
                arrayNode[poss] = node;
            } else if (level > 0) {
                poss = (poss==1)?  poss+1:poss;
                if (node == null) {
                    arrayNode = arrayNodeLevel((level - 1), null, arrayNode, poss*1);
                    arrayNode = arrayNodeLevel((level - 1), null, arrayNode, (poss*1)+1);
                } else {
                    arrayNode = arrayNodeLevel((level - 1), node.getSonL(), arrayNode, poss*1);
                    arrayNode = arrayNodeLevel((level - 1), node.getSonR(), arrayNode, (poss*1)+1);
                }
            }
        }
        return arrayNode;
    }

    private NodeBinary<T>[] addArray(NodeBinary<T>[] arrayNode, NodeBinary<T> add) {
        for (int i = 0; i < arrayNode.length; i++) {
            if (arrayNode[i] == null) {
                arrayNode[i] = add;
            }
        }
        return arrayNode;
    }

    private String stringTreeVertical(final int dividers, final NodeBinary<T> node) {
        String finalString = "";
        if (node == null)
            return finalString;
        finalString += printCharacter(dividers, characterSpace) + stringNode(dividers, node) + "\n";
        if (node.getSonL() != null)
            finalString += stringNode(dividers, node.getSonL());
        if (node.getSonL() != null)
            finalString += stringNode(dividers, node.getSonR());

        finalString += stringTreeVertical(dividers, node.getSonL());
        finalString += stringTreeVertical(dividers, node.getSonR());
        return finalString;
    }

    private String basePrintTree() {
        String finalString = "";
        int wide = this.wide;
        wide *= this.getHeight();
        wide /= 2;
        finalString = printCharacter(wide, "#") + nameTree + printCharacter(wide, "#") + "\n";
        return finalString;
    }

    // return the tree horizontally
    private String stringTree(final int dividers, final NodeBinary<T> node) {
        String finalString = "";
        if (node != null) {
            final int son = dividers / 2;
            finalString += stringTree(son, node.getSonL());
            finalString += printCharacter(dividers, characterSpace) + stringNode(dividers, node) + "\n";
            finalString += stringTree(son, node.getSonR());
        }
        return finalString;
    }

    // return string the node
    private String stringNode(final int dividers, final NodeBinary<T> node) {
        String finalString = "";
        if (node != null) {
            finalString = printCharacter(dividers, characterSpace) + node.getData().toString()
                    + printCharacter(dividers, characterSons);
            return finalString;
        } else {
            finalString = printCharacter((dividers * 2 + 3), characterSpace);
            return finalString;
        }
    }

    // print character sequence
    private String printCharacter(final int rerun, final String character) {
        String returnString = "";
        for (int i = 0; i < rerun; i++) {
            returnString += character;
        }
        return returnString;
    }

    // Get
    public int getItemsCounter() {
        return this.itemsCounter;
    }

    public T getMainData() {
        return main.getData();
    }

    public int getHeight() {
        return this.levelNode(this.main);
    }
}
