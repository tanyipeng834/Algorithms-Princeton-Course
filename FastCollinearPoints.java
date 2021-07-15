import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
public class FastCollinearPoints {
    // this is the orgin point for the other points to compare with
    
    private  ArrayList <LineSegment> lines = new ArrayList<>();
    private Point[] pointsCopy;
    

    //time complexity of n^2 logn can be achieved through linear interation and a merge sort

    public FastCollinearPoints(Point[] points){
        if(points==null){
            throw new IllegalArgumentException("Points array cannot be null");
        }
        if (checkNull(points)){
            throw new  IllegalArgumentException (" Point cannot be null in the array");

        }
        pointsCopy = Arrays.copyOf(points,points.length);
        Arrays.sort(pointsCopy);
        

        if (checkDuplicates(pointsCopy)){
            throw new IllegalArgumentException("No Duplicate Elements can be found.");

        }
        else{
            
            // sort by the y axis so that we can get the lowest point as the origin
            
            
        
        for (int i =0; i<pointsCopy.length-3;i++){
            Arrays.sort(pointsCopy);
            // 
            Arrays.sort(pointsCopy,pointsCopy[i].slopeOrder());      
            double slope =0;
            for(int j = 1; j<pointsCopy.length;j++){
                slope = pointsCopy[0].slopeTo(pointsCopy[j]);
                int startIndex = j;
                int endIndex =-1;
                int low = j+1;
                int high = pointsCopy.length-1;
                // binary search of the remaining for the same gradient
                while (low<=high){
                    int mid = (high-low)/2 +low;
                    //if the point is greater than search the left parttion
                    if(pointsCopy[0].slopeTo(pointsCopy[mid])>slope){
                        high = mid -1;
                    }
                    else if (pointsCopy[0].slopeTo(pointsCopy[mid])==slope){
                        
                        
                        
                        endIndex =mid;
                        // search the right partion for any number that is the same as this
                        low = mid+1;
                    }
                    else{
                        low = mid+1;
                    }
                }



                /*while (low<=high){
                        int mid = (high-low)/2 +low;
                        //if the point is greater than search the left parttion
                        if(pointsCopy[i].slopeTo(pointsCopy[mid])>slope){
                            high = mid -1;
                        }
                        else if (pointsCopy[i].slopeTo(pointsCopy[mid])==slope){
                            startIndex =mid;
                            // search the right partion for any number that is the same as this
                            high =mid-1;
                        }
                        else{
                            low = mid+1;
                        }
                



                }
                
                */
                //get unique lines
                
                if (endIndex-startIndex>=2&&(pointsCopy[0].compareTo(pointsCopy[j]))<0){
                lines.add(new LineSegment(pointsCopy[0], pointsCopy[endIndex]));
                j = endIndex;
                }

                
                
                
                
                    
                    

            }
        }
    }

}

    
    





       // finds all line segments containing 4 or more points
   public           int numberOfSegments(){
       return lines.size();

   }        // the number of line segments
   public LineSegment[] segments(){
    LineSegment[] lines_copy = new LineSegment[lines.size()];
    lines_copy = lines.toArray(lines_copy);
    return lines_copy;


   }
  
   private  boolean checkDuplicates(Point[] points){
    for (int i =0;i<points.length-1;i++){
        if (points[i].compareTo(points[i + 1]) == 0) {
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
  


   public static void main(String[] args) {
    
    /*Point[] points = new Point[]{new Point(2,4),new Point(4,6),new Point(6,8), new Point(8,10),new Point(8,5),new Point(10,12)};
    FastCollinearPoints FastPoints = new FastCollinearPoints(points);
    assert(FastPoints.numberOfSegments()==1);
    // test if the correct line segment is being found
    System.out.println(Arrays.toString(FastPoints.segments()));
    */
    
    
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
    
    




   }
   


                // the line segments
    
}
