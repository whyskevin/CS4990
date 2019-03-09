import java.io.IOException;
import java.util.*;

import org.jsoup.*;
import org.jsoup.nodes.Document;

public class Crawler {
//Fields
	private Set<String> pagesVisited = new HashSet<String>();
	private Queue<String> pagesToVisit = new LinkedList<String> ();
	
	
	private String nextURL() {
		String nextURL;
		do{
			nextURL = this.pagesToVisit.poll();
			
		}while(this.pagesVisited.contains(nextURL));
		
		this.pagesVisited.add(nextURL);
		return nextURL;
	}
	
	public void search(String url) {
		//Search for 10 pages MAX
		while(this.pagesVisited.size() < 10) {
			String currentURL;
			CrawlLeg leg = new CrawlLeg();
			if(this.pagesToVisit.isEmpty()) {	//If there are no more URLs in the queue
				currentURL = url;
				this.pagesVisited.add(url);
			}else {								//On the first run, the seed is passed into the queue.
				currentURL = this.nextURL();	//Then all the URLs on the seed's page is added to the queue.
			}
			leg.crawl(currentURL);				//Crawls the "currentURL" which the URL at the front of the queue.
			this.pagesToVisit.addAll(leg.getLinks());	//Links on the currentURL page are added to the queue.
		}
		System.out.println("Search done.");
	}	
}
