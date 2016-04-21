package cards;

public class Card {

	public String face, naipe;
	public int val;
	
	public Card(String face, String naipe){
		this.face=face;
		this.naipe=naipe;
		if(face.equals("A"))
            this.val=11;
		if(face.equals("2"))
            this.val=2;
		if(face.equals("3"))
            this.val=3;
		if(face.equals("4"))
            this.val=4;
		if(face.equals("5"))
            this.val=5;
		if(face.equals("6"))
            this.val=6;
		if(face.equals("7"))
            this.val=7;
		if(face.equals("8"))
            this.val=8;
		if(face.equals("9"))
            this.val=9;
		if(face.equals("10"))
            this.val=10;
		if(face.equals("J"))
            this.val=10;
		if(face.equals("Q"))
            this.val=10;
		if(face.equals("K"))
            this.val=10;
	}

	@Override
	public String toString() {
		return face + naipe;
	}
}
