package cards;

import java.util.Iterator;
import java.util.LinkedList;

public class Deal {

	int bet_value;
	//double ins_value=0;
	Shoe shoe;
	Hand p_hand;
	static Hand d_hand;
	private boolean bust=false;
	static boolean d_bust;
	private boolean split=false;
	private boolean splitc=false;
	private boolean splitace=false;
	private boolean insure=false;
	private Deal d1, d2;
	public boolean enddeal=false;
	private boolean enddeal1=false;
	static boolean dealerdone;
	static LinkedList<Hand> hands;
	private Iterator<Hand> toph;
	public int po=0;
	public Player p;
	
	public Deal(int bet, Shoe sh, Player p){
		this.bet_value=bet;
		this.shoe=sh;
		this.p_hand=new Hand(shoe.getCard(false),shoe.getCard(false));
		d_hand=new Hand(shoe.getCard(false),shoe.getCard(true));
		this.p=p;
		dealerdone=false;
		d_bust=false;
		hands=new LinkedList<Hand>();
	}
	
	public Deal(int bet, Shoe sh, Player p, Card pc){
		this.bet_value=bet;
		this.shoe=sh;
		this.p_hand=new Hand(pc,shoe.getCard(false));
		this.p=p;
		splitc=true;
		if(pc.val==11){
			//po=payout(p_hand);
			if(p_hand.cards.peekLast().val!=11){
				Player.bets++;
				enddeal1=true;
			}else{
				splitace=true;
			}
		}
	}
	
	public void showDeal(){
		System.out.println("Dealer's hand: ["+d_hand.cards.peekFirst()+", X]");
		System.out.println("Player's hand: "+p_hand+" ("+p_hand.value()+")");
		if(p_hand.value()==21){
			System.out.println("Blackjack!");
			/*Player.bets++;
			if(!splitc){
				dealer_play();
				po=payout(p_hand);
			}
			this.enddeal=true;*/
		}
	}
	
	public void hit(){
		if(!bust){
			p_hand.hit(shoe.getCard(false));
			bust=p_hand.bust();
		}
	}
	
	public void doubleDown(){
		bet_value*=2;
		p_hand.hit(shoe.getCard(false));
		bust=p_hand.bust();
	}
	
	public void dealer_play(){
		System.out.println("Dealer's hand: "+d_hand+" ("+d_hand.value()+")");
		if(d_hand.blackjack){
			System.out.println("Blackjack!");
		}
		shoe.cardCounter(d_hand.cards.peekLast());
		if(!dealerdone){
			while(!d_bust && d_hand.value()<17){
				System.out.println("Dealer hits");
				d_hand.hit(shoe.getCard(false));
				System.out.println("Dealer's hand: "+d_hand+" ("+d_hand.value()+")");
				d_bust=d_hand.bust();
				dealerdone=true;
			}
		}
		if(!d_bust){
			System.out.println("Dealer stands");
		}else{
			System.out.println("Dealer busts");
		}
		/*if(!splitc){
			System.out.println("Dealer's hand: "+d_hand+" ("+d_hand.value()+")");
			if(d_hand.blackjack){
				System.out.println("Blackjack!");
			}
		}*/
	}
	
	public int payout(Hand p_hand){
		bust=p_hand.bust();
		if(insure &&!splitc&&!split){
			if(d_hand.blackjack){
				p.balance+=bet_value;
				System.out.println("Player wins insurance and his current balance is "+p.balance);
			}else{
				p.balance-=bet_value;
				System.out.println("Player loses insurance and his current balance is "+p.balance);
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
					p.balance+=1.5*bet_value;
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
				if(!d1.enddeal1){
					d1.input(s);
				}else{
					if(s.equals("s")){
						d1.enddeal=true;
						if(!splitc){
							shoe.middeal=false;
						}	
					}else{
						if(!s.equals("ad")&&!s.equals("$")){
							System.out.println(s+": illegal command");
						}
						if(s.equals("ad")){
							System.out.println("Basic strategy: s");
						}
					}
				}
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
					if(!d2.enddeal1){
						d2.input(s);
					}else{
						d2.enddeal=true;
						//shoe.middeal=false;
					}
					if(d2.enddeal){
						if(!d2.split){
							hands.add(d2.p_hand);
						}
						//po+=d2.po;
						if(!splitc){
							dealer_play();
							//System.out.println("Dealer's hand: "+d_hand+" ("+d_hand.value()+")");
							/*if(d_hand.blackjack){
								//System.out.println("Blackjack!");
								po+=2*ins_value;
							}else{
								po-=ins_value;
							}*/
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
							shoe.middeal=false;
						}
						enddeal=true;
						//shoe.middeal=false;
					}
				}
			}
		}else{
			if(s.equals("h")){
				if(!splitace){
					hit();
					System.out.println("Player's hand: "+p_hand+" ("+p_hand.value()+")");
					if(bust){
						System.out.println("Player busts");
						if(!splitc){
							dealer_play();
							po=payout(p_hand);
							shoe.middeal=false;
						}
						Player.bets++;
						enddeal=true;
						
					}
				}else{
					System.out.println("h: illegal command");
				}
			}
			if(s.equals("s")){
				if(!splitc){
					dealer_play();
					po=payout(p_hand);
					shoe.middeal=false;
				}
				Player.bets++;
				enddeal=true;
				
			}
			if(s.equals("2")){
				if(!insure && !splitace && p_hand.cards.size()==2 && p_hand.value()>=9 && p_hand.value()<=11){
					doubleDown();
					System.out.println("Player's hand: "+p_hand+" ("+p_hand.value()+")");
					if(bust){
						System.out.println("Player busts");
					}
					if(!splitc){
						dealer_play();
						po=payout(p_hand);
						shoe.middeal=false;
					}
					p_hand.doublesplit=true;
					Player.bets++;
					enddeal=true;
					
				}else{
					System.out.println("2: illegal command");	
				}
			}
			if(s.equals("u")){
				if(!insure && !splitc&&p_hand.cards.size()==2){
					p.balance-=0.5*bet_value;
					System.out.println("Player surrenders and his current balance is "+p.balance);
					Player.bets++;
					Player.losses++;
					enddeal=true;
					shoe.middeal=false;
				}else{
					System.out.println("u: illegal command");	
				}
			}
			if(s.equals("i")){
				if(!splitc&&p_hand.cards.size()==2 && d_hand.cards.peekFirst().val==11){
					//ins_value=0.5*bet_value;
					System.out.println("Player takes insurance");
					insure=true;
					/*if(d_hand.cards.peekLast().val==10){
						System.out.println("Dealer's hand: "+d_hand+" ("+d_hand.value()+")");
						System.out.println("Blackjack!");
						Player.dbj++;
						if(p_hand.blackjack){
							Player.pbj++;
							p.balance+=bet_value;
						}
						System.out.println("Player wins insurance bet and his current balance is "+p.balance);
						Player.wins++;
						Player.bets++;
						enddeal=true;
					}else{
						System.out.println("Dealer does not have a blackjack");
						p.balance-=0.5*bet_value;
					}*/
				}else{
					System.out.println("i: illegal command");	
				}
			}
			if(s.equals("p")){
				if(!insure && p_hand.cards.size()==2 && p_hand.cards.peekFirst().val==p_hand.cards.peekLast().val){
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
			if(s.equals("ad")){
				System.out.println("Basic strategy: " + basicStrategy());
				System.out.println("Hilo strategy: " + hilo());
				System.out.println("True count: " + shoe.true_count);
			}
		}
	}
	
	public String basicStrategy(){
		
		Iterator<Card> top=p_hand.cards.iterator();
		boolean ace=false;
		int nasum=0;
		while(top.hasNext()){
			Card a=top.next();
			if(a.val==11){
				if(!ace){
					ace=true;
				}else{
					nasum++;
				}
			}else{
				nasum+=a.val;
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
			if(p_hand.cards.peekFirst().val==6){
				if(d_hand.cards.peekFirst().val>=3 && d_hand.cards.peekFirst().val<=6){
					return "p";
				}else{
					return "h";
				}
			}
			if(p_hand.cards.peekFirst().val==7){
				if(d_hand.cards.peekFirst().val<=7){
					return "p";
				}else{
					return "h";
				}
			}
			if(p_hand.cards.peekFirst().val==8 || p_hand.cards.peekFirst().val==11){
				return "p";
			}
			if(p_hand.cards.peekFirst().val==9){
				if(d_hand.cards.peekFirst().val==7 || d_hand.cards.peekFirst().val>=10){
					return "s";
				}else{
					return "p";
				}
			}
			if(p_hand.cards.peekFirst().val==10){
				return "s";
			}
			
		}else{
			if(ace==true && nasum<=10){
				//soft
				
				if(p_hand.value()>=13 && p_hand.value()<=17){
					return "h";
				}
				if(p_hand.value()>=19 && p_hand.value()<=21){
					return "s";
				}
				if(p_hand.value()==18){
					if(d_hand.cards.peekFirst().val<=8){
						return "s";
					}else{
						return "h";
					}
				}
			}else{
				
				//hard
				if(p_hand.value()>=5 && p_hand.value()<=8){
					return "h";
				}
				if(p_hand.value()>=17 && p_hand.value()<=21){
					return "s";
				}
				if(p_hand.value()==9){
					if(d_hand.cards.peekFirst().val>=3 && d_hand.cards.peekFirst().val<=6 && p_hand.cards.size()==2){
						return "2";
					}else{
						return "h";
					}
				}
				if(p_hand.value()==10){
					if(d_hand.cards.peekFirst().val<=9 && p_hand.cards.size()==2){
						return "2";
					}else{
						return "h";
					}
				}
				if(p_hand.value()==11){
					if(d_hand.cards.peekFirst().val<=10 && p_hand.cards.size()==2){
						return "2";
					}else{
						return "h";
					}
				}
				if(p_hand.value()==12){
					if(d_hand.cards.peekFirst().val>=4 && d_hand.cards.peekFirst().val<=6){
						return "s";
					}else{
						return "h";
					}
				}
				if((p_hand.value()==13 || p_hand.value()==14)){
					if( d_hand.cards.peekFirst().val<=6){
						return "s";
					}else{
						return "h";
					}
				}
				if(p_hand.value()==15){
					if( d_hand.cards.peekFirst().val<=6){
						return "s";
					}else{
						if(d_hand.cards.peekFirst().val==10 && p_hand.cards.size()==2){
							return "u";
						}else{
							return "h";
						}
					}
				}
				if(p_hand.value()==16){
					if( d_hand.cards.peekFirst().val<=6){
						return "s";
					}else{
						if(d_hand.cards.peekFirst().val>=9 && p_hand.cards.size()==2){
							return "u";
						}else{
							return "h";
						}
					}
				}
			}
		}
		return "Invalid hand";
	}
	
	public String hilo(){
		if(p_hand.cards.size()==2){
			if(d_hand.cards.peekFirst().val==11){
				if(shoe.true_count>=3){
					return "i";
				}
			}
			if(p_hand.cards.peekFirst().val== p_hand.cards.peekLast().val && p_hand.value() == 20){			
				if(d_hand.cards.peekFirst().val == 5){
					if(shoe.true_count>=5){
						return "p";
					}
					return "s";				
				}
				if(d_hand.cards.peekFirst().val == 6){
					if(shoe.true_count>=4){
						return "p";
					}
				return "s";
				}			
			}	
		}else{
			if(p_hand.value()==9){
				if(d_hand.cards.peekFirst().val==2){
					if(shoe.true_count>=1){
						return "2";
					}
					return "h";
				}
				if(d_hand.cards.peekFirst().val==7){
					if(shoe.true_count>=3){
						return "2";
					}
					return "h";
				}	
			}
			if(p_hand.value()==10){
				if(d_hand.cards.peekFirst().val==10 || d_hand.cards.peekFirst().val==11){
					if(shoe.true_count>=4){
						return "2";
					}
					return "h";
				}
			}
			if(p_hand.value()==11){
				if(d_hand.cards.peekFirst().val==11){
					if(shoe.true_count>=1){
						return "2";
					}
					return "h";
				}	
			}
			if(p_hand.value()==12){
				if(d_hand.cards.peekFirst().val==2){
					if(shoe.true_count>=3){
						return "s";
					}
					return "h";
				}
				if(d_hand.cards.peekFirst().val==3){
					if(shoe.true_count>=2){
						return "s";
					}
					return "h";
				}
				if(d_hand.cards.peekFirst().val==4){
					if(shoe.true_count>=0){
						return "s";
					}
					return "h";
				}
				if(d_hand.cards.peekFirst().val==5){
					if(shoe.true_count>=-2){
						return "s";
					}
					return "h";
				}
				if(d_hand.cards.peekFirst().val==6){
					if(shoe.true_count>=-1){
						return "s";
					}
					return "h";
				}
			}
			if(p_hand.value()==13){
				if(d_hand.cards.peekFirst().val==2){
					if(shoe.true_count>=-1){
						return "s";
					}
					return "h";
				}
				if(d_hand.cards.peekFirst().val==3){
					if(shoe.true_count>=-2){
						return "s";
					}
					return "h";
				}
			}
			if(p_hand.value()==14){
				if(d_hand.cards.peekFirst().val==10){
					if(shoe.true_count>=3&&p_hand.cards.size()==2){
						return "u";
					}
					return basicStrategy();
				}		
			}
			if(p_hand.value()==15){
				if(d_hand.cards.peekFirst().val==9){
					if(shoe.true_count>=2&&p_hand.cards.size()==2){
						return "u";
					}
					return basicStrategy();
				}
				if(d_hand.cards.peekFirst().val==10){
					if(shoe.true_count>=0 && shoe.true_count<=3&&p_hand.cards.size()==2){
						return "u";
					}
					if(shoe.true_count>=4){
						return "s";
					}
					return "h";
				}
				if(d_hand.cards.peekFirst().val==11){
					if(shoe.true_count>=1&&p_hand.cards.size()==2){
						return "u";
					}
					return basicStrategy();
				}	
			}
			if(p_hand.value()==16){
				if(d_hand.cards.peekFirst().val==9){
					if(shoe.true_count>=5){
						return "s";
					}
					return "h";
				}
				if(d_hand.cards.peekFirst().val==10){
					if(d_hand.cards.peekFirst().val==9){
						if(shoe.true_count>=0){
							return "s";
						}
						return "h";
					}
				}
			}
		}
		return basicStrategy();
	}

}
