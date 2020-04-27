#include "includes/Strings/String.h"


String::String(const char* str) {
	string = std::string(str);
}

String::String(const std::string& str) {
	string = str;
}

String::String() {
	string = "";
}

String::~String() = default;

void String::concat(const char* str) {
	string += str;
}

void String::concat(const std::string& str) {
	string += str;
}

void String::concat(const String& str) {
	string += str.string;
}

String String::replaceAll(char char_to_search, const std::string& replacement) const {
	String copy(*this);
	for (int i = 0; i < copy.length(); i++) {
		if (copy[i] == char_to_search)
			copy.string.replace(i, 1, replacement);
	}
	return copy;
}

String String::replaceFirst(char char_to_search, const std::string& replacement) const {
	String copy(*this);
	for (int i = 0; i < copy.length(); i++) {
		if (copy[i] == char_to_search) {
			copy.string.replace(i, 1, replacement);
			return copy;
		}			
	}
	return copy;
}

String String::toLowerCase() const {
	String copy(*this);
	for (char& c : copy)
		c = std::tolower(c);
	return copy;
}

String String::toUpperCase() const {
	String copy(*this);
	for (char& c : copy)
		c = std::toupper(c);
	return copy;
}

String String::substring(int start, int end) const {
	return string.substr(start, end);
}

std::vector<String> String::split(char splitter) {

	std::vector<String> list;
	int lastIndex = 0;

	for (int i = 0; i < string.size(); i++) {
		if (string[i] == splitter) {			
			list.emplace_back(string.substr(lastIndex, i - lastIndex));
			lastIndex = i;
		}
	}

	// Push last item
	list.emplace_back(string.substr(lastIndex, length() - lastIndex));
	
	return list;
}

std::vector<String> String::split(const String& splitter) {
	return std::vector<String>();
}

bool String::startsWith(char c) const {
	return string[0] == c;
}

bool String::endsWith(char c) const {
	return string[length() - 1] == c;
}

size_t String::length() const {
	return string.size();
}

const char* String::cString() const {
	return string.c_str();
}

std::string String::toString() const {
	return string;
}

bool String::isEmpty() const {
	return string.empty();
}

bool String::equals(const String& o) const {
	return string == o.string;
}

char String::charAt(int index) {
	if (index < 0 || index >= length())
		throw StringIndexOutOfBounds("Cannot access charAt index " + std::to_string(index) + " of a String of length " + std::to_string(length()));
	return string[index];
}

int String::indexOf(char c) const {
	return indexOf(c, 0);
}

int String::indexOf(char c, int fromIndex) const {
	for (uint32_t i = fromIndex; i < length(); i++) {
		if (string[i] == c)
			return i;
	}
	return -1;
}

std::basic_string<char>::iterator String::begin() {
	return string.begin();
}

std::basic_string<char>::iterator String::end() {
	return string.end();
}

char& String::operator[](int index) {
	if (index < 0 || index >= length())
		throw StringIndexOutOfBounds("Cannot access charAt index " + std::to_string(index) + " of a String of length " + std::to_string(length()));
	return string.at(index);
}

std::ostream& operator<<(std::ostream& os, const String& s) {
	os << s.string;
	return os;
}
