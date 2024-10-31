package cards;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import buttons.CardButton;
import interfaces.Saveable;
import main.Game;
/**
 * Deck class contains a number of cards. Each player has a deck. Discard and draw piles are also a deck. 
 * A Deck can transfer cards to another deck.
 */
public class Deck extends AbstractDeck implements Saveable{

	private LinkedList<Card> cards= new LinkedList<>();
	private Game game;
	private JPanel cardPanel=new JPanel();
	private JLabel discardPile;
	
	
	
	public JPanel getCardPanel() {
		return cardPanel;
	}

	public void setCardPanel(JPanel cardPanel) {
		this.cardPanel = cardPanel;
	}

	public JLabel getDiscardPile() {
		return discardPile;
	}

	public void setDiscardPile(JLabel discardPile) {
		this.discardPile = discardPile;
	}

	public Game getGame() {
		return game;
	}
	public LinkedList<Card> getCards() {
		return cards;
	}

	public void setCards(LinkedList<Card> cards) {
		this.cards = cards;
	}
	
	
	/**
	 * Constructor for Deck.
	 * @param game to connect a deck to the game.
	 */
	public Deck(Game game) {
		this.game=game;
	}
	
	

	/**
	 * This method creates 108 cards. These 108 cards are all needed cards for a game.
	 * After creating, method shuffles and adds newly created 108 cards to this Deck. Therefore, this Deck becomes the draw pile.
	 */
	public void createMainDeck() {
		LinkedList<Card> result= new LinkedList<>();//new cards will be added to this LinkedList.
		Color[] colors= {Color.blue,Color.yellow,Color.red,Color.green};//Cards will be consist of these four colors (except for wild cards).
		
		
		for (int i = 0; i < 4; i++) {
			Color color=colors[i]; //this is for creating number and actions cards for each color.
			
			//creating number cards
			Card newCard= new Card(color, "number", 0,this.getGame());
			result.add(newCard);
			for (int k = 0; k < 2; k++) {
				for (int j = 1; j < 10; j++) {
					Card newNewCard= new Card(color, "number", j,this.getGame());
					result.add(newNewCard);
			}}
			
			
			//creating action cards
			for (int j = 0; j < 2; j++) {
				Card newNewCard= new Card(color, "+2", -1,this.getGame());
				result.add(newNewCard);
			}
			for (int j = 0; j < 2; j++) {
				Card newNewCard= new Card(color, "reverse", -1,this.getGame());
				result.add(newNewCard);
			}
			for (int j = 0; j < 2; j++) {
				Card newNewCard= new Card(color, "skip", -1,this.getGame());
				result.add(newNewCard);
			}
		}
		
		//creating wild cards
		for (int j = 0; j < 4; j++) {
			Card newNewCard= new WildCard(Color.black, "+4", -1,this.getGame());
			result.add(newNewCard);
		}
		for (int j = 0; j < 4; j++) {
			Card newNewCard= new WildCard(Color.black, "wild", -1,this.getGame());
			result.add(newNewCard);
		}
		
		
		Collections.shuffle(result);// Cards are shuffled. Now cards taken from that draw pile will be random.
		this.setCards(result);//This Deck becomes draw pile.
		}
	
	
	
	

	/**
	 * This method simply adds a Card to the Deck. 
	 * The reason it is special is that all the other methods to add Cards to the Deck depends on this function.
	 * @param card is the card we will add to this Deck.
	 */
	public void addToDeck(Card card) {
		this.getCards().add(card);
		card.makeCardShowableButton();
		CardButton button=card.getShowableButton();
		try {
			
			this.cardPanel.add(button);
			button.setEnabled(true);
			if (! (this.game.getPlayscreenFrame()==null)) {
				this.game.getPlayscreenFrame().revalidate();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.cardPanel.add(button);
		}
	}
	
	
	
	
	/**
	 * This method simply adds a number of Cards to the Deck. 
	 * @param cards is a Linkedlist of Cards.
	 */
	public void addNumerousCardsToDeck(LinkedList<Card> cards) {
		for (Iterator iterator = cards.iterator(); iterator.hasNext();) {
			Card card = (Card) iterator.next();
			this.addToDeck(card);
		}
		
	}
	
	
	

	/**
	 * This method simply takes a Card from the Deck. 
	 * The reason it is special is that all the other methods to take Cards from the Deck depends on this function.
	 * @param card is the card we will take from this Deck.
	 * @return the taken card.  Null if card does not exist in the Deck.
	 */
	public Card takeTheCard(Card card) {
		try {
			
			try {
				int a =this.cardPanel.getComponentZOrder(card.getShowableButton());// if card is being taken from our pile, corresponding button is being removed from cardpanel.
				this.cardPanel.remove(a);
				
			} catch (Exception e) {
			}
			this.getCards().remove(card);
			return card;
			
		} catch (Exception e) {
			return null;
		}
		
	}

	
	
	
	/**
	 * this method is for checking if a card matches the last card in this deck. If they match, new card is being added to this Deck.
	 * It is used for discard pile Deck.
	 * @param card is the new Card.
	 * @return true if cards match. False otherwise.
	 */
	public boolean cardHasCome(Card card) {
		if (Card.doCardsAgree(this.getCards().getLast(), card)) {
			this.addToDeck(card);
			return true;
		}
		else {
			return false;
		}
	}

	
	
	/**
	 * @return true if deck is not empty. False otherwise.
	 */
	public boolean doesDeckHaveCards() {
		return !this.getCards().isEmpty();
	}

	
	/**
	 * Takes all cards except the last one from this Deck.
	 * It is used for taking all bottom cards from discard pile Deck when Cards are finished in Draw Pile.
	 * @return a linkedlist of cards taken.
	 */
	public LinkedList<Card> takeAllBottomCards() {
		if (doesDeckHaveCards()) {
			LinkedList<Card> result=new LinkedList<>();
			
			if (this.getCards().size()==1) {
				return null;
			}
			
			for (int i = 0; i < this.getCards().size()-1; i++) {
				result.add(this.getCards().pop());
			}
			Collections.shuffle(result);
			return result;
		}
		else {
			return null;
		}
	}
	
	/**
	 * When there is an exact number of cards that is being wanted to taken, this function works.
	 * @param number how many cards is being wanted to taken.
	 * @return a linkedlist of cards taken.
	 */
	public LinkedList<Card> takeTopCards(int number) {
		if (doesDeckHaveCards()) {
			LinkedList<Card> result=new LinkedList<>();
			
			for (int i = 0; i < number; i++) {
				result.add(this.getCards().pop());
			}
			
			return result;
		}
		else {
			return null;
		}
	}

	@Override
	public String toString() {
		int n=0;
		String a= "";
		for (Iterator iterator = this.getCards().iterator(); iterator.hasNext();) {
			Card card = (Card) iterator.next();
			a=a+card+"\n";
			n++;
		}
		return a;
	}
	
	
	/**
	 * When there is nothing left in the draw pile deck, this function takes bottom cards from discard pile Deck. Then adds them to draw pile Deck.
	 * @param deckToTakeCards Draw pile Deck
	 * @param deckToGiveCards Discard pile Deck
	 * @return false if there is not any card to take from discard pile. True otherwise
	 */
	public static boolean cardsExchange(Deck deckToTakeCards,Deck deckToGiveCards) {
		LinkedList<Card> cardsFromBottom=deckToGiveCards.takeAllBottomCards();
		
		try {
			if (!cardsFromBottom.isEmpty()) {
				deckToTakeCards.addNumerousCardsToDeck(cardsFromBottom);
				return true;
			}
		} catch (NullPointerException e) {
			return false;
		}
		return false;
	}
	
	
	
	/**
	 * Finds most found card color in this Deck.
	 * @return most common color.
	 */
	public Color findMostColor() {
		LinkedList<String> colors=new LinkedList<>();
		for (int i = 0; i < this.getCards().size(); i++) {
			Card card = this.getCards().get(i);
			colors.add(Card.colorToString(card.getColor()));
		}
		
		
		HashMap<String, Integer> myMap= new HashMap<>();
		myMap.put("Blue", Collections.frequency(colors, "Blue"));
		myMap.put("Red", Collections.frequency(colors, "Red"));
		myMap.put("Green", Collections.frequency(colors, "Green"));
		myMap.put("Yellow", Collections.frequency(colors, "Yellow"));
		
		
		int maxN=0;
		Color color=Color.gray;
		if (myMap.get("Blue")>maxN) {
			maxN=myMap.get("Blue");
			color=Color.blue;
			
		}
		else if (myMap.get("Red")>maxN) {
			maxN=myMap.get("Red");
			color=Color.red;

		}
		else if (myMap.get("Green")>maxN) {
			maxN=myMap.get("Green");
			color=Color.green;

		}
		else if (myMap.get("Yellow")>maxN) {
			maxN=myMap.get("Yellow");
			color=Color.yellow;

		}
		
		return color;}
	
	
	
	

	
	
	
	
	/**
	 * Makes a Jlabel for a card in order to show in the discard pile.
	 * @return that Jlabel.
	 */
	public JLabel makeCardShowable(Card card) {
		
		JLabel jLabel=new JLabel();
		
		if (card.getPoint()==50) {// wild card
			jLabel.setText(card.getType());
			jLabel.setFont(new Font("MV Boli", Font.BOLD, 20));
			jLabel.setForeground(Color.white);
		} 
		else if(card.getPoint()==20) { // action card
			jLabel.setText(card.getType());
			jLabel.setFont(new Font("MV Boli", Font.BOLD, 20));
			jLabel.setForeground(Color.black);
		}
		else { // number card
			jLabel.setText(Integer.toString(card.getNumber()));
			jLabel.setFont(new Font("MV Boli", Font.BOLD, 50));
			jLabel.setForeground(Color.black);
		}
		
		jLabel.setBackground(card.getColor());
		jLabel.setHorizontalAlignment(JLabel.CENTER);
		jLabel.setVerticalAlignment(JLabel.CENTER);
		jLabel.setOpaque(true);
		jLabel.setVisible(true);
		return jLabel;
	}
	
	
	/**
	 * Makes last discarded card in this discard pile Deck visible by creating a Jlabel and adding it to the playscreenframe.
	 */
	public void showLastDiscardedCard() {
		try {
			this.discardPile.setVisible(false);// if there is already a discard pile, make is invisible.
		} catch (Exception e) {
			
		}
		
		Card topCard= this.getCards().getLast();
		JLabel jLabel= this.makeCardShowable(topCard);
		
		this.discardPile=new JLabel();
		this.discardPile.setBounds(474, 231, 110, 150);
		
		this.discardPile=jLabel;
		this.discardPile.setBounds(474, 231, 110, 150);
		this.discardPile.setVisible(true);
		this.getGame().getPlayscreenFrame().getContentPane().add(this.discardPile);
		this.getGame().getPlayscreenFrame().revalidate();
	}
	
	/**
	 * get total score of deck based on card points.
	 */
	public int getTotalScoreOfDeck() {
		int t=0;
		for (Card card : this.getCards()) {
			t+=card.getPoint();
		}
		return t;
	}

	/**
	 * saves the deck to a file by adding all cards information.
	 */
	@Override
	public void save(String filename) {
		
		try {
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename,true));
			
			for (Card card : cards) {
				writer.write(Card.colorToString(card.getColor())+" tahir "+card.getType()+" tahir "+card.getNumber()+"\n");// I writed tahir between properties of Cards for fun :D
			}
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
		
		
