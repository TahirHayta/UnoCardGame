package cards;

import java.awt.Color;
import java.util.LinkedList;

import javax.swing.JLabel;

public abstract class AbstractDeck {
	/**
	 * This is super class of Deck. All the methods in here exist so that I could plan what Deck does when I was making the Deck Class.
	 */
	
	public abstract void createMainDeck();
	
	
	public abstract void addToDeck(Card card);
	
	
	public abstract void addNumerousCardsToDeck(LinkedList<Card> cards);
	
	
	public abstract Card takeTheCard(Card card);
	
	
	public abstract boolean cardHasCome(Card card);
	
	
	public abstract boolean doesDeckHaveCards();
	
	
	public abstract LinkedList<Card> takeAllBottomCards();
	
	
	public abstract LinkedList<Card> takeTopCards(int number);
	
	
	public abstract Color findMostColor();
	
	
	public abstract JLabel makeCardShowable(Card card) ;
	
	
	public abstract void showLastDiscardedCard();
	
	
	public abstract int getTotalScoreOfDeck();
	
	
	
	
	
	

}
