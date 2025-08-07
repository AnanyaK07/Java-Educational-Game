/// Ananya Kotla Wrote the Whole File

import java.awt.Color;
import java.awt.Font;

import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class StartGame extends JPanel /// Ananya Kotla
{
	private int points; //The ampunt of oints the user has earned throughout the game
	private JButton thoracicButton; //The button to go to the thoracic dissection
	private JButton abdominalButton; //The button to go to the abdominal dissection
	private JLabel guideUser; //Label at the bottom ot guide users
	private boolean[] completeDissections;//Checks if a dissection has been complete
	
	/* Intialize variables that were sent in as well as fv's.
	 * set the Layout ot a BorderLayout and background color
	 * Created the title using JLabel and added it to a FlowLayout panel
	 * to change the postition of the JLabel. Then added that panel to 
	 * BorderLayout.North. Created the button to go back to the home
	 * page. Created a JLabel with the an instruction. That JLabel and the
	 * goBackHome button are added to a gridLayout with 2 rows and one column.
	 * It was added to the south of the Panel. Added the ActionListener
	 * for the button.Creates a gridLayout (3 columns and one row), where 
	 * three of the buttons are added, which are the tool Button, abdominal
	 * dissection button and Thoracic dissection button. The gridLayout 
	 * panel is added to the center of the orginial panel. 
	 */
	public StartGame(CardLayout homeCardsIn, MenuHolder mhIn, HomePanelHolder hphIn, 
					 CardLayout menuCardsIn, FileAdder faIn, JFrame frameIn)
    {
		CardLayout homeCards = homeCardsIn;
		CardLayout menuCards = menuCardsIn;
		MenuHolder mh = mhIn;
		HomePanelHolder hph = hphIn;
		FileAdder fa = faIn;
		int points = 0;
		completeDissections = new boolean[2];
		JFrame frame = frameIn;
		
		setLayout(new BorderLayout());
        setBackground(new Color(255, 242, 227));
        GameButtonHandler gbh = new GameButtonHandler(homeCards, hph, mh, menuCards, fa, frame, this);
        
        JLabel menuTitle = new JLabel("Menu Page", JLabel.CENTER);
		menuTitle.setFont(new Font("Calibri", Font.BOLD, 60)); 
		menuTitle.setForeground(new Color(255, 109,156)); 
		JPanel menuLabel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		menuLabel.setBackground(new Color(255, 242, 227));
		menuLabel.add(menuTitle);
        
        JButton goBackHome = new JButton("EXIT");
        goBackHome.setBackground(new Color(255,208,215));
        goBackHome.setFont(new Font("Calibri", Font.BOLD, 40));
        goBackHome.setOpaque(true);
        goBackHome.setBorderPainted(false);
        goBackHome.setForeground(new Color(255, 109,156));
        goBackHome.setPreferredSize(new Dimension(150,60));
        JPanel homeButtonFix = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        homeButtonFix.setBackground(new Color(255, 242, 227));
        homeButtonFix.add(goBackHome);
        add(menuLabel, BorderLayout.NORTH);
        
        guideUser = new JLabel("Learn the tools before starting a dissection", JLabel.CENTER); 
        guideUser.setFont(new Font("Calibri", Font.BOLD, 30));
        guideUser.setForeground(new Color(255, 109,156));
        
        JPanel addToolLabel = new JPanel(new GridLayout(2,1));
        addToolLabel.setBackground(new Color(255, 242, 227));
        JPanel readjustToolLabel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        readjustToolLabel.setBackground(new Color(255, 242, 227));
        readjustToolLabel.add(guideUser);
        addToolLabel.add(readjustToolLabel);
        addToolLabel.add(homeButtonFix);
        add(addToolLabel, BorderLayout.SOUTH);
        goBackHome.addActionListener(gbh);
        
        JPanel addOptions = new JPanel(new GridLayout(1,3));
        addOptions.setBackground(new Color(255, 242, 227));
        JPanel adjustToolButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 150));
        adjustToolButton.setBackground(new Color(255, 242, 227));
        JButton toolButton = new JButton("Tools");
        toolButton.setBackground(new Color(255,208,215));
        toolButton.setFont(new Font("Calibri", Font.BOLD, 23));
        toolButton.setForeground(new Color(255, 109,156));
        toolButton.setOpaque(true);
        toolButton.setBorderPainted(false);
        toolButton.setPreferredSize(new Dimension(300,100));
        adjustToolButton.add(toolButton);
        toolButton.addActionListener(gbh);

		JPanel adjustAbdominalButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 150));
		adjustAbdominalButton.setBackground(new Color(255, 242, 227));
		abdominalButton = new JButton("Abdominal Dissection");
		abdominalButton.setBackground(new Color(255,208,215));
        abdominalButton.setFont(new Font("Calibri", Font.BOLD, 23));
        abdominalButton.setForeground(new Color(255, 109,156));
        abdominalButton.setOpaque(true);
        abdominalButton.setBorderPainted(false);
        abdominalButton.setEnabled(false);
        abdominalButton.setPreferredSize(new Dimension(300,100));
        adjustAbdominalButton.add(abdominalButton);
        abdominalButton.addActionListener(gbh);

		JPanel adjustThoracicButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 150));
		adjustThoracicButton.setBackground(new Color(255, 242, 227));
		thoracicButton = new JButton("Thoracic Dissection");
        thoracicButton.setBackground(new Color(255,208,215));
        thoracicButton.setFont(new Font("Calibri", Font.BOLD, 23));
        thoracicButton.setForeground(new Color(255, 109,156));
        thoracicButton.setOpaque(true);
        thoracicButton.setBorderPainted(false);
        thoracicButton.setEnabled(false);
        thoracicButton.setPreferredSize(new Dimension(300,100));
		adjustThoracicButton.add(thoracicButton);
        thoracicButton.addActionListener(gbh);
        
        addOptions.add(adjustToolButton);
        addOptions.add(adjustAbdominalButton);
        addOptions.add(adjustThoracicButton);
        
        add(addOptions, BorderLayout.CENTER);
        
	}
	
	/* Saves points everytime it changes
	 */
	public void savePoints(int pointsIn)
	{
		points = pointsIn;
	}
	
	/* Returns points to other panels
	 */
	public int returnPoints() 
	{
		return points;
	}
	
	/* Once the tool quiz is complete, it will run this method
	 * so that the user can now play one of the dissections.
	 */
	public void enableDissectionButtons()
	{
		abdominalButton.setEnabled(true);
		thoracicButton.setEnabled(true);
		guideUser.setText("Click on a dissection to play!");
	}
	
	/* This checks if one or both dissections are completed. If they are
	 * that button will be disabled.
	 */
	public void setDissectionCompletion(int dissectNum)
	{
		completeDissections[dissectNum - 1] = true;
		if(completeDissections[0] == true && completeDissections[1] == true)
		{
			abdominalButton.setEnabled(false);
			thoracicButton.setEnabled(false);
			guideUser.setText("You have completed the game!");
		}
		else if(completeDissections[0] == true)
		{
			abdominalButton.setEnabled(false);
			guideUser.setText("Play the Thoracic Dissection now!");
		}
		else if(completeDissections[1] == true)
		{
			thoracicButton.setEnabled(false);
			guideUser.setText("Play the Abdominal Dissection now!");
		}
	}
	
	/* Returns the rray with the ostored information about dissection 
	 * completion
	 */
	public boolean[] returnDissectionCompletion()
	{
		return completeDissections;
	}
}

class GameButtonHandler implements ActionListener /// Ananya Kotla
{
	private HomePanelHolder hph; // The instance of class where cardLayout for home panel is stored.
    private CardLayout homeCards; // The cards for home panel.
    private MenuHolder mh; // The instance of class where cardLayout for menu panel is stored.
    private CardLayout menuCards; // The cards for menu panel.
    private FileAdder fa; // Instance of fileAdder where pictures and file IO are created.
    private JFrame frame; // Instance of Main JFrame
    private StartGame sg; //Instance of StartGame class, where menuPage is shown
	public GameButtonHandler(CardLayout homeCardsIn, HomePanelHolder hphIn, 
							MenuHolder mhIn, CardLayout menuCardsIn, FileAdder faIn, 
							JFrame frameIn, StartGame sgIn)
	{
		homeCards = homeCardsIn;
        hph = hphIn;
        mh = mhIn;
        menuCards = menuCardsIn;
        fa = faIn;
        frame = frameIn;
        sg = sgIn;
	}
	
	/* If the exit button is pressed, then it will take you back to the
     * first/home page. If tools is pressed then it will take you to the 
     * tool page. If one of the other dissections is selected it will create
     * an instance of the Dissection class and run it. There will be 5 variables
     * sent in to that class, menuCards, fileAdder, menuHolder, the JFrame,
     * StartGame class isntance and number based on what dissection the user picks.
     */
	public void actionPerformed(ActionEvent evt)
    {
		String buttonsName = new String("");
		buttonsName = evt.getActionCommand();
		
        if(buttonsName.equals("EXIT"))
        {
            homeCards.show(this.hph, "First");
        }
        else if(buttonsName.equals("Tools"))
        {
			menuCards.show(this.mh, "Tools");
		}
		else if(buttonsName.equals("Abdominal Dissection") || buttonsName.equals("Thoracic Dissection"))
        {
			if(buttonsName.equals("Abdominal Dissection"))
			{
				Dissection disPanel = new Dissection(menuCards,fa, mh, frame, sg, 1);
				mh.add(disPanel, "Dissection");
			}
			else
			{
				Dissection disPanel = new Dissection(menuCards,fa, mh, frame, sg, 2);
				mh.add(disPanel, "Dissection");
			}
			menuCards.show(this.mh, "Dissection");
		}
		
    }
}
