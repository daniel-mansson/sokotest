package sokoban;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import sokoban.data.EntryQueue;
import sokoban.data.SokobanEntry;
import sokoban.reader.Reader;
import sokoban.reader.ReaderFile;
import sokoban.reader.ReaderStdin;

public class Sokoban extends BasicGame {
	
	ArrayList<SokobanEntry> entries;
	EntryQueue queue;
	float scale;
	int current;
	boolean drawList;
	boolean autoChange;
	Reader reader;
	int markingDrawFlags;
	private static final int listCount = 40;

	public Sokoban(String title, boolean fileReader, String filename) {
		super(title);

		scale = 20.0f;
		entries = new ArrayList<SokobanEntry>();
		queue = new EntryQueue();
		current = -1;
		drawList = true;
		autoChange = true;
		markingDrawFlags = 0xffffffff;

		if(fileReader)
			reader = new ReaderFile(queue, filename);
		else
			reader = new ReaderStdin(queue);
	}
	
	@Override
	public void init(GameContainer container) throws SlickException {
		new Thread(reader).start();
		container.getInput().enableKeyRepeat();
	}

	@Override
	public void render(GameContainer container, Graphics graphics) throws SlickException {

		if(current >= 0 && current < entries.size()) {
			SokobanEntry entry = entries.get(current);

			graphics.scale(scale, scale);
			entry.render(graphics, markingDrawFlags);

			graphics.resetTransform();
			graphics.setColor(Color.white);
			graphics.drawString("Id: " + entry.getName() + "  Path length: " + entry.getPathLength(), 10, container.getHeight() - 77);
		}
		try {
			Thread.sleep(5);
		}
		catch (InterruptedException ie) {
		}
		graphics.scale(1.0f, 1.0f);
		
		graphics.setColor(Color.white);
		graphics.drawString("Active markings: ", 10, container.getHeight() - 58);
		for(int i = 1; i <= 10; ++i) {
			graphics.setColor(((markingDrawFlags & (1 << (i % 10))) != 0) ? Color.white : Color.darkGray);			
			graphics.drawString("" + (i % 10), 140 + i * 20, container.getHeight() - 58);
		}
		
		graphics.setColor(Color.white);
		graphics.drawString("Zoom: +/-    Toggle list: L    Clear data: C    Auto change(" + (autoChange ? "on" : "off") + "): A", 10, container.getHeight() - 39);
		graphics.drawString("Change state: 1: Up/Down    5: Shift + Up/Down    One page: Left/Right    First/last: Shift + Left/Right", 10, container.getHeight() - 20);
		
		if(reader.hasError()) {
			graphics.setColor(Color.orange);
			graphics.drawString("Input error! " + reader.getError(), 10, container.getHeight() - 100);
		}
		
		if(drawList) {
			int low = current - listCount/2;
			int high = current + listCount/2;
			
			if(low < 0) {
				high -= low;
			}
			if(high > entries.size()) {
				low -= (high - entries.size());
			}

			if(low < 0)
				low = 0;
			if(high > entries.size())
				high = entries.size();
			
			graphics.resetTransform();
			for(int i = low; i < high; ++i) {
				graphics.setColor(i == current ? Color.red : Color.white);
				
				SokobanEntry entry = entries.get(i);	
				
				String s = i + ": ";		
				int k = Math.max(2 - (int)Math.log10(i==0?1:i), 0);
				for(int j = 0; j < k; ++j)
					s += " ";
				s += entry.getName() + "  Path: " + entry.getPathLength();
				
				graphics.drawString(s, container.getWidth() - 300, 7 + (i - low) * 15);			
			}
 		}
	}


	@Override
	public void update(GameContainer container, int dt) throws SlickException {
		SokobanEntry entry = queue.pop();
		if(entry != null) {
			entries.add(entry);
			if(autoChange)
				current = entries.size() - 1;
		}
		
		try {
			Thread.sleep(5);
		}
		catch (InterruptedException ie) {
		}
		
		float d = (float)dt/1000.0f;
		Input input = container.getInput();
		if(input.isKeyDown(Keyboard.KEY_MINUS) || input.isKeyDown(Keyboard.KEY_SUBTRACT) || input.isKeyDown(Keyboard.KEY_COMMA))
			scale -= (scale * d);
		if(input.isKeyDown(Keyboard.KEY_ADD) || input.isKeyDown(13) || input.isKeyDown(Keyboard.KEY_PERIOD))
			scale += (scale * d);


		if(input.isKeyPressed(Keyboard.KEY_UP)) {
				if(input.isKeyDown(Keyboard.KEY_RSHIFT) || input.isKeyDown(Keyboard.KEY_LSHIFT) ) 
					current -= 5;
				else
					--current;
				
				if(current < 0)
					current = 0;
		}

		if (input.isKeyPressed(Keyboard.KEY_LEFT)) {
			if(input.isKeyDown(Keyboard.KEY_RSHIFT) || input.isKeyDown(Keyboard.KEY_LSHIFT) ) 
				current = 0;
			else
				current -= listCount;
			
			if(current < 0)
				current = 0;
		}

		if(input.isKeyPressed(Keyboard.KEY_DOWN)) {
				if(input.isKeyDown(Keyboard.KEY_RSHIFT) || input.isKeyDown(Keyboard.KEY_LSHIFT) ) 
					current += 5;
				else
					++current;
				
				if(current >= entries.size())
					current = entries.size() - 1;
		}
		
		if (input.isKeyPressed(Keyboard.KEY_RIGHT)) {
			if(input.isKeyDown(Keyboard.KEY_RSHIFT) || input.isKeyDown(Keyboard.KEY_LSHIFT) ) 
				current = entries.size() - 1;
			else
				current += listCount;
			
			if(current >= entries.size())
				current = entries.size() - 1;
		}

		if(input.isKeyPressed(Keyboard.KEY_L)) {
			drawList = !drawList;
		}

		if(input.isKeyPressed(Keyboard.KEY_A)) {
			autoChange = !autoChange;
		}

		if(input.isKeyPressed(Keyboard.KEY_C)) {
			entries.clear();
			reader.onClear();
		}

		for(int i = 1; i <= 10; ++i) {
			if(input.isKeyPressed(i + 1)) {
				if((markingDrawFlags & (1 << (i%10))) == 0) 
					markingDrawFlags |= (1 << (i%10));
				else
					markingDrawFlags &= ~(1 << (i%10));
			}
		}
	}

	public void cleanUp() {
		
	}
}
