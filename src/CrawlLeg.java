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
	    private static int docNumber = 1;
	    public static LinkedHashMap<String, Integer> urlAndLinks = new LinkedHashMap<String,Integer>();

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
	            BufferedWriter  writer = null;
	            String fileName = "doc"+docNumber+".txt";
	            try
	            {
	            	//Write document main text to the /body_text directory
	                writer = new BufferedWriter( new FileWriter(new File("/Users/Kevin/eclipse-workspace/SearchEngine/src/body_text", fileName)));
//	                writer.write(url+"\n");
//	                writer.write(htmlDocument.toString().replaceAll("<.*?>", "------------"));	//Original REGEX approach. Would leave JavaScript and other misc. values
	                writer.write(htmlDocument.body().text().replaceAll("[^A-Za-z0-9]", " ")); //Replaces all NON alpha-numeric characters
	            }
	            catch ( IOException e)
	            {
	            	System.out.println(e);
	            }
                writer.flush();
                writer.close();
                
                //Write raw HTML text to the /repository directory
                try {
//                	System.out.println("Write HTML");
                	writer = new BufferedWriter( new FileWriter( new File("/Users/Kevin/eclipse-workspace/SearchEngine/src/repository", fileName)));
                	writer.write(htmlDocument.outerHtml());
                }catch (IOException e) {
	            	System.out.println(e);
                }
                writer.flush();
                writer.close();
                
                //Finished writing to files.
	            Elements linksOnPage = htmlDocument.select("a[href]");	//Finds the all <a href = .. </a> links
//	            System.out.println("Found (" + linksOnPage.size() + ") links");
	            for(Element link : linksOnPage)
	            {
	                this.links.add(link.absUrl("href"));
	            }
	            //Store URL and link count in HashMap
	            urlAndLinks.put(url, linksOnPage.size());
                docNumber++;
	            return true;
	        }
	        catch(IOException ioe)
	        {
	        	System.out.println(ioe);
	            // We were not successful in our HTTP request
	            return false;
	        }
	    }
	    
	    public static String htmlToText(Document doc) {
	    	return doc.body().text();
	    }

	    public List<String> getLinks()
	    {
	        return this.links;
	    }
}
