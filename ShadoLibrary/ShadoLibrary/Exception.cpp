#include "includes/Exception.h"


Exception::Exception(const std::string& msg) {
	this->msg = msg;
}

Exception::Exception() : Exception("An exception was thrown") {};

std::string Exception::getMessage() const {
	return msg;
}

//====================================================
IndexOutOfBound::IndexOutOfBound(const std::string& msg) {
	this->msg = msg;
}

IndexOutOfBound::IndexOutOfBound() {
	this->msg = "Cannot access an element outside the bounds of the List/String";
}

IndexOutOfBound::IndexOutOfBound(const Exception& e) {
	this->cause = e;
}

Exception& IndexOutOfBound::getCause() {
	return cause;
}

//====================================================
NullPointerException::NullPointerException(const std::string& msg) {
	this->msg = msg;
}

NullPointerException::NullPointerException() {
	this->msg = "A null pointer exception was thrown";
}

NullPointerException::NullPointerException(const Exception& e) {
	this->cause = e;
}

Exception& NullPointerException::getCause() {
	return cause;
}


