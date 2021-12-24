

public class Outcast {
    private WordNet wordnet;
    public Outcast(WordNet wordnet){
        this.wordnet = wordnet;
    }         // constructor takes a WordNet object
    public String outcast(String[] nouns){
        String maxNoun = null;
        int maxDistance = -1;
        for(String noun : nouns){
            int distance = 0;
            for(String otherNoun : nouns){
                if(noun.equals(otherNoun)){
                    continue;
                }
                 distance += wordnet.distance(noun, otherNoun);
                
            }
            if(distance > maxDistance){
                maxDistance = distance;
                maxNoun = noun;
               
            }
        }
        return maxNoun;

    }   
    public static void main(String[] args){
          // see test client below
        
}
}