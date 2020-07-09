/**
 *
 */

package compiler;

import compiler.preprocessor.*;
import compiler.util.*;

import java.io.*;
import java.util.*;

public class Compiler {

	private File file;
	private String data;

	public Compiler(String filepath) throws FileNotFoundException {

		this.file = new File(filepath);

		if (!file.exists())
			throw new FileNotFoundException(file.getAbsolutePath() + " was not found!");

		data = Util.readFile(filepath);
	}

	public void compile() {

		List<String> lines = Arrays.asList(data.split("\n"));

		for (String line : lines) {

			if (line.startsWith("#"))
				new Preprocessor(line).execute(this);


		}

		// Write compiled file // FOR DEBUG ONLY
		Util.writeToFile(file.getPath() + file.getName() + ".cshado", data);
	}

	public String getData() {
		return data;
	}

	/**
	 * Only replaces first occurrence
	 * @param token
	 * @param replacement
	 */
	public void replace(String token, String replacement) {
		data = data.replace(token, replacement);
	}

	public void setData(String s) {
		data = s;
	}
}
