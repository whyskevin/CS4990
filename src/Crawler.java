import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Crawler {
	private Set<String> pagesVisited = new HashSet<String>();
	private Queue<String> pagesToVisit = new LinkedList<String> ();
	private String seedURL;

	public Crawler(String url) {
		seedURL = url;
	}

	private String nextURL() {
		String nextURL;
		do{
			nextURL = this.pagesToVisit.poll();

		}while(this.pagesVisited.contains(nextURL));

		//		this.pagesVisited.add(nextURL);
		return nextURL;
	}

	public void search() {
		//Search for 100 pages MAX
		while(this.pagesVisited.size() < 100) {
			String currentURL;
			CrawlLeg leg = new CrawlLeg();
			if(this.pagesToVisit.isEmpty()) {	//If there are no more URLs in the queue
				currentURL = seedURL;
				this.pagesVisited.add(seedURL);
			}else {								//On the first run, the seed is passed into the queue.
				currentURL = this.nextURL();	//Then all the URLs on the seed's page is added to the queue.
			}
			if(leg.crawl(currentURL)) 			//Crawls the "currentURL" which the URL at the front of the queue.
			{			
				this.pagesVisited.add(currentURL);	//ONLY STORES AS VISITED IF THE URL RETURNED HTML
			}						this.pagesToVisit.addAll(leg.getLinks());	//Links on the currentURL page are added to the queue.
		}

		//Store URL and number of links to the CSV
		try {
			Set<String> keys = CrawlLeg.urlAndLinks.keySet();
			String [] arrayOfKeys = keys.toArray(new String[keys.size()]);
			for(int i = 0; i < arrayOfKeys.length; i++) {
				StringBuilder s = new StringBuilder();
				s.append(i);
				s.append(',');
				s.append(arrayOfKeys[i]);
				s.append(',');
				s.append(CrawlLeg.urlAndLinks.get(arrayOfKeys[i]));
				s.append('\n');
				FileWriter fw = new FileWriter(Indexer.CSV, true);
				//	        	System.out.println(s.toString());
				fw.write(s.toString());
				fw.flush();
				fw.close();
			}
		}catch(IOException io){
			System.out.println(io.getMessage());
		}

		System.out.println("Search done.");
	}	
}
