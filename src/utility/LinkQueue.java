/**
 * LinkQueue is to record URLs, including visited URLs and these not visited
 */
package utility;

import java.util.*;

public class LinkQueue {
	private static Set<String> visitedURL = new HashSet<String>();
	private static Queue<String> unVisitedURL = new LinkedList<String>();
	
	//get the URL queue
	public static Queue<String> getUnVisitedURL(){
		return unVisitedURL;
	}
	//add visited URLs to visitedURL
	public static void addVisitedURL(String url){
		visitedURL.add(url);
	}
	//remove visited URL from visitedURL
	public static void removeVisitedURL(String url){
		visitedURL.remove(url);
	}
	public static String unVisitedURLDeQueue(){
		return unVisitedURL.poll();
	}
	public static void addUnVisitedURL(String url){
		if(url!=null && !url.trim().equals("") && !visitedURL.contains(url) && !unVisitedURL.contains(url))
			unVisitedURL.add(url);
	}
}
