package cards;

import java.util.LinkedList;

public class Deck {

	LinkedList<Card> cards=new LinkedList<Card>();
	
	public Deck(){
		String[] naipe=new String[4];
		naipe[0]="Espadas";
		naipe[1]="Copas";
		naipe[2]="Paus";
		naipe[3]="Ouros";
		for(int n=0;n<4;n++){
			for(int i=1;i<=13;i++){
				if(i==1){
					Card carta=new Card("A",naipe[n]);
					cards.add(carta);
				}
				if(i>1 && i<11){
					Card carta=new Card(""+i,naipe[n]);
					cards.add(carta);
				}
				if(i==11){
					Card carta=new Card("J",naipe[n]);
					cards.add(carta);
				}
				if(i==12){
					Card carta=new Card("Q",naipe[n]);
					cards.add(carta);
				}
				if(i==13){
					Card carta=new Card("K",naipe[n]);
					cards.add(carta);
				}
			}
		}
	}

	@Override
	public String toString() {
		return "Deck [cards=" + cards + "]";
	}
}
