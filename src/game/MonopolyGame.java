package game;

import java.util.ArrayList;

import network.NetworkFa�ade;
import ui.UIFa�ade;

public class MonopolyGame {
	private ArrayList<Player> players;
	private Player currentPlayer;

	public MonopolyGame() {
		players = new ArrayList<>();
		currentPlayer = new Player();
		players.add(currentPlayer);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	public void executeMessage(String message) {
		String[] parsed = message.split("/");
		switch (parsed[0]) {
		case "UISCREEN":
			switch (parsed[1]) {
			case "ROLLDICE":
				currentPlayer.play(); break;
			case "ENDGAME": 
				NetworkFa�ade.getInstance().sendMessageToOthers("CLOSE");break;
			case "BUYPROPERTY":
				currentPlayer.buySquare();
			}
		case "UICREATOR":
			switch (parsed[1]) {
			case "PLAYERNAME":
				currentPlayer.setName(parsed[2]);
				break;
			case "PLAYERCOLOR":
				currentPlayer.setColor(parsed[2]);
				break;
			case "SERVER":
				NetworkFa�ade.getInstance().connect(Integer.parseInt(parsed[2]));
				UIFa�ade.getInstance().connectionDone();
				break;
			case "CLIENT":
				NetworkFa�ade.getInstance().connect(parsed[2]);
				if (!NetworkFa�ade.getInstance().isConnected()) {
					UIFa�ade.getInstance().connectionError();
					break;
				}
				UIFa�ade.getInstance().connectionDone();
				break;
			}
		}
	}
	
}
