package riSEng;

public class FilterMinus implements GenericFilterTerm
{
	public String execute(String text) {
		
		return text.toLowerCase();
	}
}
