package sokoban.reader;

import java.util.Scanner;

import sokoban.data.EntryQueue;
import sokoban.data.SokobanEntry;

public class ReaderStdin implements Reader {

	private EntryQueue queue;
	private volatile boolean error;
	private String errorMessage;

	public ReaderStdin(EntryQueue queue) {
		this.queue = queue;
		error = false;
		errorMessage = "";
	}

	@Override
	public void run() {
		Scanner in = new Scanner(System.in);

		try {
		while (true) {
			try {
				queue.push(new SokobanEntry(in));			
			}
			catch (SokobanEntry.RetryException e) {
			}
		}
		}
		catch(Exception e) {
			errorMessage = e.getMessage();
		}
		finally {
			in.close();
			error = true;
		}
	}


	@Override
	public void onClear() {
	}

	@Override
	public boolean hasError() {
		return error;
	}

	@Override
	public String getError() {
		return errorMessage;
	}
}
