package sokoban.data;

import java.util.LinkedList;

public class EntryQueue {

	private LinkedList<SokobanEntry> queue;
	private volatile boolean lock;
	
	public EntryQueue() {
		queue = new LinkedList<SokobanEntry>();
		lock = false;
	}
	
	/**
	 * @return Returns null if empty or busy
	 */
	public SokobanEntry pop() {
		if(!lock) {
			lock = true;
			SokobanEntry e = queue.pollFirst();
			lock = false;
			return e;
		}
		
		return null;
	}
	
	/**
	 * Blocks until successful
	 */
	public void push(SokobanEntry e) {
		while(lock) {
			Thread.yield();
		}
		lock = true;
		queue.addLast(e);
		lock = false;
	}
}
