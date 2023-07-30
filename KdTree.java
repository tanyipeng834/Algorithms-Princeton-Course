import java.util.Stack;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
public class KdTree {
    private int size =0;
    private Node root;
    private class Node{
        private Point2D point;
        private Node left,right;
        
        public Node(Point2D point){
            this.point = point;
            left=right=null;

        }
    }

    public         KdTree(){
        root=null;

    }                               // construct an empty set of points 
    public           boolean isEmpty(){
        if(root==null){
            return true;
        }
        else{
            return false;
        }
        }

                      // is the set empty? 
    public               int size(){
        return this.size;

    }                         // number of points in the set 
    public              void insert(Point2D p){
        if(p==null){
            throw new IllegalArgumentException("P cannnot be null");
        }
        if(this.root==null){
            this.root = new Node(p);
            this.size++;
        }
        
        else{
            if(this.contains(p)){
                return;
            }
            int level =0;
            Node x = root;
            Node y =null;
            // this x pointer will point to the position where to insert the node
            while(x!=null){
                // if the level is even, that mean we will compare by the x axis
                // this y pointer will point to where the parent node is of the node
                y=x;
                // if the point is the exact same, do not add it to the tree

                if(level%2==0){
                    boolean evenCondition = p.x()>=x.point.x();
                    if(evenCondition){
                        x =x.right;
                        level++;
                    }
                    
                    else{
                        x=x.left;
                        level++;
                    }

                }
                // if the level is odd, we will compare by the y-axis
                else{
                    boolean oddCondition = p.y()>=x.point.y();
                    if(oddCondition){
                        x =x.right;
                        level++;
                    }
                    else{
                        x=x.left;
                        level++;
                    }


                }

            }
            // have to -1 from the level as we are getting the parent's node instead of the current child
            // node
            // this level is a current even node hence it will be compared by the x axis
            if((level-1)%2==0){
                boolean evenCondition = p.x()>=y.point.x();
                if(evenCondition){
                    y.right =new Node(p);
                    this.size++;

                }

            
            else{
                y.left = new Node(p);
                this.size++;

            }
        }
        else{
            boolean oddCondition = p.y()>=y.point.y();
            if(oddCondition){
                y.right =new Node(p);
                this.size++;
            }
            else{
                y.left =  new Node(p);
                this.size++;
            }
            

        }
            


            

        }

    }              // add the point to the set (if it is not already in the set)
    public           boolean contains(Point2D p){
        if(p==null){
            throw new IllegalArgumentException("P cannot be null");
        }
        Node x = this.root;
        int level =0;
        while(x!=null){
            // checlk for the root node
            if(x.point.equals(p)){
                return true;
            }
            if(level%2==0){
                boolean evenCondition = p.x()>=x.point.x();
                    if(evenCondition){
                        x =x.right;
                        level++;
                        if(x==null){
                            return false;
                        }
                        if(x.point.equals(p)){
                            return true;
                        }
                    }
                    else{
                        x=x.left;
                        level++;
                        if(x==null){
                            return false;
                        }
                        if(x.point.equals(p)){
                            return true;
                        }
                    }

            }
            else{
                boolean oddCondition = p.y()>=x.point.y();
                if(oddCondition){
                    x =x.right;
                    level++;
                    if(x==null){
                        return false;
                    }
                    if(x.point.equals(p)){
                        return true;
                    }
                }
                else{
                    x=x.left;
                    level++;
                    if(x==null){
                        return false;
                    }
                    if(x.point.equals(p)){
                        return true;
                    }
                }


            }

            }
            return false;


        }          // does the set contain point p? 
    public              void draw(){
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        Stack<Node> stack = new Stack<>();
        Node curr = this.root;
        while(!stack.empty()||curr!=null){
            if(curr!=null){
                stack.push(curr);
                curr =curr.left;
                // draw all the left nodes first
                if(curr!=null){
                curr.point.draw();
                }

            }
            else{
                curr = stack.pop();
                curr.point.draw();
                curr = curr.right;
                if(curr!=null){
                curr.point.draw();
                }
            }
        }


    }                         // draw all points to standard draw 
    public Iterable<Point2D> range(RectHV rect){
        if(rect==null){
            throw new IllegalArgumentException(" rect cannot be null");
        }
        ArrayList<Point2D> pointsArray = new ArrayList<>();
        range(this.root,0,pointsArray,rect);
        return pointsArray;
      
             }
    private  void range(Node node,int level,ArrayList<Point2D> list,RectHV rect){
        if(node==null){
            return;
        }
        if (rect.contains(node.point)){
            list.add(node.point);
        }
        if(level%2==0){
            if(rect.xmin()<=node.point.x()&&rect.xmax()>=node.point.x()){
                // recursivel search left
                range(node.left, level+1, list, rect);
                // recursively search right
                range(node.right,level+1,list,rect);

            }
            else if(rect.xmin()>node.point.x()){
                range(node.right,level+1,list,rect);
            }
            else{
                range(node.left,level+1,list,rect);

            }

        }
            else{
                if(rect.ymin()<=node.point.y()&&rect.ymax()>=node.point.y()){
                    // recursivel search left
                    range(node.left, level+1, list, rect);
                    // recursively search right
                    range(node.right,level+1,list,rect);

            }
            else if(rect.ymin()>node.point.y()){
                range(node.right,level+1,list,rect);
            }
            else{
                range(node.left,level+1,list,rect);

            }


        }

    }
             
             // all points that are inside the rectangle (or on the boundary) 
    public           Point2D nearest(Point2D p){
        if(p==null){
            throw new IllegalArgumentException(" P cannot be null");
        }
        if(this.size==0){
            return null;
        }
        return near(this.root,p,0,this.root.point);

    }

    private Point2D near (Node node,Point2D point,int level,Point2D champion){
        
        if(node==null){
            return champion;
        }
        if(node.point.distanceTo(point)<champion.distanceTo(point)){
            champion = node.point;
        }
        if(level%2==0){
            
            // go right or left based on the x-axis
            if(point.x()>node.point.x()){

                //recursively seracgh left if the champion return is still the point
                champion = near(node.right,point,level+1,champion);
                champion = near(node.left,point,level+1,champion);



            }
            else{
                // recursively search left
                champion =near(node.left,point,level+1,champion);
                
                     champion = near(node.right,point,level+1,champion);

            }

        }
        else{
            if(point.y()>node.point.y()){
                champion = near(node.right,point,level+1,champion);
                //recursively seracgh left
                     champion=near(node.left,point,level+1,champion);

            }
            else{
                // recursively search left
                champion = near(node.left,point,level+1,champion);
                     champion = near(node.right,point,level+1,champion);


            }

        }
        return champion;
        


    }
    
 
    public static void main(String[] args){
        Point2D p1 = new Point2D(0.7,0.2);
        Point2D p2 = new Point2D(0.5,0.4);
        Point2D p3 = new Point2D(0.2,0.3);
        Point2D p4 = new Point2D(0.4,0.7);
        Point2D p5 = new Point2D(0.9,0.6);
        Point2D p6 = new Point2D(0.9,0.9);
        Point2D p7 = new Point2D(0.088,0.516);
        KdTree tree = new KdTree();
        tree.insert(p1);
        tree.insert(p2);
        tree.insert(p3);
       tree.insert(p4);
       tree.insert(p5);
       System.out.println(tree.nearest(p7));
       

        



    }                  // unit testing of the methods (optional) 
 }