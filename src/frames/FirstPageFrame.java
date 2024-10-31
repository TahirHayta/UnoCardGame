package frames;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.TextField;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JPasswordField;
import java.awt.event.HierarchyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.awt.event.HierarchyEvent;

public class FirstPageFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private TextField userPasswordIn;
	private JPanel firstPanel;
	private TextField userNameIn;
	private JTextArea txtrUsername;
	private JTextArea txtrPassword;
	private JButton signInButton2;
	private JButton logInButton2;
	private String userName;
	
	/**
	 * Launcher of the application. main function is here.
	 */
	public static void main(String[] args) {
		/************** Pledge of Honor ******************************************
		I hereby certify that I have completed this programming project on my own
		without any help from anyone else. The effort in the project thus belongs
		completely to me. I did not search for a solution, or I did not consult any
		program written by others or did not copy any program from other sources. I
		read and followed the guidelines provided in the project description.
		READ AND SIGN BY WRITING YOUR NAME SURNAME AND STUDENT ID
		SIGNATURE: <Tahir Hayta, 83412>
		*************************************************************************/
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FirstPageFrame frame = new FirstPageFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the frame.
	 */
	public FirstPageFrame() {
		setTitle("UNO Card Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 324, 241);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		firstPanel = new JPanel();
		firstPanel.setBounds(5, 5, 300, 194);
		contentPane.add(firstPanel);
		firstPanel.setLayout(null);
		
		userNameIn = new TextField(); //Takes username.
		userNameIn.setBounds(94, 35, 155, 23);
		firstPanel.add(userNameIn);
		
		txtrUsername = new JTextArea();
		txtrUsername.setText("UserName:");
		txtrUsername.setFont(new Font("MV Boli", Font.ITALIC, 15));
		txtrUsername.setBounds(10, 35, 78, 23);
		firstPanel.add(txtrUsername);
		
		txtrPassword = new JTextArea();
		txtrPassword.setText("Password:");
		txtrPassword.setFont(new Font("MV Boli", Font.ITALIC, 15));
		txtrPassword.setBounds(10, 79, 78, 23);
		firstPanel.add(txtrPassword);
		
		userPasswordIn = new TextField();//Takes password.
		userPasswordIn.setBounds(94, 79, 160, 23);
		firstPanel.add(userPasswordIn);
		
		signInButton2 = new JButton("Sign up");
		signInButton2.addActionListener(this);
		signInButton2.setForeground(Color.BLACK);
		signInButton2.setFont(new Font("MV Boli", Font.ITALIC, 25));
		signInButton2.setFocusable(false);
		signInButton2.setBorder(BorderFactory.createEtchedBorder());
		signInButton2.setBackground(Color.ORANGE);
		signInButton2.setBounds(10, 131, 107, 53);
		firstPanel.add(signInButton2);
		
		logInButton2 = new JButton("Log in");
		logInButton2.setForeground(Color.BLACK);
		logInButton2.setFont(new Font("MV Boli", Font.ITALIC, 25));
		logInButton2.setFocusable(false);
		logInButton2.setBorder(BorderFactory.createEtchedBorder());
		logInButton2.setBackground(Color.ORANGE);
		logInButton2.setBounds(183, 131, 107, 53);
		logInButton2.addActionListener(this);
		firstPanel.add(logInButton2);
		this.setVisible(true);
	}


	/**
	 * Method from ActionListener.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String userName=this.userNameIn.getText();
		String password=this.userPasswordIn.getText();
		
		if (e.getSource()==signInButton2) {
			if (!this.authenticate(userName, password)&&!userName.isEmpty()&&!password.isEmpty()) {//if authentication is unsuccessfull(user does not exist), and also name and password fields are not empty, it creates new user.
				this.signup(userName, password);
				this.userName=this.userNameIn.getText();
				this.dispose();
				new NewGameFrame(this.userName);
			}
			
		}
		
		else if (e.getSource()==logInButton2) {
			if (authenticate(userName, password)) {//if password and name are correct, it opens the next menu.
				this.userName=userName;
				this.dispose();
				new NewGameFrame(this.userName);
			}
		}
		
	}
	
	
	
	
	/**
	 * method to check if a user exist with given name and password. It looks to the users.txt file, which has usernames and passwords.
	 * @param username name of user
	 * @param password password of user
	 * @return ture if user exist. False otherwise.
	 */
    private boolean authenticate(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) { 
            String line;
            while ((line = reader.readLine()) != null) { //if and of the page has not come
                String[] parts = line.split(" : ");// parts' length must be 2. first element is username, second is password.
                if (parts.length == 2) {
                	if ((parts[0].equals(username) && parts[1].equals(password))) {
                		return true;
    				}
                	}  
            }
        } 
        catch (Exception e) {
            
        }
        return false;// if there is not such user, it returns false.
    }

    /**
     * creates new user. prints users info to the txt file.
     * @param username name of user
	 * @param password password of user
     * @return true if mission is succesfull. False if there is a problem.
     */
    private boolean signup(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {// File writer takes true as a parameter because new info will be added to the end of the file.
            writer.write(username + " : " + password);
            writer.newLine();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    
    
    
}
