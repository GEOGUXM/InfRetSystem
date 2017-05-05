package riSEng;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

public class GenericStopWordsFilter 
{
	
		TreeSet<String> swTree; 
		String stpwrds;
	
	public GenericStopWordsFilter() throws IOException
	{
		File f = new File(AppPath.EMPTY_WORDS);
		 stpwrds = new String(Files.readAllBytes(Paths.get(f.getPath())));
		swTree = new TreeSet<String>(Arrays.asList(stpwrds.split("\n")));	
		//System.out.println(swTree.toString());
	}
	
	public ArrayList<String> removeStopWords(ArrayList<String> vText) 
	{
		for(String s : swTree)
		{
			//System.out.print(s);
			if(vText.contains(s)) vText.remove(s);
		}
		
		return vText;
	}

}
