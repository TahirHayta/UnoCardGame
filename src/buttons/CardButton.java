package buttons;

import javax.swing.JButton;

import cards.Card;
/**
 * A special button class for cards. It is used to make playable cards for main player.
 */
public class CardButton extends JButton {

	
	private Card card;
	
	/**
	 * Getter method for Card.
	 * @return card
	 */
	public Card getCard() {
		return card;
	}

	/**
	 * setter method for Card.
	 * @param card
	 */
	public void setCard(Card card) {
		this.card = card;
	}

	/**
	 * Constructor for CardButton
	 * @param card to show which Card this button belongs to.
	 */
	public CardButton(Card card) {
		this.card=card;
	}
}
