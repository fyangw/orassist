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
	private volatile boolean stopped = false;
	final private String prompt;

	public ProcessMonitor(InputStream inputStream, 
			InputStream errorStream,
			OutputStream outputStream,
			String prompt) throws UnsupportedEncodingException {
		this.reader = new InputStreamReader(inputStream, "UTF8");
		this.errReader = new InputStreamReader(errorStream, "UTF8");
		this.writer = new PrintWriter(new OutputStreamWriter(outputStream));
		this.prompt = prompt;
	}

	public void start() {
		new Thread(new Runnable(){
			private InputStreamReader threadReader = reader;
			public void run() {
				try {
					int ch;
					String s = "";
					for (;!stopped && (ch = this.threadReader.read()) != -1;) {
						s += (char)ch;
						if ((char)ch == '\n' || s.equals(prompt)) {
							put(new PipeMessage(s, null, false));
							s = "";
						}
					}
					put(new PipeMessage(null, null, true));
				} catch (IOException e) {
					new RuntimeException(e);
				}
			}}).start();
		new Thread(new Runnable(){
			private InputStreamReader threadReader = errReader;
			public void run() {
				try {
					int ch;
					String s = "";
					for (;!stopped && (ch = this.threadReader.read()) != -1;) {
						s += (char)ch;
						if ((char)ch == '\n' || s.equals(prompt)) {
							put(new PipeMessage(s, null, false));
							s = "";
						}
					}
					put(new PipeMessage(null, null, true));
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
