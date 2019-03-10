
/*Inverted index. Stores the term as a key, the url, and the frequency of the term.
 *Indexes can then be ranked by the TF*IDF weight.
 * */

public class Index {
	private String key;
	private String url;
	private int frequency;
	
	public Index(String k, String u, int f) {
		key = k;
		url = u;
		f = frequency;
	}
	
	public void incrementFrequency() {
		frequency++;
	}
	
	public String getKey() 
	{
		return key;
	}
	
	public String getURL() {
		return url;
	}
}
