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


    public CNode(T content) {
        this.content = content;
        this.next=this;
        this.previous=this;
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
