/// Ananya Kotla Wrote the Whole File.

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
import java.util.Scanner;

class GameQuiz extends JPanel  /// Ananya Kotla 
{
	private String termSelected; // This used to see which organ was selected.
	private GameQuizSelect gqs; // Instance of GameQuizSelect class.
	private Scanner quizAnswersRead; // Scanner for reading the answers.
	private Scanner quizQuestionsRead; // Scanner for reading the questions.
	private String[] quizQuestions; // String array for storing the questions.
	private String[] quizAnswers; // String arrat for storing the answers.
	private JButton nextButton; // Next button to move on with the quiz.
	private JLabel question; // Used to show the current question.
	private JButton checkButton; // Used to see if the answer selected was correct or wrong.
	private int pointCounter; // Used to store the points the user earns.
	private JLabel points; // Used to show the points on the panel.
	
	/* Initalizes all field variables as well as the variables that were
	 * passed in through the constructor. Sets background and layout.
	 * Creates the title Panel, which includes the title, points, and 
	 * hearts. Creates the bottom panel, which includes exit button, 
	 * check answer button, and next button. Creates the middle panel,
	 * which includes the question as well as the the multiple choice answers
	 * as well. The multiple choice answers are made in a seperate class, and that
	 * class extends JPanel, so it is added to BorderLayout.CENTER to the middle
	 * panel. Creates the button handler class for this class and adds action
	 * listeners for all the buttons.
	 */
    public GameQuiz(CardLayout menuCardsIn, MenuHolder mhIn, GameQuizSelect gqsIn, 
					int endQuizIn, FileAdder faIn, Dissection disIn, StartGame sgIn,
					int dissectionNumIn) 
    {
		CardLayout menuCards = menuCardsIn;
		MenuHolder mh = mhIn;
		gqs = gqsIn;
		termSelected = new String("");
		quizAnswersRead = null;
		quizQuestionsRead = null;
		quizQuestions = new String[4];
		quizAnswers = new String[12];
		int endQuiz = endQuizIn;
		int dissectionNum = dissectionNumIn;
		FileAdder fa = faIn;
		StartGame sg = sgIn;
		Dissection dis = disIn;
        setBackground(new Color(255, 116, 166));
        setLayout(new BorderLayout());
        
        termSelected = gqs.getTerm();
        JPanel fixTitle = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        fixTitle.setBackground(new Color(255, 116, 166));
        JLabel gQuizTitle = new JLabel("<html><center>" + termSelected 
									+ "<br>Selected<html><center>", JLabel.CENTER);
        gQuizTitle.setFont(new Font("Calibri", Font.BOLD, 25));
        gQuizTitle.setForeground(Color.WHITE);
        fixTitle.add(gQuizTitle);

        JPanel fixPoints = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        fixPoints.setBackground(new Color(255, 116, 166));
        points = new JLabel();
        setPoints();
        points.setFont(new Font("Calibri", Font.BOLD, 20));
        points.setForeground(Color.WHITE);
        fixPoints.add(points);
        
        JPanel heartPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        heartPanel.setBackground(new Color(255, 116, 166));
        JLabel hearts = dis.getHearts();
        hearts.setForeground(new Color(255,208,215));
        hearts.setFont(new Font("Arial", Font.BOLD, 30));
        heartPanel.add(hearts);
        
        JPanel southPanel = new JPanel(new GridLayout(1, 3));
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

		JPanel addNextButton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        addNextButton.setBackground(new Color(255, 116, 166));
		nextButton = new JButton("NEXT");
        nextButton.setBackground(new Color(255, 178, 204));
        nextButton.setFont(new Font("Calibri", Font.BOLD, 35));
        nextButton.setOpaque(true);
        nextButton.setBorderPainted(false);
        nextButton.setForeground(Color.WHITE);
        nextButton.setPreferredSize(new Dimension(150, 60));
        nextButton.setVisible(false);
        addNextButton.add(nextButton);
        
        JPanel addCheckButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        addCheckButton.setBackground(new Color(255, 116, 166));
        checkButton = new JButton("CHECK ANSWER");
        checkButton.setBackground(new Color(255, 178, 204));
        checkButton.setFont(new Font("Calibri", Font.BOLD, 25));
        checkButton.setOpaque(true);
        checkButton.setBorderPainted(false);
        checkButton.setForeground(Color.WHITE);
        checkButton.setPreferredSize(new Dimension(300, 60));
        checkButton.setVisible(false);
        addCheckButton.add(checkButton);
        
        southPanel.add(addButton);
        southPanel.add(addCheckButton);
        southPanel.add(addNextButton);
        
        JPanel topPanel = new JPanel(new GridLayout(1,3));
        topPanel.add(heartPanel);
        topPanel.add(fixTitle);
        topPanel.add(fixPoints);
        add(topPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);
        
        quizQuestionsRead = fa.lookAtFile("DissectionQuestions.txt");
        quizAnswersRead	= fa.lookAtFile("DissectionAnsOptions.txt");
        AddQuestions();
        addAnswers();
        
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(new Color(255, 116, 166));
        JPanel fixQuestion = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        fixQuestion.setBackground(new Color(255, 116, 166));
        question = new JLabel(quizQuestions[0], JLabel.CENTER);
        question.setFont(new Font("Calibri", Font.BOLD, 30));
        question.setForeground(Color.WHITE);
        fixQuestion.add(question);

		MultipleChoice mc = new MultipleChoice(quizAnswers, this, gqs, fa, termSelected, quizQuestions);
        middlePanel.add(mc, BorderLayout.CENTER);
        middlePanel.add(fixQuestion, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        
        GameQuizButtonHandler gqbh = new GameQuizButtonHandler(mh, menuCards, this, mc, endQuiz, fa, gqs, sg, dissectionNum);
        nextButton.addActionListener(gqbh);
        goBackMenu.addActionListener(gqbh);
        checkButton.addActionListener(gqbh);
    }
    
    /* Sets the next button visible after check answer was clicked
     */
    public void setNextButtonVisible()
    {
		nextButton.setVisible(true);
	}
	
	/* Sets checkbutton visible once a multiple choice answer is selected.
	 */
	public void setCheckButtonVisible()
    {
		checkButton.setVisible(true);
	}
	
	/* Sets both buttons visible once the question changes.
	 */
	public void setButtonsNotVisible()
    {
		checkButton.setVisible(false);
		nextButton.setVisible(false);
	}
	
	/* Changes the question once the next button is clicked.
	 */
    public void changeQuestion(int questionCounter)
    {
		question.setText(quizQuestions[questionCounter]);
	}
	
	/*  Uses the scanner to add each question from the term that is 
	 * selected to an array and stores it each slot of the array.
	 */
    public void AddQuestions()
	{
	    String readQuestions = new String("");
	    int questionNum = 0;
	    readQuestions = quizQuestionsRead.nextLine().trim();
	    quizQuestions[questionNum] = readQuestions;
	    while(quizQuestionsRead.hasNextLine() && questionNum < 2)
	    {
			readQuestions = quizQuestionsRead.nextLine().trim();
			if(readQuestions.equals(""))
			{
				readQuestions = quizQuestionsRead.nextLine().trim();
				if(readQuestions.equalsIgnoreCase(termSelected + ":"))
				{
					questionNum++;
					readQuestions = quizQuestionsRead.nextLine().trim();
					quizQuestions[questionNum] = readQuestions;
				}
				else if(questionNum < 1)
				{
					questionNum++;
					quizQuestions[questionNum] = readQuestions;
				}
			}
		}
	}
	
	/* Calls a method from the GameQuizSelect which gets the points
	 * and sets the JLabel on the panel with the points.
	 */
	public void setPoints()
	{
		pointCounter = gqs.getPointCounter(); 
		points.setText("Points: " + pointCounter);
	}
	
	/*  Uses the scanner that reads the answers. Find the term that is selected
	 *  and adds those answers to an array. Each slot of the array is one multiple
	 *  choice answer.
	 */
	public void addAnswers()
	{
		String readAnswers = new String("");
	    int answerNum = -1;
	    int findStart = 0;
	    boolean add = true; 
	    while(quizAnswersRead.hasNextLine())
	    {
			readAnswers = quizAnswersRead.nextLine().trim();
			if(readAnswers.equalsIgnoreCase(termSelected + ":"))
			{			
				while(add == true)
				{
					readAnswers = quizAnswersRead.nextLine().trim();
					findStart = readAnswers.indexOf(')');
					if(findStart == -1)
					{
						quizAnswers[answerNum] = "<html><center>" + quizAnswers[answerNum] 
									+ "<br>" + readAnswers + "<html><center>";
					}
					else if(findStart != -1)
					{
						answerNum++;
						quizAnswers[answerNum] = "<html><center>" + 
								   readAnswers.substring(findStart + 1, 
								   readAnswers.length()).trim() + "<html><center>";
					}
					if(answerNum == 11)
					{
						add = false;
					}
				}
			}
	    }
	}
}
class MultipleChoice extends JPanel /// Ananya Kotla
{
	private JRadioButton correct, wrong1, wrong2, wrong3; // JRadioButton for each multiple choice
    private JPanel fixCorrect, fixWrong1, fixWrong2, fixWrong3; // Each panel for each radiobuttton
    private String[] quizAnswers; // Where the multiple choice answers are stored.
    private int answerSelected; // Which multiple choice answer is selected.
    private int[] checkWrong; // Stores which question and what answers the user selected wrong
    private int correctWrong; // Array space for checkWrong
    private int questionSelected; // Question that the panel is showing at the moment
    private boolean checkingAnswer; // to see if the user is checking their answer
    private ButtonGroup quizButtons; // the radiobuttons are in this button group
    private GameQuizSelect gqs; // Instance of class GameQuizSelect
    private GameQuiz gq; // Instance of class Game Quiz
    private FileAdder fa; // Intance of the class FileAdder
    private String termSelected; // Checks what term the user has selected
    private String[] quizQuestions; //Array the holds the quiz Questions
    private String[] explainWrongAnswers; // Store explanations for wrong answers
    private int numOfWrong; // Array spaces of explainWrongAnswers
    
    /* Initializes all field variables as well as the variables that were sent
     * in through the parameters. Creates all 4 JRadioButtons and flowLayout
     * panels for each one. Adds all of them to the button group. Creates
     * a button handler class for this class to see if any of the JRadioButtons
     * were selected.
     */
    public MultipleChoice(String[] quizAnswersIn, GameQuiz gqIn, GameQuizSelect gqsIn, FileAdder faIn, String termSelectedIn, String[] quizQuestionsIn)
    {
		gq = gqIn;
		gqs = gqsIn;
		fa = faIn;
		setBackground(new Color(255, 116, 166));
        setLayout(new GridLayout(2,2));
        checkingAnswer = false;
        checkWrong = new int[9];
        correctWrong = 0;
        numOfWrong = 0;
        quizQuestions = quizQuestionsIn;
        termSelected = termSelectedIn;
        explainWrongAnswers =  new String[12];
		correct = new JRadioButton();
        correct.setFont(new Font("Calibri", Font.BOLD, 25));
        correct.setOpaque(true);
        correct.setForeground(Color.WHITE);
        correct.setPreferredSize(new Dimension(400, 150));
        
		wrong1 = new JRadioButton();
        wrong1.setFont(new Font("Calibri", Font.BOLD, 25));
        wrong1.setOpaque(true);
        wrong1.setForeground(Color.WHITE);
        wrong1.setPreferredSize(new Dimension(400, 150));
        
		wrong2 = new JRadioButton();
        wrong2.setFont(new Font("Calibri", Font.BOLD, 25));
        wrong2.setOpaque(true);
        wrong2.setForeground(Color.WHITE);
        wrong2.setPreferredSize(new Dimension(400, 150));
        
		wrong3 = new JRadioButton();
        wrong3.setFont(new Font("Calibri", Font.BOLD, 25));
        wrong3.setOpaque(true);
        wrong3.setForeground(Color.WHITE);
        wrong3.setPreferredSize(new Dimension(400, 150));
		quizAnswers = quizAnswersIn;
	
        fixCorrect = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,10));
        fixCorrect.setBackground(new Color(255, 116, 166));
        fixWrong1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,10));
        fixWrong1.setBackground(new Color(255, 116, 166));
        fixWrong2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,10));
        fixWrong2.setBackground(new Color(255, 116, 166));
        fixWrong3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,10));
        fixWrong3.setBackground(new Color(255, 116, 166));
        fixCorrect.add(correct);
        fixWrong1.add(wrong1);
        fixWrong2.add(wrong2);
        fixWrong3.add(wrong3);
        
        quizButtons = new ButtonGroup();
        quizButtons.add(correct);
        quizButtons.add(wrong1);
        quizButtons.add(wrong2);
        quizButtons.add(wrong3);
        
        createOptions();
        addOptions();
        
        MultipleChoiceButtonHandler mcbh = new MultipleChoiceButtonHandler(quizAnswers, this, gq);
        correct.addActionListener(mcbh);
        wrong1.addActionListener(mcbh);
        wrong2.addActionListener(mcbh);
        wrong3.addActionListener(mcbh);
	}
	
	/* Sets the question for the panel as well as clears the selection
	 * of the JRadioButtons so that they are not already selected when
	 * a new question is asked.
	 */
	public void setQuestionSelected(int questionSelectedIn)
	{
		questionSelected = questionSelectedIn;
		quizButtons.clearSelection();
	}
	
	/* Returns the question number that is selected
	 */
	public int getQuestionSelected()
	{
		return questionSelected;
	}
	
	/* Sets the text of each radioButton every time a new question
	 * is asked, it also sets the background color and enables them.
	 */
	public void createOptions()
	{
		correct.setText(quizAnswers[4*(questionSelected)]);
		correct.setBackground(new Color(255, 178, 204));
		correct.setEnabled(true);
        wrong1.setText(quizAnswers[4*(questionSelected) + 1]);
        wrong1.setBackground(new Color(255, 178, 204));
        wrong1.setEnabled(true);
        wrong2.setText(quizAnswers[4*(questionSelected) + 2]);
        wrong2.setBackground(new Color(255, 178, 204));
        wrong2.setEnabled(true);
        wrong3.setText(quizAnswers[4*(questionSelected) + 3]);
        wrong3.setBackground(new Color(255, 178, 204));
        wrong3.setEnabled(true);
	}
	
	/* This method adds all the JRadioButtons randomly to the 
	 * panel. It uses Math.random to generate a random number. 
	 */
	public void addOptions()
	{
		boolean[] check = new boolean[4];
		int randomAnswer = -1;
		while(check[0] == false || check[1] == false || check[2] == false 
			  || check[3] == false)
		{
			randomAnswer = (int)(Math.random()* 4 + 1);
			if(randomAnswer == 1)
			{
				add(fixCorrect);
			}
			else if(randomAnswer == 2)
			{
				add(fixWrong1);
			}
			else if(randomAnswer == 3)
			{
				add(fixWrong2);
			}
			else if(randomAnswer == 4)
			{
				add(fixWrong3);
			}	
			check[randomAnswer - 1] = true;
		}
	}
	
	/* Sets what ever the user has selected as answerSelected.
	 */
	public void setAnswer(int correctOrWrong)
	{
		answerSelected = correctOrWrong;
	}

	/* This method checks if what the user has selected as the answer
	 * is incorrect or correct. If correct it will only show green. If
	 * wrong it will show both the correct answer as green as well as the 
	 * answer they chose as red. It also calls the points methods to make
	 * sure if they get it correct the points are increased. Disables the 
	 * JRadioButtons as well. It also stores all wrong answers in an array.
	 */
	public void checkQuizAnswers()
	{
		if(answerSelected != 0)
		{
			if(answerSelected == 1)
			{
				wrong1.setBackground(Color.RED);
			}
			else if(answerSelected == 2)
			{
				wrong2.setBackground(Color.RED);
			}
			else if(answerSelected == 3)
			{
				wrong3.setBackground(Color.RED);
			}
			checkWrong[correctWrong] = questionSelected;
			checkWrong[correctWrong + 1] = answerSelected;
			correctWrong += 2;
		}
		else
		{
			gqs.setPointCounter(true);
			gq.setPoints();
		}
		correct.setBackground(new Color(0, 174, 48));
		correct.setEnabled(false);
		wrong1.setEnabled(false);
		wrong2.setEnabled(false);
		wrong3.setEnabled(false);
	}
	
	/* This method uses the array where the wrong answers are stored
	 * and finds the explanation for those wrong answers in a txt file.
	 * From there we store those explanations in another array. We use scanner
	 * to read the txt file and while loops as well as if statments for it
	 * to work.
	 */
	public void addExplainAnswer()
	{
		Scanner addExplain = fa.lookAtFile("TeachUserCorrect.txt");
		String readExplain = new String("");
		int findStart = 0;

	    while(addExplain.hasNextLine() && numOfWrong == 0 && checkWrong[1] != 0)
	    {
			readExplain = addExplain.nextLine().trim();
			if(readExplain.equalsIgnoreCase(termSelected + ":") && addExplain.hasNextLine())
			{
				readExplain = addExplain.nextLine().trim();
				while(!(readExplain.substring(readExplain.length() -1).equals(":")) && addExplain.hasNextLine())
				{
					if((readExplain.charAt(0) - 48) == (checkWrong[numOfWrong] + 1) && 
								numOfWrong <= 5)
					{
						findStart = readExplain.indexOf(')');
						explainWrongAnswers[2 * numOfWrong] = termSelected;
						explainWrongAnswers[(2 * numOfWrong) + 1] = quizQuestions[checkWrong[numOfWrong]];
						explainWrongAnswers[(2 * numOfWrong)+ 2] =  "<html><center>" + 
								readExplain.substring(findStart + 2, readExplain.length()) 
								+ "<html><center>" ;
						readExplain = addExplain.nextLine().trim();
						findStart = readExplain.indexOf(')');
						while(findStart == -1)
						{
							explainWrongAnswers[(2 * numOfWrong)+ 2] = "<html><center>"  + 
									explainWrongAnswers[(2 * numOfWrong)+ 2] 
									+  " " + readExplain.substring(0, readExplain.length()) 
									+ "<html><center>";
							readExplain = addExplain.nextLine().trim();
							findStart = readExplain.indexOf(')');
						}
						while(explainWrongAnswers[(2 * numOfWrong)+ 3] == null && addExplain.hasNextLine())
						{
							if((readExplain.indexOf('b') == 0 && checkWrong[numOfWrong + 1] == 1) ||
							   (readExplain.indexOf('c') == 0 && checkWrong[numOfWrong + 1] == 2) ||
							   (readExplain.indexOf('d') == 0 && checkWrong[numOfWrong + 1] == 3))
							{
								explainWrongAnswers[(2 * numOfWrong)+ 3] = "<html><center>"  
										+ readExplain.substring(findStart + 2, readExplain.length()) 
										+ "<html><center>" ;
								readExplain = addExplain.nextLine().trim();
								findStart = readExplain.indexOf(')');
								while(findStart == -1  && addExplain.hasNextLine() && addExplain.hasNextLine())
								{
									explainWrongAnswers[(2 * numOfWrong)+ 3] = "<html><center>" 
											+ explainWrongAnswers[(2 * numOfWrong)+ 3] + " "
											+ readExplain.substring(0, readExplain.length()) 
											+"<html><center>" ;
									readExplain = addExplain.nextLine().trim();
									if ((readExplain.substring(readExplain.length() -1).equals(":")) && addExplain.hasNextLine())
									{
										findStart = 0;
										
									}
									else
									{
										findStart = readExplain.indexOf(')');
									}
								}
							}
							else
							{
								readExplain = addExplain.nextLine().trim();
							}
							
						}
						if(explainWrongAnswers[2 * numOfWrong] != null)
						{
							
							numOfWrong += 2;
						}
					}
					else
					{
						if(addExplain.hasNextLine())
						{
							readExplain = addExplain.nextLine().trim();
						}
					}
				}
			}
		}
	}
	
	/* Returns the array with the explanations for the wrong answers
	 */
	public String[] returnTeachAnswers()
	{
		return explainWrongAnswers;
	}
	
}
class GameQuizButtonHandler implements ActionListener /// Ananya Kotla
{
	private CardLayout menuCards; // The cards for menu panel.
    private MenuHolder mh; // The instance of class where cardLayout for menu panel is stored.
    private GameQuiz gq; // The instance of class GameQuiz
    private int pointIn; // The amount of points the user has hearned
    private MultipleChoice mc; // Instance of class Multiple Choice
    private int endQuiz; // To see if the user has finish quiz on 3 organs
    private FileAdder fa; // Instance of class FileAdder
    private int questionCount; // The question number the panel is showing.
    private GameQuizSelect gqs; // The instance of the class GameQuizSelect
    private StartGame sg; // The instance of startGame
    private int dissectionNum; // Stores what dissection the user is playing
    
	public GameQuizButtonHandler( MenuHolder mhIn, CardLayout menuCardsIn, GameQuiz gqIn,
			MultipleChoice mcIn, int endQuizIn, FileAdder faIn, GameQuizSelect gqsIn, 
			StartGame sgIn, int dissectionNumIn)
	{
		menuCards = menuCardsIn;
		mh = mhIn;
		gq = gqIn;
		mc = mcIn;
		questionCount = 1;
		endQuiz = endQuizIn;
		fa = faIn;
		pointIn = 0;
		gqs = gqsIn;
		sg = sgIn;
		dissectionNum = dissectionNumIn;
	}
	
	/* Action performed method to see if buttons have been pressed,and what
	 * to do if they have been. If user selects exit, it goes back to the menu 
	 * page. If use selects check answer it runs the method to check the 
	 * selected answer. It also sets the next button visible. If the user 
	 * clicks on next it first checks if the user has completed the quiz.
	 * If they have, they go to the panel where you have to enter your name
	 * to store your score. If they have finsished the section for one of the
	 * organs, it takes you back to the pick a card page. Else, it moves
	 * on to the next question. If the user gets an answer wrong it take 
	 * them to a class where the explanation for the right answers will be
	 * shown. 
	 */
	public void actionPerformed(ActionEvent evt) 
	{
		String buttonNames = new String("");
		buttonNames = evt.getActionCommand();
		
		if(buttonNames.equals("EXIT"))
        {
            menuCards.show(this.mh, "gameStart");
        }
        else if(buttonNames.equals("CHECK ANSWER"))
        {
			if (questionCount >= 0)
			{
				mc.checkQuizAnswers();
			}
			gq.setNextButtonVisible();
		}
        else if(buttonNames.equals("NEXT"))
        {
			if(endQuiz == 3 && questionCount == 3)
			{
				mc.addExplainAnswer();
				String[] explainAnswers = mc.returnTeachAnswers();
				pointIn = gqs.getPointCounter();
				sg.savePoints(pointIn);
				sg.setDissectionCompletion(dissectionNum);
				boolean[] checkDissectComplete = sg.returnDissectionCompletion();
				if(explainAnswers[0]== null)
				{
					if((dissectionNum == 1 && checkDissectComplete[1] == true) ||
						(dissectionNum == 2 && checkDissectComplete[0] == true))
					{
						AddUserName aud = new AddUserName(fa, pointIn, mh, menuCards);
						mh.add(aud, "UserName");
						menuCards.show(this.mh, "UserName");
					}
					else
					{
						menuCards.show(this.mh, "gameStart");
					}
					
				}
				else
				{
					if((dissectionNum == 1 && checkDissectComplete[1] == true) ||
						(dissectionNum == 2 && checkDissectComplete[0] == true))
					{
						TeachUserAnswers tua = new TeachUserAnswers(mh, menuCards, explainAnswers, 1, pointIn, fa);
						mh.add(tua, "TeachUser");
					}
					else
					{
						TeachUserAnswers tua = new TeachUserAnswers(mh, menuCards, explainAnswers, 2, pointIn, fa);
						mh.add(tua, "TeachUser");
					}
					menuCards.show(this.mh, "TeachUser");
				}
			}
			else if(questionCount == 3)
			{
				mc.addExplainAnswer();
				String[] explainAnswers = mc.returnTeachAnswers();
				if(explainAnswers[0] == null)
				{
					menuCards.show(this.mh, "GameQuizSelect");
				}
				else
				{
					TeachUserAnswers tua = new TeachUserAnswers(mh, menuCards, explainAnswers, 3, pointIn, fa);
					mh.add(tua, "TeachUser");
					menuCards.show(this.mh, "TeachUser");
				}
			}
			else if(questionCount < 3)
			{
				gq.setButtonsNotVisible();
				questionCount++;
				mc.setQuestionSelected(questionCount - 1);
				gq.changeQuestion(questionCount - 1);
				mc.createOptions();
				mc.addOptions();
			}
		}
	}
}

class MultipleChoiceButtonHandler implements ActionListener /// Ananya Kotla
{
	private int questionSelected; // It stores what number JRadioButton the user selected.
	private String[] quizAnswers; // String array where the multiple choice answers are stored
	private MultipleChoice mc; // Instance of class MultipleChoice
	private GameQuiz gq; // Instance of class GameQuiz
	public MultipleChoiceButtonHandler(String[] quizAnswersIn, MultipleChoice mcIn, GameQuiz gqIn)
	{
		questionSelected = -1;
		quizAnswers = quizAnswersIn;	
		mc = mcIn;
		gq = gqIn;
	}
	
	/* checks if the user has selected any of the JRadioButtons. It calls
	 * the method getQuestionSelect. If they
	 * have, then they call setAnswer where they send in the number correspondent
	 * to the JRadioButtons. After the JRadioButton has been selected, the
	 * next button is set visible.
	 */
	public void actionPerformed(ActionEvent evt) 
	{
		String quizButtonNames = new String("");
		quizButtonNames = evt.getActionCommand();
		
		questionSelected = mc.getQuestionSelected();
		if(quizButtonNames.equals(quizAnswers[4*questionSelected]))
        {
            mc.setAnswer(0);
        }
        else if(quizButtonNames.equals(quizAnswers[4*questionSelected + 1]))
        {
		    mc.setAnswer(1);
		}
		else if(quizButtonNames.equals(quizAnswers[4*questionSelected + 2]))
		{
			mc.setAnswer(2);
		}
		else if(quizButtonNames.equals(quizAnswers[4*questionSelected + 3]))
		{
			mc.setAnswer(3);
		}
		gq.setCheckButtonVisible();
	}
}
