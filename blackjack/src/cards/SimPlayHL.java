package cards;

public class SimPlayHL extends SimPlay {

	public SimPlayHL(boolean acefive,Shoe sh,Player p) {
		this.shoe=sh;
		this.p=p;
		this.acefive=acefive;
	}

	@Override
	public void simulation(){
		//int nbit=0;
		int win=0;
		int loss=0;
		while(p.balance>=p.min_bet && /*nbit <25000*/ shoe.shuffcnt<shoe.nbshuffles){
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
			System.out.println("----------------------------------------");
			System.out.println("----------------------------------------");
			System.out.println("Player is betting "+ next_bet);
			p.splitcount=0;
			Deal deal=new Deal(next_bet,shoe,p);
			deal.showDeal();
			while(!deal.enddeal){
				deal.input(deal.hilo());
				simulationSplit(deal);
			}
			//nbit++;
		}	
		p.stats();
		System.out.println(shoe.shuffcnt);
	}
	
	@Override
	public void simulationSplit(Deal deal){
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

}
