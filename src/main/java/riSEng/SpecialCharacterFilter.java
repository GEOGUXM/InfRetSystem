package riSEng;

public class SpecialCharacterFilter implements GenericFilterTerm 
{
	
	private String exp;
	private String rep;
	
	public SpecialCharacterFilter(String e, String r) 
	{
		this.exp = e;
		this.rep = r;
		
	}
	
	public String execute(String text) 
	{
	
		return text.replaceAll(exp, rep);
		
	}
	
}
