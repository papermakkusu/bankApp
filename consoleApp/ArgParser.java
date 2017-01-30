package consoleApp;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

@SuppressWarnings("deprecation")
public class ArgParser {
	private static final Logger log = Logger.getLogger(ArgParser.class.getName());
	private String[] args = null;
	private Options options = new Options();
	
	private String targetAcc = new String();
	private String sourceAcc = new String();
	private String opAmount = new String();
	private String operation = new String();
	Map<String, String> parameters = new HashMap<String, String>();

	public ArgParser(String[] args) {
		this.args = args;
		options.addOption("h", "help", false, "show help.");
		options.addOption("v", "var", true, "Testing variable created to check the options functionality.");
		options.addOption("g", "get", true, "You can withdraw money with this option, put amount after --get.");
		options.addOption("p", "put", true, "You can deposit money with this option, put amount after --put.");
		options.addOption("t", "to", true, "You can indicate users with this option, put user name after --to.");
		options.addOption("t", "transfer", true, "You can indicate the amount you want to transfer after --transfer.");
		options.addOption("f", "from", true, "n indicate users from whose accounts you want to transfer "
				+ "with this option, put user name after --from.");
	}

	public Map<String, String> parse() {
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = null;
	 
		try {
			cmd = parser.parse(options, args);
		
			if (cmd.hasOption("h"))
				help();
			if (cmd.hasOption("v")) {
				log.log(Level.INFO, "Using cli argument -v=" + cmd.getOptionValue("v"));
				// Whatever test options I might want to implement, i'll put theme here.
				}
			if (cmd.hasOption("transfer")) {
				this.operation = "transfer";
				this.sourceAcc = cmd.getOptionValue("from");
				this.targetAcc = cmd.getOptionValue("to");
				this.opAmount = cmd.getOptionValue("transfer");
			} else if (cmd.hasOption("get")) {
				this.operation = "withdrawal";
				this.sourceAcc = cmd.getOptionValue("from");
				this.opAmount = cmd.getOptionValue("get");					
			} else if (cmd.hasOption("put")) {
				this.operation = "deposit";
				this.targetAcc = cmd.getOptionValue("to");
				this.opAmount = cmd.getOptionValue("put");				
			}
			else {
				log.log(Level.SEVERE, "Missing valid options, please use --help command");
				help();
			}
		} catch (ParseException e) {
			log.log(Level.SEVERE, "Failed to parse comand line properties, please use --help command.", e);
			help();
		}
		
		this.parameters.put("operation", this.operation);
		this.parameters.put("sourseAcc", this.sourceAcc);
		this.parameters.put("targetAcc", this.targetAcc);
		this.parameters.put("opAmount", this.opAmount);
		
		return this.parameters;
	}

	private void help() {
		// This prints out some help
		HelpFormatter formater = new HelpFormatter();

		formater.printHelp("Main", options);
		System.exit(0);
 		}

	public static Map<String, String> parseArgs(String[] args) {
		ArgParser argParser = new ArgParser(args);
		Map<String, String> parsedArguments = argParser.parse();
		return parsedArguments;
	}
}
	