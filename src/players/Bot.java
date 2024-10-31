package players;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;

import cards.Card;
import cards.Deck;
import cards.WildCard;
import main.Game;

public class Bot extends Player {

	public Bot(String name, Deck deckToTakeCards, Deck deckToGiveCards,Game game) {
		super(name, deckToTakeCards, deckToGiveCards,game);
	}

	/**
	 * What can bot play according to Card in the middle
	 * @return playable cards.
	 */
	public ArrayList<Card> findPossibleMovements(){
		ArrayList<Card> result= new ArrayList<>();
		Card topCard=this.getDeckToGiveCards().getCards().getLast();
		
		for (Iterator iterator = this.getMyDeck().getCards().iterator(); iterator.hasNext();) {
			Card card = (Card) iterator.next();
			if (Card.doCardsAgree(topCard, card)) {
				result.add(card);
			}
		}
		return result;
	}
	
	
	/**
	 * This function starts when it is bot's turn.
	 */
	public void botPlays() {
		Boolean willPlay=true;
		while (this.findPossibleMovements().isEmpty()) {// if no card matches the card in the middle, take new card.
			if (!this.getDeckToTakeCards().doesDeckHaveCards()) {
				if (Deck.cardsExchange(this.deckToTakeCards, this.deckToGiveCards)) {
					
					this.takeNewCard();
				}
				else {
					this.game.getEvents().add(this.getName() +" could not played as there is no cards in the middle to draw.");
					
					willPlay=false;
					break;}
				}
			this.game.getEvents().add(this.getName() +" took new card");
			this.game.getPlayscreenFrame().getActions().addElement(this.getName() +" took new card");
			this.game.getPlayscreenFrame().revalidate();
			this.game.getPlayscreenFrame().repaint();
			this.takeNewCard();
		}
		
		if (willPlay) {
			ArrayList<Card> cardsToPlay=this.findPossibleMovements();
			Card cardToPlay= cardsToPlay.get(0);
			for (Card card : cardsToPlay) {  
				if (card.getType().equals("number")) { //Bots plays numbercards first.
					cardToPlay=card;
					break;
				}
			}
			this.playTheCard(cardToPlay);
			this.unoTime();
		}
	}


	@Override
	public void choosingColor(WildCard card) {
		((WildCard) card).chooseNewColor(this.getMyDeck().findMostColor());
	}
	
	
	/**
	 * Bots say uno %75 percent of the time.
	 */
	public void unoTime() {
		if (this.getMyDeck().getCards().size()==1) {
			SecureRandom x=new SecureRandom();
			int y=x.nextInt(4);
			if (y>1) {
				this.setDidSayUNO(true);
			}
		}
	}
	
	
	
	

}
