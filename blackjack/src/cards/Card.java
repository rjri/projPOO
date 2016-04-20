package cards;

public class Card {

	public String val, naipe;
	
	public Card(String val, String naipe){
		this.val=val;
		this.naipe=naipe;
	}

	@Override
	public String toString() {
		return val + " " + naipe;
	}
}
