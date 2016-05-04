package cards;

public interface SimPlay {
	
	/*public SimDeal(Shoe sh,Player p){
		this.shoe=sh;
		this.p=p;
		this.next_bet=p.min_bet;
	}
	public SimPlay(String sim_mode){		
		if(sim_mode.equals("BS")){
			simulation(false, false);
		}
		if(sim_mode.equals("HL")){
			simulation(false, true);
		}
		if(sim_mode.equals("BS-AF")){
			simulation(true, false);
		}
		if(sim_mode.equals("HL-AF")){
			simulation(true, true);
		}
	}*/
	
	public abstract void simulation();
	
	public abstract void simulationSplit(Deal deal);
	
}
