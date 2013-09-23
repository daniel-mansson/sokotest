package sokoban;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;


public class Main {

	public static void main(String[] args) {
		
		boolean useFile = args.length != 0 && args[0].equals("-file");
		
		if(useFile == true && args.length < 2) {
			System.out.println("File name required.");
			return;
		}
		
		if(useFile)
			System.out.println("Polling file " + args[1] + ".");
		else
			System.out.println("Reading from stdin.");
	
		Sokoban game = new Sokoban("Let's debug some SOKOBAN!", useFile, args.length >= 2 ? args[1] : null);
		try {
			AppGameContainer app = new AppGameContainer(game);
			app.setDisplayMode(1024, 650, false);
			app.setForceExit(false);
			app.setAlwaysRender(true);
			app.setShowFPS(false);
			app.start();
		}
		catch(SlickException e) {
			e.printStackTrace();
		}
		finally {
			game.cleanUp();
			System.exit(0);
		}
	} 
}
