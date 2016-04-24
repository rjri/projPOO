package cards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		Shoe shoe=new Shoe(2);
		System.out.println(shoe);
		for(int i=0;i<110;i++){
			System.out.println(shoe.getCard());
		}
		System.out.println(shoe);
		Hand hand=new Hand(shoe.getCard(),shoe.getCard());
		System.out.println(hand);
		System.out.println(hand.value());
		hand.hit(shoe.getCard());
		System.out.println(hand);
		System.out.println(hand.value());
		System.out.println("--");
		Player p =new Player(10000);
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		while(true){
			Deal deal=new Deal(10,shoe,p);
			deal.showDeal();
			while(!deal.enddeal){
				String s=br.readLine();
				if(s.equals("q")){
					System.exit(0);
				}
				deal.input(s);
			}	
		}
		//deal.dealer_play();
		//System.out.println(deal.payout());
	}

}
