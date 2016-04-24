package cards;

public class Deal {

	int bet_value;
	int ins_value=0;
	private Shoe shoe;
	Hand p_hand;
	static Hand d_hand;
	private boolean bust=false;
	static boolean d_bust;
	private boolean split=false;
	private boolean splitc=false;
	private Deal d1, d2;
	public boolean enddeal=false;
	boolean d1done=false;
	static boolean dealerdone;
	public int po=0;
	public Player p;
	
	public Deal(int bet, Shoe sh, Player p){
		this.bet_value=bet;
		this.shoe=sh;
		this.p_hand=new Hand(shoe.getCard(),shoe.getCard());
		d_hand=new Hand(shoe.getCard(),shoe.getCard());
		this.p=p;
		dealerdone=false;
		d_bust=false;
	}
	
	public Deal(int bet, Shoe sh, Player p, Card pc){
		this.bet_value=bet;
		this.shoe=sh;
		this.p_hand=new Hand(pc,shoe.getCard());
		this.p=p;
		splitc=true;
		if(pc.val==11){
			po=payout();
			enddeal=true;
		}
	}
	
	public void showDeal(){
		System.out.println("Dealer's hand: ["+d_hand.cards.peekFirst()+", X]");
		System.out.println("Player's hand: "+p_hand+" ("+p_hand.value()+")");
		if(p_hand.value()==21){
			System.out.println("Blackjack!");
			po=payout();
			enddeal=true;
		}
	}
	
	public void hit(){
		if(!bust){
			p_hand.hit(shoe.getCard());
			bust=p_hand.bust();
		}
	}
	
	public void doubleDown(){
		bet_value*=2;
		p_hand.hit(shoe.getCard());
		bust=p_hand.bust();
	}
	
	public void dealer_play(){
		if(!dealerdone){
			while(!d_bust && d_hand.value()<17){
				d_hand.hit(shoe.getCard());
				d_bust=d_hand.bust();
				dealerdone=true;
			}
		}
		if(!splitc){
			System.out.println("Dealer's hand: "+d_hand+" ("+d_hand.value()+")");
			if(d_hand.blackjack){
				System.out.println("Blackjack!");
			}
		}
	}
	
	public int payout(){
		if(!splitc){
			if(d_hand.blackjack){
				p.balance+=ins_value;
			}else{
				p.balance-=ins_value;
			}
		}
		if(bust){
			if(!splitc){
				p.balance-=bet_value;
				System.out.println("Player loses and his current balance is "+p.balance);
			}
			return -bet_value;
		}
		if(p_hand.blackjack){
			if(d_hand.blackjack){
				if(!splitc){
					System.out.println("Player pushes and his current balance is "+p.balance);
				}
				return 0;
			}else{
				if(!splitc){
					p.balance+=(int) Math.round(1.5*bet_value);
					System.out.println("Player wins and his current balance is "+p.balance);
				}
				return bet_value;//if it was split, no bonus
			}
		}else{
			if(d_hand.blackjack){
				if(!splitc){
					p.balance-=bet_value;
					System.out.println("Player loses and his current balance is "+p.balance);
				}
				return -bet_value;
			}
			if(d_bust){
				if(!splitc){
					p.balance+=bet_value;
					System.out.println("Player wins and his current balance is "+p.balance);
				}
				return bet_value;
			}
			if(p_hand.value()>d_hand.value()){
				if(!splitc){
					p.balance+=bet_value;
					System.out.println("Player wins and his current balance is "+p.balance);
				}
				return bet_value;
			}else{
				if(p_hand.value()==d_hand.value()){
					if(!splitc){
						System.out.println("Player pushes and his current balance is "+p.balance);
					}
					return 0;
				}else{
					if(!splitc){
						p.balance-=bet_value;
						System.out.println("Player loses and his current balance is "+p.balance);
					}
					return -bet_value;
				}
			}
		}
	}
	
	public void input(String s){
		if(split){
			if(!d1.enddeal){
				d1.input(s);
				if(d1.enddeal){
					System.out.println("First split done, now playing:");
					d2.showDeal();
					po+=d1.po;
				}
			}else{
				if(!d1done){
					d1done=true;
				}
				if(!d2.enddeal){
					d2.input(s);
					if(d2.enddeal){
						po+=d2.po;
						if(!splitc){
							System.out.println("Dealer's hand: "+d_hand+" ("+d_hand.value()+")");
							if(d_hand.blackjack){
								System.out.println("Blackjack!");
								po+=ins_value;
							}else{
								po-=ins_value;
							}
							p.balance+=po;
							System.out.println("Player gains "+po+" and his current balance is "+p.balance);
						}
						enddeal=true;
					}
				}
			}
		}else{
			if(s.equals("h")){
				hit();
				System.out.println("Player's hand: "+p_hand+" ("+p_hand.value()+")");
				if(bust){
					po=payout();
					enddeal=true;
				}
			}
			if(s.equals("s")){
				dealer_play();
				po=payout();
				enddeal=true;
			}
			if(s.equals("2")){
				if(p_hand.cards.size()==2){
					doubleDown();
					System.out.println("Player's hand: "+p_hand+" ("+p_hand.value()+")");
					if(!bust){
						dealer_play();
					}
					po=payout();
					enddeal=true;
				}else{
					System.out.println("2: illegal command");	
				}
			}
			if(!splitc&&s.equals("u")){
				if(p_hand.cards.size()==2){
					p.balance-=(int) Math.round(0.5*bet_value);
					System.out.println("Player surrenders and his current balance is "+p.balance);
					enddeal=true;
				}else{
					System.out.println("u: illegal command");	
				}
			}
			if(!splitc&&s.equals("i")){
				if(p_hand.cards.size()==2 && d_hand.cards.peekFirst().val==11){
					ins_value=bet_value;
					System.out.println("Player takes insurance");
				}else{
					System.out.println("i: illegal command");	
				}
			}
			if(s.equals("p")){
				if(p_hand.cards.size()==2 && p_hand.cards.peekFirst().val==p_hand.cards.peekLast().val){
					d1=new Deal(bet_value,shoe,p,p_hand.cards.peekFirst());
					System.out.println("First deal:");
					d1.showDeal();
					d2=new Deal(bet_value,shoe,p,p_hand.cards.peekLast());
					System.out.println("Second deal:");
					d2.showDeal();
					System.out.println("Now playing for the first deal");
					split=true;
				}else{
					System.out.println("p: illegal command");
				}
			}
		}
	}
}
