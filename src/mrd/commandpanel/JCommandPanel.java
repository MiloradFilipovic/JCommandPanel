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
	 * (Monospaced font size 15, black text on white background and no parser class)
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
		//previousLines.setOpaque(false);
		previousLines.setFont(textFont);
		previousLines.setForeground(textColor);
		previousLines.setBackground(backgroundColor);
		previousLines.setEditable(false);

		consoleScroll = new JScrollPane(previousLines);
		//consoleScroll.setOpaque(false);
		//consoleScroll.getViewport().setOpaque(false);

		currentLine = new JTextField();
		//currentLine.setOpaque(false);
		currentLine.setFont(textFont);
		currentLine.setForeground(textColor);
		currentLine.setBackground(backgroundColor);
		currentLine.setCaretColor(textColor);

		//when user clicks on textArea, focus is transfered to textField
		previousLines.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseClicked(MouseEvent arg0) {
				currentLine.requestFocusInWindow();
			}
		});

		currentLine.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent arg0) {
			}
			public void keyReleased(KeyEvent arg0) {
				//if ENTER key is pressed inside text box, command is read and passed to parser
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					//empty command are not being picked
					if(!currentLine.getText().equals("")) {
						//display entered command in textArea with '>>' prefix
						previousLines.append(">> " + currentLine.getText() + "\n");
						if(parser != null) {
							//if parser class is provided, pass entered command to it and display it's output in textArea
							previousLines.append(parser.parseCommand(currentLine.getText()) + "\n");
						}else {
							//if parser class is not provided, display error message
							previousLines.append("[ERROR] Parser class not found!\n");
						}
						//scroll to the end of textArea
						previousLines.setCaretPosition(previousLines.getDocument().getLength());
						//add current command to history list and update command index
						listory.add(currentLine.getText());
						commandInex = listory.size();
						currentLine.setText("");
					}
				//if user presses UP key inside textBox
				//decrease command index, and display command on that position in history
				}else if(arg0.getKeyCode() == KeyEvent.VK_UP) {
					if(!listory.isEmpty()) {
						if(commandInex > 0) {
							commandInex--;
						}
						currentLine.setText(listory.get(commandInex));
					}
				//if DOWN key is pressed inside textBox
				//increase command index and display command on that position
				}else if(arg0.getKeyCode() == KeyEvent.VK_DOWN) {
					if(!listory.isEmpty()) {
						if(commandInex < listory.size()-1) {
							commandInex++;
							currentLine.setText(listory.get(commandInex));
						//if last command in list is reached, after pressing DOWN key, empty command is displayed
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

	/**
	 * Sets text font for command panel
	 * @param font
	 */
	public void setTextFont(Font font) {
		previousLines.setFont(font);
		currentLine.setFont(font);
	}

	/**
	 * Sets background color for command panel
	 * @param color
	 */
	public void setBackgroundColor(Color color) {
		setOpaqueComponents(false);
		previousLines.setBackground(color);
		currentLine.setBackground(color);
		currentLine.setCaretColor(color);
	}
	
	/**
	 * Sets foreground color for command panel
	 * @param color
	 */
	public void setForegroundColor(Color color) {
		setOpaqueComponents(false);
		previousLines.setForeground(color);
		currentLine.setForeground(color);
		currentLine.setCaretColor(color);
	}
	
	private void setOpaqueComponents(Boolean opaque) {
		previousLines.setOpaque(opaque);
		consoleScroll.setOpaque(opaque);
		consoleScroll.getViewport().setOpaque(opaque);
		currentLine.setOpaque(opaque);
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
