import java.io.FileWriter;
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
		while(this.pagesVisited.size() < 2) {
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
		
        //Store URL and number of links to the CSV
//        try {
//        	for(int i = 0; i < CrawlLeg.urlAndLinks.size(); i++) {
//        	StringBuilder s = new StringBuilder();
//        	s.append(docNumber);
//        	s.append(',');
//        	s.append(url);
//        	s.append(',');
//        	s.append(linksOnPage.size());
//        	s.append('\n');
//        	FileWriter fw = new FileWriter(Indexer.CSV);
//        	System.out.println(s.toString());
//        	fw.write(s.toString());
//        	}
//        	fw.flush();
//        	fw.close();
//        }catch(IOException io){
//        	System.out.println(io.getMessage());
//        }
		
		System.out.println("Search done.");
	}	
}
