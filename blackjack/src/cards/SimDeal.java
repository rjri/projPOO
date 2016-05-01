package cards;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.StringTokenizer;

public class SimDeal{
	

	Shoe shoe;
	Player p;
	int next_bet;
	String next_cmd;
	
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
		while(p.balance>p.min_bet && nbit <25000){
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
			System.out.println("----------------------------------------");
			System.out.println("----------------------------------------");
			System.out.println("player is betting "+ next_bet);
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
		while(p.balance>=p.min_bet && nbit <25000){
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
			System.out.println("----------------------------------------");
			System.out.println("----------------------------------------");
			System.out.println("player is betting "+ next_bet);
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
	public void playDebug(String commands) throws IOException{
		Reader in = new FileReader(commands);
		BufferedReader br=new BufferedReader(in);
		commands=br.readLine();
		StringTokenizer st = new StringTokenizer(commands);
		String aux;
		next_bet=p.min_bet;
		String[] validinputs=new String[11];
		validinputs[0]="d";
		validinputs[1]="$";
		validinputs[2]="q";
		validinputs[3]="st";
		validinputs[4]="ad";
		validinputs[5]="h";
		validinputs[6]="s";
		validinputs[7]="i";
		validinputs[8]="u";
		validinputs[9]="p";
		validinputs[10]="2";
		Deal deal = null;
		while(st.hasMoreTokens()){
			next_cmd=st.nextToken();
			if(next_cmd.equals("b")){
				if(st.hasMoreTokens()){
					aux=st.nextToken();
					if(aux.equals("d")){
						System.out.println("Player is betting  " + next_bet);
						deal=new Deal(next_bet,shoe,p,false);
						deal.showDeal();
					}else{
						next_bet=Integer.parseInt(aux);
						System.out.println("Player is betting  " + next_bet);
					}
				}
			}
			for(int i=0;i<4;i++){
				if(validinputs[i].equals(next_cmd)){
					if(next_cmd.equals("d")){
						deal=new Deal(next_bet,shoe,p,false);
						deal.showDeal();
					}
					if(next_cmd.equals("q")){
						System.out.println("Bye");
						System.exit(0);
					}
					if(next_cmd.equals("$")){
						System.out.println("Player's current balance is "+p.showBalance());
					}
					if(next_cmd.equals("st")){
						p.stats();
					}
				}
			}	
			for(int i=4;i<10;i++){
				if(validinputs[i].equals(next_cmd)){
					if(deal!=null){
						deal.input(next_cmd);
					}else{
						System.out.println("Invalid commanddddd");
					}
				}
			}
		}
	}
}
