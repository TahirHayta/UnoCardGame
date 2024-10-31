package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cards.Card;
import main.Game;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.TextArea;

import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

import javax.swing.Action;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import javax.swing.JScrollBar;

public class PlayScreenFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Game theGame=null;
	private JLabel discardPile;
	private JPanel globalCardPanel= new JPanel();
	private JRadioButton sayUno;
	private JList list;
	private DefaultListModel<String> actions;
	private JButton skipButton;
	
	
	

	public JButton getSkipButton() {
		return skipButton;
	}

	public JRadioButton getSayUno() {
		return sayUno;
	}


	public DefaultListModel<String> getActions() {
		return actions;
	}


	public void setActions(DefaultListModel<String> actions) {
		this.actions = actions;
	}


	public Game getTheGame() {
		return theGame;
	}



	public void setTheGame(Game theGame) {
		this.theGame = theGame;
	}

/**
 * When user chooses new game from previous screen, this constructor works.
 * @param theGame is a new game.
 */
	public PlayScreenFrame(Game theGame) {
		setTitle("UNO Card Game");
		this.theGame = theGame;
		this.theGame.gameReady(this);
		this.gameScreenReady();
		
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1149, 742);
		setResizable(false);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);
		
		JMenuItem saveGameMenuItem = new JMenuItem("SaveGame");
		saveGameMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File folder = new File("SavedGames");

				
				String filename=theGame.getMainPlayerName()+" TTT "+theGame.getBotNumber(); // new file is named.
				
				
				filename=filename+".txt";
				File file=new File(folder, filename);
				try {
					if (file.createNewFile()) {
						// If file does not exist, it creates new and returns ture.
					}
					else {// if file exist, it does not create new file.
						file.delete();// old file is deleted
						file.createNewFile();// now we can create new file.
					}
					
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				
				filename="SavedGames\\"+filename;
				theGame.save(filename);
				
				
			}
		});
		
		menu.add(saveGameMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.discardPile=theGame.getDeckToGiveCards().getDiscardPile(); //Card in the discard pile. only shows one card.
		this.discardPile.setBounds(474, 231, 110, 150);
		contentPane.add(this.discardPile); 
		
		
		JButton buttonToTakeCard = new JButton("Take Card");
		buttonToTakeCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				theGame.getPlayscreenFrame().getSkipButton().setEnabled(true);
				theGame.getEvents().add(theGame.getMainPlayer().getName() +" took new card");
				actions.addElement(theGame.getMainPlayer().getName() +" took new card");
				theGame.getMainPlayer().takeNewCard();
			}
		});
		
		buttonToTakeCard.setForeground(new Color(0, 0, 0));
		buttonToTakeCard.setBackground(Color.orange);
		buttonToTakeCard.setBounds(329, 231, 110, 150);
		buttonToTakeCard.setFont(new Font("MV Boli", Font.BOLD, 15));
		buttonToTakeCard.setFocusable(false);
		contentPane.add(buttonToTakeCard);
		
		this.globalCardPanel=theGame.getMainPlayer().getMyDeck().getCardPanel();
		JPanel cardPanel = this.globalCardPanel;
		cardPanel.setBounds(10, 476, 1115, 219);
		contentPane.add(this.globalCardPanel); 
		cardPanel.setLayout(new GridLayout(2, 0, 0, 0));
		
		sayUno = new JRadioButton("Say Uno");
		sayUno.setBounds(474, 409, 110, 39);
		sayUno.setFocusable(false);
		contentPane.add(sayUno);
		
		JButton btnNewButton = new JButton("Show Bots");
		this.buttonSetter(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame jFrameb=new ShowBotsFrame(theGame);
			}
		});
		btnNewButton.setBounds(416, 48, 168, 103);
		contentPane.add(btnNewButton);
		
		
		actions= new DefaultListModel<>();
		for (String string : theGame.getEvents()) {
			actions.addElement(string);
		}
		
		
		list = new JList(actions);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(814, 48, 258, 333);
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(812, 48, 260, 333);
		contentPane.add(scrollPane);
		
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(10, 10, 264, 31);
		textPane.setText(this.theGame.getSessionName());
		textPane.setEditable(false);
		contentPane.add(textPane);
		
		skipButton = new JButton("Skip");
		skipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				skipButton.setEnabled(false);
				theGame.gameLoop();
			}
		});
		skipButton.setBounds(590, 413, 110, 30);
		contentPane.add(skipButton);
		
		
		
		
		
		this.setVisible(true);
		
		theGame.gameLoop();// After creating playscreen, game loop starts.
	}
	
	/**
	 * When user chooses load game from load game screen, this constructor works. It has little differences from other constructor.
	 * It does not initialize frame elements which require theGame. The codes which are missing here but exist in other constructor is in loadedGameScreenReady method. loadedGameScreenReady method is called after theGame of this frame is setted.
	 * I did not put explanations in this method because explanations exist in other constructor.
	 */
	public PlayScreenFrame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1149, 742);
		setResizable(false);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);
		
		JMenuItem saveGameMenuItem = new JMenuItem("SaveGame");
		saveGameMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File folder = new File("SavedGames");

				
				String filename=theGame.getMainPlayerName()+" TTT "+theGame.getBotNumber();
				
				
				filename=filename+".txt";
				File file=new File(folder, filename);
				try {
					if (file.createNewFile()) {
						
					}
					else {
						file.delete();
						file.createNewFile();
					}
					
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				
				filename="SavedGames\\"+filename;
				theGame.save(filename);
				
				
			}
		});
		
		menu.add(saveGameMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JButton buttonToTakeCard = new JButton("Take Card");
		buttonToTakeCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				theGame.getPlayscreenFrame().getSkipButton().setEnabled(true);
				theGame.getEvents().add(theGame.getMainPlayer().getName() +" took new card");
				actions.addElement(theGame.getMainPlayer().getName() +" took new card");
				theGame.getMainPlayer().takeNewCard();
			}
		});
		
		buttonToTakeCard.setForeground(new Color(0, 0, 0));
		buttonToTakeCard.setBackground(Color.orange);
		buttonToTakeCard.setBounds(329, 231, 110, 150);
		buttonToTakeCard.setFont(new Font("MV Boli", Font.BOLD, 15));
		buttonToTakeCard.setFocusable(false);
		contentPane.add(buttonToTakeCard);
		
		
		sayUno = new JRadioButton("Say Uno");
		sayUno.setBounds(474, 409, 110, 39);
		sayUno.setFocusable(false);
		contentPane.add(sayUno);
		
		JButton btnNewButton = new JButton("Show Bots");
		this.buttonSetter(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame jFrameb=new ShowBotsFrame(theGame);
			}
		});
		btnNewButton.setBounds(416, 48, 168, 103);
		contentPane.add(btnNewButton);
		
		
		skipButton = new JButton("Skip");
		skipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				skipButton.setEnabled(false);
				theGame.gameLoop();
			}
		});
		skipButton.setBounds(590, 413, 110, 30);
		contentPane.add(skipButton);
	}

	
	/**
	 * Makes game screen ready for player, by showing last discarded card.
	 */
	public void gameScreenReady() {
		this.getTheGame().getDeckToGiveCards().showLastDiscardedCard();
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * Works after initializing theGame for loading a game.
	 */
	public void loadedGameScreenReady() {
		this.getTheGame().getDeckToGiveCards().showLastDiscardedCard();
		this.discardPile=theGame.getDeckToGiveCards().getDiscardPile();
		this.discardPile.setBounds(474, 231, 110, 150);
		contentPane.add(this.discardPile);
		this.globalCardPanel=theGame.getMainPlayer().getMyDeck().getCardPanel();
		
		JPanel cardPanel = this.globalCardPanel;
		cardPanel.setBounds(10, 476, 1115, 219);
		contentPane.add(this.globalCardPanel); 
		cardPanel.setLayout(new GridLayout(2, 0, 0, 0));

		actions= new DefaultListModel<>();
		for (String string : theGame.getEvents()) {
			actions.addElement(string);
		}
		
		
		list = new JList(actions);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(814, 48, 258, 333);
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(812, 48, 260, 333);
		contentPane.add(scrollPane);
		
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(10, 10, 264, 31);
		textPane.setText(this.theGame.getSessionName());
		textPane.setEditable(false);
		contentPane.add(textPane);
		
		this.revalidate();
		this.repaint();
		this.setVisible(true);
		
	}
	
	/**
	 * This method makes it easy to edit buttons. It is necessary, because there are several buttons with same attributes.
	 * @param jbutton is button to be edited.
	 */
	public void buttonSetter(JButton jbutton) {
		jbutton.setFocusable(false); 
		jbutton.setFont(new Font("MV Boli",Font.ITALIC, 25));
		jbutton.setForeground(Color.black);
		jbutton.setBackground(Color.orange);
		jbutton.setBorder(BorderFactory.createEtchedBorder());
	}
}
