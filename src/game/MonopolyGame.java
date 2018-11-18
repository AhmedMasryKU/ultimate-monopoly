package game;

import java.util.ArrayList;

import game.card.ActionCards;
import game.card.Card;
import network.NetworkFa�ade;
import ui.UILinker;

public class MonopolyGame implements Runnable {
	private volatile boolean start = false;
	private volatile ArrayList<Player> players;
	private volatile Player currentPlayer;
	private static ArrayList<GameListener> listeners;

	public MonopolyGame() {
		players = new ArrayList<>();
		currentPlayer = new Player();
		players.add(currentPlayer);
		listeners = new ArrayList<GameListener>();
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public void executeNetworkMessage(String[] parsed) {
		if (parsed[0].equals("SENDDICE")) {
			int[] dice = currentPlayer.rollDice();
			NetworkFa�ade.getInstance().sendMessageToOthers((dice[0] + dice[1]) + "");
			return;
		} else if (parsed[0].equals("SENDNAME")) {
			NetworkFa�ade.getInstance().sendMessageToOthers(players.get(0).getName());
			return;
		} else if (parsed[0].equals("SENDCOLOR")) {
			NetworkFa�ade.getInstance().sendMessageToOthers(players.get(0).getColor());
			return;
		} else if (parsed[0].equals("RECEIVENAME")) {
			if (!parsed[1].equals(players.get(0).getName())) {
				Player newPlayer = new Player();
				newPlayer.setName(parsed[1]);
				players.add(newPlayer);
			}
			return;
		} else if (parsed[0].equals("ALLDONE")) {
			UILinker.getInstance().connectionDone();
			return;
		}
		updateCurrentPlayer(parsed[0]);
		switch (parsed[1]) {
		case "MOVE":
			int[] diceRolls = new int[3];
			diceRolls[0] = toInt(parsed[2]);
			diceRolls[1] = toInt(parsed[3]);
			diceRolls[2] = toInt(parsed[4]);
			currentPlayer.move(diceRolls);
			break;
		case "BUYESTATE":
			currentPlayer.buySquare();
			break;
		case "CARD":
			Card card;
			if (toInt(parsed[2]) == 0)
				card = ActionCards.getInstance().getChanceCard();
			else
				card = ActionCards.getInstance().getCommunityChestCard();
			currentPlayer.pickCard(card);
			break;
		case "RECEIVECOLOR":
			currentPlayer.setColor(parsed[2]);
		}
	}

	private void updateCurrentPlayer(String name) {
		for (Player player : players) {
			if (player.getName().equals(name)) {
				currentPlayer = player;
				break;
			}
		}
	}

	public void executeMessage(String message) {
		String[] parsed = message.split("/");
		switch (parsed[0]) {
		case "UISCREEN":
			switch (parsed[1]) {
			case "START":
				for (Player player : players)
					player.createPiece();
				break;
			case "ROLLDICE":
				currentPlayer.play();
				break;
			case "ENDGAME":
				NetworkFa�ade.getInstance().sendMessageToOthers("CLOSE");
				break;
			case "BUYPROPERTY":
				currentPlayer.buySquare();
			case "ENDTURN":
				NetworkFa�ade.getInstance().sendMessageToOthers("ENDTURN");
				start = true;
				break;
			}
		case "UICREATOR":
			switch (parsed[1]) {
			case "PLAYERNAME":
				currentPlayer.setName(parsed[2]);
				break;
			case "PLAYERCOLOR":
				currentPlayer.setColor(parsed[2]);
				start = true;
				break;
			case "SERVER":
				NetworkFa�ade.getInstance().connect(Integer.parseInt(parsed[2]));
				break;
			case "CLIENT":
				NetworkFa�ade.getInstance().connect(parsed[2]);
				if (!NetworkFa�ade.getInstance().isConnected()) {
					UILinker.getInstance().connectionError();
					break;
				}
				break;
			}
		}
	}

	private int toInt(String string) {
		return Integer.parseInt(string);
	}

	public void addGamelistener(GameListener lis) {
		listeners.add(lis);
	}

	public static void publishGameEvent(String message) {
		for (GameListener l : listeners) {
			l.onGameEvent(message);
		}
	}

	@Override
	public void run() {
		while (true) {
			if (start) {
				String message = NetworkFa�ade.getInstance().receiveMessage();
				String[] parsed = message.split("/");
				while (!parsed[0].equals("PLAY")) {
					executeNetworkMessage(parsed);
					message = NetworkFa�ade.getInstance().receiveMessage();
					parsed = message.split("/");
				}
				currentPlayer = players.get(0);
				// Enable Buttons in UI
			}
		}
	}

}
