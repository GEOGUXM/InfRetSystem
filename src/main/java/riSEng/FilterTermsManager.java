package riSEng;
import java.util.ArrayList;

public class FilterTermsManager 
{
	private ArrayList<GenericFilterTerm> filters = new ArrayList<GenericFilterTerm>();
		
	public void addGFT(GenericFilterTerm gft)
	{
		filters.add(gft);
	}
	
	public String execute(String text)
	{
		
		for(GenericFilterTerm fil : filters)
			text = fil.execute(text);
		return text;
	}
		
}
