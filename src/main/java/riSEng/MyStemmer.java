package riSEng;

import java.util.ArrayList;
import org.tartarus.snowball.ext.englishStemmer;

public class MyStemmer 
{
	englishStemmer engSt = new englishStemmer();
	
	public ArrayList<String> stem(ArrayList<String> vText)
	{
		
		for(String p : vText)
		{
			engSt.setCurrent(p);
			if(engSt.stem())
			{
				vText.set(vText.indexOf(p), engSt.getCurrent());
			}
		}
		
		return vText;	
	}
	
}
