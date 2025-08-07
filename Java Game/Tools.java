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
import javax.swing.JTextArea;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JCheckBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Insets;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Image;
class Tools extends JPanel /// Ella Yao
{
	private JButton quiz; // Quiz button, which starts the quiz.
	private Scanner toolsRead; // Reads the scores that are stored in a txt file.
	private String[] toolslides; // Stores each tool definition in array.
	private JLabel quizLabel; // Label is added when quiz button appears.
	private String[] names;	// the names of the pictures
	private Image[] images;	// array of images to be drawn
	private int selected; // Used to know which image should be painted
	
	/* Initializes field variables and vars sent through parameters
	 * Called methods where tools are read from a txt file
	 * and added to the JTextArea
	 *  JtextArea, JLabels, and JButtons are created
	 * set the Layout the Grid
	 * add JTextArea to and all buttons to grid layout.
	 * Created the home button and added it to the south
	 * Add ActionListener to the home button.
	 * Made 2 Handler classes for actionPerformed one for buttons
	 * and one for the JMenu
	 * Calls getMyImage to get images from the FileAdder class and stores
	 * it in the images array.
	 */
	public Tools(CardLayout menuCardsIn, MenuHolder mhIn, FileAdder faIn, StartGame sgIn)
    {
		CardLayout menuCards = menuCardsIn;
		MenuHolder mh = mhIn;
		toolsRead = null;
		FileAdder fa = faIn;
		toolslides = new String[4];
		selected = -1;
		StartGame sg = sgIn;
		
		setBackground(new Color(255, 116, 166));
		setLayout(new BorderLayout());
		
		toolsRead = fa.lookAtFile("ToolDefinitions.txt");
		addTools();
		
		JTextArea toolsInfo = new JTextArea();
		toolsInfo.setEditable(false);
        toolsInfo.setLineWrap(true);
        toolsInfo.setWrapStyleWord(true);
        toolsInfo.setFont(new Font("Calibri", Font.BOLD, 20));
        toolsInfo.setMargin(new Insets(10, 10, 5, 5));
        toolsInfo.setForeground(new Color(255, 109,156));
        toolsInfo.setPreferredSize(new Dimension(320,657));
		JPanel toolsText = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        toolsText.setBackground(new Color(255, 116, 166));
		toolsText.add(toolsInfo);
		add(toolsText, BorderLayout.WEST);
	
		
		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.setBackground(new Color(255, 116, 166));
		JMenuBar toolsMenuBar = new JMenuBar();
        JMenu toolsMenu = new JMenu("Tools");
        toolsMenuBar.setForeground(new Color(255, 116, 166));
        toolsMenu.setFont(new Font("Calibri", Font.BOLD, 40));
        toolsMenu.setPreferredSize(new Dimension(200, 60));
        JMenuItem tweezer = new JMenuItem("Tweezers");
        tweezer.setOpaque(true);
        tweezer.setBackground(new Color(255,232,239));
        tweezer.setForeground(new Color(255, 116, 166));
        tweezer.setFont(new Font("Calibri", Font.BOLD, 20));
        JMenuItem scissors = new JMenuItem("Scissors");
        scissors.setOpaque(true);
        scissors.setBackground(new Color(255,232,239));
        scissors.setForeground(new Color(255, 116, 166));
        scissors.setFont(new Font("Calibri", Font.BOLD, 20));
        JMenuItem scalpel = new JMenuItem("Scalpel");
        scalpel.setOpaque(true);
        scalpel.setBackground(new Color(255,232,239));
        scalpel.setForeground(new Color(255, 116, 166));;
        scalpel.setFont(new Font("Calibri", Font.BOLD, 20));
        JMenuItem probe = new JMenuItem("Probe");
        probe.setOpaque(true);
        probe.setBackground(new Color(255,232,239));
        probe.setForeground(new Color(255, 116, 166));
        probe.setFont(new Font("Calibri", Font.BOLD, 20));
        toolsMenu.add(tweezer);
        toolsMenu.add(scissors);
        toolsMenu.add(scalpel);
        toolsMenu.add(probe);
        toolsMenuBar.add(toolsMenu);
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 60, 10));
        menuPanel.setBackground(new Color(255, 116, 166));
        menuPanel.add(toolsMenuBar);
        rightPanel.add(menuPanel, BorderLayout.NORTH);
        
        ToolMenuHandler tmh = new ToolMenuHandler(this, toolslides, toolsInfo, selected);
        tweezer.addActionListener(tmh);
        scissors.addActionListener(tmh);
        scalpel.addActionListener(tmh);
        probe.addActionListener(tmh);
        
        JPanel addButton = new JPanel(new GridLayout(2, 1));		
		JPanel toQuizPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		JCheckBox toQuiz = new JCheckBox("<html><center>Click here to go" 
			+ "<br>straight to the quiz!<html><center>");
		toQuiz.setFont(new Font("Calibri", Font.BOLD, 20));
		toQuiz.setForeground(Color.WHITE);
		toQuizPanel.setBackground(new Color(255, 116, 166));
		toQuiz.setBackground(new Color(255, 116, 166));
		ToolsButtonHandler tbh = new ToolsButtonHandler(menuCards, mh, sg, toQuiz);
		toQuiz.addActionListener(tbh);
		toQuizPanel.add(toQuiz);
		addButton.add(toQuizPanel);
	
		JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		addButton.setBackground(new Color(255, 116, 166));
		JButton goBackMenu = new JButton("EXIT");
		goBackMenu.setBackground(new Color(255, 178, 204));
        goBackMenu.setFont(new Font("Calibri", Font.BOLD, 40));
        goBackMenu.setOpaque(true);
        goBackMenu.setBorderPainted(false);
        goBackMenu.setForeground(Color.WHITE);
        goBackMenu.setPreferredSize(new Dimension(150, 60));
        goBackMenu.addActionListener(tbh);
        exitPanel.setBackground(new Color(255, 116, 166));
        exitPanel.add(goBackMenu);
        addButton.add(exitPanel);
        
        quiz = new JButton("Quiz");
        quiz.setBackground(new Color(255, 178, 204));
        quiz.setFont(new Font("Calibri", Font.BOLD, 40));
        quiz.setOpaque(true);
        quiz.setBorderPainted(false);
        quiz.setForeground(Color.WHITE);
        quiz.setPreferredSize(new Dimension(150, 60));
        quiz.setVisible(false);
        
        quizLabel = new JLabel("<html><center>Click on Tools to learn<br>"
			+ "information about all the tools!<html><center>", JLabel.CENTER);
		quizLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        quizLabel.setForeground(Color.WHITE);
		JPanel addQuiz = new JPanel(new GridLayout(2,1));
        addQuiz.setBackground(new Color(255, 116, 166));
        JPanel readjustQuizLabel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 160));
        readjustQuizLabel.setBackground(new Color(255, 116, 166));
        readjustQuizLabel.add(quizLabel);
        JPanel readjustQuizButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        readjustQuizButton.setBackground(new Color(255, 116, 166));
        quiz.setPreferredSize(new Dimension(150, 60));
        readjustQuizButton.add(quiz);
        
        addQuiz.add(readjustQuizLabel);
        addQuiz.add(readjustQuizButton);
        rightPanel.add(addQuiz, BorderLayout.CENTER);   
        add(rightPanel, BorderLayout.EAST);
        quiz.addActionListener(tbh);
        rightPanel.add(addButton, BorderLayout.SOUTH);
        
        names = new String[]{"gamePictures/Tweezer_Dissection.jpg", "gamePictures/Scissors_Dissection.jpg",
			 "gamePictures/Probe_Dissection.jpg", "gamePictures/Scalpel_Dissection.jpg"};
        images = new Image[names.length];
        for (int i = 0; i < names.length; i++)
            {   
                images[i] = fa.getGameImages(names[i]);   
			}
	}

    /* reads the file and stores each tool slide in the in the array */
    public void addTools()
    {
        String readTools = new String("");
	    int numTools = 0;
	    toolslides[numTools] = new String("");
	    readTools = toolsRead.nextLine().trim();
	    toolslides[numTools] = toolslides[numTools] + readTools + "\n";
	    while(toolsRead.hasNextLine()&& numTools <= 3)
	    {
			readTools = toolsRead.nextLine().trim();
			toolslides[numTools] = toolslides[numTools] + readTools + " ";
			if(readTools.equals(""))
			{
				numTools++;
				toolslides[numTools] = new String("");
				readTools = toolsRead.nextLine().trim();
				toolslides[numTools] = toolslides[numTools] + readTools + "\n";
			}
			
		}
    }
    
    /* The method enables the quizLabel button
     * It's only called when the user clicks on all 4 tools in the JMenu
     */
	public void showQuiz()
	{
		quizLabel.setText("<html><center>Time to test what<br>you have learned!<html><center>");
		quiz.setVisible(true);
	}
	
	/* This is the paint component method. Its used for showing each
	 * tool picture. Based on whatever tool that is selected, it will
	 * draw that image.
	 */
	public void paintComponent(Graphics g)
	{
			super.paintComponent(g);
			if(selected >= 0 && selected <= 3)
			{
				g.drawImage(images[selected], 340, 10, 320, 657, this);
			}
	}
	
	/* sets selected based on what tool the user has selected
	 */
	public void setSelected(int selectedIn)
	{
		selected = selectedIn;
	}	
}

class ToolsButtonHandler implements ActionListener /// Ella Yao
{
	private MenuHolder mh; // The instance of class where cardLayout for menu panel is stored.
    private CardLayout menuCards; // The cards for menu panel.
	private StartGame sg; // The instance of class StartGame to call methods from that class.
	private JCheckBox toQuiz;// Checkbox to see if it's selected.
	public ToolsButtonHandler(CardLayout menuCardsIn, MenuHolder mhIn, StartGame sgIn, JCheckBox toQuizIn)
	{
		menuCards = menuCardsIn;
		mh = mhIn;
		sg = sgIn;
		toQuiz = toQuizIn;
	}
	
	/* If exit button is pressed, it will take you back to the
	 * game page. It the quiz button is pressed or the checkbox is ticked 
	 * it will take you to the quiz page by creating an instance of that class
	 * and calling it.
	 */
	public void actionPerformed(ActionEvent evt)
    {
		String buttonNames = new String("");
		buttonNames = evt.getActionCommand();
		
        if(buttonNames.equals("EXIT"))
        {
            menuCards.show(this.mh, "gameStart");
        }
        else if(buttonNames.equals("Quiz") || toQuiz.isSelected())
        {
			ToolsQuiz tQuizPan = new ToolsQuiz(menuCards, mh, sg);
			mh.add(tQuizPan, "ToolsQuiz");
			menuCards.show(this.mh, "ToolsQuiz");
			toQuiz.setSelected(false);
		}
    }
}

class ToolMenuHandler implements ActionListener /// Ella Yao
{
	private int[] count; // Made sure that if the same tool is selected more than  once it doesn't print twice.
	private Tools ts; // instance of class Tools
	private String[] slidesOfTools; // Where each tool information is stored in
	private JTextArea toolInfoAdd; //JTextArea where the information is shown
	private int selected; //If selected is set to a number that picture will be shown as well as the information.
	public ToolMenuHandler(Tools tsIn, String[] toolSlidesIn, JTextArea toolInfoIn, int selectedIn)
	{
		ts = tsIn;
		count = new int[4];
		slidesOfTools = toolSlidesIn;
		toolInfoAdd = toolInfoIn;
		selected = selectedIn;
	}
	
	/* Everytime a menu item is clicked, the slidee corresponding to that
	 * item will be added to the JTextArea, and selected initialized
	 * to a number. Selected is sent through the parameter where
	 * setSelected is called, If all items were click, then the quiz button
	 * would appear. 
	 */
	public void actionPerformed(ActionEvent evt)
    {
		String toolNames = new String("");
		toolNames = evt.getActionCommand();
		
        if(toolNames.equals("Tweezers"))
        {
			if(count[0] == 0)
            {
				count[0] = 1;
				toolInfoAdd.append(slidesOfTools[0] + "\n\n");
			}
            ts.setSelected(0);
        }
        else if(toolNames.equals("Scissors"))
        {
			if(count[1] == 0)
            {
				count[1] = 1;
				toolInfoAdd.append(slidesOfTools[1] + "\n\n");
			}
            ts.setSelected(1);
        }
        else if(toolNames.equals("Probe"))
        {
            if(count[2] == 0)
            {
				count[2] = 1;
				toolInfoAdd.append(slidesOfTools[2] + "\n\n");
			}
            ts.setSelected(2);
        }
        else if(toolNames.equals("Scalpel"))
        {
           if(count[3] == 0)
            {
				count[3] = 1;
				toolInfoAdd.append(slidesOfTools[3] + "\n\n");
			}
            ts.setSelected(3);
        }
        ts.repaint();
        if (count[0] == 1 && count[1] == 1 && count[2] == 1 && count[3] == 1) 
        {
			ts.showQuiz();
		} 
    }
}
