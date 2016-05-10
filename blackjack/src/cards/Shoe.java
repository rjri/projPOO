package cards;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * Classe que define a lista de cartas a ser jogadas.
 * 
 * @author Rui Guerra 75737
 * @author Joao Castanheira 77206
 * 
 */

public class Shoe {

	private float num_decks;
	LinkedList<Card> cards=new LinkedList<Card>();
	private Iterator<Card> top;
	int num_reset;
	boolean reset=false;
	double run_count=0;
	double true_count=0;
	float cards_used=0;
	float num_decks2;
	static int ace_five=0;
	boolean middeal=true;
	boolean debug=false;
	static int nbshuffles=-1;
	int shuffcnt=0;
	public void shuffle(){
		if(nbshuffles<0){
			System.out.println("Shuffling...");	
		}
		Collections.shuffle(cards);
		top=cards.iterator();
		cards_used=0;
		reset=true;
		shuffcnt++;
	}
	
	public Shoe(int n, int shuff){
		this.num_decks=n;
		this.num_decks2=n;
		this.num_reset=(int) Math.round((n*52*shuff)/100);
		for(int j=0;j<num_decks;j++){
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
		}
		shuffle();
		reset=false;
		this.top=cards.iterator();
	}

	public Shoe(String shoefile) throws IOException{
		try{
			Reader in = new FileReader(shoefile);
			BufferedReader br=new BufferedReader(in);
			shoefile=br.readLine();
			StringTokenizer st = new StringTokenizer(shoefile);
			String aux;
		    while (st.hasMoreTokens()) {
		    	aux=st.nextToken();
		    	if(Character.toString(aux.charAt(0)).equals("1")){
		    		 Card carta=new Card("10",Character.toString(aux.charAt(2)));
		    		 cards.add(carta);
		    	}else{
		    		Card carta=new Card(Character.toString(aux.charAt(0)),Character.toString(aux.charAt(1)));
		    		cards.add(carta);
		    	}	
		    }
		    float n=cards.size()/52;
		    this.num_decks=n;
			this.num_decks2=n;
		    debug=true;
			this.top=cards.iterator();
			br.close();
		}catch(Exception e){
			System.out.println("Error opening shoefile");
			System.exit(1);
		}
	}

	public Card getCard(boolean hole){
		if(!debug){
			if(num_reset<cards_used && !middeal){
				shuffle();
				Card a=top.next();
				if(!hole){
					cardCounter(a);
				}
				return a;	
			}
		}	
		middeal=true;
		if(top.hasNext()){
			Card a=top.next();
			if(!hole){
				cardCounter(a);
			}
			return a;
		}else{
			top=cards.iterator();
			Card a=top.next();
			if(!hole){
				cardCounter(a);
			}
			return a;
		}
	}
	
	public void cardCounter(Card a){
		if(reset){
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
		num_decks2=num_decks-(cards_used/52);
		
		if(a.val >=2 && a.val<=6){
			run_count++;
		}
		if(a.val ==10 || a.val==11){
			run_count--;
		}
		true_count=run_count/num_decks2;
	}
	
	@Override
	public String toString() {
		return "Shoe [cards=" + cards + "]";
	}
}
