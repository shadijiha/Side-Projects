#pragma once

#include "core.h"
#include <exception>
#include <string>

class SHADO_MATH_API Exception : std::exception {
public:
	Exception(const std::string& msg);

	Exception();

	Exception(const Exception& other) = default;

	std::string getMessage() const;

protected:
	std::string msg;
};


// Exceptions
class SHADO_MATH_API IndexOutOfBound : public Exception {
public:
	IndexOutOfBound(const std::string& msg);

	IndexOutOfBound();

	IndexOutOfBound(const Exception& e);

	Exception& getCause();
private:
	std::string msg;
	Exception cause;
};

class SHADO_MATH_API NullPointerException : public Exception {
public:
	NullPointerException(const std::string& msg);

	NullPointerException();

	NullPointerException(const Exception& e);

	Exception& getCause();
private:
	std::string msg;
	Exception cause;
};


typedef IndexOutOfBound ArrayListOutOfBoundException;
typedef IndexOutOfBound StringIndexOutOfBounds;
typedef IndexOutOfBound LinkedListIndexOutOfBoundException;

