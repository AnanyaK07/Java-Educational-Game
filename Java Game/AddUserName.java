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
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;

class AddUserName extends JPanel /// Ananya Kotla
{
	private JButton checkButton; // Used to see if user has typed name in.
	private PrintWriter scoresAdd; // Printwriter to add the user score.
	private int points; // the amount of points the user got from quiz.
	private JLabel showScore; // Shows score after user types in username
	
	/* Intializes all field variables as well as creates the Layout and sets
	 * background. Creates the check/submit button as well as the exit button.
	 * Adds both buttons to a south panel and adds that panel to 
	 * BorderLayout.SOUTH. Creates the title of the JPanel as well.
	 * Creates the textfield in where the user has to enter their name.
	 * Creates a button handler to check if the user has pressed any buttons
	 * and move further on. Calls the method from FileAdder where you create
	 * the printwriter and sets it equal to the fv printwriter in this class.
	 */
	public AddUserName(FileAdder faIn, int pointsIn, MenuHolder mhIn, CardLayout menuCardsIn) 
	{
		setBackground(new Color(255, 116, 166));
        setLayout(new BorderLayout());
        FileAdder fa = faIn;
        CardLayout menuCards = menuCardsIn;
		MenuHolder mh = mhIn;
        points = pointsIn;
        
        JPanel addCheckButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        addCheckButton.setBackground(new Color(255, 116, 166));
        checkButton = new JButton("SHOW SCORE");
        checkButton.setBackground(new Color(255, 178, 204));
        checkButton.setFont(new Font("Calibri", Font.BOLD, 30));
        checkButton.setOpaque(true);
        checkButton.setBorderPainted(false);
        checkButton.setForeground(Color.WHITE);
        checkButton.setPreferredSize(new Dimension(250, 60));
        checkButton.setVisible(true);
        addCheckButton.add(checkButton);
        
        JPanel southPanel = new JPanel(new BorderLayout());
		southPanel.setBackground(new Color(255, 116, 166));
        JPanel addButton = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        addButton.setBackground(new Color(255, 116, 166));
        JButton goBackMenu = new JButton("EXIT");
        goBackMenu.setBackground(new Color(255, 178, 204));
        goBackMenu.setFont(new Font("Calibri", Font.BOLD, 40));
        goBackMenu.setOpaque(true);
        goBackMenu.setBorderPainted(false);
        goBackMenu.setForeground(Color.WHITE);
        goBackMenu.setPreferredSize(new Dimension(150, 60));
        addButton.add(goBackMenu);
        
        southPanel.add(addButton, BorderLayout.EAST);
        southPanel.add(addCheckButton, BorderLayout.WEST);
        add(southPanel, BorderLayout.SOUTH);
        
        JPanel fixTitle = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        fixTitle.setBackground(new Color(255, 116, 166));
        JLabel gQuizTitle = new JLabel("ENTER USERNAME", JLabel.CENTER);
        gQuizTitle.setFont(new Font("Calibri", Font.BOLD, 40));
        gQuizTitle.setForeground(Color.WHITE);
        fixTitle.add(gQuizTitle);
        
        add(fixTitle, BorderLayout.NORTH);
        
        JPanel fixTextField = new JPanel(new FlowLayout(FlowLayout.CENTER, 300, 100));
        fixTextField.setBackground(new Color(255, 116, 166));
        JTextField nameEnter = new JTextField();
        nameEnter.setFont(new Font("Calibri", Font.BOLD, 40));
        nameEnter.setForeground(new Color(255, 116, 166));
        nameEnter.setPreferredSize(new Dimension(500, 60));
        showScore = new JLabel();
        showScore.setFont(new Font("Calibri", Font.BOLD, 40));
        showScore.setForeground(Color.WHITE);
        
        fixTextField.add(nameEnter);
        fixTextField.add(showScore);
        add(fixTextField, BorderLayout.CENTER);
        
        UserNameHandler unh = new UserNameHandler(nameEnter, this, mh, menuCards);
        checkButton.addActionListener(unh);
        goBackMenu.addActionListener(unh);
        nameEnter.addActionListener(unh);
        
        scoresAdd = fa.addScoresList();
	}
	
	/* Appends the scores.txt and adds the new name as well as the points
	 * stored. Closes the printwriter afterwards.
	 */
	public void addNameScore(String nameIn)
	{
		scoresAdd.println(nameIn + ": " + points);
		showScore.setText(nameIn + ": " + points);
		scoresAdd.close();
		checkButton.setText("SUBMIT");
	}
}

class UserNameHandler implements ActionListener /// Ananya Kotla
{
	private JTextField nameEnter; // Textfield where user enters their name
	private String name; // variable that stores the name that user entered.
	private AddUserName aun; // Instance of the class AddUserName to run one of its methods.
	private MenuHolder mh; // Instance of MenuHolder to change the card shown
	private CardLayout menuCards; // the menu panel card layout to change the card shown
	public UserNameHandler(JTextField nameEnterIn, AddUserName aunIn, MenuHolder mhIn,
								CardLayout menuCardsIn)
	{
		nameEnter = nameEnterIn;
		name = new String(" ");
		aun = aunIn;
		mh = mhIn;
		menuCards = menuCardsIn;
	}
	
	/* Action performed method to check if any of the buttons have been
	 * pressed. If Exit is pressed, then returns to the menu page, If the 
	 * user clicks on SHOW SCORE it shows their user name entered as well
	 * as their score. It changes the button text to submit. If 
	 * submit is pressed, then it will show the menu page.
	 */
	public void actionPerformed(ActionEvent evt) 
	{
		String interactNames = new String("");
		interactNames = evt.getActionCommand();
		
		if(interactNames.equals("EXIT"))
        {
            menuCards.show(this.mh, "gameStart");
        }
        else if(interactNames.equals("SUBMIT"))
        {
			menuCards.show(this.mh, "gameStart");
		}
		else if(interactNames.equals("SHOW SCORE"));
		{
			name = nameEnter.getText();
			aun.addNameScore(name);
		}
	}
}
