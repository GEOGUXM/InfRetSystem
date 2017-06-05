package riSEng;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DocSort {
	
	public static HashMap<File, Double> Docs;
	public static List<Map.Entry<File, Double>> DocList;
	
	public void createDocList()
	{
		Docs = new HashMap<File,Double>();
		double val;
		
		for (String s : InfRetrieval.processedvText)
		{
			if(Query.reverseIndex.containsKey(s))
			{
				for(File f : Query.reverseIndex.get(s).docPeso().keySet())
				{
					val = Query.reverseIndex.get(s).docPeso().get(f) * Math.pow(Query.reverseIndex.get(s).IDF(), 2);
					if(Docs.containsKey(f))
						val+=Docs.get(f);
					Docs.put(f, val);
				}
			}
		}
		
		for(File f : Docs.keySet())
		{
			if(Query.documentLength.containsKey(f))
			{
				Docs.put(f, Docs.get(f)/Query.documentLength.get(f));
			}
		}
				
	}
	
	public void descDocSort()
	{
		//https://stackoverflow.com/questions/11647889/sorting-the-mapkey-value-in-descending-order-based-on-the-value
		Comparator<Map.Entry<File, Double>> descsorter = new Comparator<Map.Entry<File,Double>>()
				{

					public int compare(Entry<File, Double> arg0, Entry<File, Double> arg1) {
						int res = arg0.getValue().compareTo(arg1.getValue());
						return res != 0 ? res : 1;
						
						
					}
			
				};
		DocList = new ArrayList</**/Entry<File, Double>/**/>(Docs.entrySet());
		
		Collections.sort(DocList, descsorter);
	}

}
