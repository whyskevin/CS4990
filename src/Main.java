import java.net.*;
import java.io.*;

public class Main {


public static void main(String[] args) throws Exception {
		//Web crawler
		Crawler crawler = new Crawler();
		crawler.search("https://yahoo.com");
		//Indexer -  processes the cleaned-up HTML documents to store in an inverted index list.
		Indexer indexer = new Indexer();
		indexer.read();
	}
}