package net.sf.orassist;

public class PipeMessage {
	private String stdinMessage;
	private String stderrMessage;
	private boolean eof;
	
	public PipeMessage(String stdinMessage, 
			String stderrMessage, boolean eof) {
		this.stdinMessage = stdinMessage;
		this.stderrMessage = stderrMessage;
		this.eof = eof;
	}
	
	public String getStdinMessage() {
		return stdinMessage;
	}
	public String getStderrMessage() {
		return stderrMessage;
	}

	public boolean isEof() {
		return eof;
	}
	
	public String value() {
		return stdinMessage != null ? stdinMessage : stderrMessage;
	}

}
