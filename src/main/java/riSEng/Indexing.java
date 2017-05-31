package riSEng;

import java.io.File;
import java.util.Collections;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Indexing 
{
	public static int numFiles; 
	
	
	public void execute() throws IOException 
	{
			
		String text;
		ArrayList<String> vText;
		File file = new File(AppPath.PRUEBA);
		numFiles = file.list().length;
		
		FilterTermsManager ftm = new FilterTermsManager();
		GenericStopWordsFilter gswf = new GenericStopWordsFilter();
		MyStemmer st = new MyStemmer();
		RemoveShortWords rsw = new RemoveShortWords();
		ObtainTFIDF obttfidf = new ObtainTFIDF();
		
		HashMap<String,Double> sdmap = new HashMap<String,Double>();
		HashMap<String, Tupla<Double, HashMap<File, Double>>> reverseIndex = new HashMap<String, Tupla<Double, HashMap<File, Double>>>();
	
		//ftm.addGFT(new FilterMayus());		
		ftm.addGFT(new FilterMinus());
		ftm.addGFT(new SpecialCharacterFilter("'",""));
		ftm.addGFT(new SpecialCharacterFilter("[^-\\w]", " "));
		ftm.addGFT(new SpecialCharacterFilter("^-+", ""));
		ftm.addGFT(new SpecialCharacterFilter("\\b[0-9]+\\b", " "));
		ftm.addGFT(new SpecialCharacterFilter("-+ | -+", " "));
		ftm.addGFT(new SpecialCharacterFilter(" +", " "));
		
		
		
		//File f = new File(AppPath.CORPUS_DATA);
		File f = new File(AppPath.PRUEBA);
		
		if(f.isDirectory())
		{
			 for(File fil : f.listFiles())
				try {
						// System.out.println(fil.getPath());
						 text = new String(Files.readAllBytes(Paths.get(fil.getPath())));
						 
						 
						//text = new String(Files.readAllBytes())
						 text = ftm.execute(text);
						 
						 
						 vText = new ArrayList<String>(Arrays.asList(text.split(" ")));
						 
						 
						 vText = gswf.removeStopWords(vText);
						 
						 System.out.println(vText);
						 vText = st.stem(vText);

						 vText = rsw.removeUpTo(3, vText);
						 
						 System.out.println(vText);
						 
						 sdmap = obttfidf.calcTF1(vText);
						 obttfidf.calcTF2(sdmap, fil);
						 
						 obttfidf.calcIDF();

							System.out.println(obttfidf.reverseIndex);
							System.out.println(sdmap);
							//HashMap<String,Double> ordered_sdmap = sdmap;
							//Collections.sort(ordered_sdmap.keySet());
						
						 
					 
				} catch (Exception e) 
			 		{
						e.printStackTrace();
			 		}
		}
	}

	public static void main(String[] args) throws IOException 
	{
		new Indexing().execute();
		
		
	}
}
