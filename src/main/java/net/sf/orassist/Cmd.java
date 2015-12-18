package net.sf.orassist;

import java.io.IOException;
import java.io.PrintStream;

public class Cmd extends Shell {

	public Cmd(PipePlug plug) throws Exception {
		super(plug);
	}

	protected String getExecutableName() {
		return "cmd";
	}

	protected String getPrompt() {
		return "^[A-Z]:.*>$";
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

	@Override
	public String stop() throws Exception {
		return command("exit");
	}

	@Override
	protected String getEncoding() {
		return "utf8";
	}

}
