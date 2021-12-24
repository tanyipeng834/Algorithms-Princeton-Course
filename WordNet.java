import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Set;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.Bag;


public class WordNet {
    private int count = 0;
    // this is the hashmap to store all the synsets that are read from the 'synsets.txt' file
    private HashMap<Integer,String[] > synsets;
    private Set<String>  allNouns;
    private final Digraph graph;
    private final SAP sap;
    private HashMap<String,Bag<Integer> > nounToIds;

    

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        if (synsets == null || hypernyms == null ) {
            throw new java.lang.IllegalArgumentException("Argument to the constructor cannot be null");
        }
        // read the sysn
        readSynsets(synsets);
        // create a digraph that can link the  hyponym(which is more general) to the hypernym(which is more specific)
        this.graph = new Digraph(this.count);
        // read the hypernyms
        In in = new In(hypernyms);
        while(in.hasNextLine()){
            String line = in.readLine();
            String[] values = line.split(",");
            int v = Integer.parseInt(values[0]);
            for(int i = 1; i < values.length; i++){
                this.graph.addEdge(v,Integer.parseInt(values[i]));
            }
            
        }
        // check if the digraph is a DAG
        
        // this will only run if it is not cyclic
        this.sap = new SAP(this.graph);
        DirectedCycle dc = new DirectedCycle(graph);
        if (dc.hasCycle()){
            throw new IllegalArgumentException("The graph is cyclic");
        }
        int rootNum = 0;
        for (int v = 0; v < count; v++)
            if (this.graph.outdegree(v) == 0)
                rootNum++;
        if (rootNum != 1)
            throw new IllegalArgumentException("Input has " + rootNum + " roots.");







    }
 
    // returns all WordNet nouns
    public Iterable<String> nouns(){
        return this.allNouns;

    }
 
    // is the word a WordNet noun?
    public boolean isNoun(String word){
        if (word==null) {
            throw new java.lang.IllegalArgumentException("Argument to the method cannot be null");
        }
        return this.allNouns.contains(word);

    }
 
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        if(!isNoun(nounA)||!isNoun(nounB)||nounA==null||nounB==null){
            throw new java.lang.IllegalArgumentException("Argument to the method cannot be null");
        }
        
        // this is the s
        Bag<Integer> setA = this.nounToIds.get(nounA);
        Bag<Integer> setB = this.nounToIds.get(nounB);
        
            
        return this.sap.length(setA,setB); 
        

    }
 
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        if(!isNoun(nounA)||!isNoun(nounB)||nounA==null||nounB==null){
            throw new java.lang.IllegalArgumentException("Argument to the method cannot be null");
        }
        Bag<Integer> setA = this.nounToIds.get(nounA);
        Bag<Integer> setB = this.nounToIds.get(nounB);
        int key = this.sap.ancestor(setA,setB); 
        String [] value =this.synsets.get(key);
        String joinedString = String.join(" ", value);
        return joinedString;
        
        

    }
    private void readSynsets(String fileName){
        this.synsets = new HashMap<Integer,String[]>();
        this.allNouns = new HashSet<String>();
        this.nounToIds = new HashMap<String,Bag<Integer>>();
        
        In in = new In(fileName);
        while(in.hasNextLine()){
            String line = in.readLine();
            this.count++;
            // split the file text based on the comma to extract the 
            String[] tokens = line.split(",");

            String[] nouns = tokens[1].split(" ");
            allNouns.addAll(Arrays.asList(nouns));
            this.synsets.put(Integer.parseInt(tokens[0]),nouns);

            // add the synsets to the hashmap
            for(String noun:nouns){
                if(!nounToIds.containsKey(noun)){
                    Bag<Integer> ids = new Bag<Integer>();
                    ids.add(Integer.parseInt(tokens[0]));
                    nounToIds.put(noun,ids);
                }
                else{
                Bag<Integer> ids = nounToIds.get(noun);
                ids.add(Integer.parseInt(tokens[0]));
                
                nounToIds.put(noun,ids);
                }
            }

            
            
        }

    }
    
    
    // do unit testing of this class
    public static void main(String[] args){
        // create a file stream for the text file and use it to create an In object
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.println(wordNet.count);
        System.out.println(wordNet.isNoun("Tekashi69"));
        wordNet.distance("hello", "baby");



    }
 }