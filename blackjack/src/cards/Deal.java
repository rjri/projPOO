package cards;

public class Deal {

	int bet_value;
	private Shoe shoe;
	Hand p_hand;
	Hand d_hand;
	private boolean bust=false;
	private boolean d_bust=false;
	public boolean enddeal=false;
	public Player p;
	
	public Deal(int bet, Shoe sh, Player p){
		this.bet_value=bet;
		this.shoe=sh;
		this.p_hand=new Hand(shoe.getCard(),shoe.getCard());
		this.d_hand=new Hand(shoe.getCard(),shoe.getCard());
		this.p=p;
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
			p.balance-=bet_value;
			System.out.println("Player loses and his current balance is "+p.balance);
			return -bet_value;
		}
		if(p_hand.blackjack){
			if(d_hand.blackjack){
				System.out.println("Player pushes and his current balance is "+p.balance);
				return 0;
			}else{
				p.balance+=(int) Math.round(1.5*bet_value);
				System.out.println("Player wins and his current balance is "+p.balance);
				return (int) Math.round(1.5*bet_value);
			}
		}else{
			if(d_hand.blackjack){
				p.balance-=bet_value;
				System.out.println("Player loses and his current balance is "+p.balance);
				return -bet_value;
			}
			if(d_bust){
				p.balance+=bet_value;
				System.out.println("Player wins and his current balance is "+p.balance);
				return bet_value;
			}
			if(p_hand.value()>d_hand.value()){
				p.balance+=bet_value;
				System.out.println("Player wins and his current balance is "+p.balance);
				return bet_value;
			}else{
				if(p_hand.value()==d_hand.value()){
					System.out.println("Player pushes and his current balance is "+p.balance);
					return 0;
				}else{
					p.balance-=bet_value;
					System.out.println("Player loses and his current balance is "+p.balance);
					return -bet_value;
				}
			}
		}
	}
	
	public void input(String s){
		if(s.equals("h")){
			hit();
			System.out.println("Player's hand: "+p_hand+" ("+p_hand.value()+")");
			if(bust){
				payout();
				enddeal=true;
			}
		}
		if(s.equals("s")){
			dealer_play();
			payout();
			enddeal=true;
		}
	}
}
