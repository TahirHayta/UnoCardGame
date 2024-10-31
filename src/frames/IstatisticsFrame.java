package frames;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Game;
import javax.swing.JTextPane;

public class IstatisticsFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/**
	 * Create the frame. When someone wons, it only shows who has won.
	 */
	public IstatisticsFrame(String text) {
		setTitle("Istatistics");
		setBounds(100, 100, 294, 107);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JTextPane txtpnTtt = new JTextPane();
		txtpnTtt.setText(text);
		txtpnTtt.setFont(new Font("MV Boli",Font.ITALIC, 20));
		txtpnTtt.setEditable(false);
		contentPane.add(txtpnTtt);
		this.setVisible(true);
	}

}
