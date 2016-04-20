package cards;

public class Main {

	public static void main(String[] args) {
		Deck deck=new Deck();
		System.out.println(deck);
		Shoe shoe=new Shoe(2);
		System.out.println(shoe);
		for(int i=0;i<110;i++){
			System.out.println(shoe.getCard());
		}
		System.out.println(shoe);
	}

}
