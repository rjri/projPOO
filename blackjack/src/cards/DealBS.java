package cards;

import java.util.Iterator;

public class DealBS extends Deal {

	public DealBS(int bet, Shoe sh, Player p) {
		super(bet, sh, p);
	}

	public DealBS(int bet, Shoe sh, Player p, Card pc) {
		super(bet, sh, p, pc);
	}

	public String strategy(){
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
