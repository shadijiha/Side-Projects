#define _CRT_SECURE_NO_WARNINGS
#include "ShadoFile.h"
#include <fstream>
#include <exception>
#include <iostream>
#include <sstream>
#include <algorithm>
#include <regex>
#include <iterator>
#include <ctime>
#include <filesystem>

#define var auto

namespace strUtil {
	static bool string_contains(const std::string& s1, const std::string& s2) {
		return (s1.find(s2) != std::string::npos);
	}

	static std::vector<std::string> string_split(const std::string& str, const std::string& delimiter = "") {

		auto s = str;
		std::vector<std::string> result;

		size_t pos = 0;
		std::string token;
		while ((pos = s.find(delimiter)) != std::string::npos) {
			token = s.substr(0, pos);
			result.push_back(token);
			s.erase(0, pos + delimiter.length());
		}

		return result;
	}

	static std::vector<std::string> string_split_regex(const std::string& str, const std::string& regexStr)
	{
		std::regex re(regexStr);
		const std::vector<std::string> parts(
			std::sregex_token_iterator(str.begin(), str.end(), re, -1),
			std::sregex_token_iterator());
		return parts;
	}
	
	static std::string& string_replaceAll(std::string& s, std::string what, std::string with) {
		//std::replace(s.begin(), s.end(), what, with);
		s = s.replace(s.begin(), s.end(), what.c_str(), with.c_str());
		return s;
	}

	// trim from start (in place)
	static std::string& ltrim(std::string& s) {
		s.erase(s.begin(), std::find_if(s.begin(), s.end(), [](unsigned char ch) {
			return !std::isspace(ch);
			}));
		return s;
	}

	// trim from end (in place)
	static std::string& rtrim(std::string& s) {
		s.erase(std::find_if(s.rbegin(), s.rend(), [](unsigned char ch) {
			return !std::isspace(ch);
			}).base(), s.end());
		return s;
	}

	// trim from both ends (in place)
	static std::string trim(std::string s) {
		std::string temp = s;
		ltrim(temp);
		rtrim(temp);
		return temp;
	}

	static bool string_startsWith(const std::string& str, const std::string& toFind) {
		return str.rfind(toFind, 0) == 0;
	}

	static  bool string_endsWith(std::string const& value, std::string const& ending)
	{
		if (ending.size() > value.size()) return false;
		return std::equal(ending.rbegin(), ending.rend(), value.rbegin());
	}
	
	static void vector_add(std::vector<std::string> vec, std::string element, uint32_t index) {
		auto it = vec.begin();
		it = vec.insert(it + index, element);
	}

	static std::string getDate() {
		using namespace std;
		
		time_t rawtime;
		struct tm* timeinfo;
		char buffer[80];

		time(&rawtime);
		timeinfo = localtime(&rawtime);

		strftime(buffer, sizeof(buffer), "%d-%m-%Y %H:%M:%S", timeinfo);
		std::string str(buffer);

		return str;
	}
}

using namespace strUtil;

ShadoFile::ShadoFile(const std::string& filename)
	: filename(filename)
{
	rawContent = readFile(this->filename);
	compile(rawContent);
}

bool ShadoFile::hasKey(const std::string& key) {
	if (keyValues.find(key) == keyValues.end())
		return false;
	return true;
}

bool ShadoFile::hasValue(const std::string& value) {

	for (auto& kv : keyValues)
		if (kv.second == value)
			return true;

	return false;	
}

std::string ShadoFile::getValue(const std::string& key) {
	return keyValues[key];
}

void ShadoFile::toJSON(const std::string& filepath) {

	std::stringstream stream;

	stream << "{\n";

	int counter = 0;
	for (auto& entry : keyValues) {
		stream << "\t\"" << entry.first << "\" : \"" << entry.second;

		if (counter >= keyValues.size() - 1)
			stream << "\"\n";
		else
			stream << "\",\n";

		counter++;
	}

	stream << "}";

	std::ofstream out(filepath);
	out << stream.str();
	out.close();
	
}

void ShadoFile::toJSON() {
	toJSON(filename + ".json");
}

std::string ShadoFile::readFile(const std::string& filepath) {

	std::ifstream ifs(filepath);

	if (!ifs) {
		auto msg = "Could not open file " + filepath;
		throw std::exception(msg.c_str());
	}
	
	return std::string((std::istreambuf_iterator<char>(ifs)),
		(std::istreambuf_iterator<char>()));	
}

void ShadoFile::trimArray(std::vector<std::string>& array) {
	for (int i = 0; i < array.size(); i++)
		array[i] = trim(array[i]);
}

void ShadoFile::compile(const std::string& content) {

	auto lines = string_split(content, "\n");

	int lineNumber = 0;
	while (lineNumber < lines.size()) {

		// Remove comments from line
		var lineWithoutComment = string_split(lines[lineNumber], "//");

		// If the line accually contained a comment, the lineWithoutComment variable length would be greater than 1
		var line = trim(lineWithoutComment[0]);
		line = trim(line);

		if (string_startsWith(line, "//")) {
			// Ignore
		} else if (string_startsWith(line, "#")) {

			var expression = string_split_regex(line, "\\s+");

			if (expression[0] == "#if") {

				auto newExpression = arrayToStringWithoutIndex(expression, 0, " ");    // Regroup expression without the #if
				bool result = evaluateExpression(newExpression);

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

				auto otherFileName = arrayToStringWithoutIndex(expression, 0, " ");    // Regroup expression without the #if

				var otherFileContent = readFile(string_replaceAll(otherFileName, "\"", ""));
				var otherFileLines = string_split(otherFileContent, "\n");

				// Remove the #include line
				lines[lineNumber] = "";

				// Merge both files to compile them together (handy for recursive #include)
				for (auto& s : otherFileLines)
					vector_add(lines, s, lineNumber++);

				// Add a defined key for this include file so the user can check if the file was included or not
				std::string definedFileNameValue = string_replaceAll(otherFileName, "\"", "");
				definedFileNameValue = trim(definedFileNameValue);
				definedFileNameValue = string_replaceAll(definedFileNameValue, ".", "_");
				definedFileNameValue = string_replaceAll(definedFileNameValue, " ", "");
				definedFileNameValue = trim(definedFileNameValue);
				std::transform(definedFileNameValue.begin(), definedFileNameValue.end(), definedFileNameValue.begin(), ::toupper);

				keyValues.emplace("__" + definedFileNameValue, "true");

				lineNumber = 0;    // In the future change this. You don't need to restart from the beginning
			} else if (expression[0] == "#endif") {
				// Ignore
				// In the future should add a check to see if a #if was declared before #endif
				lines[lineNumber] = "";

			} else if (expression[0] == "#error") {

				auto newExpression = arrayToStringWithoutIndex(expression, 0, " ");    // Regroup expression without the #if

				auto msg = "Error thrown by the file! " + newExpression + " at line " + std::to_string(lineNumber + 1);
				throw std::exception(msg.c_str());

			} else if (expression[0] == "#stop") {
				break;
			} else {

				auto msg = "Invalid Shado file preprocess command \"" + expression[0] + "\" at line " + std::to_string(lineNumber + 1);
				throw std::exception(msg.c_str());

			}
		} else if (line == "" || line == "\n") {
				// Ignore
		} else {
			// Parse tokens
			try {
				var tokens = string_split(lines[lineNumber], ":");

				if (tokens.size() <= 0)
					continue;
				
				tokens[0] = trim(tokens[0]);
				tokens[1] = trim(tokens[1]);

				// If the key or value start and ends with quotes "" remove them
				if (string_startsWith(tokens[0], "\"") && string_endsWith(tokens[0], "\""))
					tokens[0] = string_replaceAll(tokens[0], "\"", "");

				// If the value or value start and ends with quotes "" remove them
				if (string_startsWith(tokens[1], "\"") && string_endsWith(tokens[1], "\""))
					tokens[1] = string_replaceAll(tokens[1], "\"", "");

				// Replace specific compiler tokens like: __LINE__ with values
				if (tokens[1] == "__LINE__") {
					tokens[1] = std::to_string(lineNumber + 1);
				} else if (tokens[1] == "__FILE__") {

					namespace fs = std::filesystem;

					fs::path p = filename;
					
					tokens[1] = fs::absolute(p).u8string();
				} else if (tokens[1] == "__DATE__") {
					tokens[1] = getDate();
				}

				// Push the key value
				keyValues.emplace(tokens[0], tokens[1]);

			} catch (const std::exception& e) {

				auto msg = "Syntax error! Keys and values must be split by a colon \":\".\n\t> " + line + "\nat line " + std::to_string(lineNumber + 1);
				throw std::exception(msg.c_str());
			}
		}

		lineNumber++;

#if _DEBUG
		std::cout << "Total: " << lines.size() << ", current: " << lineNumber << std::endl;

		if (lineNumber == 11)
			__debugbreak();
#endif 		
	}	

#if _DEBUG
	for (const auto& line : lines)
		std::cout << line << std::endl;
#endif 

}

bool ShadoFile::evaluateExpression(const std::string& expression) {

	try {

		if (string_contains(expression, "==")) {
			var tokens = string_split(expression, "==");

			trimArray(tokens);

			if (!hasKey(tokens[0]))
				return false;
			return getValue(tokens[0]) == tokens[1];
			
		} else if (string_contains(expression, "!=")) {
			var tokens = string_split(expression, "!=");

			trimArray(tokens);

			if (!hasKey(tokens[0]))
				return false;

			return getValue(tokens[0]) != tokens[1];

		} else if (string_contains(expression, "undefined(")) {

			var tokens = string_split(expression, "(");
			trimArray(tokens);

			var key = string_replaceAll(tokens[tokens.size() - 1], ")", "");
			return !hasKey(key);
			
		} else if (string_contains(expression, "defined(")) {

			var tokens = string_split(expression, "(");
			trimArray(tokens);

			var key = string_replaceAll(tokens[tokens.size() - 1], ")", "");

			return hasKey(key);
		} else if (expression == "0" || expression == "false") {
			return false;
		} else {
			return true;
		}
		
	}
	catch (const std::exception& e) {
		std::cout << "Syntax error: Invalid expression " + expression << std::endl;
		throw e;
	}
	
}

std::string ShadoFile::arrayToStringWithoutIndex(const std::vector<std::string>& array, uint32_t indexToRemove,
	const std::string& delemitor) {

	std::stringstream builder;
	for (int i = 0; i < array.size(); i++) {
		if (i != indexToRemove)
			builder << array[i] << delemitor;
	}

	return builder.str();
}

void ShadoFile::flatten(std::vector<std::string>& original, const std::vector<std::string>& arrayToAdd, uint32_t insertPosition) {	
	
	for (int i = 0; i < arrayToAdd.size(); i++) {
		vector_add(original, arrayToAdd[i], insertPosition + i);
	}	
}
