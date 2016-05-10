package cards;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Classe que define uma mao, do jogador ou do dealer.
 * 
 * @author Rui Guerra 75737
 * @author Joao Castanheira 77206
 * 
 */

public class Hand {

	LinkedList<Card> cards=new LinkedList<Card>();
	boolean blackjack=false;
	boolean doublesplit=false;
	
	protected Hand(Card a, Card b){
		if(a.val+b.val==21){
			blackjack=true;
		}
		cards.add(a);
		cards.add(b);
	}
	
	public void hit(Card a){
		cards.add(a);
	}
	
	public int value(){
		Iterator<Card> top=cards.iterator();
		int count=0;
		boolean ace=false;
		while(top.hasNext()){
			Card a=top.next();
			if(a.val!=11){
				count+=a.val;
			}else{
				if(!ace){
					ace=true;
				}else{
					count++;
				}
			}
		}
		if(ace){
			if(count<=10){
				count+=11;
			}else{
				count++;
			}
		}
		return count;
	}
	
	public boolean bust(){
		if(this.value()>21){
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "" + cards;
	}
}
