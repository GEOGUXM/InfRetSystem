package riSEng;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;

public class ObtainTFIDF 
{
	
	//idf: num de terminos
	
	public static HashMap<String, Tupla<Double, HashMap<File, Double>>> reverseIndex = new HashMap<String, Tupla<Double,HashMap<File, Double>>>();
	private static double numFiles = 0; //Numb of files computed to get IDF, incremented up to n during TF 2nd calculation.
	
	public HashMap<String,Double> calcTF1(ArrayList<String> vText)
	{
		HashMap<String, Double> sdMap = new HashMap<String,Double>();
		double oc = 0;
		
		for(String p : vText)
		{
			if(sdMap.containsKey(p)) oc = 1 + sdMap.get(p);
			else oc = 1;
			
			sdMap.put(p, oc);	
		}//¿Está p en el mapa? Lo incremento, si no; lo creo.

		return sdMap;
	}
	
	public void calcTF2(HashMap<String, Double> sdMap, File f)
	{
		double tf = 0;
		++numFiles;
		
		for(String p: sdMap.keySet())
		{
			tf = 1+Math.log(sdMap.get(p))/Math.log(2);
			
			if(reverseIndex.containsKey(p))
			{
				
			}
		}
		
	}
}
