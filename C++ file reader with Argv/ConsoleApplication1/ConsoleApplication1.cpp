// ConsoleApplication1.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>
#include <fstream>
#include <vector>

#include "String.h"

class FileReader {
private:
	std::string path;
	std::string extention;
	std::string content = "";
	unsigned int m_lines = 0;

public:
	FileReader(std::string& path) {
		this->path = path;
	}

	FileReader(char* path) {
		this->path = path;
	}

	FileReader(const char* path) {
		this->path = path;
	}

	FileReader() = delete;

	FileReader(const FileReader& other) = delete;

	void read() {

		std::ifstream instream(path);
		std::string line;
		while (instream >> line)
		{
			content += line + ' ';
			if (instream.peek() == '\n') //detect "\n"
			{
				content += '\n';
				m_lines++;
			}
		}
		instream.close();
	}

	std::string toString() {
		return content;
	}

	int lines() {
		return m_lines;
	}
};

class FileWriter {
private:
	std::string path;
	std::ofstream stream;

public:
	FileWriter(std::string& path) {
		this->path = path;
		stream = std::ofstream(path);
	}

	FileWriter(const char* path)	{
		this->path = path;
		stream = std::ofstream(path);
	}

	FileWriter() = delete;

	FileWriter(const FileWriter& other) = delete;

	void write(const char* string) {
		stream << string;
	}

	void write(const std::string& string) {
		stream << string;
	}

	void close() {
		stream.close();
	}
};

std::vector<std::string> split(const std::string& str, const char delim) {
	size_t start;
	size_t end = 0;
	std::vector<std::string> result;

	while ((start = str.find_first_not_of(delim, end)) != std::string::npos)
	{
		end = str.find(delim, start);
		result.push_back(str.substr(start, end - start));
	}

	return result;
}

void log(std::vector<std::string> s) {
	for (const auto& temp : s)
		std::cout << temp << std::endl;
}

std::string shadoToJSON(std::string file) {

	typedef std::string String;
	using namespace std;

	String json = "{\n";

	struct Data {
		String attribute;
		String value;
	};

	auto lines = split(file, '\n');
	for (String line : lines) {

		json += "\t{\n";

		auto tokens = split(line, ':');

		if (tokens.size() > 1) {
			json += "\t\t\"" + tokens[0] + "\":\"" + tokens[1] + "\",\n";
		}

		json += "\t},\n";
	}

	json += "}";

	return json;
}

int main(int argc, char* argv[])
{
	typedef std::string String;

	std::cout << "Have " << argc << " arguments:\n\n" << std::endl;

	// Start at 1 to ignore the first argument (which is the name of this progarm)
	for (int i = 1; i < argc; ++i) {
		std::cout << "Reading: " << argv[i] << std::endl;

		// Read shado file
		FileReader reader(argv[i]);
		reader.read();

		// Convert to json
		std::string temp = argv[i];
		temp += ".json";

		FileWriter writer(temp);
		writer.write(shadoToJSON(reader.toString()));
		writer.close();

		std::cout << "Successfully parsed " << argv[i] << "to JSON" << std::endl;
	}

	std::cin.get();
}

// Run program: Ctrl + F5 or Debug > Start Without Debugging menu
// Debug program: F5 or Debug > Start Debugging menu

// Tips for Getting Started: 
//   1. Use the Solution Explorer window to add/manage files
//   2. Use the Team Explorer window to connect to source control
//   3. Use the Output window to see build output and other messages
//   4. Use the Error List window to view errors
//   5. Go to Project > Add New Item to create new code files, or Project > Add Existing Item to add existing code files to the project
//   6. In the future, to open this project again, go to File > Open > Project and select the .sln file
