import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Iterator;

public class Board {
    private int [][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        this.tiles= tiles;

    }
                                           
    // string representation of this board
    public String toString(){
        String string = Integer.toString(this.tiles.length)+"\n";
        // iterate through the row
        for (int i=0;i<(this.tiles.length);i++){
            // iterate through all the 
            for (int j =0; j<this.tiles[0].length;j++ ){
                // print the value in the respective part of the grid
                if(j==0){
                    string+=" ";
                }
               string +=Integer.toString(this.tiles[i][j]);
               if(j!=this.tiles[0].length-1 ){
                   string +=" ";
               }

            }
            // do not add an  new line for the last line as it is redundadnt
            if (i!=this.tiles.length-1){
                string +="\n";

            }
            
        }
        return string;

    }
    

    // board dimension n
    public int dimension(){
        return this.tiles.length;

    }
   

    // number of tiles out of place
    public int hamming(){
        int counter =0;
        // this is to make a counter to represent the row number in the grid
        for(int i =0;i<this.dimension();i++){
            for(int j =0;j<this.tiles[0].length;j++){
                // take care of the empty square in the board
                if (this.tiles[i][j]!=0 &&this.tiles[i][j]!=(i*3+(j+1))){

                        counter++;

                    }

                }

            }
            return counter;

            }
            


    
 
    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int manhattanDistances=0;
        for(int i=0;i<this.dimension();i++){
            for(int j =0;j<this.tiles[0].length;j++){
                // get the correct column by getting the remainder by dividing the digit by the row number and getting the remainder
                // need to add a one as the row number start from 0
                if(this.tiles[i][j]!=0){

                
                int correctColumn = (this.tiles[i][j]-1)%(this.dimension());
                // After subtracting the remainder,we would get an number which is divisible by the number of rows in the grid
                int correctRow = (this.tiles[i][j]-1- correctColumn)/this.dimension();
                manhattanDistances+=Math.abs(correctColumn-j);
                manhattanDistances+=Math.abs(correctRow-i);
                }
            }
        }
        return manhattanDistances;


    }

    

    // is this board the goal board?
    public boolean isGoal(){
        if(this.hamming()==0){
            return true;
        }
        else{
            return false;
        }

    }
    

    // board will be the same if there are the same number of wrong tiles and the manhattan distance is the same
    public boolean equals(Object y){
        if(y==null){
            throw new IllegalArgumentException("Y cannot be null");
        }
        // check if it is the same board class type
        if(this.getClass()==y.getClass()){
           Board that = (Board)y;
            if (Arrays.deepEquals(this.tiles,that.tiles)){
                return true;
            }

        }
        return false;
        

        }

        // finish get space to get the space in the tiles and also copy function to copy a 2 dimensionall array
        // do swap function also

    private int[] getSpace(){
        int [] array = new int[2];
        for ( int i =0; i<this.dimension(); i++){
            for(int j =0;j<this.dimension();j++){
                if(this.tiles[i][j]==0){
                    array[0]=i;
                    array[1]=j;
        
                    return array;
                    
                }
            }
        }
        return array;

    }
    private int[][] copyArray(){
        // allocate sufficient memory for the copy of the 
        int [] [] copyTiles = new int [this.dimension()][this.dimension()];
        for (int i =0; i<this.dimension();i++){
            for(int j =0;j<this.dimension();j++){
                copyTiles[i][j] = this.tiles[i][j];
            }
        }
        // onced the for loop has eneded return the copied array
        return copyTiles;

    }

    

    
    

    // all neighboring boards
    // lets add the boards in a clockwise fashion
    public Iterable<Board> neighbors(){
        int[] blankSquare = this.getSpace();
        Queue<Board> queue = new LinkedList<>(); 
        if(blankSquare[0]>0){
            // swap the blank square with the  square on top of it if it is not the top square

            queue.add(new Board(this.swap(blankSquare[0],blankSquare[1],blankSquare[0]-1,blankSquare[1])));
        }
        // now we will swap the blnk square with the square to the right
        if(blankSquare[1]<this.dimension()-1){
            queue.add(new Board(this.swap(blankSquare[0],blankSquare[1],blankSquare[0],blankSquare[1]+1)));
        }
        // now we will swap the blank square with the square bottom to it
        if(blankSquare[0]<this.dimension()-1){
            queue.add(new Board(this.swap(blankSquare[0],blankSquare[1],blankSquare[0]+1,blankSquare[1])));
        }
        if (blankSquare[1]>0){
            queue.add(new Board(this.swap(blankSquare[0],blankSquare[1],blankSquare[0],blankSquare[1]-1)));
        }

        // after adding all the neighbours to it,we will return the iterable
        return  queue;
        

    }
    private int[][] swap(int blankRow,int blankColumn,int squareRow,int squareColumn){
        // use this as a variable to store the temp variable to swap the columns 
        int [][]copyArray= this.copyArray();
        // swap this with the blank row
        copyArray[blankRow][blankColumn] = copyArray[squareRow][squareColumn];
        // previous filled square will become blank
        copyArray[squareRow][squareColumn]=0;
        // after making these changes , i will return this board back
        return copyArray;


    }

    

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        int[] blankSquare =this.getSpace();
        int[][]copyArray=this.tiles;
        // ignore the row with the blank square 
        for(int i =0; i<this.dimension();i++){
            // if it is the same row as the blank square go to the next row.
            if(i==blankSquare[0]){
                continue;
            }
            int temp = copyArray[i][0];
            // once the squares are swapped break out of this statement
            copyArray[i][0] = copyArray[i][1];
        // previous filled square will become blank
            copyArray[i][1]=temp;
            break;

        }
        return new Board(copyArray);
        

    }

    // unit testing (not graded)
    public static void main(String[] args){
        int [][] puzzleBoard = {{0,1,3},
                                {4,2,5},
                                {7,8,6}};
        Board boardOne = new Board(puzzleBoard);
        System.out.println(boardOne.dimension());
        System.out.println(boardOne.toString());
        System.out.println(boardOne.hamming());
        System.out.println(boardOne.manhattan());
        System.out.println(Arrays.toString(boardOne.getSpace()));
        // copied successfully
        System.out.println(Arrays.deepToString(boardOne.copyArray()));
        Iterator iterator = boardOne.neighbors().iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        // twin works man
        System.out.println(boardOne.twin());

        


    }

}