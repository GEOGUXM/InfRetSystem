package riSEng;

public class Tupla<S, T> {
	

	    public S x;
	    public T y;
	    public void setIDF(S x){this.x = x;}
	    public S IDF(){return x;}
	    public T docPeso(){return y;}
	    public Tupla(S x, T y) {
	        this.x = x;
	        this.y = y;
	    }
	}


