package sokoban.reader;

public interface Reader extends Runnable {
	void onClear();
	boolean hasError();
	String getError();
}
