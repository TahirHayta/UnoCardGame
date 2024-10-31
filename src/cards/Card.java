package cards;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import buttons.CardButton;
import main.Game;

public class Card {
	
	protected Color color;
	protected String type;
	protected int number;
	protected int point;
	protected boolean active=true;
	protected CardButton showableButton;
	protected Game game;
	
	
	public CardButton getShowableButton() {
		return showableButton;
	}

	public void setShowableButton(CardButton showableButton) {
		this.showableButton = showableButton;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	
	
	/**
	 * Constructor for Card objects.
	 * @param color shows the color. Type of the parameter is Color
	 * @param type shows which type this Card belongs to. There are 6 types which are number, +2, reverse, skip, +4, wild.
	 * @param number shows the number of number cards. Number of other cards are -1.
	 * @param game shows the game this card belongs to.
	 */
	public Card(Color color, String type, int number,Game game) {
		this.color = color;
		this.type = type;
		this.number = number;
		this.game=game;
		if (type.equals("number")) {
			this.point=number;
		}
		else if (type.equals("+4")) {
			this.point=50;
		}
		else if (type.equals("wild")) {
			this.point=50;
		}
		else{ // remaining cards are action cards. Their points are 20. 
			this.point=20;
		}
	}
	
	/**
	 * This method shows that if a card matches other card.
	 * @param previouscard must be the top card on the discard pile.
	 * @param newcard must be the card which player wants to discard.
	 * @return true if Cards match. returns false if cards do not match.
	 */
	public static boolean doCardsAgree(Card previouscard,Card newcard) {
		
		if (newcard.getPoint()==50) { //if newcard is wild, this means it matches every card.
			return true;
		}
		
		if (previouscard.getColor().equals(Color.black)) {
			if (previouscard.getColor().equals(newcard.getColor())) { //if previous card was wild, method looks if newly chosen color of wild card matches new card.
				return true;
			}
		}
		
		if (previouscard.getColor().equals(newcard.getColor())) { // if their colors are same, they will always match each other.
			return true;
		}
		else if (previouscard.getType().equals(newcard.getType()) && previouscard.getNumber()==newcard.getNumber()) { // if they are same type and they have same number, they will always match.
			return true;
		}
		return false;
	}

	/**
	 * this method is for turning five Card colors into type string.
	 * @param color 
	 * @return string of that color.
	 */
	public static String colorToString(Color color) {
		String colors= new String();
		if (color.equals(Color.black)) {
			colors="Black";
		}
		else if (color.equals(Color.green)) {
			colors="Green";
		}
		else if (color.equals(Color.blue)) {
			colors="Blue";
		}
		else if (color.equals(Color.red)) {
			colors="Red";
		}
		else if (color.equals(Color.yellow)) {
			colors="Yellow";
		}
		return colors;
	}
	/**
	 * this method is for turning five Card colors's string names into type Color.
	 * @param string either Black, Green, Blue, Red, Yellow.
	 * @return Color type of that color
	 */
	public static Color stringToColor(String string) {
		Color color2;
		if (string.equals("Black")) {
			color2=Color.black;
		}
		else if (string.equals("Green")) {
			color2=Color.green;
		}
		else if (string.equals("Blue")) {
			color2=Color.blue;
		}
		else if (string.equals("Red")) {
			color2=Color.red;
		}
		else if (string.equals("Yellow")) {
			color2=Color.yellow;
		}
		else {
			color2=Color.gray;
		}
		return color2;
	}
	
	/**
	 * overrides toString method for Card.
	 */
	@Override
	public String toString() {
		
		String color= Card.colorToString(this.color);
		return color+" "+this.getType().toString()+" "+this.getPoint();
	}
	
	
	
	
	/**
	 * This method makes a CardButton for a Card. Then assigns it to this.ShowableButton
	 * This method is used only for Main Player's Cards.
	 */
	public void makeCardShowableButton() {
		try {
			boolean a1=this.getShowableButton().equals(null);// Only to check if there exist already a showableButton. If it does not exist, there will be an exception. Code will contunie executing from catch block.
		} catch (Exception e) {

			CardButton jButton=new CardButton(this);
			
			if (this.getPoint()==50) {// for WildCards
				jButton.setText(this.getType());
				jButton.setFont(new Font("MV Boli", Font.BOLD, 20));
				jButton.setForeground(Color.white);
			} 
			else if(this.getPoint()==20) { // action cards
				jButton.setText(this.getType());
				jButton.setFont(new Font("MV Boli", Font.BOLD, 20));
				jButton.setForeground(Color.black);
			}
			else { // number cards
				jButton.setText(Integer.toString(this.getNumber()));
				jButton.setFont(new Font("MV Boli", Font.BOLD, 50));
				jButton.setForeground(Color.black);
			}
			jButton.setEnabled(false);// it will be enabled at Player's turn
			
			jButton.setFocusable(false);
			jButton.setBackground(this.getColor());
			jButton.setHorizontalAlignment(jButton.CENTER);
			jButton.setVerticalAlignment(jButton.CENTER);
			
			jButton.setOpaque(true);
			jButton.setVisible(true); // it is visible so we can see the card.
			
			jButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getSource()==jButton) {
						game.getMainPlayer().playTheCard(jButton.getCard()); //when clicked, card is played.
						if (jButton.getCard().equals(game.getDeckToGiveCards().getCards().getLast())) {
							if (jButton.getCard().getPoint()!=50) { // After the Card is played, it checks if player will choose a color. Thats why it checks if the card is wildcard.
								game.gameLoop();// if there is no problem, game contunies after Main player played.
							}
							
						}
						
						
					}
				}
			});
			
			this.setShowableButton(jButton);}
		}
	
	
	
	
}
