package riSEng;

public class FilterMayus implements GenericFilterTerm
{
	public String execute(String text) {
		
		return text.toUpperCase();
	}
}
