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
		for(int j=0;j<num_decks;j++){
			//Deck novo=new Deck();
			String[] naipe=new String[4];
			naipe[0]="S";
			naipe[1]="H";
			naipe[2]="C";
			naipe[3]="D";
			for(int k=0;k<4;k++){
				for(int i=1;i<=13;i++){
					if(i==1){
						Card carta=new Card("A",naipe[k]);
						cards.add(carta);
					}
					if(i>1 && i<11){
						Card carta=new Card(""+i,naipe[k]);
						cards.add(carta);
					}
					if(i==11){
						Card carta=new Card("J",naipe[k]);
						cards.add(carta);
					}
					if(i==12){
						Card carta=new Card("Q",naipe[k]);
						cards.add(carta);
					}
					if(i==13){
						Card carta=new Card("K",naipe[k]);
						cards.add(carta);
					}
				}
			}
			//cards.addAll(novo.cards);
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
