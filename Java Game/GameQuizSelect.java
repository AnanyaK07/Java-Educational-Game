/// Ananya Kotla Wrote the Whole File

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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GameQuizSelect extends JPanel /// Ananya Kotla
{
	private JButton nextButton; // button to move on with the quiz
	private String[] abdTermNames; // Stores all the abdominal organ names in this array
	private String[] thrTermNames; // Stores all the thoracic organ names in this array
	private String selectedTerm; // stores the organ that they are going to be quized on
	private int pointCounter; // the amount of points the user has earned
	private JLabel points; // Shows the points on the JPanel
	
	/* Initalizes all field vars as well as the vars that were passed in
	 * through the parameters. Sets the layout and background. Creates the
	 * topPanel, which includes the title, the hearts, and the points, which
	 * are all JLabels that are created. Creates the southPanel where the
	 * exit button and the next button are placed. Creates the class TermSelect
	 * and sets it to the center of the JPanel. Creates a button handler 
	 * class for this class to see if any of the buttons were pressed. Creates
	 * action listeners for each button.
	 */
    public GameQuizSelect(CardLayout menuCardsIn, MenuHolder mhIn, FileAdder faIn, 
						  Dissection disIn, StartGame sgIn, int dissectionNumIn) 
    {
		abdTermNames = new String[] {"Anus", "Cecum", "Gallbladder", "Large Intestine", 
			"Small Intestine", "Spleen", "Stomach", "Liver", "Pancreas", 
			"Umbilical Vein", "Urogenital Opening", "Urogenital Papilla"};
		thrTermNames = new String[] {"Diaphragm", "Lungs (Left)", "Lungs (Right)",
			"Larynx", "Thyroid Gland", "Heart", "Pericardium", "Coronary Artery",
			"Trachea", "Pulmonary Artery", "Thymus Tissue", "Thymus Glands"};
		CardLayout menuCards = menuCardsIn;
		MenuHolder mh = mhIn;
		selectedTerm = new String("");
		FileAdder fa = faIn;
		StartGame sg = sgIn;
		Dissection dis = disIn;
		int dissectionNum = dissectionNumIn;
		pointCounter = dis.returnPointsNum();
        setBackground(new Color(255, 116, 166));
        setLayout(new BorderLayout());

        JPanel fixQTitle = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        fixQTitle.setBackground(new Color(255, 116, 166));
        JLabel quizTitle = new JLabel("Choose Your Card!", JLabel.CENTER);
        quizTitle.setFont(new Font("Calibri", Font.BOLD, 30));
        quizTitle.setForeground(Color.WHITE);
        fixQTitle.add(quizTitle);

        JPanel fixPoints = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        fixPoints.setBackground(new Color(255, 116, 166));
        points = new JLabel();
        setPointCounter(false);
        points.setFont(new Font("Calibri", Font.BOLD, 20));
        points.setForeground(Color.WHITE);
        fixPoints.add(points);
        
        JPanel heartPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        heartPanel.setBackground(new Color(255, 116, 166));
        JLabel hearts = dis.getHearts();
        hearts.setForeground(new Color(255,208,215));
        hearts.setFont(new Font("Arial", Font.BOLD, 30));
        heartPanel.add(hearts);

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
        southPanel.add(addNextButton, BorderLayout.EAST);
        southPanel.add(addButton, BorderLayout.WEST);
        
        JPanel topPanel = new JPanel(new GridLayout(1,3));
        topPanel.setBackground(new Color(255, 116, 166));
        topPanel.add(heartPanel);
        topPanel.add(fixQTitle);
        topPanel.add(fixPoints);
        add(topPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);
        
        TermSelect ts = new TermSelect(this, dissectionNum);
        add(ts, BorderLayout.CENTER);
        GQuizSelectButtonHandler gsqbh = new GQuizSelectButtonHandler(mh, menuCards, 
									this, ts, fa, dis, sg, dissectionNum);
        nextButton.addActionListener(gsqbh);
        goBackMenu.addActionListener(gsqbh);
    }
    
    /* Sets next button visible
     */
    public void enableNextButton()
	{
		nextButton.setVisible(true);
	}
	
	/* Sets next button to invisible
	 */
	public void disableNextButton()
	{
		nextButton.setVisible(false);
	}
	
	/* sets the selected term.
	 */
	public void setTerm(int termNum, int chooseQuizIn)
    {
		if(chooseQuizIn == 1)
		{
			selectedTerm = abdTermNames[termNum];
		}
		else if(chooseQuizIn == 2)
		{
			selectedTerm = thrTermNames[termNum];
		}
	}
	
	/* Returns the selected term as a string.
	 */
	public String getTerm()
    {
		return selectedTerm;
	}
	
	/* increases the points if the user selects the right answer
	 * Also updates the points label.
	 */
	public void setPointCounter(boolean add)
	{
		if(add == true)
		{
			pointCounter = pointCounter + 100;
		}
		points.setText("Points: " +  pointCounter);
	}
	
	/* Returns the number of points 
	 */
	public  int getPointCounter()
	{
		return pointCounter;
	}
}

class TermSelect extends JPanel /// Ananya Kotla
{	  
	private boolean choose; // checks if the user has chosen a term
	private int next; // The number corresponding to the next term that is chosen
	
	/* Initalizes all field variables as well as vars sent through
	 * parameters. Sets layout and background. Creates all 12 organ
	 * JButtons for both dissections. Based on the dissectioNum it chooses
	 * of the dissections and then adds them randomly to the panel using math.random.
	 * Creates a handler class for all 12 buttons and adds action listeners
	 * for all 12 buttons for both dissections.
	 */
	public TermSelect(GameQuizSelect gqsIn, int dissectionNumIn)
	{
		JButton[] allAbdButtons = new JButton[12];
		JButton[] allThrButtons = new JButton[12];
		GameQuizSelect gqs = gqsIn;
		int dissectionNum = dissectionNumIn;
		choose = true;
		next = 0;

		setLayout(new GridLayout(4,3));
		setBackground(new Color(255, 116, 166));
		
		JPanel anusTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
		anusTerm.setBackground(new Color(255, 116, 166));
		JButton anus = new JButton();
		anus.setFont(new Font("Calibri", Font.BOLD, 25));
		anus.setForeground(Color.WHITE);
		anus.setBackground(new Color(255, 178, 204));
		anus.setOpaque(true);
		anus.setBorderPainted(false);
		anus.setPreferredSize(new Dimension(200, 75));
		anusTerm.add(anus);
		allAbdButtons[0] = anus;

		JPanel cecumTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cecumTerm.setBackground(new Color(255, 116, 166));
		JButton cecum = new JButton();
		cecum.setFont(new Font("Calibri", Font.BOLD, 25));
		cecum.setForeground(Color.WHITE);
		cecum.setBackground(new Color(255, 178, 204));
		cecum.setOpaque(true);
		cecum.setBorderPainted(false);
		cecum.setPreferredSize(new Dimension(200, 75));
		cecumTerm.add(cecum);
		allAbdButtons[1] = cecum;

		JPanel gallbladderTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
		gallbladderTerm.setBackground(new Color(255, 116, 166));
		JButton gallbladder = new JButton();
		gallbladder.setFont(new Font("Calibri", Font.BOLD, 25));
		gallbladder.setForeground(Color.WHITE);
		gallbladder.setBackground(new Color(255, 178, 204));
		gallbladder.setOpaque(true);
		gallbladder.setBorderPainted(false);
		gallbladder.setPreferredSize(new Dimension(200, 75));
		gallbladderTerm.add(gallbladder);
		allAbdButtons[2] = gallbladder;

        JPanel largeIntestineTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        largeIntestineTerm.setBackground(new Color(255, 116, 166));
        JButton largeIntestine = new JButton();
        largeIntestine.setFont(new Font("Calibri", Font.BOLD, 25));
        largeIntestine.setForeground(Color.WHITE);
        largeIntestine.setBackground(new Color(255, 178, 204));
        largeIntestine.setOpaque(true);
        largeIntestine.setBorderPainted(false);
        largeIntestine.setPreferredSize(new Dimension(200, 75));
        largeIntestineTerm.add(largeIntestine);
        allAbdButtons[3] = largeIntestine;

        JPanel smallIntestineTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        smallIntestineTerm.setBackground(new Color(255, 116, 166));
        JButton smallIntestine = new JButton();
        smallIntestine.setFont(new Font("Calibri", Font.BOLD, 25));
        smallIntestine.setForeground(Color.WHITE);
        smallIntestine.setBackground(new Color(255, 178, 204));
        smallIntestine.setOpaque(true);
        smallIntestine.setBorderPainted(false);
        smallIntestine.setPreferredSize(new Dimension(200, 75));
        smallIntestineTerm.add(smallIntestine);
        allAbdButtons[4] = smallIntestine;

        JPanel spleenTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        spleenTerm.setBackground(new Color(255, 116, 166));
        JButton spleen = new JButton();
        spleen.setFont(new Font("Calibri", Font.BOLD, 25));
        spleen.setForeground(Color.WHITE);
        spleen.setBackground(new Color(255, 178, 204));
        spleen.setOpaque(true);
        spleen.setBorderPainted(false);
        spleen.setPreferredSize(new Dimension(200, 75));
        spleenTerm.add(spleen);
        allAbdButtons[5] = spleen;

        JPanel stomachTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stomachTerm.setBackground(new Color(255, 116, 166));
        JButton stomach = new JButton();
        stomach.setFont(new Font("Calibri", Font.BOLD, 25));
        stomach.setForeground(Color.WHITE);
        stomach.setBackground(new Color(255, 178, 204));
        stomach.setOpaque(true);
        stomach.setBorderPainted(false);
        stomach.setPreferredSize(new Dimension(200, 75));
        stomachTerm.add(stomach);
        allAbdButtons[6] = stomach;

        JPanel liverTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        liverTerm.setBackground(new Color(255, 116, 166));
        JButton liver = new JButton();
        liver.setFont(new Font("Calibri", Font.BOLD, 25));
        liver.setForeground(Color.WHITE);
        liver.setBackground(new Color(255, 178, 204));
        liver.setOpaque(true);
        liver.setBorderPainted(false);
        liver.setPreferredSize(new Dimension(200, 75));
        liverTerm.add(liver);
        allAbdButtons[7] = liver;

        JPanel pancreasTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pancreasTerm.setBackground(new Color(255, 116, 166));
        JButton pancreas = new JButton();
        pancreas.setFont(new Font("Calibri", Font.BOLD, 25));
        pancreas.setForeground(Color.WHITE);
        pancreas.setBackground(new Color(255, 178, 204));
        pancreas.setOpaque(true);
        pancreas.setBorderPainted(false);
        pancreas.setPreferredSize(new Dimension(200, 75));
        pancreasTerm.add(pancreas);
        allAbdButtons[8] = pancreas;

        JPanel umbilicalVeinTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        umbilicalVeinTerm.setBackground(new Color(255, 116, 166));
        JButton umbilicalVein = new JButton();
        umbilicalVein.setFont(new Font("Calibri", Font.BOLD, 25));
        umbilicalVein.setForeground(Color.WHITE);
        umbilicalVein.setBackground(new Color(255, 178, 204));
        umbilicalVein.setOpaque(true);
        umbilicalVein.setBorderPainted(false);
        umbilicalVein.setPreferredSize(new Dimension(200, 75));
        umbilicalVeinTerm.add(umbilicalVein);
        allAbdButtons[9] = umbilicalVein;

        JPanel urogenitalOpeningTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        urogenitalOpeningTerm.setBackground(new Color(255, 116, 166));
        JButton urogenitalOpening = new JButton();
        urogenitalOpening.setFont(new Font("Calibri", Font.BOLD, 25));
        urogenitalOpening.setForeground(Color.WHITE);
        urogenitalOpening.setBackground(new Color(255, 178, 204));
        urogenitalOpening.setOpaque(true);
        urogenitalOpening.setBorderPainted(false);
        urogenitalOpening.setPreferredSize(new Dimension(200, 75));
        urogenitalOpeningTerm.add(urogenitalOpening);
        allAbdButtons[10] = urogenitalOpening;

        JPanel urogenitalPapillaTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        urogenitalPapillaTerm.setBackground(new Color(255, 116, 166));
        JButton urogenitalPapilla = new JButton();
        urogenitalPapilla.setFont(new Font("Calibri", Font.BOLD, 25));
        urogenitalPapilla.setForeground(Color.WHITE);
        urogenitalPapilla.setBackground(new Color(255, 178, 204));
        urogenitalPapilla.setOpaque(true);
        urogenitalPapilla.setBorderPainted(false);
        urogenitalPapilla.setPreferredSize(new Dimension(200, 75));
        urogenitalPapillaTerm.add(urogenitalPapilla);
        allAbdButtons[11] = urogenitalPapilla;
        
        JPanel diaphragmTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        diaphragmTerm.setBackground(new Color(255, 116, 166));
        JButton diaphragm = new JButton();
        diaphragm.setFont(new Font("Calibri", Font.BOLD, 25));
        diaphragm.setForeground(Color.WHITE);
        diaphragm.setBackground(new Color(255, 178, 204));
        diaphragm.setOpaque(true);
        diaphragm.setBorderPainted(false);
        diaphragm.setPreferredSize(new Dimension(200, 75));
        diaphragmTerm.add(diaphragm);
        allThrButtons[0] = diaphragm;
        
        JPanel lungsLeftTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lungsLeftTerm.setBackground(new Color(255, 116, 166));
        JButton lungsLeft = new JButton();
        lungsLeft.setFont(new Font("Calibri", Font.BOLD, 25));
        lungsLeft.setForeground(Color.WHITE);
        lungsLeft.setBackground(new Color(255, 178, 204));
        lungsLeft.setOpaque(true);
        lungsLeft.setBorderPainted(false);
        lungsLeft.setPreferredSize(new Dimension(200, 75));
        lungsLeftTerm.add(lungsLeft);
        allThrButtons[1] = lungsLeft;
        
        JPanel lungsRightTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lungsRightTerm.setBackground(new Color(255, 116, 166));
        JButton lungsRight = new JButton();
        lungsRight.setFont(new Font("Calibri", Font.BOLD, 25));
        lungsRight.setForeground(Color.WHITE);
        lungsRight.setBackground(new Color(255, 178, 204));
        lungsRight.setOpaque(true);
        lungsRight.setBorderPainted(false);
        lungsRight.setPreferredSize(new Dimension(200, 75));
        lungsRightTerm.add(lungsRight);
        allThrButtons[2] = lungsRight;
        
        JPanel larynxTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        larynxTerm.setBackground(new Color(255, 116, 166));
        JButton larynx = new JButton();
        larynx.setFont(new Font("Calibri", Font.BOLD, 25));
        larynx.setForeground(Color.WHITE);
        larynx.setBackground(new Color(255, 178, 204));
        larynx.setOpaque(true);
        larynx.setBorderPainted(false);
        larynx.setPreferredSize(new Dimension(200, 75));
        larynxTerm.add(larynx);
        allThrButtons[3] = larynx;
        
        JPanel thyroidGlandTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        thyroidGlandTerm.setBackground(new Color(255, 116, 166));
        JButton thyroidGland = new JButton();
        thyroidGland.setFont(new Font("Calibri", Font.BOLD, 25));
        thyroidGland.setForeground(Color.WHITE);
        thyroidGland.setBackground(new Color(255, 178, 204));
        thyroidGland.setOpaque(true);
        thyroidGland.setBorderPainted(false);
        thyroidGland.setPreferredSize(new Dimension(200, 75));
        thyroidGlandTerm.add(thyroidGland);
        allThrButtons[4] = thyroidGland;
        
        JPanel heartTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        heartTerm.setBackground(new Color(255, 116, 166));
        JButton heart = new JButton();
        heart.setFont(new Font("Calibri", Font.BOLD, 25));
        heart.setForeground(Color.WHITE);
        heart.setBackground(new Color(255, 178, 204));
        heart.setOpaque(true);
        heart.setBorderPainted(false);
        heart.setPreferredSize(new Dimension(200, 75));
        heartTerm.add(heart);
        allThrButtons[5] = heart;
        
        JPanel pericardiumTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pericardiumTerm.setBackground(new Color(255, 116, 166));
        JButton pericardium = new JButton();
        pericardium.setFont(new Font("Calibri", Font.BOLD, 25));
        pericardium.setForeground(Color.WHITE);
        pericardium.setBackground(new Color(255, 178, 204));
        pericardium.setOpaque(true);
        pericardium.setBorderPainted(false);
        pericardium.setPreferredSize(new Dimension(200, 75));
        pericardiumTerm.add(pericardium);
        allThrButtons[6] = pericardium;
        
        JPanel coronaryArteryTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        coronaryArteryTerm.setBackground(new Color(255, 116, 166));
        JButton coronaryArtery = new JButton();   
        coronaryArtery.setFont(new Font("Calibri", Font.BOLD, 25));
        coronaryArtery.setForeground(Color.WHITE);
        coronaryArtery.setBackground(new Color(255, 178, 204));
        coronaryArtery.setOpaque(true);
        coronaryArtery.setBorderPainted(false);
        coronaryArtery.setPreferredSize(new Dimension(200, 75));
        coronaryArteryTerm.add(coronaryArtery);
        allThrButtons[7] = coronaryArtery;
        
        JPanel tracheaTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        tracheaTerm.setBackground(new Color(255, 116, 166));
        JButton trachea = new JButton();
        trachea.setFont(new Font("Calibri", Font.BOLD, 25));
        trachea.setForeground(Color.WHITE);
        trachea.setBackground(new Color(255, 178, 204));
        trachea.setOpaque(true);
        trachea.setBorderPainted(false);
        trachea.setPreferredSize(new Dimension(200, 75));
        tracheaTerm.add(trachea);
        allThrButtons[8] = trachea;
        
        JPanel pulmonaryArteryTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pulmonaryArteryTerm.setBackground(new Color(255, 116, 166));
        JButton pulmonaryArtery = new JButton();
        pulmonaryArtery.setFont(new Font("Calibri", Font.BOLD, 25));
        pulmonaryArtery.setForeground(Color.WHITE);
        pulmonaryArtery.setBackground(new Color(255, 178, 204));
        pulmonaryArtery.setOpaque(true);
        pulmonaryArtery.setBorderPainted(false);
        pulmonaryArtery.setPreferredSize(new Dimension(200, 75));
        pulmonaryArteryTerm.add(pulmonaryArtery);
        allThrButtons[9] = pulmonaryArtery;
        
        JPanel thymusTissueTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        thymusTissueTerm.setBackground(new Color(255, 116, 166));
        JButton thymusTissue = new JButton();
        thymusTissue.setFont(new Font("Calibri", Font.BOLD, 25));
        thymusTissue.setForeground(Color.WHITE);
        thymusTissue.setBackground(new Color(255, 178, 204));
        thymusTissue.setOpaque(true);
        thymusTissue.setBorderPainted(false);
        thymusTissue.setPreferredSize(new Dimension(200, 75));
        thymusTissueTerm.add(thymusTissue);
        allThrButtons[10] = thymusTissue;
        
        JPanel thymusGlandsTerm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        thymusGlandsTerm.setBackground(new Color(255, 116, 166));
        JButton thymusGlands = new JButton();
        thymusGlands.setFont(new Font("Calibri", Font.BOLD, 25));
        thymusGlands.setForeground(Color.WHITE);
        thymusGlands.setBackground(new Color(255, 178, 204));
        thymusGlands.setOpaque(true);
        thymusGlands.setBorderPainted(false);
        thymusGlands.setPreferredSize(new Dimension(200, 75));
        thymusGlandsTerm.add(thymusGlands);
        allThrButtons[11] = thymusGlands;
        
        TermSelectButtonHandler tsbh = new TermSelectButtonHandler(anus, 
			cecum, gallbladder, largeIntestine, smallIntestine, spleen, stomach, 
			liver, pancreas, umbilicalVein, urogenitalOpening, urogenitalPapilla,
			diaphragm, lungsLeft, lungsRight, larynx, thyroidGland, heart, 
			pericardium, coronaryArtery, trachea, pulmonaryArtery, 
			thymusTissue, thymusGlands, gqs, this, dissectionNum);
		
		anus.addActionListener(tsbh);
		cecum.addActionListener(tsbh);
		gallbladder.addActionListener(tsbh);
		largeIntestine.addActionListener(tsbh);
		smallIntestine.addActionListener(tsbh);
		spleen.addActionListener(tsbh);
		stomach.addActionListener(tsbh);
		liver.addActionListener(tsbh);
		pancreas.addActionListener(tsbh);
		umbilicalVein.addActionListener(tsbh);
		urogenitalOpening.addActionListener(tsbh);
		urogenitalPapilla.addActionListener(tsbh);
		diaphragm.addActionListener(tsbh);
		lungsLeft.addActionListener(tsbh); 
		lungsRight.addActionListener(tsbh); 
		larynx.addActionListener(tsbh); 
		thyroidGland.addActionListener(tsbh); 
		heart.addActionListener(tsbh);
		pericardium.addActionListener(tsbh); 
		coronaryArtery.addActionListener(tsbh); 
		trachea.addActionListener(tsbh); 
		pulmonaryArtery.addActionListener(tsbh);
		thymusTissue.addActionListener(tsbh); 
		thymusGlands.addActionListener(tsbh);
		
        int randomName = 0;
        int keepGoing = 0;
        while(keepGoing < 12) 
        {
            randomName = (int) (Math.random() * 12 + 0);
            if (allAbdButtons[randomName] != null && allThrButtons[randomName] != null) 
            {
                if (randomName == 0) 
                {
					if(dissectionNum == 1)
					{
						add(anusTerm);
					}
					else if(dissectionNum == 2)
					{
						add(diaphragmTerm);
					}
                } 
                else if (randomName == 1) 
                {
					if(dissectionNum == 1)
					{
						add(cecumTerm);
					}
					else if(dissectionNum == 2)
					{
						add(lungsLeftTerm);
					}
                } 
                else if (randomName == 2) 
                {
					if(dissectionNum == 1)
					{
						add(gallbladderTerm);
					}
					else if(dissectionNum == 2)
					{
						add(lungsRightTerm);
					}
                } 
                else if (randomName == 3) 
                {
					if(dissectionNum == 1)
					{
						add(largeIntestineTerm);
					}
					else if(dissectionNum == 2)
					{
						add(larynxTerm);
					}
                } 
                else if (randomName == 4) 
                {
					
					if(dissectionNum == 1)
					{
						add(smallIntestineTerm);
					}
					else if(dissectionNum == 2)
					{
						add(thyroidGlandTerm);
					}
                } 
                else if (randomName == 5) 
                {
					if(dissectionNum == 1)
					{
						add(spleenTerm);
					}
					else if(dissectionNum == 2)
					{
						add(heartTerm);
					}
                } 
                else if (randomName == 6) 
                {
					if(dissectionNum == 1)
					{
						add(stomachTerm);
					}
					else if(dissectionNum == 2)
					{
						add(pericardiumTerm);
					}
                } 
                else if (randomName == 7) 
                {
					if(dissectionNum == 1)
					{
						add(liverTerm);
					}
					else if(dissectionNum == 2)
					{
						add(coronaryArteryTerm);
					}
                } 
                else if (randomName == 8) 
                {
					if(dissectionNum == 1)
					{
						add(pancreasTerm);
					}
					else if(dissectionNum == 2)
					{
						add(tracheaTerm);
					}
                } 
                else if (randomName == 9) 
                {
					if(dissectionNum == 1)
					{
						add(umbilicalVeinTerm);
					}
					else if(dissectionNum == 2)
					{
						add(pulmonaryArteryTerm);
					}
                } 
                else if (randomName == 10) 
                {
					if(dissectionNum == 1)
					{
						add(urogenitalOpeningTerm);
					}
					else if(dissectionNum == 2)
					{
						add(thymusTissueTerm);
					}
                } 
                else if (randomName == 11) 
                {
					if(dissectionNum == 1)
					{
						add(urogenitalPapillaTerm);
					}
					else if(dissectionNum == 2)
					{
						add(thymusGlandsTerm);
					}
                }
                if(dissectionNum == 1)
                {
					allAbdButtons[randomName] = null;
				}
				else if(dissectionNum == 2)
				{
					allThrButtons[randomName] = null;
				}
                keepGoing++;
            }
        }
    }
    
    /* Returns the boolean if the user has chosen a term.
     */
    public boolean getChoose()
    {
		return choose;
	}
	
	/* Sets the boolean choose.
	 */
	public void setChoose(boolean chooseIn)
    {
		choose = chooseIn;
	}
	
	/* Returns the int that corresponds to the button that was chosen before
	 */
	public int getNext()
	{
		return next;
	}
	
	/* Sets next to the parameter that was sent in.
	 */
	public void setNext(int nextIn)
	{
		next = nextIn;
	}
}

class TermSelectButtonHandler implements ActionListener /// Ananya Kotla
{
	private JButton anus, cecum, gallbladder, largeIntestine, smallIntestine, 
			spleen, stomach, liver, pancreas, umbilicalVein, urogenitalOpening, 
			urogenitalPapilla, diaphragm, lungsLeft, lungsRight, larynx, 
			thyroidGland, heart, pericardium, coronaryArtery, trachea, 
			pulmonaryArtery, thymusTissue, thymusGlands; // The buttons which are part of the quiz
	private boolean choose; // To see if the user has chosen a term
	private int select; // the term the user has selected
	private int[] same; // Checks if the user has click the same term twice
	private	int next; // Corresponds to the terms that were selected before
	private GameQuizSelect gqs; // The instance of the class GameQuizSelect 
	private TermSelect ts; // The instance of the class TermSelect
	private int questionCountNum; // The amount of points the user has.
	private int chooseQuiz; //Chooses quix based on the dissection they completed
	
	public TermSelectButtonHandler(JButton anusIn, JButton cecumIn, JButton gallbladderIn, 
			JButton largeIntestineIn, JButton smallIntestineIn, JButton spleenIn, JButton stomachIn, 
			JButton liverIn, JButton pancreasIn, JButton umbilicalVeinIn, JButton urogenitalOpeningIn, 
			JButton urogenitalPapillaIn, JButton diaphragmIn, JButton lungsLeftIn, JButton lungsRightIn, 
			JButton larynxIn, JButton thyroidGlandIn, JButton heartIn, JButton pericardiumIn, 
			JButton coronaryArteryIn, JButton tracheaIn, JButton pulmonaryArteryIn, 
			JButton thymusTissueIn, JButton thymusGlandsIn, GameQuizSelect gqsIn, TermSelect tsIn,
			int chooseQuizIn)
	{
		anus = anusIn;
		cecum = cecumIn;
		gallbladder = gallbladderIn;
		largeIntestine = largeIntestineIn;
		smallIntestine = smallIntestineIn;
		spleen = spleenIn;
		stomach = stomachIn;
		liver = liverIn;
		pancreas = pancreasIn;
		umbilicalVein = umbilicalVeinIn;
		urogenitalOpening = urogenitalOpeningIn;
		urogenitalPapilla = urogenitalPapillaIn;
		diaphragm = diaphragmIn;
		lungsLeft = lungsLeftIn;
		lungsRight = lungsRightIn;
		larynx = larynxIn;
		thyroidGland = thyroidGlandIn;
		heart = heartIn;
		pericardium = pericardiumIn;
		coronaryArtery = coronaryArteryIn;
		trachea = tracheaIn;
		pulmonaryArtery = pulmonaryArteryIn;
		thymusTissue = thymusTissueIn;
		thymusGlands = thymusGlandsIn;
		
		select = -1;
		choose = false;
		same = new int[] {-2,-2,-2};
		next = 0;
		questionCountNum = 0;
		ts = tsIn;
		gqs = gqsIn;
		chooseQuiz = chooseQuizIn;
	}
	
	/* Checks to see which term the user has selected. Sets that
	 * term's text as well as enables the next button. It also make 
	 * sure that the user clicks a different term for the next round, before
	 * the next button shows up. 
	 */
	public void actionPerformed(ActionEvent evt) 
	{
		JButton clickedTerm = (JButton) evt.getSource();
		choose = ts.getChoose();
	
	
		if(choose == true)
		{
			if (clickedTerm == anus || clickedTerm == diaphragm) 
			{
				if(clickedTerm == anus)
				{
					anus.setText("Anus");
				}
				else
				{
					diaphragm.setText("Diaphragm");
				}
				select = 0;
			}
			else if (clickedTerm == cecum || clickedTerm == lungsLeft) 
			{
				if(clickedTerm == cecum)
				{
					cecum.setText("Cecum");
				}
				else
				{
					lungsLeft.setText("Lungs (Left)");
				}
				select = 1;
			} 
			else if (clickedTerm == gallbladder || clickedTerm == lungsRight) 
			{
				if(clickedTerm == gallbladder)
				{
					gallbladder.setText("Gallbladder");;
				}
				else
				{
					lungsRight.setText("Lungs (Right)");
				}
				select = 2;
			} 
			else if (clickedTerm == largeIntestine || clickedTerm == larynx) 
			{
				if(clickedTerm == largeIntestine)
				{
					largeIntestine.setText("<html><center>Large<br>Intestine<html><center>");
				}
				else
				{
					larynx.setText("Larynx");
				}
				select = 3;
			} 
			else if (clickedTerm == smallIntestine || clickedTerm == thyroidGland) 
			{
				if(clickedTerm == smallIntestine)
				{
					smallIntestine.setText("<html><center>Small<br>Intestine<html><center>");
				}
				else
				{
					thyroidGland.setText("Thyroid Gland");
				}
				select = 4;
			} 
			else if (clickedTerm == spleen || clickedTerm == heart) 
			{
				if(clickedTerm == spleen)
				{
					spleen.setText("Spleen");
				}
				else
				{
					heart.setText("Heart");
				}
				select = 5;
			} 
			else if (clickedTerm == stomach || clickedTerm == pericardium) 
			{
				if(clickedTerm == stomach)
				{
					stomach.setText("Stomach");
				}
				else
				{
					pericardium.setText("Pericardium");
				}
				select = 6;
			}
			else if (clickedTerm == liver || clickedTerm == coronaryArtery) 
			{
				if(clickedTerm == liver)
				{
					liver.setText("Liver");
				}
				else
				{
					coronaryArtery.setText("<html><center>"
									+ "Coronary Artery<html><center>");
				}
				select = 7;
			} 
			else if (clickedTerm == pancreas || clickedTerm == trachea) 
			{
				if(clickedTerm == pancreas)
				{
					pancreas.setText("Pancreas");
				}
				else
				{
					trachea.setText("Trachea");
				}
				select = 8;
			} 
			else if (clickedTerm == umbilicalVein || clickedTerm == pulmonaryArtery) 
			{
				if(clickedTerm == umbilicalVein)
				{
					umbilicalVein.setText("<html><center>"
									+ "Umbilical<br>Vein<html><center>");
				}
				else
				{
					pulmonaryArtery.setText("<html><center>"
									+ "Pulmonary Artery<html><center>");
				}
				select = 9;
			} 
			else if (clickedTerm == urogenitalOpening || clickedTerm == thymusTissue) 
			{
				if(clickedTerm == urogenitalOpening)
				{
					urogenitalOpening.setText("<html><center>"
										+ "Urogenital<br>Opening<html><center>");
				}
				else
				{
					thymusTissue.setText("<html><center>"
										+ "Thymus<br>Tissue<html><center>");
				}
				select = 10;
			} 
			else if (clickedTerm == urogenitalPapilla || clickedTerm == thymusGlands) 
			{
				if(clickedTerm == urogenitalPapilla)
				{
					urogenitalPapilla.setText("<html><center>"
										+ "Urogenital<br>Papilla<html><center>");
				}
				else
				{
					thymusGlands.setText("<html><center>"
										+ "Thymus<br>Glands<html><center>");
				}
				select = 11;
			}
			next = ts.getNext();
			same[next] = select;
			if((next == 0) || (same[0] != same[1] && next == 1) || 
			   (same[1] != same[2] && same[0] != same[2] && next == 2))
			{
				ts.setChoose(false);
				gqs.setTerm(select, chooseQuiz);
				gqs.enableNextButton();
				ts.setNext(next + 1);
			}
		}                          
	}
}

class GQuizSelectButtonHandler implements ActionListener /// Ananya Kotla
{
	private CardLayout menuCards; // The cards for menu panel.
    private MenuHolder mh; // The instance of class where cardLayout for menu panel is stored.
    private GameQuizSelect gqs; // The instance of the class GameQuizSelect
    private TermSelect ts; // The instance of the class TermSelect
    private int endQuiz; // To check if the user has finished the quiz
    private FileAdder fa; // The instance of the class FileAdder
    private Dissection dis; // Instance of class Dissection
    private StartGame sg; //Instance of class Startgame
    private int dissectionNum; // Stores the num of the dissection they just completed
	public GQuizSelectButtonHandler(MenuHolder mhIn, CardLayout menuCardsIn, GameQuizSelect gqsIn,
									TermSelect tsIn, FileAdder faIn, Dissection disIn, StartGame sgIn,
									int dissectionNumIn)
	{
		
		menuCards = menuCardsIn;
		mh = mhIn;
		gqs = gqsIn;
		ts = tsIn;
		endQuiz = -1;
		fa = faIn;
		dis = disIn;
		sg = sgIn;
		dissectionNum = dissectionNumIn;
	}
	
	/* Checks if the user has clicked the exit button or the next button.
	 * First they check if the user has finished the quiz. If the user clicks
	 * exit, it goes back to the menu page. If the user clicks next it makes
	 * the class GameQuiz and adds it to the Cardlayout. It shows that card
	 * and sets choose to true.
	 */
	public void actionPerformed(ActionEvent evt) 
	{
		String buttonNames = new String("");
		buttonNames = evt.getActionCommand();
		
		endQuiz = ts.getNext();
		if(buttonNames.equals("EXIT"))
        {
            menuCards.show(this.mh, "gameStart");
        }
        else if(buttonNames.equals("NEXT"))
        {
			GameQuiz gQuizPanel = new GameQuiz(menuCards, mh, gqs, endQuiz, fa, dis, sg, dissectionNum);
			mh.add(gQuizPanel, "GameQuiz");
			menuCards.show(this.mh, "GameQuiz");
			gqs.disableNextButton();
			ts.setChoose(true);
		}
	}
}
