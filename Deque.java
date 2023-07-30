
import java.util.Iterator;
import java.util.NoSuchElementException;

//import jdk.internal.jshell.tool.resources.version;
// use array for constant worst time implementation of queue and stack
public class Deque<Item> implements Iterable<Item> {
    private Node head;
    private Node last;
    private int size;
    private class Node{
        Item item;
        Node next;
        Node before;

    private Node(Item item){
        this.item = item;

    }
    }

    // construct an empty deque
    // time complexity
    public Deque(){
        head = null;

    }

    // is the deque empty?
    public boolean isEmpty(){
        return size==0;

    }

    // return the number of items on the deque
    public int size(){
        return size;

    }

    // add the item to the front
    // timecomplexity )(1)
    // memory complexity 0(1)
    public void addFirst(Item item){
        if (item == null){
            throw new IllegalArgumentException("Null Argument is not allowed in this function.");
        }
        if (isEmpty()){
            head = new Node(item);
            last = head;
            size = 1;

        }
        else{
            Node oldFirst = head;
            head = new Node(item);
            head.next = oldFirst;
            oldFirst.before = head;
            size ++;






        }

        
    }

   

    // add the item to the back
    //handle the case of 1 
    public void addLast(Item item){
        if (item ==null){
            throw new IllegalArgumentException("Null Argument is not allowed in this function.");
        }
        if (isEmpty()){
            head = new Node(item);
            last = head;
            size = 1;}
        else{        
        Node newLast = new Node(item);
        newLast.next = null;
        last.next = newLast;
        // change the last of the linked list to the new last.
        newLast.before = last;
        last = newLast;
        size++;
        }

    }

    // remove and return the item from the front
    public Item removeFirst(){
        if (isEmpty()){
            throw new NoSuchElementException("Empty deque");

        }
        if(size ==1){
            Node oldHead = head;
            head =null;
            last = null;
            size --;
            return oldHead.item;
        }
        else{
        Node oldFirst = head;
        head = oldFirst.next;
        head.before = null;
        size--;
        return oldFirst.item;
        }
    }

    // remove and return the item from the back
    public Item removeLast(){
        if (isEmpty()){
            throw new NoSuchElementException("Empty deque");

        }
        if(size ==1){
            Node oldLast = last;
            head = null;
            last = null;
            size--;
            return oldLast.item;
        }
        else{
        Node oldLast = last;
        Node secondLastElement = last.before;
        last = secondLastElement;
        secondLastElement.next = null;
        size --;
        return oldLast.item;}

    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return  new LinkedListIterator();

    }
    private class LinkedListIterator implements Iterator<Item>{
        private Node current = head;
        @Override
        public boolean hasNext(){
            return current != null;
        }
        @Override
        public void remove(){
            throw new UnsupportedOperationException("This operation is not supported.");
        }
        @Override
        public Item next(){
            try{
            Item currentItem = current.item;
            current = current.next;
            return currentItem;
        }
            
            catch(NullPointerException e ){
                throw new NoSuchElementException("There is no more items to return");
            }
        }
            


        


    }

    // unit testing (required)
    public static void main(String[] args){
        Deque<Integer> queue = new Deque<>();
        queue.addFirst(1);
        queue.addFirst(3);
        queue.addLast(8);
        Iterator <Integer> q = queue.iterator();
        
       
       
        

        
        while (queue.head!=null){
            System.out.println(queue.head.item);
            queue.head = queue.head.next;

        }
        System.out.println(queue.last.item);
        System.out.println((q.next()));
        System.out.println((q.next()));
        System.out.println((q.next()));
        


    }

}