package cunoc.Logic.Tree;

public class Tree<T> implements Runnable {
    private final int wide = 10;
    private final String nameTree = "Arbol";
    private final String characterSpace = " ";
    private final String characterSons = "_";

    private NodeBinary<T> main = null;

    // Builder
    public Tree(NodeBinary<T> data) {
        add(data);
    }

    public Tree() {
    }

    // add node in tree
    public void add(NodeBinary<T> data) {
        // if the mein null
        if (this.main == null) {
            this.main = data;
        } else {
            this.main = addSort(data, this.main);
        }
        // sort the treee
        // new Thread(this).start();
        this.main = balanceTree(this.main);
    }

    // add node in tree returna boolean if add
    public boolean addBoolean(NodeBinary<T> data) {
        int items = this.getItemsCounter();
        this.add(data);
        int itemsTwo = this.getItemsCounter();
        if (items == itemsTwo) {
            return false;
        } else {
            return true;
        }
    }

    // add node according to the hierarchy
    private NodeBinary<T> addSort(NodeBinary<T> data, NodeBinary<T> branch) {
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

    // node search
    public boolean search(NodeBinary<T> data, NodeBinary<T> branch) {
        if (data != null && branch != null) {
            if (data.getData() == branch.getData()) {
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

    // node delete
    public boolean leafDeletion(NodeBinary<T> data, NodeBinary<T> branch) {
        if (data != null && branch != null) {
            if (data.getValue() == branch.getValue()) {
                if (branch.getSonL() == null && branch.getSonR() == null) {
                    if (branch.getFather().getSonL().getValue() == data.getValue()) {
                        branch.getFather().setSonL(null);
                        return true;
                    } else if (branch.getFather().getSonR().getValue() == data.getValue()) {
                        branch.getFather().setSonR(null);
                        return true;
                    }
                }
                return false;
            } else if (branch.getValue() < data.getValue()) {
                return search(data, branch.getSonR());
            } else {
                return search(data, branch.getSonL());
            }
        } else {
            return false;
        }
    }

    // go to last left node
    private NodeBinary<T> lastLTree(NodeBinary<T> sort) {
        if (sort.getSonL() != null) {
            sort.getSonL().setFather(sort);
            return lastLTree(sort.getSonL());
        } else {
            return sort;
        }
    }

    // go to last right node
    private NodeBinary<T> lastRTree(NodeBinary<T> sort) {
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
        int level = levelNode(sort);
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
        // for subtree
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
        NodeBinary<T> add = new NodeBinary<T>(rota.getData(), rota.getValue());
        if (rota.getSonL() != null) {
            NodeBinary<T> addSon = new NodeBinary<T>(rota.getSonL().getData(), rota.getSonL().getValue());
            addSon.setSonL(rota.getSonL().getSonL());
            addSon.setSonR(rota.getSonL().getSonR());
            add.setSonR(rota.getSonR());
            rota = addSort(add, addSon);
        }
        return rota;
    }

    // simple rotation to the right
    private NodeBinary<T> rotateR(NodeBinary<T> rota) {
        NodeBinary<T> add = new NodeBinary<T>(rota.getData(), rota.getValue());
        if (rota.getSonR() != null) {
            // copy node
            NodeBinary<T> addSon = new NodeBinary<T>(rota.getSonR().getData(), rota.getSonR().getValue());
            addSon.setSonL(rota.getSonR().getSonL());
            addSon.setSonR(rota.getSonR().getSonR());
            add.setSonL(rota.getSonL());
            rota = addSort(add, addSon);
        }
        return rota;
    }

    // node height
    private int levelNode(NodeBinary<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + maxNum(levelNode(node.getSonL()), levelNode(node.getSonR()));
        }
    }

    // node total items
    private int sumTotalNodeItems(NodeBinary<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + sumTotalNodeItems(node.getSonL()) + sumTotalNodeItems(node.getSonR());
        }
    }

    // sum total of the node
    public int sumTotalNode(NodeBinary<T> node) {
        if (node == null) {
            return 0;
        } else {
            return node.getValue() + sumTotalNode(node.getSonL()) + sumTotalNode(node.getSonR());
        }
    }

    // compare two ints to return the larger
    private int maxNum(int one, int two) {
        return (one > two) ? one : two;
    }

    // array of tree in order
    public int[] inOrderArray() {
        int itemsCounter = this.getItemsCounter();
        int[] ret = new int[itemsCounter];
        NodeBinary<T>[] arrayNodes = this.arrayTreeInOrden();
        for (int i = 0; i < arrayNodes.length; i++) {
            ret[i] = arrayNodes[i].getValue();
        }
        return ret;
    }

    // Node preOrden
    public NodeBinary<T>[] arrayTreeInPreOrden() {
        return arrayPreOrden(this.main, new NodeBinary[this.getItemsCounter()]);
    }

    // Node preOrden
    public NodeBinary<T>[] arrayPreOrden(NodeBinary<T> node, NodeBinary<T>[] arrayNode) {
        if (node != null) {
            addArrayNodeBinary(node, arrayNode);
            arrayNode = arrayPreOrden(node.getSonL(), arrayNode);
            arrayNode = arrayPreOrden(node.getSonR(), arrayNode);
        }
        return arrayNode;
    }

    // return tree in pos orden
    public NodeBinary<T>[] arrayTreeInPosOrden() {
        return arrayPosOrden(this.main, new NodeBinary[this.getItemsCounter()]);
    }

    // Node posOrden
    public NodeBinary<T>[] arrayPosOrden(NodeBinary<T> node, NodeBinary<T>[] arrayNode) {
        if (node != null) {
            arrayNode = arrayPosOrden(node.getSonL(), arrayNode);
            arrayNode = arrayPosOrden(node.getSonR(), arrayNode);
            addArrayNodeBinary(node, arrayNode);
        }
        return arrayNode;
    }

    // return tree an inorden
    public NodeBinary<T>[] arrayTreeInOrden() {
        return arrayInOrden(this.main, new NodeBinary[this.getItemsCounter()]);
    }

    // Node inOrden
    public NodeBinary<T>[] arrayInOrden(NodeBinary<T> node, NodeBinary<T>[] arrayNode) {
        if (node != null) {
            arrayNode = arrayInOrden(node.getSonL(), arrayNode);
            addArrayNodeBinary(node, arrayNode);
            arrayNode = arrayInOrden(node.getSonR(), arrayNode);
        }
        return arrayNode;
    }

    // add node in array of pre or pos or in orden
    private void addArrayNodeBinary(NodeBinary<T> node, NodeBinary<T>[] arrayNode) {
        for (int i = 0; i < arrayNode.length; i++) {
            if (arrayNode[i] == null) {
                arrayNode[i] = node;
                break;
            }
        }
    }

    private boolean searchArrayInt(int data, int[] array) {
        for (int i : array) {
            if (i == data) {
                return true;
            }
        }
        return false;
    }

    // return a string from the tree
    public String printTreeHorizontally() {
        String String = basePrintTree();
        String += stringTree(wide, this.main) + "\n";
        return String;
    }

    // return a string from the tree
    public String printTreeVertical() {
        String String = basePrintTree();
        int length = this.getHeight();
        int center = this.wide * length;
        for (int i = 0; i < length; i++) {
            NodeBinary<T> arrayNode[] = arrayNodeLevel(i);
            for (int j = 0; j < arrayNode.length; j++) {
                String += stringNode(center, arrayNode[j]);
            }
            center /= 2;
            String += "\n";
        }
        return String;
    }

    // number pow
    private int pow(int base, int pow) {
        if (pow > 1) {
            return base * pow(base, pow - 1);
        } else if (pow == 0) {
            return 1;
        } else if (pow == 1) {
            return base;
        }
        return 0;
    }

    // return an array of tree levels
    public NodeBinary<T>[] arrayNodeLevel(int level) {
        NodeBinary<T>[] arrayNode = new NodeBinary[pow(2, level)];
        this.arrayNodeLevel(level, this.main, arrayNode, 0, arrayNode.length);
        return arrayNode;
    }

    // return an array of node levels
    private NodeBinary<T>[] arrayNodeLevel(int level, NodeBinary<T> node, NodeBinary<T>[] arrayNode, int leftMin,
            int rightMax) {
        if (node != null) {
            if (level == 0) {
                if (leftMin < arrayNode.length) {
                    arrayNode[leftMin] = node;
                }
                return arrayNode;
            }
            int check = rightMax - leftMin;
            check /= 2;
            arrayNode = arrayNodeLevel(level - 1, node.getSonL(), arrayNode, leftMin, rightMax - check);
            arrayNode = arrayNodeLevel(level - 1, node.getSonR(), arrayNode, rightMax - check, rightMax);
        }
        return arrayNode;
    }

    // the title of the tree
    public String basePrintTree() {
        String String = "";
        int wide = this.wide;
        wide *= this.getHeight();
        wide /= 2;
        String = printCharacter(wide, "#") + nameTree + printCharacter(wide, "#") + "\n";
        return String;
    }

    // return the tree horizontally
    private String stringTree(int dividers, NodeBinary<T> node) {
        String String = "";
        if (node != null) {
            int son = dividers / 2;
            String += stringTree(son, node.getSonL());
            String += printCharacter(dividers, characterSpace) + stringNode(dividers, node) + "\n";
            String += stringTree(son, node.getSonR());
        }
        return String;
    }

    // return string the node
    private String stringNode(int dividers, NodeBinary<T> node) {
        String String = "";
        if (node != null) {
            String = printCharacter(dividers, characterSpace) + node.getData().toString()
                    + printCharacter(dividers, characterSpace);
            return String;
        } else {
            String = printCharacter((dividers * 2 + 3), characterSpace);
            return String;
        }
    }

    // print character sequence
    private String printCharacter(int rerun, String character) {
        String returnString = "";
        for (int i = 0; i < rerun; i++) {
            returnString += character;
        }
        return returnString;
    }

    // Get
    public int getItemsCounter() {
        return this.sumTotalNodeItems(this.main);
    }

    public T getMainData() {
        return main.getData();
    }

    public NodeBinary<T> getMain() {
        return this.main;
    }

    public int getHeight() {
        return this.levelNode(this.main);
    }

    public int getWide() {
        return this.wide;
    }

    public boolean isEmpty() {
        return this.main == null;
    }
}
