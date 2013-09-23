package sokoban.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import sokoban.data.EntryQueue;
import sokoban.data.SokobanEntry;

public class ReaderFile implements Reader {

	EntryQueue queue;
	private volatile int count;
	private volatile int ignore;
	private volatile boolean reset;
	private volatile boolean error;
	private String filename;
	private volatile String errorMessage;

	public ReaderFile(EntryQueue queue, String filename) {
		this.queue = queue;
		count = 0;
		ignore = 0;
		reset = false;
		error = false;
		this.filename = filename;
		errorMessage = "";
	}

	@Override
	public void run() {
		Scanner in;
		try {
			in = new Scanner(new FileInputStream(filename));

			while (true) {
				try {

					try {
						SokobanEntry entry = new SokobanEntry(in);

						if (reset) {
							reset = false;
							throw new Exception();
						}

						if (ignore > 0) {
							--ignore;
						}
						else {
							++count;
							queue.push(entry);
						}
					}
					catch (SokobanEntry.RetryException e) {

					}
				}
				catch (Exception e) {
					try {
						Thread.sleep(250);
					}
					catch (InterruptedException e2) {
					}

					try {
						in = new Scanner(new FileInputStream(filename));
						ignore = count;
					}
					catch (FileNotFoundException e1) {
					}
				}
			}

		}
		catch (Exception e3) {
			errorMessage = e3.getMessage();
		}
		finally {
			error = true;
		}
	}

	@Override
	public void onClear() {
		reset = true;
		count = 0;
		ignore = 0;
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
