package cards;

public class Player {

	public int balance;
	public int min_bet;
	public int max_bet;
	
	public Player(int bal,int min, int max){
		this.balance=bal;
		this.min_bet=min;
		this.max_bet=max;
	}
	
	public int showBalance(){
		return balance;
	}
}
