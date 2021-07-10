/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;



public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        // handle the case of a degenerate point

        if (this.x == that.x && this.y ==that.y){
            return Double.NEGATIVE_INFINITY;
        }
        // handle the case of a horizontal line
        if (this.y == that.y){
            return +0.0;
        }
        // handle the case of a vertical line
        if (this.x ==that.x){
            return Double.POSITIVE_INFINITY;

          
        }
        //  handle a normal slope gradient
        else{
            return ((double)(that.y-this.y))/(that.x-this.x);
        }

       
    
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        // compare by the y coordinates first
        if (this.y>that.y){
            return +1;

        }
        // assuiming there is a tie of the y coordinates of the point, have to break by the x cooridnates
        if (this.y== that.y){

            // check if the x coordinates is larger
            if(this.x>that.x){
                return +1;

            }
            else if (this.x==that.x){
                return 0;
            }
            // if the x coordinates is smaller ,then means it is smaller
            else{
                return -1;
            }
        
        }
        // when the other Point y cooridantes is larger than the current point
        else{
            return -1;
        }

    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
        /* YOUR CODE HERE */
    }


    private  class SlopeOrder implements Comparator<Point>{
        Point invokingPoint = Point.this;
        @Override
        public int compare(Point Point1,Point Point2){
            // if the first point is larger than the second point
            if (invokingPoint.slopeTo(Point1)>invokingPoint.slopeTo(Point2)){
                return 1;
            }
            // checking for the vice versa of the above comment
            else if(invokingPoint.slopeTo(Point1)<invokingPoint.slopeTo(Point2)){
                return -1;
            }
            // condition where the slope to the invoking point are the same
            else{
                return 0;
            }

            

        }


    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    
        
    



    

    /**
     * Unit tests the Point data type.
     */


    
    public static void main(String[] args) {
        /* Create Points that are used for testing of the class Methods*/
        Point p1 = new Point(1,1);
        Point p2 = new Point(1,2);
        Point p3 = new Point(3,1);


        // Test if the y cooordinates compare points by the y - coordinates
        assert(p1.compareTo(p2)<0);
        // Test if the compare to method is able to break by the x - coordinates.
        assert(p3.compareTo(p1)>0);
        // Test if the slope of a horizontal line returns positive zero
        assert(p1.slopeTo(p3) == +0.0);
        // Test if the slope of a vertical line returns positive infinity
        assert(p2.slopeTo(p1)== Double.POSITIVE_INFINITY);
        // Test if a degnerate point returns negative infinity.
        assert(p1.slopeTo(p1)==Double.NEGATIVE_INFINITY);
        // Test if it can handle a normal slope gradient
        assert(p3.slopeTo(p2) == -0.500);
        // Test the slope order where the first point is larger than the seconf 
        assert(p1.slopeOrder().compare(p2,p3) == 1);
        // test if the correct number is returned when the slope to the invoking point is the same
        assert(p1.slopeOrder().compare(p2,p2)==0);
        // test the slope order where the second point slope to the invoking point is larger than the first point
        assert(p1.slopeOrder().compare(p3,p2) == -1);
        System.out.println("Hello");


    
        
        
    }
}