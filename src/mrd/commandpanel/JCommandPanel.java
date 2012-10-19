package mrd.commandpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

/**
 * GUI component that simulates console behavior
 * @author Milorad Filipovic <milorad.filipovic19@gmail.com>
 *
 */
public class JCommandPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	/**
	 * TextArea that contains previously entered user commands
	 * and application output text (not editable)
	 */
	private JTextArea previousLines;
	private JScrollPane consoleScroll;
	/**
	 * TextField used for command typing
	 */
	private JTextField currentLine;
	/**
	 * Command history
	 */
	private ArrayList<String> listory;
	/**
	 * Current command index in history list
	 */
	private int commandInex;
	/**
	 * Instance of CommandParser to which all received commands are forwarded
	 */
	private CommandParser parser;
	
	/**
	 * Default constructor
	 * Initializes JCommandPanel with default settings
	 * (Monospaced font size 15, black text on white background)
	 */
	public JCommandPanel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		listory = new ArrayList<String>();
		Font textFont = new Font("Monospaced",Font.PLAIN,15);
		initGUI(Color.BLACK,Color.WHITE, textFont);
	}
	
	/**
	 * Initializes JCommandPanel with passed settings
	 * @param textColor console text color
	 * @param backgroundColor console background color
	 * @param textFont console text font
	 * @param commandParser parser class for the console
	 */
	public JCommandPanel(Color textColor, Color backgroundColor, Font textFont, CommandParser commandParser) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		listory = new ArrayList<String>();
		parser = commandParser;
		initGUI(textColor, backgroundColor, textFont);
	}
	
	private void initGUI(Color textColor, Color backgroundColor, Font textFont) {
		previousLines = new JTextArea();
		previousLines.setFont(textFont);
		previousLines.setForeground(textColor);
		previousLines.setBackground(backgroundColor);
		previousLines.setEditable(false);
		
		consoleScroll = new JScrollPane(previousLines);
		
		currentLine = new JTextField();
		currentLine.setFont(textFont);
		currentLine.setForeground(textColor);
		currentLine.setBackground(backgroundColor);
		currentLine.setCaretColor(textColor);
		
		previousLines.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				currentLine.requestFocusInWindow();
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		
currentLine.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					if(!currentLine.getText().equals("")) {
						previousLines.append(">> " + currentLine.getText() + "\n");
						previousLines.append(parser.parseCommand(currentLine.getText()) + "\n");
						previousLines.setCaretPosition(previousLines.getDocument().getLength());
						listory.add(currentLine.getText());
						commandInex = listory.size();
						currentLine.setText("");
					}
				}else if(arg0.getKeyCode() == KeyEvent.VK_UP) {
					if(!listory.isEmpty()) {
						if(commandInex > 0) {
							commandInex--;
						}
						currentLine.setText(listory.get(commandInex));
					}
				}else if(arg0.getKeyCode() == KeyEvent.VK_DOWN) {
					if(!listory.isEmpty()) {
						if(commandInex < listory.size()-1) {
							commandInex++;
							currentLine.setText(listory.get(commandInex));
						}else if (commandInex == listory.size()-1) {
							commandInex = listory.size();
							currentLine.setText("");
						}
					}
				}
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});
		
		add(consoleScroll, BorderLayout.CENTER);
		add(currentLine, BorderLayout.SOUTH);
	}
	
	public void setTextFont(Font font) {
		previousLines.setFont(font);
		currentLine.setFont(font);
	}
	
	public JTextArea getPreviousLines() {
		return previousLines;
	}
	public void setPreviousLines(JTextArea previousLines) {
		this.previousLines = previousLines;
	}
	public JTextField getCurrentLine() {
		return currentLine;
	}
	public void setCurrentLine(JTextField currentLine) {
		this.currentLine = currentLine;
	}
	public CommandParser getParser() {
		return parser;
	}
	public void setParser(CommandParser parser) {
		this.parser = parser;
	}
	
}
