package sokoban.data;

import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class State {

	//Bitwise combination: goal - 1, box - 2, wall - 4 
	//                                       0 floor      1 goal       2 box                       3 goal|box                4 wall       5 invalid    6 invalid    7 invalid
	private static final Color color[] =   { Color.black, Color.blue, new Color(200, 90, 20), 	new Color(240, 100, 20), 	Color.gray, 		Color.magenta, 	Color.magenta, Color.magenta };
	private static final Color outline[] = { Color.black, Color.blue, Color.red, 				Color.yellow, 				Color.darkGray, 	Color.cyan, 	Color.cyan, Color.cyan };
	private ArrayList<String> rawState;

	public State(Scanner in) {
		rawState = new ArrayList<String>();
		String line = "";
		
		line = in.nextLine();
		while(!line.equals("-")) {
			rawState.add(line);
			line = in.nextLine();
		}
	}
	
	public ArrayList<String> getRawState() {
		return rawState;
	}
	
	void render(Graphics g) {
		int y = 0;
		for(String s : rawState) {	
			for(int i = 0; i < s.length(); ++i) {
				char c = s.charAt(i);
				int value = 0;
				if(c == '#')
					value = 4;
				else if(c == ' ')
					value = 0;
				else if(c == '$')
					value = 2;
				else if(c == '.' || c == '+')
					value = 1;
				else if(c == '*')
					value = 3;
				
				boolean goal = (value & 1) != 0;
				
				if((value & 2) == 0)
					value &= ~1;

				if((value & 2) != 0) {
					g.setColor(color[value]);
					g.fillRect(i+0.05f, y+0.05f, 0.9f, 0.9f);
					g.setColor(outline[value]);
					g.drawRect(i + 0.01f+0.05f, y + 0.01f+0.05f, 0.9f - 0.02f, 0.9f - 0.02f);
				}
				else {
					g.setColor(color[value]);
					g.fillRect(i - 0.01f, y - 0.01f, 1.0f - 0.02f, 1.0f - 0.02f);
					g.setColor(outline[value]);
					g.drawRect(i - 0.01f, y - 0.01f, 1.0f - 0.02f, 1.0f - 0.02f);			
				}
				if(goal) {
					g.setColor(color[1]);
					g.fillOval(i + 0.25f, y + 0.25f, 0.5f, 0.5f);
					g.setColor(outline[1]);
					g.drawOval(i + 0.25f, y + 0.25f, 0.5f, 0.5f);
				}
			}
			
			++y;
		}
	}
}
