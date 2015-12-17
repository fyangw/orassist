package net.sf.orassist;

public class PipeMessage {
	private Integer stdinMessage;
	private Integer stderrMessage;
	
	public PipeMessage(Integer stdinMessage, 
			Integer stderrMessage) {
		this.stdinMessage = stdinMessage;
		this.stderrMessage = stderrMessage;
	}
	
	public Integer getStdinMessage() {
		return stdinMessage;
	}
	public Integer getStderrMessage() {
		return stderrMessage;
	}

	public boolean eof() {
		return value() == -1;
	}
	
	public Integer value() {
		return stdinMessage != null ? stdinMessage : stderrMessage;
	}

}
