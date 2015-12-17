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
	protected PipePlug plug;
	private ProcessMonitor monitor;

	public Shell(PipePlug plug) {
		this.plug = plug;
	}
	
	public String start() throws Exception {
        this.process = Runtime.getRuntime().exec(getExecutableName(), getEnvironmentVariables()); 
        this.monitor = new ProcessMonitor(process.getInputStream(), process.getErrorStream(), process.getOutputStream());
        monitor.start();
		return "";
	}

	protected abstract String[] getEnvironmentVariables();
	protected abstract String getExecutableName();
	protected abstract String getPrompt();
	protected abstract String prepareCommand(String command) throws IOException;
	
	public String read() throws Exception {
        StringBuffer edit = new StringBuffer();
        StringBuffer line = new StringBuffer();
		for (int ch; (ch = monitor.get().value()) != -1;) {
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
	
	protected void log(char ch) throws IOException {
		plug.out(ch);
	}
	protected void log(CharSequence str) throws IOException {
		plug.out(str.toString());
	}
	
	public String command(String command) throws Exception {
		command = prepareCommand(command);
		monitor.write(command);
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
