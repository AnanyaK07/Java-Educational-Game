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
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import javax.swing.JTextArea;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.awt.Image;
import java.util.Scanner;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class TLearningOrgan extends JPanel /// Ella Yao
{
	private CardLayout menuCards; // The cards that are used for the homePanel.
	private MenuHolder mh; // Instance of MenuHolder class.
	private FileAdder fa; // Instance of FileAdder class.
	
	/* Initalizes both field vars as well as vars that were sent through
	 * the parameters. Creates all the components needed for this panel
	 * and adds to the panel. The nextView button is used to change the
	 * picture so the user can look at more terms.
	 */
	public TLearningOrgan(CardLayout menuCardsIn, MenuHolder mhIn, FileAdder faIn)
	{
		menuCards = menuCardsIn;
		mh = mhIn;
		fa = faIn;
		setBackground(new Color(255, 116, 166));

		setLayout(new BorderLayout());
		JPanel viewPanel = new JPanel();
		TPicturePanel tpp = new TPicturePanel(menuCards, mh, fa);
		TViewButtonHandler tvbh = new TViewButtonHandler(menuCards, mh, tpp);
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
        next.addActionListener(tvbh);
        
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
        goBackButton.addActionListener(tvbh);
        exitPanel.add(goBackButton);
        exitPanel.setBackground(new Color(255, 116, 166));
       	exitPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 525));
        add(exitPanel, BorderLayout.EAST);
        add(tpp, BorderLayout.CENTER);
    }
}

class TPicturePanel extends JPanel implements MouseListener /// Ella Yao
{
	private Image organPicture; // Image where  the picture of the learn dissections are stored
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
	public TPicturePanel(CardLayout menuCardsIn, MenuHolder mhIn, FileAdder faIn)
	{
		menuCards = menuCardsIn;
		mh = mhIn;
		fa = faIn;
		counter = 1;
		
		organSlides = new String[16];
		readOrgans = fa.lookAtFile("ThoracicDefinitions.txt");
		readOrganInfo();
		organPicture = null;
		String organPictureName = "GamePictures/OrganPic_T1.png";
		organPicture = fa.getGameImages(organPictureName);
		setLayout(new BorderLayout());
		JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(255, 116, 166));
		textPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		organInfo = new JTextArea("Click on a organ name in the picture to learn about them!"
			+ " For this dissection, all of the terms are on this picture, so"
			+ " there is no need to press the Another View button!\n\n" + organSlides[0]);
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
	    while(readOrgans.hasNextLine()&& organLineNum <= 14)
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
	
	/* When the method on top is called, it calls repaint, and based
	 * on what counter is saved as, it shows that image.
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(250, 5, 600, 585);
		g.drawImage(organPicture, 250, 5, 600, 600, this);
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
		// pericardium
		if (mouseX >=  403 && mouseX <= 470 && mouseY >= 185 && mouseY <= 200) 
		{
			organInfo.setText(organSlides[11]);
		}
		// right lung
		else if (mouseX >=  410 && mouseX <= 470 && mouseY >= 220 && mouseY <= 235) 
		{
			organInfo.setText(organSlides[3]);
		}
		// thymus tissue
		else if (mouseX >=  423 && mouseX <= 511 && mouseY >= 160 && mouseY <= 175) 
		{
			organInfo.setText(organSlides[6]);
		}
		// cornary
		else if (mouseX >=  375 && mouseX <= 463 && mouseY >= 265 && mouseY <= 280) 
		{
			organInfo.setText(organSlides[13]);
		}
		// heart
		if (mouseX >=  485 && mouseX <= 515 && mouseY >= 285 && mouseY <= 300) 
		{
			organInfo.setText(organSlides[4]);
		}
		// larynx
		else if (mouseX >=  450 && mouseX <= 495 && mouseY >= 82 && mouseY <= 97) 
		{
			organInfo.setText(organSlides[9]);
		}
		// thymus gland
		else if (mouseX >=  580 && mouseX <= 660 && mouseY >= 147 && mouseY <= 162) 
		{
			organInfo.setText(organSlides[5]);
		}
		// left lung
		else if (mouseX >=  625 && mouseX <= 680 && mouseY >= 240 && mouseY <= 255) 
		{
			organInfo.setText(organSlides[2]);
		}
		// pulmonary atery
		if (mouseX >=  620 && mouseX <= 715 && mouseY >= 177 && mouseY <= 192) 
		{
			organInfo.setText(organSlides[10]);
		}
		// trachea
		else if (mouseX >=  470 && mouseX <= 515 && mouseY >= 112 && mouseY <= 127) 
		{
			organInfo.setText(organSlides[8]);
		}
		// pharynx
		else if (mouseX >=  550 && mouseX <= 610 && mouseY >= 55 && mouseY <= 70) 
		{
			organInfo.setText(organSlides[12]);
		}
		// thyroid gland
		else if (mouseX >=  570 && mouseX <= 650 && mouseY >= 92 && mouseY <= 107) 
		{
			organInfo.setText(organSlides[7]);
		}
		// Diaphgram
		else if (mouseX >=  580 && mouseX <= 645 && mouseY >= 280 && mouseY <= 295) 
		{
			organInfo.setText(organSlides[1]);
		}
	}
	public void mousePressed(MouseEvent evt){} 
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
}

class TViewButtonHandler implements ActionListener /// Ella Yao
{
	private CardLayout menuCards; // cardLayout menuCards is for the menu page.
	private MenuHolder mh; // Instance of class MenuHolder;
	private TPicturePanel tpp; //Instance of class TPicturePanel;
	private int counter; // the var that determines what image should be shown.
	
	public TViewButtonHandler(CardLayout menuCardsIn, MenuHolder mhIn, TPicturePanel tppIn)
	{
		menuCards = menuCardsIn;
		mh = mhIn;
		tpp = tppIn;
		counter = 1;
	}
	
	/* Checks if the user as clicked on any of the components that 
	 * the actionListener was added to. If user clicks exit, the panel changes
	 * to a different one. If user selects Take Quiz! then the
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
		else if(buttonNames.equals("TAKE QUIZ!"))
		{
			menuCards.show(this.mh, "GameQuizSelect");
		}
	}	
}
