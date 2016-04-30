package cards;

public class SimDeal{
	
	Shoe shoe;
	Player p;
	int next_bet;
	public SimDeal(Shoe sh,Player p){
		this.shoe=sh;
		this.p=p;
		this.next_bet=p.min_bet;
	}
	public void playSim(String sim_mode){
		
		if(sim_mode.equals("BS")){
			simulationBS(false);
		}
		if(sim_mode.equals("HL")){
			simulationHL(false);
		}
		if(sim_mode.equals("BS-AF")){
			simulationBS(true);
		}
		if(sim_mode.equals("HL-AF")){
			simulationHL(true);
		}
	}
	
	public void simulationBS(boolean acefive){
		int nbit=0;
		int win=0;
		int loss=0;
		while(p.balance>p.min_bet && nbit <50000){
			if(acefive){
				next_bet=p.ace_five();
			}else{
				if(Player.wins>win){
					next_bet=next_bet+p.min_bet;
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
			System.out.println("bet:"+ next_bet);
			p.splitcount=0;
			Deal deal=new Deal(next_bet,shoe,p,true);
			deal.showDeal();
			while(!deal.enddeal){
				deal.input(deal.basicStrategy());
				simulationSplitBS(deal);
			}
			nbit++;
		}	
		p.stats();
		System.out.println(nbit);
	}
	public void simulationHL(boolean acefive){
		int nbit=0;
		int win=0;
		int loss=0;
		while(p.balance>=p.min_bet && nbit <50000){
			if(acefive){
				next_bet=p.ace_five();
			}else{
				if(Player.wins>win){
					next_bet=next_bet+p.min_bet;
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
			System.out.println("bet:"+ next_bet);
			p.splitcount=0;
			Deal deal=new Deal(next_bet,shoe,p,true);
			deal.showDeal();
			while(!deal.enddeal){
				deal.input(deal.hilo());
				simulationSplitHL(deal);
			}
			nbit++;
		}	
		p.stats();
		System.out.println(nbit);
	}
	
	public void simulationSplitHL(Deal deal){
		if(deal.split){//first split
			while(!deal.d1.enddeal){
				deal.input(deal.d1.hilo());
				if(deal.d1.split){//Second split
					while(!deal.d1.d1.enddeal){
						deal.input(deal.d1.d1.hilo());
						if(deal.d1.d1.split){//third split
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
						if(deal.d1.d2.split){//third split
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
				if(deal.d2.split){//Second split
					while(!deal.d2.d1.enddeal){
						deal.input(deal.d2.d1.hilo());
						if(deal.d2.d1.split){//third split
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
						if(deal.d2.d2.split){//third split
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
	public void simulationSplitBS(Deal deal){
		if(deal.split){//first split
			while(!deal.d1.enddeal){
				deal.input(deal.d1.basicStrategy());
				if(deal.d1.split){//Second split
					while(!deal.d1.d1.enddeal){
						deal.input(deal.d1.d1.basicStrategy());
						if(deal.d1.d1.split){//third split
							while(!deal.d1.d1.d1.enddeal){
								deal.input(deal.d1.d1.d1.basicStrategy());
							}
							while(!deal.d1.d1.d2.enddeal){
								deal.input(deal.d1.d1.d2.basicStrategy());
							}
						}
					}
					while(!deal.d1.d2.enddeal){
						deal.input(deal.d1.d2.basicStrategy());
						if(deal.d1.d2.split){//third split
							while(!deal.d1.d2.d1.enddeal){
								deal.input(deal.d1.d2.d1.basicStrategy());
							}
							while(!deal.d1.d2.d2.enddeal){
								deal.input(deal.d1.d2.d2.basicStrategy());
							}
						}
					}
				}
			}
			while(!deal.d2.enddeal){
				deal.input(deal.d2.basicStrategy());
				if(deal.d2.split){//Second split
					while(!deal.d2.d1.enddeal){
						deal.input(deal.d2.d1.basicStrategy());
						if(deal.d2.d1.split){//third split
							while(!deal.d2.d1.d1.enddeal){
								deal.input(deal.d2.d1.d1.basicStrategy());
							}
							while(!deal.d2.d1.d2.enddeal){
								deal.input(deal.d2.d1.d2.basicStrategy());
							}
						}
					}
					while(!deal.d2.d2.enddeal){
						deal.input(deal.d2.d2.basicStrategy());
						if(deal.d2.d2.split){//third split
							while(!deal.d2.d2.d1.enddeal){
								deal.input(deal.d2.d2.d1.basicStrategy());
							}
							while(!deal.d2.d2.d2.enddeal){
								deal.input(deal.d2.d2.d2.basicStrategy());
							}
						}
					}
				}
			}
		}
	}
}
