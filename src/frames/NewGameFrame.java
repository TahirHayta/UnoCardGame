package frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import main.Game;

public class NewGameFrame extends JFrame implements ActionListener{

	
	private JButton goBackButton=new JButton();
	private JButton createGameButton= new JButton();
	private JButton loadGameButton=new JButton();
	private JButton leaderboardButton=new JButton();
	private JLabel BotNText=new JLabel();
	private JComboBox<Integer> BotNChooser;
	private int botN=1;
	private String name=new String();
	
	/**
	 * This method makes it easy to edit buttons. It is necessary, because there are several buttons with same attributes.
	 * @param jbutton is button to be edited.
	 */
	public void buttonSetter(JButton jbutton) {
		jbutton.setFocusable(false); 
		jbutton.setFont(new Font("MV Boli",Font.ITALIC, 25));
		jbutton.setForeground(Color.black);
		jbutton.setBackground(Color.orange);
		jbutton.setBorder(BorderFactory.createEtchedBorder());
	}
	
	/**
	 * This method makes it easy to edit buttons. It is necessary, because there are several buttons with same attributes.
	 * @param jbutton is button to be edited.
	 */
	public void buttonSetter2(JButton jbutton) {
		jbutton.setFocusable(false); 
		jbutton.setFont(new Font("MV Boli",Font.ITALIC, 10));
		jbutton.setForeground(Color.white);
		jbutton.setBackground(Color.blue);
		jbutton.setBorder(BorderFactory.createEtchedBorder());
	}
	
	/**
	 * Creates the frame
	 * @param name is username.
	 */
	public NewGameFrame(String name) {
		
		this.name=name;
		this.setTitle("New Game Screen");
		this.setSize(400,250);
		this.setResizable(false);
		this.getContentPane().setBackground(Color.white);
		this.setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		goBackButton.setText("Go Back");// Back to first page.
		goBackButton.addActionListener(this);
		goBackButton.setBounds(25,110,125,75);
		this.buttonSetter(goBackButton);

		
		createGameButton.setText("Create Game");// creates new game according to bot number.
		createGameButton.addActionListener(this);
		createGameButton.setBounds(200,110,175,75);
		this.buttonSetter(createGameButton);
		
		
		BotNText.setText("Number of Bots:");
		BotNText.setBounds(25,25,275,100);
		BotNText.setFont(new Font("MV Boli",Font.ITALIC, 30));

		
		String[] numbers= {"1","2","3","4","5","6","7","8","9"};// at least there is 1, at most there is 9 bots.
		BotNChooser= new JComboBox(numbers);// this makes us choose how many bots we would like to play with.
		BotNChooser.addActionListener(this);
		BotNChooser.setEditable(false);
		BotNChooser.setBounds(300,60,60,30);
		
		
		loadGameButton.setText("Load Game");// new screen appears where we can choose a saved game.
		loadGameButton.addActionListener(this);
		loadGameButton.setBounds(250,10,125,40);
		this.buttonSetter2(loadGameButton);
		this.add(loadGameButton);
		
		leaderboardButton.setText("Leaderboard");// new screen appears with leaderboard.
		leaderboardButton.addActionListener(this);
		leaderboardButton.setBounds(25,10,125,40);
		this.buttonSetter2(leaderboardButton);
		this.add(leaderboardButton);
		
		
		this.add(BotNChooser);
		this.add(BotNText);
		this.add(createGameButton);
		this.add(goBackButton);
		
		this.setVisible(true);
	}
	
	
	
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		 if(e.getSource()==BotNChooser) {
			 this.botN=BotNChooser.getSelectedIndex()+1;
		 }
		 
		 if(e.getSource()==createGameButton) {
			 Game theGame= new Game(this.name, this.botN); //Edit Tahir
			 this.dispose();
			 PlayScreenFrame thePlayScreen=new PlayScreenFrame(theGame);
			 
			 
		 }
		 
		 
		 if(e.getSource()==goBackButton) {
			 this.dispose();
			 FirstPageFrame firstPageFrame=new FirstPageFrame();
		 }
		 if (e.getSource()==loadGameButton) {
			 LoadGameFrame loadGameFrame=new LoadGameFrame(this.name);
			 this.dispose();
		}
		 if (e.getSource()==leaderboardButton) {
			 LeaderboardFrame leaderboardFrame= new LeaderboardFrame();
		}
		
		
	}

}
