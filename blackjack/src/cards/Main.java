package cards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		if(args.length<1){
			System.out.println("Usage: [-i|-d|-s] (args)");
			System.exit(1);
		}
		int min_bet=10;
		int max_bet=100;
		int balance=1000;
		int shoesize=4;
		int shuffle=50;
		if(args[0].equals("-i")){
			try{
				min_bet=Integer.parseInt(args[1]);
				if(min_bet<1){
					System.out.println("min_bet must be greater than 0");
					System.exit(1);
				}
				max_bet=Integer.parseInt(args[2]);
				if(max_bet<10*min_bet || max_bet>20*min_bet){
					System.out.println("max_bet must be between 10 and 20 times greater than min_bet");
					System.exit(1);
				}
				balance=Integer.parseInt(args[3]);
				if(balance<50*min_bet){
					System.out.println("balance must be at least 50 times greater than min_bet");
					System.exit(1);
				}
				shoesize=Integer.parseInt(args[4]);
				if(shoesize<4 || shoesize>8){
					System.out.println("shoesize must be an integer between 4 and 8");
					System.exit(1);
				}
				shuffle=Integer.parseInt(args[5]);
				if(shuffle<10 || shuffle>100){
					System.out.println("shuffle must be an integer between 10 and 100");
					System.exit(1);
				}
			}catch(ArrayIndexOutOfBoundsException | NumberFormatException e){
				System.out.println("Usage: -i min_bet max_bet balance shoesize shuffle");
				System.exit(1);
			}
		}else{
			System.out.println("Usage: [-i|-d|-s] (args)");
			System.exit(1);
		}
		Shoe shoe=new Shoe(shoesize,shuffle);
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
		Player p =new Player(balance,min_bet,max_bet);
		Player.init_balance=balance;
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
							if(am>=p.min_bet&&am<=p.max_bet&&am<=p.balance){
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
								if(s.equals("st")){
									p.stats();
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
				if(s.equals("st")){
					p.stats();
				}
			}else{
				System.out.println("Invalid input");
			}
		}
		//deal.dealer_play();
		//System.out.println(deal.payout());
	}

}
