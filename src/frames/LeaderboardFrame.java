package frames;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.ListSelectionModel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class LeaderboardFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ArrayList<String> stringlist= new ArrayList<>();



	/**
	 * Create the frame. It shows users' istatistics.
	 */
	public LeaderboardFrame() {
		setTitle("LeaderBoard");
		setBounds(100, 100, 1001, 660);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DefaultListModel<String> dlm= new DefaultListModel<>();
		makeLeaderboard();
		Collections.sort(stringlist, new Comparator<String>() { //Comparator in order to sort istatistics according to average score.
			   @Override
			   public int compare(String line1, String line2) {
				   line1=line1.split("Average Score: ")[1].substring(0,(line1.split("Average Score: ")[1]).length()-1).replace(',', '.');// A line is a long sentence consist of different kinds of information. Average score is in the end of it. So, firstly we split the line into two, with second including something like "20,25 ". Then we delete the last " " character by using substring. Now we have something like "20,25". We change "," with "." so we can turn it into a Double.
				   Double lin1b=Double.parseDouble(line1);// We turn string into a double.
				   line2=line2.split("Average Score: ")[1].substring(0,(line2.split("Average Score: ")[1]).length()-1).replace(',', '.');// Progress is same.
				   Double lin2b=Double.parseDouble(line2);
				   if (Double.compare(lin1b, lin2b)<0) {
					return 1;
				}
				   else if (Double.compare(lin1b, lin2b)>0) {
					return -1;
				}
				   return 0;
			   }
			 });
		for (String string : stringlist) {
			dlm.addElement(string); // Add newly sorted list  to default list model.
		}
		
		
		JList list = new JList(dlm);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(10, 10, 967, 603);
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(10, 10, 967, 603);
		contentPane.add(scrollPane);
		
		this.setVisible(true);
	}
	
	/**
	 * Reads the leaderboard info from leaderboard.txt and adds every line to this.stringlist.
	 */
	public void makeLeaderboard() {
	    try {
            BufferedReader reader = new BufferedReader(new FileReader("leaderboard.txt"));
            String newRecord="";
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" , "); // Every line is splitted into 5 parts. first part is username, second is total wins, third is total losts, 4. is total games, and 5. is total score.
                if (parts.length==5) {
                	
                	double wlRatio=(double) Integer.parseInt(parts[1])/Integer.parseInt(parts[2]); // Calculates win lose ratio by dividing total wins to total losts.
                	String wlRatioString= String.format("%.2f", wlRatio);
                	
                	double as=(double) Integer.parseInt(parts[4])/Integer.parseInt(parts[3]);// Calculates average score by dividing total score to total games..
                	String asString= String.format("%.2f", as);
                	
		            newRecord="      Username: "+parts[0]+", Total wins: "+parts[1]+", Total losts: "+parts[2]+", Total games: "+parts[3]+", Total score: "+parts[4]+", Win/Lose Ratio: "+wlRatioString+", Average Score: "+asString+"\n";// Puts all information into one sentence.
		            this.stringlist.add(newRecord);
					}
                	}
            reader.close();
            }
	    catch (Exception e) {
			e.printStackTrace();
		}
	}
}
