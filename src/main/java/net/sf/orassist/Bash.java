package net.sf.orassist;

import java.io.IOException;
import java.io.PrintStream;

public class Bash extends Shell {

	public Bash(PipePlug plug) throws Exception {
		super(plug);
	}

	protected String getExecutableName() {
		return "bash -c 'bash --norc --noprofile --login -i 2>&1'";
	}

	protected String getPrompt() {
		return "^bash-4.3\\$ $";
	}

	protected String[] getEnvironmentVariables () { 
		return new String[]{"PATH=/usr/bin:/usr/local/bin:/usr/sbin"}; 
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
