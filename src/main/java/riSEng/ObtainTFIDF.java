package riSEng;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;

public class ObtainTFIDF 
{
	
	//idf: num de terminos
	
	public static HashMap<String, Tupla<Double, HashMap<File, Double>>> reverseIndex = new HashMap<String, Tupla<Double,HashMap<File, Double>>>();
	private static double numFiles = 0;
	//Numb of files computed to get IDF, incremented up to n during TF 2nd calculation.
	//public static double idf_;
	
	public HashMap<String,Double> calcTF1(ArrayList<String> vText)
	{
		HashMap<String, Double> sdMap = new HashMap<String,Double>();
		double oc = 0;
		
		for(String p : vText)
		{
			if(sdMap.containsKey(p)) oc = 1 + sdMap.get(p);
			else oc = 1;
			
			sdMap.put(p, oc);	
		}

		return sdMap;
	}
	
	public void calcTF2(HashMap<String, Double> textFreq, File f)
	{
		double tf = 0;
		
		
		for(String p: textFreq.keySet())
		{
			tf = 1+Math.log(textFreq.get(p))/Math.log(2);
			
			if(reverseIndex.containsKey(p))
			{
				reverseIndex.get(p).docPeso().put(f, tf);
			} else
			{
				HashMap<File, Double> fileMap = new HashMap<File, Double>();
				fileMap.put(f, tf);
				reverseIndex.put(p, new Tupla<Double, HashMap<File,Double>>(0.0, fileMap));
			}
		}	
	}

	
	public void calcIDF()
	{
		for(String p : reverseIndex.keySet())
		{
			reverseIndex.get(p).setIDF(Math.log(Indexing.numFiles/reverseIndex.get(p).docPeso().size())/Math.log(2));
		}
	}
	
	public HashMap<File, Double> calcLength()
	{
		HashMap<File, Double> lngth = new HashMap<File, Double>();
		double idf = 0.0, tfidf = 0.0;
		
		for(String p : reverseIndex.keySet())
		{
			idf = reverseIndex.get(p).IDF();
			for(File f : reverseIndex.get(p).docPeso().keySet())
			{
				tfidf = Math.pow(reverseIndex.get(p).docPeso().get(f)*idf,2);
				if(lngth.containsKey(f)) tfidf+=lngth.get(f);
				lngth.put(f, tfidf);
			}
		}
		
		for(File f : lngth.keySet()) lngth.put(f, Math.sqrt(lngth.get(f)));
		
		return lngth;
	
		
		
	}
}
