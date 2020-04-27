#pragma once
#include <string>
#include <vector>
#include <iostream>
#include "includes/Exception.h"

class String {
public:
	String(const char* str);
	String(const std::string& str);
	String();
	~String();

	void concat(const char* str);
	void concat(const std::string& str);
	void concat(const String& str);

	// Replacement functions (They do not modify the calling string)
	String replaceAll(char char_to_search, const std::string& replacement) const;
	String replaceFirst(char char_to_search, const std::string& replacement) const;

	String toLowerCase() const;
	String toUpperCase() const;
	
	String substring(int start, int end) const;

	std::vector<String> split(char splitter);
	std::vector<String> split(const String& splitter);

	bool startsWith(char c) const;
	bool endsWith(char c) const;

	// Getters
	size_t length() const;
	const char* cString() const;
	std::string toString() const;
	bool isEmpty() const;
	bool equals(const String& o) const;
	char charAt(int index);

	int indexOf(char c) const;
	int indexOf(char c, int fromIndex) const;

	std::basic_string<char>::iterator begin();
	std::basic_string<char>::iterator end();

	// Custom cast operators
	operator std::string () const { return string; }
	operator const char* () const { return string.c_str(); }

	// binary operators
	char& operator[] (int index);
	String& operator + (const String& s) { concat(s); return *this; }
	bool operator == (const String& o) const { return equals(o); }

	friend std::ostream& operator<<(std::ostream& os, const String& s);

	// STATIC
	template <typename T>
	static String toString(T val);

private:
	std::string string;
};

template <typename T>
String String::toString(T val) {
	return String(std::to_string(val));
}
