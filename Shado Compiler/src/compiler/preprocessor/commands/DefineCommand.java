/**
 *
 */

package compiler.preprocessor.commands;

import compiler.Compiler;
import compiler.preprocessor.*;

public class DefineCommand extends PreprocessorCommand {

	private String whatToReplace;
	private String replacement;


	public DefineCommand(String raw_command, String whatToReplace, String replacement) {
		super(raw_command, "define", 2, new Object[]{whatToReplace, replacement});

		this.whatToReplace = whatToReplace;
		this.replacement = replacement;
	}

	/**
	 * Execute a predefined Runnable function
	 *
	 * @param o
	 */
	@Override
	public void execute(Compiler o) {

		// Remove the raw token (to not get stuck in an infinit loop)
		o.replace(raw_command, "");

		// Do the replacement
		String temp = o.getData().replaceAll("\\b" + whatToReplace + "\\b", replacement);

		o.setData(temp);
	}
}
