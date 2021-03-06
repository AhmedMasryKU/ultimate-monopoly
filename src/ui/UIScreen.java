package ui;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import game.Controller;
import game.GameListener;

public class UIScreen extends JFrame implements GameListener {
	private static final long serialVersionUID = 1L;
	private static final String boardImagePath = "resources/board.png";
	private static final String deedImagePath = "resources/deeds/";
	private static final String cardImagePath = "resources/cards/";
	private static final String jailImagePath = "resources/jail.png";
	private static final String dieImagePath = "resources/dieSides/side";
	private static final String musicPath = "resources/music.wav";

	private Controller controller;
	private Animator animator;
	private PathFinder pathFinder;
	private ConcurrentHashMap<String, Piece> pieces;
	private ArrayList<String> deeds;
	private ArrayList<JButton> willBeActivetedButtons;
	private String willBeShowedDeed = "The Embarcadero";
	private boolean isRolled = false;
	private boolean active;
	private final Object[] jailOptions = { "Roll for Doubles", "Pay Bail" };
	private Piece myPiece;

	private String message;
	private JBoard board;
	private JDice dice;
	private JTextArea deedInformation;
	private JTextArea playerText;
	private JTextArea infoText;
	private JTextArea chatText;
	private JPanel playerArea;
	private JPanel pauseResumePanel;
	private JPanel jail;
	private JButton buyBuildingButton;
	private JButton sellBuildingButton;
	private JButton mortgageButton;
	private JButton unmortgageButton;
	private JButton buySquareButton;
	private JButton pauseResumeButton;
	private JButton rollDiceButton;
	private JButton endTurnButton;
	private JButton saveGameButton;
	private JButton endGameButton;
	private JButton chatButton;
	private JLabel deed;
	private JComboBox<String> deedComboBox;
	private JComboBox<String> playerComboBox;

	/// UI constants
	private int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	private int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

	private int screenX = (screenWidth - screenHeight) / 2;
	private int screenY = 0;

	private int controlPaneWidth = (screenWidth - screenHeight) / 2;
	private int controlPaneHeight = screenHeight;

	private int controlPaneXMargin = controlPaneWidth / 30;
	private int controlPaneYMargin = controlPaneHeight / 108;

	private int controlPaneComponentWidth = controlPaneWidth / 2;
	private int controlPaneComponentHeight = controlPaneHeight / 12;

	private Font font = new Font("Tahoma", Font.PLAIN, screenWidth / 80);

	private Image boardImage = new ImageIcon(boardImagePath).getImage().getScaledInstance(screenHeight, -1,
			Image.SCALE_SMOOTH);

	private double scaleFactor = ((double) screenHeight) / new ImageIcon(boardImagePath).getIconHeight();

	private static final int unscaledPieceSize = 80;
	private int pieceSize = (int) (scaleFactor * unscaledPieceSize);

	private static final int unscaledDieSize = 160;
	private int dieSize = (int) (scaleFactor * unscaledDieSize);

	/**
	 * Create the panel.
	 */
	public UIScreen() {
		controller = Controller.getInstance();
		pieces = new ConcurrentHashMap<String, Piece>();
		deeds = new ArrayList<String>();
		willBeActivetedButtons = new ArrayList<JButton>();
		animator = new Animator();
		pathFinder = new PathFinder(scaleFactor);

		setTitle("Ultimate Monopoly by Waterfall Haters!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setUndecorated(true);
		setLayout(null);

		dice = new JDice();
		board = new JBoard();
		board.setIcon(new ImageIcon(boardImage));
		board.setBounds(screenX, screenY, screenHeight, screenHeight);
		getContentPane().add(board);
		animator.addComponentToAnimate(board);
		new Thread(animator, "Animator").start();

		JPanel leftControlPanel = new JPanel();
		leftControlPanel.setBounds(0, 0, controlPaneWidth, controlPaneHeight);
		leftControlPanel.setLayout(null);

		deed = new JLabel();
		deed.setBounds(0, 0, controlPaneWidth, 6 * controlPaneComponentHeight);
		deed.setVerticalAlignment(JLabel.CENTER);
		leftControlPanel.add(deed);

		deedInformation = new JTextArea();
		deedInformation.setEditable(false);
		deedInformation.setFont(font);
		deedInformation.setBounds(controlPaneXMargin, controlPaneYMargin + 6 * controlPaneComponentHeight,
				2 * controlPaneComponentWidth - 2 * controlPaneXMargin,
				2 * controlPaneComponentHeight - 2 * controlPaneYMargin);
		leftControlPanel.add(deedInformation);

		deedComboBox = new JComboBox<String>();
		deedComboBox.setFont(font);
		deedComboBox.setBounds(controlPaneXMargin, controlPaneYMargin + 8 * controlPaneComponentHeight,
				2 * controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		leftControlPanel.add(deedComboBox);

		buyBuildingButton = new JButton("Buy Building");
		buyBuildingButton.setBounds(controlPaneXMargin, controlPaneYMargin + 9 * controlPaneComponentHeight,
				controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		buyBuildingButton.setFont(font);
		buyBuildingButton.setEnabled(false);
		leftControlPanel.add(buyBuildingButton);

		sellBuildingButton = new JButton("Sell Building");
		sellBuildingButton.setBounds(controlPaneXMargin + controlPaneComponentWidth,
				controlPaneYMargin + 9 * controlPaneComponentHeight, controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		sellBuildingButton.setFont(font);
		sellBuildingButton.setEnabled(false);
		leftControlPanel.add(sellBuildingButton);

		mortgageButton = new JButton("Mortgage");
		mortgageButton.setBounds(controlPaneXMargin, controlPaneYMargin + 10 * controlPaneComponentHeight,
				controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		mortgageButton.setFont(font);
		mortgageButton.setEnabled(false);
		leftControlPanel.add(mortgageButton);

		unmortgageButton = new JButton("Unmortgage");
		unmortgageButton.setBounds(controlPaneXMargin + controlPaneComponentWidth,
				controlPaneYMargin + 10 * controlPaneComponentHeight,
				controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		unmortgageButton.setFont(font);
		unmortgageButton.setEnabled(false);
		leftControlPanel.add(unmortgageButton);

		buySquareButton = new JButton("Buy Square");
		buySquareButton.setBounds(controlPaneXMargin, controlPaneYMargin + 11 * controlPaneComponentHeight,
				2 * controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		buySquareButton.setFont(font);
		buySquareButton.setEnabled(false);
		leftControlPanel.add(buySquareButton);

		getContentPane().add(leftControlPanel);

		JPanel rightPanel = new JPanel();
		rightPanel.setBounds(screenX + screenHeight, screenY, controlPaneWidth, controlPaneHeight);
		rightPanel.setLayout(null);

		chatButton = new JButton("Chat");
		chatButton.setBounds(controlPaneXMargin, controlPaneYMargin, controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		chatButton.setEnabled(true);
		chatButton.setFont(font);
		rightPanel.add(chatButton);

		endGameButton = new JButton("End Game");
		endGameButton.setBounds(controlPaneXMargin + controlPaneComponentWidth, controlPaneYMargin,
				controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		endGameButton.setFont(font);
		rightPanel.add(endGameButton);

		playerArea = new JPanel();
		playerArea.setLayout(null);
		playerArea.setOpaque(true);
		playerArea.setBounds(controlPaneXMargin, controlPaneYMargin + controlPaneComponentHeight,
				2 * controlPaneComponentWidth - 2 * controlPaneXMargin,
				4 * controlPaneComponentHeight - 2 * controlPaneYMargin);

		playerText = new JTextArea();
		playerText.setEditable(false);
		playerText.setFont(font);
		JScrollPane playerScroll = new JScrollPane(playerText);
		playerScroll.setBounds(controlPaneXMargin, 2 * controlPaneYMargin,
				2 * controlPaneComponentWidth - 4 * controlPaneXMargin,
				4 * controlPaneComponentHeight - 4 * controlPaneYMargin);
		playerArea.add(playerScroll);
		rightPanel.add(playerArea);

		playerComboBox = new JComboBox<String>();
		playerComboBox.setFont(font);
		playerComboBox.setBounds(controlPaneXMargin, controlPaneYMargin + 5 * controlPaneComponentHeight,
				2 * controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		rightPanel.add(playerComboBox);

		infoText = new JTextArea();
		infoText.setEditable(false);
		infoText.setText("Welcome to Utimate Monopoly\nby Waterfall Haters!");
		infoText.setFont(font);
		JScrollPane infoArea = new JScrollPane(infoText);
		infoArea.setBounds(controlPaneXMargin, controlPaneYMargin + 6 * controlPaneComponentHeight,
				2 * controlPaneComponentWidth - 2 * controlPaneXMargin,
				2 * controlPaneComponentHeight - 2 * controlPaneYMargin);
		rightPanel.add(infoArea);

		chatText = new JTextArea();
		chatText.setEditable(false);
		chatText.setText("To send a message, use chat button!");
		chatText.setFont(font);
		JScrollPane chatArea = new JScrollPane(chatText);
		chatArea.setBounds(controlPaneXMargin, controlPaneYMargin + 8 * controlPaneComponentHeight,
				2 * controlPaneComponentWidth - 2 * controlPaneXMargin,
				2 * controlPaneComponentHeight - 2 * controlPaneYMargin);
		rightPanel.add(chatArea);

		pauseResumeButton = new JButton("Pause");
		pauseResumeButton.setBounds(controlPaneXMargin, controlPaneYMargin + 10 * controlPaneComponentHeight,
				controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		pauseResumeButton.setFont(font);
		pauseResumeButton.setEnabled(false);
		rightPanel.add(pauseResumeButton);

		saveGameButton = new JButton("Save Game");
		saveGameButton.setBounds(controlPaneXMargin + controlPaneComponentWidth,
				controlPaneYMargin + 10 * controlPaneComponentHeight,
				controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		saveGameButton.setFont(font);
		saveGameButton.setEnabled(false);
		rightPanel.add(saveGameButton);

		rollDiceButton = new JButton("Roll Dice");
		rollDiceButton.setBounds(controlPaneXMargin, controlPaneYMargin + 11 * controlPaneComponentHeight,
				controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		rollDiceButton.setFont(font);
		rollDiceButton.setEnabled(false);
		rightPanel.add(rollDiceButton);

		endTurnButton = new JButton("End Turn");
		endTurnButton.setBounds(controlPaneXMargin + controlPaneComponentWidth,
				controlPaneYMargin + 11 * controlPaneComponentHeight,
				controlPaneComponentWidth - 2 * controlPaneXMargin,
				controlPaneComponentHeight - 2 * controlPaneYMargin);
		endTurnButton.setFont(font);
		endTurnButton.setEnabled(false);
		rightPanel.add(endTurnButton);

		getContentPane().add(rightPanel);

		pauseResumePanel = new JPanel();
		pauseResumePanel.setBounds(screenWidth / 3 - screenX, screenHeight / 4, screenWidth / 3, screenHeight / 2);
		pauseResumePanel.setBackground(Color.WHITE);
		pauseResumePanel.setLayout(new GridBagLayout());
		JLabel pauseResumeLabel = new JLabel("PAUSED");
		pauseResumeLabel.setFont(new Font(font.getFontName(), Font.PLAIN, font.getSize() * 2));
		pauseResumePanel.setVisible(false);
		pauseResumePanel.add(pauseResumeLabel);
		board.add(pauseResumePanel);

		jail = new JPanel();
		Image jailImage = new ImageIcon(jailImagePath).getImage().getScaledInstance(screenWidth / 3, -1,
				Image.SCALE_SMOOTH);
		jail.setLayout(new BorderLayout(0, 0));
		jail.add(new JLabel(new ImageIcon(jailImage)), BorderLayout.CENTER);
		JLabel jailLabel = new JLabel("You are in Jail!");
		jailLabel.setFont(new Font("Tahoma", Font.PLAIN, 36));
		jailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jail.add(jailLabel, BorderLayout.SOUTH);

		rollDiceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String s = JOptionPane.showInputDialog(null, "Enter dice total", "", JOptionPane.PLAIN_MESSAGE);
				message = "UISCREEN/ROLLDICE/" + s;
				controller.dispatchMessage(message);
				willBeActivetedButtons.add(endTurnButton);

				/*
				 * isRolled = true; rollDiceButton.setEnabled(false);
				 * willBeActivetedButtons.add(endTurnButton); message = "UISCREEN/ROLLDICE";
				 * controller.dispatchMessage(message);
				 */
			}
		});

		buySquareButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				buySquareButton.setEnabled(false);
				message = "UISCREEN/BUYPROPERTY";
				controller.dispatchMessage(message);
			}
		});

		buyBuildingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				message = "UISCREEN/BUYBUILDING";
				controller.dispatchMessage(message);
			}
		});

		sellBuildingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				message = "UISCREEN/SELLBUILDING";
				controller.dispatchMessage(message);
			}
		});

		mortgageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				message = "UISCREEN/MORTGAGE";
				Controller.getInstance().dispatchMessage(message);
			}
		});

		unmortgageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				message = "UISCREEN/UNMORTGAGE";
				Controller.getInstance().dispatchMessage(message);
			}
		});

		endTurnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				endTurnButton.setEnabled(false);
				active = false;
				disableButtons();
				message = "UISCREEN/ENDTURN";
				controller.dispatchMessage(message);
			}
		});

		pauseResumeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pauseResumeButton.getText().equals("Pause")) {
					disableButtons();
					pauseResumeButton.setEnabled(true);
					pauseResumeButton.setText("Resume");
					message = "UISCREEN/PAUSE";
					Controller.getInstance().dispatchMessage(message);
				} else {
					enableButtons();
					message = "UISCREEN/RESUME";
					Controller.getInstance().dispatchMessage(message);
					if (isRolled) {
						endTurnButton.setEnabled(true);
					} else
						rollDiceButton.setEnabled(true);
					pauseResumeButton.setText("Pause");
				}
			}
		});

		saveGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new FileNameExtensionFilter("Ultimate Monopoly Save Files", "umsf"));
				if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					String saveFile = chooser.getSelectedFile().getPath();
					message = "UISCREEN/SAVEGAME/" + saveFile;
					Controller.getInstance().dispatchMessage(message);
				}
			}
		});

		endGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				animator.destruct();
				controller.dispatchMessage("UISCREEN/ANIMATIONEND");
				controller.dispatchMessage("UISCREEN/ENDGAME");
				dispose();
			}
		});

		chatButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = JOptionPane.showInputDialog(null, "Enter your message", "Chat",
						JOptionPane.OK_CANCEL_OPTION);
				if (message != null)
					controller.dispatchMessage("UISCREEN/CHAT/" + message);
			}
		});

		deedComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = (String) deedComboBox.getSelectedItem();
				Image deedImage = new ImageIcon(deedImagePath + name + ".png").getImage()
						.getScaledInstance(controlPaneWidth, -1, Image.SCALE_SMOOTH);
				deed.setIcon(new ImageIcon(deedImage));
				String info = deeds.get(deedComboBox.getSelectedIndex());
				controller.dispatchMessage("UISCREEN/DEEDINFO/" + info);
			}
		});

		playerComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = (String) playerComboBox.getSelectedItem();
				controller.dispatchMessage("UISCREEN/PLAYERINFO/" + name);
			}
		});

	}

	private void disableButtons() {
		buyBuildingButton.setEnabled(false);
		sellBuildingButton.setEnabled(false);
		mortgageButton.setEnabled(false);
		unmortgageButton.setEnabled(false);
		buySquareButton.setEnabled(false);
		pauseResumeButton.setEnabled(false);
		rollDiceButton.setEnabled(false);
		endTurnButton.setEnabled(false);
		saveGameButton.setEnabled(false);
	}

	private void enableButtons() {
		buyBuildingButton.setEnabled(true);
		sellBuildingButton.setEnabled(true);
		mortgageButton.setEnabled(true);
		unmortgageButton.setEnabled(true);
		pauseResumeButton.setEnabled(true);
		saveGameButton.setEnabled(true);
	}

	@Override
	public void onGameEvent(String message) {
		String[] parsed = message.split("/");
		switch (parsed[0]) {
		case "START":
			setVisible(true);
			startMusic();
			break;
		case "ACTION":
			infoText.insert(parsed[1] + "\n", 0);
			break;
		case "COLOR":
			repaint();
			playerArea.setBackground(new Color(toInt(parsed[1]), true));
			break;
		case "MOVE":
			Piece piecem = pieces.get(parsed[1]);
			animator.startAnimator();
			pathFinder.addPath(piecem.path, toInt(parsed[2]), toInt(parsed[3]), toInt(parsed[4]), toInt(parsed[5]));
			if (active)
				myPiece = piecem;
			else
				piecem.isActive = true;
			board.repaint();
			break;
		case "JUMP":
			Piece piecej = pieces.get(parsed[1]);
			pathFinder.addPoint(piecej.path, toInt(parsed[2]), toInt(parsed[3]));
			piecej.lastPoint = piecej.path.nextPoint();
			piecej.isActive = true;
			board.repaint();
			break;
		case "PLAYERDATA":
			playerText.setText("");
			for (int i = 1; i < parsed.length; i++) {
				playerText.append(parsed[i] + "\n");
			}
			break;
		case "PLAY":
			active = true;
			isRolled = false;
			rollDiceButton.setEnabled(true);
			enableButtons();
			break;
		case "REMOVEPIECE":
			if (pieces.remove(parsed[1]).isActive)
				animator.stopAnimator();
			board.repaint();
			break;
		case "PAUSE":
			pauseResumePanel.setVisible(true);
			animator.stopAnimator();
			break;
		case "RESUME":
			pauseResumePanel.setVisible(false);
			animator.startAnimator();
			break;
		case "DOUBLE":
			if (active) {
				willBeActivetedButtons.add(rollDiceButton);
				willBeActivetedButtons.remove(endTurnButton);
			}
			break;
		case "JAIL":
			if (active) {
				rollDiceButton.setEnabled(false);
				endTurnButton.setEnabled(true);
			}
			break;
		case "PLAYERINFO":
			playerComboBox.setSelectedItem(parsed[1]);
			break;
		case "CHAT":
			chatText.insert(parsed[1] + "\n", 0);
			break;
		case "DICE":
			String infod = parsed[1] + " rolled:\n";
			infod += "Die 1: " + parsed[2] + "\n";
			infod += "Die 2: " + parsed[3] + "\n";
			infod += "Speed Die: ";
			if (toInt(parsed[4]) == 4) {
				infod += "Mr.Monopoly Bonus Move";
			} else if (toInt(parsed[4]) == 5) {
				infod += "Bus Icon";
			} else {
				infod += parsed[4];
			}
			infoText.insert(infod + "\n", 0);
			if (active) {
				dice.d1 = toInt(parsed[2]) - 1;
				dice.d2 = toInt(parsed[3]) - 1;
				dice.d3 = toInt(parsed[4]) - 1;
				dice.i = 1;
				animator.startAnimator();
			}
			break;
		case "BUY":
			buySquareButton.setEnabled(true);
			break;
		case "ESTATE":
			if (active)
				willBeActivetedButtons.add(buySquareButton);
			willBeShowedDeed = parsed[1];
			break;
		case "NOOWNER":
			deedInformation.setText("There is no owner of this square!");
			break;
		case "PROPERTY":
			String infop = "Owner: " + parsed[1] + "\n";
			infop += "This square has ";
			if (parsed[2].equals("NOBUILDING"))
				infop += "no buildings!";
			else {
				if (parsed[2].equals("0"))
					infop += parsed[3] + " houses!";
				else if (parsed[2].equals("1"))
					infop += "a hotel!";
				else if (parsed[2].equals("2"))
					infop += "a skyscraper";
			}
			deedInformation.setText(infop);
			break;
		case "TRANSIT":
			String infot = "Owner: " + parsed[1] + "\n";
			infot += parsed[1] + " has " + parsed[2] + " transit stations!";
			infot += "This station has " + parsed[2] + " train depots!";
			deedInformation.setText(infot);
			break;
		case "UTILITY":
			String infou = "Owner: " + parsed[1] + "\n";
			infou += parsed[1] + " has " + parsed[2] + " utility squares!";
			deedInformation.setText(infou);
			break;
		case "OUTOFJAIL":
			rollDiceButton.setEnabled(false);
			endTurnButton.setEnabled(true);
			break;
		case "OUTOFJAILPAY":
			if (parsed[1].equals("F")) {
				rollDiceButton.setEnabled(false);
				endTurnButton.setEnabled(true);
			} else {
				rollDiceButton.setEnabled(true);
				endTurnButton.setEnabled(false);
			}
			break;
		case "JAILACTION":
			if (active) {
				rollDiceButton.setEnabled(false);
				endTurnButton.setEnabled(true);
				int n = JOptionPane.showOptionDialog(null, jail, "", JOptionPane.YES_NO_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, jailOptions, jailOptions[0]);
				if (n == JOptionPane.YES_OPTION) {
					System.out.println("Hi2");
					Controller.getInstance().dispatchMessage("UISCREEN/JAIL2/DOUBLES");
				} else {
					// paid bail
					System.out.println("Hi");
					Controller.getInstance().dispatchMessage("UISCREEN/JAIL2/PAYBAIL");
				}
			}
			break;
		case "PLAYER":
			playerComboBox.addItem(parsed[1]);
			Piece piece = new Piece();
			piece.color = new Color(toInt(parsed[2]), true);
			pathFinder.addPoint(piece.path, toInt(parsed[3]), toInt(parsed[4]));
			piece.lastPoint = piece.path.nextPoint();
			board.repaint();
			pieces.put(parsed[1], piece);
			break;
		case "DEED":
			for (int i = 1; i < parsed.length; i += 3) {
				deeds.add(parsed[i + 1] + "/" + parsed[i + 2]);
				deedComboBox.addItem(parsed[i]);
			}
			break;
		case "CARD2":
			Image cardImage = new ImageIcon(cardImagePath + parsed[1] + ".png").getImage()
					.getScaledInstance(screenWidth / 3, -1, Image.SCALE_SMOOTH);
			JOptionPane.showMessageDialog(null, new ImageIcon(cardImage), "", JOptionPane.PLAIN_MESSAGE);
			break;
		case "SELLBUILDING":
			if (parsed[1].equals("NO")) {
				JOptionPane.showMessageDialog(null, parsed[2], "", JOptionPane.PLAIN_MESSAGE);
			} else {
				HashMap<Object, ArrayList<Object>> possibilities1 = new HashMap<>();
				//
				int i = 2;
				while (i < parsed.length) {
					ArrayList<Object> squares = new ArrayList<>();
					String groupName = parsed[i];
					i++;
					while (!parsed[i].equals("END")) {
						squares.add(parsed[i]);
						i++;
					}
					possibilities1.put(groupName, squares);
					i++;
				}
				//
				String group = (String) JOptionPane.showInputDialog(null, "Choose a color group from the following:\n",
						"Customized Dialog", JOptionPane.PLAIN_MESSAGE, null, possibilities1.keySet().toArray(), null);
				if (group != null) {
					String square = (String) JOptionPane.showInputDialog(null, "Choose a groups for that player:\n",
							"Customized Dialog", JOptionPane.PLAIN_MESSAGE, null, possibilities1.get(group).toArray(),
							null);
					if (square != null) {
						message = "UISCREEN/SELLBUILDING2/" + group + "/" + square + "/";
						Controller.getInstance().dispatchMessage(message);
					}
				}
			}
			break;
		case "BUILDING":
			if (active) {
				if (parsed[1].equals("YES")) {
					ArrayList<Object> possibilities = new ArrayList<>();
					for (int i = 2; i < parsed.length; i++) {
						possibilities.add(parsed[i]);
					}
					String s = (String) JOptionPane.showInputDialog(null, "Choose a color group!", "",
							JOptionPane.PLAIN_MESSAGE, null, possibilities.toArray(), null);
					if (s != null)
						Controller.getInstance().dispatchMessage("UISCREEN/BUYBUILDING2/" + s.split(" ")[0] + "/");
				} else {
					JOptionPane.showMessageDialog(null, "You don't have any monopoly or majority ownership", "",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			break;
		case "BUILDING2":
			if (active) {
				ArrayList<Object> possibilities = new ArrayList<>();
				if (parsed[1].equals("NO")) {
					JOptionPane.showMessageDialog(null, parsed[2], "", JOptionPane.PLAIN_MESSAGE);
				} else {
					for (int i = 1; i < parsed.length - 1; i++) {
						possibilities.add(parsed[i]);
					}
					String s = (String) JOptionPane.showInputDialog(null, "Choose a square", "",
							JOptionPane.PLAIN_MESSAGE, null, possibilities.toArray(), possibilities.get(0));
					if (s != null)
						Controller.getInstance()
								.dispatchMessage("UISCREEN/BUYBUILDING3/" + s + "/" + parsed[parsed.length - 1]);
				}
			}
			break;
		case "CARD":
			if (parsed[1].equals("HURRICANE")) {
				switch (parsed[2]) {
				case "CHOOSEPLAYER":
					ConcurrentHashMap<Object, ArrayList<Object>> possibilities1 = new ConcurrentHashMap<>();
					int i = 3;
					while (i < parsed.length) {
						ArrayList<Object> groups = new ArrayList<>();
						String playerName = parsed[i];
						i++;
						while (!parsed[i].equals("END")) {
							groups.add(parsed[i]);
							i++;
						}
						possibilities1.put(playerName, groups);
						i++;
					}

					String player = (String) JOptionPane.showInputDialog(null, "Choose a player from the following:\n",
							"", JOptionPane.PLAIN_MESSAGE, null, possibilities1.keySet().toArray(), null);
					if (player != null) {
						String group = (String) JOptionPane.showInputDialog(null, "Choose a groups for that player:\n",
								"", JOptionPane.PLAIN_MESSAGE, null, possibilities1.get(player).toArray(), null);
						if (group != null) {
							message = "UISCREEN/HURRICANE/EXECUTE/" + player + "/" + group + "/";
							Controller.getInstance().dispatchMessage(message);
						}
					}
					break;
				}
			}
			break;
		case "MORTGAGE":
			if (parsed[1].equals("NO")) {
				JOptionPane.showMessageDialog(null, parsed[2], "", JOptionPane.ERROR_MESSAGE);
			} else {
				ArrayList<Object> possibilities = new ArrayList<>();
				for (int i = 3; i < parsed.length; i++) {
					possibilities.add(parsed[i]);
				}
				String s = (String) JOptionPane.showInputDialog(null, "Choose a Property to mortgage", "",
						JOptionPane.PLAIN_MESSAGE, null, possibilities.toArray(), null);
				if (s != null)
					Controller.getInstance().dispatchMessage("UISCREEN/MORTGAGE2/" + s + "/" + parsed[2]);

			}
			break;
		case "UNMORTGAGE":
			if (parsed[1].equals("NO")) {
				JOptionPane.showMessageDialog(null, parsed[2], "", JOptionPane.ERROR_MESSAGE);
			} else {
				ArrayList<Object> possibilities = new ArrayList<>();
				for (int i = 2; i < parsed.length; i++) {
					possibilities.add(parsed[i]);
				}
				String s = (String) JOptionPane.showInputDialog(null, "Choose a Property to unmortgage", "",
						JOptionPane.PLAIN_MESSAGE, null, possibilities.toArray(), null);
				if (s != null)
					Controller.getInstance().dispatchMessage("UISCREEN/UNMORTGAGE2/" + s);

			}
			break;
		}
	}

	private void startMusic() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(musicPath));
					clip.open(inputStream);
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				} catch (Exception e) {
				}
			}
		}, "Music").start();
	}

	private int toInt(String string) {
		return Integer.parseInt(string);
	}

	private class Piece {
		private Path path;
		private Point lastPoint;
		private Color color;
		private boolean isActive = false;

		public Piece() {
			path = new Path(scaleFactor);
		}

		public void paint(Graphics g) {
			g.setColor(color);
			g.fillOval(lastPoint.x, lastPoint.y, pieceSize, pieceSize);
			if (isActive) {
				dice.i = 0;
				if (path != null && path.hasMoreSteps())
					lastPoint = path.nextPoint();
				else {
					animator.stopAnimator();
					isActive = false;
					controller.dispatchMessage("UISCREEN/ANIMATIONEND");

					for (JButton button : willBeActivetedButtons)
						button.setEnabled(true);
					deedComboBox.setSelectedItem(willBeShowedDeed);
					willBeActivetedButtons.clear();
				}
			}
		}

	}

	private class JDice {
		private final ArrayList<Image> sideImages = new ArrayList<Image>(8);

		int i = 1;
		int d1;
		int d2;
		int d3;

		private Random r;

		public JDice() {
			for (int i = 1; i < 9; i++)
				sideImages.add(new ImageIcon(dieImagePath + i + ".png").getImage().getScaledInstance(dieSize, -1,
						Image.SCALE_SMOOTH));
			r = new Random();
		}

		public void paint(Graphics g) {
			if (i < 60)
				randomDice(g);
			else if (i < 120)
				realDice(g);
			else if (i == 120)
				myPiece.isActive = true;
			i++;
		}

		public void randomDice(Graphics g) {
			g.drawImage(sideImages.get(r.nextInt(6)), (screenHeight - 3 * dieSize - 20) / 2,
					(screenHeight - dieSize) / 2, null);
			g.drawImage(sideImages.get(r.nextInt(6)), (screenHeight - dieSize) / 2, (screenHeight - dieSize) / 2, null);
			int sd = r.nextInt(5);
			g.drawImage(sideImages.get(sd > 2 ? sd + 3 : sd), (screenHeight + dieSize) / 2 + 10,
					(screenHeight - dieSize) / 2, null);
		}

		public void realDice(Graphics g) {
			g.drawImage(sideImages.get(d1), (screenHeight - 3 * dieSize - 20) / 2, (screenHeight - dieSize) / 2, null);
			g.drawImage(sideImages.get(d2), (screenHeight - dieSize) / 2, (screenHeight - dieSize) / 2, null);
			g.drawImage(sideImages.get(d3 > 2 ? d3 + 3 : d3), (screenHeight + dieSize) / 2 + 10,
					(screenHeight - dieSize) / 2, null);
		}

	}

	private class JBoard extends JLabel {
		private static final long serialVersionUID = 1L;

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (active && dice.i != 0)
				dice.paint(g);
			for (Piece piece : pieces.values())
				piece.paint(g);
		}
	}

}
