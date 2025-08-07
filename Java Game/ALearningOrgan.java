/// Ella Yao Wrote the Whole File

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import javax.swing.JTextArea;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.awt.Image;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class ALearningOrgan extends JPanel /// Ella Yao
{
	private CardLayout menuCards; // The cards that are used for the homePanel.
	private MenuHolder mh; // Instance of MenuHolder class.
	private FileAdder fa; // Instance of FileAdder class.
	
	/* Initalizes both field vars as well as vars that were sent through
	 * the parameters. Creates all the components needed for this panel
	 * and adds to the panel. The nextView button is used to change the
	 * picture so the user can look at more terms.
	 */
	public ALearningOrgan(CardLayout menuCardsIn, MenuHolder mhIn, FileAdder faIn)
	{
		menuCards = menuCardsIn;
		mh = mhIn;
		fa = faIn;
		setBackground(new Color(255, 116, 166));

		setLayout(new BorderLayout());
		JPanel viewPanel = new JPanel();
		PicturePanel pp = new PicturePanel(menuCards, mh, fa);
		ViewButtonHandler vbh = new ViewButtonHandler(menuCards, mh, pp);
		viewPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40,10));
		JButton nextView = new JButton("Another View");
		nextView.setBackground(new Color(255, 208, 215));
        nextView.setFont(new Font("Calibri", Font.BOLD, 18));
	    nextView.setOpaque(true);
        nextView.setBorderPainted(false);
        nextView.setForeground(new Color(255, 109, 156));
        nextView.setPreferredSize(new Dimension(180, 60));
        
        JButton next = new JButton("TAKE QUIZ!");
        next.setBackground(new Color(255, 208, 215));
        next.setFont(new Font("Calibri", Font.BOLD, 25));
	    next.setOpaque(true);
        next.setBorderPainted(false);
        next.setForeground(new Color(255, 109, 156));
        next.setPreferredSize(new Dimension(180, 60));
        next.addActionListener(vbh);
        
        nextView.addActionListener(vbh);
		viewPanel.add(nextView);
		viewPanel.add(next);
		viewPanel.setBackground(new Color(255, 116, 166));
		add(viewPanel, BorderLayout.NORTH);
        JPanel exitPanel = new JPanel();
		JButton goBackButton = new JButton("EXIT");
		goBackButton.setBackground(new Color(255, 208, 215));
        goBackButton.setFont(new Font("Calibri", Font.BOLD, 40));
        goBackButton.setOpaque(true);
        goBackButton.setBorderPainted(false);
        goBackButton.setForeground(new Color(255, 109, 156));
        goBackButton.setPreferredSize(new Dimension(150, 60));
        goBackButton.addActionListener(vbh);
        exitPanel.add(goBackButton);
        exitPanel.setBackground(new Color(255, 116, 166));
       	exitPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 525));
        add(exitPanel, BorderLayout.EAST);
        add(pp, BorderLayout.CENTER);
    }
}

class PicturePanel extends JPanel implements MouseListener /// Ella Yao
{
	private Image[] organPicture; // Image array where pictures of the learn dissections are stored
	private CardLayout menuCards; // The cards that are used for the homePanel.
	private MenuHolder mh; // Instance of the class MenuHolder
	private FileAdder fa; // Instance of the class FileAdder.
	private Scanner readOrgans; // Scanner used to read the definitions for each organ
	private String[] organSlides; // String array that stores information on each term
	private JTextArea organInfo; // JTextArea that dispays information on each term
	private int counter; // Used to display a certain picture for terms
		
	/* Initializes all field vars as well vars sent through parameters
	 * Gets all the images using file adder as well as it creates the 
	 * JTextArea where all the information will be shown.
	 */
	public PicturePanel(CardLayout menuCardsIn, MenuHolder mhIn, FileAdder faIn)
	{
		menuCards = menuCardsIn;
		mh = mhIn;
		fa = faIn;
		counter = 1;
		
		organSlides = new String[16];
		readOrgans = fa.lookAtFile("AbdominalDefinitions.txt");
		readOrganInfo();
		organPicture = new Image[3];
		String[] organPictureName = {"GamePictures/OrganPic_A1.png", "GamePictures/OrganPic_A2.png",
			"GamePictures/OrganPic_A3.png"};	
			
		for (int i = 0; i < 3; i++)
		{
			organPicture[i] = fa.getGameImages(organPictureName[i]);
		}	
		setLayout(new BorderLayout());
		JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(255, 116, 166));
		textPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		organInfo = new JTextArea("Click on a organ name in the picture to learn about them!"
			+ " Click next view to look at different terms!\n\n" + organSlides[0]);
		organInfo.setFont(new Font("Calibri", Font.BOLD, 19));
		organInfo.setForeground(new Color(255, 116, 166));
		organInfo.setEditable(false);
        organInfo.setLineWrap(true);
        organInfo.setWrapStyleWord(true);
        organInfo.setMargin(new Insets(10, 10, 5, 5));	
        JScrollPane spOrgan = new JScrollPane(organInfo);
        spOrgan.setPreferredSize(new Dimension(300, 585));
        textPanel.add(spOrgan);
        add(textPanel, BorderLayout.WEST);
        setBackground(new Color(255, 116, 166));
        
        addMouseListener(this); 
	}
	
	/* Reads all the organInfo based on what's on the txt file.
	 * It users a scanner to read each line of the txt file and stores
	 * the definitions/information of each term in each array slot. 
	 * This is done using if-else statements as well as while loops.
	 */
	public void readOrganInfo()
	{

        String readOrganLine = new String("");
	    int organLineNum = 0;
	    organSlides[organLineNum] = new String("");
	    readOrganLine = readOrgans.nextLine().trim();
	    organSlides[organLineNum] = readOrganLine;
	    while(readOrgans.hasNextLine()&& organLineNum <= 15)
	    {
			readOrganLine = readOrgans.nextLine().trim();

			if (readOrganLine.equals("")) 
			{
				readOrganLine = readOrgans.nextLine().trim();
				if (readOrganLine.equals(""))
				{
					organLineNum++;
					readOrganLine = readOrgans.nextLine().trim();
					organSlides[organLineNum] = readOrganLine;
				}	
				else 
				{
						organSlides[organLineNum] += "\n\n" + readOrganLine + "\n";
				}
			} 
			else 
			{
				organSlides[organLineNum] += readOrganLine + " ";
			}
		}
	}
	
	/* When this method is called is saves counter to whatever variable
	 * is passed through it and then calls repaint, which uses the counter.
	 */
	public void getCounter(int count)
	{
		counter = count;
		repaint();
	}
	
	/* When the method on top is called, it calls repaint, and based
	 * on what counter is saved as, it shows that image.
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(250, 5, 600, 585);
		if (counter % 3 == 1)
			g.drawImage(organPicture[0], 250, 5, 600, 600, this);
		else if (counter % 3 == 2)
			g.drawImage(organPicture[1], 250, 5, 600, 600, this);
		else if (counter % 3 == 0)
			g.drawImage(organPicture[2], 250, 5, 600, 600, this);  
	}
	
	/* Mouse event listener method. If the user clicks on the term
	 * that is shown in the picture, then it sets the text to the information
	 * that is based on that organ. We find coordinates of the 
	 * terms on each picture, and if the user clicks on those coordinates
	 * that it updates the JTextArea.
	 */
	public void mouseClicked(MouseEvent evt)
	{
		int mouseX = evt.getX();
        int mouseY = evt.getY();
        if( counter % 3 == 0)
        {
			// vein
			if (mouseX >=  365 && mouseX <= 475 && mouseY >= 253 && mouseY <= 273) 
			{
				organInfo.setText(organSlides[13]);
			}
			// pap
			else if (mouseX >=  365 && mouseX <= 505 && mouseY >= 408 && mouseY <= 428) 
			{
				organInfo.setText(organSlides[15]);
			}
			// opening
			else if (mouseX >=  395 && mouseX <= 555 && mouseY >= 458 && mouseY <= 478) 
			{
				organInfo.setText(organSlides[14]);
			}
			// anus
			else if (mouseX >=  617 && mouseX <= 662 && mouseY >= 416 && mouseY <= 436) 
			{
				organInfo.setText(organSlides[12]);
			}
		}
        else if (counter % 3 == 2)
        {
			// gall
			if (mouseX >=  365 && mouseX <= 475 && mouseY >= 230 && mouseY <= 260) 
			{
				organInfo.setText(organSlides[7]);
			}
			// stomach
			else if (mouseX >=  645 && mouseX <= 740 && mouseY >= 253 && mouseY <= 283) 
			{
				organInfo.setText(organSlides[4]);
			}
			// pan
			else if (mouseX >=  660 && mouseX <= 745 && mouseY >= 313 && mouseY <= 343) 
			{
				organInfo.setText(organSlides[5]);
			}
			// cecum
			else if (mouseX >=  620 && mouseX <= 685 && mouseY >= 413 && mouseY <= 438) 
			{
				organInfo.setText(organSlides[11]);
			}
		}
		else if (counter % 3 == 1)
		{
			// large
			if (mouseX >=  555 && mouseX <= 670 && mouseY >= 415 && mouseY <= 435) 
			{
				organInfo.setText(organSlides[9]);
			}
			//liver
			else if (mouseX >=  395 && mouseX <= 430 && mouseY >= 230 && mouseY <= 250) 
			{
				organInfo.setText(organSlides[6]);
			}
			//eso
			else if (mouseX >=  415 && mouseX <= 505 && mouseY >= 147 && mouseY <= 167) 
			{
				organInfo.setText(organSlides[3]);
			}
			//spleen
			else if (mouseX >=  657 && mouseX <= 712 && mouseY >= 323 && mouseY <= 343) 
			{
				organInfo.setText(organSlides[10]);
			}
			// small
			else if (mouseX >=  390 && mouseX <= 505 && mouseY >= 407 && mouseY <= 427) 
			{
				organInfo.setText(organSlides[8]);
			}

			else if (mouseX >=  415 && mouseX <= 495 && mouseY >= 57 && mouseY <= 77) 
			{
				organInfo.setText(organSlides[2]);
			}
			//mouth
			else if (mouseX >=  585 && mouseX <= 645 && mouseY >= 37 && mouseY <= 57) 
			{
				organInfo.setText(organSlides[1]);
			}
		}	
	}
	public void mousePressed(MouseEvent evt){} 
	public void mouseReleased(MouseEvent evt){}
	public void mouseEntered(MouseEvent evt){}
	public void mouseExited(MouseEvent evt){}
}

class ViewButtonHandler implements ActionListener /// Ella Yao
{
	private CardLayout menuCards; // cardLayout menuCards is for the menu page.
	private MenuHolder mh; // Instance of class MenuHolder;
	private PicturePanel pp; //Instance of class PicturePanel;
	private int counter; // the var that determines what image should be shown.
	
	public ViewButtonHandler(CardLayout menuCardsIn, MenuHolder mhIn, PicturePanel ppIn)
	{
		menuCards = menuCardsIn;
		mh = mhIn;
		pp = ppIn;
		counter = 1;
	}
	
	/* Checks if the user as clicked on any of the components that 
	 * the actionListener was added to. If user clicks exit, the panel changes
	 * to a different one. If the user clicks Another View, then updates
	 * counter and sends it in to a method, which eventuallly repaints that
	 * image based on the counter. If user selects Take Quiz! then the
	 * page goes to the quiz page.
	 */
	public void actionPerformed(ActionEvent evt)
	{
		String buttonNames = new String("");
		buttonNames = evt.getActionCommand();
		if(buttonNames.equals("EXIT"))
		{
			menuCards.show(this.mh, "gameStart");
		}
		else if(buttonNames.equals("Another View"))
		{
			counter++;
			pp.getCounter(counter);
		}
		else if(buttonNames.equals("TAKE QUIZ!"))
		{
			menuCards.show(this.mh, "GameQuizSelect");
		}
	}	
}
