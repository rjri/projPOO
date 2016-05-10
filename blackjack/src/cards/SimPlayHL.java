package cards;

/**
 * Classe que implementa a interface SimPlay para o modo de simulacao
 * usando a estrategia hi-lo.
 * 
 * @author Rui Guerra 75737
 * @author Joao Castanheira 77206
 * 
 */

public class SimPlayHL implements SimPlay {

	Shoe shoe;
	Player p;
	int next_bet;
	boolean acefive;
	
	public SimPlayHL(boolean acefive,Shoe sh,Player p) {
		this.shoe=sh;
		this.p=p;
		this.acefive=acefive;
	}

	@Override
	public void simulation(){
		int win=0;
		int loss=0;
		while(p.balance>=p.min_bet && shoe.shuffcnt<Shoe.nbshuffles){
			if(acefive){
				next_bet=p.ace_five();
			}else{
				if(Player.wins>win){
					next_bet=next_bet+p.min_bet;
					if(next_bet>p.max_bet){
						next_bet=p.max_bet;
					}
					win=Player.wins;
				}
				if(Player.losses>loss){
					loss=Player.losses;
					next_bet=next_bet-p.min_bet;
					if(next_bet<p.min_bet){
						next_bet=p.min_bet;
					}
				}
			}
			if(next_bet>p.balance){
				next_bet=(int)p.balance;
			}
			Deal.avbets=(int) Math.floor((p.balance-next_bet)/next_bet);
			p.splitcount=0;
			Deal deal=new DealSim(next_bet,shoe,p);
			while(!deal.enddeal){
				deal.input(deal.hilo());
				simulationSplit(deal);
			}
		}	
		p.stats();
	}
	
	@Override
	public void simulationSplit(Deal deal){
		if(deal.split){
			while(!deal.d1.enddeal){
				deal.input(deal.d1.hilo());
				if(deal.d1.split){
					while(!deal.d1.d1.enddeal){
						deal.input(deal.d1.d1.hilo());
						if(deal.d1.d1.split){
							while(!deal.d1.d1.d1.enddeal){
								deal.input(deal.d1.d1.d1.hilo());
							}
							while(!deal.d1.d1.d2.enddeal){
								deal.input(deal.d1.d1.d2.hilo());
							}
						}
					}
					while(!deal.d1.d2.enddeal){
						deal.input(deal.d1.d2.hilo());
						if(deal.d1.d2.split){
							while(!deal.d1.d2.d1.enddeal){
								deal.input(deal.d1.d2.d1.hilo());
							}
							while(!deal.d1.d2.d2.enddeal){
								deal.input(deal.d1.d2.d2.hilo());
							}
						}
					}
				}
			}
			while(!deal.d2.enddeal){
				deal.input(deal.d2.hilo());
				if(deal.d2.split){
					while(!deal.d2.d1.enddeal){
						deal.input(deal.d2.d1.hilo());
						if(deal.d2.d1.split){
							while(!deal.d2.d1.d1.enddeal){
								deal.input(deal.d2.d1.d1.hilo());
							}
							while(!deal.d2.d1.d2.enddeal){
								deal.input(deal.d2.d1.d2.hilo());
							}
						}
					}
					while(!deal.d2.d2.enddeal){
						deal.input(deal.d2.d2.hilo());
						if(deal.d2.d2.split){
							while(!deal.d2.d2.d1.enddeal){
								deal.input(deal.d2.d2.d1.hilo());
							}
							while(!deal.d2.d2.d2.enddeal){
								deal.input(deal.d2.d2.d2.hilo());
							}
						}
					}
				}
			}
		}
	}

}
