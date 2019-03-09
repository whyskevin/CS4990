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
	
	public void search(String url, String searchWord) {
		while(this.pagesVisited.size() < 10) {
			String currentURL;
			CrawlLeg leg = new CrawlLeg();
			if(this.pagesToVisit.isEmpty()) {
				currentURL = url;
				this.pagesVisited.add(url);
			}else {
				currentURL = this.nextURL();
			}
			leg.crawl(currentURL);
			boolean success = leg.searchForWord(searchWord);
			if(success) {
				System.out.println("Word found at " + currentURL);
				break;
			}
			this.pagesToVisit.addAll(leg.getLinks());
			
		}
		System.out.println("Search done.");
	}	
}
