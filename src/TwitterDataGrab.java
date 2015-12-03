/**
 * 
 */
package src;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * @author Team Dragon: Dustin, Brayden, Steph, Jeffrey
 * Hello Team! To run, just go to the green circle with an arrow up top, click on the drop down and select "Run Configuration"
 * There just go to "arguments" and put something there. Use " " for multiple expressions. Have fun!
 *
 */
public class TwitterDataGrab {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String[] cleanTweets = null;
		HashMap<String, Integer> Tweets = new HashMap<String, Integer>();
		
		if (args.length < 1) {
            System.out.printf("\u001B[31m Usage \u001B[0m: java twitter4j.examples.search.SearchTweets [query]\n"
            		+ "For [query] more than one word use quotes");
            System.exit(-1);
        }
		for(int i = 0; i < args.length; i++){
			Tweets = GetTwitterData(args[i]);
			cleanTweets = TweetCleaner(Tweets);
		 
		
        TwitterClustering sentenceClustering = new TwitterClustering(cleanTweets);
        sentenceClustering.JsonFormat();
		}
	}
	
public static HashMap<String, Integer> GetTwitterData(String userIntput){
	
	//The HashMap we are storing  the Tweets in.
	HashMap<String, Integer> Tweets = new HashMap<String, Integer>();
	int MaxTweets = 700;
	
    Twitter twitter = new TwitterFactory().getInstance();
    
	    try {
	    	//Makes a new Query object.
	        Query query = new Query(userIntput);
	        QueryResult result; 
	       for(int i = 0; i <= MaxTweets; i++){
	    	   	//loops threw and gets results from the twitter Query.
	            result = twitter.search(query);
	            List<Status> tweets = result.getTweets();
	            for (Status tweet : tweets) {
	            	
	            	Integer freq = Tweets.get("@" + tweet.getUser().getScreenName() + " - " + tweet.getText()); 
					
					//Checks map for frequency
					if (freq == null) { //Tweet doesn't exist
						Tweets.put(tweet.getText(), 1);	
					} else { //Tweet exists, increment count
						Tweets.put(tweet.getText(), freq + 1);
					}
	            	
	               System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
	            }
	            if ((query = result.nextQuery()) != null){
	            	break;
	            }
	            
	        } 
	        
	    } catch (TwitterException te) {
	        te.printStackTrace();
	    }	
	
		return Tweets;
	}


public static String[]  TweetCleaner(HashMap<String, Integer> Tweets){
		
		int size = 100;
		int Count = 0;
		String[] tweets = new String[size];
		String Names = "(@[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*:)";
		String URLS = "(https?|http?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		String Clean = "[^\\w&&[^\\s]&&[^.,?]]";
			
		for (Map.Entry<String, Integer>HM:Tweets.entrySet()) {
			
			//Gets the Tweet from the collection
			String result = HM.getKey();
			result = result.replaceAll(URLS,""); //Removes URLS
			result = result.replaceAll(Names,"");// Removes Twitter name in this formant "@somename:"
			result = result.toLowerCase().replaceAll(Clean , " "); // Removes all other Unwanted Text.
			System.out.println(result);
			tweets[Count] = result; 	
			
			Count++;
			
			//Dynamically Reallocates array.
			if(Count >= tweets.length){
				tweets = Arrays.copyOf(tweets, tweets.length + size);
			}
		}	
		return tweets;
	}
}