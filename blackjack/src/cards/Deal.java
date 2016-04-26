package cards;

import java.util.Iterator;
import java.util.LinkedList;

public class Deal {

	int bet_value;
	int ins_value=0;
	Shoe shoe;
	Hand p_hand;
	static Hand d_hand;
	private boolean bust=false;
	static boolean d_bust;
	private boolean split=false;
	private boolean splitc=false;
	private Deal d1, d2;
	public boolean enddeal=false;
	static boolean dealerdone;
	static LinkedList<Hand> hands;
	private Iterator<Hand> toph;
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
		hands=new LinkedList<Hand>();
	}
	
	public Deal(int bet, Shoe sh, Player p, Card pc){
		this.bet_value=bet;
		this.shoe=sh;
		this.p_hand=new Hand(pc,shoe.getCard());
		this.p=p;
		splitc=true;
		if(pc.val==11){
			//po=payout(p_hand);
			Player.bets++;
			enddeal=true;
		}
	}
	
	public void showDeal(){
		System.out.println("Dealer's hand: ["+d_hand.cards.peekFirst()+", X]");
		System.out.println("Player's hand: "+p_hand+" ("+p_hand.value()+")");
		if(p_hand.value()==21){
			System.out.println("Blackjack!");
			Player.bets++;
			if(!splitc){
				dealer_play();
				po=payout(p_hand);
			}
			this.enddeal=true;
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
	
	public int payout(Hand p_hand){
		bust=p_hand.bust();
		if(!splitc&&!split){
			if(d_hand.blackjack){
				p.balance+=ins_value;
			}else{
				p.balance-=ins_value;
			}
		}
		if(bust){
			if(!splitc&&!split){
				p.balance-=bet_value;
				System.out.println("Player loses and his current balance is "+p.balance);
			}
			Player.losses++;
			return -bet_value;
		}
		if(p_hand.blackjack){
			if(d_hand.blackjack){
				if(!splitc&&!split){
					System.out.println("Player pushes and his current balance is "+p.balance);
				}
				Player.pbj++;
				Player.dbj++;
				Player.pushes++;
				return 0;
			}else{
				if(!splitc&&!split){
					p.balance+=(int) Math.round(1.5*bet_value);
					Player.pbj++;
					System.out.println("Player wins and his current balance is "+p.balance);
				}
				Player.wins++;
				return bet_value;//if it was split, no bonus
			}
		}else{
			if(d_hand.blackjack){
				if(!splitc&&!split){
					p.balance-=bet_value;
					System.out.println("Player loses and his current balance is "+p.balance);
				}
				Player.dbj++;
				Player.losses++;
				return -bet_value;
			}
			if(d_bust){
				if(!splitc&&!split){
					p.balance+=bet_value;
					System.out.println("Player wins and his current balance is "+p.balance);
				}
				Player.wins++;
				return bet_value;
			}
			if(p_hand.value()>d_hand.value()){
				if(!splitc&&!split){
					p.balance+=bet_value;
					System.out.println("Player wins and his current balance is "+p.balance);
				}
				Player.wins++;
				return bet_value;
			}else{
				if(p_hand.value()==d_hand.value()){
					if(!splitc&&!split){
						System.out.println("Player pushes and his current balance is "+p.balance);
					}
					Player.pushes++;
					return 0;
				}else{
					if(!splitc&&!split){
						p.balance-=bet_value;
						System.out.println("Player loses and his current balance is "+p.balance);
					}
					Player.losses++;
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
					if(!d1.split){
						hands.add(d1.p_hand);
					}
					System.out.println("First split done, now playing:");
					d2.showDeal();
					//po+=d1.po;
				}
			}else{
				if(!d2.enddeal){
					d2.input(s);
					if(d2.enddeal){
						if(!d2.split){
							hands.add(d2.p_hand);
						}
						//po+=d2.po;
						if(!splitc){
							dealer_play();
							//System.out.println("Dealer's hand: "+d_hand+" ("+d_hand.value()+")");
							if(d_hand.blackjack){
								//System.out.println("Blackjack!");
								po+=ins_value;
							}else{
								po-=ins_value;
							}
							toph=hands.iterator();
							int num=1;
							while(toph.hasNext()){
								Hand test=toph.next();
								int p1=payout(test);
								if(test.doublesplit){
									p1*=2;
								}
								if(p1<0){
									System.out.println("Player loses "+(-p1)+" from hand "+num);
								}
								if(p1==0){
									System.out.println("Player pushes hand "+num);
								}
								if(p1>0){
									System.out.println("Player wins "+p1+" from hand "+num);
								}
								po+=p1;
								num++;
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
					if(!splitc){
						dealer_play();
						po=payout(p_hand);
					}
					Player.bets++;
					enddeal=true;
				}
			}
			if(s.equals("s")){
				if(!splitc){
					dealer_play();
					po=payout(p_hand);
				}
				Player.bets++;
				enddeal=true;
			}
			if(s.equals("2")){
				if(p_hand.cards.size()==2 && p_hand.value()>=9 && p_hand.value()<=11){
					doubleDown();
					System.out.println("Player's hand: "+p_hand+" ("+p_hand.value()+")");
					if(!splitc){
						if(!bust){
							dealer_play();
						}
						po=payout(p_hand);
					}
					p_hand.doublesplit=true;
					Player.bets++;
					enddeal=true;
				}else{
					System.out.println("2: illegal command");	
				}
			}
			if(s.equals("u")){
				if(!splitc&&p_hand.cards.size()==2){
					p.balance-=(int) Math.round(0.5*bet_value);
					System.out.println("Player surrenders and his current balance is "+p.balance);
					Player.bets++;
					Player.losses++;
					enddeal=true;
				}else{
					System.out.println("u: illegal command");	
				}
			}
			if(s.equals("i")){
				if(!splitc&&p_hand.cards.size()==2 && d_hand.cards.peekFirst().val==11){
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
	
	public String basicStrategy(){
		Iterator<Card> top=p_hand.cards.iterator();
		boolean ace=false;
		while(top.hasNext()){
			Card a=top.next();
			if(a.val==11){
				ace=true;
			}
		}
		if(p_hand.cards.size()==2 && p_hand.cards.peekFirst().val==p_hand.cards.peekLast().val){
			if(p_hand.cards.peekFirst().val==2 || p_hand.cards.peekFirst().val==3){
				if(d_hand.cards.peekFirst().val>=4 && d_hand.cards.peekFirst().val<=7){
					return "p";
				}else{
					return "h";
				}
			}
			if(p_hand.cards.peekFirst().val==4){
				return "h";
			}
			if(p_hand.cards.peekFirst().val==5){
				if(d_hand.cards.peekFirst().val>=10){
					return "h";
				}else{
					return "2";
				}
			}
		}else{
			if(ace=true){
				//soft
			}else{
				//hard
			}
		}
		return null;
	}
}
