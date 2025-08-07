/// Ananya Kotla Wrote the Whole File

import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;

class MenuHolder extends JPanel /// Ananya Kotla
{
	/* This class only creates a new CardLayout.
	 * These cards will be for the page when you click buttons
	 * on the gamePage.
	 * This includes the gamePage itself, and the options from where you can 
	 * go from there:
	 * 	tools
	 * 	Learn about Abdominal organs
	 *  Learn about thoracic organs
	 * All of these panels are added to the CardLayout.
	 * Each panel has its own class.
	 *  6 vars are sent through the parameters of StartGame, which includes
	 *  homeCards, the instance of MenuHolder, menuCards, fileAdder, frame and the
	 *  instance of HomePanelHolder.
	 *  These are used to switch between cards.
	 *  Menu cards, fileadder and instance of MenuHolder are the only vars that is sent through 
	 * the rest of the other panels.
	 * In tools, the instance of the class startGame is also sent through.
	 * Other classes are added which are part of the 3 main panels.
	 */
	public MenuHolder(CardLayout homeCardsIn, HomePanelHolder hphIn, FileAdder faIn, JFrame frameIn)
	{
		CardLayout homeCards = homeCardsIn;
		HomePanelHolder hph = hphIn;
		FileAdder fa = faIn;
		JFrame frame = frameIn;
		CardLayout menuCards = new CardLayout();
		setLayout(menuCards);
		StartGame startGamePan = new StartGame(homeCards, this, hph, menuCards, fa, frame);
		add(startGamePan, "gameStart");
		Tools toolPanels = new Tools(menuCards, this, fa, startGamePan);
		add(toolPanels, "Tools");
		TLearningOrgan tlo = new TLearningOrgan(menuCards, this, fa);
		add(tlo, "TOrgan"); 
		ALearningOrgan alo = new ALearningOrgan(menuCards, this, fa);
		add(alo, "AOrgan"); 
	}
}
