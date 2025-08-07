import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Image;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileWriter;

class FileAdder /// Both Ananya Kotla and Ella Yao
{
	/* This class contains static methods and is used to create
	 * all the try-catch blocks to make all the scanners, printwriters, 
	 * and images used in the game.
	 */
	public FileAdder()
	{

	}
	
	/* Tries to read one of the game's txt file using try-catch 
	 * Creates a new scanner to read the specific file that 
	 * was sent through the parameters and then it returns that
	 * scanner.
	 */
	public static Scanner lookAtFile(String fileName) /// Ella Yao
    {
		Scanner fileRead = null;
        File listOfFile = new File(fileName);
        try
        {
           fileRead = new Scanner(listOfFile);
        }
        catch(FileNotFoundException e)
        {
            System.err.println("Cannot find " + fileName + " file.");
            System.exit(1);
        }
        return fileRead;
    }

    
    
	/* Creates a String with the name of the picture;
	 * Creates a new File, with that pictureName
	 * Uses a try-catch block to find a file with that name
	 * If it is not found and error statement will be
	 * printed.
	 */
	public static Image getGameImages(String pictName) /// Ananya Kotla
	{
		Image picture = null;
        File pictFile = new File(pictName);
        try 
        {
			picture = ImageIO.read(pictFile);
        } 
        catch (IOException e) 
        {
			System.err.println("\n\n " + pictName + " can't be found.\n\n");
			e.printStackTrace();
        }
		return picture;
	}
	
	
	
	/* Creates a String with the name of the scores txt file.
	 * Creates a new File, with that string.
	 * Uses a try-catch block to find a file with that name
	 * and read it using a printWriter. If it is not found and error statement will be
	 * printed.
	 */
	public static PrintWriter addScoresList() /// Ananya Kotla
	{
		PrintWriter scoresAdd = null;
		String outFileName = new String("Scores.txt");
		File outFile = new File(outFileName);
		try
		{
			scoresAdd = new PrintWriter(new FileWriter(outFile, true));
		}
		catch(IOException e)
		{
			System.err.println("Cannot append to " + outFileName + "file.");
			System.exit(6);
		}
		return scoresAdd;
	}	
}
