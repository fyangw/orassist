package net.sf.orassist;

import java.io.PrintStream;

public class Bash extends Shell {

	public Bash(PrintStream out) throws Exception {
		super(out);
	}

	protected String getExecutableName() {
		return "bash";
	}

	protected String getPrompt() {
		return "$ ";
	}

	protected String[] getEnvironmentVariables () { 
		return new String[]{}; 
	}
	
	protected String prepareCommand(String command) {
		return command;
	}
	
	public String start() throws Exception {
		return super.start() + read();
	}

}
