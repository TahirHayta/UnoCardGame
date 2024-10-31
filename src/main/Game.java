package main;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFrame;

import cards.Card;
import cards.Deck;
import cards.WildCard;
import frames.IstatisticsFrame;
import frames.NewGameFrame;
import frames.PlayScreenFrame;
import interfaces.Saveable;
import players.Bot;
import players.Player;

public class Game implements Saveable{

	private int turn=0;
	private String mainPlayerName;
	private Player mainPlayer;
	private int botNumber;
	private LinkedList<Player> players=new LinkedList<>();
	private Deck deckToTakeCards= new Deck(this);
	private Deck deckToGiveCards=new Deck(this);
	private PlayScreenFrame playscreenFrame;
	private boolean way=true;
	private String sessionName;
	private ArrayList<String> events=new ArrayList<>();

	


	public ArrayList<String> getEvents() {
		return events;
	}

	public void setEvents(ArrayList<String> events) {
		this.events = events;
	}

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	/**
	 * @return Clockwise if way is true. Counterclockwise otherwise.
	 */
	public String getWay() {
		if (way) {
			return "Clockwise";
		} else {
			return "Counterclockwise";
		}
	}

	public void setWay(boolean way) {
		this.way = way;
	}

	public PlayScreenFrame getPlayscreenFrame() {
		return playscreenFrame;
	}

	public void setPlayscreenFrame(PlayScreenFrame playscreenFrame) {
		this.playscreenFrame = playscreenFrame;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public Player getMainPlayer() {
		return mainPlayer;
	}

	public void setMainPlayer(Player mainPlayer) {
		this.mainPlayer = mainPlayer;
	}

	public String getMainPlayerName() {
		return mainPlayerName;
	}

	public void setMainPlayerName(String mainPlayerName) {
		this.mainPlayerName = mainPlayerName;
	}

	public int getBotNumber() {
		return botNumber;
	}

	public void setBotNumber(int botNumber) {
		this.botNumber = botNumber;
	}
	
	public LinkedList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(LinkedList<Player> players) {
		this.players = players;
	}

	public Deck getDeckToTakeCards() {
		return deckToTakeCards;
	}

	public void setDeckToTakeCards(Deck deckToTakeCards) {
		this.deckToTakeCards = deckToTakeCards;
	}

	public Deck getDeckToGiveCards() {
		return deckToGiveCards;
	}
	
	public void setDeckToGiveCards(Deck deckToGiveCards) {
		this.deckToGiveCards = deckToGiveCards;
	}
	
	public Game(String mainPlayerName, int botNumber) {
		super();
		this.mainPlayerName = mainPlayerName;
		this.botNumber = botNumber;
	}

	
	/**
	 * Creates bots and add these bots to this.players. Bots' names are TA names from Comp132 :D
	 */
	public void makeBots() {
		
		for (int i = 0; i < this.getBotNumber(); i++) {
			if (i==0) {
				Bot bot1= new Bot("Reşit", this.deckToTakeCards, this.deckToGiveCards,this);
				this.players.add(bot1);
			}
			else if (i==1) {
				Bot bot2= new Bot("Hamza", this.deckToTakeCards, this.deckToGiveCards,this);
				this.players.add(bot2);
			}
			else if (i==2) {
				Bot bot3= new Bot("Doğan", this.deckToTakeCards, this.deckToGiveCards,this);
				this.players.add(bot3);
			}
			else if (i==3) {
				Bot bot4= new Bot("Aylanur", this.deckToTakeCards, this.deckToGiveCards,this);
				this.players.add(bot4);
			}
			else if (i==4) {
				Bot bot5= new Bot("Abdulrezzak", this.deckToTakeCards, this.deckToGiveCards,this);
				this.players.add(bot5);
			}
			else if (i==5) {
				Bot bot6= new Bot("Andrew", this.deckToTakeCards, this.deckToGiveCards,this);
				this.players.add(bot6);
			}
			else if (i==6) {
				Bot bot7= new Bot("Vahideh", this.deckToTakeCards, this.deckToGiveCards,this);
				this.players.add(bot7);
			}
			else if (i==7) {
				Bot bot8= new Bot("Attila", this.deckToTakeCards, this.deckToGiveCards,this);
				this.players.add(bot8);
			}
			else if (i==8) {
				Bot bot9= new Bot("Öznur", this.deckToTakeCards, this.deckToGiveCards,this);
				this.players.add(bot9);
			}
		}
	}
	
	
	
	

	/**
	 * Makes game ready to play by creating bots, preparing draw pile and discard pile etc.
	 * @param playScreenFrame to initialize this.playScreenFrame
	 */
	public void  gameReady(PlayScreenFrame playScreenFrame) {
		this.playscreenFrame=playScreenFrame;
		this.deckToTakeCards.createMainDeck();
		do {
			this.deckToGiveCards.addNumerousCardsToDeck(this.getDeckToTakeCards().takeTopCards(1));
		} while (!this.getDeckToGiveCards().getCards().getLast().getType().equals("number"));//!!!!NUMBER KART KONTROLÜ
		
		Player mainPlayer= new Player(this.getMainPlayerName(), deckToTakeCards, deckToGiveCards,this);
		this.setMainPlayer(mainPlayer);
		this.players.add(mainPlayer);
		this.makeBots();
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			player.startPosition(deckToTakeCards);	
		}
		this.sessionName="Session Name: "+mainPlayerName+" against "+this.botNumber+ " bots.";
	}
	
	
	
	
	/**
	 * This is the most important function in the Program.
	 * It makes bots play, and exists from the loop when it is main player's turn. After main player's turn, it starts again.
	 */
	public void gameLoop() {
		
		this.getPlayscreenFrame().getSkipButton().setEnabled(false);
		
		Boolean gameContunies=true;
		int i=this.turn;
		
		
		int indexOfAfterMainPlayer=this.getPlayers().indexOf(this.getMainPlayer());// Main player can only be first or last in the index. If it is the last player, then after it index 0 comes.
		if (indexOfAfterMainPlayer==0) {
			indexOfAfterMainPlayer=1;
		}
		else {
			indexOfAfterMainPlayer=0;
		}
		
		
		
		
		
		this.getPlayscreenFrame().revalidate();
		while (gameContunies) {
			if (i>(this.botNumber)) {// bot number is equal to length of players list.
				i=0;
			}
			if (i<0) {
				i=this.botNumber;
			}
			
			Player playerToPlay=this.players.get(i);
			Card topCard=this.getDeckToGiveCards().getCards().getLast();
			

			
			if (topCard.isActive()) {  // Checking action cards and wild card.
				if (topCard.getType().equals("skip")) {
					i++;
				}
				else if (topCard.getType().equals("+2")) {
					i++;
					playerToPlay.takeTwo();
					
				}
				else if (topCard.getType().equals("+4")) {
					i++;
					playerToPlay.takeFour();
				}
				else if (topCard.getType().equals("reverse")) {
					this.way=!way;
					Collections.reverse(this.players);
					i=this.players.indexOf(playerToPlay);
					i=i+1;
				}
				topCard.setActive(false);// so that a card only affects one player.
				continue;
			} 
			if (i>(this.botNumber)) {
				i=0;
			}
			if (i<0) {
				i=this.botNumber;
			}
			
			
			if (players.get(i).equals(this.getMainPlayer())) {
				this.mainPlayer.mainPlayersTurn();
				gameContunies=false;
			}
			else {
				((Bot)playerToPlay).botPlays();
				
				if (i==indexOfAfterMainPlayer && !this.getPlayscreenFrame().getSayUno().isSelected()&&this.getMainPlayer().getMyDeck().getCards().size()==1) { // If main player does not say uno, he will be punished.
					this.mainPlayer.takeTwo();}
				
			}
			
			if (playerToPlay.hasWon()) {
				somebodyWons(playerToPlay);
				break;
			}
			
			i++;
			this.setTurn(i);// if we exit from loop, when loop restarts we can know whose turn it is.
			
			this.playscreenFrame.revalidate();
			this.playscreenFrame.repaint();
			
		}
	}
	
	
	
	/**
	 * When somebody won a game, this function works. It updates users istatistics, which is in leaderboard.
	 * @param winner
	 */
	public void somebodyWons(Player winner) {
		this.events.add(winner.getName()+" has won!");
		this.playscreenFrame.dispose();
		NewGameFrame newGameFrame= new NewGameFrame(mainPlayerName);
		
		
		String username=this.mainPlayerName;
		int winN=0;
		int loseN=0;
		int totalGames=1;
		int totalScore=0;
		if (winner.equals(this.getMainPlayer())) {
			winN++;
			totalScore+=this.totalScoreCalculater();
			IstatisticsFrame istatisticsFrame= new IstatisticsFrame("You won!");
		}
		else {
			loseN++;
			IstatisticsFrame istatisticsFrame= new IstatisticsFrame(winner.getName()+" has won!");
		}
		
		
	    try {
	            BufferedReader reader = new BufferedReader(new FileReader("leaderboard.txt"));
	            String newRecord="";
	            String line;
	            String upperText="";// is the istatistics upper in the text file. If our character's istatistics does not exist, it will be all lines in text file.
	            String resttext="";// lines after our characters istatistics. If our character's istatistics does not exist, it will be empty.
	            
	            boolean found = false;
	            while ((line = reader.readLine()) != null) {
	                String[] parts = line.split(" , ");
	                if (found) {
	                    resttext=resttext+parts[0]+" , "+parts[1]+" , "+parts[2]+" , "+parts[3]+" , "+parts[4]+"\n";

					}
	                if (parts.length==5&& !found) {
	                	//parts update
	                	if (parts[0].equals(username)) {
		                    parts[1]=Integer.toString(Integer.parseInt(parts[1])+winN);

		                    parts[2]=Integer.toString(Integer.parseInt(parts[2])+loseN);
		                    parts[3]=Integer.toString(Integer.parseInt(parts[3])+totalGames);
		                    parts[4]=Integer.toString(Integer.parseInt(parts[4])+totalScore);
			            	newRecord=username+" , "+parts[1]+" , "+parts[2]+" , "+parts[3]+" , "+parts[4]+"\n";//update our characters record.
			            	upperText=upperText+newRecord;
			            	
		                    found = true;
						}
	                	else {
	                    upperText=upperText+parts[0]+" , "+parts[1]+" , "+parts[2]+" , "+parts[3]+" , "+parts[4]+"\n";
	                } 
	                	}
	            }
	            
	            reader.close();
	            
	            BufferedWriter writer = new BufferedWriter(new FileWriter("leaderboard.txt"));
	            if (!found) {
	            	writer.newLine();
	            	newRecord=username+" , "+Integer.toString(winN)+" , "+Integer.toString(loseN)+" , "+Integer.toString(totalGames)+" , "+Integer.toString(totalScore)+"\n";
	            	upperText=upperText+newRecord;//add to the end.
	            }


	            upperText=upperText+resttext;//if found, our updated character istatistics are already in the end of uppertext.
	            writer.write(upperText);
	            writer.close();
	            }
	    catch (Exception e) {
				e.printStackTrace();
			}
	    this.updateGameIstatistics();
	        }
		
		
	
	public int totalScoreCalculater() {
		int tot=0;
		for (Player player : players) {
			if (!player.equals(this.getMainPlayer())) {
				tot+=player.getMyDeck().getTotalScoreOfDeck();
			}
		}
		return tot;
	}
	
	
	/**
	 * It adds finished game's events to the AllGameIstatistics.txt
	 */
	public void updateGameIstatistics() {
		BufferedWriter updater;
		try {
			updater = new BufferedWriter(new FileWriter("AllGameIstatistics.txt",true));
			updater.write("\n\n\n"+this.getSessionName()+"\n");
			for (String string : events) {
				updater.write("\n"+string);
			}
			updater.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * Saves all game to a text file.
	 */
	public void save(String filename) {
		BufferedWriter writer;
		
		try {
			writer= new BufferedWriter(new FileWriter(filename,true));
			
			writer.write(this.getMainPlayerName()+"\n");
			writer.write(this.getBotNumber()+"\n");
			writer.write(this.getWay()+"\n");
			
			writer.write("deckToGiveCards:\n");
			writer.close();// closing so changes occur in text file.
			writer= new BufferedWriter(new FileWriter(filename,true));// reopening because we will write new things after this.getDeckToGiveCards().save(filename)
			this.getDeckToGiveCards().save(filename); 
			writer.write("TTT\n");
			writer.write("deckToTakeCards:\n");
			writer.close();
			writer= new BufferedWriter(new FileWriter(filename,true));
			this.getDeckToTakeCards().save(filename);
			writer.write("TTT\n");
			writer.write("Player's cards:\n");
			writer.close();
			writer= new BufferedWriter(new FileWriter(filename,true));
			this.getMainPlayer().getMyDeck().save(filename);
			writer.write("TTT\n");
			
			ArrayList<Bot> bots= new ArrayList<>();
			for (Player player : this.getPlayers()) {
				if (player instanceof Bot) {
					bots.add((Bot)player);
				}
			}
			for (int i = 0; i < bots.size(); i++) {
				Bot bot = bots.get(i);
				writer.write("Bot"+(i)+"'s cards:\n");
				writer.close();
				writer= new BufferedWriter(new FileWriter(filename,true));
				bot.getMyDeck().save(filename);
				writer.write("TTT\n");

			}
			
			writer.write("events:\n");
			for (String line : this.getEvents()) {
				writer.write(line+"\n");
			}

			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * Loads game from a text file.
	 */
	public void  loadGame(String filename) {
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			this.mainPlayerName=reader.readLine();
			this.botNumber=Integer.parseInt(reader.readLine());
			
			
			this.players=new LinkedList<>();
			this.deckToGiveCards=new Deck(this);
			this.events=new ArrayList<>();
			
			line=reader.readLine();
			if (!line.equals("Counterclockwise\n")) {
				this.way=false;
				this.turn=this.getBotNumber();
			}
			else {
				this.way=true;
				this.turn=0;
			}
			if (reader.readLine().equals("deckToGiveCards:")) { //add cards to discard pile deck.
				while (!(line = reader.readLine()).equals("TTT") ) {
					String[] cardInfo=line.split(" tahir ");
					
					if (Card.stringToColor(cardInfo[0]).equals(Color.black)) {// if it is a wildcard.
						WildCard newCard= new WildCard(Card.stringToColor(cardInfo[0]), cardInfo[1], Integer.parseInt(cardInfo[2]),this);
						this.deckToGiveCards.addToDeck(newCard);
					}
					else {
						Card newCard= new Card(Card.stringToColor(cardInfo[0]), cardInfo[1], Integer.parseInt(cardInfo[2]),this);
						this.deckToGiveCards.addToDeck(newCard);
					}	
				}
			}
			if (reader.readLine().equals("deckToTakeCards:")) {
				while (!(line = reader.readLine()).equals("TTT") ) {
					String[] cardInfo=line.split(" tahir ");
					if (Card.stringToColor(cardInfo[0]).equals(Color.black)) {
						WildCard newCard= new WildCard(Card.stringToColor(cardInfo[0]), cardInfo[1], Integer.parseInt(cardInfo[2]),this);
						this.deckToTakeCards.addToDeck(newCard);
					}
					else {
						Card newCard= new Card(Card.stringToColor(cardInfo[0]), cardInfo[1], Integer.parseInt(cardInfo[2]),this);
						this.deckToTakeCards.addToDeck(newCard);
					}	
				}
			}
			Player mainPlayer= new Player(this.getMainPlayerName(), deckToTakeCards, deckToGiveCards,this);
			this.setMainPlayer(mainPlayer);
			if (reader.readLine().equals("Player's cards:")) {
				while (!(line = reader.readLine()).equals("TTT") ) {
					String[] cardInfo=line.split(" tahir ");
					if (Card.stringToColor(cardInfo[0]).equals(Color.black)) {
						WildCard newCard= new WildCard(Card.stringToColor(cardInfo[0]), cardInfo[1], Integer.parseInt(cardInfo[2]),this);
						mainPlayer.getMyDeck().addToDeck(newCard);
					}
					else {
						Card newCard= new Card(Card.stringToColor(cardInfo[0]), cardInfo[1], Integer.parseInt(cardInfo[2]),this);
						mainPlayer.getMyDeck().addToDeck(newCard);
					}	
				}
			}
			
			this.makeBots();
			
			this.setMainPlayer(mainPlayer);
			this.players.add(mainPlayer);
			
			for (int i = 0; i < this.players.size()-1; i++) {
				Player player = players.get(i);
				if (reader.readLine().equals("Bot"+i+"'s cards:")) {
					while (!(line = reader.readLine()).equals("TTT") ) {
						String[] cardInfo=line.split(" tahir ");
						if (Card.stringToColor(cardInfo[0]).equals(Color.black)) {
							WildCard newCard= new WildCard(Card.stringToColor(cardInfo[0]), cardInfo[1], Integer.parseInt(cardInfo[2]),this);
							player.getMyDeck().addToDeck(newCard);
						}
						else {
							Card newCard= new Card(Card.stringToColor(cardInfo[0]), cardInfo[1], Integer.parseInt(cardInfo[2]),this);
							player.getMyDeck().addToDeck(newCard);
						}	
					}
				}
			}
			
			if (!way) {
				Collections.reverse(this.players);
			}
			
			reader.readLine();
			while ((line = reader.readLine()) != null) {
				this.events.add(line);
			}
			
			
			
			this.sessionName="Session Name: "+mainPlayerName+" against "+this.botNumber+ " bots.";
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
			
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	}
	

