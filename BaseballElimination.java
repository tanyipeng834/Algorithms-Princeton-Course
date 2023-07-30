import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import java.util.ArrayList;

import java.util.HashMap;

public class BaseballElimination{
    // This is to keep track of the number of baseball teams.
    private int teamCount;
    private In fileStream;
    private int[] wins;
    private int[] losses;
    private int[] remaining;
    private int[][] games;
    private FlowNetwork eliminationGraph;
    private HashMap<Integer,Integer> idToVertex;
    private int vertices;
    private FordFulkerson ford;
    private ArrayList<String> teams =  new ArrayList<String>();
     
     private HashMap<String,Integer> teamToId = new HashMap<String,Integer>();
     private HashMap<Integer,String> idToTeam = new HashMap<Integer,String>();
public BaseballElimination(String filename){    
    this.fileStream = new In(filename);
    this.teamCount = this.fileStream.readInt();
    this.wins = new int[this.teamCount];
    this.losses = new int[this.teamCount];
    this.remaining = new int [this.teamCount];
    this.games = new int[this.teamCount][this.teamCount];
    for(int i=0;i<this.teamCount;i++){
        String team = this.fileStream.readString();
        this.teamToId.put(team, i);
        this.idToTeam.put(i,team);
        this.wins[i] = this.fileStream.readInt();
        this.losses[i] = this.fileStream.readInt();
        this.remaining[i] = this.fileStream.readInt();
        for(int j=0;j<this.teamCount;j++)
        {
            games[i][j] = this.fileStream.readInt();


        }
    }
    
    // create a baseball division from given filename in format specified below

}
public              int numberOfTeams(){
   
 return this.teamCount;

}                        // number of teams
public Iterable<String> teams()

{
    
    
   
    return this.teamToId.keySet();

}                                // all teams
public              int wins(String team)   {
    if(!teamToId.containsKey(team)){
        throw new IllegalArgumentException("The team is not valid") ;
    }
    int id = teamToId.get(team);
    return this.wins[id];


}                   // number of wins for given team
public              int losses(String team) {
    if(!teamToId.containsKey(team)){
        throw new IllegalArgumentException("The team is not valid") ;
    }
    int id = teamToId.get(team);
    return this.losses[id];

}                   // number of losses for given team
public              int remaining(String team)  {
    if(!teamToId.containsKey(team)){
        throw new IllegalArgumentException("The team is not valid") ;
    }
    int id = teamToId.get(team);
    return this.remaining[id];


}               // number of remaining games for given team
public              int against(String team1, String team2) {
    if(!(teamToId.containsKey(team1)&&teamToId.containsKey(team2))){
        throw new IllegalArgumentException("The team is not valid") ;
    }

    int idOne = teamToId.get(team1);
    int idTwo = teamToId.get(team2);
    return games[idOne][idTwo];

}   // number of remaining games between team1 and team2


private static int nCr(int n, int r){
    if(n<r || n==0)
    {
      return 1;
    }
 
    int num = 1, den = 1;
    for(int i=r; i>=1; i--){
      num = num * n--;
      den = den * i;
    }
    
    return num/den;
  }
  

private  boolean createFlowNetwork(int teamId){
    // Number of vertices would include the source and sink vertex and also the number of teams -1 choose 2 vertex
    int n = this.teamCount-1;
    
    
    int combinations = BaseballElimination.nCr(n,2);
    this. vertices = 2 + combinations + this.teamCount-1;
    this.eliminationGraph = new FlowNetwork(this.vertices);
    int sourceCapacity =0;
    // create a hashmap to link the team vertex to the graph
    this. idToVertex = new HashMap<Integer,Integer>();

    for( int i=0, j=0;i<this.teamCount;i+=1)
    {
         
        if(teamId != i){
           
        this.idToVertex.put(i,combinations+j+1);
        j++;
        }
        
        
    
        


    }
    

   
    
        
        // first team
        int counter = 1;
        for(int j =0;j<this.teamCount;j++){
            if(j!=teamId){
            
            
            // second team
            for(int k =j+1;k<this.teamCount;k++)
            {
                if(k!=teamId){
                
                    // add the first team
                    this.eliminationGraph.addEdge(new FlowEdge(counter,idToVertex.get(j),Double.POSITIVE_INFINITY));
                    // ADD THE SECOND TEAM
                    this.eliminationGraph.addEdge(new FlowEdge(counter,idToVertex.get(k),Double.POSITIVE_INFINITY));
                    this.eliminationGraph.addEdge(new FlowEdge(0,counter,games[j][k]));
                    sourceCapacity+=games[j][k];
                    counter +=1;
                }



                

            }
            this.eliminationGraph.addEdge(new FlowEdge(idToVertex.get(j),vertices-1,wins[teamId]+remaining[teamId]-wins[j]));
        }
        // connect all the team to the sink vertex;
        
        
    }
        

        
    

    this.ford = new FordFulkerson(this.eliminationGraph, 0, vertices-1);
    if(ford.value()<sourceCapacity){
        return true;
    }
    else{
        return false;
    }

   
        
}


    
    





public          boolean isEliminated(String team)     
{
    if(!teamToId.containsKey(team)){
        throw new IllegalArgumentException("The team is not valid") ;
    }
    // trival elimination
    int teamId = teamToId.get(team);
    this.teams = new ArrayList<String>();

    for(int i =0;i<this.teamCount;i++){
        
            if(wins[teamId]+remaining[teamId]<wins[i]){
                this.teams.add(this.idToTeam.get(i));
                return true;
            }


        }

        // if it is not eliminated then we will do the non trival elimination
        // Create a flow network
        return createFlowNetwork(teamId);

        

    }

       // is given team eliminated?
public Iterable<String> certificateOfElimination(String team)

{
    if(!teamToId.containsKey(team)){
        throw new IllegalArgumentException("The team is not valid") ;
    }
    if(isEliminated(team))
    {
        
        
        
        if(this.teams.isEmpty()){
         // Create an ArrayList object
         this.teams = new ArrayList<String>();
         for( int key : this.idToVertex.keySet())
         {
            if (this.ford.inCut(this.idToVertex.get(key)))
            {
                teams.add(this.idToTeam.get(key));
            }

         }
        }
         return teams;




    }
    else{
        return null;
    }

}
// subset R of teams that eliminates given team; null if not eliminated


public static void main(String[] args){
BaseballElimination be = new BaseballElimination("teams30.txt");
System.out.println(be.numberOfTeams());
System.out.println(be.certificateOfElimination("Team18"));

}


}