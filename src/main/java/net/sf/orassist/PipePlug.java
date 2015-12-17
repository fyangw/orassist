package net.sf.orassist;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class PipePlug {
	
	private Reader stdin;
	private Writer stdout;
	private Writer stderr;

	public PipePlug(Reader stdin, Writer stdout, Writer stderr) {
		this.stdin = stdin;
		this.stdout = stdout;
		this.stderr = stderr;
	}

	public void out(char ch) throws IOException {
		stdout.append(ch);
		stdout.flush();
	}
	
	public void out(CharSequence s) throws IOException {
		stdout.append(s);
		stdout.flush();
	}

	public void err(char ch) throws IOException {
		stderr.write(ch);
		stderr.flush();
	}
	
	public void err(CharSequence s) throws IOException {
		stdout.append(s);
		stdout.flush();
	}

	public int read() throws IOException {
		return stdin.read();
	}

}
