package net.sf.orassist;

import java.io.IOException;
import java.io.PrintStream;

public class Sqlplus extends Shell {

	public Sqlplus(PipePlug term) throws Exception {
		super(term);
	}

	protected String getExecutableName() {
		return "sqlplus /nolog";
	}

	protected String getPrompt() {
		return "SQL> ";
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
	
	public String start() throws Exception {
		return super.start() + read();
	}

	public String stop() throws Exception {
		return command("exit");
	}
}
