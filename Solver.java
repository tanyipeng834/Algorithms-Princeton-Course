import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;
public class Solver {
    MinPQ<searchNode> priorityQueue;
    Board inital;
    MinPQ<searchNode> priorityQueueTwin;
    

    private class searchNode implements Comparable<searchNode>{
        Board board;
        int numberOfMoves =0;
        int manhattanPriority =0;
        // pointer to the seach node presviously
        searchNode before; 

        @Override
        public int compareTo(searchNode node){
            if (this.manhattanPriority> node.manhattanPriority){
                return +1;
            }
            else if (this.manhattanPriority==node.manhattanPriority){
                return 0;
            }
            else{
                return -1;
            }
        }
        // build a constructor for the search node object
        private searchNode(Board board,int numberOfMoves, int manhattanPriority,searchNode before){
            this.board = board;
            this.numberOfMoves = numberOfMoves;
            this.manhattanPriority = manhattanPriority;
            this.before = before;
        }







    }
    public Solver(Board initial){
        if(initial==null){
            throw new IllegalArgumentException("Board cannot ne null ");
        }
        this.inital = initial;
        this.priorityQueue = new MinPQ();
        // get the twin board 
        Board twin = this.inital.twin();
        this.priorityQueueTwin = new MinPQ();
        // construct the initial search node
        searchNode initialNode = new searchNode(initial,0,initial.manhattan(),null);
        searchNode initalNodeTwin = new searchNode(twin,0,twin.manhattan(),null);
        this.priorityQueue.insert(initialNode);
        this.priorityQueueTwin.insert(initalNodeTwin);

        searchNode minNode = initialNode;
        searchNode minNodeTwin = initalNodeTwin;
        // keep going until one og the board reaches the goal
        while (!this.priorityQueue.min().board.isGoal()&&!this.priorityQueueTwin.min().board.isGoal()){
        
        // do not delete min if it is the only board
            minNode = this.priorityQueue.delMin();
            minNodeTwin = this.priorityQueueTwin.delMin();
        
        for(Board board:minNode.board.neighbors()){
            // convert all boards into search nodes and add them into the priority queue
            if(board!=minNode.board){
            searchNode node = new searchNode(board,minNode.numberOfMoves+1,board.manhattan()+minNode.numberOfMoves+1,minNode);
            this.priorityQueue.insert(node);
            }

        }
        for(Board board:minNodeTwin.board.neighbors()){
            // convert all boards into search nodes and add them into the priority queue
            if(board!=minNode.board){
            searchNode node = new searchNode(board,minNodeTwin.numberOfMoves+1,board.manhattan()+minNodeTwin.numberOfMoves+1,minNodeTwin);
            this.priorityQueueTwin.insert(node);
            }

        }


        }



        


    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        if (this.priorityQueue.min().board.isGoal()){
            return true;

        }
        else{
            return false;
        }

    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        if(this.isSolvable()){
            // return the number of moves to reach this stage
            return this.priorityQueue.min().numberOfMoves;

        }
        else{
            return -1;
        }

    }

    // sequence of boards in a shortest solution; null if unsolvable
    // can use a stack implementatin  where we trace back from the goal board 
    // and iterate in the LIFO manner
    public Iterable<Board> solution(){
        if(this.isSolvable()){
            Stack <Board> stack = new Stack<>();
            searchNode currentNode = this.priorityQueue.min();
            while(currentNode!=null){
                stack.push(currentNode.board);
                currentNode = currentNode.before;

            }
            return stack;
        }
            // return this 
            
            


        else{
            return null;
        }

    }


    // test client (see below) 
    public static void main(String[] args){
        int [][] puzzleBoard = {{0,1,3},
        {4,2,5},
        {7,8,6}};
    Board initial = new Board(puzzleBoard);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
    }

    
}
