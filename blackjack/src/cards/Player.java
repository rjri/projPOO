package cards;

public class Player {

	public int balance;
	public int min_bet;
	public int max_bet;
	static int bets=0, wins=0, losses=0, pushes=0, init_balance=0, pbj=0, dbj=0;
	
	public Player(int bal,int min, int max){
		this.balance=bal;
		this.min_bet=min;
		this.max_bet=max;
	}
	
	public int showBalance(){
		return balance;
	}
	
	public void stats(){
		System.out.println("BJ P/D   "+(float)pbj/bets+"/"+(float)dbj/bets);
		System.out.println("Win      "+(float)wins/bets);
		System.out.println("Lose     "+(float)losses/bets);
		System.out.println("Push     "+(float)pushes/bets);
		System.out.println("Balance  "+balance+"("+(((float)balance/init_balance)-1)*100+"%)");
	}
}
