package cards;

/**
 * Classe que extende Deal para o modo de simulacao.
 * 
 * @author Rui Guerra 75737
 * @author Joao Castanheira 77206
 * 
 */

public class DealSim extends Deal {

	public DealSim(int bet, Shoe sh, Player p) {
		super(bet, sh, p);
	}

	public DealSim(int bet, Shoe sh, Player p, Card pc) {
		super(bet, sh, p, pc);
	}
	
	public void dealer_play(){
		shoe.cardCounter(d_hand.cards.peekLast());
		if(!dealerdone){
			while(!d_bust && d_hand.value()<17){
				d_hand.hit(shoe.getCard(false));
				d_bust=d_hand.bust();
				dealerdone=true;
			}
		}
		
	}
	
	public int payout(Hand p_hand){
		bust=p_hand.bust();
		if(insure &&!splitc&&!split){
			if(d_hand.blackjack){
				p.balance+=bet_value;
			}else{
				p.balance-=bet_value;
			}
		}
		if(bust){
			if(!splitc&&!split){
				p.balance-=bet_value;
			}
			Player.losses++;
			return -bet_value;
		}
		if(p_hand.blackjack){
			if(d_hand.blackjack){
				Player.pbj++;
				Player.dbj++;
				Player.pushes++;
				return 0;
			}else{
				if(!splitc&&!split){
					p.balance+=1.5*bet_value;
					Player.pbj++;
				}
				Player.wins++;
				return bet_value;
			}
		}else{
			if(d_hand.blackjack){
				if(!splitc&&!split){
					p.balance-=bet_value;
				}
				Player.dbj++;
				Player.losses++;
				return -bet_value;
			}
			if(d_bust){
				if(!splitc&&!split){
					p.balance+=bet_value;
				}
				Player.wins++;
				return bet_value;
			}
			if(p_hand.value()>d_hand.value()){
				if(!splitc&&!split){
					p.balance+=bet_value;
				}
				Player.wins++;
				return bet_value;
			}else{
				if(p_hand.value()==d_hand.value()){
					Player.pushes++;
					return 0;
				}else{
					if(!splitc&&!split){
						p.balance-=bet_value;
					}
					Player.losses++;
					return -bet_value;
				}
			}
		}
	}
	
	public void split(String s){
		if(!d1.enddeal){
			if(!d1.enddeal1){
				d1.input(s);
			}else{
				if(s.equals("s")){
					d1.enddeal=true;	
				}
			}
			if(d1.enddeal){
				if(!d1.split){
					hands.add(d1.p_hand);
				}
			}				
		}else{
			if(!d2.enddeal){
				if(!d2.enddeal1){
					d2.input(s);
				}else{
					d2.enddeal=true;
				}
				if(d2.enddeal){
					if(!d2.split){
						hands.add(d2.p_hand);
					}
					if(!splitc){
						dealer_play();
						toph=hands.iterator();
						while(toph.hasNext()){
							Hand test=toph.next();
							int p1=payout(test);
							if(test.doublesplit){
								p1*=2;
							}
							po+=p1;
						}
						p.balance+=po;
						shoe.middeal=false;
					}
					enddeal=true;
				}
			}
		}
	}
	
	public void input(String s){
		if(split){
			split(s);
		}else{
			if(s.equals("h")){
				if(!splitace){
					hit();
					if(bust){
						if(!splitc){
							if(d_hand.blackjack){
								Player.dbj++;
							}
							shoe.cardCounter(d_hand.cards.peekLast());
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
				if(avbets>0 && !insure && !splitace && p_hand.cards.size()==2 && p_hand.value()>=9 && p_hand.value()<=11){
					doubleDown();
					
					if(!splitc){
						dealer_play();
						po=payout(p_hand);
						shoe.middeal=false;
					}
					p_hand.doublesplit=true;
					Player.bets++;
					avbets--;
					enddeal=true;
					
				}else{
					System.out.println("2: illegal command");	
				}
			}
			if(s.equals("u")){
				if(!insure && !splitc&&p_hand.cards.size()==2){
					p.balance-=0.5*bet_value;
					if(d_hand.blackjack){
						Player.dbj++;
					}
					Player.bets++;
					Player.losses++;
					enddeal=true;
					shoe.middeal=false;
				}else{
					System.out.println("u: illegal command");	
				}
			}
			if(s.equals("i")){
				if(avbets>0 && !splitc&&p_hand.cards.size()==2 && d_hand.cards.peekFirst().val==11){
					insure=true;
					avbets--;
				}else{
					System.out.println("i: illegal command");
				}
			}
			if(s.equals("p")){
				if(avbets>0 && !insure && p_hand.cards.size()==2 && p_hand.cards.peekFirst().val==p_hand.cards.peekLast().val && p.splitcount<=2){
					
					d1=new DealSim(bet_value,shoe,p,p_hand.cards.peekFirst());
					d2=new DealSim(bet_value,shoe,p,p_hand.cards.peekLast());
					split=true;
					avbets--;
				}else{
					System.out.println("p: illegal command");
				}
			}
		}
	}
	
}
