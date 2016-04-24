package cards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class Deal {

	int bet_value;
	private Shoe shoe;
	Hand p_hand;
	Hand d_hand;
	private boolean bust=false;
	private boolean d_bust=false;
	public boolean enddeal=false;
	public boolean moves=false;
	public boolean split=false;
	public Player p;
	
	public Deal(int bet, Shoe sh, Player p){
		this.bet_value=bet;
		this.shoe=sh;
		this.p_hand=new Hand(shoe.getCard(),shoe.getCard());
		this.d_hand=new Hand(shoe.getCard(),shoe.getCard());
		this.p=p;
	}
	public Deal(int bet, Shoe sh, Player p,Hand dealer,Card b){
		this.bet_value=bet;
		this.shoe=sh;
		this.d_hand=dealer;
		this.p_hand=new Hand(b,shoe.getCard());		
		this.p=p;
	}
	
	public void showDeal(){
		System.out.println("Dealer's hand: ["+d_hand.cards.peekFirst()+", X]");
		System.out.println("Player's hand: "+p_hand+" ("+p_hand.value()+")");
		if(p_hand.value()==21){
			System.out.println("Blackjack!");
			payout();
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
	
	public void input(String s) throws IOException{
		if(s.equals("h")){
			moves=true;
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
		if(s.equals("2") && !moves){
			if(p_hand.value()==9 || p_hand.value()==10 || p_hand.value()==11){
				bet_value=bet_value*2;
				hit();
				System.out.println("Player's hand: "+p_hand+" ("+p_hand.value()+")");
				dealer_play();
				payout();
				enddeal=true;
			}else{
				System.out.println("Invalid Command: Hand value must be 9,10 or 11");
			}
		}
		if(s.equals("u") && !moves){
			p.balance-=(bet_value/2);
			System.out.println("Player loses half his bet and his current balance is "+p.balance);
			enddeal=true;
		}
		if(s.equals("p") && !moves){ //not sure
			Card a=p_hand.cards.get(0);
			Card b=p_hand.cards.get(1);
			if(a.val == b.val){
				BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
				p_hand=new Hand(a,shoe.getCard());
				Deal split=new Deal(bet_value,shoe,p,d_hand,b);
				showDeal();
				split.showDeal();
				String s2=br.readLine();
				split.input(s2);
				//dealer must wait
			}else{
				System.out.println("Invalid command");
			}
		}
		/*if(s.equals("i") && !moves){
			Card a=d_hand.cards.get(0);
			if(a.val == 11){
				
			}
		}*/
	}
}
