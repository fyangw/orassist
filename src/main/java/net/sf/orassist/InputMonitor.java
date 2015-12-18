package net.sf.orassist;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

public class InputMonitor {

	static Logger logger = Logger.getLogger(InputMonitor.class.getCanonicalName());
	
	private String prompt;
	private InputStreamReader reader;
    StringBuilder edit;
    StringBuilder line;

	public InputMonitor(InputStream stream, String prompt) throws UnsupportedEncodingException {
		this.reader = new InputStreamReader(stream, "utf-8");
		this.prompt = prompt;
	}

	public String read() throws Exception {
		line = new StringBuilder();
		
		for (int ch; (ch = reader.read()) != -1;) {
        	log((char)ch);
        	
    		line.append((char)ch);
    		
        	if ((char)ch == '\n') {
        		onLineFeed(line);
        		line.delete(0, line.length());
        	}
        	
        	if (prompt != null && line.toString().equals(prompt)) {
        		onPrompt(line);
        		line.delete(0, line.length());
        		break;
        	}
        }
        return edit.toString();		
	}

	private void onPrompt(StringBuilder line) {
	}

	private void onLineFeed(StringBuilder line) {
		edit.append(line);
		logger.info(line.toString());
	}

	private void log(char ch) {
		System.out.print(ch);
	}
}
