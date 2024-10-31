package frames;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cards.Card;
import cards.WildCard;
import main.Game;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * This frame is for choosing new color to wild cards.
 */
public class ColorChoosingFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private WildCard card;
	private JButton blueButton;
	private JButton redButton;
	private JButton yellowButton;
	private JButton greenButton;
	private Game game;
	
	public Game getGame() {
		return game;
	}


	public WildCard getCard() {
		return card;
	}

	public void setCard(WildCard card) {
		this.card = card;
	}

	/**
	 * Creates the frame.
	 * @param card for which card new color is being choosed.
	 * @param game
	 */
	public ColorChoosingFrame(WildCard card, Game game) {
		setTitle("Choose new color");
		this.card=card;
		this.game=game;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 440, 180);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// There are four buttons for four colors. Main player can choose any of them by clicking.
		blueButton= new JButton("Blue");
		this.buttonSetter(blueButton, Color.blue);
		blueButton.setBounds(10, 25, 85, 92);
		contentPane.add(blueButton);
		
		redButton= new JButton("Red");
		this.buttonSetter(redButton, Color.red);
		redButton.setBounds(119, 25, 85, 92);
		contentPane.add(redButton);
		
		yellowButton= new JButton("Yellow");
		this.buttonSetter(yellowButton, Color.yellow);
		yellowButton.setBounds(226, 25, 85, 92);
		contentPane.add(yellowButton);
		
		greenButton= new JButton("Green");
		this.buttonSetter(greenButton, Color.green);
		greenButton.setBounds(335, 25, 85, 92);
		contentPane.add(greenButton);
		
		this.setVisible(true);
	}
	
	/**
	 * This method makes it easy to edit buttons. It is necessary, because there are several buttons with same attributes.
	 * @param button button which will be edited
	 * @param color color of that button.
	 */
	public void buttonSetter(JButton button,Color color) {
		button.setBackground(color);
		if (color.equals(Color.blue)) {
			button.setForeground(Color.white); //if color of button is blue, black foreground is not appropriate. white foreground is better.
		}
		else {
			button.setForeground(Color.black);
		}
		
		button.setFont(new Font("MV Boli", Font.ITALIC, 10));
		button.addActionListener(this);
		button.setFocusable(false);
	}

	/**
	 * Method from ActionListener.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource()==blueButton) {
			this.getCard().chooseNewColor(Color.blue);
		}
		else if (e.getSource()==redButton) {
			this.getCard().chooseNewColor(Color.red);
		}
		else if (e.getSource()==yellowButton) {
			this.getCard().chooseNewColor(Color.yellow);
		}
		else if (e.getSource()==greenButton) {
			this.getCard().chooseNewColor(Color.green);
		}// new color for card is choosed according to which button is clicked
		
		for (Card card2 : this.getGame().getMainPlayer().getMyDeck().getCards()) {
			card2.getShowableButton().setEnabled(false);// Main player has played, so it is Bots' turn. In order to avoid user clicking Cards when it is not his turn, cardbuttons are being disabled.
		}
		this.getGame().getMainPlayer().getDeckToGiveCards().showLastDiscardedCard();
		this.dispose();
		game.gameLoop();// game contunies. Bots play.
	}
}
