
package Estructuras;

import java.util.Iterator;


public class ArrayList<E> implements List<E> {
    
    private int effectiveSize;
    private int capacity;
    private E[] elements;
    
    public ArrayList(int capacity){
        this.capacity = capacity;
        elements = (E[]) (new Object[capacity]);
        effectiveSize = 0;
    }

    public ArrayList() {
        elements = (E[])(new Object[capacity]); // arreglo
        effectiveSize = 0;
    }
    
    private boolean isFull(){
        return effectiveSize == capacity;
    }
    
    private void addCapacity(){
        E[] tmp = (E[]) new Object[capacity*2];
        for (int i = 0; i < capacity; i++) {
            tmp[i] = elements[i];
        }
        elements = tmp;
        capacity = capacity*2;
    }

    @Override
    public boolean movehead(int numero) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addFirst(E e) {
        if(e == null){
            return false;
        }
        else if(isEmpty()){
            elements[0] = e;
            effectiveSize++;
            return true;
        }
        
        else if(isFull()){
            addCapacity();
        }
        
        for(int i = effectiveSize - 1; i>= 0; i--){
            elements[i+1] = elements[i];
        }
        elements[0] = e;
        effectiveSize++;
        return true;
    }

    @Override
    public boolean addLast(E e) {
        if(e == null){
            return false;
        }
        else if(isEmpty()){
            elements[0] = e;
            effectiveSize++;
            return true;
        }
        else if ( isFull()){
            addCapacity();
        }
        elements[effectiveSize++] = e;
        return true;
    }

    @Override
    public E removeFirst() {
        if(isEmpty()) return (E) elements;
        
        
        for(int i = 0; i<effectiveSize -2; i++){
            elements[i] = elements[i+1];
        }
        
        elements[--effectiveSize] = null;
        
        return (E) elements; 
    }

    @Override
    public E removeLast() {
        E e = elements[effectiveSize-1];
        if (isEmpty()) return e; 
        elements[--effectiveSize] = null;
        return e;
    }

    @Override
    public int size() {
        return effectiveSize;
    }

    @Override
    public boolean isEmpty() {
        return effectiveSize == 0;
    }

    @Override
    public void clear() {
        if (isEmpty()) return;
        capacity = effectiveSize;
        elements = (E[]) new Object[capacity];
        effectiveSize = 0;
    }

    @Override
    public E add(int index, E element) {
        if(isEmpty()){
            return (E) elements;
        }
        else if(isFull()){
            addCapacity();
        }
        for (int i = effectiveSize -1; i >= index; i--) {
            elements[i] = elements[i+1];
        }
        elements[index] = element;
        effectiveSize++;
        return (E) elements;
        
    }

    @Override
    public E remove(int index) {
        if(isEmpty()){
            return (E) elements;
        }
        else if(index >= effectiveSize){
            return (E) elements;
        }
        for (int i = index; i < effectiveSize - 1; i++) {
            elements[i] = elements[i+1];
        }
        elements[effectiveSize - 1] = null;
        effectiveSize--;
        return (E) elements;
    }

    @Override
    public E get(int index) {
        return (E) elements[index];
    }

    @Override
    public E set(int index, E element) {
        elements[index] = element;
        return (E) elements;
    }

    
    Iterator<E> it = new Iterator<E>(){
        
        int cursor = 0;
        @Override
        public boolean hasNext() {
            return cursor < effectiveSize;
        }

        @Override
        public E next() {
            return elements[cursor++];
        }
        
    };

    @Override
    public String toString() {
        if(isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i = 0; i < effectiveSize -1 ; i++ ){
            sb.append(elements[i]);
            sb.append(",");
        }
        sb.append(elements[effectiveSize - 1]);
        sb.append("]");
        return sb.toString();
    }
    
    
    
    
}
