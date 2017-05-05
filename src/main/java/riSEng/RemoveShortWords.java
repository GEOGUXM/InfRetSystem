package riSEng;

import java.util.ArrayList;

public class RemoveShortWords 
{

	public ArrayList<String> removeUpTo(int nChars, ArrayList<String> vText)
	{
		/*for(String s: vText)
		{
			if(s.length()<nChars) vText.remove(vText.indexOf(s));
			
		}*/
		
		for (int i = 0; i < vText.size(); i++) {
			String s = vText.get(i);
			if(s.length()<=nChars) {
				vText.remove(vText.indexOf(s));
				--i;
			}
		}
		
		return vText;
	}
	
}