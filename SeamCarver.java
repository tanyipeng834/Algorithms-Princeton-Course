import edu.princeton.cs.algs4.Picture;
import java.io.File;
import java.awt.Color;
import java.util.Arrays;

public class SeamCarver {
    // have a data type for the current picture for the seam carver
    private Picture currentPicture;
    private double energyArray[][];
    private  double distTo [][];
    private  int  edgeTo [][];
    

   


    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture){
        if(picture ==null){
            throw new IllegalArgumentException("Picture cannot be null");
        }
        this.currentPicture = new Picture(picture);
        // allocating memory to the array 
        this.energyArray = new double [this.currentPicture.height()][this.currentPicture.width()];
        for(int i =0;i<this.currentPicture.height();i++){
            for( int j =0; j<this.currentPicture.width();j++){
                this.energyArray[i][j] = getEnergy(j,i);
            }
        }

        
        


    }
 
    // current picture
    public Picture picture(){
        return this.currentPicture;

    }

    private double getEnergy(int x, int y){

       
        if ( x==0 || x ==this.currentPicture.width() -1 || y==0 || y ==this.currentPicture.height() -1){
            return 1000;

        }

        else{
            // Consider the x -axis first for the color
            Color x1,x2 ,y1,y2;
            
            x1 = this.currentPicture.get(x-1,y);
            x2 = this.currentPicture.get(x+1,y);
            y1 = this.currentPicture.get(x,y-1);
            y2 = this.currentPicture.get(x,y+1);
            double xProduct = Math.pow(Math.abs(x1.getRed()-x2.getRed()),2)+Math.pow(Math.abs(x1.getGreen()-x2.getGreen()),2)+Math.pow(Math.abs(x1.getBlue()-x2.getBlue()),2);
            double yProduct = Math.pow(Math.abs(y1.getRed()-y2.getRed()),2)+Math.pow(Math.abs(y1.getGreen()-y2.getGreen()),2)+Math.pow(Math.abs(y1.getBlue()-y2.getBlue()),2);
            double endProduct = Math.sqrt(xProduct + yProduct);
            return endProduct;
            
            
    
            
        }


    }

    public double energy(int x , int y ){
        if(x<0 ||x >this.width()-1 || y<0||y>this.height()-1){
            throw new IllegalArgumentException("The parameters in the function are not in the appropiate range");
        }
        // reverse the order cobert enery from x y to i j
        return this.energyArray[y][x];
    }
 
    // width of current picture
    public int width(){
        return this.currentPicture.width();

    }
 
    // height of current picture
    public int height(){

        return this.currentPicture.height();
    }
 
    // energy of pixel at column x and row y
    
 
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam(){
        // this will be the width of the picture
        boolean tranpolse = true;
        int [] path = new int [this.width()];

        this.distTo = new double[this.height()][this.width()];
        this.edgeTo = new int [this.height()][this.width()];
        for (double[] r: this.distTo) Arrays.fill(r, Double.POSITIVE_INFINITY);
        for (int[] r: this.edgeTo) Arrays.fill(r, Integer.MAX_VALUE);
        for(int i =0;i<this.height();i++)
        // fill up the index only for the first column
        {
            this.distTo[i][0] =1000;


        }
        

        // topological sort start from all the top vertices
        for( int i =0 ;i<this.currentPicture.width()-1;i++){

            for(int j =0;j<this.currentPicture.height();j++){
                relax(j,i,tranpolse);






            }
        }

        



        // Remember to set the class dist to and edge to arrays to null.

        double [] minArray = new double [this.height()];
        for(int i=0;i<this.height();i++){
            minArray[i] = this.distTo[i][this.width()-1];
        }
        int minIndex = findMinIndex(minArray);
        int currentIndex = minIndex ;
        path[this.width()-1] = minIndex;
        int currentWidth = this.width()-1;
        while (currentWidth-1>=0){
        

            path[currentWidth-1] = this.edgeTo[currentIndex][currentWidth];
            currentIndex = this.edgeTo[currentIndex][currentWidth];
            currentWidth-=1;



        }
        //clean up the memory for future use
        this.distTo = null;
        this.edgeTo = null;

        return path;
        

        
    }
 
    // sequence of indices for vertical seam
    public int[] findVerticalSeam()

    {
        boolean tranpolse = false;
        int [] path = new int [this.height()];

        this.distTo = new double[this.height()][this.width()];
        this.edgeTo = new int [this.height()][this.width()];
        for (double[] r: this.distTo) Arrays.fill(r, Double.POSITIVE_INFINITY);
        for (int[] r: this.edgeTo) Arrays.fill(r, Integer.MAX_VALUE);
        Arrays.fill(this.distTo[0], 1000);
        // fill up the index only for the top row
        

        // topological sort start from all the top vertices
        for( int i =0 ;i<this.currentPicture.height()-1;i++){

            for(int j =0;j<this.currentPicture.width();j++){
                relax(i,j,tranpolse);






            }
        }

        



        // Remember to set the class dist to and edge to arrays to null.
        int minIndex = findMinIndex(this.distTo[this.height()-1]);
        int currentIndex = minIndex ;
        path[this.height()-1] = minIndex;
        int currentHeight = this.height()-1;
        while (currentHeight-1>=0){

            path[currentHeight-1] = this.edgeTo[currentHeight][currentIndex];
            currentIndex = this.edgeTo[currentHeight][currentIndex];
            currentHeight-=1;



        }
        this.distTo = null;
        this.edgeTo = null;

        return path;
        


    
    }

    private int findMinIndex(double[] minArray){
        if (minArray.length == 0){
        return -1;
        }

    int index = 0;
    double min = minArray[index];

    for (int i = 1; i < minArray.length; i++){
        if (minArray[i] <= min){
        min = minArray[i];
        index = i;
        }
    }
    return index;



    }

private void relax ( int i,int j, boolean transpolse){
    if(!transpolse){

    if(this.distTo[i+1][j]>(this.distTo[i][j] + this.energyArray[i+1][j])){
        this.distTo[i+1][j] = this.energyArray[i+1][j] + this.distTo[i][j];
        // parent
        this.edgeTo[i+1][j] =  j;
    }
    if(j+1<this.width() && this.distTo[i+1][j+1]>(this.distTo[i][j] + this.energyArray[i+1][j+1])){
        this.distTo[i+1][j+1] = this.energyArray[i+1][j+1] + this.distTo[i][j];
        // parent
        this.edgeTo[i+1][j+1] =  j;
    }
    if( j-1>=0 && this.distTo[i+1][j-1]>(this.energyArray[i+1][j-1] + this.distTo[i][j])){
        this.distTo[i+1][j-1] = this.energyArray[i+1][j-1] + this.distTo[i][j];
        // parent
        this.edgeTo[i+1][j-1] =  j;
    }
}

else{
    if(this.distTo[i][j+1]>(this.distTo[i][j] + this.energyArray[i][j+1])){
        this.distTo[i][j+1] = this.energyArray[i][j+1] + this.distTo[i][j];
        // parent
        this.edgeTo[i][j+1] =  i;
    }
    if(i+1<this.height() && this.distTo[i+1][j+1]>(this.distTo[i][j] + this.energyArray[i+1][j+1])){
        this.distTo[i+1][j+1] = this.energyArray[i+1][j+1] + this.distTo[i][j];
        // parent
        this.edgeTo[i+1][j+1] =  i;
    }
    if( i-1>=0 && this.distTo[i-1][j+1]>(this.energyArray[i-1][j+1] + this.distTo[i][j])){
        this.distTo[i-1][j+1] = this.energyArray[i-1][j+1] + this.distTo[i][j];
        // parent
        this.edgeTo[i-1][j+1] =  i;
    }




    
}
    
    


    







}
private boolean checkRemove(int length, int[] array){
    if(array.length!= length){
        return false;
    }
    
    for (int i =0;i<array.length-1;i++){
        if(!(Math.abs(array[i]-array[i+1])==1 || Math.abs(array[i]-array[i+1])==0)){
            return false;
        }
    }
    return true;



    

}
 
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam)
    {
        if(seam ==null){
            throw new IllegalArgumentException("The paramater cannot be null");
        }
        if(!checkRemove(this.width(), seam)){
            throw new IllegalArgumentException("The array is not a valid seam");
        }
        if(this.height()<=1){
            throw new IllegalArgumentException("The height of picture should be more than 1");
        }

        Picture newPic = new Picture(this.width(),this.height()-1);
        // this represents the rows of the picture.
        for( int col =0; col<this.width();col++)
        {
            // This is the Pixel that has to be removed for each row;
            int removePixel = seam [col];
            for(int row=0;row<this.height();row++)
            {
                // Rotate the pixels in x and y axis for the picture
                Color currentColor = this.picture().get(col,row);
                if(row<removePixel){
                    newPic.set(col, row,currentColor);
                }
                else if(row>removePixel){

                    newPic.set(col,row-1, currentColor);
                }



            }


        }
        this.currentPicture = newPic;


        

        
        

        
        
    }
    
 
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam){
        // from 0 to the count in seam will represent each row in the picture.
        if(seam ==null){
            throw new IllegalArgumentException("The paramater cannot be null");
        }
        if(!checkRemove(this.height(), seam)){
            throw new IllegalArgumentException("The array is not a valid seam");
        }
        if(this.width()<=1){
            throw new IllegalArgumentException("The height of picture should be more than 1");
        }

        // Create a new Picture to remove the Vertical Seam
        Picture newPic = new Picture(this.width()-1,this.height());
        // this represents the rows of the picture.
        for( int row =0; row<this.height();row++)
        {
            // This is the Pixel that has to be removed for each row;
            int removePixel = seam [row];
            for(int col=0;col<this.width();col++)
            {
                // Rotate the pixels in x and y axis for the picture
                Color currentColor = this.picture().get(col,row);
                if(col<removePixel){
                    newPic.set(col,row, currentColor);
                }
                else if(col>removePixel){

                    newPic.set(col-1,row, currentColor);
                }



            }


        }
        this.currentPicture = newPic;


        

        






        

    }
 
    //  unit testing (optional)
    public static void main(String[] args){
        File newFile = new File("10x12.png");
        Picture newPic = new Picture(newFile);
       SeamCarver s1 = new SeamCarver(newPic); 
       s1.removeHorizontalSeam(s1.findHorizontalSeam());
       s1.removeVerticalSeam(s1.findVerticalSeam());
       
      
       
       
       

    }
 
 }