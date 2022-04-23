package cunoc.Tree;

public class NodeBinary<T> {
    private T data;
    private NodeBinary<T> father = null;
    private NodeBinary<T> sonR = null;
    private NodeBinary<T> sonL = null;
    private int value;

    public NodeBinary(T data, int value) {
        this.data = data;
        this.value = value;
    }

    public int getChildCounter() {
        return ((sonR != null && sonL == null) || (sonL != null && sonR == null)) ? 1
                : (sonL != null & sonR != null) ? 2 : 0;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public NodeBinary<T> getFather() {
        return father;
    }

    public void setFather(NodeBinary<T> father) {
        this.father = father;
    }

    public NodeBinary<T> getSonR() {
        return sonR;
    }

    public void setSonR(NodeBinary<T> sonR) {
        this.sonR = sonR;
    }

    public NodeBinary<T> getSonL() {
        return sonL;
    }

    public void setSonL(NodeBinary<T> sonL) {
        this.sonL = sonL;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
