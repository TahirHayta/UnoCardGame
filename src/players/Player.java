package players;

import java.awt.Color;
import java.util.LinkedList;

import cards.Card;
import cards.Deck;
import cards.WildCard;
import frames.ColorChoosingFrame;
import main.Game;

public class Player {
	protected String name;
	protected Deck myDeck;
	protected Deck deckToTakeCards;
	protected Deck deckToGiveCards;
	protected Boolean didSayUNO=true;
	protected Game game;
	
	
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Deck getMyDeck() {
		return myDeck;
	}
	public void setMyDeck(Deck myDeck) {
		this.myDeck = myDeck;
	}
	public Deck getDeckToTakeCards() {
		return deckToTakeCards;
	}
	public void setDeckToTakeCards(Deck deckToTakeCards) {
		this.deckToTakeCards = deckToTakeCards;
	}
	public Deck getDeckToGiveCards() {
		return deckToGiveCards;
	}
	public void setDeckToGiveCards(Deck deckToGiveCards) {
		this.deckToGiveCards = deckToGiveCards;
	}
	public Boolean getDidSayUNO() {
		return didSayUNO;
	}
	public void setDidSayUNO(Boolean didSayUNO) {
		this.didSayUNO = didSayUNO;
	}
	
	
	
	public Player(String name, Deck deckToTakeCards, Deck deckToGiveCards, Game game) {
		this.game=game;
		this.name = name;
		this.myDeck=new Deck(this.game);
		this.deckToGiveCards=new Deck(game);
		this.deckToTakeCards=new Deck(game);
		this.deckToTakeCards = deckToTakeCards;
		this.deckToGiveCards = deckToGiveCards;
	}

	public void takeNewCard() {
		try {
			this.getMyDeck().addNumerousCardsToDeck(this.getDeckToTakeCards().takeTopCards(1));// take one card

		} catch (NullPointerException e) {
			this.game.getEvents().add(this.getName()+" cannot take card. Cards are shuffling.");

			this.game.getPlayscreenFrame().getActions().addElement(this.getName()+" cannot take card. Cards are shuffling.");
			this.game.getPlayscreenFrame().revalidate();
			this.game.getPlayscreenFrame().repaint();
			
			if (Deck.cardsExchange(this.deckToTakeCards, this.deckToGiveCards)) {
				takeNewCard();
			}
		}
	}
	/**
	 * taking seven cards
	 * @param deckToTakeCards
	 */
	public void startPosition(Deck deckToTakeCards) {
		for (int i = 0; i < 7; i++) {
			this.takeNewCard();
		}
	}
	
	public void takeTwo() {
		for (int i = 0; i < 2; i++) {
			this.takeNewCard();
		}
	}
	
	public void takeFour() {
		for (int i = 0; i < 4; i++) {
			this.takeNewCard();
		}}
	/**
	 * controls if player has won.
	 * @return true or false
	 */
	public boolean hasWon() {
		return this.getMyDeck().getCards().isEmpty();
	}
	
	
	
	public void playTheCard(Card card) {
		if (this.getDeckToGiveCards().cardHasCome(card)) {// if card matches
			this.getMyDeck().takeTheCard(card);
			this.game.getEvents().add(this.getName() +" played "+card);
			this.game.getPlayscreenFrame().getActions().addElement("Player "+this.getName() +" played "+card);
			this.game.getPlayscreenFrame().revalidate();
			this.game.getPlayscreenFrame().repaint();
			
			
			
			if (card.getColor().equals(Color.black)) {
				this.choosingColor((WildCard)card);
				if (this instanceof Bot) {
					for (Card card2 : this.getMyDeck().getCards()) {
						card2.getShowableButton().setEnabled(false);
					}
					this.getDeckToGiveCards().showLastDiscardedCard();
				}
			}
			else {
				for (Card card2 : this.getMyDeck().getCards()) {
					card2.getShowableButton().setEnabled(false);
				}
				this.getDeckToGiveCards().showLastDiscardedCard();
			}

			
		}
	}
	
	/**
	 * Enables card buttons so main player can click them.
	 */
	public void mainPlayersTurn() {
		for (Card card : this.getMyDeck().getCards()) {
			card.getShowableButton().setEnabled(true);
		}
		
	}
	
	public void choosingColor(WildCard card) {
		ColorChoosingFrame colorChoosingFrame=new ColorChoosingFrame(card,this.getGame());
	}
}
