package cards;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.StringTokenizer;

/**
 * Classe que implementa o modo de debug.
 * 
 * @author Rui Guerra 75737
 * @author Joao Castanheira 77206
 * 
 */

public class DebugPlay {

	Shoe shoe;
	Player p;
	int next_bet;
	String next_cmd;
	String commands;
	
	public DebugPlay(String commands, Shoe sh, Player p){
		this.shoe=sh;
		this.p=p;
		this.commands=commands;
	}
	
	public void playDebug() throws IOException{
		try{
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
							System.out.println("\n-cmd b");
							System.out.println("Player is betting  " + next_bet);
							System.out.println("\n-cmd d");
							deal=new Deal(next_bet,shoe,p);
							deal.showDeal();
						}else{
							next_bet=Integer.parseInt(aux);
							System.out.println("\n-cmd b "+next_bet);
							System.out.println("Player is betting  " + next_bet);
						}
					}
				}
				for(int i=0;i<4;i++){
					if(validinputs[i].equals(next_cmd)){
						if(next_cmd.equals("d")){
							System.out.println("\n-cmd d");
							deal=new Deal(next_bet,shoe,p);
							deal.showDeal();
						}
						if(next_cmd.equals("q")){
							System.out.println("\n-cmd q");
							System.out.println("Bye");
							System.exit(0);
						}
						if(next_cmd.equals("$")){
							System.out.println("\n-cmd $");
							System.out.println("Player's current balance is "+p.showBalance());
						}
						if(next_cmd.equals("st")){
							System.out.println("\n-cmd st");
							p.stats();
						}
					}
				}	
				for(int i=4;i<10;i++){
					if(validinputs[i].equals(next_cmd)){
						System.out.println("\n-cmd "+next_cmd);
						if(deal!=null){
							deal.input(next_cmd);
						}else{
							System.out.println("Invalid command");
						}
					}
				}
			}
			br.close();
		}catch(Exception e){
			System.out.println("Error opening cmd-file");
			System.exit(1);
		}
	}
	
}
