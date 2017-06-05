package riSEng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class InfRetrieval {
	
	private String terms;
	public static ArrayList<String> vText;
	public static ArrayList<String> processedvText = new ArrayList<String>();
	Query q = new Query();
	DocSort ds = new DocSort();
	
	public void retrieve() throws IOException
	{
		FilterTermsManager ftm = new FilterTermsManager();
		GenericStopWordsFilter gswf = new GenericStopWordsFilter();
		MyStemmer st = new MyStemmer();
		RemoveShortWords rsw = new RemoveShortWords();
		
		
		terms = ftm.execute(terms);		
		
		vText = new ArrayList<String>(Arrays.asList(terms.split(" ")));
		
		vText = gswf.removeStopWords(vText);
		vText = rsw.removeUpTo(2, vText);
		vText = st.stem(vText);
		
		ds.createDocList();
		ds.descDocSort();
		
	}
	
	public void getIndex() throws IOException
	{
		
		q.preprocessing();
	}
	public void read(String s)
	{
		terms = new String(s);
	}
	

}
