import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;

public class BruteCollinearPoints {
    // count the number of line segments found by the consturctor
    private  ArrayList <LineSegment> lines = new ArrayList<>();
    private Point[] pointsCopy;
    public BruteCollinearPoints(Point[] points){
        if(points==null){
            throw new IllegalArgumentException("Points array cannot be null");
        }
        if (checkNull(points)){
            throw new  IllegalArgumentException (" Point cannot be null in the array");

        }
        // allocate memory to the new array
        
        // similar reference
        pointsCopy = Arrays.copyOf(points,points.length);
        Arrays.sort(pointsCopy);
       
        if (checkDuplicates(pointsCopy)){
            throw new IllegalArgumentException("No Duplicate Elements can be found.");

        }
        else{

            
        // using the compareTo method to sort the points
        
        // iterate through thr points in the array
        for (int i =0 ; i<this.pointsCopy.length-3;i++){
            for (int j =i+1;j<this.pointsCopy.length-2;j++){
                for (int k =j+1;k<this.pointsCopy.length-1;k++){
                    for (int l = k+1;l<this.pointsCopy.length;l++){
                        if ((this.pointsCopy[i].slopeTo(this.pointsCopy[j])==this.pointsCopy[i].slopeTo(this.pointsCopy[k]))&&(this.pointsCopy[i].slopeTo(this.pointsCopy[l])==this.pointsCopy[i].slopeTo(this.pointsCopy[k]))){
                            lines.add(new LineSegment(this.pointsCopy[i], this.pointsCopy[l]));

                        }
                        
                    }
 
                }
            }
        }
   }

}   
   // finds all line segments containing 4 points
   public  int numberOfSegments(){
       return lines.size();

   }        // the number of line segments
   private  boolean checkDuplicates(Point[] pointsArray){
    
    for (int i =0;i<pointsArray.length-1;i++){
        if (pointsArray[i].compareTo(pointsArray[i + 1]) == 0) {
            return true;
        }
    }
    return false;

    }
    private boolean checkNull(Point[] pointsArray){
        for (int i =0;i<pointsArray.length;i++){
            if (pointsArray[i]==null) {
                return true;
            }
        }
        return false;

    }
   public LineSegment[] segments(){
       LineSegment[] lines_copy = new LineSegment[lines.size()];
       lines_copy = lines.toArray(lines_copy);
       return lines_copy;


   }

   







public static void main(String[] args) {
    
    Point[] points_test = new Point[]{new Point(2,4),new Point(4,6),new Point(6,8), new Point(8,10),new Point(3,5)};
    BruteCollinearPoints BrutePoints = new BruteCollinearPoints(points_test);
    // test if the method that calculate the number of line segments is able to be reflected properly
    assert(BrutePoints.numberOfSegments()==1);
    // test if the correct line segment is being found
    System.out.println(Arrays.toString(BrutePoints.segments()));
    






}



   }

                              // the line segments
    

