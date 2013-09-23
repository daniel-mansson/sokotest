package sokoban.data;

import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import util.Vec2;

public class Path {

	private static final Color c1 = Color.green;
	private static final Color c2 = Color.cyan;
	String rawPath;
	ArrayList<Vec2> path;

	public Path(Scanner in) {
		rawPath = in.nextLine();
		path = new ArrayList<Vec2>();
	}
	
	public void prepare(int playerX, int playerY) {
		path.clear();
		Vec2 current = new Vec2(playerX, playerY);
		
		path.add(current.clone());
		
		for(int i = 0; i < rawPath.length(); ++i) {
			char c = rawPath.charAt(i);
			switch(c) {
			case 'U':
				current.y -= 1;
				break;
			case 'D':
				current.y += 1;
				break;
			case 'R':
				current.x += 1;
				break;
			case 'L':
				current.x -= 1;
				break;
			default:
				continue;
			}
			
			path.add(current.clone());
		}
	}
	
	public String getRawPath() {
		return rawPath;
	}
	
	public ArrayList<Vec2> getPath() {
		return path;
	}

	void render(Graphics g) {
		
		if(path.isEmpty())
			return;

		
		float t = 0;
		Vec2 prev = path.get(0);
		for(int i = 1; i < path.size(); ++i) {
			Vec2 pos = path.get(i);
			t = (float)(i - 1) / (float)(path.size() - 2);
			Color c = new Color(c1.r * (1 - t) + c2.r * t, c1.g * (1 - t) + c2.g * t, c1.b * (1 - t) + c2.b * t);
			g.setColor(c);
			g.drawLine(prev.x+0.5f, prev.y+0.5f, pos.x+0.5f, pos.y+0.5f);
			prev = pos;
		}

	}
	
	int getLength() {
		return path.size() - 1;
	}
}
