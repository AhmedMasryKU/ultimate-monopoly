package game.square;

import game.Player;

public class UtilitySquare extends Square {
	private int rent;
	private int price;
	private Player owner;
	
	public UtilitySquare(String name, int number, int rent, int price, Player owner) {
		super(name, number);
		this.rent = rent;
		this.price = price;
		this.owner = owner;
	}

	//The rent system is prone to change
	public int getRent() {
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}

	public int getPrice() {
		return price;
	}

	public Player getOwner() {
		return owner;
	}
	
	@Override
	public void executeAction() {
		// TODO Auto-generated method stub
		if(owner==null) {
			// The player can buy  the Square; 
		} else {
			// The player has to pay Rent; 
		}
	}
}