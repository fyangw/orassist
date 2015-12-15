package net.sf.orassist;

import java.io.PrintStream;

public class Sqlplus extends Shell {

	public Sqlplus(PrintStream out) throws Exception {
		super(out);

		// wait for prompt and put logo info to log
		read();
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
	
	protected String prepareCommand(String command) {
		if (!command.endsWith(";")) {
			command += ";\n";
		} else {
			command += "\n";
		}
		return command;
	}

}
