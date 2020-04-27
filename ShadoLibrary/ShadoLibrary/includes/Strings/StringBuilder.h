#pragma once

#include <string>
#include <vector>

class StringBuilder {
public:
	StringBuilder(const std::string& str);
	StringBuilder(int initial_size);
	StringBuilder(const StringBuilder& other);
	StringBuilder();
	~StringBuilder();

	StringBuilder& append(int s);
	StringBuilder& append(char s);
	StringBuilder& append(long s);
	StringBuilder& append(float s);
	StringBuilder& append(double s);
	StringBuilder& append(bool s);
	StringBuilder& append(const std::string& s);

	unsigned int length() const;

	std::string toString() const;
	bool equals(StringBuilder& o) const;
	bool equals(std::string& o) const;

	template <typename T>
	StringBuilder& operator << (T val);
	
	operator std::string() const { return toString(); }
	
private:
	std::vector<std::string> buffer;
};

template <typename T>
StringBuilder& StringBuilder::operator<<(T val) {
	append(val);
	return *this;
}
