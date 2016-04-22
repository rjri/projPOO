package cards;

public class Main {

	public static void main(String[] args) {
		Shoe shoe=new Shoe(2);
		System.out.println(shoe);
		for(int i=0;i<110;i++){
			System.out.println(shoe.getCard());
		}
		System.out.println(shoe);
		Hand hand=new Hand(shoe.getCard(),shoe.getCard());
		System.out.println(hand);
		System.out.println(hand.value());
		hand.hit(shoe.getCard());
		System.out.println(hand);
		System.out.println(hand.value());
		System.out.println("--");
		Deal deal=new Deal(10,shoe);
		deal.showDeal();
		deal.dealer_play();
		System.out.println(deal.payout());
	}

}
