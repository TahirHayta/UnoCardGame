package cards;

import java.awt.Color;

import main.Game;

public class WildCard extends Card {

	private Color newColor;
	
	public Color getNewColor() {
		return newColor;
	}

	

	/**
	 * Constructor for WildCards. Parameters are same as Card.
	 * @param color always Color.black
	 * @param type shows which type this Card belongs to. There are 2 types which are +4, wild.
	 * @param number always -1.
	 * @param game shows the game this card belongs to.
	 */
	public WildCard(Color color, String type, int number, Game game) {
		super(color, type, number,game);
	}
	/**
	 * Wild cards are originally black. This methods selects a new color for WildCards. New color depends on what color Main player wants. Bots select the color which most of their cards have.
	 * @param color new color.
	 */
	public  void chooseNewColor(Color color) {
		this.newColor=color;
		this.color=color;
	}

}
