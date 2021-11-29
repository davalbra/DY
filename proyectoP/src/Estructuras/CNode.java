package Estructuras;

/**
 *
 * @author danny
 * @param <T>
 */
public class CNode<T> {

    private T content;
    private CNode<T> previous;
    private CNode<T> next;
    private int i;
    private int j;

    public CNode(T content,int i,int j) {
        this.content = content;
        this.next=this;
        this.previous=this;
        this.i=i;
        this.j=j;
                
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public CNode<T> getPrevious() {
        return previous;
    }

    public void setPrevious(CNode<T> previous) {
        this.previous = previous;
    }

    public CNode<T> getNext() {
        return next;
    }

    public void setNext(CNode<T> next) {
        this.next = next;
    }

}
