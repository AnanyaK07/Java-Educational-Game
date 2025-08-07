import java.awt.Color;
import java.awt.Font;

import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

class Scoreboard extends JPanel /// Ananya Kotla
{
	private JTextArea scoresAdd; // The JTextArea that contains all the scores and names.
    private Scanner scoresRead; // Reads the scores that are stored in a txt file.
    
    /* Sets home cards equal to the variable passed in. 
     * Uses String.format to add the beginning part of the Scores, in the JTextArea
     * Background and layout type(Border Layout) is set.
     * Creates the JLabel for the title of the panel.
     * Puts the JLabel in a seperate FlowLayout JPanel, so you can add a vgap and hgap.
     * That JPanel is added to the North of the Panel. Creates the exit
     * button as well and goes through a similar process. It's added ot the 
     * South of the panel though. Adds actionListener for the button.
     * calls two methods, which will read the text file with the
     * scores and add the scores to the JTextArea.
     * Adds the details to the JTextArea and JScrollPane. That is added 
     * to the center of the Panel. 
     */
	public Scoreboard(CardLayout homeCardsIn, HomePanelHolder hphIn, FileAdder faIn)
    {
		CardLayout homeCards = homeCardsIn;
		HomePanelHolder hph = hphIn;
        scoresRead = null;
        FileAdder fa = faIn;
        String spacing = String.format(" %-40s%s", "Usernames:", "Scores:");
        scoresAdd = new JTextArea(spacing);
        JScrollPane seeMoreScores = new JScrollPane(scoresAdd);
        
		setLayout(new BorderLayout());
        setBackground(new Color(255, 242, 227));
        
        JLabel panelTitle = new JLabel("Scoreboard", JLabel.CENTER);
		panelTitle.setFont(new Font("Calibri", Font.BOLD, 60)); 
		panelTitle.setForeground(new Color(255, 109,156)); 
		JPanel panelLabel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		panelLabel.setBackground(new Color(255, 242, 227));
		panelLabel.add(panelTitle);
        
        JButton goBackHome = new JButton("EXIT");
        goBackHome.setBackground(new Color(255,208,215));
        goBackHome.setFont(new Font("Calibri", Font.BOLD, 40));
        goBackHome.setForeground(new Color(255, 109,156));
        goBackHome.setOpaque(true);
        goBackHome.setBorderPainted(false);
        goBackHome.setPreferredSize(new Dimension(150,60));
        JPanel homeButtonFix = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        homeButtonFix.setBackground(new Color(255, 242, 227));
        homeButtonFix.add(goBackHome);

        add(panelLabel, BorderLayout.NORTH);
        add(homeButtonFix, BorderLayout.SOUTH);
        ScoreButtonHandler sbh = new ScoreButtonHandler(homeCards, hph);
        goBackHome.addActionListener(sbh);

        scoresRead = fa.lookAtFile("Scores.txt");
        addScores();

        scoresAdd.setEditable(false);
        scoresAdd.setLineWrap(true);
        scoresAdd.setWrapStyleWord(true);
        scoresAdd.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        scoresAdd.setForeground(new Color(255, 109,156));
        seeMoreScores.setPreferredSize(new Dimension(700, 400));
        JPanel scoresText = new JPanel(new FlowLayout(FlowLayout.CENTER));
        scoresText.setBackground(new Color(255, 242, 227));
        scoresText.add(seeMoreScores);
        add(scoresText, BorderLayout.CENTER);
	}
    
    /* D & I's 3 String variables an intializes them, 
     *  One to save each line of the text file in.
     *  One for names in the text file.
     *  One for the points in the text file.
     * Also D & I one int variable to check if there is a colon.
     * Uses a while loop
     *  readScore equals to the line.
     *  sets colonFInd to the index where the colon is. The colon 
     *  seperates the name from the points. 
     * 	Uses the method .substring(), to only add the name part to the 
     * 	var name and the score part ot the var points, using colonFind
     * Uses String.format so that all names are lining up and all points
     * are also lined up. Then it is added to the JTextArea.
     */
    public void addScores()
    {
        String readScore = new String("");
        String name = new String("");
        String points = new String("");
        int colonFind = 0;
        while (scoresRead.hasNextLine())
        {
            readScore = scoresRead.nextLine().trim();
            colonFind = readScore.indexOf(':');
            name = readScore.substring(0, colonFind);
            points = readScore.substring(colonFind + 2);
            String formattedLine = String.format(" %-40s%s", name, points);
            scoresAdd.append("\n" + formattedLine);
        }
    }
}
class ScoreButtonHandler implements ActionListener /// Ananya Kotla
{
	private HomePanelHolder hph; // instance of class where cardLayout for home panel is stored
    private CardLayout homeCards; // cards for home panel
    
	public ScoreButtonHandler(CardLayout homeCardsIn, HomePanelHolder hphIn)
	{
		homeCards = homeCardsIn;
        hph = hphIn;
	}
	
	/* If the exit button is pressed, then it will take you back to the
     * first/home page.
     */
	public void actionPerformed(ActionEvent evt)
    {
		String exitName = new String("");
		exitName = evt.getActionCommand();
		
        if(exitName.equals("EXIT"))
        {
            homeCards.show(this.hph, "First");
        }
    }
}
