
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private WeightedQuickUnionUF gridVirtualTopAndBottom;
    private boolean[][] gridFilled;
    private  int gridSize;
    private int openSites =0;
    private WeightedQuickUnionUF gridVirtualTop;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if (n<=0){
            throw new IllegalArgumentException("N must be greater than 0.");
        }
         gridVirtualTopAndBottom = new WeightedQuickUnionUF(n*n +2);
         gridVirtualTop = new WeightedQuickUnionUF(n*n+1);
         // n^2 is the virtual top while n^2 +1 is the virtual bottom
         gridFilled = new boolean[n][n];
         gridSize = n;
         // Make all sites initially blocked





    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        /* connect all sites in the first row to the virtual top and
        the sites in the bottom row to the virtual bottom*/
        throwError(row, col);

        if(row ==1){
            gridVirtualTop.union(flattenGrid(row, col), gridSize*gridSize);
            gridVirtualTopAndBottom.union(flattenGrid(row, col), gridSize*gridSize);
        }
        if(row == gridSize){
            gridVirtualTopAndBottom.union(flattenGrid(row,col),(gridSize*gridSize+1));
        }
        if (!isOpen(row, col)){

            //open the current box
            gridFilled [row-1][col-1] =true;
            openSites++;}
        else{
            return;
        }
            /* Check if the the adjacent boxes are in the grid and if they open
            union them with the adjacent boxes*/
            // check for site below current square
            if (validateSite(row+1, col)&&(isOpen(row+1,col))){
                gridVirtualTop.union(flattenGrid(row+1, col),flattenGrid(row, col));
                gridVirtualTopAndBottom.union(flattenGrid(row+1, col),flattenGrid(row, col));
            }
            // check for site above the current square
            if (validateSite(row-1, col) && isOpen(row-1,col)){
                gridVirtualTop.union(flattenGrid(row-1, col),flattenGrid(row, col));
                gridVirtualTopAndBottom.union(flattenGrid(row-1, col),flattenGrid(row, col));

            }
            // check for site to the right of the current square
            if ( validateSite(row, col+1)&& isOpen(row,col+1)){
                gridVirtualTop.union(flattenGrid(row, col+1),flattenGrid(row, col));
                gridVirtualTopAndBottom.union(flattenGrid(row, col+1),flattenGrid(row, col));


            }
            // check for site to the left of the current square

            if(validateSite(row, col-1)&&isOpen(row,col-1)){
                gridVirtualTop.union(flattenGrid(row, col-1),flattenGrid(row, col));
                gridVirtualTopAndBottom.union(flattenGrid(row, col-1),flattenGrid(row, col));

            }










    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        throwError(row, col);

        return gridFilled[row-1][col-1] ==true;

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        throwError(row, col);
        // check if virtual top is connected to site
        return (gridVirtualTop.find(gridSize*gridSize)==gridVirtualTop.find((flattenGrid(row, col))));
    }

    // returns the number of open sites
    public int numberOfOpenSites(){

        return openSites;

    }

    // does the system percolate?
    //  system percolates if virtual top connected to virtual bottom
    public boolean percolates(){
        if (gridVirtualTopAndBottom.find(gridSize*gridSize)==gridVirtualTopAndBottom.find((gridSize*gridSize)+1)){
            return true;
        }
        else{
            return false;
        }

    }
    private int  flattenGrid(int row, int column){
        return (gridSize*(row-1)) + column -1;
    }
    private boolean validateSite(int row, int col){
        return ((row>=1&&row<= gridSize) && (col <=gridSize && col>=1));
    }

    private void throwError(int row,int column){
        if ((1>row||row>gridSize)||(1>column||column>gridSize)){
        throw new IllegalArgumentException("The Row or Column is not in the Prescribed Range");
    }
}

    // test client (optional)
    public static void main(String[] args){

    }
}