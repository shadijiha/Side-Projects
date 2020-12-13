using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;

namespace ShadoObjectFileCSharp {
	public sealed class ShadoFile : IEnumerable<KeyValuePair<string, object>> {
		private readonly string filepath;
		private readonly Dictionary<string, object> keyValues;

		public static readonly char DEFAULT_SEPARATOR = ':';
		private char separator = ':';

		/// <summary>
		/// Compiles a Shado File to key value pairs
		/// </summary>
		/// <param name="filename">The file to compile</param>
		/// <example>
		///	Usage:
		/// 5 | ...
		/// 6 |		var file = new ShadoFile(@"example.shado");
		/// 7 | ...
		/// </example>
		public ShadoFile(string filename) {
			filepath = filename;
			keyValues = new Dictionary<string, object>();

			// Add the __c_sharp key value because the file was compiled by C#
			keyValues.Add("__C#__", "1");

			Compile(ReadFile(filename));
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="key"></param>
		/// <returns>Returns true of the Shado file contains that key</returns>
		public bool HasKey(string key) {
			return keyValues.ContainsKey(key);
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="value"></param>
		/// <returns>Returns true of the Shado file contains that value</returns>
		public bool HasValue(string value) {
			return keyValues.ContainsValue(value);
		}

		/// <summary>
		/// Gets the value of a key
		/// </summary>
		/// <param name="key">The key</param>
		/// <returns>Returns the key if it exists, null otherwise</returns>
		public string GetValue(string key) {
			return keyValues[key].ToString();
		}

		/// <summary>
		/// Use this function if you know what's the type of the value
		/// </summary>
		/// <typeparam name="T">The type of the value</typeparam>
		/// <param name="key">The key</param>
		/// <returns></returns>
		/// <example>
		///	Use:
		/// 16|	...
		/// 17|		var file = new ShadoFile(@"example.shado");
		/// 18|		double[] array = file.GetValue<double[]>("testArray");
		/// 20|	...
		///
		/// File: example.shado
		/// 1 |	...
		/// 2 |	testArray : [1, 78, 4, 4, 6, 5, 4]
		/// 3 |	...
		/// </example>
		public T GetValue<T>(string key) {
			return (T)keyValues[key];
		}

		/// <summary>
		/// Inserts a key value pair to the Shado File
		/// </summary>
		/// <param name="key">The key to insert</param>
		/// <param name="value">The value to insert</param>
		public void Insert(string key, object value) {

			try {
				keyValues.Add(key, value.ToString());
			}
			catch (ArgumentException) {
				return;
			}

			using (StreamWriter writer = File.AppendText(GetFilePath())) {

				// If it is not the default separator then add the #using directive
				if (separator != DEFAULT_SEPARATOR)
					writer.Write($"\n#using separator '{separator}'");

				writer.Write($"\n{key} {separator} {value.ToString()}");

				if (separator != DEFAULT_SEPARATOR)
					writer.Write($"\n#using separator '{DEFAULT_SEPARATOR}'");

				writer.Close();
			}
		}

		/// <summary>
		/// Changes the value of a key in the file
		/// </summary>
		/// <param name="key">The key to change</param>
		/// <param name="newValue">To the new value</param>
		public void Update(string key, object newValue) {

			if (!HasKey(key))
				return;

			// Get which line is the result to update
			uint lineToChange = 0;
			bool found = false;
			List<string> buffer = new List<string>();
			using (StreamReader reader = new StreamReader(GetFilePath())) {

				string line;
				while ((line = reader.ReadLine()) != null) {
					if (!line.Contains(key) && !found) {
						lineToChange++;
					}
					else {
						found = true;
					}

					buffer.Add(line);
				}

				reader.Close();
			}

			// Write the updated result
			using (StreamWriter writer = new StreamWriter(GetFilePath())) {

				for (int i = 0; i < buffer.Count; i++) {
					if (i == lineToChange) {
						var x = Regex.Split(buffer[i].Trim(), "\\s+");
						var before = String.Join(' ', x, 0, 2);
						var rep = newValue.ToString();

						writer.WriteLine(String.Join(" ", before, rep));
					}
					else {
						writer.WriteLine(buffer[i]);
					}
				}

				writer.Close();
			}

			// Modify the memory
			keyValues[key] = newValue.ToString();
		}

		/// <summary>
		/// Deleted a key value from the file (updates the file)
		/// </summary>
		/// <param name="key">The key to delete</param>
		/// <param name="deleteFromFile">If true the key, value pair will be deleted, otherwise only an #if 0 will be add before the key value</param>
		public void Delete(string key, bool deleteFromFile = true) {

			List<string> buffer = new List<string>();
			uint atLine = 0;
			bool found = false;
			using (StreamReader reader = new StreamReader(GetFilePath())) {

				string line;
				while ((line = reader.ReadLine()) != null) {
					if (!Regex.Split(line.Trim(), "\\s+")[0].Contains(key) && !found) {
						atLine++;
						
					}
					else {
						found = true;
					}

					buffer.Add(line);
				}

				reader.Close();
			}

			// Write the updated result
			using (StreamWriter writer = new StreamWriter(GetFilePath())) {

				for (int i = 0; i < buffer.Count; i++) {
					if (i == atLine) {
						if (!deleteFromFile)
							writer.WriteLine($"#if 0\n\t{buffer[i]}\n#endif");
					} else {
						writer.WriteLine(buffer[i]);
					}
				}

				writer.Close();
			}


			keyValues.Remove(key);
		}

		/// <summary>
		/// Inserts a key-value pair to the file. If the key already exists, the value if updated
		/// </summary>
		/// <param name="key">The key to insert</param>
		/// <param name="value">The value to insert/update</param>
		public void InsertIfNotExists(string key, object value) {
			if (HasKey(key))
				Update(key, value);
			else
				Insert(key, value);
		}

		/// <summary>
		/// Convert the Shado file to JSON and writes the JSON to a directory
		/// </summary>
		/// <param name="filepath">The file where you want to store the JSON file</param>
		public void ToJson(string filepath) {
			StringBuilder builder = new StringBuilder();
			builder.Append("{\n");

			uint counter = 0;
			foreach (var entry in keyValues) {

				builder.Append("\t\"").Append(entry.Key).Append("\" : \"").Append(entry.Value.ToString().Replace("\\", "\\\\"));

				if (counter >= keyValues.Count - 1)
					builder.Append("\"\n");
				else {
					builder.Append("\",\n");
				}

				counter++;
			}

			builder.Append("}");

			// Write file
			using (StreamWriter writer = new StreamWriter(filepath)) {
				writer.Write(builder.ToString());
				writer.Close();
			}
		}

		/// <summary>
		/// Convert the Shado file to JSON and writes the JSON to the Shado file directory
		/// </summary>
		public void ToJson() {
			ToJson(this.filepath + ".json");
		}

		/// <summary>
		/// Changes the separator of the file
		/// </summary>
		/// <param name="s">The new separator</param>
		public void SetSeparator(char s) {
			separator = s;
		}

		/// <summary>
		/// Get the full path of a specific file name
		/// </summary>
		/// <param name="filename">The file name, if it is empty, the full path of the current Shado File will be returned</param>
		/// <returns>Returns the fill path of a given file</returns>
		public string GetFilePath(string filename = null) {
			if (filename == null)
				filename = this.filepath;

			if (!filename.Contains(':')) {
				// TODO: Line 363 is an Imposter
#warning The next line is an imposter
				return Directory.GetParent(Directory.GetCurrentDirectory()).Parent.Parent + "\\" + filename;
			}

			return filename;
		}

		private void Compile(string content) {

			List<string> lines = new List<string>(content.Split("\n"));

			string SEPERATOR = ":";

			// This line number is used for iteration and is modified depending how the program is viewing the file
			int lineNumber = 0;
			// This line number is used for Error message only and is not modified (Reflects the accual number in the file)
			uint originalLineNumber = 0; 

			// Detect the type of the file
			for (int i = 0; i < lines.Count; i++) {
				String tempLine = lines[i].Trim();
				if (tempLine.StartsWith("//") || tempLine == "" || tempLine == "\n") {
					continue;
				} else if (tempLine.StartsWith("#type")) {
					String tempExpr = Regex.Replace(tempLine, "\\s+", " ");
					if (tempLine != "#type object file") {
						throw new Exception("Currently the compiler only supports Shado Object files \"#type object file\"");
					} else {
						lines[i] = "";
						keyValues.Add("__FILE_TYPE__", "Shado object file");
						break;
					}
				} else {
					throw new Exception("The first statement of Shado File must be its type \"#type SOMETHING\"");
				}
			}

			while (lineNumber < lines.Count) {

				// Remove comments from line
				// If the line accually contained a comment, the lineWithoutComment variable length would be greater than 1
				string line = lines[lineNumber].Split("//")[0].Trim();
				lines[lineNumber] = line;

				if (!line.StartsWith("//")) {
					line = ReplaceCompilerTokens(line, originalLineNumber);
				}
				else {
					lineNumber++;
					originalLineNumber++;
					continue;
				}

				if (line.StartsWith("#")) {

					string[] expression = Regex.Split(line, "\\s+");

					if (expression[0] == "#if") {

						string newExpression = string.Join(' ', expression, 1, expression.Length - 1);
						bool result = EvaluateExpression(newExpression, originalLineNumber + 1);

						// Remove the #if line
						lines[lineNumber] = "";

						if (!result) {
							// In the future we can use level system for nested #if statements
							while (lines[lineNumber] != "#endif") {
								lines[lineNumber] = ""; // Ignore line
								lineNumber++;
							}

							lineNumber -= 2;
						}

					} else if (expression[0] == "#include") {

						string otherFileName = string.Join(' ', expression, 1, expression.Length - 1);
						string otherFileContent = ReadFile(Regex.Replace(otherFileName, "\"|'", ""));
						string[] otherFileLines = otherFileContent.Split("\n");

						// Remove the #include line
						lines[lineNumber] = "";

						// Merge both files to compile them together (handy for recursive #include)
						int _start_line = lineNumber;
						foreach (string s in otherFileLines)
							lines.Insert(lineNumber++, s);

						// Add a defined key for this include file so the user can check if the file was included or not
						string definedFileNameValue = Regex.Replace(otherFileName.Replace("\"", "").Trim().Split("\\")[^1].Replace(".", "_"), "\\s+", "").Trim().ToUpper();
						keyValues.Add("__" + definedFileNameValue, "true");

						// Restart from the #include line 
						lineNumber = _start_line;    

					} else if (expression[0] == "#endif") {
						// Ignore
						// In the future should add a check to see if a #if was declared before #endif
						lines[lineNumber] = "";

					} else if (expression[0] == "#error") {
						string newExpression = String.Join(' ', expression, 1, expression.Length - 1);    // Regroup expression without the #if
						throw new Exception("Error thrown by the file! " + newExpression + " at line " + (originalLineNumber + 1));

					} else if (expression[0] == "#stop") {
						break;

					} else if (expression[0] == "#using") {

						// See what's the using is for
						if (expression[1] == "separator") {
							string newSeparator = Regex.Replace(expression[^1], "\"|'","");
							newSeparator = newSeparator == "__DEFAULT_SEPARATOR__" ? DEFAULT_SEPARATOR.ToString() : newSeparator;

							SEPERATOR = newSeparator;
							this.separator = newSeparator[0];
						}
						else {
							throw new InvalidOperationException("Invalid usage of the #using directive. #using directive cannot be alone");
						}

					} else if (expression[0] == "#type") {
						throw new InvalidOperationException("The file type can be defined only once! \"" + expression[0] + "\" at line " + (originalLineNumber + 1));
					} else {
						throw new Exception("Invalid Shado file preprocess command \"" + expression[0] + "\" at line " + (originalLineNumber + 1));
					}

				} else if (line == "" || line == "\n" || line == "\r") {
					// Ignore
				}
				else {
					// Parse tokens
					try {

						string[] tokens = line.Split(SEPERATOR, 2);
						tokens[0] = tokens[0].Trim();
						tokens[1] = tokens[1].Trim();

						// If the key or value start and ends with quotes "" remove them
						for (int i = 0; i < tokens.Length; i++) {
							if (tokens[i].StartsWith("\"") && tokens[i].EndsWith("\""))
								tokens[i] = tokens[i].Replace("\"", "");
						}

						// Replace specific compiler tokens like: __LINE__ with values
						tokens[1] = ReplaceCompilerTokens(tokens[1], originalLineNumber);

						// Push the key value
						keyValues.Add(tokens[0], ConvertToTrueDataType(tokens[1]));

					}
					catch (IndexOutOfRangeException) {
						throw new Exception(FormatErrorMessage($"Syntax error! Keys and values must be split by \"{SEPERATOR} \"", originalLineNumber + 1));
					}
					catch (Exception) { }
				}

				lineNumber++;
				originalLineNumber++;
			}

		}

		/// <summary>
		/// Gets a line of the file as a string
		/// </summary>
		/// <param name="line">The line number to return (Starting from 1)</param>
		/// <returns>Returns the requested line OR an empty string if the line doesn't exist</returns>
		public string GetLine(uint line) {

			uint count = 1;
			using (StreamReader reader = new StreamReader(GetFilePath())) {
				string lineStr;
				while ((lineStr = reader.ReadLine()) != null) {
					if (count == line)
						return lineStr;
					count++;
				}
				reader.Close();
			}

			return "";
		}

		private bool EvaluateExpression(string expression, uint currentLine) {
			expression = expression.Trim();

			try {
				if (expression.Contains("==")) {
					string[] tokens = expression.Split("==").Select(e => e.Trim()).ToArray();

					return GetValue(tokens[0]) != null && GetValue(tokens[0]).ToString() == tokens[1];
				}

				if (expression.Contains("!=")) {
					string[] tokens = expression.Split("!=").Select(e => e.Trim()).ToArray();
					return GetValue(tokens[0]).ToString() != null && GetValue(tokens[0]).ToString() != tokens[1];
				}

				if (expression.Contains("undefined(")) {

					string[] tokens = expression.Split("(").Select(e => e.Trim()).ToArray();
					string key = tokens[^1].Replace(")", "");
					return !HasKey(key);

				}

				if (expression.Contains("defined(")) {

					string[] tokens = expression.Split("(").Select(e => e.Trim()).ToArray();
					string key = tokens[tokens.Length - 1].Replace(")", "");
					return HasKey(key);

				}

				if (expression.Contains("file_exists(")) {
					string[] tokens = expression.Split("(").Select(e => e.Trim()).ToArray();
					string file__ = tokens[^1].Replace(")", "").Replace("\"", "").Replace("'", "");
					return File.Exists(file__);
				}

				if (expression == "0" || expression.ToLower() == "false") {
					return false;
				}

				if (expression == "1" || expression.ToLower() == "true") {
					return true;
				}

				// Otherwise evaluate any Arithmatic expressions
				DataTable table = new DataTable();
				return Convert.ToBoolean(table.Compute(expression, ""));
			}
			catch (Exception) {
				throw new Exception(FormatErrorMessage("Syntax error: Invalid expression " + expression, currentLine));
			}
		}

		private string ReplaceCompilerTokens(string token, uint lineNumber) {

			string result = token.Trim();

			if (token.Contains("__LINE__")) {
				result = result.Replace("__LINE__", (lineNumber + 1) + "");
			} 
			
			if (token.Contains("__FILE__")) {
				result = result.Replace("__FILE__", Path.GetFullPath(this.filepath));
			} 
			
			if (token.Contains("__DATE__")) {
				result = result.Replace("__DATE__", DateTime.Now.ToString());
			} 
			
			if (token.Contains("__DIR__")) {
				result = result.Replace("__DIR__", Directory.GetCurrentDirectory().ToString());
			}

			// Evaluate any Arithmatic expressions
			DataTable table = new DataTable();
			try {
				result = table.Compute(result, "").ToString();
			}
			catch (Exception) {}

			return result;
		}

		private object ConvertToTrueDataType(string data) {

			// If it is an array
			if (data.StartsWith("[") && data.EndsWith("]")) {
				var elements = data.Replace("[", "").Replace("]", "").Split(",");

				uint numOfDouble = 0;
				uint numOfLong = 0;
				uint numOfBool = 0;
				// Calculate the number of object types
				foreach (var e in elements) {
					if (long.TryParse(e.Trim(), out _))
						numOfLong++;
					else if (double.TryParse(e.Trim(), out _))
						numOfDouble++;
					else if (bool.TryParse(e.Trim(), out _))
						numOfBool++;
				}

				// See which array should be constructed
				if (numOfLong == elements.Length) {
					long[] arr = new long[elements.Length];

					int i = 0;
					foreach (var e in elements) {
						long.TryParse(e.Trim(), out arr[i++]);
					}

					return arr;
				}

				if (numOfDouble == elements.Length || numOfLong + numOfDouble == elements.Length) {
					double[] arr = new double[elements.Length];

					int i = 0;
					foreach (var e in elements) {
						double.TryParse(e.Trim(), out arr[i++]);
					}

					return arr;
				}

				if (numOfBool == elements.Length) {
					bool[] arr = new bool[elements.Length];

					int i = 0;
					foreach (var e in elements) {
						bool.TryParse(e.Trim(), out arr[i++]);
					}
					return arr;
				}

				// Return array as strings
				{
					String[] arr = new string[elements.Length];

					int i = 0;
					foreach (var e in elements) {
						arr[i++] = e;
					}
					return arr;
				}

			} else if (long.TryParse(data, out _)) {
				return long.Parse(data);
			} else if (double.TryParse(data, out _)) {
				return double.Parse(data);
			} else if (bool.TryParse(data, out _)) {
				return bool.Parse(data);
			} else {
				return data;
			}
		}

		private string ReadFile(string filename) {
			StringBuilder result = new StringBuilder();

			// If the file name contains a : that means that it is an absolute filepath, In that case don't touch it
			// Otherwise, construct the directory from The project directory
			filename = GetFilePath(filename);

			// See if file exists
			if (!File.Exists(filename))
				throw new FileNotFoundException($"Invalid file! {filename} was not found!");

			using (StreamReader reader = new StreamReader(filename)) {
				string line;
				while ((line = reader.ReadLine()) != null) {
					result.Append(line).Append("\n");
				}
				reader.Close();
			}

			return result.ToString();
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="message"></param>
		/// <param name="line">The line where the error occured (Starting from 1)</param>
		/// <returns></returns>
		private string FormatErrorMessage(string message, uint line) {

			Func<uint, string> format_error = e => (e < 10 ? e + " " : e + "");

			return $"{message}.\n\n" +
			       $"     {format_error(line - 1)}|  {GetLine(line - 1)}\n" +
			       $"---> {format_error(line)}|  {GetLine(line)}\n" +
			       $"     {format_error(line + 1)}|  {GetLine(line + 1)}\n\n" +
			       $"at line {line}";
		}

		/// <summary>
		/// Use this function to iterate over the shado file
		/// </summary>
		/// <returns>Returns an iterator with key value pairs</returns>
		IEnumerator IEnumerable.GetEnumerator() {
			return keyValues.GetEnumerator();
		}

		public IEnumerator<KeyValuePair<string, object>> GetEnumerator() {
			return keyValues.GetEnumerator();
		}

		public object this[string index] {
			get {
				return keyValues[index];
			}

			set {
				keyValues[index] = value;
			}
		}
	}
}
