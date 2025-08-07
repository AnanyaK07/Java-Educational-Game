/// Ananya Kotla & Ella Yao Wrote the File.

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class Dissection extends JPanel implements MouseMotionListener, MouseListener 
{
	private CardLayout menuCards; // The cards that are used for the homePanel.
	private MenuHolder mh; //Instance of class MenuHolder
	private FileAdder fa; // a class to return Images, PrintWriter, Scanner, etc.
	private Image[] pigPicture; // the image for the pig dissection
	private int stepCounter; //The counter for what step the user is on for dissection
	private	boolean[] cuts; // Number of cuts in a dissection
	private	int xCutPosition; //x coordinate for when mouse is pressed
	private	int yCutPosition;//y coordinate for when mouse is pressed
	private boolean[] notDragging; //Checks if user is still dragging
	private int toolNum; // num of tool the user is using
	private int pointsNum; // points the user as earned
	private JLabel points; // The jlabel shows the points on the panel
	private JLabel hearts; // The Jlabel shows the hearts on the panel
	private int heartCounter; //Checks how many hearts are still remaining
	private boolean[] startGood; //Checks If the starting postition is good
	private int stepNum; // The num of step they are on right now
	private JFrame frame; //The frame (used for pop up message)
	private JButton nextStep; // button for next step if neccessary
	private JLabel instructions; // Shows instructions to tell user what to do
	private TimerPanel tmp; //instance of class Timer Panel
	private StartGame sg; //Intance of class StartGame
	private int dissectionNumber; //Stores what dissection the user has selected
	
	/* Initializes field variables and vars went in through parameters
	 * set the Layout the Border Layout
	 * Created the home button and added it to the south
	 * Add ActionListener to the home button
	 * This method also creates a String with the pig dissection image's
	 * name and recieves an instance of FileAdder and use the instance to 
	 * call getPigPictures, where it returns the image so it can be 
	 * displayed using paintComponent. 
	 * Creates instances of both timer class panels.
	 * Adds the mouse listeners at then end
	 */
	public Dissection(CardLayout menuCardsIn, FileAdder faIn, MenuHolder mhIn, 
								JFrame frameIn, StartGame sgIn, int dissectionNumIn) /// Ella Yao
    {
		stepCounter = 1;
		cuts = new boolean[15];
		toolNum = 0;
		sg = sgIn;
		pointsNum = sg.returnPoints();
		heartCounter = 0;
		stepNum = 0;
		notDragging = new boolean[15];
		startGood = new boolean[15];
		dissectionNumber = dissectionNumIn;
		this.menuCards = menuCardsIn;
		this.mh = mhIn;
		frame = frameIn;
		fa = faIn;
		setBackground(new Color(255, 116, 166));
		setLayout(new BorderLayout());
		pigPicture = new Image[9];
		
		
	
		String[] pigAbdPictureName = {"GamePictures/1_Pig.png", "GamePictures/2_Pig.png",
			"GamePictures/3_Pig.png", "GamePictures/4_Pig.png", "GamePictures/5_Pig.png",
			"GamePictures/6_Pig.png", "GamePictures/7_Pig.png", "GamePictures/8_Pig.png"};
		String[] pigThrPictureName = {"GamePictures/1_TPig.png", "GamePictures/2_TPig.png",
			"GamePictures/3_TPig.png", "GamePictures/4_TPig.png", "GamePictures/5_TPig.png",
			"GamePictures/6_TPig.png", "GamePictures/7_TPig.png", "GamePictures/8_TPig.png",
			"GamePictures/9_TPig.png"};
		JPanel instructPanel = new JPanel();
        instructPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        instructions = new JLabel("<html><left>Click Start to start the<br>dissection. "
						+ "Click on Tools to<br>select a tool before<br>starting each step. "
						+ "Drag your<br>mouse to do the each<br>step in the dessection.<br><html><left>");
        
        instructions.setFont(new Font("Calibri", Font.BOLD, 15));
        instructions.setForeground(new Color(255,208,215));
        instructPanel.add(instructions);
        instructPanel.setBackground(new Color(255, 116, 166));
        
		DissectionButtonHandler dbh = new DissectionButtonHandler(menuCards, mh, stepCounter, 
						instructions, this, frame, fa, sg, dissectionNumber);
		
		if(dissectionNumber == 1)
		{
			for (int i = 0; i < 8; i++)
			{
				pigPicture[i] = fa.getGameImages(pigAbdPictureName[i]);
			}	
		}
		else if(dissectionNumber == 2)
		{
			for (int i = 0; i < 9; i++)
			{
				pigPicture[i] = fa.getGameImages(pigThrPictureName[i]);
			}	
		}
		
		
		JPanel toolSide = new JPanel(new BorderLayout());
		
		toolSide.setBackground(new Color(255, 116, 166));
		JButton goBackMenu = new JButton("EXIT");
		goBackMenu.setBackground(new Color(255, 208, 215));
        goBackMenu.setFont(new Font("Calibri", Font.BOLD, 40));
        goBackMenu.setOpaque(true);
        goBackMenu.setBorderPainted(false);
        goBackMenu.setForeground(new Color(255, 109, 156));
        goBackMenu.setPreferredSize(new Dimension(150, 60));
        goBackMenu.addActionListener(dbh);
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        buttonPanel.setBackground(new Color(255, 208, 215));
        JPanel nextPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        JPanel quitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        quitPanel.add(goBackMenu);
        
        nextStep = new JButton("Next Step");
        nextStep.setBackground(new Color(255, 208, 215));
        nextStep.setFont(new Font("Calibri", Font.BOLD, 18));
        nextStep.setOpaque(true);
        nextStep.setBorderPainted(false);
        nextStep.setForeground(new Color(255, 109, 156));
        nextStep.setPreferredSize(new Dimension(150, 60));
        nextStep.setVisible(false);
        nextStep.addActionListener(dbh);
        nextPanel.add(nextStep);
        
        JMenuBar toolsMenuBar = new JMenuBar();
        JMenu toolsMenu = new JMenu("Tools");
		toolsMenuBar.setForeground(new Color(255, 116, 166));
		toolsMenuBar.setBackground(new Color(255, 178, 204));
        toolsMenu.setFont(new Font("Calibri", Font.BOLD, 20));
        toolsMenu.setPreferredSize(new Dimension(180, 40));
        JMenuItem tweezer = new JMenuItem("Tweezers");
        tweezer.setOpaque(true);
        tweezer.setBackground(new Color(255,232,239));
        tweezer.setForeground(new Color(255, 116, 166));
        tweezer.setFont(new Font("Calibri", Font.BOLD, 18));
        JMenuItem scissors = new JMenuItem("Scissors");
        scissors.setOpaque(true);
        scissors.setBackground(new Color(255,232,239));
        scissors.setForeground(new Color(255, 116, 166));
        scissors.setFont(new Font("Calibri", Font.BOLD, 18));
        JMenuItem scalpel = new JMenuItem("Scalpel");
        scalpel.setOpaque(true);
        scalpel.setBackground(new Color(255,232,239));
        scalpel.setForeground(new Color(255, 116, 166));;
        scalpel.setFont(new Font("Calibri", Font.BOLD, 18));
        JMenuItem probe = new JMenuItem("Probe");
        probe.setOpaque(true);
        probe.setBackground(new Color(255,232,239));
        probe.setForeground(new Color(255, 116, 166));
        probe.setFont(new Font("Calibri", Font.BOLD, 18));
        toolsMenu.add(tweezer);
        toolsMenu.add(scissors);
        toolsMenu.add(scalpel);
        toolsMenu.add(probe);
        toolsMenuBar.add(toolsMenu);
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 100));
        menuPanel.setBackground(new Color(255, 116, 166));
        menuPanel.add(toolsMenuBar);
        toolSide.add(menuPanel, BorderLayout.CENTER);
        
        ToolMenuHandler1 tmh1 = new ToolMenuHandler1(this);
        tweezer.addActionListener(tmh1);
        scissors.addActionListener(tmh1);
        scalpel.addActionListener(tmh1);
        probe.addActionListener(tmh1);

		points = new JLabel();
		setPoints();
        points.setFont(new Font("Calibri", Font.BOLD, 20));
        points.setForeground(new Color(255,208,215));
        JPanel pointPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        pointPanel.add(points);
        
        buttonPanel.add(nextPanel);
        buttonPanel.add(quitPanel);
        toolSide.add(buttonPanel, BorderLayout.SOUTH);
        toolSide.add(pointPanel, BorderLayout.NORTH);
        pointPanel.setBackground(new Color(255, 116, 166));
        quitPanel.setBackground(new Color(255, 116, 166));
        nextPanel.setBackground(new Color(255, 116, 166));
        menuPanel.setBackground(new Color(255, 116, 166));
        
        add(toolSide, BorderLayout.EAST);
        JPanel instructionSide = new JPanel();
        instructionSide.setBackground(new Color(255, 116, 166));
        instructionSide.setLayout(new GridLayout(4, 1));
        JPanel instructGrid = new JPanel();
        instructGrid.setLayout(new BorderLayout());
        instructGrid.setBackground(new Color(255, 116, 166));
        
        JPanel heartPanel = new JPanel();
        heartPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        heartPanel.setBackground(new Color(255, 116, 166));
        hearts = new JLabel("♥ ♥ ♥", JLabel.CENTER);
        hearts.setForeground(new Color(255,208,215));
        hearts.setFont(new Font("Arial", Font.BOLD, 30));
        heartPanel.add(hearts);
        instructGrid.add(heartPanel, BorderLayout.NORTH);
        instructGrid.add(instructPanel, BorderLayout.SOUTH);
        
        MoisturePanel mp = new MoisturePanel(dbh);
        mp.moistTimerRunner();
        tmp = new TimerPanel(mp, this, frame, mh, menuCards);
        tmp.countDownTimerRunner();
        
        instructionSide.add(instructGrid);
        instructionSide.add(tmp);
        instructionSide.add(mp);
        add(instructionSide, BorderLayout.WEST);
        addMouseListener(this);
        addMouseMotionListener(this);
	}
	
	/* The moethod saves counter to what every var that is sen through
	 * If counter becomes a specific number during on of the dissections
	 * then the next button will appear. This method disables it once they
	 * click it.
	 */
	public void getCounter(int count) /// Ananya Kotla
	{
		stepCounter = count;
		repaint();
		changeInstructions();
		if(stepCounter == 5 && dissectionNumber == 1)
		{
			nextStep.setVisible(false);
		}
		else if((stepCounter == 4 || stepCounter == 7)&& dissectionNumber == 2)
		{
			nextStep.setVisible(false);
		}
	}
	
	/* The method returns stepCounter
	 */
	public int returnCounter() /// Ananya Kotla
	{
		return stepCounter;
	}
	
	/* This method shows the points on the JPanel and resets it everytime
	 * points change
	 */
	public void setPoints() /// Ananya Kotla
	{
		points.setText("Points: " + pointsNum);
	}
	
	/* Uses heart counter to change the text of the amount of hearts. 
	 * If the user ends up with no hearts left they have to restart the dissection
	 */
	public void setHearts() /// Ananya Kotla
	{
		if(heartCounter == 1)
		{
			hearts.setText("♥ ♥");
		}
		else if(heartCounter == 2)
		{
			hearts.setText("♥");
		}
	    else if(heartCounter == 3)
		{
			hearts.setText("No hearts");
			menuCards.show(this.mh, "gameStart");
		}
	}
	
	/* Returns the JLabel that shows the hearts.
	 */
	public JLabel getHearts() /// Ananya Kotla
	{
		return hearts;
	}
	
	/* Returns the number of points the user has earned.
	 */
	public int returnPointsNum() /// Ananya Kotla
	{
		return pointsNum;
	}
	
	/* Saves the toolNumber based on what the user selected in the JMenu
	 */
	public void setToolSelected(int toolNumIn) /// Ananya Kotla
	{
		toolNum = toolNumIn;
	}
	
	/* Returns the toolNumber based on what the user selected in the JMenu
	 */
	public int getToolSelected() /// Ananya Kotla
	{
		return toolNum;
	}
	
	/* Based on what mistake the user makes, a JOptionPane will show up
	 * saying what your mistake might be.
	 */
	public void showIncorrectMessage(int option) /// Ananya Kotla
	{
		if(option == 1)
		JOptionPane.showMessageDialog(frame, "<html><center>Make sure to "
				+ "cut only on the lines<br>and read the instructions carefully!"
				+ "<br>Redo the cut you just made. You<br>have lost a heart "
				+ "and some points!<html><center>", "Incorrect", 
				JOptionPane.INFORMATION_MESSAGE);
		else if(option == 2)
		JOptionPane.showMessageDialog(frame, "<html><center>Make sure you "
				+ "are starting the cut<br>at the right place and read the"
				+ "<br>instructions again! You have lost<br>a heart and "
				+ "some points!<html><center>", "Incorrect", JOptionPane.INFORMATION_MESSAGE);
	    else if(option == 3)
		JOptionPane.showMessageDialog(frame, "<html><center>Make sure you're "
				+ "ending the cut at<br>the right place and read the<br>"
				+ "instructions again! Redo do the<br>cut youjust made. "
				+ "You have lost<br>a heart and some points!<html><center>", 
				"Incorrect", JOptionPane.INFORMATION_MESSAGE);
		else if(option == 4)
		JOptionPane.showMessageDialog(frame, "<html><center>Read the instructions "
				+ "carefully!<html><center>", "Incorrect", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/* Based on what step is coming and what dissection is is in, it 
	 * changes the text of the instructions everytime the user completes
	 * a step using an if-else statment. In some cases it will set nextButton
	 * to true if there is nothing to do that step
	 */
	public void changeInstructions() /// Ananya Kotla
	{
		if(stepCounter == 1)
		{
			if(dissectionNumber == 1)
			{
				instructions.setText("<html><left>Select a tool and "
						+ "use it to<br>Make a cut by tracing<br>the "
						+ "brown lines in the<br>stomach area! "
						+ "Each line is a<br>seperate cut. "
						+"Start the cut at<br>the top and work "
						+"your way<br>down to the bottom.<html><left>");
			}
			else if(dissectionNumber == 2)
			{
				instructions.setText("<html><left>Select a tool and "
						+ "use it to<br>Make a cut by tracing<br>the "
						+ "brown line in the<br>stomach area! "
						+ "Each line is a<br>seperate cut.<br><br><br><html><left>");
			}
		}		
		else if(stepCounter == 2)
		{
			if(dissectionNumber == 1)
			{
				instructions.setText("<html><left>Make a cut "
						+ "in the<br>stomach area! "
						+"Start the<br>cut at the top and work"
						+"<br>your way down to the bottom.<br><br><br><html><left>");
			}
			else if(dissectionNumber == 2)
			{
				instructions.setText("<html><left>Select a tool and"
					+ "<br>use it to open up the cut."
					+ "<br>Hint: Look at the"
					+ "<br>direction of the arrows!<br><br><br><html><left>");
			}
		}	
		else if(stepCounter == 3)
		{
			if(dissectionNumber == 1)
			{
				instructions.setText("<html><left>Select a tool and"
						+ "<br>use it to open up the cut."
						+ "<br>Hint: Look at the"
						+ "<br>direction of the arrows!<br><br><br><html><left>");
			}
			else if(dissectionNumber == 2)
			{
				instructions.setText("<html><left>This is what the pig"
						+ "<br>looks like now! Nothing<br>"
						+ "to do here! Click"
						+ "<br> Next Step to move on.<br><br><br><html><left>");
				nextStep.setVisible(true);
			}
		}
		else if(stepCounter == 4)
		{
			if(dissectionNumber == 1)
			{
				instructions.setText("<html><left>This is what the pig"
						+ "<br>looks like now! Nothing<br>"
						+ "to do here! Click"
						+ "<br> Next Step to move on.<br><br><br><html><left>");
				nextStep.setVisible(true);
			}
			if(dissectionNumber == 2)
			{
				instructions.setText("<html><left>Make a cut "
						+ "in the<br>stomach area! "
						+ "FIRST make<br>the horizontal cut. "
						+"Start the<br>cut at the top and work"
						+"<br>your way down to the bottom.<br><br><br><html><left>");
			}
		}
		else if(stepCounter == 5)
		{
			if(dissectionNumber == 1)
			{
				instructions.setText("<html><left>Select a tool and"
						+ "<br>use it to make cuts"
						+ "<br>on the side of the stomach!"
						+ "<br><br><br><br><html><left>");
			}
			else if(dissectionNumber == 2)
			{
				instructions.setText("<html><left>Select a tool to "
						+ "cut the<br>ribs now! "
						+ "FIRST make<br>the horizontal cut<br><br><br><html><left>");
			}
		}
		else if(stepCounter == 6)
		{
			if(dissectionNumber == 1)
			{
				instructions.setText("<html><left>Repeat the same<br>"
						+ "step on the other side!<br><br><br><br><br><html><left>");
			}
			else if(dissectionNumber == 2)
			{
				instructions.setText("<html><left>This is what the pig"
						+ "<br>looks like now! Nothing<br>"
						+ "to do here! Click"
						+ "<br> Next Step to move on.<br><br><br><html><left>");
				nextStep.setVisible(true);
			}
		}
		else if(stepCounter == 7)
		{
			if(dissectionNumber == 1)
			{
				instructions.setText("<html><left>Select a tool and"
						+ "<br>use it to open up "
						+ "<br>the flaps of skin.<br><br><br><br><html><left>");
			}
			else if(dissectionNumber == 2)
			{
				instructions.setText("<html><left>Make a cut "
						+ "in the<br>throat area!<br><br><br><html><left>");
			}
		}
		
		else if(stepCounter == 8)
		{
			if(dissectionNumber == 1)
			{
				instructions.setText("<html><left>You did it!"
						+ "<br>Click Next Step to learn"
						+ "<br>about the organs you"
						+ "<br>see here!<br><br><br><html><left>");
				nextStep.setVisible(true);
			}
			if(dissectionNumber == 2)
			{
				instructions.setText("<html><left>Use the probe to<br>gently "
						+ " open the<br>throat area!<br><br><br><html><left>");
			}
		}
		else if(stepCounter == 9)
		{
			if(dissectionNumber == 2)
			{
				instructions.setText("<html><left>You did it!"
						+ "<br>Click Next Step to learn"
						+ "<br>about the organs you"
						+ "<br>see here!<br><br><br><html><left>");
				nextStep.setVisible(true);
			}
		}		
	}
	 
	/* This paintComponent method uses g.drawImage() -- the one with
	 * four parameters, to display the image at a certain location in the
	 * panel based on what step it is on, using stepCCounter,*/
	public void paintComponent(Graphics g) /// Ella Yao
	{
		super.paintComponent(g);
		if (stepCounter == 1)
			g.drawImage(pigPicture[0], 175, 20, this);
		else if (stepCounter == 2)
			g.drawImage(pigPicture[1], 175, 20, this);
		else if (stepCounter == 3)
			g.drawImage(pigPicture[2], 175, 20, this);
		else if (stepCounter == 4)
			g.drawImage(pigPicture[3], 175, 20, this);
		else if (stepCounter == 5)
			g.drawImage(pigPicture[4], 175, 20, this);
		else if (stepCounter == 6)
			g.drawImage(pigPicture[5], 175, 20, this);
		else if (stepCounter == 7)
			g.drawImage(pigPicture[6], 175, 20, this);
		else if (stepCounter == 8)
			g.drawImage(pigPicture[7], 175, 20, this);
		else if (stepCounter == 9)
			g.drawImage(pigPicture[8], 175, 20, this);
	}
	
	/* This method checks if every cut is following on the line in
	 * the picture based on coordinates. It gets the x and y postition and
	 * based on what step they are on and what dissection they are in, we 
	 * use specific coordinates to make sure they are making a good cut. Otherwis
	 * an error will show up and the user will loose a heart and 50 points.
	 * Uses an if-else statment to do this.
	 */
	public void mouseDragged(MouseEvent evt)  /// Ananya Kotla
    {
		xCutPosition = evt.getX();
		yCutPosition = evt.getY();
		boolean running  = tmp.returnRunning();
		if(running == true && dissectionNumber == 1)
		{
			int getTool = getToolSelected();
			if(notDragging[0] == false && startGood[0] == true)
			{
				if(xCutPosition <= 477 && xCutPosition >= 470 && 
				   yCutPosition >= 232 && yCutPosition <= 306 && 
				   getTool == 3)
				{
					cuts[0] = true;
					stepNum = 0;
				}
				else
				{
					cuts[0] = false;
					heartCounter++;
					pointsNum -= 50;
					showIncorrectMessage(1);
				}
			}
			else if(notDragging[1] == false && startGood[1] == true)
			{
				if(xCutPosition <= 477 && xCutPosition >= 433 && 
				   yCutPosition >= 290 && yCutPosition <= 306 &&
				   getTool == 3)
				{
					cuts[1] = true;
					stepNum = 1;
				}
				else
				{
					cuts[1] = false;
					heartCounter++;
					pointsNum -= 50;
					showIncorrectMessage(1);
					
				}
			}
			else if(notDragging[2] == false && startGood[2] == true)
			{
				if(xCutPosition <= 450 && xCutPosition >= 415 && yCutPosition >= 290 && 
				   yCutPosition <= 414 && getTool == 3)
				{
					cuts[2] = true;
					stepNum = 2;
				}
				else
				{
					cuts[2] = false;
					heartCounter++;
					pointsNum -= 50;
					showIncorrectMessage(1);
				}
			}
			else if(notDragging[3] == false && startGood[3] == true)
			{
				if(xCutPosition <= 490 && xCutPosition >= 470 && yCutPosition >= 290 && 
				   yCutPosition <= 306 && getTool == 3)
				{
					cuts[3] = true;
					stepNum = 3;

				}
				else
				{
					cuts[3] = false;
					heartCounter++;
					pointsNum -= 50;
					showIncorrectMessage(1);
				}
			}
			else if(notDragging[4] == false && startGood[4] == true)
			{
				if(xCutPosition <= 540 && xCutPosition >= 480 && yCutPosition >= 290 && 
				   yCutPosition <= 430 && getTool == 3)
				{
					cuts[4] = true;
					stepNum = 4;

				}
				else
				{
					cuts[4] = false;
					heartCounter++;
					pointsNum -= 50;
					showIncorrectMessage(1);
				}
			}
			else if((notDragging[5] == false || notDragging[6] == false) && 
			        (startGood[5] == true || startGood[6] == true))
			{
				
				if(xCutPosition >= 475 && yCutPosition >= 235 && yCutPosition <= 300 && 
				   getTool == 1 && notDragging[6] == false)
				{
					cuts[5] = true;
					stepNum = 5;

				}
				else if(xCutPosition <= 470 && yCutPosition >= 235 && yCutPosition <= 300 && 
				        getTool == 1 && notDragging[5] == false)
				{
					cuts[6] = true;
					stepNum = 6;
				}
				else
				{   
					if(stepNum == 5 && notDragging[5] == false)
					{
						cuts[5] = false;
					}
					else if(stepNum == 6 && notDragging[6] == false)
					{
						cuts[6] = false;
					}
					heartCounter++;
					pointsNum -= 50;
					showIncorrectMessage(4);
				}
			}
			else if((notDragging[7] == false || notDragging[8] == false) && 
			        (startGood[7] == true || startGood[8] == true))
			{
				if(xCutPosition <= 365 && xCutPosition >= 350 && yCutPosition >= 315 && 
				   yCutPosition <= 415 && getTool == 3 && notDragging[7] == false)
				{
					cuts[7] = true;
					stepNum = 7;

				}
				else if(xCutPosition <= 500 && xCutPosition >= 485 && yCutPosition >= 320 && 
				        yCutPosition <= 430 && getTool == 3 && notDragging[8] == false)
				{
					cuts[8] = true;
					stepNum = 8;
				}
				else
				{
					if(stepNum == 7 && notDragging[7] == false)
					{
						cuts[7] = false;
					}
					else if(stepNum == 8 && notDragging[8] == false)
					{
						cuts[8] = false;
					}
					heartCounter++;
					pointsNum -= 50;
					showIncorrectMessage(1);
				}
			}
			
			else if((notDragging[9] == false || notDragging[10] == false) && 
					(startGood[9] == true || startGood[10] == true)) 
			{
				if(xCutPosition <= 450 && xCutPosition >= 435 && yCutPosition >= 340 && 
				   yCutPosition <= 445 && getTool == 3 && notDragging[9] == false)
				{
					cuts[9] = true;
					stepNum = 9;
					
				}
				else if(xCutPosition <= 587 && xCutPosition >= 572 && yCutPosition >= 335 && 
				        yCutPosition <= 435 && getTool == 3 && notDragging[10] == false)
				{
					cuts[10] = true;
					stepNum = 10;
				}
				else
				{
					if(stepNum == 9 && notDragging[9] == false)
					{
						cuts[9] = false;
					}
					else if(stepNum == 10 && notDragging[10] == false)
					{
						cuts[10] = false;
					}
					heartCounter++;
					pointsNum -= 50;
					showIncorrectMessage(1);
				}
			}
			
			else if((notDragging[11] == false || notDragging[12] == false) && 
					(startGood[11] == true || startGood[12] == true))
			{
				if(xCutPosition >= 476 && yCutPosition >= 234 && yCutPosition <= 424 && 
				   getTool == 1 && notDragging[11] == false)
				{
					cuts[11] = true;
					stepNum = 11;
				}
				else if(xCutPosition <= 470  && yCutPosition >= 234 && yCutPosition <= 424 && 
				        getTool == 1 && notDragging[12] == false)
				{
					cuts[12] = true;
					stepNum = 12;
				}
				else
				{
					if(stepNum == 11 && notDragging[11] == false)
					{
						cuts[11] = false;
					}
					else if(stepNum == 12 && notDragging[12] == false)
					{
						cuts[12] = false;
					}
					heartCounter++;
					pointsNum -= 50;
					showIncorrectMessage(4);
				}
			}
			setPoints();
			setHearts();
		}
		else if(running == true && dissectionNumber == 2)
		{
			int getTool = getToolSelected();
			if(notDragging[0] == false && startGood[0] == true)
			{
				if(xCutPosition <= 550 && xCutPosition >= 390 && yCutPosition >= 245 && 
				   yCutPosition <= 290 && getTool == 3)
				{
					cuts[0] = true;
					stepNum = 0;

				}
				else
				{
					cuts[0] = false;
					heartCounter++;
					pointsNum -= 50;
					showIncorrectMessage(1);
				}
			}
			else if((notDragging[1] == false || notDragging[2] == false) && 
					(startGood[1] == true || startGood[2] == true))
			{
				if(xCutPosition <= 550 && xCutPosition >= 400 && yCutPosition <= 260 && 
				   notDragging[1] == false && getTool == 1)
				{
					cuts[1] = true;
					stepNum = 1;
				}
				else if(xCutPosition <= 550 && xCutPosition >= 400  && yCutPosition >= 260 && 
				        notDragging[2] == false && getTool == 1)
				{
					cuts[2] = true;
					stepNum = 2;
				}
				else
				{
					if(stepNum == 1 && notDragging[1] == false)
					{
						cuts[1] = false;
					}
					else if(stepNum == 2 && notDragging[2] == false)
					{
						cuts[2] = false;
					}
					heartCounter++;
					pointsNum -= 50;
					showIncorrectMessage(4);
				}
			}
			else if((notDragging[3] == false || notDragging[4] == false || 
					 notDragging[5] == false || notDragging[6] == false) && 
					 (startGood[3] == true || startGood[4] == true ||
					 startGood[5] == true || startGood[6] == true))
			{
				
				if(xCutPosition <= 540 && xCutPosition >= 410 && yCutPosition >= 165 && 
				   yCutPosition <= 205 && getTool == 3 && notDragging[3] == false  && 
				   startGood[3] == true)
				{
					cuts[3] = true;
					stepNum = 3;

				}
				else if(xCutPosition <= 420 && xCutPosition >= 295 && yCutPosition >= 165 && 
				        yCutPosition <= 283 && getTool == 3 && notDragging[4] == false &&
				        notDragging[3] == true)
				{
					cuts[4] = true;
					stepNum = 4;
				}
				else if(xCutPosition <= 475 && xCutPosition >= 460 && yCutPosition >= 160 && 
						yCutPosition <= 265 && getTool == 3 && notDragging[5] == false && 
						notDragging[3] == true)
				{
					cuts[5] = true;
					stepNum = 5;
				}
				else if(xCutPosition <= 550 && xCutPosition >= 530 && yCutPosition >= 185 && 
						yCutPosition <= 280 && getTool == 3 && notDragging[6] == false && 
						notDragging[3] == true)
				{
					cuts[6] = true;
					stepNum = 6;
				}
				else
				{
					if(stepNum == 3 && notDragging[3] == false)
					{
						cuts[3] = false;
					}
					else if(stepNum == 4 && notDragging[4] == false)
					{
						cuts[4] = false;
					}
					else if(stepNum == 5 && notDragging[5] == false)
					{
						cuts[5] = false;
					}
					else if(stepNum == 6 && notDragging[6] == false)
					{
						cuts[6] = false;
					}
					heartCounter++;
					pointsNum -= 50;
					showIncorrectMessage(1);
				}
			}
			else if((notDragging[7] == false || notDragging[8] == false || 
					 notDragging[9] == false || notDragging[10] == false) && 
					 (startGood[7] == true || startGood[8] == true ||
					 startGood[9] == true || startGood[10] == true))
			{
				
				if(xCutPosition <= 540 && xCutPosition >= 410 && yCutPosition >= 165 && 
				   yCutPosition <= 205 && getTool == 2 && notDragging[7] == false)
				{
					cuts[7] = true;
					stepNum = 7;

				}
				else if(xCutPosition <= 420 && xCutPosition >= 295 && yCutPosition >= 165 && 
						yCutPosition <= 283 && getTool == 2 && notDragging[8] == false && 
						notDragging[7] == true)
				{
					cuts[8] = true;
					stepNum = 8;
				}
				else if(xCutPosition <= 475 && xCutPosition >= 460 && yCutPosition >= 160 && 
						yCutPosition <= 265 && getTool == 2 && notDragging[9] == false &&
						notDragging[7] == true)
				{
					cuts[9] = true;
					stepNum = 9;
				}
				else if(xCutPosition <= 550 && xCutPosition >= 530 && yCutPosition >= 185 && 
						yCutPosition <= 280 && getTool == 2 && notDragging[10] == false && 
						notDragging[7] == true)
				{
					cuts[10] = true;
					stepNum = 10;
				}
				else
				{
					if(stepNum == 7 && notDragging[7] == false)
					{
						cuts[7] = false;
					}
					else if(stepNum == 8 && notDragging[8] == false)
					{
						cuts[8] = false;
					}
					else if(stepNum == 9 && notDragging[9] == false)
					{
						cuts[9] = false;
					}
					else if(stepNum == 10 && notDragging[10] == false)
					{
						cuts[10] = false;
					}
					heartCounter++;
					pointsNum -= 50;
					showIncorrectMessage(1);
				}
			}
			else if(notDragging[11] == false && startGood[11] == true)
			{
				if(xCutPosition <= 475 && xCutPosition >= 460 && yCutPosition >= 110 && 
				   yCutPosition <= 175 && getTool == 3)
				{
					cuts[11] = true;
					stepNum = 11;
				}
				else
				{
					cuts[11] = false;
					heartCounter++;
					pointsNum -= 50;
					showIncorrectMessage(1);
				}
			}
			else if((notDragging[12] == false || notDragging[13] == false) && 
					(startGood[12] == true || startGood[13] == true))
			{
				if(xCutPosition <= 468 && yCutPosition <= 170 && yCutPosition >= 100 && 
				   notDragging[12] == false && getTool == 4)
				{
					cuts[12] = true;
					stepNum = 12;
				}
				else if(xCutPosition >= 470 && yCutPosition <= 170 && yCutPosition >= 100 && 
						notDragging[13] == false && getTool == 4)
				{
					cuts[13] = true;
					stepNum = 13;
				}
				else
				{
					if(stepNum == 12 && notDragging[12] == false)
					{
						cuts[12] = false;
					}
					else if(stepNum == 13 && notDragging[13] == false)
					{
						cuts[13] = false;
					}
					heartCounter++;
					pointsNum -= 50;
					showIncorrectMessage(4);
				}
			}
			setPoints();
			setHearts();
		}
	}
		
	/* Makes sure the starting position of the cut is correct, otherwise,
	 * the game will give an error and the cut won't be proceeded. This checks
	 * based on what step the user is on and uses coordinates to make sure
	 * the user is starting at the right position.
	 */
    public void mousePressed(MouseEvent evt) /// Ananya Kotla
    {
		xCutPosition = evt.getX();
		yCutPosition = evt.getY();
		boolean running2  = tmp.returnRunning();
		if(running2 == true && dissectionNumber == 1)
		{
			if(notDragging[0] == false || notDragging[1] == false || notDragging[2] == false)
			{
				if(xCutPosition <= 477 && xCutPosition >= 470 && yCutPosition >= 225 && 
				   yCutPosition <= 240)
				{
					startGood[0] = true;
				}
				else if(xCutPosition <= 477 && xCutPosition >= 470 && yCutPosition >= 290 && 
						yCutPosition <= 306)
				{
					startGood[1] = true;
				}
				else if(xCutPosition <= 450 && xCutPosition >= 435 && yCutPosition >= 290 && 
						yCutPosition <= 310)
				{
					startGood[2] = true;
					
				}
				else if(yCutPosition >= 173 && yCutPosition <= 455 && xCutPosition >= 499 && 
						xCutPosition <= 545)
				{
					heartCounter++;
					pointsNum -= 50;
					showIncorrectMessage(2);
				}
			}
			if((notDragging[3] == false || notDragging[4] == false) && stepCounter ==2)
			{
				if(xCutPosition <= 477 && xCutPosition >= 470 && yCutPosition >= 290 && 
				   yCutPosition <= 306)
				{
					startGood[3] = true;
				}
				else if(xCutPosition >= 480 && xCutPosition <= 490 && yCutPosition <= 310 && 
						yCutPosition >= 290)
				{
					startGood[4] = true;
				}
				else if(yCutPosition >= 173 && yCutPosition <= 455 && xCutPosition >= 499 && 
						xCutPosition <= 545)
				{
					heartCounter++;
					pointsNum -= 50;
					showIncorrectMessage(2);
				}
			}
			else if(xCutPosition >= 420 && xCutPosition <= 470 && yCutPosition <= 360 && 
					yCutPosition >= 225 && stepCounter == 3)
			{
				startGood[5] = true;
			}
			else if(xCutPosition <= 522 && xCutPosition >= 475 && yCutPosition <= 360 && 
					yCutPosition >= 225 && stepCounter == 3)
			{
				startGood[6] = true;
			}
			else if(xCutPosition <= 365 && xCutPosition >= 350 && yCutPosition <= 335 && 
					yCutPosition >= 315 && stepCounter == 5)
			{
				startGood[7] = true;	
			}
			else if(xCutPosition <= 500  && xCutPosition >= 485 && yCutPosition <= 340 && 
					yCutPosition >= 320 && stepCounter == 5)
			{
				startGood[8] = true;	
			}
			else if(xCutPosition <= 450 && xCutPosition >= 435 && yCutPosition <= 355 && 
					yCutPosition >= 335 && stepCounter == 6)
			{
				startGood[9] = true;
			}
			else if(xCutPosition <= 587 && xCutPosition >= 572 && yCutPosition <= 355 && 
					yCutPosition >= 335 && stepCounter == 6)
			{
				startGood[10] = true;
			}
			else if(xCutPosition <= 520 && xCutPosition >= 500 && yCutPosition <= 415 && 
					yCutPosition >= 235 && stepCounter == 7)
			{
				startGood[11] = true;
				
			}
			else if(xCutPosition <= 440 && xCutPosition >= 420 && yCutPosition <= 415 && 
					yCutPosition >= 235 && stepCounter == 7)
			{
				startGood[12] = true;
			}
			else if(stepCounter >= 3)
			{
				if((stepCounter == 3 || stepCounter == 7) && xCutPosition >= 480 && 
					xCutPosition <= 490 && yCutPosition <= 310 && yCutPosition >= 290)
				{
					showIncorrectMessage(4);
					heartCounter++;
					pointsNum -= 50;
					
				}
				else if(yCutPosition >= 290  && yCutPosition <= 450 && xCutPosition >= 280 && 
						xCutPosition <= 590)
				{
					showIncorrectMessage(2);
					heartCounter++;
					pointsNum -= 50;
				}
			}
			setPoints();
			setHearts();
		}
		else if(running2 == true && dissectionNumber == 2)
		{
			if(xCutPosition <= 405 && xCutPosition >= 390 && yCutPosition >= 270 && 
			   yCutPosition <= 290 && stepCounter == 1)
			{
				startGood[0] = true;
			}
			else if(xCutPosition <= 550 && xCutPosition >= 400 && yCutPosition <= 260 && 
					yCutPosition >= 225 && stepCounter == 2)
			{
				startGood[1] = true;	
			}
			else if(xCutPosition <= 550  && xCutPosition >= 400 && yCutPosition <= 310 && 
					yCutPosition >= 260 && stepCounter == 2)
			{
				startGood[2] = true;	
			}
			else if(xCutPosition <= 420 && xCutPosition >= 410 && yCutPosition <= 205 && 
					yCutPosition >= 185 && stepCounter == 4 && cuts[3] == false)
			{
				startGood[3] = true;
			}
			else if(xCutPosition <= 420 && xCutPosition >= 410 && yCutPosition <= 205 && 
					yCutPosition >= 185 && stepCounter == 4 && cuts[4] == false)
			{
				startGood[4] = true;
			}
			else if(xCutPosition <= 475 && xCutPosition >= 460 && yCutPosition <= 185 && 
					yCutPosition >= 160 && stepCounter == 4)
			{
				startGood[5] = true;
			}
			else if(xCutPosition <= 540 && xCutPosition >= 520 && yCutPosition <= 210 && 
					yCutPosition >= 185 && stepCounter == 4)
			{
				startGood[6] = true;
			}
			else if(xCutPosition <= 420 && xCutPosition >= 410 && yCutPosition <= 205 && 
					yCutPosition >= 185 && stepCounter == 5 )
			{
				startGood[7] = true;
			}
			else if(xCutPosition <= 420 && xCutPosition >= 410 && yCutPosition <= 205 && 
					yCutPosition >= 185 && stepCounter == 5)
			{
				startGood[8] = true;
			}
			else if(xCutPosition <= 475 && xCutPosition >= 460 && yCutPosition <= 185 && 
					yCutPosition >= 160 && stepCounter == 5)
			{
				startGood[9] = true;
			}
			else if(xCutPosition <= 540 && xCutPosition >= 520 && yCutPosition <= 210 && 
					yCutPosition >= 185 && stepCounter == 5)
			{
				startGood[10] = true;
			}
			else if(yCutPosition <= 125 && yCutPosition >= 110 && xCutPosition <= 475 && 
					xCutPosition >= 460 && stepCounter == 7)
			{
				startGood[11] = true;
			}
			else if(xCutPosition <= 468 && xCutPosition >= 447 && yCutPosition <= 170 && 
					yCutPosition >= 100 && stepCounter == 8)
			{
				startGood[12] = true;	
			}
			else if(xCutPosition <= 491  && xCutPosition >= 470 && yCutPosition <= 170 && 
					yCutPosition >= 100 && stepCounter == 8)
			{
				startGood[13] = true;	
			}
			else
			{
				if(stepCounter == 2 || stepCounter == 8 && xCutPosition <= 700 && xCutPosition >= 240 && 
				   yCutPosition <= 450 && yCutPosition >= 40)
				{
					showIncorrectMessage(4);
					heartCounter++;
					pointsNum -= 50;
				}
				else if(xCutPosition <= 560 && xCutPosition >= 380 && yCutPosition <= 450 && 
				        yCutPosition >= 40)
				{
					showIncorrectMessage(2);
					heartCounter++;
					pointsNum -= 50;	
				}
			}
			setPoints();
			setHearts();
		}
    }

	/* Makes sure the ending position of the cut is correct, otherwise,
	 * the game will give an error and the cut won't be accepted. This checks
	 * based on what step the user is on and uses coordinates to make sure
	 * the user is ending at the right position.
	 */
    public void mouseReleased(MouseEvent evt) /// Ananya Kotla
    {
		xCutPosition = evt.getX();
		yCutPosition = evt.getY();
		boolean running3  = tmp.returnRunning();
		if(running3 == true && dissectionNumber == 1)
		{
			if(notDragging[0] == false || notDragging[1] == false || notDragging[2] == false)
			{
				if(xCutPosition <= 477 && xCutPosition >= 470 && yCutPosition >= 290 && 
				   yCutPosition <= 306 && cuts[0] == true)
				{
					notDragging[0] = true;
				}
				else if(xCutPosition <= 450 && xCutPosition >= 433 && yCutPosition >= 290 && 
						yCutPosition <= 306 && cuts[1] == true)
				{
					notDragging[1] = true;
				}
				else if(xCutPosition <= 440 && xCutPosition >= 410 && yCutPosition >= 400 && 
						yCutPosition <= 415 && cuts[2] == true)
				{
					notDragging[2] = true;
				}
				else if(xCutPosition <= 450 && xCutPosition >= 435 && yCutPosition >= 290 && 
						yCutPosition <= 310)
				{
					heartCounter++;
					pointsNum -= 50;
					showIncorrectMessage(3);
				}
			}
			
			if(notDragging[0] == true && notDragging[1] == true && notDragging[2] == true && stepCounter == 1)
			{
				pointsNum += 200;
				getCounter(2);
			}
			else if((notDragging[3] == false || notDragging[4] == false) && stepCounter == 2)
			{
				if(xCutPosition >= 480 && xCutPosition <= 490 && yCutPosition <= 310 && 
				   yCutPosition >= 290 && cuts[3] == true)
				{
					notDragging[3] = true;
				}
				else if(xCutPosition <= 540 && xCutPosition >= 530 && yCutPosition >= 405 && 
						yCutPosition <= 425 && cuts[4] == true)
				{
					notDragging[4] = true;
				}
				else if(xCutPosition <= 450 && xCutPosition >= 435 && yCutPosition >= 290 && 
						yCutPosition <= 310)
				{
					heartCounter++;
					pointsNum -= 50;
					showIncorrectMessage(3);
				}
			}
			
			if(notDragging[3] == true && notDragging[4] == true && stepCounter == 2)
			{
				pointsNum += 200;
				getCounter(3);
			}
			else if(xCutPosition <= 420  && yCutPosition <= 360 && yCutPosition >= 225 && 
					stepCounter == 3 && cuts[6] == true)
			{
				notDragging[5] = true;
				if(cuts[5] == true)
				{
					pointsNum += 200;
					getCounter(4);
				}
			}
			else if(xCutPosition >= 522 && yCutPosition <= 360 && yCutPosition >= 225 && 
					stepCounter == 3 &&  cuts[5] == true)
			{
				notDragging[6] = true;
				if(cuts[6] == true)
				{
					pointsNum += 200;
					getCounter(4);
				}
			}
			else if(xCutPosition <= 365 && xCutPosition >= 350 && yCutPosition <= 415 && 
					yCutPosition >= 400 && stepCounter == 5 && cuts[7] == true)
			{
				notDragging[7] = true;
				if(cuts[8] == true)
				{
					pointsNum += 200;
					getCounter(6);
				}
			}
			else if(xCutPosition <= 500  && xCutPosition >= 485 && yCutPosition <= 430 && 
					yCutPosition >= 410 && stepCounter == 5 && cuts[8] == true)
			{
				notDragging[8] = true;
				if(cuts[7] == true)
				{
					pointsNum += 200;
					getCounter(6);
				}
			}
			else if(xCutPosition <= 450 && xCutPosition >= 435 && yCutPosition <= 445 && 
					yCutPosition >= 430 && stepCounter == 6  && cuts[9] == true)
			{
				notDragging[9] = true;
				if(cuts[10] == true)
				{
					pointsNum += 200;
					getCounter(7);
				}
			}
			else if(xCutPosition <= 587 && xCutPosition >= 572 & yCutPosition <= 435 && 
					yCutPosition >= 420 && stepCounter == 6  && cuts[10] == true)
			{
				notDragging[10] = true;
				if(cuts[9] == true)
				{
					pointsNum += 200;
					getCounter(7);
				}
			}
			else if(xCutPosition >= 540 && yCutPosition <= 415 && yCutPosition >= 235 && 
					stepCounter == 7  && cuts[11] == true)
			{
				notDragging[11] = true;
				if(cuts[12] == true)
				{
					pointsNum += 200;
					getCounter(8);
				}
			}
			else if(xCutPosition <= 420 && yCutPosition <= 415 && yCutPosition >= 235 && 
					stepCounter == 7 && cuts[12] == true)
			{
				notDragging[12] = true;
				if(cuts[11] == true)
				{
					pointsNum += 200;
					getCounter(8);
				}
			}
			else if(stepCounter >= 3)
			{
				if((stepCounter == 3 || stepCounter == 7) && xCutPosition >= 285 && 
					xCutPosition <= 700 && yCutPosition <= 310 && yCutPosition >= 290)
				{
					showIncorrectMessage(4);
					heartCounter++;
					pointsNum -= 50;
				}
				else if(yCutPosition >= 290 && yCutPosition <= 450 && xCutPosition >= 280 && 
						xCutPosition <= 590)
				{
					showIncorrectMessage(3);
					heartCounter++;
					pointsNum -= 50;
				}
				cuts[stepNum] = false;
			}
			setPoints();
			setHearts();
		}
		else if(running3 == true && dissectionNumber == 2)
		{
			if(xCutPosition <= 550  && xCutPosition >= 540  && yCutPosition <= 290 && 
				yCutPosition >= 270 && stepCounter == 1 && cuts[0] == true)
			{
				notDragging[0] = true;
				pointsNum += 200;
				getCounter(2);
			}
			else if(xCutPosition <= 550 && xCutPosition >= 400 && yCutPosition <= 225 && 
					stepCounter == 2  && cuts[1] == true)
			{
				notDragging[1] = true;
				if(cuts[2] == true)
				{
					pointsNum += 200;
					getCounter(3);
				}
			}
			else if(xCutPosition <= 550 && xCutPosition >= 400 && yCutPosition >= 310 && 
			stepCounter == 2 && cuts[2] == true)
			{
				notDragging[2] = true;
				if(cuts[1] == true)
				{
					pointsNum += 200;
					getCounter(3);
				}
			}
			else if(xCutPosition <= 540 && xCutPosition >= 530 && yCutPosition <= 205 && 
					yCutPosition >= 185 && stepCounter == 4 && cuts[3] == true)
			{
				notDragging[3] = true;
				if(cuts[4] == true && cuts[5] == true && cuts[6] == true)
				{
					pointsNum += 200;
					getCounter(5);
				}
			}
			else if(xCutPosition <= 405 && xCutPosition >= 395 && yCutPosition <= 280 && 
					yCutPosition >= 270 && stepCounter == 4 &&  cuts[4] == true)
			{
				notDragging[4] = true;
				if(cuts[3] == true && cuts[5] == true && cuts[6] == true)
				{
					pointsNum += 200;
					getCounter(5);
				}
			}
			else if(xCutPosition <= 475 && xCutPosition >= 460 && yCutPosition <= 260 && 
					yCutPosition >= 250 && stepCounter == 4 && cuts[5] == true)
			{
				notDragging[5] = true;
				if(cuts[3] == true && cuts[4] == true && cuts[6] == true)
				{
					pointsNum += 200;
					getCounter(5);
				}
			}
			else if(xCutPosition <= 550 && xCutPosition >= 535 && yCutPosition <= 280 && 
					yCutPosition >= 270 && stepCounter == 4 && cuts[6] == true)
			{
				notDragging[6] = true;
				if(cuts[3] == true && cuts[4] == true && cuts[5] == true)
				{
					pointsNum += 200;
					getCounter(5);
				}
			}
			else if(xCutPosition <= 540 && xCutPosition >= 530 && yCutPosition <= 205 && 
					yCutPosition >= 185 && stepCounter == 5 && cuts[7] == true)
			{
				notDragging[7] = true;
				if(cuts[8] == true && cuts[9] == true && cuts[10] == true)
				{
					pointsNum += 200;
					getCounter(6);
				}
			}
			else if(xCutPosition <= 405 && xCutPosition >= 395 && yCutPosition <= 280 && 
					yCutPosition >= 270 && stepCounter == 5 &&  cuts[8] == true)
			{
				notDragging[8] = true;
				if(cuts[7] == true && cuts[9] == true && cuts[10] == true)
				{
					pointsNum += 200;
					getCounter(6);
				}
			}
			else if(xCutPosition <= 475 && xCutPosition >= 460 && yCutPosition <= 260 && 
					yCutPosition >= 250 && stepCounter == 5 && cuts[9] == true)
			{
				notDragging[9] = true;
				if(cuts[7] == true && cuts[8] == true && cuts[10] == true)
				{
					pointsNum += 200;
					getCounter(6);
				}
			}
			else if(xCutPosition <= 550 && xCutPosition >= 535 && yCutPosition <= 280 && 
					yCutPosition >= 270 && stepCounter == 5 && cuts[10] == true)
			{
				notDragging[10] = true;
				if(cuts[7] == true && cuts[8] == true && cuts[9] == true)
				{
					pointsNum += 200;
					getCounter(6);
				}
			}
			else if(xCutPosition <= 475 && xCutPosition >= 460  && yCutPosition <= 175 && 
					yCutPosition >= 165 && stepCounter == 7 && cuts[11] == true)
			{
				notDragging[11] = true;
				pointsNum += 200;
				getCounter(8);
			}
			else if(xCutPosition <= 447 && yCutPosition <= 170 && yCutPosition >= 100 && 
					stepCounter == 8  && cuts[12] == true)
			{
				notDragging[12] = true;
				if(cuts[13] == true)
				{
					pointsNum += 200;
					getCounter(9);
				}
			}
			else if(xCutPosition >= 491 && yCutPosition <= 170 && yCutPosition >= 100 && 
					stepCounter == 8 && cuts[13] == true)
			{
				notDragging[13] = true;
				if(cuts[12] == true)
				{
					pointsNum += 200;
					getCounter(9);
				}
			}
			else
			{
				if(stepCounter == 2 || stepCounter == 8 && xCutPosition <= 700 && 
				   xCutPosition >= 240 && yCutPosition <= 450 && yCutPosition >= 40)
				{
					showIncorrectMessage(4);
					heartCounter++;
					pointsNum -= 50;
				}
				else if(xCutPosition <= 560 && xCutPosition >= 380 && yCutPosition <= 450 && 
				        yCutPosition >= 40)
				{
					showIncorrectMessage(3);
					heartCounter++;
					pointsNum -= 50;
				}
				cuts[stepNum] = false;
			}
			setPoints();
			setHearts();
		}
    }
	public void mouseMoved(MouseEvent evt) {}
    public void mouseClicked(MouseEvent evt) {}
    public void mouseEntered(MouseEvent evt) {}
    public void mouseExited(MouseEvent evt) {}
}

/* Checks if a user selectes the right tool, then only then
 * they can proceed with the right cut.
 */
class ToolMenuHandler1 implements ActionListener /// Ananya Kotla
{
	private Dissection dis; //Instance of class Dissection
	
	/* Initalizes all field vars
	 */
	public ToolMenuHandler1(Dissection disIn)
	{
		dis = disIn;
	}
	
	/* If the user selects on one of the tools in the JMenu 
	 * then it will call a method to set that tool as selected.
	 */ 
	public void actionPerformed(ActionEvent evt)
	{
		String toolNames = new String("");
		toolNames = evt.getActionCommand();
		if(toolNames.equals("Tweezers"))
        {
			dis.setToolSelected(1);
		}
		else if(toolNames.equals("Scissors"))
        {
			dis.setToolSelected(2);
		}
		else if(toolNames.equals("Scalpel"))
        {
			dis.setToolSelected(3);
		}
		else if(toolNames.equals("Probe"))
        {
			dis.setToolSelected(4);
		}
	}
}

/* This handler class is used for the buttons.
 */
class DissectionButtonHandler implements ActionListener /// Ananya Kotla
{
	private CardLayout menuCards; // Instance of the cardlayout for the menuPanel
	private MenuHolder mh; //Instance of class MenuHolder
	private MoisturePanel mp; // Instance of che class Moisture Panel
	private int stepCounter; //Based on what step the user is on
	private JLabel instructions; //The JLabel that shows the instructions.
	private Dissection dis; //The instance of the class Dissection
	private int seconds; // The amount of seconds for the countdown timer
	private JFrame frame; //The JFrame which it user for te JOptionPane
	private FileAdder fa; //The instance of the class FileAdder
	private StartGame sg; //The isntance of the class StartGame
	private int dissectionNumber; // Stores what dissection the user is doing.
	
	public DissectionButtonHandler(CardLayout menuCardsIn, MenuHolder mhIn, int count, 
								  JLabel instruct, Dissection disIn, JFrame frameIn, 
								  FileAdder faIn, StartGame sgIn, int dissectionNumberIn)
	{
		menuCards = menuCardsIn;
		mh = mhIn;
		stepCounter = count;
		instructions = instruct;
		dis = disIn;
		frame = frameIn;
		fa = faIn;
		sg = sgIn;
		dissectionNumber = dissectionNumberIn;
	}
	
	/* If the user runs out of time, then this pop-up panel will show up
	 */
	public void getSeconds(int sec)
	{
		seconds = sec;
		if(seconds == 0)
		{
			JOptionPane.showMessageDialog(frame, "<html><center>There was "
				+ "not enough moisture and the pig dried up!<html><center>","You Failed :(", 
				JOptionPane.INFORMATION_MESSAGE);
			 menuCards.show(this.mh, "gameStart");
		}
	}
	
	/* If exit button is pressed, it will take you back to the
	 * game page. If next step is pressed, based on the counter it 
	 * will either change to the next step or make an instance of the class
	 * on the learning the organs and show that class.
	 */
	public void actionPerformed(ActionEvent evt)
    {
		String buttonNames = new String("");
		buttonNames = evt.getActionCommand();
        if(buttonNames.equals("EXIT"))
        {
            menuCards.show(this.mh, "gameStart");
        }
        
        else if(buttonNames.equals("Next Step"))
        {
			stepCounter  = dis.returnCounter();
			if(dissectionNumber == 1)
			{
				if(stepCounter == 4)
				{
					dis.getCounter(5);
				}
				else if(stepCounter == 8)
				{
					GameQuizSelect gQuizSelectPanel = new GameQuizSelect(menuCards,
							mh, fa, dis, sg, dissectionNumber);
					mh.add(gQuizSelectPanel, "GameQuizSelect");
					menuCards.show(this.mh, "AOrgan");
				}
			}
			else if(dissectionNumber == 2)
			{
				if(stepCounter == 3)
				{
					dis.getCounter(4);
				}
				if(stepCounter == 6)
				{
					dis.getCounter(7);
				}
				else if(stepCounter == 9)
				{
					GameQuizSelect gQuizSelectPanel = new GameQuizSelect(menuCards,
							mh, fa, dis, sg, dissectionNumber);
					mh.add(gQuizSelectPanel, "GameQuizSelect");
					menuCards.show(this.mh, "TOrgan");
				}
			} 
		}	
    }
}

/* this class is to run the timer for the dissection */
class TimerPanel extends JPanel implements ActionListener /// Ella Yao
{
	private Timer countDowntimer; // this timer is for the time the user gets for the dissection
	private int time; // the time is how much time the user have to dissect
	private boolean running; // for the timer to continue running or to stop running
	private JButton startTimer; // to start the timer when the user enters the page
	private MoisturePanel mp; // used to start the moisture timer
	private int tenthSec; // tenthSec will count the seconds
	private int elapsedMinutes; // elapsedMinutes will count how many minutes the timer have ran
	private double secondsDisplay; // secondsDisplay tell how many seconds the time have passed
	private Dissection dis; // The instance of the class Dissection
	private JFrame frame; // The frame from the main panel
	private MenuHolder mh; // The instance of the class MenuHolder
	private CardLayout menuCards; // The cardlayout that is used for the meny panel
	/* this method makes an instance of MoisturePanel so that it can be
	 * called to start both timers */
	public TimerPanel(MoisturePanel mpIn, Dissection disIn, JFrame frameIn, 
					  MenuHolder mhIn, CardLayout menuCardsIn)
	{
		mp = mpIn;
		dis = disIn;
		frame = frameIn;
		mh = mhIn;
		menuCards = menuCardsIn;
	}
	
	/* this method creates the timer and the JButton to start the timer */
	public void countDownTimerRunner() 
	{        
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 20));
		setBackground(new Color(255, 116, 166));
		initialValues();
		countDowntimer = new Timer ( 100, this );
		countDowntimer.start();
	    requestFocusInWindow();
	    running = false;
	    startTimer = new JButton("Start!");
	    startTimer.setBackground(new Color(255, 208, 215));
        startTimer.setFont(new Font("Calibri", Font.BOLD, 20));
        startTimer.setOpaque(true);
        startTimer.setBorderPainted(false);
        startTimer.setForeground(new Color(255, 109, 156));
        startTimer.setPreferredSize(new Dimension(150, 60));
        startTimer.addActionListener(this);
        add(startTimer);
	    
	}

	/* returns the boolean running to see if the timer is still running
	 */
	public boolean returnRunning()
	{
		return running;
	}
	
	/* this method sets the orginial values of the timer */
	public void initialValues()
	{
		time = 120;
		tenthSec = 0;
		elapsedMinutes = 0;
	}
	
	/* this method is used to draw the time for the user to see how much
	 * time is left */
	public void paintComponent ( Graphics g )   
	{
		super.paintComponent ( g );
		g.setColor(new Color(255,208,215));

		g.setFont(new Font("Calibri", Font.BOLD, 40));;
		
		secondsDisplay = time % 60; 	
		elapsedMinutes = time / 60;
    	
    	if(secondsDisplay == 0)
    	{	
			g.drawString (elapsedMinutes + " : " + 
				String.format("%.0f0", secondsDisplay), 20, 140 );
			
		}	
		else if(secondsDisplay < 10)
		{
			g.drawString (elapsedMinutes + " : " + 
				String.format("%02.0f", secondsDisplay), 20, 140 );
		}
		else
		{
			g.drawString (elapsedMinutes + " : " + 
				String.format("%.0f", secondsDisplay), 20, 140 );
		}
	}

	/* this is the actionPerformed method for the timer to decrease in
	 * time after the start button is clicked If the tmer runs out
	 * then a opo up panel will appear with an error message
	 */
	public void actionPerformed(ActionEvent evt) 
	{
		String command = evt.getActionCommand();
		if ( command != null )   
		{
			if ( command.equals("Start!") )
			{
				dis.getCounter(1);
				running = true;
				countDowntimer.start();
				mp.startMoistureTimer();
				startTimer.setVisible(false);
			}
			this.repaint();
		}
		else if (secondsDisplay == 0 && elapsedMinutes == 0)
		{
			countDowntimer.stop();
			running = false;
			JOptionPane.showMessageDialog(frame, "<html><center> There is "
				+ "no more time remaining!<html><center>","You Failed :(", JOptionPane.INFORMATION_MESSAGE);
			 menuCards.show(this.mh, "gameStart");
		}
			
		tenthSec++;	
		if (running && tenthSec % 10 == 0) 
		{
			if (elapsedMinutes > 0 || secondsDisplay > 0) 
			{
				time--; 
			}
			tenthSec = 0;
			this.repaint();
		}
	}	
}	

/* this class is to run the time to see how much moisture is left in the 
 * pig through a percetange and a bar as a visual representation of the 
 * percentage */
class MoisturePanel extends JPanel implements ActionListener /// Ella Yao
{
	private Timer moistTimer; // this is for the moisture Timer
	private int time; // this is for how long the timer is
	private int tenthSec; // tenthSec will count the seconds
	private int elapsedMinutes; // elapsedMinutes will count how many minutes the timer have ran
	private double secondsDisplay; // secondsDisplay tell how many seconds the time have passed
	private JButton addMoisture; // this button is to add time for the moisture
	private boolean runTime; // this is used to keep the timer running or to stop running
	private DissectionButtonHandler dbh; // Instance of the dissection button handler class
	private int stop; //checks if the moisture panel has run out
	
	public MoisturePanel(DissectionButtonHandler dbhIn)
	{
		dbh = dbhIn;
		stop = 0;
	}
	
	/* this method is used to create the button to add moisture */
	public void moistTimerRunner() 
	{        
		runTime = true;
		setBackground(new Color(255, 116, 166));
		initialValues();
	    addMoisture = new JButton("Add Moisture");
	    addMoisture.setBackground(new Color(255, 208, 215));
        addMoisture.setFont(new Font("Calibri", Font.BOLD, 20));
        addMoisture.setOpaque(true);
        addMoisture.setBorderPainted(false);
        addMoisture.setForeground(new Color(255, 109, 156));
        addMoisture.setPreferredSize(new Dimension(180, 60));
        addMoisture.addActionListener(this);
        this.add(addMoisture);
	    
	}
	
	/* this method is for creating the timer, where it will be called
	 * when the start button is pressed so that both timers start at the
	 * same time */
	public Timer startMoistureTimer()
	{
		moistTimer = new Timer ( 40, this );
		moistTimer.start();
	    requestFocusInWindow();
	    return moistTimer;
	}

	/* this method sets the original values of the timer -- the time for
	 * the timer would be 100 for 100% displayed in the Moisture Level */
	public void initialValues()
	{
		time = 100;
		tenthSec = 0;
		elapsedMinutes = 0;
	}
	
	/* this method uses paintComponent to show how much moisture is left
	 * in the pig through a percentage. Below the Moisture Level percentage
	 * is a bar made from two rectangles of different sizes, where the smaller
	 * rectangle will change it length according to how much of the moisture
	 * is left in the pig.*/
	public void paintComponent ( Graphics g )   
	{
		super.paintComponent ( g );
		g.setColor(new Color(255,208,215));

		g.setFont(new Font("Calibri", Font.BOLD, 13));;
		
		secondsDisplay = time;
    	
		g.drawString ("Moisture Level: " +
			String.format("%.0f%%", secondsDisplay), 5, 90);
		
		int seconds = (int)secondsDisplay;
			
		g.fillRect(5, 100, 120, 20);
		g.setColor(new Color(255, 109, 156));
		g.fillRect(10, 105, seconds, 10);
		
		if (seconds < 20)
		{
			g.setFont(new Font("Calibri", Font.BOLD, 18));;
			g.setColor(new Color(255,208,215));
			if (seconds != 0)
			{
				g.drawString("Don't forget to ", 5, 140);
				g.drawString("add moisture!", 5, 160);
			}
			else
			{
				g.drawString("Uh oh! The pig", 5, 140);
				g.drawString("ran out of moisture!", 5, 160);
			}
			repaint();
			
		}
		if(seconds >= 0)
		{
			if(seconds == 0)
			{
				stop++;
				if(stop == 1)
				{
					dbh.getSeconds(seconds);
				}
			}
			else if(seconds > 0)
			{
				dbh.getSeconds(seconds);
			}
		}
	}

	/* this method will add "moisture" to the moisture level if the button is 
	 * clicked, or will stop the user from adding "moisture if the
	 * Moisture Level reaches reaches 0%. By decreasing or increasing the
	 * "moisture" for the Moisture Level, the Moisture Level percentage and 
	 * the bar for the Moisture Level will change too. */
	public void actionPerformed(ActionEvent evt) 
	{
		String command = evt.getActionCommand();
		if ( command != null )   
		{
			if ( command.equals("Add Moisture") )
			{
				if (time != 0)
				{
					time += 5;
					if (time > 100)
						time = 100;
				}		
			}		
		}
		else if (secondsDisplay == 0)
		{
			moistTimer.stop();
			runTime = false;
		}
			
		tenthSec++;	
		if (runTime && tenthSec % 10 == 0) 
		{
			if (secondsDisplay > 0) 
			{
				time--; 
			}
			tenthSec = 0;
		}
		this.repaint();
	}	
}	
