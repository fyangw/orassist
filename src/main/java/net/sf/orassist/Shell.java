package net.sf.orassist;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.CharBuffer;
import java.util.ArrayList;

public abstract class Shell {
	private Process process;
	private PrintWriter writer;
	private InputStreamReader reader;
	private InputStreamReader errReader;
	private PrintWriter logWriter;

	public Shell(PrintStream out) {
		this.logWriter = new PrintWriter(new OutputStreamWriter(out));
	}
	
	public String start() throws Exception {
        this.process = Runtime.getRuntime().exec(getExecutableName(), getEnvironmentVariables()); 
        this.reader = new InputStreamReader(process.getInputStream(), "UTF8");
        this.errReader = new InputStreamReader(process.getErrorStream(), "UTF8");
        this.writer = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));
		return "";
	}

	protected abstract String[] getEnvironmentVariables();
	protected abstract String getExecutableName();
	protected abstract String getPrompt();
	protected abstract String prepareCommand(String command);
	
	public String read() throws Exception {
        StringBuffer edit = new StringBuffer();
        StringBuffer line = new StringBuffer();
        for (int ch; (ch = reader.read()) != -1;) {
        	log((char)ch);
    		line.append((char)ch);
        	if ((char)ch == '\n') {
        		edit.append(line);
        		line.delete(0, line.length());
        	}

        	if (line.toString().equals(getPrompt())) {
        		break;
        	}
        }
        return edit.toString();		
	}
	
	private void log(char ch) {
		logWriter.write(ch);
		logWriter.flush();
	}
	private void log(CharSequence str) {
		logWriter.write(str.toString());
		logWriter.flush();
	}
	
	public String command(String command) throws Exception {
		command = prepareCommand(command);
		log(command);
		writer.write(command);
		writer.flush();
		return read();
	}
	
	public String[] commands(String[] commands) throws Exception {
		ArrayList<String> retArr = new ArrayList<String>();
		for (String command : commands) {
			retArr.add(command(command));
		}
		return retArr.toArray(new String[retArr.size()]);
	}

}
