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
		
		/*for (int i = 0; i < vText.size(); i++) {
			String p = vText.get(i);
		}*/
		return vText;	
	}
	
}
