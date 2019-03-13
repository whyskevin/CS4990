/* The Indexer class is the inverted index list.
 * It will store Index terms in a HashMap for efficient accessibility.
 */

import java.util.*;
import java.io.*;
import java.net.URI;

public class Indexer {
	public static File CSV = new File("/Users/Kevin/eclipse-workspace/SearchEngine/src/report.csv");
	static HashMap <String, HashMap<Integer,Integer>> indexList;	//Index -> (documentNumber, frequency)
	URI directory;
	public final File folder = new File ("/Users/Kevin/eclipse-workspace/SearchEngine/src/body_text");
	
	//Will read all files in the "/repository" directory and create Index objects for each term.
	public Indexer(URI dir) {
		indexList = new HashMap<>();
		directory = dir;
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
	
}
