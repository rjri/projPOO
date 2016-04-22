package cards;

public class Deal {

	int bet_value;
	private Shoe shoe;
	Hand p_hand;
	Hand d_hand;
	private boolean bust=false;
	private boolean d_bust=false;
	
	public Deal(int bet, Shoe sh){
		this.bet_value=bet;
		this.shoe=sh;
		this.p_hand=new Hand(shoe.getCard(),shoe.getCard());
		this.d_hand=new Hand(shoe.getCard(),shoe.getCard());
	}
	
	public void showDeal(){
		System.out.println("Dealer's hand: ["+d_hand.cards.peekFirst()+", X]");
		System.out.println("Player's hand: "+p_hand+" ("+p_hand.value()+")");
		if(p_hand.value()==21){
			System.out.println("Blackjack!");
		}
	}
	
	public void hit(){
		if(!bust){
			p_hand.hit(shoe.getCard());
			bust=p_hand.bust();
		}
	}
	
	public void dealer_play(){
		while(!d_bust && d_hand.value()<17){
			d_hand.hit(shoe.getCard());
			d_bust=d_hand.bust();
		}
		System.out.println("Dealer's hand: "+d_hand+" ("+d_hand.value()+")");
		if(d_hand.blackjack){
			System.out.println("Blackjack!");
		}
	}
	
	public int payout(){
		if(bust){
			return -bet_value;
		}
		if(p_hand.blackjack){
			if(d_hand.blackjack){
				return 0;
			}else{
				return (int) Math.round(1.5*bet_value);
			}
		}else{
			if(d_hand.blackjack){
				return -bet_value;
			}
			if(d_bust){
				return bet_value;
			}
			if(p_hand.value()>d_hand.value()){
				return bet_value;
			}else{
				if(p_hand.value()==d_hand.value()){
					return 0;
				}else{
					return -bet_value;
				}
			}
		}
	}
}
