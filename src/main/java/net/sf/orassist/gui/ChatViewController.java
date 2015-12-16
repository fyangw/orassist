package net.sf.orassist.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import net.sf.orassist.Sqlplus;

public class ChatViewController extends JFrame {

    public static void main(String[] args) {
        new ChatViewController();
    }

    private JScrollPane timelinePane;
    private JPanel inputPanel;
    private JTextField commandText;
    private JButton goButton;
    private ChatViewController model = this;
    private Sqlplus sqlplus;
    private JTextPane consoleText; 
    @SuppressWarnings("serial")
    private SimpleAttributeSet attrSet = new SimpleAttributeSet();

    public ChatViewController() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);//DISPOSE_ON_CLOSE

        this.setLayout(new BorderLayout());
        this.add(timelinePane = new JScrollPane(consoleText = new JTextPane()),BorderLayout.CENTER);
        StyleConstants.setForeground(attrSet, Color.DARK_GRAY);
        StyleConstants.setBold(attrSet, false);
        StyleConstants.setFontSize(attrSet, 16);
        StyleConstants.setFontFamily(attrSet, "Courier New"); //"黑体"

        consoleText.setEditable(false);
        timelinePane.setHorizontalScrollBarPolicy( 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
        timelinePane.setVerticalScrollBarPolicy( 
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
        this.add(inputPanel = new JPanel(),BorderLayout.SOUTH);
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(commandText = new JTextField(),BorderLayout.CENTER);
        commandText.setFont(new Font("Courier New", Font.PLAIN, 16));
        inputPanel.add(goButton = new JButton("Go"),BorderLayout.EAST);

        bind();

        this.setSize(800, 600);
        this.setVisible(true);

        try {
            this.sqlplus = new Sqlplus(System.out);
            append(sqlplus.start(), Color.DARK_GRAY);
        } catch(Throwable t) {

        }
    }

    private void bind() {
        final ChatViewController model = this;

        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.goButtonPressed();
            }
        });
        commandText.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
            }
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == e.VK_ENTER) {
                    model.commandTextEnterRelease();
                }
            }
        });
    }

    protected void goButtonPressed() {
        model.go();
    }

    protected void commandTextEnterRelease() {
        model.go();
    }

    private void go() {
        try {
            String commandText = this.commandText.getText();
            String text = sqlplus.command(commandText);

            this.commandText.setText("");
            append("SQL> " + commandText + "\n", Color.BLUE);
            append(text, Color.DARK_GRAY);
        } catch (Exception e) {
            new RuntimeException(e);
        }
    }

    private void append(String str, Color color) {
        Document doc = this.consoleText.getDocument();
        try {
            str = replaceTabWithSpace(str);
            StyleConstants.setForeground(attrSet, color);
            doc.insertString(doc.getLength(), str, attrSet);
            this.consoleText.setCaretPosition(doc.getLength());
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }        
    }

    private String replaceTabWithSpace(String str) {
    	String[] lines = str.split("\n");
    	String ret = "";
    	for (String line:lines) {
	        String[] arr = line.split("\t");
    		line = "";
	        int tabSize = 8;
	        for (int i = 0; i < arr.length; i ++) {
	        	if (i != 0) {
	        		line += "        ".substring(line.length() % tabSize);
	        	}
	        	line += arr[i];
	        }
	        if (ret.length() != 0)
	        	ret += "\n";
	        ret += line;
    	}
    	if (str.endsWith("\n")) {
    		ret += "\n";
    	}
    	
        return ret;
    }
}