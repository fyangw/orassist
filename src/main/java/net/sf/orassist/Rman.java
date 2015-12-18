package net.sf.orassist;

import java.io.IOException;
import java.io.PrintStream;

public class Rman extends Shell {

	public Rman(PipePlug plug) throws Exception {
		super(plug);

		// wait for prompt and put logo info to log
		read();
	}

	protected String getExecutableName() {
		return "rman";
	}

	protected String getPrompt() {
		return "RMAN> ";
	}

	protected String[] getEnvironmentVariables () { 
		return new String[]{"NLS_LANG=American_America.UTF8"}; 
		//Simplified Chinese_China.UTF8, Japanese_Japan.UTF8 
	}
	
	protected String prepareCommand(String command) throws IOException {
		if (!command.endsWith(";")) {
			command += ";\n";
		} else {
			command += "\n";
		}
		log(command);
		return command;
	}

	@Override
	public String stop() throws Exception {
		return command("exit");
	}

}
