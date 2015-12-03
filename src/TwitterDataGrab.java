/**
 * 
 */
package src;

import java.util.List;

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
		
		/* Commented out to test the SentenceClustering
		if (args.length < 1) {
            System.out.printf("\u001B[31m Usage \u001B[0m: java twitter4j.examples.search.SearchTweets [query]\n"
            		+ "For [query] more than one word use quotes");
            System.exit(-1);
        }
        Twitter twitter = new TwitterFactory().getInstance();
        //TODO:: figure out rate limit issues, possible with caching of some sort  
        try {
            Query query = new Query(args[0]);
            QueryResult result; 
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                }
            } while ((query = result.nextQuery()) != null);
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
       }
	   */
		
		//TODO:: add Brayden's code here. Pass his array of sentences 
		// tweets array is for testing -- replace it with the array of sentences Brayden collects 
		String[] tweets = {"first tweet", "second tweet", "third tweet", "I am confused that single words are skipped!",
        		"testing", "let's see what you can do", "testing out results", "starting the test", 
        		"did it pass the test?", "not sure if this will work", "will it work?", "I sure hope it works",
        		"Rain is missing?", "", "missing rain?", "rain is not missing as long as it is not alone", 
        		"the rainforest is pretty", "I'm ready for vacation!", "where would you go on vacation?", "rain",
        		"this skips single words!", "why are you skipping single words?", "fourth tweet", "fifth tweet",
        		"does skip sentences words!", "more stuff about rain?", "sixth tweet", "tweet number seven"};
		
		//TODO:: figure out why single words are skipped and if that is a problem
		//TODO:: Figure out why some sentences are not being clustered correctly
        TwitterClustering sentenceClustering = new TwitterClustering(tweets);
        sentenceClustering.JsonFormat();
		
	}	
}
