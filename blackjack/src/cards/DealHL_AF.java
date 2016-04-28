package cards;

public class DealHL_AF extends DealHL {

	public DealHL_AF(int bet, Shoe sh, Player p) {
		super(bet, sh, p);
	}

	public DealHL_AF(int bet, Shoe sh, Player p, Card pc) {
		super(bet, sh, p, pc);
	}
	
	int ad_bet=p.min_bet;
	public int ace_five(){
		if(Shoe.ace_five >=2 && ad_bet*2<=p.max_bet){
			ad_bet*=2;
		}else{
			ad_bet=p.min_bet;
		}
		
		return ad_bet;
	}
}
