import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class CrawlLeg {

	    // We'll use a fake USER_AGENT so the web server thinks the robot is a normal web browser.
	    private static final String USER_AGENT =
	            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	    private List<String> links = new LinkedList<String>();
	    private Document htmlDocument;
	    private static int docNumber = 0;

	    /**
	     * This performs all the work. It makes an HTTP request, checks the response, and then gathers
	     * up all the links on the page. Perform a searchForWord after the successful crawl
	     * 
	     * @param url
	     *            - The URL to visit
	     * @return whether or not the crawl was successful
	     */
	    public boolean crawl(String url)
	    {
	        try
	        {
	            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
	            Document htmlDocument = connection.get();
	            this.htmlDocument = htmlDocument;
	            if(connection.response().statusCode() == 200) // 200 is the HTTP OK status code
	                                                          // indicating that everything is great.
	            {
	                System.out.println("\nVisiting: " + url);
	            }
	            if(!connection.response().contentType().contains("text/html"))
	            {
	                System.out.println("**Failure** Retrieved something other than HTML");
	                return false;
	            }
	            System.out.println("Retrieved HTML: \n");
//	            System.out.println(htmlDocument);
	            BufferedWriter  writer = null;
	            try
	            {
	                writer = new BufferedWriter( new FileWriter(new File("/Users/Kevin/eclipse-workspace/SearchEngine/src/repository", "doc"+docNumber+".txt")));
	                writer.write(url+"\n");
	                writer.write(htmlDocument.toString());
	                docNumber++;
	            }
	            catch ( IOException e)
	            {
	            	System.out.println(e);
	            }
	            
	            
	            Elements linksOnPage = htmlDocument.select("a[href]");	//Finds the all <a href = .. </a> links
	            System.out.println("Found (" + linksOnPage.size() + ") links");
	            for(Element link : linksOnPage)
	            {
//	            	System.out.println(link);
	                this.links.add(link.absUrl("href"));
	            }
	            return true;
	        }
	        catch(IOException ioe)
	        {
	        	System.out.println(ioe);
	            // We were not successful in our HTTP request
	            return false;
	        }
	    }

	    public List<String> getLinks()
	    {
	        return this.links;
	    }
}
