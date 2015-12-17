package net.sf.orassist;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProcessMonitor {

	volatile private List<PipeMessage> messageQueue = new LinkedList<PipeMessage>();
	
	synchronized public void put(PipeMessage message) {
		messageQueue.add(message);
		notify();
	}
	
	synchronized public PipeMessage get() {
		while (messageQueue.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// got notified
			}
		}
		return messageQueue.remove(0);
	}
	
	private InputStreamReader reader;
	private InputStreamReader errReader;
	private PrintWriter writer;
	private boolean stopped = false;

	public ProcessMonitor(InputStream inputStream, 
			InputStream errorStream,
			OutputStream outputStream) throws UnsupportedEncodingException {
		this.reader = new InputStreamReader(inputStream, "UTF8");
		this.errReader = new InputStreamReader(errorStream, "UTF8");
		this.writer = new PrintWriter(new OutputStreamWriter(outputStream));
	}

	public void start() {
		new Thread(new Runnable(){
			public void run() {
				try {
					int ch = 0;
					while (!stopped && ch != -1) {
						put(new PipeMessage(ch = reader.read(), null));
					}
				} catch (IOException e) {
					new RuntimeException(e);
				}
			}}).start();
		new Thread(new Runnable(){
			public void run() {
				try {
					int ch = 0;
					while (!stopped && ch != -1) {
						put(new PipeMessage(null, ch = errReader.read()));
					}
				} catch (IOException e) {
					new RuntimeException(e);
				}
			}}).start();
	}
	
	public void stop() {
		stopped = true;
	}
	
	public void write(CharSequence message) {
		writer.append(message);
		writer.flush();
	}	
}
