import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.Math;
public class PercolationStats {
    private  int gridSize;
    private int trials;
    private double[] thresholdArray;
    private final static double CONFIDENCE_95 =1.96;




    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if (n<=0 || trials<=0){
            throw new IllegalArgumentException("n and trials must be greater than 0.");
        }
        // All Sites are blocked

        gridSize = n;
        this.trials =trials;
        thresholdArray = new double[trials];

        for(int i = 0;i<trials;i++){
            Percolation percolationBoard = new Percolation(n);
            while(!percolationBoard.percolates()){
                // Get a random number from 0 to end of board
                int row = StdRandom.uniform(1, gridSize+1);
                int col = StdRandom.uniform(1,gridSize+1);
                percolationBoard.open(row, col);}
                int openSites =percolationBoard.numberOfOpenSites();
            double result = ((double)openSites/(gridSize*gridSize));
            thresholdArray[i] = result;
        }




    }

    // sample mean of percolation threshold
    public double mean(){
         return StdStats.mean(thresholdArray);

    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(thresholdArray);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return (mean()-((CONFIDENCE_95*stddev())/Math.sqrt(trials)));

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return (mean()+((CONFIDENCE_95*stddev())/Math.sqrt(trials)));

    }






            // Open random site






   // test client (see below)
   public static void main(String[] args){
       PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
      System.out.printf("mean           = %f\n",percolationStats.mean());
      System.out.printf("stddev         = %f\n",percolationStats.stddev());
      String confidence = percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi();
      System.out.println("95% confidence interval =" +"[" + confidence +"]");


   }

}