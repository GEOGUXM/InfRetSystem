package riSEng;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

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
		
		HashMap<String,Double> freq = new HashMap<String,Double>();
		HashMap<String, Tupla<Double, HashMap<File, Double>>> reverseIndex = new HashMap<String, Tupla<Double, HashMap<File, Double>>>();
	
		//ftm.addGFT(new FilterMayus());		
		ftm.addGFT(new FilterMinus());
		ftm.addGFT(new SpecialCharacterFilter("'",""));
		ftm.addGFT(new SpecialCharacterFilter("[^-\\w]", " "));
		ftm.addGFT(new SpecialCharacterFilter("^-+", ""));
		ftm.addGFT(new SpecialCharacterFilter("\\b[0-9]+\\b", " "));
		ftm.addGFT(new SpecialCharacterFilter("-+ | -+", " "));
		ftm.addGFT(new SpecialCharacterFilter(" +", " "));
		
		
		int i = 0;
		//File f = new File(AppPath.CORPUS_DATA);
		File f = new File(AppPath.PRUEBA);
		
		if(f.isDirectory())
		{
			 for(File fil : f.listFiles())
				//try {
			 {	// System.out.println(fil.getPath());
				 	mostrarIndexing(i);
						 text = new String(Files.readAllBytes(Paths.get(fil.getPath())));
				
						//text = new String(Files.readAllBytes())
						 text = ftm.execute(text);
	
						 vText = new ArrayList<String>(Arrays.asList(text.split(" ")));
				
						 vText = gswf.removeStopWords(vText);
						 
						 vText = rsw.removeUpTo(3, vText);
						 //System.out.println(vText);
						 vText = st.stem(vText);

						// System.out.println(vText);
						 
						 freq = obttfidf.calcTF1(vText);
						 obttfidf.calcTF2(freq, fil);
			 }			
						 
			obttfidf.calcIDF();

			try{
			PrintWriter reverseIndexWriter = new PrintWriter(AppPath.RES+"reverseIndex");
			PrintWriter docLengthWriter = new PrintWriter(AppPath.RES+"docLength");
			System.out.println(reverseIndex);
			reverseIndexWriter.write(reverseIndex.toString());
			docLengthWriter.write(obttfidf.calcLength().toString());
			
			reverseIndexWriter.close();
			docLengthWriter.close();
			}
			catch(Exception e){}
						
						 
					 
				/*} catch (Exception e) 
			 		{
						e.printStackTrace();
			 		}
		}*/
			 
		}
	}
	
	public void mostrarIndexing(int i){
		double porcentaje = (i*100.0/numFiles);
		int cuarto = (int)porcentaje/4;
		String barra = "<";
		for(int j = 0; j < cuarto; j++){
			barra = barra+"=";
		}
		for(int j = cuarto; j < 25; j++){
			barra = barra+" ";
		}
		NumberFormat formatter = new DecimalFormat("#0.00");
		System.out.print("\r"+barra+">\t"+formatter.format(porcentaje)+"% de archivos indexados");
	}
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException 
	{
		Scanner scan = new Scanner(System.in);
		Indexing I = new Indexing();
		I.execute();
		//Scanner scan = new Scanner(System.in);
		//new Indexing().execute();
		
		InfRetrieval infret = new InfRetrieval();
		
		infret.getIndex();

		System.out.println("Query: ");
		String mainQuery = scan.nextLine();
		
		infret.read(mainQuery);
		infret.retrieve();
		try{
		int limit = 10;
		
		if(DocSort.DocList.size()<limit) limit = DocSort.DocList.size();
		
		for(int i = 0; i<limit; ++i)
		{
			Entry<File, Double> mapfildoub = DocSort.DocList.get(i);
			System.out.println("Doc ID: " + mapfildoub.getKey().getName() + "; weight: " + mapfildoub.getValue());
		}
		}catch(Exception e) {e.printStackTrace();}

	}
}
