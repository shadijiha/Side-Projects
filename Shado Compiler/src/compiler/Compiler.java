/**
 *
 */

package compiler;

import compiler.language.*;
import compiler.preprocessor.*;
import compiler.util.*;

import java.io.*;
import java.util.*;

public class Compiler {

	private static final List<String> reserved_words = new ArrayList<>();
	private static boolean initialized = false;

	private File file;
	private String data;
	private int currentLine;

	private Map<Integer, Variable> variables;

	public Compiler(String filepath) throws FileNotFoundException {

		this.file = new File(filepath);

		if (!file.exists())
			throw new FileNotFoundException(file.getAbsolutePath() + " was not found!");

		data = Util.readFile(filepath);
		variables = new HashMap<>();

		if (!initialized)
			initialize();
	}

	/**
	 * Compiles the given file
	 */
	public void compile() {

		List<String> lines = Arrays.asList(data.split("\n"));

		for (String line : lines) {

			// Preprocessor command
			if (line.startsWith("#"))
				new Preprocessor(line).execute(this);

			// If the line starts with a premetive and it contains a "=" then it is a variable or function declaration
			for (String type : Variable.getPremetives()) {

				// Variable assignment --> e.g:  int a = 10;
				if (line.startsWith(type + " ") && line.contains("=")) {
					new AssignmentExpression(line).execute(this);
					break;
				}

				// variable declaration --> e.g. int a;
				if (line.startsWith(type + " ") && !line.contains("=")) {
					new VariableDeclarationExpression(line).execute(this);
					break;
				}

				// declared variable assignment
				// e.g.:	4|	int x;
				//			5|	x = 15;
				if (!line.startsWith(type + " ") && line.contains("=")) {
					new DeclaredVariableAssignment(line).execute(this);
					break;
				}
			}

			currentLine++;
		}

		// Write compiled file // FOR DEBUG ONLY
		Util.writeToFile(Util.filenameNoExtension(file.getName()) + ".cshado", data);

		// Write a map file // FOR DEBUG ONLY
		Util.writeToFile(Util.filenameNoExtension(file.getName()) + ".debug", toMap());
	}

	/**
	 * @return Returns the data of the compiler's buffer
	 */
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

	/**
	 * Replaces all the data inside the compiler buffer
	 * @param s The new Data
	 */
	public void setData(String s) {
		data = s;
	}

	/**
	 * Initializes the compiler (e.g. reserved words)
	 */
	private void initialize() {

		List<String> list = Arrays.asList("if", "else", "switch", "case", "default",           // Conditional
				"while", "for", "when", "do", "goto",            // Loops
				"class", "struct", "new", "this", "super", "base", "constructor", "abstract", "interface",   // Classes
				"public", "private", "package", "protected",    // Access modifiers
				"import", "export", "extern", "using",            // Import
				"final", "const", "override", "native", "static",
				"synchronized", "volatile",
				"var", "auto");        // Functions modifiers

		reserved_words.addAll(Variable.getPremetives());
		reserved_words.addAll(list);

		initialized = true;
	}

	/**
	 * gets the line that is currently getting evaluated
	 * @return
	 */
	public int getCurrentLine() {
		return currentLine;
	}

	/**
	 * Gets the name of the file that is currently getting compiled
	 * @return
	 */
	public String getFileName() {
		return file.getName();
	}

	/**
	 * Adds a variable to the compiler's buffer
	 * @param v
	 */
	public void addVariable(Variable v) {
		variables.put(v.hashCode(), v);
	}

	public boolean hasVariable(Variable v) {
		for (var var : variables.entrySet())
			if (var.getValue().getName().equals(v.getName()))
				return true;
		return false;
	}

	public boolean updateVariable(String varName, Object newVal) {
		for (var var : variables.entrySet())
			if (var.getValue().getName().equals(varName))
				var.getValue().setValue(newVal);
		return false;
	}

	public Map<Integer, Variable> allVariables() {
		return new HashMap<>(variables);
	}

	@Deprecated
	private String toMap() {

		StringBuilder builder = new StringBuilder();

		for (var var : variables.entrySet())
			builder.append(var.getValue().getName()).append(", ").append(var.getValue()).append(", ").append(var.getValue().getType()).append("\n");

		return builder.toString();
	}
}
