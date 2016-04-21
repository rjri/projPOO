package cards;

import java.util.Iterator;
import java.util.LinkedList;

public class Hand {

	LinkedList<Card> cards=new LinkedList<Card>();
	
	protected Hand(Card a, Card b){
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

	@Override
	public String toString() {
		return "Hand [cards=" + cards + "]";
	}
}
