package frames;

import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Game;

import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoadGameFrame extends JFrame  implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String mainPlayerName;
	private JList list;
	private JButton btnNewButton;
	

	public String getMainPlayerName() {
		return mainPlayerName;
	}
	public void setMainPlayerName(String mainPlayerName) {
		this.mainPlayerName = mainPlayerName;
	}
	/**
	 * This method makes it easy to edit buttons. It is necessary, because there are several buttons with same attributes.
	 * @param jbutton is button to be edited.
	 */
	public void buttonSetter(JButton jbutton) {
		jbutton.setFocusable(false); 
		jbutton.setFont(new Font("MV Boli",Font.ITALIC, 25));
		jbutton.setForeground(Color.black);
		jbutton.setBackground(Color.orange);
		jbutton.setBorder(BorderFactory.createEtchedBorder());}


	/**
	 * Creates frame.
	 * @param mainPlayerName is username.
	 */
	public LoadGameFrame(String mainPlayerName) {
		setTitle("Load Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 371, 454);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.mainPlayerName=mainPlayerName;
		File folder = new File("SavedGames");
        File[] files = folder.listFiles(); // lists all files in SavedGames folder.
        
        ArrayList<String> fileNames= new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
			fileNames.add(files[i].getPath().split(".txt")[0]);//Removing ".txt" from the end of file names.
		}
        
        DefaultListModel<String> dlm= new DefaultListModel<>();
        for (String string : fileNames) {
        	if (string.split(" TTT ")[0].equals("SavedGames\\"+this.mainPlayerName)) {//Basically file names are username+" TTT "+botnumber. After splitting using " TTT " and taking 0. element, we are finding username.
        		dlm.addElement(string);// if the usernames are same, we can open that game. so this is adding it to the defaultlistmodel. Games with different usernames are invisible, therefore not openable.
			}
			
		}
		
		
		list = new JList(dlm);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(10, 10, 967, 603);
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(38, 28, 282, 290);
		contentPane.add(scrollPane);
		
		

		
		
		
		
		btnNewButton = new JButton("Play Game");
		btnNewButton.addActionListener(this);
		btnNewButton.setBounds(96, 328, 163, 78);
		this.buttonSetter(btnNewButton);
		contentPane.add(btnNewButton);
		
		this.setVisible(true);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!this.list.isSelectionEmpty() && e.getSource()==this.btnNewButton) {
			String filename= this.list.getSelectedValue().toString()+".txt";
			Game loadedGame=new Game(this.mainPlayerName, 0);
			loadedGame.loadGame(filename);
			
			
			PlayScreenFrame thePlayScreen=new PlayScreenFrame();
			loadedGame.setPlayscreenFrame(thePlayScreen);
			thePlayScreen.setTheGame(loadedGame);
			thePlayScreen.loadedGameScreenReady();
			this.dispose();
			thePlayScreen.revalidate();
			thePlayScreen.repaint();
			thePlayScreen.getTheGame().gameLoop();
			
		}
		
	}
}
