

import edu.princeton.cs.algs4.In;
import java.util.HashSet;
import java.util.Set;
import edu.princeton.cs.algs4.StdOut;
public class BoggleSolver {
    private PrefixTrieST<String> wordTrie;
    

    public BoggleSolver(String[] dictionary){
        wordTrie= new PrefixTrieST<String>();

        for(int i =0; i<dictionary.length;i++){
            wordTrie.put(dictionary[i]);

        }


    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board){

        // create a new of words
      
        Set<String> wordSet = new HashSet<String>();
        // The board should be able to start from anywhere
        for(int i =0; i<board.rows();i++){
            for(int j =0;j<board.cols();j++)
            {
                // Go through all the tiles in the board
                boolean [] [] marked = new boolean[board.rows()][board.cols()];
                dfs(board,wordSet,marked,i,j,"");

            }

        }


        return wordSet;

    }


    private void dfs(BoggleBoard board ,Set<String> words, boolean [][] marked, int rows , int cols,String currentWord)
    {
        marked[rows][cols] = true;
        // mark the current board
        char currentLetter = board.getLetter(rows,cols);
        if (currentLetter=='Q'){
            currentWord+="QU";
        }
        else{
            currentWord +=board.getLetter(rows, cols);

        }
        
       
        // Check if the word exisit in the dictionary
        if(currentWord.length()>=3 && this.wordTrie.contains(currentWord)){
            words.add(currentWord);
        }
        
        // If there are no words with the prefix , we can return to the a parent word , do not have to go down 
        // the route
        if(!wordTrie.containsPrefix(currentWord)){
            marked[rows][cols] = false;
            return;

        }
        // If the word is in the current dictionary and is at least 3 letter
        // then we will add it to the words set
       
        //Now we will recusively go through all the neighbours and if it is not marked
        // This is for the left
        if(cols-1>=0 && !marked[rows][cols-1]){
            dfs(board,words,marked,rows,cols-1,currentWord);
        }
        // this is for the right
        if(cols+1<board.cols() && !marked[rows][cols+1]){
            dfs(board,words,marked,rows,cols+1,currentWord);
        }
        // This is for the top tile
        if(rows-1>=0 && !marked[rows-1][cols]){
            dfs(board,words,marked,rows-1,cols,currentWord);
        }
        // This is for the bottom tile
        if(rows+1<board.rows() && !marked[rows+1][cols]){
            dfs(board,words,marked,rows+1,cols,currentWord);
        }
        // This is for the top left tile
        if(cols-1>=0 && rows-1>=0 && !marked[rows-1][cols-1])
        {
            dfs(board,words,marked,rows-1,cols-1,currentWord);
            

        }
        // This is for the bottom left tile
        if(cols-1>=0 && rows+1<board.rows() && !marked[rows+1][cols-1])
        {
            dfs(board,words,marked,rows+1,cols-1,currentWord);
            

        }
        // This is for the top right tile
        if(cols+1<board.cols() && rows-1>=0 && !marked[rows-1][cols+1])
        {
            dfs(board,words,marked,rows-1,cols+1,currentWord);
            

        }
        // This is for the bottom right tile
        if(cols+1<board.cols() && rows+1<board.rows() && !marked[rows+1][cols+1])
        {
            dfs(board,words,marked,rows+1,cols+1,currentWord);
            

        }
        
        marked[rows][cols] = false;
        
        
    }




    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word){

        if(!wordTrie.contains(word)){
            return 0;
        }
        if(word.length()>=3  && word.length()<=4){
            return 1;
        }
        else if(word.length()==5){
            return 2;
        }
        else if (word.length()==6){
            return 3;
        }
        else if (word.length()==7){
            return 5;
        }

       else if(word.length()>=8){
        return 11;
       }
       return 0;

    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);





        
      
    }
    




}
