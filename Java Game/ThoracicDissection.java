import java.awt.Color;
import java.awt.Font;

import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ThoracicDissection extends JPanel /// Ella Yao
{
	/* Initializes variables that were in parameters
	 * set the Layout the Border Layout
	 * Created the home button and added it to the south
	 * Add ActionListener to the home button
	 */
	public ThoracicDissection(CardLayout menuCardsIn, MenuHolder mhIn)
    {
		CardLayout menuCards = menuCardsIn;
		MenuHolder mh = mhIn;
		
		setBackground(new Color(255, 242, 227));
		setLayout(new BorderLayout());
		ThoracicButtonHandler tbh = new ThoracicButtonHandler(menuCards, mh);
		
		JPanel addButton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		addButton.setBackground(new Color(255, 242, 227));
		JButton goBackMenu = new JButton("EXIT");
		goBackMenu.setBackground(new Color(255, 208, 215));
        goBackMenu.setFont(new Font("Calibri", Font.BOLD, 40));
        goBackMenu.setOpaque(true);
        goBackMenu.setBorderPainted(false);
        goBackMenu.setForeground(new Color(255, 109, 156));
        goBackMenu.setPreferredSize(new Dimension(150, 60));
        goBackMenu.addActionListener(tbh);
        addButton.add(goBackMenu);
        add(addButton, BorderLayout.SOUTH);
	}
}

class ThoracicButtonHandler implements ActionListener /// Ella Yao
{
	private MenuHolder mh; // The instance of class where cardLayout for menu panel is stored.
    private CardLayout menuCards; // The cards for menu panel.
	
	public ThoracicButtonHandler(CardLayout menuCardsIn, MenuHolder mhIn)
	{
		menuCards = menuCardsIn;
		mh = mhIn;
	}
	
	/* If exit button is pressed, it will take you back to the
	 * game page
	 */
	public void actionPerformed(ActionEvent evt)
    {
		String buttonNames = new String("");
		buttonNames = evt.getActionCommand();
		
        if(buttonNames.equals("EXIT"))
        {
            menuCards.show(this.mh, "gameStart");
        }
    }
}
