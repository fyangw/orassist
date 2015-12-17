package net.sf.orassist;

import java.io.IOException;
import java.io.PrintStream;

public class Bash extends Shell {

	public Bash(PipePlug plug) throws Exception {
		super(plug);
	}

	protected String getExecutableName() {
		return "sh -i";
	}

	protected String getPrompt() {
		return "sh-4.3$ ";
	}

	protected String[] getEnvironmentVariables () { 
		return new String[]{}; 
	}
	
	protected String prepareCommand(String command) throws IOException {
		return command + "\n";
	}
	
	public String start() throws Exception {
		return super.start() + read();
	}

}
