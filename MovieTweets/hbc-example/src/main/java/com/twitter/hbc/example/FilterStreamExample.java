/**
 * Copyright 2013 Twitter, Inc.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package com.twitter.hbc.example;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;
import org.json.*;

public class FilterStreamExample {

  public static void run(String consumerKey, String consumerSecret, String token, String secret) throws Exception {
    BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
    StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
    // add some track terms
    endpoint.trackTerms(Lists.newArrayList("#AAPBreakUp"));

    Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);
    // Authentication auth = new BasicAuth(username, password);

    // Create a new BasicClient. By default gzip is enabled.
    Client client = new ClientBuilder()
            .hosts(Constants.STREAM_HOST)
            .endpoint(endpoint)
            .authentication(auth)
            .processor(new StringDelimitedProcessor(queue))
            .build();

    // Establish a connection
    client.connect();

    buildDS();
    // Do whatever needs to be done with messages
    int Limit = 20;
    for (int msgRead = 0; msgRead < Limit; msgRead++) {
      String msg = queue.take();
      JSONObject json = new JSONObject(msg);
      String tweet=  (String) json.get("text");
      System.out.println(tweet + "Sentiment =" + classifyText(tweet));      
    }

    client.stop();

  }
  
  //Unit test
/*  public static void main(String[] args) {
	    try {
	      //SampleStreamExample.run(args[0], args[1], args[2], args[3]);
	    	buildDS();
	    	System.out.println(classifyText("The Movie,is #fantaStic"));
	    } catch (Exception e) {
	      System.out.println(e);
	    }
	  }*/
  
  //Data Structure holding polarity .
  static Set<String> positiveList;
  static Set<String> negativeList;
  public static void buildDS() {
	  positiveList = new HashSet<String>();
	  negativeList = new HashSet<String>();
	  
	  //add from file
	  inserFromFile("positive-words.txt", positiveList);
	  inserFromFile("negative-words.txt", negativeList);	  
	 /* Data sources are provided by- http://www.cs.uic.edu/~liub/FBS/sentiment-analysis.html#datasets */
  }
  
  //predictor 
  public static String classifyText(String msg) {
	 
	  //Delimeters need to be further extended.
	StringTokenizer st = new StringTokenizer(msg,"[,. #]+");
	int positive =0 ,negative =0 ,neutral =0;
	while(st.hasMoreTokens()) {
		String next = st.nextToken().toLowerCase();
		//System.out.println(next);
		positive += (positiveList.contains(next) ? 1: 0);
		negative += (negativeList.contains(next) ? 1: 0);
		
		//See whether neutral adds any value, TODO -
		if(!positiveList.contains(next) && !negativeList.contains(next))
		neutral += (positiveList.contains(next) ? 1: 0);
	}
	return (positive == negative) ? "Neutral" : ((positive > negative) ? "Positive" :"Negative");
  }
  
  //helper function.
  public static void inserFromFile(String filename, Set<String> list) {
	  try 
	  {
	  InputReader in = new InputReader(filename);
	  while(in.hasMoreTokens()) {
		  list.add(in.nextToken());
	  }
	  }
	  catch(Exception e) {
		  e.printStackTrace();
	  }
  }
  

}


//IO
class InputReader {
    BufferedReader reader;
    StringTokenizer tokenizer;

    InputReader() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    InputReader(String fileName) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(new File(fileName)));
    }

    String readLine() throws IOException {
        return reader.readLine();
    }

    String nextToken() throws IOException {
        while (tokenizer == null || !tokenizer.hasMoreTokens())
            tokenizer = new StringTokenizer(readLine(),"[-]");
        return tokenizer.nextToken();
    }

    boolean hasMoreTokens() throws IOException {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            String s = readLine();
            if (s == null)
                return false;
            tokenizer = new StringTokenizer(s);
        }
        return true;
    }

    int nextInt() throws NumberFormatException, IOException {
        return Integer.parseInt(nextToken());
    }

    long nextLong() throws NumberFormatException, IOException {
        return Long.parseLong(nextToken());
    }

    double nextDouble() throws NumberFormatException, IOException {
        return Double.parseDouble(nextToken());
    }
}

