#pragma once
#include <string>
#include <unordered_map>
#include <vector>


class ShadoFile {
public:
	ShadoFile(const std::string& filename);

	bool hasKey(const std::string& key);
	bool hasValue(const std::string& value);

	std::string getValue(const std::string& key);

	void toJSON(const std::string& filepath);
	void toJSON();

private:
	static std::string readFile(const std::string& filepath);
	static void trimArray(std::vector<std::string>& array);

	void compile(const std::string& data);
	bool evaluateExpression(const std::string& expression);
	std::string arrayToStringWithoutIndex(const std::vector<std::string>& array, uint32_t indexToRemove = -1, const std::string& delemitor = "");
	void flatten(std::vector<std::string>& original, const std::vector<std::string>& array, uint32_t insertPosition);

private:

	std::string rawContent;
	std::string filename;

	std::unordered_map<std::string, std::string> keyValues;	
};
