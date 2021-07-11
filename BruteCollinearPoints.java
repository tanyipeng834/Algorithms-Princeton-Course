import java.util.Arrays;

public class BruteCollinearPoints {
    // count the number of line segments found by the consturctor
    public  static int numberofSegments =0;
    public  static LineSegment[] lines = new LineSegment[1];
    public BruteCollinearPoints(Point[] points){
        // iterate through thr points in the array
        for (int i =0 ; i<points.length-3;i++){
            for (int j =i+1;j<points.length-2;j++){
                for (int k =j+1;k<points.length-1;k++){
                    for (int l = k+1;l<points.length;l++){
                        if ((points[i].slopeTo(points[j])==points[i].slopeTo(points[k]))&&(points[i].slopeTo(points[l])==points[i].slopeTo(points[k]))){
                            BruteCollinearPoints.enqueue(new LineSegment(points[i], points[l]));

                        }
                        
                    }

                }
            }
            

        }

    }   // finds all line segments containing 4 points
   public  int numberOfSegments(){
       return numberofSegments;

   }        // the number of line segments
   public LineSegment[] segments(){
       return lines;


   }

   private  static void resize(int capacity){
    LineSegment [] copy = new LineSegment[capacity];
    for (int i =0;i<numberofSegments;i++){
        copy[i] = lines[i];



    }
    lines = copy;


   }
   private  static void enqueue(LineSegment line){
    if(line == null){
        throw new IllegalArgumentException("Item cannot be null");
    }
    if (numberofSegments == lines.length){
        resize(2*lines.length);
    }
    lines[numberofSegments++] = line;



}
public static void main(String[] args) {
    Point[] points = new Point[]{new Point(2,4),new Point(4,6),new Point(6,8), new Point(8,10)};
    BruteCollinearPoints BrutePoints = new BruteCollinearPoints(points);
    // test if the method that calculate the number of line segments is able to be reflected properly
    assert(BrutePoints.numberOfSegments()==1);
    // test if the correct line segment is being found
    System.out.println(Arrays.toString(BrutePoints.segments()));




   }
                }                // the line segments
    

