package cards;

public class Player {

	public float balance;
	public int min_bet;
	public int max_bet;
	static int bets=0, wins=0, losses=0, pushes=0, init_balance=0, pbj=0, dbj=0;
	float splitcount=0;
	public Player(int bal,int min, int max){
		this.balance=bal;
		this.min_bet=min;
		this.max_bet=max;
	}
	
	public float showBalance(){
		return balance;
	}
	
	int ad_bet=min_bet;
	public int ace_five(){
		if(Shoe.ace_five >=2){
			if(ad_bet*2>=max_bet){
				return max_bet;
			}
			ad_bet*=2;
		}else{
			ad_bet=min_bet;
		}
		
		return ad_bet;
	}
	
	public void stats(){
		System.out.println("BJ P/D   "+(float)pbj/bets+"/"+(float)dbj/bets);
		System.out.println("Win      "+(float)wins/bets);
		System.out.println("Lose     "+(float)losses/bets);
		System.out.println("Push     "+(float)pushes/bets);
		System.out.println("Balance  "+balance+"("+(((float)balance/init_balance)-1)*100+"%)");
	}
}
