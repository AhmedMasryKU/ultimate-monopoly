package game.square.action;

import game.square.Square;
import game.square.SquareType;

public abstract class Action extends Square{
	private static final long serialVersionUID = 1L;
	
	private static final SquareType type = SquareType.ACTION;

	@Override
	public SquareType getType() {
		return type;
	}
	
	@Override
	public boolean isOwned() {
		return true;
	}

}
