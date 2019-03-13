/* The Indexer class is the inverted index list.
 * It will store Index terms in a HashMap for efficient accessibility.
 */

import java.util.*;
import java.io.*;
import java.net.URI;

public class Indexer {
	public static File CSV = new File("/Users/Kevin/eclipse-workspace/SearchEngine/src/report.csv");
	public static File IndexList = new File("/Users/Kevin/eclipse-workspace/SearchEngine/src/index_list.csv");
	static HashMap <String, HashMap<Integer,Integer>> indexList;	//Index -> (documentNumber, frequency)
	public final File folder = new File ("/Users/Kevin/eclipse-workspace/SearchEngine/src/body_text");
	
	//Will read all files in the "/repository" directory and create Index objects for each term.
	public Indexer() {
		indexList = new HashMap<>();
	}
	
	public boolean read() throws FileNotFoundException {
//		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();	//Array of all files in the given directory
		for(File x: listOfFiles) {
			if(x.isFile()) {	
				Scanner in = new Scanner(x);
				String term;
				int indexOfDot = x.getName().indexOf('.');
				int indexOfC = x.getName().indexOf('c');
				String digit = x.getName().substring(indexOfC+1, indexOfDot);
//				System.out.println(ddd);
				int documentNum = Integer.valueOf(digit);	//Gets the document number by looking at index of '.'
				while(in.hasNext()) {	//Checks if the file has the next token
					term = in.next();
//					System.out.println(term);
					if(indexList.containsKey(term))		//If the indexList has the term
					{
						HashMap<Integer, Integer> list = indexList.get(term);	//Gets the list of (document,term) pairs
						if(list.containsKey(documentNum)) {
							list.replace(documentNum, list.get(documentNum)+1);	//Finds the documentNumber and increments the value
						}
					}else{	//If the indexList doesn't have the term
						HashMap<Integer, Integer> newMap = new HashMap<>();	//Creates a new HashMap
						newMap.put(documentNum, 1);
						indexList.put(term, newMap);
					}
				}
			}else {
				System.out.println("Directory contains bad file");
				return false;
			}
		}		
		return true;
	}
	
	public void writeToFile()throws IOException{
		 try {
	        	Set<String> keys = Indexer.indexList.keySet();
	        	String [] arrayOfKeys = keys.toArray(new String[keys.size()]);
	        	for(int x = 0; x < arrayOfKeys.length; x++) {
		        	StringBuilder s = new StringBuilder();
		        	s.append(x);
		        	
	        		for(int i = 0; i < arrayOfKeys.length; i++) {
		        	s.append(i);
		        	s.append(',');
		        	s.append(arrayOfKeys[i]);
		        	s.append(',');
		        	s.append(CrawlLeg.urlAndLinks.get(arrayOfKeys[i]));
		        	s.append('\n');
		        	FileWriter fw = new FileWriter(Indexer.IndexList, true);
		        	System.out.println(s.toString());
		        	fw.write(s.toString());
		        	fw.flush();
		        	fw.close();
		        	}
	        	}
	        }catch(IOException io){
	        	System.out.println(io.getMessage());
	        }	
	}
	
	
}
