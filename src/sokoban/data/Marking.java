package sokoban.data;

import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import util.Vec2;

public class Marking {
	
	int id;
	ArrayList<Vec2> points;
	Color color;

	public Marking(Scanner in) {
		id = in.nextInt();	
		color = new Color(in.nextInt(), in.nextInt(), in.nextInt());
		color.a = 0.6f;
		
		points = new ArrayList<Vec2>();
		
		int count = in.nextInt();
		for(int i = 0; i < count; ++i) {
			points.add(new Vec2(in.nextInt(), in.nextInt()));
		}
		
		in.nextLine();
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		
		for(Vec2 p : points) {
			g.fillRect(p.x+0.05f+((float)id/40.0f), p.y+0.05f+((float)id/40.0f), 0.6f, 0.6f);
		}
	}
	
	public int getId() {
		return id;
	}
}
