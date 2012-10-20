package mrd.commandpanel;

/**
 * Interface to be implemented by parser class for JCommandPanel
 * @author Milorad Filipovic <milorad.filipovic19@gmail.com>
 *
 */
public interface CommandParser {

	/**
	 * Method which receives textual command from command panel,
	 * parses it, and returns application output which will be displayed to user
	 * @param command
	 * @return text response which will be displayed to user
	 */
	public String parseCommand(String command);
	
}
