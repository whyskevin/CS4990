/* The Indexer class is the inverted index list.
 * It will store Index terms in a HashMap for efficient accessibility.
 */

import java.util.*;
import java.io.*;
import java.net.URI;

public class Indexer {
	public static File CSV = new File("/Users/Kevin/eclipse-workspace/SearchEngine/src/report.csv");
	public static File IndexList = new File("/Users/Kevin/eclipse-workspace/SearchEngine/src/index_list.csv");
	static HashMap <String, Integer> indexList;	//Index -> (term,frequency across all docs)
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
				while(in.hasNext()) {	//Checks if the file has the next token
					term = in.next();
//					System.out.println(term);
					if(indexList.containsKey(term))		//If the indexList has the term
					{
						int frequency = indexList.get(term);	//Gets frequency
						indexList.put(term, frequency+1);
					}else {
						indexList.put(term, 1);
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
	        		String term = arrayOfKeys[x];
		        	StringBuilder s = new StringBuilder();
		        	s.append(term);
		        	s.append(',');
		        	s.append(indexList.get(term));
		        	s.append('\n');
		        	FileWriter fw = new FileWriter(Indexer.IndexList, true);
//		        	System.out.println(s.toString());
		        	fw.write(s.toString());
		        	fw.flush();
		        	fw.close();
	        	}
	        }catch(IOException io){
	        	System.out.println(io.getMessage());
	        }	
	}
	
	
}
