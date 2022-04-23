package cunoc.Tree;

public class Tree<T> implements Runnable {
    private NodeBinary<T> main = null;
    private int itemsCounter = 0;

    public int getItemsCounter() {
        return this.itemsCounter;
    }

    public T getMainData() {
        return main.getData();
    }

    public Tree() {
    }

    public Tree(NodeBinary<T> data) {
        add(data);
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
        //new Thread(this).start();
        this.main = sortTree(this.main);
        // System.out.println("agrege :"+data.getValue());
        this.itemsCounter++;
    }

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

    private boolean search(NodeBinary<T> data, NodeBinary<T> branch) {
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

    private NodeBinary<T> lastLTree(NodeBinary<T> sort) {
        if (sort.getSonL() != null) {
            sort.getSonL().setFather(sort);
            return lastLTree(sort.getSonL());
        } else {
            return sort;
        }
    }

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
        this.main = this.sortTree(this.main);
    }

    private NodeBinary<T> sortTree(NodeBinary<T> sort) {
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
        if (sort.getSonL() != null) {
            sort.setSonL(sortTree(sort.getSonL()));
        }
        if (sort.getSonR() != null) {
            sort.setSonR(sortTree(sort.getSonR()));
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
            NodeBinary<T> addSon = new NodeBinary<T>(rota.getSonR().getData(), rota.getSonR().getValue());
            addSon.setSonL(rota.getSonR().getSonL());
            addSon.setSonR(rota.getSonR().getSonR());
            add.setSonL(rota.getSonL());
            rota = addSon;
            addSort(add, rota);
        }
        return rota;
    }

    private int levelNode(NodeBinary<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + maxNum(levelNode(node.getSonL()), levelNode(node.getSonR()));
        }
    }

    private int maxNum(int one, int two) {
        return (one > two) ? one : two;
    }

    // array of tree pos orden
    public int[] inOrderArray() {
        int[] ret = new int[this.itemsCounter];
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

    private boolean searchArrayInt(int data, int[] array) {
        for (int i : array) {
            if (i == data) {
                return true;
            }
        }
        return false;
    }

    // print in console
    public void printTree() {
        System.out.println("----------Arbol----------");
        int level = levelNode(this.main);
        NodeBinary<T> printNode = this.main;
        for (int i = 0; i < level; i++) {
            System.out.print(printNode.getValue());
            // brothers
            printNode = this.main;
            for (int j = 0; j < i; j++) {
                printNode = printNode.getFather();
            }
            System.out.println("");
        }
    }

}
