import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item [] arrayQueue;
    private int arrayPointer = 0;

    // construct an empty randomized queue
    public RandomizedQueue(){
        arrayQueue =(Item[]) new Object[1];
        

    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return (arrayPointer==0);

    }

    // return the number of items on the randomized queue
    public int size(){
        return arrayPointer;

    }

    

    // add the item
    public void enqueue(Item item){
        if(item == null){
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (arrayPointer == arrayQueue.length){
            resize(2*arrayQueue.length);
        }
        arrayQueue[arrayPointer++] = item;



    }
    private void resize (int capacity){
        Item [] copy =(Item[]) new Object[capacity];
        for (int i =0;i<arrayPointer;i++){
            copy[i] = arrayQueue[i];



        }
        arrayQueue = copy;


    }

    // remove and return a random item
    public Item dequeue(){
        if (isEmpty()){
            throw new NoSuchElementException("There is no element in the queue");
        }
        int randomRange = StdRandom.uniform(0,arrayPointer);
        Item randomItem = arrayQueue[randomRange];
        arrayQueue[randomRange] = arrayQueue[--arrayPointer];
        arrayQueue[arrayPointer] = null;
        if (arrayPointer>0 && arrayPointer==arrayQueue.length/4){
            resize(arrayQueue.length/2);
        }
        return randomItem;


    }

    // return a random item (but do not remove it)
    public Item sample(){
        if (isEmpty()){
            throw new NoSuchElementException("There is no element in the queue");
        }
        int randomRange = StdRandom.uniform(0,arrayPointer);
        Item randomItem = arrayQueue[randomRange];
        return randomItem;

        

        



    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return  new randomArrayIterator();

    
    
    
    
    }
    private class randomArrayIterator implements Iterator<Item>{
        Item [] shuffledArray;
        int iteratorPointer = 0;
        public randomArrayIterator(){
            shuffledArray = (Item[]) new Object[arrayPointer];
            for (int i =0;i<arrayPointer;i++){
                shuffledArray[i] = arrayQueue[i];
            }
            StdRandom.shuffle(shuffledArray);


        }
        @Override
        public boolean hasNext(){
            return iteratorPointer<arrayPointer;
           


        }
        @Override
        public void remove(){
            throw new UnsupportedOperationException("This operation is not supported.");

        }
        @Override
        public Item next(){
            if (!hasNext()){
                throw new NoSuchElementException("There is no next element.");
            }
            return shuffledArray[iteratorPointer++];

        }

    }

    
    public static void main(String[] args){
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(5);
        queue.enqueue(9);
        System.out.println(queue.sample());
        System.out.println(queue.size());
        
        Iterator <Integer> q = queue.iterator();
        System.out.println(q.next());
        System.out.println(q.next());
        
    }

}