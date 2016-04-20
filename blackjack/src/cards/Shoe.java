package cards;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class Shoe {

	private int num_decks;
	LinkedList<Card> cards=new LinkedList<Card>();
	private Iterator<Card> top;
	
	public void shuffle(){
		System.out.println("Shuffling...");
		Collections.shuffle(cards);
		top=cards.iterator();
	}
	
	public Shoe(int n){
		this.num_decks=n;
		for(int i=0;i<num_decks;i++){
			Deck novo=new Deck();
			cards.addAll(novo.cards);
		}
		shuffle();
		this.top=cards.iterator();
	}
	
	public Card getCard(){
		if(top.hasNext()){
			return top.next();
		}else{
			shuffle();
			return top.next();
		}
	}

	@Override
	public String toString() {
		return "Shoe [cards=" + cards + "]";
	}
}
