package src;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.apache.*;
import org.apache.http.HttpEntity;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.commons.logging.*;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


/**
 * Created by Stephanie on 11/22/2015.
 */

public class TwitterClustering {
    private String[] tweets;
   
    public TwitterClustering(String[] tweets) {
        this.tweets = tweets;
    }

    public void JsonFormat() {
        
    	String jsonFormat;
        jsonFormat = "{\n \"type\": \"pre-sentenced\", \n \"text\": [";

        for (int i = 0; i < tweets.length; i++){
            if (i == tweets.length-1) {
                jsonFormat = jsonFormat + "\n\t{\n\t\t\"sentence\":" + " \"" + tweets[i] + "\"\n\t}";
                break;
            }
            jsonFormat = jsonFormat + "\n\t{\n\t\t\"sentence\":" + " \"" + tweets[i] + "\"\n\t},";
        }
        jsonFormat = jsonFormat + "\n] \n}";

        PostJson(jsonFormat);
    }

    public void PostJson (String jsonFormat) {
        
    	try {
        	// These code snippet use an open-source library. http://unirest.io/java
        	HttpResponse<JsonNode> response = Unirest.post("https://rxnlp-core.p.mashape.com/generateClusters")
        	.header("X-Mashape-Key", "fchp92gsyhmshVlxPBnaDOvD9kCsp1qRbw5jsnVbreoeBZXXse")
        	.header("Content-Type", "application/json")
        	.header("Accept", "application/json")
        	.body(jsonFormat)
        	.asJson();    	
        		
        	System.out.println(response.getStatus());
        	System.out.println(response.getHeaders());
        	System.out.println(response.getBody());

        	String jsonStr = response.getBody().toString();
        	JSONObject jsonObj = new JSONObject(jsonStr);
        	System.out.println(XML.toString(jsonObj));
        	
        	String format = prettyFormat(XML.toString(jsonObj), 4);
        	System.out.println(format);
        	
      	PrintWriter writer = new PrintWriter("Results.xml", "UTF-8");
        	writer.println(format);
        	writer.close();
        	
    	} catch (UnirestException e){
    			e.printStackTrace();
    			
    	} catch (IOException e) {
    		e.printStackTrace();
    	} catch (JSONException e) {
			e.printStackTrace();
		}
    }
    
    public static String prettyFormat(String input, int indent) {
        try {
            Source xmlInput = new StreamSource(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer(); 
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
        } catch (Exception e) {
            throw new RuntimeException(e); 
        }
    }

    public static String prettyFormat(String input) {
        return prettyFormat(input, 2);
    }
}
