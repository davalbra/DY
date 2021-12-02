package Estructuras;

import java.util.Comparator;
import java.util.ListIterator;

/**
 *
 * @author danny
 * @param <T>
 */
public class ListaCircularDE<T> implements List<T> {

    CNode<T> last;
    int size = 0;

    
    public boolean movehead(int numero) {
        
        if (numero < 0) {
            numero = size + numero;
        }
        
        if (isEmpty() || numero == 0) {
            return false;
        } else if (numero > size) {
            last = getNode(size % numero);
        } else {
            last = getNode(numero);
        }
        return true;
    }

    @Override
    public boolean addFirst(T data) {
        CNode<T> newNode = new CNode(data);
        if (isEmpty()) {
            last = newNode;
        } else if (size == 1) {
            newNode.setNext(last);
            newNode.setPrevious(last);
            last.setPrevious(newNode);
            last.setNext(newNode);
        } else {
            newNode.setNext(last.getNext());
            newNode.setPrevious(last);
            last.getNext().setPrevious(newNode);
            last.setNext(newNode);
        }
        size++;
        return true;
    }

    @Override
    public boolean addLast(T data) {
        CNode<T> newNode = new CNode(data);
        if (isEmpty()) {
            last = newNode;
            last.setNext(newNode);
            last.setPrevious(newNode);
        }
        newNode.setNext(last);
        newNode.setPrevious(last.getPrevious());
        last.getPrevious().setNext(newNode);
        last.setPrevious(newNode);
        size++;
        return true;

    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else if (size == 1) {
            T data = last.getNext().getContent();
            size--;
            last = null;
            return data;
        } else {
            T data = last.getNext().getContent();
            last.getNext().getNext().setPrevious(last);
            last.getNext().setPrevious(null);
            last.setNext(last.getNext().getNext());
            size--;
            return data;
        }
    }

    @Override
    public T removeLast() {

        if (isEmpty()) {
            return null;
        } else if (size == 1) {
            T data = last.getContent();
            size--;
            last = null;
            return data;
        } else {
            T data = last.getContent();
            last.getPrevious().setNext(last.getNext());
            last.getNext().setPrevious(last.getPrevious());
            last = last.getPrevious();
            size--;
            return data;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        last = null;
        size = 0;
    }

    @Override
    public void add(int index, T data) {
        if (index == 0) {
            addFirst(data);
        } else if (index > 0 && index <= size) {
            CNode<T> aux = getNode(index);
            CNode<T> newNode = new CNode(data);
            aux.getPrevious().setNext(newNode);
            newNode.setPrevious(aux.getPrevious());
            newNode.setNext(aux);
            aux.setPrevious(newNode);
            size++;
        }
    }

    @Override
    public T remove(int index) {
        T element = null;
        if (isEmpty()) {
            return null;
        } else if (index > 0 && index < size) {
            CNode<T> aux = getNode(index);
            element = aux.getContent();
            aux.getPrevious().setNext(aux.getNext());
            aux.getNext().setPrevious(aux.getPrevious());
            aux.setContent(null);
            aux.setPrevious(null);
            aux.setNext(null);
            size--;
        }
        return element;
    }

    @Override
    public T get(int index) {
        T element = null;
        if (index >= 0 && index < size) {
            CNode<T> aux = getNode(index);
            element = aux.getContent();
        }
        return element;
    }

    @Override
    public T set(int index, T data) {
        T element = null;
        if (index >= 0 && index < size) {
            CNode<T> aux = getNode(index);
            element = aux.getContent();
            aux.setContent(data);
        }
        return element;
    }

    private CNode<T> getNode(int index) {
        CNode<T> aux = last;
        if (index <= size / 2) {
            for (; index > 0; index--) {
                aux = aux.getNext();
            }
        } else if (index > size / 2) {
            index = size - index;
            for (; index > 0; index--) {
                aux = aux.getPrevious();
            }
        }
        return aux;
    }

    
    public ListIterator<T> listIterator() {
        if (!this.isEmpty()) {
            ListIterator<T> it = new ListIterator<>() {
                private CNode<T> traveler =last;

                @Override
                public boolean hasNext() {
                    return size > 0;
                }

                @Override
                public T next() {
                    traveler = traveler.getNext();
                    return traveler.getPrevious().getContent();
                }

                @Override
                public boolean hasPrevious() {
                    return size > 0;
                }

                @Override
                public T previous() {
                    traveler = traveler.getPrevious();
                    return traveler.getNext().getContent();
                }

                @Override
                public int nextIndex() {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public int previousIndex() {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override
                public void set(T e) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                @Override

                public void add(T e) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

            };
            return it;
        }
        return null;
    }

    @Override

    public String toString() {
        String text = "[";
        int counter = 0;
        for (CNode<T> node = last; counter < size; counter++) {
            text += node.getContent().toString();
            if (counter != size - 1) {
                text += ", ";
            }
            node = node.getNext();
        }
        return text + "]";
    }

}
