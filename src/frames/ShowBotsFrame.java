package frames;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Game;
import players.Player;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JTextArea;

public class ShowBotsFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private Game game;
	

	/**
	 * Create the frame.
	 */
	public ShowBotsFrame(Game game) {
		setTitle("Bots Info");
		this.game=game;
		setBounds(100, 100, 390, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		ArrayList<String> a=new ArrayList<>();
		for (Player bot : game.getPlayers()) {
			a.add(bot.getName()+" has "+bot.getMyDeck().getCards().size()+" cards.");
		}
		a.add("The way is: "+this.game.getWay());
		a.add("Draw pile has "+this.game.getDeckToTakeCards().getCards().size()+" cards.");
		for (int i = 0; i < a.size(); i++) {
			JTextArea textArea = new JTextArea();
			textArea.setText(a.get(i));
			contentPane.add(textArea);
		}
		
		this.setVisible(true);
		
	}

}
