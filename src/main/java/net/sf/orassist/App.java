package net.sf.orassist;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.concurrent.Callable;

public class App
{
    public static void main(String[] args)
    {
        try {
        	//new App().sqlplusDemo();
        	//new App().rmanDemo();
        	new App().bashDemo();
        } catch (Throwable e) {
        	new RuntimeException(e);
        }
    }
    
    public void sqlplusDemo() throws Exception {
    	Shell sqlplus = new Sqlplus(System.out);
    	sqlplus.start();
    	String[] texts = sqlplus.commands(new String[] {
			"connect / as sysdba",
			"set lin 150",
			"set pages 9999",
			"set timi on",
			"select * from dual",
			"quit"
		});

    }
    
    public void rmanDemo() throws Exception {
    	Shell rman = new Rman(System.out);
    	rman.start();
    	String[] texts = rman.commands(new String[] {
    		"connect target /",
    		//"backup as compressed backupset incremental level 0 database plus archivelog delete input",
    		"delete noprompt obsolete",
    		"list backupset",
    		"quit"
    	});
    	
    }
    
    public void bashDemo() throws Exception {
    	Shell bash = new Bash(System.out);
    	bash.start();
    	String[] texts = bash.commands(new String[] {
    		"ls -la",
    		"exit"
    	});
    	
    }
}
