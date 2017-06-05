package riSEng;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Query {
	
	 FilterTermsManager ftm;
	 public static HashMap<String,Tupla<Double, HashMap<File, Double>>> reverseIndex;
	 public static HashMap<File, Double> documentLength;
	
	public void preprocessing() throws IOException
	{
		//CREACION DE FILTROS PARA LA CONSULTA
		ftm = new FilterTermsManager();

		ftm.addGFT(new SpecialCharacterFilter(" +", ""));

		ftm.addGFT(new SpecialCharacterFilter("[{}]",""));
		
		
		getreverseindex();
		
		getdoclength();
		
		
		
	}
	
	public void getdoclength() throws IOException
	{

		String dlFiltered = new String(Files.readAllBytes(Paths.get(AppPath.RES+"docLength")));
		documentLength = new HashMap<File, Double>();
		
		//Aplicamos los filtros
		dlFiltered = ftm.execute(dlFiltered);
		
		
		ArrayList<String> termsLength = new ArrayList<String>(Arrays.asList(dlFiltered.split(",")));
		//System.out.println(termsLength);
		for(String s : termsLength)
		{
			ArrayList<String> doc = new ArrayList<String>(Arrays.asList(s.split("=")));
			documentLength.put(new File(doc.get(0)), Double.parseDouble(doc.get(1)));
		}
		
	}
	
	public void getreverseindex() throws IOException
	{
		
		String rInd = new String(Files.readAllBytes(Paths.get(AppPath.RES+"reverseIndex")));
		reverseIndex = new HashMap<String, Tupla<Double, HashMap<File, Double>>>();
	
		//Aplicamos los filtros
		rInd = ftm.execute(rInd);
		
		ArrayList<String> termsIndex = new ArrayList<String>(Arrays.asList(rInd.split("@@")));
		
		/* Como los tf de cada palabra están colocados
		 * palabra = idf
		 * Path = tf, path2 = tf... 
		 */
		
		int i = termsIndex.size()-1;
		System.out.println(i);
		ArrayList<String> wordIDF = new ArrayList<String>(Arrays.asList(termsIndex.get(i-1).split(",")));
		
		ArrayList<String> pathTF = new ArrayList<String>(Arrays.asList(termsIndex.get(i).split(",")));
		
		termsIndex.set(i, wordIDF.get(termsIndex.size()-1)+pathTF.toString());
		
		for(String s: termsIndex)
		{
			 wordIDF = new ArrayList<String>(Arrays.asList(termsIndex.get(i-1).split(",")));
			 pathTF = new ArrayList<String>(Arrays.asList(termsIndex.get(i).split(",")));
			 pathTF.remove(pathTF.size()-1);
			 termsIndex.set(i, wordIDF.get(termsIndex.size()-1)+pathTF.toString());
		
		}
		termsIndex.remove(0);
		
		for(String s: termsIndex)
		{
			s = new SpecialCharacterFilter("]", "").execute(s);
			s = new SpecialCharacterFilter(" +","").execute(s);
			
			ArrayList<String> str = new ArrayList<String>(Arrays.asList(s.split("\\x5b")));
			ArrayList<String> splitIDF = new ArrayList<String>(Arrays.asList(str.get(0).split("=")));
			ArrayList<String> splitTF = new ArrayList<String>(Arrays.asList(str.get(1).split(",")));
		
			double idf = Double.parseDouble(splitIDF.get(1));
			reverseIndex.put(splitIDF.get(0), new Tupla<Double, HashMap<File, Double>>(idf, new HashMap<File, Double>()));
			
			for(String t : splitTF)
			{
				ArrayList<String> fileTF = new ArrayList<String>(Arrays.asList(t.split("=")));
				reverseIndex.get(splitIDF.get(0)).docPeso().put(new File(fileTF.get(0)), Double.parseDouble(fileTF.get(1)));
			}
			
			
			
			
			
			
		}
	}

}
