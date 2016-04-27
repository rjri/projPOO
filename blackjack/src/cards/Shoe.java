package cards;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class Shoe {

	private int num_decks;
	LinkedList<Card> cards=new LinkedList<Card>();
	private Iterator<Card> top;
	int num_reset;
	boolean reset=false;
	double run_count=0;
	double true_count=0;
	int num_cards=0;
	int cards_used=0;
	double num_decks2;
	int ace_five=0;
	
	public void shuffle(){
		System.out.println("Shuffling...");
		Collections.shuffle(cards);
		top=cards.iterator();
		reset=true;
	}
	
	public Shoe(int n, int shuff){
		this.num_decks=n;
		this.num_decks2=n;
		this.num_reset=(int) Math.round((n*52*shuff)/100);
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
		reset=false;
		this.top=cards.iterator();
	}
	
	public Card getCard(){
		if(top.hasNext()&&num_reset>cards_used){
			Card a=top.next();
			cardCounter(a);
			return a;
		}else{
			shuffle();
			Card a=top.next();
			cardCounter(a);
			return a;
		}
	}
	
	public void cardCounter(Card a){
		if(reset){
			num_cards=0;
			cards_used=0;
			num_decks2=num_decks;
			run_count=0;
			ace_five=0;
			reset=false;
		}
		
		if(a.val ==5){
			ace_five++;
		}
		if(a.val==11){
			ace_five--;
		}
		cards_used++;
		num_cards++;
		if(num_cards==52){
			num_decks2--;
			num_cards=0;
		}
		if(a.val >=2 && a.val<=6){
			run_count++;
		}
		if(a.val ==10 || a.val==11){
			run_count--;
		}
		true_count=(run_count/num_decks2);
		//System.out.println("true count: " +true_count + "run count:" +run_count +" "+num_decks2+"");
	}
	@Override
	public String toString() {
		return "Shoe [cards=" + cards + "]";
	}
}
