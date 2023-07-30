import java.util.Iterator;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;


public class PointSET {
    private SET<Point2D> setOfPoints;
    public         PointSET(){
        // initialize a new binary search tree
        this.setOfPoints = new SET<>();
       

    }                               // construct an empty set of points 
    public           boolean isEmpty(){
        //use the instance method on the current set
        return this.setOfPoints.isEmpty();
    }
                             // is the set empty? 
    public               int size(){
        return this.setOfPoints.size();

    }                         // number of points in the set 
    public  void insert(Point2D p){
        if (p==null){
            throw new IllegalArgumentException("Argument cannot be null");
        }
        this.setOfPoints.add(p);

    }              // add the point to the set (if it is not already in the set)
    public           boolean contains(Point2D p){
        if (p==null){
            throw new IllegalArgumentException("Argument cannot be null");
        }
        // check if the current set cointain point p
        return this.setOfPoints.contains(p);

    }            // does the set contain point p? 
    public              void draw(){
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        Iterator <Point2D> iteratorPoints = this.setOfPoints.iterator();
        // keep going to the next item if it has a next element
        while(iteratorPoints.hasNext()){
            // draw the point in standard draw
            iteratorPoints.next().draw();
            

        }


    }                         // draw all points to standard draw 
    // itreate through all the points and use the draw method
    public Iterable<Point2D> range(RectHV rect){
        if(rect==null){
            throw new IllegalArgumentException("argument cannot be null");
        }
        Iterator <Point2D> iteratorPoints = this.setOfPoints.iterator();
        ArrayList<Point2D> pointsArray = new ArrayList<>();
        // iterate thorugh all the points in the 
        while(iteratorPoints.hasNext()){
            Point2D currentPoint = iteratorPoints.next();
            if(rect.contains(currentPoint)){
                pointsArray.add(currentPoint);
            }
        }
        return pointsArray;

    }             // all points that are inside the rectangle (or on the boundary) 
    public           Point2D nearest(Point2D p){
        if(p==null){
            throw new IllegalArgumentException("P cannot be null");
        }
        Iterator <Point2D> iteratorPoints = this.setOfPoints.iterator();
        Point2D nearestPoint = null;
        while(iteratorPoints.hasNext()){
            Point2D currentPoint = iteratorPoints.next();
            if (nearestPoint==null){
                nearestPoint = currentPoint;
            }
            else{
                if (p.distanceTo(currentPoint)<p.distanceTo(nearestPoint)){
                    nearestPoint = currentPoint;
                }
            }
        }
        return nearestPoint;
        

    }
    
    
 
    public static void main(String[] args){

        PointSET pointSet = new PointSET();
        Point2D p1 = new Point2D(0.1,0.1);
        Point2D p2 = new Point2D (0.2,0.2);
        Point2D p3 = new Point2D (0.3,0.3);
        RectHV rect = new RectHV(0,0,0.5,0.2);


        // test the insert method
        // insert three points into the bst
        pointSet.insert(p1);
        pointSet.insert(p2);
        pointSet.insert(p3);
        // size method works
        System.out.println(pointSet.size());
        // contains method works
        System.out.println(pointSet.contains(p1));
        pointSet.draw();
        System.out.println(pointSet.nearest(p1));

        


                  // unit testing of the methods (optional) 
 }
}