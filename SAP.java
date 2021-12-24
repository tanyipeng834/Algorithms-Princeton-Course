import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private final Digraph graph;
    

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        if (G == null) {
            throw new java.lang.IllegalArgumentException("Argument to the constructor cannot be null");
        }
        this.graph =new Digraph(G);
        // initialize marked[] to make it all false by default 

    }
 
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        validateVertex(v);
        validateVertex(w);
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(this.graph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(this.graph, w);
        
        int shortestPath = this.graph.V();

        for(int i =0; i<this.graph.V(); i++){   // go through all the vertices in the graph\
            int totalDistance =0;
            if(bfsV.hasPathTo(i) && bfsW.hasPathTo(i)){
                totalDistance = bfsV.distTo(i) + bfsW.distTo(i);
                if(totalDistance < shortestPath){
                    shortestPath = totalDistance;
                    
                }

           
            }
        }
        if (shortestPath == this.graph.V()) {
            return -1;
        }
        else{
            return shortestPath;
        }
       


        

       

    }
 
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   
    public int ancestor(int v, int w){
        validateVertex(v);
        validateVertex(w);
        // go through all the vertices in the graph and get the one with the shortest path
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(this.graph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(this.graph, w);
        int shortestAncestor = 0;
        double shortestPath = Double.POSITIVE_INFINITY;

        for(int i =0; i<this.graph.V(); i++){   // go through all the vertices in the graph\
            int totalDistance =0;
            if(bfsV.hasPathTo(i) && bfsW.hasPathTo(i)){
                totalDistance = bfsV.distTo(i) + bfsW.distTo(i);
                if(totalDistance < shortestPath){
                    shortestPath = totalDistance;
                    shortestAncestor = i;
                }

           
            }
        }
        if (shortestPath == Double.POSITIVE_INFINITY) {
            return -1;
        }
        else{
            return shortestAncestor;
        }


    }
 
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        validateVertices(v);
        validateVertices(w);
        if(getCount(v)==0||getCount(w)==0){
            return -1;
        }
        // for any vertex in v and any vertex in w u want to get the shortest ancestral path
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(this.graph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(this.graph, w);
        
        int  shortestPath = this.graph.V();
          
        for(int i =0; i<this.graph.V(); i++){   // go through all the vertices in the graph\
            int totalDistance =0;
            if(bfsV.hasPathTo(i) && bfsW.hasPathTo(i)){
                totalDistance = bfsV.distTo(i) + bfsW.distTo(i);
                if(totalDistance < shortestPath){
                    shortestPath = totalDistance;
                    
                }

           
            }
        }
        if (shortestPath == this.graph.V()) {
            return -1;
        }
        else{
            return shortestPath;
        }
       


        

    }
 
    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        validateVertices(v);
        validateVertices(w);
        if(getCount(v)==0||getCount(w)==0){
            return -1;
        }

        // for any vertex in v and any vertex in w u want to get the shortest ancestral path
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(this.graph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(this.graph, w);
        
        int shortestAncestor = 0;
        double shortestPath = Double.POSITIVE_INFINITY;
          
        for(int i =0; i<this.graph.V(); i++){   // go through all the vertices in the graph\
            int totalDistance =0;
            if(bfsV.hasPathTo(i) && bfsW.hasPathTo(i)){
                totalDistance = bfsV.distTo(i) + bfsW.distTo(i);
                if(totalDistance < shortestPath){
                    shortestPath = totalDistance;
                    shortestAncestor = i;
                }

           
            }
        }
      if (shortestPath == Double.POSITIVE_INFINITY) {
            return -1;
        }
        else{
            return shortestAncestor;
        }


    }
    private void validateVertex(int v) {
        int V = this.graph.V();
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        for (Integer v : vertices) {
    
            if (v == null) {
                throw new IllegalArgumentException("vertex is null");
            }
            validateVertex(v);
        }
        
    }
    private int getCount(Iterable<Integer> vertices){
        int count =0;
        for (Integer v : vertices) {
            count++;
        }
        return count;
    }
   
 
    // do unit testing of this class
    public static void main(String[] args){
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
        int v = StdIn.readInt();
        int w = StdIn.readInt();
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }

    }
 }