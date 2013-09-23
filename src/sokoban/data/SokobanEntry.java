package sokoban.data;

import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class SokobanEntry {

	public class RetryException extends Exception {
		private static final long serialVersionUID = 2089082273171855207L;	
	}
	private static final Color playerColors[] = { new Color(0, 200, 0), Color.green };
	
	private String name;
	private int playerX;
	private int playerY;
	private State state;
	private Path path;
	private ArrayList<Marking> markings;

	public SokobanEntry(Scanner in) throws RetryException {
		String header = "";

		header = in.nextLine();
		if(!header.equals(";begin")) {
			System.err.println("Invalid begin header: " + header);
			throw new RetryException();
		}
		
		markings = new ArrayList<Marking>();
		
		//Part of begin header;
		name = in.nextLine();
		
		while(true) {		
			header = in.nextLine();

			if(header.equals(";player")) {
				playerX = in.nextInt();
				playerY = in.nextInt();
				in.nextLine();
			}
			else if(header.equals(";state")) {
				state = new State(in);
			}
			else if(header.equals(";path")) {
				path = new Path(in);
			}
			else if(header.equals(";marking")) {
				markings.add(new Marking(in));
			}
			else if(header.equals(";end")) {
				break;
			}
			else {
				System.out.println("Unsupported header: " + header);
			}
		}	
		
		if(path != null)
			path.prepare(playerX, playerY);
	}
	
	public void render(Graphics g, int markingDrawFlags) {
		
		if(state != null)
			state.render(g);
		
		for(Marking m : markings) {
			if(((1 << m.id) & markingDrawFlags) != 0)
				m.render(g);
		}

		if(path != null)
			path.render(g);
		
		g.setColor(playerColors[0]);
		g.fillOval(playerX+0.05f, playerY+0.05f, 0.9f, 0.9f);
		g.setColor(playerColors[1]);
		g.drawOval(playerX+0.05f, playerY+0.05f, 0.9f, 0.9f);

	}
	
	public String getName() {
		return name;
	}
	
	public int getPathLength() {
		if(path == null)
			return -1;
		else
			return path.getLength();
	}
}
