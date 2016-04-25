package cards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		Shoe shoe=new Shoe(2);
		System.out.println(shoe);
		/*for(int i=0;i<110;i++){
			System.out.println(shoe.getCard());
		}
		System.out.println(shoe);
		Hand hand=new Hand(shoe.getCard(),shoe.getCard());
		System.out.println(hand);
		System.out.println(hand.value());
		hand.hit(shoe.getCard());
		System.out.println(hand);
		System.out.println(hand.value());
		System.out.println("--");*/
		Player p =new Player(10000,10,100);
		int bet_amount=p.min_bet;
		boolean bet_done=false;
		String[] validinputs=new String[11];
		validinputs[0]="d";
		validinputs[1]="$";
		validinputs[2]="q";
		validinputs[3]="st";
		validinputs[4]="h";
		validinputs[5]="s";
		validinputs[6]="i";
		validinputs[7]="u";
		validinputs[8]="p";
		validinputs[9]="2";
		validinputs[10]="ad";
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		while(true){
			String s=br.readLine();
			boolean valid=false;
			try{
				if(s.charAt(0)=='b'){
					if(s.length()>2){
						String s1=s.substring(2);
						try{
							int am=Integer.parseInt(s1);
							if(am>=p.min_bet&&am<=p.max_bet){
								bet_amount=am;
							}else{
								System.out.println("Invalid bet amount");
							}
						}catch(NumberFormatException e){
							System.out.println("Invalid input");
						}
					}
					System.out.println("Player bet "+bet_amount);
					bet_done=true;
					valid=true;
				}
			}catch(StringIndexOutOfBoundsException e){
				System.out.println("Invalid input");
				valid=true;
			}
			for(int i=0;i<4;i++){
				if(validinputs[i].equals(s)){
					valid=true;
				}
			}
			if(valid){
				if(s.equals("d")){
					if(bet_done){
						Deal deal=new Deal(bet_amount,shoe,p);
						deal.showDeal();
						while(!deal.enddeal){
							s=br.readLine();
							valid=false;
							for(int i=1;i<11;i++){
								if(validinputs[i].equals(s)){
									valid=true;
								}
							}
							if(valid){
								if(s.equals("q")){
									System.exit(0);
								}
								if(s.equals("$")){
									System.out.println("Player's balance: "+p.showBalance());
								}
								deal.input(s);
							}else{
								System.out.println("Invalid input");
							}
						}
						bet_done=false;
					}else{
						System.out.println("Please place your bet");
					}
				}
				if(s.equals("q")){
					System.exit(0);
				}
				if(s.equals("$")){
					System.out.println("Player's balance: "+p.showBalance());
				}
			}else{
				System.out.println("Invalid input");
			}
		}
		//deal.dealer_play();
		//System.out.println(deal.payout());
	}

}
