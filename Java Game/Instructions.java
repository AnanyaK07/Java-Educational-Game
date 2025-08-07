/// Ella Yao Wrote the Whole File

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

import java.awt.Insets;
import java.util.Scanner;

class Instructions extends JPanel /// Ella Yao
{
    private Scanner instructionsRead; // scanner to show the instructions
    private String[] slides; // array to store the instructions
    
    /* Initializes variables that were sent through the parameters
     * makes the nextPage and backPage buttons, the exit button, and the
     * text area so that the instructions can be displayed; all of the 
     * componenets are part of a FlowLayout in a BorderLayout */
    public Instructions(CardLayout homeCardsIn, HomePanelHolder hphIn, FileAdder faIn)
    {
        CardLayout homeCards = homeCardsIn;
        HomePanelHolder hph = hphIn;
        FileAdder fa = faIn;
        slides = new String[10];
        instructionsRead = null;
       
        JButton nextPage = new JButton("Next Page");
        nextPage.setBackground(new Color(255,208,215));
        nextPage.setFont(new Font("Calibri", Font.BOLD, 19));
        nextPage.setForeground(new Color(255, 109,156));
        nextPage.setOpaque(true);
        nextPage.setBorderPainted(false);
        nextPage.setPreferredSize(new Dimension(150, 50));
        
        JButton backPage = new JButton("Back Page");
        backPage.setBackground(new Color(255,208,215));
        backPage.setFont(new Font("Calibri", Font.BOLD, 19));
        backPage.setForeground(new Color(255, 109,156));
        backPage.setOpaque(true);
        backPage.setBorderPainted(false);
        backPage.setPreferredSize(new Dimension(150, 50));
        
        instructionsRead = fa.lookAtFile("HowToPlay.txt");
        addInstructions();
        
        JTextArea instruct = new JTextArea(slides[0]);
        JButton goBackHome = new JButton("EXIT");

        setLayout(new BorderLayout(200, 0));
        setBackground(new Color(255, 242, 227));

        JPanel picture = new JPanel();
        picture.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 608));
        picture.setBackground(new Color(255, 242, 227));
        picture.add(goBackHome);
        add(picture, BorderLayout.EAST);
        JPanel instruction = new JPanel();
        JPanel instructionPanel = new JPanel();
        instruction.setLayout(new BorderLayout());
        instructionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        instructionPanel.setBackground(new Color(255, 242, 227));
        instructionPanel.add(instruct);
        instruct.setForeground(new Color(255, 109,156));
        instruct.setEditable(false);
        instruct.setLineWrap(true);
        instruct.setWrapStyleWord(true);
        instruct.setMargin(new Insets(10, 10, 5, 5));
        instruct.setPreferredSize(new Dimension(500, 600));
        instruction.add(instructionPanel, BorderLayout.CENTER);

        JPanel pagePanel = new JPanel();
        pagePanel.setLayout(new GridLayout(1, 2));
        JPanel nextPanel = new JPanel();
        JPanel backPanel = new JPanel();
        nextPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        nextPanel.setBackground(new Color(255, 242, 227));
        backPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        backPanel.setBackground(new Color(255, 242, 227));
        nextPanel.add(backPage);
        backPanel.add(nextPage);

		InstructButtonHandler ibh = new InstructButtonHandler(homeCards, hph, slides, instruct);
        nextPage.addActionListener(ibh);
        backPage.addActionListener(ibh);
        pagePanel.add(nextPanel);
        pagePanel.add(backPanel);
        instruction.add(pagePanel, BorderLayout.SOUTH);
        instruction.add(pagePanel, BorderLayout.SOUTH);
        add(instruction, BorderLayout.CENTER);

        instruct.setFont(new Font("Calibri", Font.BOLD, 18));
        goBackHome.setBackground(new Color(255, 208, 215));
        goBackHome.setFont(new Font("Calibri", Font.BOLD, 40));
        goBackHome.setForeground(new Color(255, 109, 156));
        goBackHome.setOpaque(true);
        goBackHome.setBorderPainted(false);
        goBackHome.setPreferredSize(new Dimension(150, 60));
        goBackHome.addActionListener(ibh);
    }
 
    /* reads the file and stores each instruction slide in the in the array */
    public void addInstructions()
	{
	    String readDirections = new String("");
	    int num = 0;
	    slides[num] = new String("");
	    readDirections = instructionsRead.nextLine().trim();
	    slides[num] = slides[num] + readDirections + "\n";
	    while(instructionsRead.hasNextLine()&& num <= 9)
	    {
			readDirections = instructionsRead.nextLine().trim();
			slides[num] = slides[num] + readDirections + " ";
			if(readDirections.equals(""))
			{
				num++;
				slides[num] = new String("");
				readDirections = instructionsRead.nextLine().trim();
				slides[num] = slides[num] + readDirections + "\n";
			}
			
		}
    }
}

class InstructButtonHandler implements ActionListener  /// Ella Yao
{
	private HomePanelHolder hph; // Instance of class where card layout is made for home panel
    private CardLayout homeCards; // cards for home panel
    private JTextArea instruct;// The JTextArea where instructions are shown
    private String[] slides; // Each instruction is stored in this array 
    private int counter; // used to check when to add the slide to the JtextArea
	public InstructButtonHandler(CardLayout homeCardsIn, HomePanelHolder hphIn, String[] slidesIn, JTextArea instructIn)
	{
		homeCards = homeCardsIn; 
        hph = hphIn;
        instruct = instructIn;
        slides = slidesIn;
        counter = 1;
	}
	
	/* Use if else to see which button is clicked; if the user presses
     * the exit button, it will take them back to the home page.
     * if next page or back page button is pressed, there will be nested
     * if-else inside using counter to know which instruction should
     * be displayed*/
    public void actionPerformed(ActionEvent evt)
    {
		String buttonName = new String("");
		buttonName = evt.getActionCommand();
		
        if(buttonName.equals("EXIT"))
        {
            homeCards.show(this.hph, "First");
        }

        else if(buttonName.equals("Next Page"))
        {
            if(counter == 1)
            {
                instruct.setText(slides[1]);
            }
            else if(counter == 2)
            {
                instruct.setText(slides[2]);
            }
            else if(counter == 3)
            {
                instruct.setText(slides[3]);
            }
            else if(counter == 4)
            {
                instruct.setText(slides[4]);
            }
            else if(counter == 5)
            {
                instruct.setText(slides[5]);
            }
            else if(counter == 6)
            {
                instruct.setText(slides[6]);
            }
            else if(counter == 7)
            {
                instruct.setText(slides[7]);
            }
            else if(counter == 8)
            {
                instruct.setText(slides[8]);
            }
            else if(counter == 9)
            {
                instruct.setText(slides[9]);
            }
            if(counter >= 1 && counter < 10)
            {
                counter ++;
			}
        }

        else if(buttonName.equals("Back Page"))
        {
            if(counter == 3)
            {
                instruct.setText(slides[1]);
            }
            else if(counter == 4)
            {
                instruct.setText(slides[2]);
            }
            else if(counter == 5)
            {
                instruct.setText(slides[3]);
            }
            else if(counter == 6)
            {
                instruct.setText(slides[4]);
            }
            else if(counter == 7)
            {
                instruct.setText(slides[5]);
            }
            else if(counter == 8)
            {
                instruct.setText(slides[6]);
            }
            else if(counter == 9)
            {
                instruct.setText(slides[7]);
            }
            else if(counter == 10)
            {
                instruct.setText(slides[8]);
            }
            else if (counter == 2)
            {
                instruct.setText(slides[0]);
            }
            if(counter > 1 && counter <= 10)
            {
				counter --;
			}
        }
    }
}
