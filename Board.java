import java.util.Arrays;
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
                
               string +=Integer.toString(this.tiles[i][j]);
               if(j!=this.tiles[0].length-1){
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
        // check if it is the same board class type
        if(this.getClass()==y.getClass()){
           Board that = (Board)y;
            if (Arrays.equals(this.tiles,that.tiles)){
                return true;
            }

        }
        return false;
        

        }

    private int[] getSpace(){

    }

    
    

    // all neighboring boards
    public Iterable<Board> neighbors(){
        

    }

    

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){

    }
    

    // unit testing (not graded)
    public static void main(String[] args){
        int [][] puzzleBoard = {{1,2,3},
                                {4,5,6},
                                {7,8,0}};
        Board boardOne = new Board(puzzleBoard);
        System.out.println(boardOne.toString());
        System.out.println(boardOne.hamming());
        System.out.println(boardOne.manhattan());

        


    }

}