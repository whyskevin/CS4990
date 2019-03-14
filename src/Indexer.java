/* The Indexer class is the inverted index list.
 * It will store Index terms in a HashMap for efficient accessibility.
 * Stored (term, frequency) are used for analysis of Zipf's Law
 */

import java.util.*;
import java.io.*;

public class Indexer {
	public static File CSV = new File("/Users/Kevin/eclipse-workspace/SearchEngine/src/report.csv");
	public static File IndexList = new File("/Users/Kevin/eclipse-workspace/SearchEngine/src/index_list.csv");
	static HashMap <String, Integer> indexList;	//Index -> (term,frequency across all docs)
	public final File folder = new File ("/Users/Kevin/eclipse-workspace/SearchEngine/src/body_text");

	public Indexer() {
		indexList = new HashMap<>();
	}

	//Will read all files in the "/repository" directory and count frequency for each term.
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
						indexList.put(term, frequency+1);		//Increments frequency is term exists
					}else {	
						indexList.put(term, 1);					//Else put in w/ 1 freq
					}
				}
				in.close();
			}else{
				System.out.println("Directory contains bad file");
				return false;
			}
		}
		return true;
	}

	//Writes the terms and frequency to the "index_list.csv" in (term,freq) pairs
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
				fw.write(s.toString());
				fw.flush();
				fw.close();
			}
		}catch(IOException io){
			System.out.println(io.getMessage());
		}	
	}


}
