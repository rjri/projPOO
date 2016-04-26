package cards;

public class DealHL extends Deal {

	public DealHL(int bet, Shoe sh, Player p) {
		super(bet, sh, p);
		// TODO Auto-generated constructor stub
	}

	public DealHL(int bet, Shoe sh, Player p, Card pc) {
		super(bet, sh, p, pc);
		// TODO Auto-generated constructor stub
	}
	
	public String hilo(){
		if(p_hand.cards.size()==2){
			if(d_hand.cards.peekFirst().val==11){
				if(shoe.true_count>=3){
					return "i";
				}
			}
			if(p_hand.cards.peekFirst().val== p_hand.cards.peekLast().val && p_hand.value() == 20){			
				if(d_hand.cards.peekFirst().val == 5){
					if(shoe.true_count>=5){
						return "p";
					}
					return "s";				
				}
				if(d_hand.cards.peekFirst().val == 6){
					if(shoe.true_count>=4){
						return "p";
					}
				return "s";
				}			
			}	
		}else{
			if(p_hand.value()==9){
				if(d_hand.cards.peekFirst().val==2){
					if(shoe.true_count>=1){
						return "2";
					}
					return "h";
				}
				if(d_hand.cards.peekFirst().val==7){
					if(shoe.true_count>=3){
						return "2";
					}
					return "h";
				}	
			}
			if(p_hand.value()==10){
				if(d_hand.cards.peekFirst().val==10 || d_hand.cards.peekFirst().val==11){
					if(shoe.true_count>=4){
						return "2";
					}
					return "h";
				}
			}
			if(p_hand.value()==11){
				if(d_hand.cards.peekFirst().val==11){
					if(shoe.true_count>=1){
						return "2";
					}
					return "h";
				}	
			}
			if(p_hand.value()==12){
				if(d_hand.cards.peekFirst().val==2){
					if(shoe.true_count>=3){
						return "s";
					}
					return "h";
				}
				if(d_hand.cards.peekFirst().val==3){
					if(shoe.true_count>=2){
						return "s";
					}
					return "h";
				}
				if(d_hand.cards.peekFirst().val==4){
					if(shoe.true_count>=0){
						return "s";
					}
					return "h";
				}
				if(d_hand.cards.peekFirst().val==5){
					if(shoe.true_count>=-2){
						return "s";
					}
					return "h";
				}
				if(d_hand.cards.peekFirst().val==6){
					if(shoe.true_count>=-1){
						return "s";
					}
					return "h";
				}
			}
			if(p_hand.value()==13){
				if(d_hand.cards.peekFirst().val==2){
					if(shoe.true_count>=-1){
						return "s";
					}
					return "h";
				}
				if(d_hand.cards.peekFirst().val==3){
					if(shoe.true_count>=-2){
						return "s";
					}
					return "h";
				}
			}
			if(p_hand.value()==14){
				if(d_hand.cards.peekFirst().val==10){
					if(shoe.true_count>=3){
						return "u";
					}
					return basicStrategy();
				}		
			}
			if(p_hand.value()==15){
				if(d_hand.cards.peekFirst().val==9){
					if(shoe.true_count>=2){
						return "u";
					}
					return basicStrategy();
				}
				if(d_hand.cards.peekFirst().val==10){
					if(shoe.true_count>=0 && shoe.true_count<=3){
						return "u";
					}
					if(shoe.true_count>=4){
						return "s";
					}
					return "h";
				}
				if(d_hand.cards.peekFirst().val==11){
					if(shoe.true_count>=1){
						return "u";
					}
					return basicStrategy();
				}	
			}
			if(p_hand.value()==16){
				if(d_hand.cards.peekFirst().val==9){
					if(shoe.true_count>=5){
						return "s";
					}
					return "h";
				}
				if(d_hand.cards.peekFirst().val==10){
					if(d_hand.cards.peekFirst().val==9){
						if(shoe.true_count>=0){
							return "s";
						}
						return "h";
					}
				}
			}
		}
		return basicStrategy();
	}
	
}
