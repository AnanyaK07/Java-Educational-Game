/// Ananya Kotla Wrote the Whole File

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ToolsQuiz extends JPanel /// Ananya Kotla
{
	private JButton checkButton; // button to check user's answer
	private JButton nextButton; // button to move on with the quiz
	private JLabel instructUser; // Label that gives instructions to the user
	
	/* Creates layout and sets background.
	 * Intializes field vars as well as vars passed in through the
	 * constructor parameters.
	 * Makes all the buttons and adds actionListeners for them as well
	 * as adds them to the panel.
	 * Creates an instance of a class call QuizMatch and sends in two parameters
	 *  - Instance of ToolsQuiz and the JLabel field var.
	 * Creates instance of a handler class for actionPerformed
	 *  - Sends in menuCards, instance of menuHolder, instance of toolsQuiz
	 * 		and instance of quizMatch.
	 */
	public ToolsQuiz(CardLayout menuCardsIn, MenuHolder mhIn, StartGame sgIn)
	{
		CardLayout menuCards = menuCardsIn;
		MenuHolder mh = mhIn;
		setBackground(new Color(255, 116, 166));
		setLayout(new BorderLayout());
		StartGame sg = sgIn;
		
		JPanel addExitButton = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		addExitButton.setBackground(new Color(255, 116, 166));
		JButton goBackMenu = new JButton("EXIT");
		goBackMenu.setBackground(new Color(255, 232, 239));
        goBackMenu.setFont(new Font("Calibri", Font.BOLD, 40));
        goBackMenu.setOpaque(true);
        goBackMenu.setBorderPainted(false);
        goBackMenu.setForeground(new Color(255, 116, 166));
        goBackMenu.setPreferredSize(new Dimension(150, 60));
        addExitButton.add(goBackMenu);
        
        JPanel addNextButton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        addNextButton.setBackground(new Color(255, 116, 166));
		nextButton = new JButton("NEXT");
        nextButton.setBackground(new Color(255, 232, 239));
        nextButton.setFont(new Font("Calibri", Font.BOLD, 25));
        nextButton.setOpaque(true);
        nextButton.setBorderPainted(false);
        nextButton.setForeground(new Color(255, 116, 166));
        nextButton.setPreferredSize(new Dimension(150, 60));
        nextButton.setVisible(false);
        addNextButton.add(nextButton);
        
        JPanel addCheckButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        addCheckButton.setBackground(new Color(255, 116, 166));
        checkButton = new JButton("CHECK ANSWERS");
        checkButton.setBackground(new Color(255, 232, 239));
        checkButton.setFont(new Font("Calibri", Font.BOLD, 25));
        checkButton.setOpaque(true);
        checkButton.setBorderPainted(false);
        checkButton.setForeground(new Color(255, 116, 166));
        checkButton.setPreferredSize(new Dimension(300, 60));
        checkButton.setVisible(false);
        addCheckButton.add(checkButton);
        
        JPanel addInstructUser = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        addInstructUser.setBackground(new Color(255, 116, 166));
        instructUser = new JLabel("", JLabel.CENTER);
        instructUser.setFont(new Font("Calibri", Font.BOLD, 25));
        instructUser.setForeground(Color.WHITE);
        instructUser.setText("<html><center>Click on a term and its definition.<br>Click CHECK "
				+ "ANSWERS to see if its correct!<html><center>");
		addInstructUser.add(instructUser);
		
		QuizMatch qm = new QuizMatch(this, instructUser, sg);
		add(qm, BorderLayout.CENTER);
		ToolQuizButtonHandler tbh = new ToolQuizButtonHandler(menuCards, mh, qm, this);
		JPanel addButtons = new JPanel(new GridLayout(1,2));
        add(addInstructUser, BorderLayout.NORTH);
        addButtons.add(addExitButton);
        addButtons.add(addCheckButton);
        addButtons.add(addNextButton);
        add(addButtons, BorderLayout.SOUTH);
        nextButton.addActionListener(tbh);
        goBackMenu.addActionListener(tbh);
        checkButton.addActionListener(tbh);
	}
	
	/* This class sets checkbutton to visible*/
	public void enableSubmitButton()
	{
		checkButton.setVisible(true);
	}
	
	/* This class sets checkbutton not visible*/
	public void disableSubmitButton()
	{
		checkButton.setVisible(false);
	}
	
	/* This class sets nextbutton to visible*/
	public void enableNextButton()
	{
		nextButton.setVisible(true);
	}
	
	/* This class sets nextbutton not visible*/
	public void disableNextButton()
	{
		nextButton.setVisible(false);
		instructUser.setText("<html><center>Click on a term and it's definition.<br>Click CHECK "
				+ "ANSWERS to see if its correct!<html><center>");
	}
}

class QuizMatch extends JPanel /// Ananya Kotla
{
	private ToolsQuiz tq; // Instance of ToolsQuiz class
	private JButton tweezerLabel, scissorsLabel, probeLabel, scalpelLabel,
			tweezerDef, scissorsDef, probeDef, scalpelDef; // All buttons used in the game
	private int labelCounter; // Counter everytime a label button is selected.
	private int defCounter; // Counter everytime a defintion button is selected.
	private int combinedCounter; // Counter everytime for all buttons is selected.
	private JButton[] allButtons; // All the buttons are stored here
	private int[] slides; // A duplicate of the other array, used for placing JButtons.
	private int storeLabelAnswers; // stores what label button is part of the answer
	private int storeDefAnswers;// stores what Definition button is part of the answer
	private boolean checkingAnswer; //The other methods won't run if this is true
	private JLabel instructLabel; // Instructions to help the user with the quiz
	private StartGame sg;
	
	/* Sets layout and background as well as initializes field vars and vars that
	 * were sent through the parameters. Creates all 8 buttons and sends
	 * them into the handler class as well as the two different counters,
	 * the button array, instance of ToolsQuiz class, and the boolean
	 * Adds action listeners to all the buttons. 
	 * Uses a while loop to randomly the buttons when added to the panel
	 * 	Includes Math.random for the random effect
	 * 	Uses if-else statments to add all the buttons to the panel.
	 */
    public QuizMatch(ToolsQuiz tqIn, JLabel instructLabelIn, StartGame sgIn) 
    {
        setLayout(new GridLayout(4, 2));
        setBackground(new Color(255, 116, 166));
        tq = tqIn;
        labelCounter = 0;
        defCounter = 0;
        combinedCounter = 0;
        slides = new int[8];
        allButtons = new JButton[8];
        storeLabelAnswers = -1;
        storeDefAnswers = -1;
		checkingAnswer = false;
		instructLabel = instructLabelIn;
		sg = sgIn;
		
        JPanel fixtweezerLabel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        fixtweezerLabel.setBackground(new Color(255, 116, 166));
		tweezerLabel = new JButton("Tweezers");
		tweezerLabel.setFont(new Font("Calibri", Font.BOLD, 40));
		tweezerLabel.setForeground(Color.WHITE);
		tweezerLabel.setBackground(new Color(255, 178, 204));
		tweezerLabel.setOpaque(true);
		tweezerLabel.setBorderPainted(false);
		tweezerLabel.setPreferredSize(new Dimension(300,100));
		fixtweezerLabel.add(tweezerLabel);
		
		JPanel fixscissorsLabel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        fixscissorsLabel.setBackground(new Color(255, 116, 166));
		scissorsLabel = new JButton("Scissors");
		scissorsLabel.setFont(new Font("Calibri", Font.BOLD, 40));
		scissorsLabel.setForeground(Color.WHITE);
		scissorsLabel.setBackground(new Color(255, 178, 204));
		scissorsLabel.setOpaque(true);
		scissorsLabel.setBorderPainted(false);
		scissorsLabel.setPreferredSize(new Dimension(300,100));
		fixscissorsLabel.add(scissorsLabel);
		
		JPanel fixProbeLabel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        fixProbeLabel.setBackground(new Color(255, 116, 166));
		probeLabel = new JButton("Probe");
		probeLabel.setFont(new Font("Calibri", Font.BOLD, 40));
		probeLabel.setForeground(Color.WHITE);
		probeLabel.setBackground(new Color(255, 178, 204));
		probeLabel.setOpaque(true);
		probeLabel.setBorderPainted(false);
		probeLabel.setPreferredSize(new Dimension(300,100));
		fixProbeLabel.add(probeLabel);
		
		JPanel fixScalpelLabel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        fixScalpelLabel.setBackground(new Color(255, 116, 166));
		scalpelLabel = new JButton("Scalpel");
		scalpelLabel.setFont(new Font("Calibri", Font.BOLD, 40));
		scalpelLabel.setForeground(Color.WHITE);
		scalpelLabel.setBackground(new Color(255, 178, 204));
		scalpelLabel.setOpaque(true);
		scalpelLabel.setBorderPainted(false);
		scalpelLabel.setPreferredSize(new Dimension(300,100));
		fixScalpelLabel.add(scalpelLabel);
		
		JPanel fixtweezerDef = new JPanel(new FlowLayout(FlowLayout.CENTER));
        fixtweezerDef.setBackground(new Color(255, 116, 166));
		tweezerDef = new JButton("<html><center>This tool is used for picking" 
			 + "<br>something up or to hold<br>something "
			 + "in place.</center></html>");
		tweezerDef.setForeground(new Color(255, 116, 166));	 
		tweezerDef.setFont(new Font("Calibri", Font.BOLD, 20));
		tweezerDef.setBackground(new Color(255, 232, 239));
		tweezerDef.setOpaque(true);
		tweezerDef.setBorderPainted(false);
		tweezerDef.setPreferredSize(new Dimension(300,100));
		fixtweezerDef.add(tweezerDef);
		
		JPanel fixscissorsDef = new JPanel(new FlowLayout(FlowLayout.CENTER));
        fixscissorsDef.setBackground(new Color(255, 116, 166));
		scissorsDef = new JButton("<html><center>This tool is used "
		  +"to cut through bone.<html><center>");
		scissorsDef.setForeground(new Color(255, 116, 166));
		scissorsDef.setFont(new Font("Calibri", Font.BOLD, 20));
		scissorsDef.setBackground(new Color(255, 232, 239));
		scissorsDef.setOpaque(true);
		scissorsDef.setBorderPainted(false);
		scissorsDef.setPreferredSize(new Dimension(300,100));
		fixscissorsDef.add(scissorsDef);
		
		JPanel fixProbeDef = new JPanel(new FlowLayout(FlowLayout.CENTER));
        fixProbeDef.setBackground(new Color(255, 116, 166));
		probeDef = new JButton("<html><center>This tool is used to "
		 + "point<br>to parts on the pig.<html><center>");
		probeDef.setForeground(new Color(255, 116, 166));
		probeDef.setFont(new Font("Calibri", Font.BOLD, 20));
		probeDef.setBackground(new Color(255, 232, 239));
		probeDef.setOpaque(true);
		probeDef.setBorderPainted(false);
		probeDef.setPreferredSize(new Dimension(300,100));
		fixProbeDef.add(probeDef);
		
		JPanel fixScalpelDef = new JPanel(new FlowLayout(FlowLayout.CENTER));
        fixScalpelDef.setBackground(new Color(255, 116, 166));
		scalpelDef = new JButton("<html><center>This tool is used "
			+ "to cut through skin and soft tissue.<html><center>");
		scalpelDef.setForeground(new Color(255, 116, 166));
		scalpelDef.setFont(new Font("Calibri", Font.BOLD, 20));
		scalpelDef.setBackground(new Color(255, 232, 239));
		scalpelDef.setOpaque(true);
		scalpelDef.setBorderPainted(false);
		scalpelDef.setPreferredSize(new Dimension(300,100));
		fixScalpelDef.add(scalpelDef);
		
		MatchQuizButtonHandler mqbh = new MatchQuizButtonHandler(tweezerLabel, 
		scissorsLabel, probeLabel, scalpelLabel, tweezerDef, scissorsDef, probeDef, scalpelDef,
		allButtons, tq, this);
		
		tweezerLabel.addActionListener(mqbh);
		scissorsLabel.addActionListener(mqbh);
		probeLabel.addActionListener(mqbh);
		scalpelLabel.addActionListener(mqbh);
		tweezerDef.addActionListener(mqbh);
		scissorsDef.addActionListener(mqbh);
		probeDef.addActionListener(mqbh);
		scalpelDef.addActionListener(mqbh);
		
		int randomToolName = 0;
		int randomToolDef = 0;
		
		while(slides[0] == 0 || slides[1] == 0 || slides[2] == 0 || slides[3] == 0)
		{
			randomToolName = (int)(Math.random()* 4 + 1);
			randomToolDef = (int)(Math.random()* 4 + 1);
			if(slides[randomToolName - 1] == 0 && slides[randomToolDef + 3] == 0)
			{
				 if(randomToolName == 1)
				 {
					add(fixtweezerLabel);
				 }
				 else if(randomToolName == 2)
				 {
					add(fixscissorsLabel);
				 }
				 else if(randomToolName == 3)
				 {
					add(fixProbeLabel);
				 }
				 else if(randomToolName == 4)
				 {
					add(fixScalpelLabel);
				 }
				 slides[randomToolName - 1] = 1; 
		
				 if(randomToolDef == 1)
				 {
					add(fixtweezerDef);
				 }
				 else if(randomToolDef == 2)
				 {
					add(fixscissorsDef);
				 }
				 else if(randomToolDef == 3)
			     {
					add(fixProbeDef);
				 }
				 else if(randomToolDef == 4)
				 {
					add(fixScalpelDef);
				 }
				 slides[randomToolDef + 3] = 1;
			}
			 
		 }			
    }
    
    /* This method checks if the buttons are correct of if they are wrong
     * 	uses for loops to check which buttons have been pressed
     * 	check if certain buttons are pressed togther, then they are correct
     * Otherwise, user is wrong and has failed the quiz.
     * Changes colors of buttns to indicate if the user is correct or 
     * wrong. 
     */
    public void checkButtons()
    {
		checkingAnswer = true;
		for(int x = 0; x < 4; x++)
		{
			if(allButtons[x] != null && slides[x] != 0)
			{
				storeLabelAnswers = x;
				slides[x] = 0;
			}
		}
		for(int y = 4; y < 8; y++)
		{
			if(allButtons[y] != null && slides[y] != 0)
			{
				storeDefAnswers = y;
				slides[y] = 0;
			}
		}
		if((storeLabelAnswers == 0 && storeDefAnswers == 4) ||
				(storeLabelAnswers == 1 && storeDefAnswers == 5) ||
				(storeLabelAnswers == 2 && storeDefAnswers == 6) ||
				(storeLabelAnswers == 3 && storeDefAnswers == 7))
		{
			allButtons[storeLabelAnswers].setBackground(Color.GREEN);
			allButtons[storeDefAnswers].setBackground(Color.GREEN);
			allButtons[storeDefAnswers].setForeground(Color.WHITE);
			combinedCounter++;
			if(combinedCounter != 4)
			{
				instructLabel.setText("<html><center>Good Job!<br>Click NEXT to "
						+ "continue on with the quiz.<html><center>");
				tq.enableNextButton();
				labelCounter = 0;
				defCounter = 0;
			}
			else
			{
				instructLabel.setText("Yay! You have passed the Quiz. Click EXIT to return"
					+" back to the menu Page.");
				sg.enableDissectionButtons();
			}
		}
		else 
		{
			allButtons[storeLabelAnswers].setBackground(Color.RED);
			allButtons[storeDefAnswers].setBackground(Color.RED);
			allButtons[storeDefAnswers].setForeground(Color.WHITE);
			if(storeLabelAnswers == 0)
			{
				tweezerDef.setBackground(Color.GREEN);
				tweezerDef.setForeground(Color.WHITE);
			}
			else if(storeLabelAnswers == 1)
			{
				scissorsDef.setBackground(Color.GREEN);
				scissorsDef.setForeground(Color.WHITE);
			}
			else if(storeLabelAnswers == 2)
			{
				probeDef.setBackground(Color.GREEN);
				probeDef.setForeground(Color.WHITE);
			}
			else if(storeLabelAnswers == 3)
			{
				scalpelDef.setBackground(Color.GREEN);
				scalpelDef.setForeground(Color.WHITE);
			}
			instructLabel.setText("<html><center>Oh No! Looks like its incorrect!"
					+" You have failed the Quiz. The<br>correct answer for the term"
					+ " is in green. Click EXIT to redo the tools section.<html><center>");
		}
	}
	
	/* If the user is correct, then the buttons they chose will disapear
	 * when moving on. 
	 */
	public void disableButtons()
	{
		checkingAnswer = false;
		for(int x = 0; x < 8; x++)
		{
			if(allButtons[x] != null)
			{
				allButtons[x].setVisible(false);
			}
		}
		tq.disableSubmitButton();
		
	}
	
	/* Returns if the user is checking their answer
	 */
	public boolean getCheckingAnswer()
	{
		return checkingAnswer;
	}
	
	/* Return everytime user clicks submit button
	 */
	public int getCounter()
	{
		return combinedCounter;
	}
	
	/* Returns when user clicks on one of the label buttons when called
	 */
	public int returnLabelCounter()
	{
		return labelCounter;
	}
	
	/* Returns when user clicks on one of the Defition buttons when called
	 */
	public int returnDefCounter()
	{
		return defCounter;
	}
	
	/* Updates both counters when neccesary
	 */
	public void saveCounters(int def, int label)
	{
		labelCounter = label;
		defCounter = def;
	}
}
	
class ToolQuizButtonHandler implements ActionListener /// Ananya Kotla
{
	private CardLayout menuCards; // The cards for menu panel.
    private MenuHolder mh; // The instance of class where cardLayout for menu panel is stored.
	private QuizMatch qm; // The instance of the class QuizMatch.
	private ToolsQuiz tq; // The intance of the class ToolsQuiz
	
	public ToolQuizButtonHandler(CardLayout menuCardsIn, MenuHolder mhIn, 
								 QuizMatch qmIn, ToolsQuiz tqIn)
	{
		menuCards = menuCardsIn;
		mh = mhIn;
		qm = qmIn;
		tq = tqIn;
	}
	
	/* If exit button is pressed, it will take you back to the
	 * game page. If the check answers button is pressed, it will run
	 * a method to check if those answers are correct. If the next button is
	 * pressed, it will be disabled as well as the button for the answers before.
	 */
	public void actionPerformed(ActionEvent evt)
    {
		String buttonNames = new String("");
		buttonNames = evt.getActionCommand();
		
		int finish = qm.getCounter();
        if(buttonNames.equals("EXIT") && finish == 4)
        {
            menuCards.show(this.mh, "gameStart");
        }
        else if(buttonNames.equals("EXIT") && finish != 4)
        {
			menuCards.show(this.mh, "gameStart");
		}
        else if(buttonNames.equals("CHECK ANSWERS"))
        {
			qm.checkButtons();
		}
		else if (buttonNames.equals("NEXT"))
		{
			tq.disableNextButton();
			qm.disableButtons();
		}
    }
}
class MatchQuizButtonHandler implements ActionListener /// Ananya Kotla
{
	private JButton tweezerLabel, scissorsLabel, probeLabel, scalpelLabel,
			tweezerDef, scissorsDef, probeDef, scalpelDef; // The buttons which are part of the quiz
	private int labelCounter; // counter for when label buttons are pressed
	private int defCounter; // counter for when definition buttons are pressed
	private JButton[] allButtons;  // array where all the quiz buttons are stored
	private ToolsQuiz tq; // Instance of class ToolsQuiz
	private QuizMatch qm; //Instance of class QuizMatch
	public MatchQuizButtonHandler(JButton tweezerLabelIn, JButton scissorsLabelIn,
			JButton probeLabelIn, JButton scalpelLabelIn, JButton tweezerDefIn, 
			JButton scissorsDefIn, JButton probeDefIn, JButton scalpelDefIn,
			JButton[] allButtonsIn, ToolsQuiz tqIn, QuizMatch qmIn)
	{
		tweezerLabel = tweezerLabelIn;
		scissorsLabel = scissorsLabelIn;
		probeLabel = probeLabelIn;
		scalpelLabel = scalpelLabelIn;
		tweezerDef = tweezerDefIn;
		scissorsDef = scissorsDefIn;
		probeDef = probeDefIn;
		scalpelDef = scalpelDefIn;
		labelCounter = 0;
		defCounter = 0;
		allButtons = allButtonsIn;
		tq = tqIn;
		qm = qmIn;
	}
	/* Gets the source of which button is pressed
	 * If the answer is not being checked
	 * checks if any of the label buttons were pressed. If so
	 * changes the color so that is seem like you have selected that button
	 * You can switch between buttons to select a button.
	 * The same thing goes with the definition buttons. If one label button
	 * is selected as well as a definition button, then the submit button 
	 * will apear when calling the method and it will check if the answer is 
	 * correct.
	 */
	public void actionPerformed(ActionEvent evt) 
	{
        JButton clickedButton = (JButton) evt.getSource();
        boolean checkAnswer = qm.getCheckingAnswer();
        labelCounter = qm.returnLabelCounter();
        defCounter = qm.returnDefCounter();
        
        if(checkAnswer == false)
        {
			if(clickedButton == tweezerLabel || clickedButton == scissorsLabel ||
			   clickedButton == probeLabel || clickedButton == scalpelLabel)
			{
				if (labelCounter >= 1) 
				{
					for (int x = 0; x < 4; x++) 
					{ // Reset only the left buttons
						if (allButtons[x] != null) 
						{
							allButtons[x].setBackground(new Color(255, 178, 204));
							allButtons[x] = null;
						}
					}
					labelCounter = 0;
				}
				if (clickedButton == tweezerLabel)
				{
					tweezerLabel.setBackground(new Color(175, 227, 218));
					allButtons[0] = tweezerLabel;
				} 
				else if (clickedButton == scissorsLabel)
				{
					scissorsLabel.setBackground(new Color(175, 227, 218));
					allButtons[1] = scissorsLabel;
				}
				else if (clickedButton == probeLabel) 
				{
					probeLabel.setBackground(new Color(175, 227, 218));
					allButtons[2] = probeLabel;
				} 
				else if (clickedButton == scalpelLabel) 
				{
					scalpelLabel.setBackground(new Color(175, 227, 218));
					allButtons[3] = scalpelLabel;  
				} 
				labelCounter++;
			}
			else
			{
				if (defCounter >= 1) 
				{
					for (int x = 4; x < 8; x++) 
					{ // Reset only the right buttons
						if (allButtons[x] != null) 
						{
							allButtons[x].setBackground(new Color(255, 232, 239));
							allButtons[x] = null;
						}
					}
					defCounter = 0;		
				}
				// Update button arrays based on which button is clicked
				if (clickedButton == tweezerDef)
				{
					tweezerDef.setBackground(new Color(175, 227, 218));
					allButtons[4] = tweezerDef;
				} 
				else if (clickedButton == scissorsDef) 
				{
					scissorsDef.setBackground(new Color(175, 227, 218));
					allButtons[5] = scissorsDef;
				} 
				else if (clickedButton == probeDef) 
				{
					probeDef.setBackground(new Color(175, 227, 218));
					allButtons[6] = probeDef;		
				} 
				else if (clickedButton == scalpelDef) 
				{
					scalpelDef.setBackground(new Color(175, 227, 218));
					allButtons[7] = scalpelDef;
				}
				defCounter++;
			}
			if( defCounter == 1 && labelCounter == 1)
			{
				tq.enableSubmitButton();	
			}
			qm.saveCounters(defCounter, labelCounter);
		}
	}
}
