package net.sf.orassist.gui;

import java.awt.BorderLayout;
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
	private JTextArea consoleText; 
	
	public ChatViewController() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setLayout(new BorderLayout());
		this.add(timelinePane = new JScrollPane(consoleText = new JTextArea()),BorderLayout.CENTER);
		consoleText.setEditable(false);
		timelinePane.setHorizontalScrollBarPolicy( 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		timelinePane.setVerticalScrollBarPolicy( 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
		this.add(inputPanel = new JPanel(),BorderLayout.SOUTH);
		inputPanel.setLayout(new BorderLayout());
		inputPanel.add(commandText = new JTextField(),BorderLayout.CENTER);
		inputPanel.add(goButton = new JButton("Go"),BorderLayout.EAST);
		
		bind();
		
		this.setSize(800, 600);
		this.setVisible(true);
		
		try {
			this.sqlplus = new Sqlplus(System.out);
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
			this.consoleText.append("SQL> " + commandText);
			this.consoleText.append("\n");
			this.consoleText.append(text);
		} catch (Exception e) {
			new RuntimeException(e);
		}
	}

}
