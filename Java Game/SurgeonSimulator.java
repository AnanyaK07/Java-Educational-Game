// SurgeonSimulator.java Game Project
/* This game is trying to teach the player the basics of making incisions 
 *  and identifying the organs after dissecting, which is part of a surgeon's job. 
 * Since the player is a beginner “surgeon”, they will not be dissecting a 
 * real human, but instead a fetal pig. The player will do 2 major dissections, 
 * which is covering two systems of the body. At the end of each dissection, t
 * he player will take a Quiz to see how much they actually know.
 */

/// Ananya Kotla Wrote the Whole File

import java.awt.Color;
import java.awt.Font;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SurgeonSimulator /// Ananya Kotla
{
    public SurgeonSimulator()
    {
    }

	/* Creates instance of the Home Panel class to call the method run.
	 */
    public static void main(String[] args)
    {
        SurgeonSimulator ss = new SurgeonSimulator();
        ss.run();
    }

	/* Creates the Jframe and sets all the proportions and details.
	 * Creates instane of class HomePanelHolder and stores adds it to the
	 * frame.
	 */
    public void run()
    {
        JFrame frame = new JFrame("Surgeon Simulator");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setResizable(false);
        HomePanelHolder hph = new HomePanelHolder(frame);
        frame.getContentPane().add(hph);
        frame.setVisible(true);
    }
}
    
class HomePanelHolder extends JPanel /// Ananya Kotla
{
	/* Creates the cardLayout for the HomePage. This includes...
	 *  the HomePage it self
	 *  the instructions page
	 *  the scorboard page
	 *  the game page
	 * Creates instances of each class and adds them to the card layout
	 *  Sends in the cards into each class in the parameter
	 *  In MenuHolder, the instance of itself also is sent in.
	 * Also sends in FileAdder which part of file IO when necessary
	 * as well as an instance of this class. It sends in the frame into
	 * menuHolder as well because we wil be using pop up messages
	 */
	public HomePanelHolder(JFrame frameIn)
	{
		CardLayout homeCards = new CardLayout();
		setLayout(homeCards);
		JFrame frame = frameIn;
		
		FileAdder fa = new FileAdder();
		StartHomePanel startHomePan = new StartHomePanel(homeCards, this);
		add(startHomePan, "First");
		Instructions instructPanel = new Instructions(homeCards, this, fa);
		add(instructPanel, "Instruction");
		Scoreboard scorePanel = new Scoreboard(homeCards, this, fa);
		add(scorePanel, "Score");
		MenuHolder menuPanel = new MenuHolder(homeCards, this, fa, frame);
		add(menuPanel, "Game");
	}
}

class StartHomePanel extends JPanel /// Ananya Kotla
{
	/* Constructor called from HomePanelHolder
	 * Layout is set to null and background color is set.
	 * Initializes variables that were sent in.
	 * Adds all the details to each button. Since it is a null layout
	 * the method setBounds is used for the position of the button.
	 * Adds each button to the panel as well as creates a JLabel for the
	 * title and it is added to the panel.
	 * For each button there is an Action Listener added to it.
	 */
    public StartHomePanel(CardLayout homeCardsIn, HomePanelHolder hphIn)
    {
        CardLayout homeCards = homeCardsIn;
        HomePanelHolder hph = hphIn;
        
        setLayout(null);
        setBackground(new Color(255, 242, 227));

		HomeButtonHandler hbh = new HomeButtonHandler(homeCards, hph);
		JButton instructionsButton = new JButton("How To Play");
        instructionsButton.setBackground(new Color(255,208,215));
        instructionsButton.setFont(new Font("Calibri", Font.BOLD, 35));
        instructionsButton.setForeground(new Color(255, 109,156));
        instructionsButton.setOpaque(true);
        instructionsButton.setBorderPainted(false);
        instructionsButton.setBounds(350,200,300,100);
        add(instructionsButton);
        instructionsButton.addActionListener(hbh);

		JButton scoreboardButton = new JButton("Scoreboard");
        scoreboardButton.setBackground(new Color(255,208,215));
        scoreboardButton.setFont(new Font("Calibri", Font.BOLD, 35));
        scoreboardButton.setForeground(new Color(255, 109,156));
        scoreboardButton.setOpaque(true);
        scoreboardButton.setBorderPainted(false);
        scoreboardButton.setBounds(350,350,300,100);
        add(scoreboardButton);
        scoreboardButton.addActionListener(hbh);
        
		JButton gameButton = new JButton("Start Game!");
        gameButton.setBackground(new Color(255,208,215));
        gameButton.setFont(new Font("Calibri", Font.BOLD, 35));
        gameButton.setForeground(new Color(255, 109,156));
        gameButton.setOpaque(true);
        gameButton.setBorderPainted(false);
        gameButton.setBounds(350,500,300,100);
        add(gameButton);
        gameButton.addActionListener(hbh);

        JLabel title = new JLabel("Surgeon Simulator", JLabel.CENTER);
        title.setFont(new Font("Calibri", Font.BOLD, 60));
        title.setForeground(new Color(255, 109,156));
        title.setBounds(150,10,700,100);
        add(title);

        JLabel subTitle = new JLabel("*(PIG EDITION)*", JLabel.CENTER);
        subTitle.setFont(new Font("Calibri", Font.BOLD, 30));
        subTitle.setForeground(new Color(255, 109,156));
        subTitle.setBounds(200,65,600,100);
        add(subTitle);

		JButton exitButton = new JButton("EXIT");
        exitButton.setBackground(new Color(255,208,215));
        exitButton.setFont(new Font("Calibri", Font.BOLD, 40));
        exitButton.setForeground(new Color(255, 109,156));
        exitButton.setOpaque(true);
        exitButton.setBorderPainted(false);
        exitButton.setBounds(840,608,150,60);
        add(exitButton);
        exitButton.addActionListener(hbh);
    }  
}

class HomeButtonHandler implements ActionListener /// Ananya Kotla
{
	private HomePanelHolder hph; // instance of class where cardLayout for home panel is stored
    private CardLayout homeCards; // cards for home panel
    
	public HomeButtonHandler(CardLayout homeCardsIn, HomePanelHolder hphIn)
	{
		homeCards = homeCardsIn;
        hph = hphIn;
	}
	/* Since ActionListeners were added to every button, if a button
     * was pressed then this method will be called.
     * If exitButton is pressed, it will close the program.
     * Any other button that is pressed will use the cards to switch
     * to a card in the layout. For example if gameButton is pressed
     * it will change the card called game, which has the game panel in 
     * it.
     */
    public void actionPerformed(ActionEvent evt)
    {
		String buttonName = new String("");
		buttonName = evt.getActionCommand();
        if(buttonName.equals("EXIT"))
        {
            System.exit(0);
        }

        else if(buttonName.equals("Scoreboard"))
        {
            homeCards.show(this.hph, "Score");
        }
        else if(buttonName.equals("Start Game!"))
        {
			homeCards.show(this.hph, "Game");
		}
		else if(buttonName.equals("How To Play"))
        {
			homeCards.show(this.hph, "Instruction");
		}
    }
}
