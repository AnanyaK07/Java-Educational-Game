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
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class TeachUserAnswers extends JPanel  /// Ananya Kotla 
{
	private String[] explainAnswers; // String array that stores explanations for each wrong answer.
	private JLabel term; // The term that was seleted when the user got a wrong answer.
	private JLabel question; // The question that was seleted when the user got a wrong answer.
	private JLabel correctAnswer; // The correct answer that the user didn't select.
	private JLabel userAnswer; // The wrong answer that the user selected.
	private int numOfCorrection; // The array index of explainAnswers
	
	/* Initalizes all field variables as wells as the vars that were
	 * sent through the parameters. Creates the title as well as 
	 * the bottom panel buttons (next and exit buttons). The middle panel
	 * is where the String array will be shown. There are four parts
	 * that are stored for each wrong question. The term, questions,
	 * the wrong answer the user picked, and the right answer + explanation
	 * A button handler class is also created and action listeners are 
	 * created for button components.
	 */
	public TeachUserAnswers(MenuHolder mhIn, CardLayout menuCardsIn, 
							String[] explainAnswersIn, int checkComplete, 
							int pointIn, FileAdder faIn)
	{
		MenuHolder mh = mhIn;
		CardLayout menuCards = menuCardsIn;
		explainAnswers = explainAnswersIn;
		numOfCorrection = 0;
		int complete = checkComplete;
		int point = pointIn;
		FileAdder fa = faIn;
		setLayout(new BorderLayout());
		setBackground(new Color(255, 116, 166));
		
		JPanel UfixTitle = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        UfixTitle.setBackground(new Color(255, 116, 166));
        JLabel teachUserTitle = new JLabel("Lets See What You Got Incorrect!", JLabel.CENTER);
        teachUserTitle.setFont(new Font("Calibri", Font.BOLD, 40));
        teachUserTitle.setForeground(Color.WHITE);
        UfixTitle.add(teachUserTitle);
        add(UfixTitle, BorderLayout.NORTH);
        
        JPanel middlePanel = new JPanel (new FlowLayout(FlowLayout.CENTER, 700, 40));
        middlePanel.setBackground(new Color(255, 116, 166));
		term = new JLabel();
		term.setFont(new Font("Calibri", Font.BOLD, 28));
        term.setForeground(Color.WHITE);
        middlePanel.add(term);
		question = new JLabel();
		question.setFont(new Font("Calibri", Font.BOLD, 28));
        question.setForeground(Color.WHITE);
        middlePanel.add(question);
        correctAnswer = new JLabel();
		correctAnswer.setFont(new Font("Calibri", Font.BOLD, 28));
        correctAnswer.setForeground(Color.WHITE);
        middlePanel.add(correctAnswer);
        userAnswer = new JLabel();
		userAnswer.setFont(new Font("Calibri", Font.BOLD, 28));
        userAnswer.setForeground(Color.WHITE);
        middlePanel.add(userAnswer);
        addCorrections(0);
        add(middlePanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new GridLayout(1,2));
        JPanel addNextButton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        addNextButton.setBackground(new Color(255, 116, 166));
		JButton nextButton = new JButton("NEXT");
        nextButton.setBackground(new Color(255, 178, 204));
        nextButton.setFont(new Font("Calibri", Font.BOLD, 35));
        nextButton.setOpaque(true);
        nextButton.setBorderPainted(false);
        nextButton.setForeground(Color.WHITE);
        nextButton.setPreferredSize(new Dimension(150, 60));
        addNextButton.add(nextButton);
        
        JPanel addExitButton = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        addExitButton.setBackground(new Color(255, 116, 166));
        JButton goBackMenu = new JButton("EXIT");
        goBackMenu.setBackground(new Color(255, 178, 204));
        goBackMenu.setFont(new Font("Calibri", Font.BOLD, 40));
        goBackMenu.setOpaque(true);
        goBackMenu.setBorderPainted(false);
        goBackMenu.setForeground(Color.WHITE);
        goBackMenu.setPreferredSize(new Dimension(150, 60));
        addExitButton.add(goBackMenu);
        addExitButton.add(goBackMenu);
        
        bottomPanel.add(addExitButton);
        bottomPanel.add(addNextButton);
        add(bottomPanel, BorderLayout.SOUTH);
        
        TeachUserAnswersButtonHandler tuabh = new TeachUserAnswersButtonHandler
				(this, mh, menuCards, explainAnswers, complete, point, fa);
        goBackMenu.addActionListener(tuabh);
        nextButton.addActionListener(tuabh);
	}
	
	/* Called by the button handler class. It sets all the JLabels
	 * texts, which are part of the middle panel, based on what num is
	 * in the string array.
	 */
	public void addCorrections(int numIn)
	{
		numOfCorrection = numIn;
		if(!(explainAnswers[numOfCorrection].equals(null)))
		{
			term.setText(explainAnswers[numOfCorrection]);
			question.setText(explainAnswers[numOfCorrection + 1]);
			correctAnswer.setText(explainAnswers[numOfCorrection + 2]);
			userAnswer.setText(explainAnswers[numOfCorrection + 3]);
			
		}
	}
	
	/* Returns the number of corrections
	 */
	public int numOfCorrections()
	{
		return numOfCorrection;
	}	
}

class TeachUserAnswersButtonHandler implements ActionListener /// Ananya Kotla 
{
	private TeachUserAnswers tua; // Instance of class TeachUserAnswers.
	private MenuHolder mh; // Instance of class MenuHolder.
	private CardLayout menuCards; // Cardlayout for the cards in menu page.
	private String[] explainAnswers; // String array that stores explanations.
	private int numOfNextCorrect; // Based on the array index of explainAnswers.
	private int complete; // int that checks if user has completed the game.
	private int points; // Points the user has earned.
	private FileAdder fa; // Instance of class FileAdder.
	public TeachUserAnswersButtonHandler(TeachUserAnswers tuaIn, MenuHolder mhIn, 
						CardLayout menuCardsIn, String[] explainAnswersIn, 
						int completeIn, int pointsIn, FileAdder faIn)
	{
		tua = tuaIn;
		mh = mhIn;
		menuCards = menuCardsIn;
		explainAnswers = explainAnswersIn;
		numOfNextCorrect = 0;
		complete = completeIn;
		points = pointsIn;
		fa = faIn;
	}
	
	/* Checks if any of the buttons have been clicked. If exit has been
	 * clicked than it goes back to the menu page panel
	 * Otherwise if next has been clicked, it first gets the array index
	 * that its supposed to be on. Based on that it checks if the user completes 
	 * the game, has completed a dissection, or has completed part of quiz.
	 * From there it changes the cardlayout or creates an instance of 
	 * a different class and runs it.
	 */
	public void actionPerformed(ActionEvent evt) 
	{
		String buttonNames2 = new String("");
		buttonNames2 = evt.getActionCommand();
		if(buttonNames2.equals("EXIT"))
        {
			 menuCards.show(this.mh, "gameStart");
		}
		else if(buttonNames2.equals("NEXT"))
		{
			numOfNextCorrect  = tua.numOfCorrections();
			if(numOfNextCorrect == 8)
			{
				if(complete == 1)
				{
					AddUserName aud = new AddUserName(fa, points, mh, menuCards);
					mh.add(aud, "UserName");
					menuCards.show(this.mh, "UserName");
				}
				else if(complete == 2)
				{
					menuCards.show(this.mh, "gameStart");
				}
				else if(complete == 3)
				{
					 menuCards.show(this.mh, "GameQuizSelect");
				}
			}
			else if(explainAnswers[numOfNextCorrect + 4] == null)
			{
				
				 if(complete == 1)
				 {
					AddUserName aud = new AddUserName(fa, points, mh, menuCards);
					mh.add(aud, "UserName");
					menuCards.show(this.mh, "UserName");
				 }
				 else if(complete == 2)
				 {
					menuCards.show(this.mh, "gameStart");
				 }
				 else if(complete == 3)
				 {
					 menuCards.show(this.mh, "GameQuizSelect");
				 }
			}
			else
			{	
				numOfNextCorrect = tua.numOfCorrections() + 4;
				tua.addCorrections(numOfNextCorrect);
			}
		}
	}
}
