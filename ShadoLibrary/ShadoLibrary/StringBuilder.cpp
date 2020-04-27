#include "includes/Strings/StringBuilder.h"

StringBuilder::StringBuilder(const std::string& str) {
	buffer.push_back(str);
}

StringBuilder::StringBuilder(int initial_size) {
	buffer.reserve(initial_size);
}

StringBuilder::StringBuilder(const StringBuilder& other) {
	buffer.reserve(other.buffer.size());
	buffer = std::vector<std::string>(other.buffer);
}


StringBuilder::StringBuilder() {
	buffer.reserve(5);
}

StringBuilder::~StringBuilder() = default;

StringBuilder& StringBuilder::append(int s) {
	buffer.push_back(std::to_string(s));
	return *this;
}

StringBuilder& StringBuilder::append(char s) {
	buffer.push_back(std::to_string(s));
	return *this;
}

StringBuilder& StringBuilder::append(long s) {
	buffer.push_back(std::to_string(s));
	return *this;
}

StringBuilder& StringBuilder::append(float s) {
	buffer.push_back(std::to_string(s));
	return *this;
}

StringBuilder& StringBuilder::append(double s) {
	buffer.push_back(std::to_string(s));
	return *this;
}

StringBuilder& StringBuilder::append(bool s) {
	buffer.push_back(std::to_string(s));
	return *this;
}

StringBuilder& StringBuilder::append(const std::string& s) {
	buffer.push_back(s);
	return *this;
}

unsigned StringBuilder::length() const {
	return toString().size();
}

std::string StringBuilder::toString() const {
	std::string result;
	for (auto s : buffer)
		result += s;
	return result;
}

bool StringBuilder::equals(StringBuilder& o) const {

	// They don't have same size so they are not equal;
	if (buffer.size() != o.buffer.size())
		return false;
	
	for (int i = 0; i < buffer.size(); i++) {
		if (buffer[i] != o.buffer[i])
			return false;
	}

	return true;
}

bool StringBuilder::equals(std::string& o) const {
	return toString() == o;
}
