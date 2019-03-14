/*Main class used as a driver for the web crawling project.
 * Crawler creates a web crawler with one seeded URL and finds all out-links on the HTML document
 * Indexer will read/parse all terms and increment their frequencies.
 * */
public class Main {

	public static void main(String[] args) throws Exception {
		//Web crawler
		Crawler crawler = new Crawler("https://en.wikipedia.org/wiki/Main_Page");
		crawler.search();
		//Indexer -  processes the cleaned-up HTML documents to store in an inverted index list.
		Indexer indexer = new Indexer();
		indexer.read();
		indexer.writeToFile();
	}
}